package com.mycompany.tattooflow.Model.DAO;

import apoio.ConexaoBD;
import com.mycompany.tattooflow.Model.Tatuador;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testa a classe TatuadorDAO usando o banco de dados:
 * 1) Cria tabela tatuadores em @BeforeClass (se não existir)
 * 2) No @Before, limpa o conteúdo da tabela antes de cada teste
 * 3) Executa os testes
 * 4) Em @AfterClass, dropa a tabela e fecha a conexão
 */
public class TatuadorDAOTest {

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS tatuadores ( " +
            "  id      SERIAL PRIMARY KEY, " +
            "  nome    VARCHAR(100) NOT NULL, " +
            "  email   VARCHAR(100) NOT NULL, " +
            "  celular VARCHAR(11) " +
            ");";

    private TatuadorDAO dao;

    @BeforeClass
    public static void setUpClass() {
        try {
            ConexaoBD.executeUpdate(SQL_CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException("Erro em @BeforeClass: falha ao verificar tabela 'tatuadores'.\n" + e.getMessage(), e);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            ConexaoBD.executeUpdate("DELETE FROM tatuadores;");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao limpar tabela 'tatuadores'.\n" + e.getMessage(), e);
        }
        ConexaoBD.getInstance().shutdown();
    }

    @Before
    public void setUp() {
        ConexaoBD.getInstance().shutdown();
        dao = new TatuadorDAO();
        try {
            ConexaoBD.executeUpdate("TRUNCATE TABLE tatuadores RESTART IDENTITY CASCADE;");
        } catch (SQLException e) {
            throw new RuntimeException("Erro em @Before: falha ao truncar tabela 'tatuadores'.\n" + e.getMessage(), e);
        }
    }

    @After
    public void tearDown() {
        ConexaoBD.getInstance().shutdown();
    }

    /**
     * Teste 10 - Testa a inserção e recuperação de um tatuador:
     * 1) Insere um tatuador via dao.salvar
     * 2) Recupera todos e valida que existe somente 1
     * 3) Recupera o primeiro e valida os dados
     */
    @Test
    public void testInserirERecuperarTatuador() throws SQLException {
        // 1) Cria objeto e insere no banco
        Tatuador t = new Tatuador();
        t.setNome("Carlos Silva");
        t.setEmail("carlos@tattoo.com");
        t.setCelular("51988881111");
        dao.salvar(t);

        // 2) Recupera todos, deve haver 1
        ArrayList<Tatuador> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula", lista);
        assertEquals("Deve haver exatamente 1 tatuador na tabela", 1, lista.size());

        // 3) Recupera o primeiro e valida os dados
        Tatuador recuperado = lista.get(0);
        assertNotNull("Tatuador recuperado não deve ser null", recuperado);
        assertEquals(t.getNome(), recuperado.getNome());
        assertEquals(t.getEmail(), recuperado.getEmail());
        assertEquals(t.getCelular(), recuperado.getCelular());
    }

    /**
     * Teste 11 - Testa a edição de um tatuador:
     * 1) Insere um tatuador inicial
     * 2) Altera campos via dao.editar(...)
     * 3) Recupera e valida as alterações
     */
    @Test
    public void testEditarTatuador() throws SQLException {
        // 1) Insere um registro inicial
        Tatuador t = new Tatuador();
        t.setNome("Rafael Antigo");
        t.setEmail("rafael@tattoo.com");
        t.setCelular("51900000000");
        dao.salvar(t);

        // 2) Edita com novos dados (ID=1 pois RESTART IDENTITY)
        Tatuador modificado = new Tatuador();
        modificado.setId(1);
        modificado.setNome("Rafael Modificado");
        modificado.setEmail("rafael.novo@tattoo.com");
        modificado.setCelular("51911111111");
        dao.editar(modificado);

        // 3) Recupera e valida os campos atualizados
        ArrayList<Tatuador> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula após edição", lista);
        assertEquals("Deve haver exatamente 1 tatuador na tabela", 1, lista.size());

        Tatuador recuperado = lista.get(0);
        assertEquals("Rafael Modificado", recuperado.getNome());
        assertEquals("rafael.novo@tattoo.com", recuperado.getEmail());
        assertEquals("51911111111", recuperado.getCelular());
    }

    /**
     * Teste 12 - Testa a exclusão de um tatuador:
     * 1) Insere um tatuador
     * 2) Chama dao.excluir(1)
     * 3) Verifica que recuperarTodos() retorna lista vazia
     */
    @Test
    public void testExcluirTatuador() throws SQLException {
        // 1) Insere um registro
        Tatuador t = new Tatuador();
        t.setNome("Fernanda Exclusao");
        t.setEmail("fernanda@tattoo.com");
        t.setCelular("51922222222");
        dao.salvar(t);

        // Confirma que existe antes da exclusão
        ArrayList<Tatuador> antes = dao.recuperarTodos();
        assertFalse("Deve haver tatuadores antes da exclusão", antes.isEmpty());

        // 2) Exclui o tatuador de ID = 1
        dao.excluir(1);

        // 3) Verifica que recuperarTodos() retorna lista vazia
        ArrayList<Tatuador> depois = dao.recuperarTodos();
        assertNotNull("Lista retornada por recuperarTodos não deve ser null", depois);
        assertTrue("Lista deve estar vazia após exclusão do tatuador", depois.isEmpty());
    }

    /**
     * Teste 13 - Testa a inserção de múltiplos tatuadores e a recuperação de todos:
     * 1) Insere 3 tatuadores
     * 2) Recupera todos e valida a quantidade
     */
    @Test
    public void testRecuperarTodosComMultiplosTatuadores() throws SQLException {
        // 1) Insere 3 tatuadores
        String[] nomes = {"Bruno", "Camila", "Diego"};
        for (String nome : nomes) {
            Tatuador t = new Tatuador();
            t.setNome(nome);
            t.setEmail(nome.toLowerCase() + "@tattoo.com");
            t.setCelular("51900000000");
            dao.salvar(t);
        }

        // 2) Recupera todos e valida
        ArrayList<Tatuador> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula", lista);
        assertEquals("Deve haver exatamente 3 tatuadores na tabela", 3, lista.size());
    }
}
