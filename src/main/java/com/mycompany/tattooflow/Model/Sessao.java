/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Model;

/**
 *
 * @author marcus.arenhardt
 */
public class Sessao {
    
    private int id;
    private int clienteId;
    private int tatuadorId;
    private int tatuagenId;
    private String dataHora;
    private String duracaoMinutos;
    private String descricao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getTatuadorId() {
        return tatuadorId;
    }

    public void setTatuadorId(int tatuadorId) {
        this.tatuadorId = tatuadorId;
    }

    public int getTatuagenId() {
        return tatuagenId;
    }

    public void setTatuagenId(int tatuagenId) {
        this.tatuagenId = tatuagenId;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(String duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
