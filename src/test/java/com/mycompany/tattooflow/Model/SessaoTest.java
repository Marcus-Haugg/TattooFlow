package com.mycompany.tattooflow.Model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class SessaoTest {

    private Sessao sessao;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        sessao = new Sessao();
    }

    @After
    public void tearDown() {
        sessao = null;
    }

    // Teste 17
    @Test
    public void testGettersAndSetters() {
        // Arrange
        int idEsperado = 1;
        int clienteIdEsperado = 2;
        int tatuadorIdEsperado = 3;
        int tatuagenIdEsperado = 4;
        String dataHoraEsperada = "20/06/2024 14:30";
        String duracaoMinutosEsperada = "120";
        String descricaoEsperada = "Sessao de retoque";

        // Act
        sessao.setId(idEsperado);
        sessao.setClienteId(clienteIdEsperado);
        sessao.setTatuadorId(tatuadorIdEsperado);
        sessao.setTatuagenId(tatuagenIdEsperado);
        sessao.setDataHora(dataHoraEsperada);
        sessao.setDuracaoMinutos(duracaoMinutosEsperada);
        sessao.setDescricao(descricaoEsperada);

        // Assert
        assertEquals(idEsperado, sessao.getId());
        assertEquals(clienteIdEsperado, sessao.getClienteId());
        assertEquals(tatuadorIdEsperado, sessao.getTatuadorId());
        assertEquals(tatuagenIdEsperado, sessao.getTatuagenId());
        assertEquals(dataHoraEsperada, sessao.getDataHora());
        assertEquals(duracaoMinutosEsperada, sessao.getDuracaoMinutos());
        assertEquals(descricaoEsperada, sessao.getDescricao());
    }
}
