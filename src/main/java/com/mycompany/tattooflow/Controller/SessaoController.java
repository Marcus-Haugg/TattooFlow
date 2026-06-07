/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Controller;

import com.mycompany.tattooflow.Model.DAO.SessaoDAO;
import com.mycompany.tattooflow.Model.Sessao;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author marcus
 */
public class SessaoController {

    SessaoDAO sessaoDAO = new SessaoDAO();

    public boolean salvar(Sessao s) {
        try {
            sessaoDAO.salvar(s);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar sessao: " + ex.getMessage());
            return false;
        }
    }

    public ArrayList<Sessao> recuperarTodos() {
        try {
            return sessaoDAO.recuperarTodos();
        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar sessoes: " + ex.getMessage());
            return null;
        }
    }

    public boolean excluir(int id) {
        try {
            sessaoDAO.excluir(id);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir sessao: " + ex.getMessage());
            return false;
        }
    }

    public boolean editar(Sessao s) {
        try {
            sessaoDAO.editar(s);
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro ao editar sessao: " + ex.getMessage());
            return false;
        }
    }

}
