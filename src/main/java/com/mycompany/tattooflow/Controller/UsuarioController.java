/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Controller;

import com.mycompany.tattooflow.Model.DAO.UsuarioDAO;
import com.mycompany.tattooflow.Model.Usuario;
import java.sql.SQLException;

/**
 *
 * @author marcus
 */
public class UsuarioController {

    UsuarioDAO uDAO = new UsuarioDAO();

    public Usuario autenticar(String email, String senha) {
        try {
            return uDAO.autenticar(email, senha);
        } catch (SQLException ex) {
            System.out.println("Erro ao autenticar usuário: " + ex.getMessage());
            
        }
        
        return null;
    }

}
