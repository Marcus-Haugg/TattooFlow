/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Controller;

import com.mycompany.tattooflow.Model.Estilo;
import com.mycompany.tattooflow.Model.DAO.EstiloDAO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author marcus
 */
public class EstiloController {

    EstiloDAO estiloDAO = new EstiloDAO();

    public boolean salvar(Estilo e) {
        try {
            estiloDAO.salvar(e);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar estilo: " + ex.getMessage());
            return false;
        }
    }

    public ArrayList<Estilo> recuperarTodos() {
        try {
            return estiloDAO.recuperarTodos();
        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar estilos: " + ex.getMessage());
            return null;
        }
    }

    public boolean excluir(int id) {
        try {
            estiloDAO.excluir(id);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir estilo: " + ex.getMessage());
            return false;
        }
    }

}
