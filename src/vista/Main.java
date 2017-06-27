package vista;

import datos.ConexionBD;


public class Main {
    
    public static ConexionBD haceConexion;
    
    
    
    public static void main(String[] args) {
                
                IU_principal p = new IU_principal();
                p.setVisible(true);
                
    }
}
