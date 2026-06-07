/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Model.DAO;

import apoio.ConexaoBD;
import com.mycompany.tattooflow.Model.Sessao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author marcus
 */
public class SessaoDAO {

    public void salvar(Sessao s) throws SQLException {
        String sql = "INSERT INTO sessoes (cliente_id, tatuador_id, tatuagem_id, data_hora, duracao_minutos, descricao) VALUES ("
                + s.getClienteId() + ","
                + s.getTatuadorId() + ","
                + s.getTatuagenId() + ","
                + "'" + s.getDataHora() + "',"
                + s.getDuracaoMinutos() + ","
                + "'" + s.getDescricao() + "'"
                + ")";
        System.out.println("sql: " + sql);
        ConexaoBD.executeUpdate(sql);
    }

    public ArrayList<Sessao> recuperarTodos() throws SQLException {
        ArrayList<Sessao> sessoes = new ArrayList();
        String sql = "SELECT * FROM sessoes";
        ResultSet resultadoQ = ConexaoBD.executeQuery(sql);
        while (resultadoQ.next()) {
            Sessao s = new Sessao();
            s.setId(resultadoQ.getInt("id"));
            s.setClienteId(resultadoQ.getInt("cliente_id"));
            s.setTatuadorId(resultadoQ.getInt("tatuador_id"));
            s.setTatuagenId(resultadoQ.getInt("tatuagem_id"));
            s.setDataHora(resultadoQ.getString("data_hora"));
            s.setDuracaoMinutos(resultadoQ.getString("duracao_minutos"));
            s.setDescricao(resultadoQ.getString("descricao"));
            sessoes.add(s);
        }
        return sessoes;
    }

    public void editar(Sessao s) throws SQLException {
        String sql = "UPDATE sessoes SET "
                + "cliente_id = " + s.getClienteId() + ","
                + "tatuador_id = " + s.getTatuadorId() + ","
                + "tatuagem_id = " + s.getTatuagenId() + ","
                + "data_hora = '" + s.getDataHora() + "',"
                + "duracao_minutos = " + s.getDuracaoMinutos() + ","
                + "descricao = '" + s.getDescricao() + "'"
                + " WHERE id = " + s.getId();
        System.out.println("sql: " + sql);
        ConexaoBD.executeUpdate(sql);
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM sessoes WHERE id = " + id;
        System.out.println("sql: " + sql);
        ConexaoBD.executeUpdate(sql);
    }

}
