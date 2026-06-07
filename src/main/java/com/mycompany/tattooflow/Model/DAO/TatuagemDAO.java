/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Model.DAO;

import apoio.ConexaoBD;
import com.mycompany.tattooflow.Model.Tatuagem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author marcus
 */
public class TatuagemDAO {

    public void salvar(Tatuagem t) throws SQLException {
        String sql = "INSERT INTO tatuagens (tatuador_id, estilo_id, descricao, data_criacao) VALUES ("
                + t.getTatuadorId() + ","
                + t.getEstiloId() + ","
                + "'" + t.getDescricao() + "',"
                + "'" + t.getDataCriacao() + "'"
                + ")";
        System.out.println("sql: " + sql);
        ConexaoBD.executeUpdate(sql);
    }

    public ArrayList<Tatuagem> recuperarTodos() throws SQLException {
        ArrayList<Tatuagem> tatuagens = new ArrayList();
        String sql = "SELECT * FROM tatuagens";
        ResultSet resultadoQ = ConexaoBD.executeQuery(sql);
        while (resultadoQ.next()) {
            Tatuagem t = new Tatuagem();
            t.setId(resultadoQ.getInt("id"));
            t.setTatuadorId(resultadoQ.getInt("tatuador_id"));
            t.setEstiloId(resultadoQ.getInt("estilo_id"));
            t.setDescricao(resultadoQ.getString("descricao"));
            t.setDataCriacao(resultadoQ.getString("data_criacao"));
            tatuagens.add(t);
        }
        return tatuagens;
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM tatuagens WHERE id = " + id;
        System.out.println("sql: " + sql);
        ConexaoBD.executeUpdate(sql);
    }

    public void editar(Tatuagem t) throws SQLException {
        String sql = "UPDATE tatuagens SET "
                + "tatuador_id = " + t.getTatuadorId() + ","
                + "estilo_id = " + t.getEstiloId() + ","
                + "descricao = '" + t.getDescricao() + "',"
                + "data_criacao = '" + t.getDataCriacao() + "'"
                + " WHERE id = " + t.getId();
        System.out.println("sql: " + sql);
        ConexaoBD.executeUpdate(sql);
    }

}
