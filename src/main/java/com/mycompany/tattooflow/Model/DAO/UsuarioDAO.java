/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Model.DAO;

import apoio.ConexaoBD;
import com.mycompany.tattooflow.Model.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author marcus
 */
public class UsuarioDAO {

    public Usuario autenticar(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = '" + email + "' AND senha = MD5('" + senha + "')";

        System.out.println("sql: " + sql);
        ResultSet resultadoQ = ConexaoBD.executeQuery(sql);

        if (resultadoQ.next()) {
            Usuario u = new Usuario();
            u.setId(resultadoQ.getInt("id"));
            u.setEmail(resultadoQ.getString("email"));
            u.setSenha(resultadoQ.getString("senha"));
            return u;
        }
        
         return null;

    }

}
