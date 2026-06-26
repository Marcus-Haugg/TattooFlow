package com.mycompany.tattooflow.Model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TatuagemTest {

    private Tatuagem tatuagem;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        tatuagem = new Tatuagem();
    }

    @After
    public void tearDown() {
        tatuagem = null;
    }

    // Teste 16
    @Test
    public void testGettersAndSetters() {
        // Arrange
        int idEsperado = 1;
        int tatuadorIdEsperado = 2;
        int estiloIdEsperado = 3;
        String descricaoEsperada = "Dragão nas costas";
        String dataCriacaoEsperada = "15/03/2024";

        // Act
        tatuagem.setId(idEsperado);
        tatuagem.setTatuadorId(tatuadorIdEsperado);
        tatuagem.setEstiloId(estiloIdEsperado);
        tatuagem.setDescricao(descricaoEsperada);
        tatuagem.setDataCriacao(dataCriacaoEsperada);

        // Assert
        assertEquals(idEsperado, tatuagem.getId());
        assertEquals(tatuadorIdEsperado, tatuagem.getTatuadorId());
        assertEquals(estiloIdEsperado, tatuagem.getEstiloId());
        assertEquals(descricaoEsperada, tatuagem.getDescricao());
        assertEquals(dataCriacaoEsperada, tatuagem.getDataCriacao());
    }
}
