package com.mycompany.tattooflow.Model.DAO;

import apoio.ConexaoBD;
import com.mycompany.tattooflow.Model.Cliente;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testa a classe ClienteDAO usando o banco de dados:
 * 1) Cria tabela clientes em @BeforeClass (se não existir)
 * 2) No @Before, limpa o conteúdo da tabela antes de cada teste
 * 3) Executa os testes
 * 4) Em @AfterClass, dropa a tabela e fecha a conexão
 */
public class ClienteDAOTest {

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS clientes ( " +
            "  id        SERIAL PRIMARY KEY, " +
            "  nome      VARCHAR(100) NOT NULL, " +
            "  cpf       VARCHAR(11)  NOT NULL, " +
            "  email     VARCHAR(100) NOT NULL, " +
            "  celular   VARCHAR(11), " +
            "  data_nasc VARCHAR(10) " +
            ");";

    private ClienteDAO dao;

    @BeforeClass
    public static void setUpClass() {
        try {
            ConexaoBD.executeUpdate(SQL_CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException("Erro em @BeforeClass: falha ao verificar tabela 'clientes'.\n" + e.getMessage(), e);
        }
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            ConexaoBD.executeUpdate("DELETE FROM clientes;");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao limpar tabela 'clientes'.\n" + e.getMessage(), e);
        }
        ConexaoBD.getInstance().shutdown();
    }

    @Before
    public void setUp() {
        ConexaoBD.getInstance().shutdown();
        dao = new ClienteDAO();
        try {
            ConexaoBD.executeUpdate("TRUNCATE TABLE clientes RESTART IDENTITY CASCADE;");
        } catch (SQLException e) {
            throw new RuntimeException("Erro em @Before: falha ao truncar tabela 'clientes'.\n" + e.getMessage(), e);
        }
    }

    @After
    public void tearDown() {
        ConexaoBD.getInstance().shutdown();
    }

    /**
     * Teste 6 - Testa a inserção e recuperação de um cliente:
     * 1) Insere um cliente via dao.salvar
     * 2) Recupera todos e valida que existe somente 1
     * 3) Recupera o primeiro e valida os dados
     */
    @Test
    public void testInserirERecuperarCliente() throws SQLException {
        // 1) Cria objeto e insere no banco
        Cliente c = new Cliente();
        c.setNome("Ana Paula");
        c.setCpf("12345678909");
        c.setEmail("ana@email.com");
        c.setCelular("51999990000");
        c.setData_nasc("01/01/1990");
        dao.salvar(c);

        // 2) Recupera todos, deve haver 1
        ArrayList<Cliente> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula", lista);
        assertEquals("Deve haver exatamente 1 cliente na tabela", 1, lista.size());

        // 3) Recupera o primeiro e valida os dados
        Cliente recuperado = lista.get(0);
        assertNotNull("Cliente recuperado não deve ser null", recuperado);
        assertEquals(c.getNome(), recuperado.getNome());
        assertEquals(c.getCpf(), recuperado.getCpf());
        assertEquals(c.getEmail(), recuperado.getEmail());
        assertEquals(c.getCelular(), recuperado.getCelular());
        assertEquals(c.getData_nasc(), recuperado.getData_nasc());
    }

    /**
     * Teste 7 - Testa a edição de um cliente:
     * 1) Insere um cliente inicial
     * 2) Altera campos via dao.editar(...)
     * 3) Recupera e valida as alterações
     */
    @Test
    public void testEditarCliente() throws SQLException {
        // 1) Insere um registro inicial
        Cliente c = new Cliente();
        c.setNome("Joao Antigo");
        c.setCpf("00000000000");
        c.setEmail("joao@email.com");
        c.setCelular("51900000000");
        c.setData_nasc("10/05/1985");
        dao.salvar(c);

        // 2) Edita com novos dados (ID=1 pois RESTART IDENTITY)
        Cliente modificado = new Cliente();
        modificado.setId(1);
        modificado.setNome("Joao Modificado");
        modificado.setCpf("11111111111");
        modificado.setEmail("joao.novo@email.com");
        modificado.setCelular("51911111111");
        modificado.setData_nasc("10/05/1986");
        dao.editar(modificado);

        // 3) Recupera e valida os campos atualizados
        ArrayList<Cliente> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula após edição", lista);
        assertEquals("Deve haver exatamente 1 cliente na tabela", 1, lista.size());

        Cliente recuperado = lista.get(0);
        assertEquals("Joao Modificado", recuperado.getNome());
        assertEquals("11111111111", recuperado.getCpf());
        assertEquals("joao.novo@email.com", recuperado.getEmail());
    }

    /**
     * Teste 8 - Testa a exclusão de um cliente:
     * 1) Insere um cliente
     * 2) Chama dao.excluir(1)
     * 3) Verifica que recuperarTodos() retorna lista vazia
     */
    @Test
    public void testExcluirCliente() throws SQLException {
        // 1) Insere um registro
        Cliente c = new Cliente();
        c.setNome("Maria Exclusao");
        c.setCpf("99999999999");
        c.setEmail("maria@email.com");
        c.setCelular("51922222222");
        c.setData_nasc("15/08/1995");
        dao.salvar(c);

        // Confirma que existe antes da exclusão
        ArrayList<Cliente> antes = dao.recuperarTodos();
        assertFalse("Deve haver clientes antes da exclusão", antes.isEmpty());

        // 2) Exclui o cliente de ID = 1
        dao.excluir(1);

        // 3) Verifica que recuperarTodos() retorna lista vazia
        ArrayList<Cliente> depois = dao.recuperarTodos();
        assertNotNull("Lista retornada por recuperarTodos não deve ser null", depois);
        assertTrue("Lista deve estar vazia após exclusão do cliente", depois.isEmpty());
    }

    /**
     * Teste 9 - Testa a inserção de múltiplos clientes e a recuperação de todos:
     * 1) Insere 3 clientes
     * 2) Recupera todos e valida a quantidade
     */
    @Test
    public void testRecuperarTodosComMultiplosClientes() throws SQLException {
        // 1) Insere 3 clientes
        String[] nomes = {"Pedro", "Lucas", "Mariana"};
        for (String nome : nomes) {
            Cliente c = new Cliente();
            c.setNome(nome);
            c.setCpf("00000000000");
            c.setEmail(nome.toLowerCase() + "@email.com");
            c.setCelular("51900000000");
            c.setData_nasc("01/01/2000");
            dao.salvar(c);
        }

        // 2) Recupera todos e valida
        ArrayList<Cliente> lista = dao.recuperarTodos();
        assertNotNull("Lista não deve ser nula", lista);
        assertEquals("Deve haver exatamente 3 clientes na tabela", 3, lista.size());
    }
}
