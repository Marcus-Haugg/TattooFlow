/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Controller;

import com.mycompany.tattooflow.Model.Cliente;
import java.util.ArrayList;

/**
 *
 * @author marcus.arenhardt
 */
public class ClienteController {
    ArrayList<Cliente> c = new ArrayList<Cliente>();
    
    public void salvar(Cliente cli){    
        c.add(cli);
    }
    
    public void imprimirTodos(){
        for (int i = 0; i < c.size() && i < 10; i++) {
            System.out.println(c.get(i).getNome());   
        }
    }
    
    public ArrayList<Cliente> recuperarTodos(){
        return c;
    }
  
}
