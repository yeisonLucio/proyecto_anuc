package control;

import datos.ConexionBD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

import javax.swing.JTable;


public class Metodos implements ActionListener {
    
    ConexionBD maneBD =new ConexionBD();
    
    
    public void creaBotonesProyecto(JPanel panel, String nombreBTN, JTable tabla){
    //cada vez que es llamado me crea un boton en el panel    
    //ademas le agrega el evento a cada boton para que cargue los datos en el jtable    
        //ciclo que crea y agrega un evento a cada boton creado
      
            JButton  boton=new JButton(nombreBTN.toUpperCase());
            panel.add(boton);
            boton.setVisible(true);
            
            boton.addActionListener (new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    System.out.println("evento mouse con nombre "+nombreBTN);
                    maneBD.ConsultarSegunBoton(tabla, nombreBTN);
                    System.out.println("esta en Metodos en void creaBotonesProyecto");
                } 
            }
            );
        }//

    @Override
    public void actionPerformed(ActionEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    }

    

