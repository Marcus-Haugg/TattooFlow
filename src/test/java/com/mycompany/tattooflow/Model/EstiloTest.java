package com.mycompany.tattooflow.Model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class EstiloTest {

    private Estilo estilo;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        estilo = new Estilo();
    }

    @After
    public void tearDown() {
        estilo = null;
    }

    // Teste 5
    @Test
    public void testGettersAndSetters() {
        // Arrange
        int idEsperado = 3;
        String nomeEstiloEsperado = "Old School";

        // Act
        estilo.setId(idEsperado);
        estilo.setNomeEstilo(nomeEstiloEsperado);

        // Assert
        assertEquals(idEsperado, estilo.getId());
        assertEquals(nomeEstiloEsperado, estilo.getNomeEstilo());
    }
}
