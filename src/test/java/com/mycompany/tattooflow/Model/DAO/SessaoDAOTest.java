package com.mycompany.tattooflow.Model.DAO;

import apoio.ConexaoBD;
import com.mycompany.tattooflow.Model.Sessao;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testa a classe SessaoDAO usando o banco de dados:
 * 1) Cria tabelas dependentes em @BeforeClass (se não existirem)
 * 2) No @Before, limpa sessoes e recria registros pai antes de cada teste
 * 3) Executa os testes
 * 4) Em @AfterClass, limpa os dados e fecha a conexão
 */
public class SessaoDAOTest {

    private static final String SQL_CREATE_CLIENTES =
            "CREATE TABLE IF NOT EXISTS clientes ( " +
            "  id        SERIAL PRIMARY KEY, " +
            "  nome      VARCHAR(100) NOT NULL, " +
            "  cpf       VARCHAR(11)  NOT NULL, " +
            "  email     VARCHAR(100) NOT NULL, " +
            "  celular   VARCHAR(11), " +
            "  data_nasc VARCHAR(10) " +
            ");";

    private static final String SQL_CREATE_TATUADORES =
            "CREATE TABLE IF NOT EXISTS tatuadores ( " +
            "  id      SERIAL PRIMARY KEY, " +
            "  nome    VARCHAR(100) NOT NULL, " +
            "  email   VARCHAR(100) NOT NULL, " +
            "  celular VARCHAR(11) " +
            ");";

    private static final String SQL_CREATE_SESSOES =
            "CREATE TABLE IF NOT EXISTS sessoes ( " +
            "  id               SERIAL PRIMARY KEY, " +
            "  cliente_id       INTEGER NOT NULL, " +
            "  tatuador_id      INTEGER NOT NULL, " +
            "  tatuagem_id      INTEGER NOT NULL, " +
            "  data_hora        VARCHAR(20), " +
            "  duracao_minutos  VARCHAR(10), " +
            "  descricao        VARCHAR(500), " +
            "  CONSTRAINT fk_sessoes_cliente  FOREIGN KEY (cliente_id)  REFERENCES clientes(id), " +
            "  CONSTRAINT fk_sessoes_tatuador FOREIGN KEY (tatuador_id) REFERENCES tatuadores(id) " +
            ");";

    private SessaoDAO dao;

    @BeforeClass
    public static void setUpClass() {
        try {
            ConexaoBD.executeUpdate(SQL_CREATE_CLIENTES);
            ConexaoBD.executeUpdate(SQL_CREATE_TATUADORES);
            ConexaoBD.executeUpdate(SQL_CREATE_SESSOES);
        } catch (SQLException e) {
            throw new RuntimeException("Erro em @BeforeClass: falha ao verificar tabelas.\n" + e.getMessage(), e);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            ConexaoBD.executeUpdate("DELETE FROM sessoes;");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao limpar tabela 'sessoes'.\n" + e.getMessage(), e);
        }
        ConexaoBD.getInstance().shutdown();
    }

    @Before
    public void setUp() {
        ConexaoBD.getInstance().shutdown();
        dao = new SessaoDAO();
        try {
            // Limpa a tabela filha antes de truncar os pais
            ConexaoBD.executeUpdate("TRUNCATE TABLE sessoes RESTART IDENTITY;");
            ConexaoBD.executeUpdate("TRUNCATE TABLE clientes RESTART IDENTITY CASCADE;");
            ConexaoBD.executeUpdate("TRUNCATE TABLE tatuadores RESTART IDENTITY CASCADE;");
            // Insere registros pai (id=1 pois RESTART IDENTITY)
            ConexaoBD.executeUpdate("INSERT INTO clientes (nome, cpf, email, celular, data_nasc) VALUES ('Cliente Teste', '00000000000', 'cliente@email.com', '51900000000', '01/01/2000');");
            ConexaoBD.executeUpdate("INSERT INTO tatuadores (nome, email, celular) VALUES ('Tatuador Teste', 'teste@tattoo.com', '51900000000');");
        } catch (SQLException e) {
            throw new RuntimeException("Erro em @Before: falha ao preparar tabelas.\n" + e.getMessage(), e);
        }
    }

    @After
    public void tearDown() {
        ConexaoBD.getInstance().shutdown();
    }

    /**
     * Teste 22 - Testa a inserção e recuperação de uma sessão:
     * 1) Insere uma sessão via dao.salvar
     * 2) Recupera todas e valida que existe somente 1
     * 3) Recupera a primeira e valida os dados
     */
    @Test
    public void testInserirERecuperarSessao() throws SQLException {
        // 1) Cria objeto e insere no banco
        Sessao s = new Sessao();
        s.setClienteId(1);
        s.setTatuadorId(1);
        s.setTatuagenId(1);
        s.setDataHora("20/06/2024 14:30");
        s.setDuracaoMinutos("120");
        s.setDescricao("Sessao inicial de tatuagem");
        dao.salvar(s);

        // 2) Recupera todas, deve haver 1
        ArrayList<Sessao> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula", lista);
        assertEquals("Deve haver exatamente 1 sessão na tabela", 1, lista.size());

        // 3) Recupera a primeira e valida os dados
        Sessao recuperada = lista.get(0);
        assertNotNull("Sessão recuperada não deve ser null", recuperada);
        assertEquals(s.getClienteId(), recuperada.getClienteId());
        assertEquals(s.getTatuadorId(), recuperada.getTatuadorId());
        assertEquals(s.getDataHora(), recuperada.getDataHora());
        assertEquals(s.getDuracaoMinutos(), recuperada.getDuracaoMinutos());
        assertEquals(s.getDescricao(), recuperada.getDescricao());
    }

    /**
     * Teste 23 - Testa a edição de uma sessão:
     * 1) Insere uma sessão inicial
     * 2) Altera campos via dao.editar(...)
     * 3) Recupera e valida as alterações
     */
    @Test
    public void testEditarSessao() throws SQLException {
        // 1) Insere um registro inicial
        Sessao s = new Sessao();
        s.setClienteId(1);
        s.setTatuadorId(1);
        s.setTatuagenId(1);
        s.setDataHora("01/01/2024 10:00");
        s.setDuracaoMinutos("60");
        s.setDescricao("Descricao antiga");
        dao.salvar(s);

        // 2) Edita com novos dados (ID=1 pois RESTART IDENTITY)
        Sessao modificada = new Sessao();
        modificada.setId(1);
        modificada.setClienteId(1);
        modificada.setTatuadorId(1);
        modificada.setTatuagenId(1);
        modificada.setDataHora("15/06/2024 15:00");
        modificada.setDuracaoMinutos("180");
        modificada.setDescricao("Descricao modificada");
        dao.editar(modificada);

        // 3) Recupera e valida os campos atualizados
        ArrayList<Sessao> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula após edição", lista);
        assertEquals("Deve haver exatamente 1 sessão na tabela", 1, lista.size());

        Sessao recuperada = lista.get(0);
        assertEquals("15/06/2024 15:00", recuperada.getDataHora());
        assertEquals("180", recuperada.getDuracaoMinutos());
        assertEquals("Descricao modificada", recuperada.getDescricao());
    }

    /**
     * Teste 24 - Testa a exclusão de uma sessão:
     * 1) Insere uma sessão
     * 2) Chama dao.excluir(1)
     * 3) Verifica que recuperarTodos() retorna lista vazia
     */
    @Test
    public void testExcluirSessao() throws SQLException {
        // 1) Insere um registro
        Sessao s = new Sessao();
        s.setClienteId(1);
        s.setTatuadorId(1);
        s.setTatuagenId(1);
        s.setDataHora("10/05/2024 09:00");
        s.setDuracaoMinutos("90");
        s.setDescricao("Sessao para exclusao");
        dao.salvar(s);

        // Confirma que existe antes da exclusão
        ArrayList<Sessao> antes = dao.recuperarTodos();
        assertFalse("Deve haver sessões antes da exclusão", antes.isEmpty());

        // 2) Exclui a sessão de ID = 1
        dao.excluir(1);

        // 3) Verifica que recuperarTodos() retorna lista vazia
        ArrayList<Sessao> depois = dao.recuperarTodos();
        assertNotNull("Lista retornada por recuperarTodos não deve ser null", depois);
        assertTrue("Lista deve estar vazia após exclusão da sessão", depois.isEmpty());
    }

    /**
     * Teste 25 - Testa a inserção de múltiplas sessões e a recuperação de todas:
     * 1) Insere 3 sessões
     * 2) Recupera todas e valida a quantidade
     */
    @Test
    public void testRecuperarTodosComMultiplasSessoes() throws SQLException {
        // 1) Insere 3 sessões
        String[] descricoes = {"Primeira sessao", "Segunda sessao", "Terceira sessao"};
        for (String descricao : descricoes) {
            Sessao s = new Sessao();
            s.setClienteId(1);
            s.setTatuadorId(1);
            s.setTatuagenId(1);
            s.setDataHora("01/01/2024 10:00");
            s.setDuracaoMinutos("60");
            s.setDescricao(descricao);
            dao.salvar(s);
        }

        // 2) Recupera todas e valida
        ArrayList<Sessao> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula", lista);
        assertEquals("Deve haver exatamente 3 sessões na tabela", 3, lista.size());
    }
}
