/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tattooflow;

import com.mycompany.tattooflow.View.TelaInicial;
import apoio.ConexaoBD;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author marcus.arenhardt
 */
public class TattooFlow {

    public static void main(String[] args) {
    try {
        ConexaoBD.getInstance().getConnection();
        TelaInicial tl = new TelaInicial();
        tl.setVisible(true);
    } catch (Exception e) {
        System.out.println(e);
        JOptionPane.showMessageDialog(null, "Erro de conexão com o banco de dados!\nPor favor entre em contato com o suporte.");
    } finally {
        ConexaoBD.getInstance().shutdown();
    }
}
}
    

