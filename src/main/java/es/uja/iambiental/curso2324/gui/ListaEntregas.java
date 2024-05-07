/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uja.iambiental.curso2324.gui;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Alberto Limas
 */
public class ListaEntregas extends JPanel{
    
   
    public ListaEntregas() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setSize(200, 800);
    }
    
    public void agregarEntrega(String entrega){
        System.out.println(entrega);
        //this.add(new JLabel(entrega));
        this.add(new JLabel(entrega,JLabel.LEFT));
        this.repaint();
        this.setVisible(true);
    }
    
    public void quitarEntrega(){
        remove(this.getComponents()[0]);
        this.repaint();
    }

    
    
}
