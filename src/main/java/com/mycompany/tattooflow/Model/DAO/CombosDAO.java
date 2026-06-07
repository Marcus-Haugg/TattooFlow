/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tattooflow.Model.DAO;

import apoio.ComboItem;
import apoio.ConexaoBD;
import java.sql.ResultSet;
import javax.swing.JComboBox;

/**
 *
 * @author marcus
 */
public class CombosDAO {

    ResultSet resultado = null;

    public void popularCombo(String tabela, JComboBox combo) {
        combo.removeAllItems();
        ComboItem item = new ComboItem();
        item.setCodigo(0);
        item.setDescricao("Selecione");
        combo.addItem(item);
        try {
            resultado = ConexaoBD.executeQuery("select * from " + tabela);
            if (resultado.isBeforeFirst()) {
                while (resultado.next()) {
                    item = new ComboItem();
                    item.setCodigo(resultado.getInt(1));
                    item.setDescricao(resultado.getString(2));
                    combo.addItem(item);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao popular Combo = " + e.toString());
        }
    }

}
