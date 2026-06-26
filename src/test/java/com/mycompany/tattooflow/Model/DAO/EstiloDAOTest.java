package com.mycompany.tattooflow.Model.DAO;

import apoio.ConexaoBD;
import com.mycompany.tattooflow.Model.Estilo;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testa a classe EstiloDAO usando o banco de dados:
 * 1) Cria tabela estilos em @BeforeClass (se não existir)
 * 2) No @Before, limpa o conteúdo da tabela antes de cada teste
 * 3) Executa os testes
 * 4) Em @AfterClass, dropa a tabela e fecha a conexão
 */
public class EstiloDAOTest {

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS estilos ( " +
            "  id          SERIAL PRIMARY KEY, " +
            "  nome_estilo VARCHAR(100) NOT NULL " +
            ");";

    private EstiloDAO dao;

    @BeforeClass
    public static void setUpClass() {
        try {
            ConexaoBD.executeUpdate(SQL_CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException("Erro em @BeforeClass: falha ao verificar tabela 'estilos'.\n" + e.getMessage(), e);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            ConexaoBD.executeUpdate("DELETE FROM estilos;");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao limpar tabela 'estilos'.\n" + e.getMessage(), e);
        }
        ConexaoBD.getInstance().shutdown();
    }

    @Before
    public void setUp() {
        ConexaoBD.getInstance().shutdown();
        dao = new EstiloDAO();
        try {
            ConexaoBD.executeUpdate("TRUNCATE TABLE estilos RESTART IDENTITY CASCADE;");
        } catch (SQLException e) {
            throw new RuntimeException("Erro em @Before: falha ao truncar tabela 'estilos'.\n" + e.getMessage(), e);
        }
    }

    @After
    public void tearDown() {
        ConexaoBD.getInstance().shutdown();
    }

    /**
     * Teste 14 - Testa a inserção e recuperação de um estilo:
     * 1) Insere um estilo via dao.salvar
     * 2) Recupera todos e valida que existe somente 1
     * 3) Recupera o primeiro e valida os dados
     */
    @Test
    public void testInserirERecuperarEstilo() throws SQLException {
        // 1) Cria objeto e insere no banco
        Estilo e = new Estilo();
        e.setNomeEstilo("Old School");
        dao.salvar(e);

        // 2) Recupera todos, deve haver 1
        ArrayList<Estilo> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula", lista);
        assertEquals("Deve haver exatamente 1 estilo na tabela", 1, lista.size());

        // 3) Recupera o primeiro e valida os dados
        Estilo recuperado = lista.get(0);
        assertNotNull("Estilo recuperado não deve ser null", recuperado);
        assertEquals(e.getNomeEstilo(), recuperado.getNomeEstilo());
    }

    /**
     * Teste 15 - Testa a exclusão de um estilo:
     * 1) Insere um estilo
     * 2) Chama dao.excluir(1)
     * 3) Verifica que recuperarTodos() retorna lista vazia
     */
    @Test
    public void testExcluirEstilo() throws SQLException {
        // 1) Insere um registro
        Estilo e = new Estilo();
        e.setNomeEstilo("Realismo");
        dao.salvar(e);

        // Confirma que existe antes da exclusão
        ArrayList<Estilo> antes = dao.recuperarTodos();
        assertFalse("Deve haver estilos antes da exclusão", antes.isEmpty());

        // 2) Exclui o estilo de ID = 1
        dao.excluir(1);

        // 3) Verifica que recuperarTodos() retorna lista vazia
        ArrayList<Estilo> depois = dao.recuperarTodos();
        assertNotNull("Lista retornada por recuperarTodos não deve ser null", depois);
        assertTrue("Lista deve estar vazia após exclusão do estilo", depois.isEmpty());
    }
}
