/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Controller;

import com.mycompany.tattooflow.Model.DAO.TatuagemDAO;
import com.mycompany.tattooflow.Model.Tatuagem;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author marcus
 */
public class TatuagemController {

    TatuagemDAO tatuagemDAO = new TatuagemDAO();

    public boolean salvar(Tatuagem t) {
        try {
            tatuagemDAO.salvar(t);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar tatuagem: " + ex.getMessage());
            return false;
        }
    }

    public ArrayList<Tatuagem> recuperarTodos() {
        try {
            return tatuagemDAO.recuperarTodos();
        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar tatuagens: " + ex.getMessage());
            return null;
        }
    }

    public boolean editar(Tatuagem t) {
        try {
            tatuagemDAO.editar(t);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao editar tatuagem: " + ex.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        try {
            tatuagemDAO.excluir(id);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir tatuagem: " + ex.getMessage());
            return false;
        }
    }

}
