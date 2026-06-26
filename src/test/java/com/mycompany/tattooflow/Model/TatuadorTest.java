package com.mycompany.tattooflow.Model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TatuadorTest {

    private Tatuador tatuador;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        tatuador = new Tatuador();
    }

    @After
    public void tearDown() {
        tatuador = null;
    }

    // Teste 4
    @Test
    public void testGettersAndSetters() {
        // Arrange
        int idEsperado = 2;
        String nomeEsperado = "Carlos Silva";
        String emailEsperado = "carlos@tattoo.com";
        String celularEsperado = "(51) 98888-1111";

        // Act
        tatuador.setId(idEsperado);
        tatuador.setNome(nomeEsperado);
        tatuador.setEmail(emailEsperado);
        tatuador.setCelular(celularEsperado);

        // Assert
        assertEquals(idEsperado, tatuador.getId());
        assertEquals(nomeEsperado, tatuador.getNome());
        assertEquals(emailEsperado, tatuador.getEmail());
        assertEquals(celularEsperado, tatuador.getCelular());
    }
}
