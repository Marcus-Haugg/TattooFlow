package apoio;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConexaoBDTest {

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        try {
            ConexaoBD instance = ConexaoBD.getInstance();
            if (instance.getConnection() != null) {
                instance.shutdown();
            }
        } catch (Exception e) {
            // ignora se não há conexão ativa
        }
    }

    // Teste 1
    @Test
    public void testConexaoNaoNula() throws SQLException {
        Connection conn = ConexaoBD.getInstance().getConnection();
        assertNotNull("A conexão não deve ser nula", conn);
    }

    // Teste 2
    @Test
    public void testConexaoAberta() throws SQLException {
        Connection conn = ConexaoBD.getInstance().getConnection();
        assertFalse("A conexão não deve estar fechada", conn.isClosed());
        conn.close();
    }
}
