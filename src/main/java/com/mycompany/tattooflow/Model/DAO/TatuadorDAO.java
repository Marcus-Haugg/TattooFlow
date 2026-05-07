/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Model.DAO;

import apoio.ConexaoBD;
import com.mycompany.tattooflow.Model.Tatuador;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author marcus
 */
public class TatuadorDAO {
    private ResultSet resultadoQ = null;

public void salvar(Tatuador t) throws SQLException {
    String sql = ""
            + "INSERT INTO tatuadores (nome, email, celular) VALUES ("
            + "'" + t.getNome() + "',"
            + "'" + t.getEmail() + "',"
            + "'" + t.getCelular() + "'"
            + ")";
    System.out.println("sql: " + sql);
    ConexaoBD.executeUpdate(sql);
}

public ArrayList<Tatuador> recuperarTodos() throws SQLException {
    ArrayList<Tatuador> tatuadores = new ArrayList();
    String sql = "SELECT * FROM tatuadores";
    ResultSet resultadoQ = ConexaoBD.executeQuery(sql);
    while (resultadoQ.next()) {
        Tatuador t = new Tatuador();
        t.setId(resultadoQ.getInt("id"));
        t.setNome(resultadoQ.getString("nome"));
        t.setEmail(resultadoQ.getString("email"));
        t.setCelular(resultadoQ.getString("celular"));
        tatuadores.add(t);
    }
    return tatuadores;
}

public void excluir(int id) throws SQLException {
    String sql = "DELETE FROM tatuadores WHERE id = " + id;
    System.out.println("sql: " + sql);
    ConexaoBD.executeUpdate(sql);
}
    
}
