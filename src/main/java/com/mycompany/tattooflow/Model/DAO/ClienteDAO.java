/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Model.DAO;

import apoio.ConexaoBD;
import com.mycompany.tattooflow.Model.Cliente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author marcus
 */
public class ClienteDAO {

    private ResultSet resultadoQ = null; // interface que representa o resultado de uma consulta SQL executada em um banco de dados

    public void salvar(Cliente c) throws SQLException {

        String sql = ""
                + "INSERT INTO clientes (nome, cpf, email, celular, data_nasc) VALUES ("
                + "'" + c.getNome() + "',"
                + "'" + c.getCpf() + "',"
                + "'" + c.getEmail() + "',"
                + "'" + c.getCelular() + "',"
                + "'" + c.getData_nasc() + "'"
                + ")";
        System.out.println("sql: " + sql);
        ConexaoBD.executeUpdate(sql);

    }

    public ArrayList<Cliente> recuperarTodos() throws SQLException {
        ArrayList<Cliente> clientes = new ArrayList();

        String sql = "SELECT * FROM clientes";
        ResultSet resultadoQ = ConexaoBD.executeQuery(sql);
        while (resultadoQ.next()) {
            Cliente c = new Cliente();
            c.setId(resultadoQ.getInt("id"));
            c.setNome(resultadoQ.getString("nome"));
            c.setCpf(resultadoQ.getString("cpf"));
            c.setEmail(resultadoQ.getString("email"));
            c.setCelular(resultadoQ.getString("celular"));
            c.setData_nasc(resultadoQ.getString("data_nasc"));
            clientes.add(c);
        }
        return clientes;
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = " + id;
        System.out.println("sql: " + sql);
        ConexaoBD.executeUpdate(sql);
    }
    
    public void editar(Cliente c) throws SQLException {
    String sql = "UPDATE clientes SET "
            + "nome = '" + c.getNome() + "',"
            + "cpf = '" + c.getCpf() + "',"
            + "email = '" + c.getEmail() + "',"
            + "celular = '" + c.getCelular() + "',"
            + "data_nasc = '" + c.getData_nasc() + "'"
            + " WHERE id = " + c.getId();
    System.out.println("sql: " + sql);
    ConexaoBD.executeUpdate(sql);
}

}
