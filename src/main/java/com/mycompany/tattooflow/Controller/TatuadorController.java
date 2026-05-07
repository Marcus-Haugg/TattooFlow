/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Controller;

import com.mycompany.tattooflow.Model.DAO.TatuadorDAO;
import com.mycompany.tattooflow.Model.Tatuador;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author marcus.arenhardt
 */
public class TatuadorController {
   TatuadorDAO tatuadorDAO = new TatuadorDAO();

public boolean salvar(Tatuador t) {
    try {
        tatuadorDAO.salvar(t);
        return true;
    } catch (SQLException ex) {
        System.out.println("Erro ao salvar tatuador: " + ex.getMessage());
        return false;
    }
}

public ArrayList<Tatuador> recuperarTodos() {
    try {
        return tatuadorDAO.recuperarTodos();
    } catch (SQLException ex) {
        System.out.println("Erro ao recuperar tatuadores: " + ex.getMessage());
        return null;
    }
}

public boolean excluir(int id) {
    try {
        tatuadorDAO.excluir(id);
        return true;
    } catch (SQLException ex) {
        System.out.println("Erro ao excluir tatuador: " + ex.getMessage());
        return false;
    }
}
}
