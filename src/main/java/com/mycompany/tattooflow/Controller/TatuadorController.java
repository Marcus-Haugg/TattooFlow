/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Controller;

import com.mycompany.tattooflow.Model.Tatuador;
import java.util.ArrayList;

/**
 *
 * @author marcus.arenhardt
 */
public class TatuadorController {
    ArrayList<Tatuador> t = new ArrayList<Tatuador>();
    
    public void salvar(Tatuador tat){    
        t.add(tat);
    }
    
    public ArrayList<Tatuador> recuperarTodos(){
        return t;
    }
}
