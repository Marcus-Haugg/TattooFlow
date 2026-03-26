/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Controller;

/**
 *
 * @author marcus.arenhardt
 */
public class TatuagenController {
    
    private int id;
    private int estiloId;
    private String descricao;
    private String dataCriacao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstiloId() {
        return estiloId;
    }

    public void setEstiloId(int estiloId) {
        this.estiloId = estiloId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
}
