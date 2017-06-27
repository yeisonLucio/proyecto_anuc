package datos;


import com.toedter.calendar.JDateChooser;
import control.Metodos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



public class ConexionBD {
    
    DefaultTableModel modelo;
    Connection con=null;
    DefaultListModel modeloLista;
    GetterAndSetter gys = new GetterAndSetter();
    
    
    //------------------------------------------------------------------------    
    //--------------------METODO DE CONEXION.------------------------------
    //------------------------------------------------------------------------
    
    public void crearTxt() throws IOException{
         
         JTextField host = new JTextField();
         JTextField nameBD = new JTextField();
         JTextField user = new JTextField();
         JPasswordField password = new JPasswordField();
         Object[] message = {
         "Host:", host,
         "Name:", nameBD,
         "User:", user,
         "Password:", password,
         };
         int option = JOptionPane.showConfirmDialog(null, message, "Llena el formulario", JOptionPane.OK_CANCEL_OPTION);
         if(option == JOptionPane.OK_OPTION){
            File archivo = new File("parametros.txt");
            FileWriter escribir=new FileWriter(archivo);
            escribir.write(host.getText()+"\n");
            escribir.write(nameBD.getText()+"\n");
            escribir.write(user.getText()+"\n");
            escribir.write(password.getText()+"\n");
            escribir.close();
         }else {
             System.exit(0);
         }
    }
    public void obtenerParametros(){
            
            try{
            String ruta = "parametros.txt";
            File archivo = new File(ruta);
            
                if(archivo.exists()) {
                    FileReader fr = new FileReader(archivo);
                    BufferedReader br = new BufferedReader(fr);
                    String [] linea= new String[4];
                    int n=0;

                    while (n<4) {
                        linea[n]=br.readLine();

                        n++;
                    }
                    //System.out.println("Linea es "+Arrays.toString(linea));
                    gys.setHost(linea[0]);
                    gys.setNomBD(linea[1]);
                    gys.setUser(linea[2]);
                    gys.setPassword(linea[3]);
                }
                else{
                    crearTxt();
                    obtenerParametros();
                }
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Ocurrio un error al cargar los datos de entrada \n"+e);
            }
    }
    public Connection getConexion(String host, String nomBD, String user, String password) throws SQLException{
		try {
                    
                    obtenerParametros();
                    host = gys.getHost();
                    nomBD = gys.getNomBD();
                    user = gys.getUser();
                    password = gys.getPassword();
                    String url = "jdbc:mysql://"+host+"/"+nomBD+"";
			Class.forName("com.mysql.jdbc.Driver");
                        con=DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.out.println("error "+e);
			e.printStackTrace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido establecer \nconexion con el servidor servidor:\n"+e);
                        System.out.println("el error es sql "+e);
                        System.exit(0);
                        
		}
		return con;
   }
    //------------------------------------------------------------------------    
    //--------------------METODO DE LLENAR .------------------------------
    //------------------------------------------------------------------------
    public void llenarTablaDocumentos(JTable tabla, DefaultTableModel model) throws SQLException{
        try{
        
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
        ResultSet rs = stmt.executeQuery("select * from valoresadicionales p where p.campo = 'documento'");   
        ResultSetMetaData rsMd = rs.getMetaData();
        
        int cantidadColumnas = rsMd.getColumnCount();   
        int columBoolean = 1;
         
        Object filas[]= new Object[cantidadColumnas];
        while(rs.next()){    
          for (int i = 1; i <= cantidadColumnas; i++) {
                  if(i==columBoolean){
                      filas[columBoolean-1]=Boolean.FALSE;
                  }else{
                      filas[i-1]=rs.getObject(i);
                  }
          }
        model.addRow(filas);
        }
        tabla.updateUI();
        
        rs.close() ;
        stmt.close() ;
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
            System.out.println("error "+ex);
        }
    }
    public void llenarComboEstudios(JComboBox combo) throws SQLException{
        try {
            Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
            ResultSet rs = stmt.executeQuery("select * from valoresadicionales p where p.campo = 'estudios' order by valor asc");
            ResultSetMetaData rsMd = rs.getMetaData();
            while(rs.next())
                combo.addItem(rs.getString("valor").toUpperCase());//nombre es el campo de la base de datos
            rs.close();
            stmt.close();
         } catch (Exception e) {
             System.out.println("error en conexion al extraer combo estudios"+e);
         }
    }
    public void llenarComboSector(JComboBox combo) throws SQLException{
        try {
            Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
            ResultSet rs = stmt.executeQuery("select * from valoresadicionales p where p.campo = 'sector' order by valor asc");
            ResultSetMetaData rsMd = rs.getMetaData();
            while(rs.next())
                combo.addItem(rs.getString("valor").toUpperCase());//nombre es el campo de la base de datos
            rs.close();
            stmt.close();
         } catch (Exception e) {
             System.out.println("error en conexion al extraer combo estudios"+e);
         }
    }
    public void llenarListaProyectos(JComboBox combo) throws SQLException{
        try {
            Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
            ResultSet rs = stmt.executeQuery("select p.abreviatura from proyecto p");
            ResultSetMetaData rsMd = rs.getMetaData();
            while(rs.next())
                combo.addItem(rs.getString("abreviatura").toUpperCase());//nombre es el campo de la base de datos
            rs.close();
            stmt.close();
         } catch (Exception e) {
             System.out.println("error en conexion al extraer combos"+e);
         }
    }
    public void llenarListaMunicipio(JList lista){
    try {
            modeloLista= new DefaultListModel();
            lista.setModel(modeloLista);
            Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
            
            ResultSet rs = stmt.executeQuery("select m.nombre from municipio m order by nombre asc");
            ResultSetMetaData rsMd = rs.getMetaData();
            while(rs.next())
                modeloLista.addElement(rs.getString("nombre").toUpperCase());//nombre es el campo de la base de datos
            lista.setModel(modeloLista);
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("error al cargar lista "+ex);
        }
     }
    public void llenarListaMunicipio(JComboBox lista){
        try {
            Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;        
            ResultSet rs = stmt.executeQuery("select m.nombre from municipio m order by idmunicipio asc");
            ResultSetMetaData rsMd = rs.getMetaData();
            while(rs.next())
                lista.addItem(rs.getString("nombre").toUpperCase());//nombre es el campo de la base de datos
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("error al cargar el combo "+ex);
        }
     }
    public void llenarComboVeredaSegunMunicipio(JComboBox lista, String nomMuni){
        try {    
            lista.removeAllItems();
            Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
            
            ResultSet rs = stmt.executeQuery("select v.nombre from vereda v where municipio_idmunicipio = (select m.idmunicipio from municipio m where m.nombre = '"+nomMuni+"') order by nombre asc");
            ResultSetMetaData rsMd = rs.getMetaData();
            while(rs.next())
                lista.addItem(rs.getString("nombre").toUpperCase());//nombre es el campo de la base de datos
            
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.out.println("error al cargar el combo "+ex);
        }
     }
    public void llenarTablaMunicipio(JTable tabla){
       try{
        modelo = new DefaultTableModel(){
            public boolean isCellEditable(int fila, int columna){
                return false;
            }
        };
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setModel(modelo);
        //esta linea es para que se ajuste automaticamente al espacio de la columna
        //tabla.setAutoResizeMode(tabla.AUTO_RESIZE_OFF);
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
        ResultSet rs = stmt.executeQuery("SELECT m.nombre MUNICIPIO, v.nombre VEREDA FROM municipio m INNER JOIN "
                + "vereda v ON m.idmunicipio=v.municipio_idmunicipio order by m.nombre asc");   
        ResultSetMetaData rsMd = rs.getMetaData();
        int cantidadColumnas = rsMd.getColumnCount();   
        for (int i = 1; i < cantidadColumnas+1; i++) {
            modelo.addColumn(rsMd.getColumnLabel(i));
        } 
        Object matrizFila[]= new Object[cantidadColumnas];
        while(rs.next()){    
          for (int i = 0; i < cantidadColumnas; i++) {
                  matrizFila[i]=rs.getString(i+1);
          }
        modelo.addRow(matrizFila);
        }
        // Close the result set, statement
        rs.close() ;
        stmt.close() ;
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
            System.out.println("error "+ex);
        }
     }
    public void llenarTablaProyecto(JTable tabla){
        try{
        modelo = new DefaultTableModel(){
            public boolean isCellEditable(int fila, int columna){
                return false;
            }
        };
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setModel(modelo);
        //esta linea es para que se ajuste automaticamente al espacio de la columna
        //tabla.setAutoResizeMode(tabla.AUTO_RESIZE_OFF);
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
        ResultSet rs = stmt.executeQuery("SELECT p.idproyecto ID, p.abreviatura NOMBRE, p.cantidad BENEFICIARIOS, p.rubro PRESUPUESTO, p.aporte APORTE, p.estado ESTADO from proyecto p");       
        ResultSetMetaData rsMd = rs.getMetaData();
        int cantidadColumnas = rsMd.getColumnCount();   
        for (int i = 1; i < cantidadColumnas+1; i++) {
            modelo.addColumn(rsMd.getColumnLabel(i));
        } 
        Object matrizFila[]= new Object[cantidadColumnas];
        while(rs.next()){    
            for (int i = 0; i < cantidadColumnas; i++) {
                matrizFila[i]=rs.getString(i+1);
            }
        modelo.addRow(matrizFila);
        }
        // Close the result set, statement
        rs.close() ;
        stmt.close() ;
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
            System.out.println("error "+ex);
        }
    }
    public void CargarDatosDEProyecto(int cod, JTextArea nom, JTextField abr, JTextField can, JTextField pre,
            JTextField apo, JComboBox combo, JDateChooser fec, JTextArea obs){
    try{
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
        ResultSet rs = stmt.executeQuery("SELECT p.nombre, p.abreviatura, p.cantidad, p.rubro, p.aporte, p.estado, p.fecha, p.observaciones from proyecto p where idproyecto = "+cod);   
        ResultSetMetaData rsMd = rs.getMetaData();
        while(rs.next()){
            nom.setText(rs.getString(1));
            abr.setText(rs.getString(2));
            can.setText(rs.getString(3));
            pre.setText(rs.getString(4));
            apo.setText(rs.getString(5));
            combo.setSelectedItem(""+rs.getObject(6));
            fec.setDate(rs.getDate(7));
            obs.setText(rs.getString(8));
        }
        // Close the result set, statement
        rs.close() ;
        stmt.close() ;
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
            System.out.println("error "+ex);
        }
    }
    //------------------------------------------------------------------------    
    //--------------------METODO DE OBTENER .------------------------------
    //------------------------------------------------------------------------
        public String getNomMunicipio(int id) throws SQLException{
         String cad=null;
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
        // Execute the query
        ResultSet rs = stmt.executeQuery("SELECT m.nombre from municipio m where m.idmunicipio = "+id);
        // Loop through the result set
        while(rs.next()){
            cad = rs.getString(1);
        }
        rs.close();
        stmt.close();
        return cad; 
    }
    public int getIDvereda(JComboBox combo){
        int r=0;
        try {
            Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
            ResultSet rs=stmt.executeQuery("select v.idvereda from vereda v where v.nombre = '"+combo.getSelectedItem()+"'");
            while(rs.next())
                r = rs.getInt("idvereda");
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"error \n" +ex); 
           
        }
        return r;
    }
    public int getIDpersonaConCedula(String cc){
        int id=0;
        try {
            Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
            ResultSet rs=stmt.executeQuery("select idpersona from persona where identificacion = '"+cc+"'");
            while(rs.next())
                id = rs.getInt("idpersona");
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"error \n" +ex); 
           
        }
        return id;
    }
    public int getIDproyectoConAbreviatura(String abreviatura){
        int id=0;
        try {
            Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
            ResultSet rs=stmt.executeQuery("select idproyecto from proyecto where abreviatura = '"+abreviatura+"'");
            while(rs.next())
                id = rs.getInt("idpersona");
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null,"error \n" +ex); 
           
        }
        return id;
    }
    
    //------------------------------------------------------------------------    
    //--------------------METODO DE VARIOS .------------------------------
    //------------------------------------------------------------------------
    
    public boolean verificarSiExistePersona(String id) throws SQLException{
        try {
            boolean band = false;
            Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
            ResultSet rs = stmt.executeQuery("select identificacion from persona where identificacion = '"+id+"'");
            ResultSetMetaData rsMd = rs.getMetaData();
            String aux=null;
            while(rs.next()&&band==false){
                aux = rs.getString("identificacion");
                if(id.equalsIgnoreCase(aux)){
                    band = true;
                }
            }
            rs.close();
            stmt.close();
            if(band){
                return true;
            }else{
                return false;
            }
         } catch (Exception e) {
             System.out.println("error en conexion al verificar persona"+e);
             return false;
         }
    }
    public boolean verificarSiEstaEnProyecto(String idPer) throws SQLException{
        try {
            boolean band = false;
            Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
            ResultSet rs = stmt.executeQuery("select idpersona_proyecto from persona_proyecto where persona_idpersona = '"+idPer+"'");
            ResultSetMetaData rsMd = rs.getMetaData();
            String aux=null;
            while(rs.next()&&band==false){
                aux = rs.getString("idpersona_proyecto");
                if(aux!=null){
                    band = true;
                }
            }
            rs.close();
            stmt.close();
            
            if(band){
                return true;
            }else{
                return false;
            }
         } catch (Exception e) {
             System.out.println("error en conexion al verificar persona en proyecto "+e);
             return false;
         }
    }
    public String listarAbreviaturaProyectoSegunIDpersona(int id) throws SQLException{
        String cad="";
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
        // Execute the query
        ResultSet rs = stmt.executeQuery("select p.abreviatura from proyecto p join persona_proyecto pp on "
                + "p.idproyecto = pp.proyecto_idproyecto and pp.persona_idpersona = "+id);
        // Loop through the result set
        while(rs.next()){
            cad = rs.getString(1)+"\n"+cad;
        }
        rs.close();
        stmt.close();
        return cad;
    }
   //este trae los proyectos y los envia a "crea botones"
    public void listarProyectos(JPanel pnl, JTable table) throws SQLException{
        Metodos mt = new Metodos();
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
        // Execute the query
        ResultSet rs = stmt.executeQuery("select * from proyecto");
        // Loop through the result set
        while(rs.next()){
        //select * persona where       
            mt.creaBotonesProyecto(pnl, rs.getString(3), table);
            
        }
        // Close the result set, statement
        rs.close() ;
        stmt.close() ;
    }
    
    
    public void ConsultarSegunBoton( JTable tabla, String dndePY){
    try{
        modelo = new DefaultTableModel(){
        public boolean isCellEditable(int fila, int columna){
            return false;
        }};
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setModel(modelo);
        //esta linea es para que se ajuste automaticamente al espacio de la columna
        tabla.setAutoResizeMode(tabla.AUTO_RESIZE_OFF);
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
        //ResultSet rs = stmt.executeQuery("SELECT * FROM proyecto WHERE nombre = '"+dndePY+"'");
        ResultSet rs = stmt.executeQuery("select p.nombre,per.* from aplica ap join proyecto p on ap.proyecto_cod=p.cod_proyecto " +
                                            "join persona per on ap.persona_cod=per.cod_persona where cod_proyecto='1'");
        System.out.println("si paso x aki");
        ResultSetMetaData rsMd = rs.getMetaData();  
        int cantidadColumnas = rsMd.getColumnCount();
        for (int i = 1; i < cantidadColumnas+1; i++) {
            modelo.addColumn(rsMd.getColumnLabel(i));
        } 
        Object matrizFila[]= new Object[cantidadColumnas];
        while(rs.next()){    
            for (int i = 0; i < cantidadColumnas; i++) {
                matrizFila[i]=rs.getString(i+1);
            }
            modelo.addRow(matrizFila);
        }
        // Close the result set, statement
        rs.close() ;
        stmt.close() ;
        System.out.println("esta en Finaliza en ConexionBD en void ConsultarSegunBoton");
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
            System.out.println("error "+ex);
        }
    }
    public void mostrarTodos( JTable tabla){
    try{
        modelo = new DefaultTableModel(){
            public boolean isCellEditable(int fila, int columna){
                return false;
            }
        };
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setModel(modelo);
        //esta linea es para que se ajuste automaticamente al espacio de la columna
        tabla.setAutoResizeMode(tabla.AUTO_RESIZE_OFF);
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
      
        ResultSet rs = stmt.executeQuery("select * from vistapersona");
        ResultSetMetaData rsMd = rs.getMetaData();  
      
        int cantidadColumnas = rsMd.getColumnCount();   
        for (int i = 1; i < cantidadColumnas+1; i++) {
           modelo.addColumn(rsMd.getColumnLabel(i));
        } 
        Object matrizFila[]= new Object[cantidadColumnas];
        while(rs.next()){    
            for (int i = 0; i < cantidadColumnas; i++) {
                matrizFila[i]=rs.getString(i+1);
            }
            modelo.addRow(matrizFila);
        }
        // Close the result set, statement
        rs.close() ;
        stmt.close() ;
        }catch(Exception ex){
           JOptionPane.showMessageDialog(null, ex);
           System.out.println("error "+ex);
        }
    }
    
    public void BuscarEnTablaProyecto(JTable tabla, String letra){
    try{
        modelo = new DefaultTableModel(){
            public boolean isCellEditable(int fila, int columna){
                return false;
            }
        };
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setModel(modelo);
        //esta linea es para que se ajuste automaticamente al espacio de la columna
        //tabla.setAutoResizeMode(tabla.AUTO_RESIZE_OFF);
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
        ResultSet rs = stmt.executeQuery("SELECT p.idproyecto ID, p.abreviatura NOMBRE, p.cantidad BENEFICIARIOS, p.rubro PRESUPUESTO, p.aporte APORTE, p.estado ESTADO from proyecto p where abreviatura like '%"+letra+"%'");       
        ResultSetMetaData rsMd = rs.getMetaData();
        int cantidadColumnas = rsMd.getColumnCount();   
        for (int i = 1; i < cantidadColumnas+1; i++) {
            modelo.addColumn(rsMd.getColumnLabel(i));
        } 
        Object matrizFila[]= new Object[cantidadColumnas];
        while(rs.next()){    
            for (int i = 0; i < cantidadColumnas; i++) {
                  matrizFila[i]=rs.getString(i+1);
            }
        modelo.addRow(matrizFila);
        }
        // Close the result set, statement
        rs.close() ;
        stmt.close() ;
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
            System.out.println("error "+ex);
        }
    }
    public int ultimoCodigoPersona() throws SQLException{
        int codigo=0;
        
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
        // Execute the query
        ResultSet rs = stmt.executeQuery("SELECT p.cod_persona FROM persona p ORDER BY p.cod_persona DESC LIMIT 1");
        // Loop through the result set
        while(rs.next()){
            codigo = Integer.parseInt(rs.getString(1));
            }
        rs.close();
        stmt.close();
        return codigo;
    }
    public String obtenerVereda(int id) throws SQLException{
        String cad=null;
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement() ;
        // Execute the query
        ResultSet rs = stmt.executeQuery("SELECT v.nombre from vereda v where v.idvereda = "+id);
        // Loop through the result set
        while(rs.next()){
            cad = rs.getString(1);
        }
        rs.close();
        stmt.close();
        return cad;
    }
     
     //----------------------------------------------------------------------
     //-----------METODOS DE INSERTAR------------------------------------
     //----------------------------------------------------------------------
    public void insertarProyecto(String nombre, String estado, String fecha, int numB, int presupuesto, int aporte, 
           String observaciones, String abreviatura) throws SQLException{
       
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
        stmt.executeUpdate("INSERT INTO proyecto(nombre, estado, fecha, cantidad, rubro, aporte, "
               + "Observaciones, abreviatura) VALUES "
               + "('"+nombre+"', '"+estado+"', '"+fecha+"', "+numB+", "+presupuesto+", "+aporte+", '"+observaciones+"', '"+abreviatura+"')");
        stmt.close();
    } 
    public void insertarMunicipio(String nombre) throws SQLException{
       Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
    stmt.executeUpdate("INSERT INTO municipio(nombre) VALUES"+"('"+nombre+"')");   
   }
   
   public void insertarVereda(int municipio_cod, String nombre) throws SQLException{
       Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
       stmt.executeUpdate("INSERT INTO vereda(municipio_idmunicipio,nombre) VALUES"+"("+municipio_cod+",'"+nombre+"')");
               
       stmt.close();
       
   }
   public void insertarPersonaAfiliado(String nombre, String apellido, String identificacion, int codAnuc, String telefono, int edad, 
           String genero, String estudios, String tierra, String sector, int prioridad, FileInputStream fis, int longitud, int vereda, String desplazado) throws SQLException{
       PreparedStatement ps = null;
       
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
        String insert = "INSERT INTO persona(nombre, apellido, identificacion, codigoAnuc, telefono, edad, "
               + "genero, estudios, tierra, sector, prioridad, foto, vereda_idvereda, afiliado, desplazado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
        ps = con.prepareStatement(insert);
        
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, identificacion);
        ps.setInt(4, codAnuc);
        ps.setString(5, telefono);
        ps.setInt(6, edad);
        ps.setString(7, genero);
        ps.setString(8, estudios);
        ps.setString(9, tierra);
        ps.setString(10, sector);
        ps.setInt(11, prioridad);
        ps.setBinaryStream(12, fis, longitud);
        ps.setInt(13, vereda);
        ps.setString(14, "SI");
        ps.setString(15, desplazado);
        ps.execute();
        ps.close();
        stmt.close();
    }
   public void insertarPersonaBeneficiario(String nombre, String apellido, String identificacion, Double puntajeSisben, int edad, String telefono, 
           String conyugue, String afiliado, String finca, double area, String Bcc, String Bsisben, String Bconyugue,
           String BsanaPosecion, String Bantecedentes, String Bcaracterizacion, String Bfirma, String Bgeoreferenciacion){
        try {
            PreparedStatement ps = null;
            
            Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
            String insert = "INSERT INTO persona(nombre, apellido, identificacion, codigoAnuc, telefono, edad, "
                    + "genero, estudios, tierra, sector, prioridad, foto, vereda_idvereda) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); ";
            ps = con.prepareStatement(insert);
            
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, identificacion);
        
            ps.execute();
            ps.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   public void insertarPersonaProyecto(int idpersona, int idproyecto, String fechaIngreso) throws SQLException{
       Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
       stmt.executeUpdate("INSERT INTO vereda(persona_idpersona, proyecto_idproyecto, fechaIngreso) VALUES"+"("+idpersona+","+idproyecto+",'"+fechaIngreso+"')");
               
       stmt.close();
       
   }
    //----------------------------------------------------------------------
    //-----------METODOS DE ELIMINAR------------------------------------
    //----------------------------------------------------------------------
    public void eliminarProyecto(int cod_proyecto) throws SQLException{
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
        stmt.executeUpdate("delete from proyecto where idproyecto ="+cod_proyecto);
        stmt.close();
    } 
    public void eliminarMunicipio(int idMun) throws SQLException{
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
        stmt.executeUpdate("delete from municipio where idmunicipio ="+idMun);
        stmt.close();
    }
    public void eliminarVereda(int idver) throws SQLException{
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
        stmt.executeUpdate("delete from vereda where idvereda ="+idver);
        stmt.close();
    }
    //----------------------------------------------------------------------
    //-----------METODOS DE MODIFICAR------------------------------------
    //----------------------------------------------------------------------
    
    public void modificarProyecto(int cod, String nombre, String estado, String fecha, int numB, int presupuesto, int aporte, 
           String observaciones, String abreviatura) throws SQLException{
       
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
        stmt.executeUpdate("update proyecto set nombre='"+nombre+"', estado='"+estado+"', fecha='"+fecha+"', cantidad="+numB+","
                + " rubro="+presupuesto+", aporte="+aporte+", Observaciones='"+observaciones+"', abreviatura='"+abreviatura+"' "
                + "where idproyecto= "+cod);
        stmt.close();
    } 
    public void modificarMunicipio(int id, String nombre) throws SQLException{
       
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
        stmt.executeUpdate("update municipio set nombre='"+nombre+"' where idmunicipio = "+id);
        stmt.close();
    }
    public void modificarVereda(int id, String nombre, int municipio) throws SQLException{
       
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
        stmt.executeUpdate("update vereda set nombre='"+nombre+"', municipio_idmunicipio = "+municipio+" where idvereda = "+id);
        stmt.close();
    }
    public void modificarPersonaAfiliado(String nombre, String apellido, String identificacion, int codAnuc, String telefono, int edad, 
           String genero, String estudios, String tierra, String sector, int prioridad, FileInputStream fis, int longitud, int vereda, String desplazado, int idpersona) throws SQLException{
       PreparedStatement ps = null;
       
        Statement stmt = getConexion(gys.getHost(), gys.getNomBD(), gys.getUser(), gys.getPassword()).createStatement();
            String insert = "update persona set nombre = ?, apellido = ?, identificacion = ?, codigoAnuc = ?, telefono = ?, edad = ?, "
               + "genero = ?, estudios = ?, tierra= ?, sector = ?, prioridad = ?, foto = ?, vereda_idvereda = ?, afiliado = ?, desplazado = ?"
                    + "where idpersona = ? ";
        ps = con.prepareStatement(insert);
        
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, identificacion);
        ps.setInt(4, codAnuc);
        ps.setString(5, telefono);
        ps.setInt(6, edad);
        ps.setString(7, genero);
        ps.setString(8, estudios);
        ps.setString(9, tierra);
        ps.setString(10, sector);
        ps.setInt(11, prioridad);
        ps.setBinaryStream(12, fis, longitud);
        ps.setInt(13, vereda);
        ps.setString(14, "SI");
        ps.setString(15, desplazado);
        ps.setInt(16, idpersona);
        ps.executeUpdate();
        ps.close();
        stmt.close();
    } 
     

    
}