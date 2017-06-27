/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import control.Validaciones;
import datos.ConexionBD;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author stivenSP
 */
public class IU_persona extends javax.swing.JFrame {

    FileInputStream fis;
    int longitudByte, apretafoto = 0;
    boolean consultar = false;
    Validaciones v = new Validaciones();
    ConexionBD bd = new ConexionBD();
    
    
    public IU_persona() {
        initComponents();
        this.setLocationRelativeTo(null);
        metodosInicio();
        cargaDeCombos();
        //autoIncrementCodigo();
        configuracionSlider();
    }
    private void configuracionSlider(){
        sliderPrioridad.setMaximum(5);
        sliderPrioridad.setMinimum(1);
        sliderPrioridad.setValue(1);
        sliderPrioridad.setMinorTickSpacing(1);
        sliderPrioridad.setMajorTickSpacing(1);
        sliderPrioridad.setPaintLabels(true);
        sliderPrioridad.setPaintTicks(true);
    }
    private final void metodosInicio(){
        v.validarSoloLetras(txtNombre);
        v.validarSoloLetras(txtApellido);
        
        v.validarSoloNumeros(txtCedula);
        v.validarSoloNumeros(txtCelular);
        v.validarSoloNumeros(txtCodigo);
        
        v.limitarCaracteres(txtNombre, 20);
        v.limitarCaracteres(txtApellido, 20);
        v.limitarCaracteres(txtCelular, 10);
        v.limitarCaracteres(txtCedula, 10);
        v.limitarCaracteres(txtCodigo, 11);
        v.limitarCaracteres(txtEdad, 2);
        //comboVereda.setEnabled(false);
    }
    private void limpiar(){
        txtApellido.setText(null);
        txtCedula.setText(null);
        txtCelular.setText(null);
        txtCodigo.setText(null);
        txtEdad.setText(null);
        txtNombre.setText(null);
        ComboEstudios.setSelectedItem(0);
        ComboGenero.setSelectedItem(0);
        ComboProyecto.setSelectedItem(0);
        comboMunicipio.setSelectedItem(0);
        comboSector.setSelectedItem(0);
        comboTierra.setSelectedItem(0);
        comboVereda.setSelectedItem(0);
        sliderPrioridad.setValue(1);
    }
    private void cargaDeCombos(){
        try {
            bd.llenarListaProyectos(ComboProyecto);
            bd.llenarListaMunicipio(comboMunicipio);
            bd.llenarComboEstudios(ComboEstudios);
            bd.llenarComboSector(comboSector);
        } catch (SQLException ex) {
            System.out.println("error al cargar combos "+ex);
        }
    }
    public Image getIconImage(){
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("imagenes/logo_anuc.png"));
        return retValue;
    }
    private void validarIngreso(){
        String nom = txtNombre.getText().trim();
        String ape = txtApellido.getText().trim();
        String ced = txtCedula.getText().trim();
        String cod = txtCodigo.getText().trim();
        String eda = txtEdad.getText().trim();
        int gen = ComboGenero.getSelectedIndex();
        int mun = comboMunicipio.getSelectedIndex();
        if(nom.isEmpty()||ape.isEmpty()||ced.isEmpty()||cod.isEmpty()||eda.isEmpty()||gen==0||mun==0){
            BtnGuardar.setEnabled(false);
            btnVerificar.setEnabled(false);
        }else{
            BtnGuardar.setEnabled(true);
            btnVerificar.setEnabled(true);
        }
    }

    public void CargarFoto(){
        JFileChooser j = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG & PNG", "jpg", "png");//filtra la extencion del archivo
        j.setFileFilter(filtro);
        
        int estado = j.showOpenDialog(null);
        if(estado ==JFileChooser.APPROVE_OPTION){
            try {
                fis= new FileInputStream(j.getSelectedFile());
                this.longitudByte = (int)j.getSelectedFile().length();
                try {
                    lblFoto.setIcon(null);
                    Image icono = ImageIO.read(j.getSelectedFile()).getScaledInstance(lblFoto.getWidth(), 
                            lblFoto.getHeight(), Image.SCALE_DEFAULT);
                    lblFoto.setIcon(new ImageIcon(icono));
                    lblFoto.updateUI();
                    apretafoto = 1;
                } catch (IOException e) {
                    System.out.println("error al cargar foto "+e);
                }
            } catch (FileNotFoundException e) {
                    System.out.println("error al cargar file "+e);
            }
        }
        
    }
    private void Guardar(){
        try {
            String nom = txtNombre.getText();
            String ape = txtApellido.getText();
            String id = txtCedula.getText();
            int cod = Integer.parseInt(txtCodigo.getText());
            String tel = txtCelular.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String gene = ComboGenero.getSelectedItem().toString();
            String estu = ComboEstudios.getSelectedItem().toString();
            String tier = comboTierra.getSelectedItem().toString();
            String sect = comboSector.getSelectedItem().toString();
            int pri = sliderPrioridad.getValue();
            int ver = bd.getIDvereda(comboVereda);
            String desp = comboDesplazado.getSelectedItem().toString();
            bd.insertarPersonaAfiliado(nom, ape, id, cod, tel, edad, gene, estu, tier, sect, pri, fis, longitudByte, ver, desp);
            JOptionPane.showMessageDialog(null, "guardado correctamente");
            if(ComboProyecto.getSelectedIndex()!=0){
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                bd.insertarPersonaProyecto(bd.getIDpersonaConCedula(id), 
                        bd.getIDproyectoConAbreviatura(ComboProyecto.getSelectedItem().toString()), dateFormat.format(date));
            }
            limpiar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al guardar"+ex);
            System.err.println("error "+ex);
        }
    }
        private void Modificar(){
        try {
            String nom = txtNombre.getText();
            String ape = txtApellido.getText();
            String id = txtCedula.getText();
            int cod = Integer.parseInt(txtCodigo.getText());
            String tel = txtCelular.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String gene = ComboGenero.getSelectedItem().toString();
            String estu = ComboEstudios.getSelectedItem().toString();
            String tier = comboTierra.getSelectedItem().toString();
            String sect = comboSector.getSelectedItem().toString();
            int pri = sliderPrioridad.getValue();
            int ver = bd.getIDvereda(comboVereda);
            String desp = comboDesplazado.getSelectedItem().toString();
            bd.modificarPersonaAfiliado(nom, ape, id, cod, tel, edad, gene, estu, tier, sect, pri, fis, longitudByte, ver, desp,bd.getIDpersonaConCedula(id));
            JOptionPane.showMessageDialog(null, "guardado correctamente");
            if(ComboProyecto.getSelectedIndex()!=0){
                Date date = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                bd.insertarPersonaProyecto(bd.getIDpersonaConCedula(id), 
                        bd.getIDproyectoConAbreviatura(ComboProyecto.getSelectedItem().toString()), dateFormat.format(date));
            }
            limpiar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al guardar"+ex);
            System.err.println("error "+ex);
        }
    }
    private void botonVerificar(){
        try {
            if(bd.verificarSiExistePersona(txtCedula.getText())){
                //verificar si se esta en proyectos
                if(bd.verificarSiEstaEnProyecto(""+bd.getIDpersonaConCedula(txtCedula.getText()))){
                    //si esta en proyectos listar los proyectos
                    JOptionPane.showMessageDialog(null, "Se encuentra en:\n"
                            +bd.listarAbreviaturaProyectoSegunIDpersona(bd.getIDpersonaConCedula(txtCedula.getText())).toUpperCase());         
                }else{
                    JOptionPane.showMessageDialog(null, "No se encuentra registrado en algun proyecto");
                }
            }else{
                JOptionPane.showMessageDialog(null, "No se encuentra registrado este numero de cedula:\n "+txtCedula.getText());
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al verificar"+ex);
        }
    }
    
    public void autoIncrementCodigo(){
            try {
            if(jCheckBox1.isSelected()){
                txtCodigo.setText(""+(bd.ultimoCodigoPersona()+1));
            }else{
                txtCodigo.setText(null);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al cargar el ultimo codigo");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblFoto = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCelular = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        ComboGenero = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        ComboEstudios = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        comboTierra = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        comboSector = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        comboMunicipio = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        comboVereda = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        BtnGuardar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnVerificar = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        ComboProyecto = new javax.swing.JComboBox();
        jCheckBox1 = new javax.swing.JCheckBox();
        txtEdad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        sliderPrioridad = new javax.swing.JSlider();
        jLabel8 = new javax.swing.JLabel();
        comboDesplazado = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(getIconImage());
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(153, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblFoto.setBackground(new java.awt.Color(153, 255, 255));
        lblFoto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/persona.png"))); // NOI18N
        lblFoto.setToolTipText("Cargar foto");
        lblFoto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblFoto.setOpaque(true);
        lblFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblFotoMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblFotoMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblFotoMouseEntered(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Nombre*:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Apellido*:");

        txtNombre.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtNombre.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtNombreCaretUpdate(evt);
            }
        });
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        txtApellido.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtApellido.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtApellidoCaretUpdate(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Identificacion*:");

        txtCedula.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtCedula.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCedulaCaretUpdate(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Codigo Anuc:*");

        txtCodigo.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtCodigo.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCodigoCaretUpdate(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Celular:");

        txtCelular.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Genero*:");

        ComboGenero.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--selecionar--", "M", "F"}));
        ComboGenero.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ComboGenero.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboGeneroItemStateChanged(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Estudios:");

        ComboEstudios.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--selecionar--"}));
        ComboEstudios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Tierra:");

        comboTierra.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--selecionar--", "SI", "NO"}));
        comboTierra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Sector Productivo:");

        comboSector.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--selecionar--"}));
        comboSector.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Municipio*:");

        comboMunicipio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--selecionar--" }));
        comboMunicipio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboMunicipio.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboMunicipioItemStateChanged(evt);
            }
        });
        comboMunicipio.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                comboMunicipioPropertyChange(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Vereda*:");

        comboVereda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--selecionar--" }));
        comboVereda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboVereda.setEnabled(false);
        comboVereda.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboVeredaItemStateChanged(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Edad:");

        BtnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        BtnGuardar.setText("Guardar");
        BtnGuardar.setEnabled(false);
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/atras.png"))); // NOI18N
        jButton2.setText("Atras");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnVerificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/verificar.png"))); // NOI18N
        btnVerificar.setText("Verificar");
        btnVerificar.setEnabled(false);
        btnVerificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerificarActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        jButton4.setText("Eliminar");
        jButton4.setEnabled(false);

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Proyecto:");

        ComboProyecto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--selecionar--" }));

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Auto");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        txtEdad.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtEdadCaretUpdate(evt);
            }
        });

        jLabel6.setText("Prioridad:");

        sliderPrioridad.setOrientation(javax.swing.JSlider.VERTICAL);

        jLabel8.setText("Desplazado:");

        comboDesplazado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--selecionar--", "SI", "NO"}));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVerificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ComboEstudios, 0, 140, Short.MAX_VALUE)
                            .addComponent(comboSector, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sliderPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboDesplazado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel14)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ComboProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox1))
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(comboTierra, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboVereda, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboMunicipio, javax.swing.GroupLayout.Alignment.LEADING, 0, 140, Short.MAX_VALUE))
                    .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(44, 44, 44)
                                        .addComponent(jLabel1)
                                        .addGap(25, 25, 25)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel3))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                            .addComponent(jLabel5)
                                            .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                            .addComponent(jLabel7)
                                            .addComponent(ComboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(42, 42, 42)
                                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                                    .addComponent(jLabel2)
                                                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(21, 21, 21)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                                    .addComponent(jLabel4)
                                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jCheckBox1))
                                                .addGap(46, 46, 46)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel14)
                                                    .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(comboTierra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addGap(19, 19, 19)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(comboMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jLabel9))
                                    .addComponent(ComboEstudios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(comboSector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(comboVereda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(ComboProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(comboDesplazado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sliderPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(BtnGuardar)
                    .addComponent(btnVerificar)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        try {
            if(bd.verificarSiExistePersona(txtCedula.getText())){
                //verificar si se esta en proyectos
                if(bd.verificarSiEstaEnProyecto(""+bd.getIDpersonaConCedula(txtCedula.getText()))){
                    //si esta en proyectos entonces debolistar los proyectos
                    int resp = JOptionPane.showConfirmDialog(null, "Se encuentra en:\n"
                            +bd.listarAbreviaturaProyectoSegunIDpersona(bd.getIDpersonaConCedula(txtCedula.getText())).toUpperCase()+
                            " \ndeseas afiliarlo?", 
                            "Seleciona una opcion!", JOptionPane.YES_NO_OPTION);   
                    if(resp == 0){
                        //modifica el q ya esta
                        Modificar();
                    }else{
                        limpiar();
                    }        
                }else{
                    JOptionPane.showMessageDialog(null, "grave error no deberia de entrar en este if xq para exister debe estar en un proyecto");
                }
            }else{
                Guardar();
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al verificar"+ex);
        }            
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //new GUI_Afiliado().setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void lblFotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFotoMouseClicked
        CargarFoto();
    }//GEN-LAST:event_lblFotoMouseClicked

    private void txtNombreCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtNombreCaretUpdate
        validarIngreso();
    }//GEN-LAST:event_txtNombreCaretUpdate

    private void txtApellidoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtApellidoCaretUpdate
        validarIngreso();
    }//GEN-LAST:event_txtApellidoCaretUpdate

    private void txtCedulaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCedulaCaretUpdate
        validarIngreso();
        String c = txtCedula.getText().trim();
        if(c.isEmpty())
            btnVerificar.setEnabled(false);
        else
            btnVerificar.setEnabled(true);
    }//GEN-LAST:event_txtCedulaCaretUpdate

    private void comboMunicipioPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_comboMunicipioPropertyChange
        
    }//GEN-LAST:event_comboMunicipioPropertyChange

    private void comboMunicipioItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboMunicipioItemStateChanged
        if(comboMunicipio.getSelectedIndex()==0){
            comboVereda.setEnabled(false);
        }else{
            comboVereda.setEnabled(true);
        }
        bd.llenarComboVeredaSegunMunicipio(comboVereda, comboMunicipio.getSelectedItem().toString());
    }//GEN-LAST:event_comboMunicipioItemStateChanged

    private void lblFotoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFotoMouseEntered
        lblFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 1));
    }//GEN-LAST:event_lblFotoMouseEntered

    private void lblFotoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblFotoMouseExited
        lblFoto.setBorder(null);
    }//GEN-LAST:event_lblFotoMouseExited

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        autoIncrementCodigo();
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void txtCodigoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCodigoCaretUpdate
        validarIngreso();
    }//GEN-LAST:event_txtCodigoCaretUpdate

    private void txtEdadCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtEdadCaretUpdate
        validarIngreso();
    }//GEN-LAST:event_txtEdadCaretUpdate

    private void ComboGeneroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboGeneroItemStateChanged
        validarIngreso();
    }//GEN-LAST:event_ComboGeneroItemStateChanged

    private void comboVeredaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboVeredaItemStateChanged
        validarIngreso();
    }//GEN-LAST:event_comboVeredaItemStateChanged

    private void btnVerificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerificarActionPerformed
        botonVerificar();
    }//GEN-LAST:event_btnVerificarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IU_persona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IU_persona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IU_persona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IU_persona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IU_persona().setVisible(false);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JComboBox ComboEstudios;
    private javax.swing.JComboBox ComboGenero;
    private javax.swing.JComboBox ComboProyecto;
    private javax.swing.JButton btnVerificar;
    private javax.swing.JComboBox comboDesplazado;
    private javax.swing.JComboBox comboMunicipio;
    private javax.swing.JComboBox comboSector;
    private javax.swing.JComboBox comboTierra;
    private javax.swing.JComboBox comboVereda;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JSlider sliderPrioridad;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCelular;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
