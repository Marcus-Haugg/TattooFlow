/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Model.DAO;

import apoio.ConexaoBD;
import com.mycompany.tattooflow.Model.Estilo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author marcus
 */
public class EstiloDAO {

    private ResultSet resultadoQ = null;

    public void salvar(Estilo e) throws SQLException {
        String sql = ""
                + "INSERT INTO estilos (nome_estilo) VALUES ("
                + "'" + e.getNomeEstilo() + "'"
                + ")";
        System.out.println("sql: " + sql);
        ConexaoBD.executeUpdate(sql);
    }

    public ArrayList<Estilo> recuperarTodos() throws SQLException {
        ArrayList<Estilo> estilos = new ArrayList();
        String sql = "SELECT * FROM estilos";
        ResultSet resultadoQ = ConexaoBD.executeQuery(sql);
        while (resultadoQ.next()) {
            Estilo e = new Estilo();
            e.setId(resultadoQ.getInt("id"));
            e.setNomeEstilo(resultadoQ.getString("nome_estilo"));
            estilos.add(e);
        }
        return estilos;
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM estilos WHERE id = " + id;
        System.out.println("sql: " + sql);
        ConexaoBD.executeUpdate(sql);
    }

}
