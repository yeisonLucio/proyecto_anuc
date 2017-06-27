package vista;

import control.Validaciones;
import datos.ConexionBD;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;


/**
 *
 * @author stivenSP
 */
public class IU_proyecto extends javax.swing.JFrame {

    Validaciones v = new Validaciones();
    ConexionBD bd = new ConexionBD();
    boolean consultar = false;
    int codigoProyecto;
    
    IU_principal prin = new IU_principal();
    
    public IU_proyecto() {
        initComponents();
        this.setLocationRelativeTo(null);
        metodosDeInicio();
        cargarDatosTabla();
        jTabbedPane1.setEnabledAt(1, false);
    }
    
    public void metodosDeInicio(){
        v.validarSoloNumeros(txtAporte);
        v.validarSoloNumeros(txtPresupuesto);
        v.validarSoloNumeros(txtCantidad);
        
        v.limitarCaracteres(areaNombre, 500);
        v.limitarCaracteres(areaObservaciones, 500);
        v.limitarCaracteres(txtAbreviatura, 30);
        v.limitarCaracteres(txtCantidad, 11);
        v.limitarCaracteres(txtPresupuesto, 11);
        v.limitarCaracteres(txtAporte, 11);
    }
    public void validarIngreso(){
        String nom = areaNombre.getText().trim();
        String abr = txtAbreviatura.getText().trim();
        String can = txtCantidad.getText().trim();
        String pre = txtPresupuesto.getText().trim();
        String apo = txtAporte.getText().trim();
        Date fec = JDateFecha.getDate();
        int selec = jComboBox1.getSelectedIndex();
        
        if(nom.isEmpty()||abr.isEmpty()||can.isEmpty()||pre.isEmpty()||apo.isEmpty()||fec==null||selec==0){
            btnGuardar.setEnabled(false);
        }else{
            btnGuardar.setEnabled(true);
        }
    }
    public void cargarDatosTabla(){
        bd.llenarTablaProyecto(TablaDatos);
    }
    public void limpiarCampos(){
        areaNombre.setText(null);
        txtAbreviatura.setText(null);
        txtCantidad.setText(null);
        txtPresupuesto.setText(null);
        txtAporte.setText(null);
        areaObservaciones.setText(null);
        JDateFecha.setDate(null);
        jComboBox1.setSelectedIndex(0);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaDatos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnAtrass = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaNombre = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        txtAbreviatura = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPresupuesto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtAporte = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        areaObservaciones = new javax.swing.JTextArea();
        btnAtras = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        JDateFecha = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        TablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Cantidad Beneficiarios", "Presupuesto", "Aporte", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaDatosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaDatos);
        if (TablaDatos.getColumnModel().getColumnCount() > 0) {
            TablaDatos.getColumnModel().getColumn(0).setResizable(false);
            TablaDatos.getColumnModel().getColumn(1).setResizable(false);
            TablaDatos.getColumnModel().getColumn(2).setResizable(false);
            TablaDatos.getColumnModel().getColumn(3).setResizable(false);
            TablaDatos.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel1.setText("Buscar");

        txtBuscar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtBuscarCaretUpdate(evt);
            }
        });

        btnAtrass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/atras.png"))); // NOI18N
        btnAtrass.setText("Atras");
        btnAtrass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrassActionPerformed(evt);
            }
        });

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(27, 27, 27)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAtrass)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNuevo)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtrass)
                    .addComponent(btnNuevo))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Proyectos", jPanel1);

        jLabel2.setText("Nombre");

        areaNombre.setColumns(20);
        areaNombre.setRows(5);
        areaNombre.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                areaNombreCaretUpdate(evt);
            }
        });
        jScrollPane2.setViewportView(areaNombre);

        jLabel3.setText("Abreviatura");

        txtAbreviatura.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtAbreviaturaCaretUpdate(evt);
            }
        });
        txtAbreviatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAbreviaturaActionPerformed(evt);
            }
        });

        jLabel4.setText("Cantidad beneficiarios");

        txtCantidad.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCantidadCaretUpdate(evt);
            }
        });

        jLabel5.setText("Presupuesto");

        txtPresupuesto.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtPresupuestoCaretUpdate(evt);
            }
        });

        jLabel6.setText("Aporte");

        txtAporte.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtAporteCaretUpdate(evt);
            }
        });

        jLabel7.setText("Fecha");

        jLabel8.setText("Observaciones");

        areaObservaciones.setColumns(20);
        areaObservaciones.setRows(5);
        jScrollPane3.setViewportView(areaObservaciones);

        btnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/atras.png"))); // NOI18N
        btnAtras.setText("Atras");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/eliminar.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel9.setText("Estado");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--selecionar--", "EN EJECUCION", "CULMINADO", "EN ESPERA" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jComboBox1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBox1PropertyChange(evt);
            }
        });

        JDateFecha.setDateFormatString("yyyy//MM/d");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtAbreviatura, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtAporte)
                                    .addComponent(txtCantidad)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(JDateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAtras)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(txtAbreviatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(JDateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtPresupuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtAporte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAtras)
                    .addComponent(btnEliminar)
                    .addComponent(btnGuardar))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Nuevo", jPanel2);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAbreviaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAbreviaturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAbreviaturaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            if(consultar==false){
                //GUARDAR
                //captura datos
                String nombre = areaNombre.getText();
                String abreviatura = txtAbreviatura.getText();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                int presupuesto = Integer.parseInt(txtPresupuesto.getText());
                int aporte = Integer.parseInt(txtAporte.getText());
                String obsevaciones = areaObservaciones.getText();
                String estado = jComboBox1.getSelectedItem().toString();
                //captura de fecha 
                //String formato = JDateFecha.getDateFormatString();
                Date date = JDateFecha.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/d");
                
                //ingreso a BD                
                bd.insertarProyecto(nombre, estado, String.valueOf(sdf.format(date)), cantidad, presupuesto, aporte, obsevaciones, abreviatura);
                //Mensaje de confirmacion
                JOptionPane.showMessageDialog(null, "Guardado correctamente");
                //actualizar tabla proyectos
                bd.llenarTablaProyecto(TablaDatos);
                //actualizar panel de proyectos en la principal
                limpiarCampos();
                
                //FALTA ACTUALIZAR MENU EN LA PRINCIPAL
                
            }else{
                //MODIFICAR
                //captura datos
                String nombre = areaNombre.getText();
                String abreviatura = txtAbreviatura.getText();
                int cantidad = Integer.parseInt(txtCantidad.getText());
                int presupuesto = Integer.parseInt(txtPresupuesto.getText());
                int aporte = Integer.parseInt(txtAporte.getText());
                String obsevaciones = areaObservaciones.getText();
                String estado = jComboBox1.getSelectedItem().toString();
                //captura de fecha 
                //String formato = JDateFecha.getDateFormatString();
                Date date = JDateFecha.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/d");
                
                //modifica en BD
                bd.modificarProyecto(codigoProyecto, nombre, estado, String.valueOf(sdf.format(date)), cantidad, presupuesto, aporte, obsevaciones, abreviatura);
                //Mensaje de confirmacion
                JOptionPane.showMessageDialog(null, "Modificado correctamente");
                //actualizar tabla proyectos
                bd.llenarTablaProyecto(TablaDatos);
                //actualizar panel de proyectos en la principal
                limpiarCampos();
                //cambia d pestaña y coloca la otra en false
                jTabbedPane1.setSelectedIndex(0);
                jTabbedPane1.setEnabledAt(1, false);
                //FALTA ACTUALIZAR MENU EN LA PRINCIPAL
            }
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al Guardar"+e);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        jTabbedPane1.setSelectedIndex(0);
        jTabbedPane1.setEnabledAt(1, false);
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void btnAtrassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrassActionPerformed
        dispose();
    }//GEN-LAST:event_btnAtrassActionPerformed

    private void areaNombreCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_areaNombreCaretUpdate
        validarIngreso();
    }//GEN-LAST:event_areaNombreCaretUpdate

    private void txtAbreviaturaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtAbreviaturaCaretUpdate
        validarIngreso();
    }//GEN-LAST:event_txtAbreviaturaCaretUpdate

    private void txtCantidadCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCantidadCaretUpdate
        validarIngreso();
    }//GEN-LAST:event_txtCantidadCaretUpdate

    private void txtPresupuestoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPresupuestoCaretUpdate
        validarIngreso();
    }//GEN-LAST:event_txtPresupuestoCaretUpdate

    private void txtAporteCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtAporteCaretUpdate
        validarIngreso();
    }//GEN-LAST:event_txtAporteCaretUpdate

    private void TablaDatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaDatosMouseClicked
        codigoProyecto = Integer.parseInt(TablaDatos.getValueAt(TablaDatos.getSelectedRow(), 0).toString());
        bd.CargarDatosDEProyecto(codigoProyecto, areaNombre, txtAbreviatura, txtCantidad, txtPresupuesto, txtAporte, jComboBox1, JDateFecha, areaObservaciones);
        //este me cuenta los click si son dos cambia d pestaña
        if(evt.getClickCount()==2){
            jTabbedPane1.setSelectedIndex(1);
            jTabbedPane1.setEnabledAt(1, true);
            btnGuardar.setEnabled(true);
            btnEliminar.setEnabled(true);
            codigoProyecto = Integer.parseInt(TablaDatos.getValueAt(TablaDatos.getSelectedRow(), 0).toString());
            consultar = true;//este me indica q voy a modificar
        }
            
    }//GEN-LAST:event_TablaDatosMouseClicked

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        limpiarCampos();
        //este me habilita la otra pestaña
        jTabbedPane1.setEnabledAt(1, true);
        //este me cambia a la otra pestaña
        jTabbedPane1.setSelectedIndex(1);
        btnEliminar.setEnabled(false);
        consultar = false;
        
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // ELIMINAR
        int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar este proyecto?", "Seleciona una opcion!", JOptionPane.YES_NO_OPTION);
        if(resp==0){
            try {
            bd.eliminarProyecto(codigoProyecto);
            JOptionPane.showMessageDialog(null, "Eliminado correctamente");
            cargarDatosTabla();
            limpiarCampos();
            btnEliminar.setEnabled(false);
            consultar = false;
            jTabbedPane1.setSelectedIndex(0);
            jTabbedPane1.setEnabledAt(1, false);
            } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al eliminar");
            }    
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jComboBox1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBox1PropertyChange
        
    }//GEN-LAST:event_jComboBox1PropertyChange

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        validarIngreso();
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void txtBuscarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtBuscarCaretUpdate
        bd.BuscarEnTablaProyecto(TablaDatos, txtBuscar.getText());
    }//GEN-LAST:event_txtBuscarCaretUpdate


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
            java.util.logging.Logger.getLogger(IU_proyecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IU_proyecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IU_proyecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IU_proyecto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IU_proyecto().setVisible(false);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser JDateFecha;
    private javax.swing.JTable TablaDatos;
    private javax.swing.JTextArea areaNombre;
    private javax.swing.JTextArea areaObservaciones;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnAtrass;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtAbreviatura;
    private javax.swing.JTextField txtAporte;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtPresupuesto;
    // End of variables declaration//GEN-END:variables
}
