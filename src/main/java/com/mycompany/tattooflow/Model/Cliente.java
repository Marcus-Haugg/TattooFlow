/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Model;

import com.mycompany.tattooflow.Controller.ClienteController;
import java.util.ArrayList;

/**
 *
 * @author marcus.arenhardt
 */
public class Cliente {
    ArrayList<ClienteController> c = new ArrayList<ClienteController>();
    
    public void salvar(ClienteController cli){    
        c.add(cli);
    };
    
    public void imprimirTodos(){
        for (int i = 0; c.size() < 10; i++) {
            System.out.println(c.get(i).getNome());   
        }
    };
    
    public ArrayList<ClienteController> recuperarTodos(){
        return c;
    }
  
}
