package com.mycompany.tattooflow.Model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClienteTest {

    private Cliente cliente;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        cliente = new Cliente();
    }

    @After
    public void tearDown() {
        cliente = null;
    }

    // Teste 3
    @Test
    public void testGettersAndSetters() {
        // Arrange
        int idEsperado = 1;
        String nomeEsperado = "Ana Paula";
        String cpfEsperado = "123.456.789-09";
        String emailEsperado = "ana@email.com";
        String celularEsperado = "(51) 99999-0000";
        String dataNascEsperada = "01/01/1990";

        // Act
        cliente.setId(idEsperado);
        cliente.setNome(nomeEsperado);
        cliente.setCpf(cpfEsperado);
        cliente.setEmail(emailEsperado);
        cliente.setCelular(celularEsperado);
        cliente.setData_nasc(dataNascEsperada);

        // Assert
        assertEquals(idEsperado, cliente.getId());
        assertEquals(nomeEsperado, cliente.getNome());
        assertEquals(cpfEsperado, cliente.getCpf());
        assertEquals(emailEsperado, cliente.getEmail());
        assertEquals(celularEsperado, cliente.getCelular());
        assertEquals(dataNascEsperada, cliente.getData_nasc());
    }
}
