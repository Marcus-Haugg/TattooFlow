package com.mycompany.tattooflow.Model.DAO;

import apoio.ConexaoBD;
import com.mycompany.tattooflow.Model.Tatuagem;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testa a classe TatuagemDAO usando o banco de dados:
 * 1) Cria tabelas dependentes em @BeforeClass (se não existirem)
 * 2) No @Before, limpa tatuagens e recria registros pai antes de cada teste
 * 3) Executa os testes
 * 4) Em @AfterClass, limpa os dados e fecha a conexão
 */
public class TatuagemDAOTest {

    private static final String SQL_CREATE_ESTILOS =
            "CREATE TABLE IF NOT EXISTS estilos ( " +
            "  id          SERIAL PRIMARY KEY, " +
            "  nome_estilo VARCHAR(100) NOT NULL " +
            ");";

    private static final String SQL_CREATE_TATUADORES =
            "CREATE TABLE IF NOT EXISTS tatuadores ( " +
            "  id      SERIAL PRIMARY KEY, " +
            "  nome    VARCHAR(100) NOT NULL, " +
            "  email   VARCHAR(100) NOT NULL, " +
            "  celular VARCHAR(11) " +
            ");";

    private static final String SQL_CREATE_TATUAGENS =
            "CREATE TABLE IF NOT EXISTS tatuagens ( " +
            "  id           SERIAL PRIMARY KEY, " +
            "  tatuador_id  INTEGER NOT NULL, " +
            "  estilo_id    INTEGER NOT NULL, " +
            "  descricao    VARCHAR(500), " +
            "  data_criacao VARCHAR(10), " +
            "  CONSTRAINT fk_tatuagens_tatuador FOREIGN KEY (tatuador_id) REFERENCES tatuadores(id), " +
            "  CONSTRAINT fk_tatuagens_estilo   FOREIGN KEY (estilo_id)   REFERENCES estilos(id) " +
            ");";

    private TatuagemDAO dao;

    @BeforeClass
    public static void setUpClass() {
        try {
            ConexaoBD.executeUpdate(SQL_CREATE_ESTILOS);
            ConexaoBD.executeUpdate(SQL_CREATE_TATUADORES);
            ConexaoBD.executeUpdate(SQL_CREATE_TATUAGENS);
        } catch (SQLException e) {
            throw new RuntimeException("Erro em @BeforeClass: falha ao verificar tabelas.\n" + e.getMessage(), e);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            ConexaoBD.executeUpdate("DELETE FROM tatuagens;");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao limpar tabela 'tatuagens'.\n" + e.getMessage(), e);
        }
        ConexaoBD.getInstance().shutdown();
    }

    @Before
    public void setUp() {
        ConexaoBD.getInstance().shutdown();
        dao = new TatuagemDAO();
        try {
            // Limpa a tabela filha antes de truncar os pais
            ConexaoBD.executeUpdate("TRUNCATE TABLE tatuagens RESTART IDENTITY;");
            ConexaoBD.executeUpdate("TRUNCATE TABLE tatuadores RESTART IDENTITY CASCADE;");
            ConexaoBD.executeUpdate("TRUNCATE TABLE estilos RESTART IDENTITY CASCADE;");
            // Insere registros pai (id=1 pois RESTART IDENTITY)
            ConexaoBD.executeUpdate("INSERT INTO tatuadores (nome, email, celular) VALUES ('Tatuador Teste', 'teste@tattoo.com', '51900000000');");
            ConexaoBD.executeUpdate("INSERT INTO estilos (nome_estilo) VALUES ('Estilo Teste');");
        } catch (SQLException e) {
            throw new RuntimeException("Erro em @Before: falha ao preparar tabelas.\n" + e.getMessage(), e);
        }
    }

    @After
    public void tearDown() {
        ConexaoBD.getInstance().shutdown();
    }

    /**
     * Teste 18 - Testa a inserção e recuperação de uma tatuagem:
     * 1) Insere uma tatuagem via dao.salvar
     * 2) Recupera todas e valida que existe somente 1
     * 3) Recupera a primeira e valida os dados
     */
    @Test
    public void testInserirERecuperarTatuagem() throws SQLException {
        // 1) Cria objeto e insere no banco
        Tatuagem t = new Tatuagem();
        t.setTatuadorId(1);
        t.setEstiloId(1);
        t.setDescricao("Dragão nas costas");
        t.setDataCriacao("15/03/2024");
        dao.salvar(t);

        // 2) Recupera todas, deve haver 1
        ArrayList<Tatuagem> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula", lista);
        assertEquals("Deve haver exatamente 1 tatuagem na tabela", 1, lista.size());

        // 3) Recupera a primeira e valida os dados
        Tatuagem recuperada = lista.get(0);
        assertNotNull("Tatuagem recuperada não deve ser null", recuperada);
        assertEquals(t.getTatuadorId(), recuperada.getTatuadorId());
        assertEquals(t.getEstiloId(), recuperada.getEstiloId());
        assertEquals(t.getDescricao(), recuperada.getDescricao());
        assertEquals(t.getDataCriacao(), recuperada.getDataCriacao());
    }

    /**
     * Teste 19 - Testa a edição de uma tatuagem:
     * 1) Insere uma tatuagem inicial
     * 2) Altera campos via dao.editar(...)
     * 3) Recupera e valida as alterações
     */
    @Test
    public void testEditarTatuagem() throws SQLException {
        // 1) Insere um registro inicial
        Tatuagem t = new Tatuagem();
        t.setTatuadorId(1);
        t.setEstiloId(1);
        t.setDescricao("Descricao antiga");
        t.setDataCriacao("01/01/2023");
        dao.salvar(t);

        // 2) Edita com novos dados (ID=1 pois RESTART IDENTITY)
        Tatuagem modificada = new Tatuagem();
        modificada.setId(1);
        modificada.setTatuadorId(1);
        modificada.setEstiloId(1);
        modificada.setDescricao("Descricao modificada");
        modificada.setDataCriacao("10/06/2024");
        dao.editar(modificada);

        // 3) Recupera e valida os campos atualizados
        ArrayList<Tatuagem> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula após edição", lista);
        assertEquals("Deve haver exatamente 1 tatuagem na tabela", 1, lista.size());

        Tatuagem recuperada = lista.get(0);
        assertEquals("Descricao modificada", recuperada.getDescricao());
        assertEquals("10/06/2024", recuperada.getDataCriacao());
    }

    /**
     * Teste 20 - Testa a exclusão de uma tatuagem:
     * 1) Insere uma tatuagem
     * 2) Chama dao.excluir(1)
     * 3) Verifica que recuperarTodos() retorna lista vazia
     */
    @Test
    public void testExcluirTatuagem() throws SQLException {
        // 1) Insere um registro
        Tatuagem t = new Tatuagem();
        t.setTatuadorId(1);
        t.setEstiloId(1);
        t.setDescricao("Tatuagem para exclusao");
        t.setDataCriacao("05/05/2024");
        dao.salvar(t);

        // Confirma que existe antes da exclusão
        ArrayList<Tatuagem> antes = dao.recuperarTodos();
        assertFalse("Deve haver tatuagens antes da exclusão", antes.isEmpty());

        // 2) Exclui a tatuagem de ID = 1
        dao.excluir(1);

        // 3) Verifica que recuperarTodos() retorna lista vazia
        ArrayList<Tatuagem> depois = dao.recuperarTodos();
        assertNotNull("Lista retornada por recuperarTodos não deve ser null", depois);
        assertTrue("Lista deve estar vazia após exclusão da tatuagem", depois.isEmpty());
    }

    /**
     * Teste 21 - Testa a inserção de múltiplas tatuagens e a recuperação de todas:
     * 1) Insere 3 tatuagens
     * 2) Recupera todas e valida a quantidade
     */
    @Test
    public void testRecuperarTodosComMultiplasTatuagens() throws SQLException {
        // 1) Insere 3 tatuagens
        String[] descricoes = {"Rosa no braco", "Caveira no peito", "Mandala nas costas"};
        for (String descricao : descricoes) {
            Tatuagem t = new Tatuagem();
            t.setTatuadorId(1);
            t.setEstiloId(1);
            t.setDescricao(descricao);
            t.setDataCriacao("01/01/2024");
            dao.salvar(t);
        }

        // 2) Recupera todas e valida
        ArrayList<Tatuagem> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula", lista);
        assertEquals("Deve haver exatamente 3 tatuagens na tabela", 3, lista.size());
    }
}
