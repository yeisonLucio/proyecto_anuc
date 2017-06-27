package control;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Validaciones {
    
    public void validarSoloLetras(JTextField campo){
        campo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                if(Character.isDigit(c)){
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }
    
    public void validarSoloNumeros(JTextField campo){
        campo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                int k = (int)e.getKeyCode();
                if(!Character.isDigit(c)){
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }
    public void limitarCaracteres(JTextField campo, int cantPermitida){
        campo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                int tamaño = campo.getText().length();
                if(tamaño>=cantPermitida){
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }
    public void limitarCaracteres(JTextArea campo, int cantPermitida){
        campo.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                int tamaño = campo.getText().length();
                if(tamaño>=cantPermitida){
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }   
}
