package tiendatorresi;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class BuscarProveedores extends javax.swing.JFrame {

 public static String url= "jdbc:mysql://localhost/tiendatorresi";
    public static Connection conexion;
    public static Statement sentencia;
    public static String usuario="root";
    public static String cont="root";
    public static ResultSet ResProv;
    public static int [] prov_pr = new int [40];
    public static ResultSet r_pr;
    public TableRowSorter trs;
    
    public BuscarProveedores() {
        initComponents();
        PrepararBaseDeDatos();
        CargarCombo();
        CargarConsultaBP();
        CargarDatosBP();
    }
    
    public static void PrepararBaseDeDatos(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, cont);
            if (conexion!=null){
                System.out.println("Conexion Exitosa");}
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);}
        try{
            sentencia=conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error de sentencia");}
        }
    
    public static void CargarCombo(){
        try{
            int i=1;
            cboLocalidadBP.removeAllItems();
            prov_pr[0]= 0;
            cboLocalidadBP.addItem("Todas");
            ResProv = sentencia.executeQuery("SELECT * FROM tiendatorresi.localidad ORDER BY tiendatorresi.localidad.nombre_localidad");
            ResProv.beforeFirst();
            while(ResProv.next()){
                cboLocalidadBP.addItem(ResProv.getString("nombre_localidad"));
                prov_pr[i]= ResProv.getInt("id_localidad");
                i++;
            }
        }catch(Exception e){
               System.out.println("Error cargar combo BP: "+e);
        }
    }
    
    public void CargarConsultaBP(){
         try{
             r_pr= sentencia.executeQuery("SELECT id_proveedor, nombre_proveedor, direccion_proveedor, cuit_proveedor, email_proveedor, celular_proveedor, id_localidad FROM localidad, proveedor WHERE localidad.id_localidad = proveedor.localidad_id_localidad ORDER BY id_proveedor");
                     
         }catch(Exception e){
             System.out.println("Error CargarConsulta BP: " + e);
         }   
     }
    
    public void CargarDatosBP(){
        DefaultTableModel table =(DefaultTableModel) tblProveedoresBP.getModel();
        int i=0 , a=0;
        a = table.getRowCount();
        for(i=1; i<=a; i++){
            table.removeRow(0);
        }
        try{
            int f = 0;
            r_pr.beforeFirst();
            while(r_pr.next()){
                table.addRow(new Object []{"","","","",""});
                table.setValueAt(r_pr.getString("id_proveedor"),f,0);
                table.setValueAt(r_pr.getString("nombre_proveedor"), f, 1);
                table.setValueAt(r_pr.getString("direccion_proveedor"), f, 2);
                table.setValueAt(r_pr.getString("cuit_proveedor"), f, 3);
                table.setValueAt(r_pr.getString("email_proveedor"), f, 4);
                table.setValueAt(r_pr.getString("celular_proveedor"), f, 5);
                table.setValueAt(r_pr.getString("id_localidad"), f, 6);
                f++;
            }  
        }catch(Exception e){
            System.out.println("Error CargarDatos BP: " + e);
        }
     }
    
  private void CargarConsultaCategoriaBP(){
        try{
            DefaultTableModel table = (DefaultTableModel) tblProveedoresBP.getModel();
            int i=1, a=0;
            a = table.getRowCount();
            for(int q=1; q<=a; q++){
                table.removeRow(0);
            }
            if(cboLocalidadBP.getSelectedIndex()==0){
                CargarConsultaBP();
            } else{
                while(i!= cboLocalidadBP.getSelectedIndex()){
                    i++;
                }
                r_pr= sentencia.executeQuery("SELECT id_proveedor, nombre_proveedor, direccion_proveedor, cuit_proveedor, email_proveedor, celular_proveedor, id_localidad FROM tiendatorresi.localidad, tiendatorresi.proveedor WHERE proveedor.localidad_id_localidad = localidad.id_localidad AND proveedor.localidad_id_localidad = "+prov_pr[i]+" ORDER BY proveedor.id_proveedor");
                
            }
            CargarDatosBP();
        }catch(Exception e){
            System.out.println("Error CargarConsultaCategoria: " + e);
        }
    }  
    
/*    CODIGO DE LA TABLA (click derecho - custom code)
tblLocalidad = new javax.swing.JTable(){

    public boolean isCellEditable(int row, int column){
        for(int i = 0; i < tblLocalidad.getRowCount();i++){
            if(row==i){
                return false;
            }
        }
        return true;
    }
};   
*/    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBuscarProveedor = new javax.swing.JPanel();
        lblNombreBP = new javax.swing.JLabel();
        lblLocalidadBP = new javax.swing.JLabel();
        txtNombreBP = new javax.swing.JTextPane();
        cboLocalidadBP = new javax.swing.JComboBox();
        btnSeleccionarBP = new javax.swing.JButton();
        spnlBuscarProveedores = new javax.swing.JScrollPane();
        tblProveedoresBP = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        pnlBuscarProveedor.setBackground(new java.awt.Color(0, 51, 204));

        lblNombreBP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblNombreBP.setForeground(new java.awt.Color(255, 204, 25));
        lblNombreBP.setText("Nombre");

        lblLocalidadBP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblLocalidadBP.setForeground(new java.awt.Color(255, 204, 25));
        lblLocalidadBP.setText("Localidad");

        txtNombreBP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtNombreBP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreBPKeyTyped(evt);
            }
        });

        cboLocalidadBP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cboLocalidadBP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLocalidadBPActionPerformed(evt);
            }
        });

        btnSeleccionarBP.setBackground(new java.awt.Color(255, 204, 25));
        btnSeleccionarBP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnSeleccionarBP.setForeground(new java.awt.Color(0, 51, 204));
        btnSeleccionarBP.setText("Seleccionar");
        btnSeleccionarBP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarBPActionPerformed(evt);
            }
        });

        tblProveedoresBP = new javax.swing.JTable(){

            public boolean isCellEditable(int row, int column){
                for(int i = 0; i < tblProveedoresBP.getRowCount();i++){
                    if(row==i){
                        return false;
                    }
                }
                return true;
            }
        };
        tblProveedoresBP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblProveedoresBP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Direccion", "Cuit", "Email", "Celular", "ID Localidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProveedoresBP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProveedoresBPMouseClicked(evt);
            }
        });
        spnlBuscarProveedores.setViewportView(tblProveedoresBP);
        if (tblProveedoresBP.getColumnModel().getColumnCount() > 0) {
            tblProveedoresBP.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblProveedoresBP.getColumnModel().getColumn(0).setMaxWidth(50);
            tblProveedoresBP.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblProveedoresBP.getColumnModel().getColumn(1).setMaxWidth(200);
            tblProveedoresBP.getColumnModel().getColumn(2).setPreferredWidth(0);
            tblProveedoresBP.getColumnModel().getColumn(2).setMaxWidth(125);
            tblProveedoresBP.getColumnModel().getColumn(3).setPreferredWidth(0);
            tblProveedoresBP.getColumnModel().getColumn(3).setMaxWidth(100);
            tblProveedoresBP.getColumnModel().getColumn(4).setPreferredWidth(0);
            tblProveedoresBP.getColumnModel().getColumn(4).setMaxWidth(125);
            tblProveedoresBP.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblProveedoresBP.getColumnModel().getColumn(5).setMaxWidth(100);
            tblProveedoresBP.getColumnModel().getColumn(6).setMinWidth(0);
            tblProveedoresBP.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblProveedoresBP.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        javax.swing.GroupLayout pnlBuscarProveedorLayout = new javax.swing.GroupLayout(pnlBuscarProveedor);
        pnlBuscarProveedor.setLayout(pnlBuscarProveedorLayout);
        pnlBuscarProveedorLayout.setHorizontalGroup(
            pnlBuscarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarProveedorLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pnlBuscarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spnlBuscarProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlBuscarProveedorLayout.createSequentialGroup()
                        .addGroup(pnlBuscarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblLocalidadBP)
                            .addComponent(lblNombreBP))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlBuscarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboLocalidadBP, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreBP, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(62, 62, 62)
                        .addComponent(btnSeleccionarBP)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        pnlBuscarProveedorLayout.setVerticalGroup(
            pnlBuscarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarProveedorLayout.createSequentialGroup()
                .addGroup(pnlBuscarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBuscarProveedorLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlBuscarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreBP)
                            .addComponent(txtNombreBP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlBuscarProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLocalidadBP)
                            .addComponent(cboLocalidadBP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlBuscarProveedorLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(btnSeleccionarBP)))
                .addGap(18, 18, 18)
                .addComponent(spnlBuscarProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBuscarProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBuscarProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreBPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreBPKeyTyped
txtNombreBP.addKeyListener(new KeyAdapter (){
          @Override
    public void keyReleased (KeyEvent Ke){
        trs.setRowFilter(RowFilter.regexFilter("(?i)"+ txtNombreBP.getText(),1));
    }
    });
        trs=new TableRowSorter(tblProveedoresBP.getModel());
         tblProveedoresBP.setRowSorter(trs); 
    }//GEN-LAST:event_txtNombreBPKeyTyped

    private void btnSeleccionarBPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarBPActionPerformed
        try{
            String id =(String) tblProveedoresBP.getValueAt(tblProveedoresBP.getSelectedRow(), 0);
            String nombre =(String) tblProveedoresBP.getValueAt(tblProveedoresBP.getSelectedRow(), 1);
            String direccion =(String) tblProveedoresBP.getValueAt(tblProveedoresBP.getSelectedRow(), 2);
            String cuit =(String) tblProveedoresBP.getValueAt(tblProveedoresBP.getSelectedRow(), 3);
            String email =(String) tblProveedoresBP.getValueAt(tblProveedoresBP.getSelectedRow(), 4);
            String celular =(String) tblProveedoresBP.getValueAt(tblProveedoresBP.getSelectedRow(), 5);
            String localidad =(String) tblProveedoresBP.getValueAt(tblProveedoresBP.getSelectedRow(), 6);
            Inicio.txtIdP.setText(id);
            Inicio.txtNombreP.setText(nombre);
            Inicio.txtDireccionP.setText(direccion);
            Inicio.txtCuitP.setText(cuit);
            Inicio.txtEmailP.setText(email);
            Inicio.txtCelularP.setText(celular);
            int i= Integer.parseInt(localidad);
            int aa= 0;
            while(i!= Inicio.prov_ar[aa]){
                aa++;
            }
            Inicio.cboLocalidadP.setSelectedIndex(aa);
            Inicio.BuscarUnRegistroProveedores(id);
            this.setVisible(false);
        }catch (Exception e){
            System.out.println(e);
            }
            this.dispose();
    }//GEN-LAST:event_btnSeleccionarBPActionPerformed

    private void cboLocalidadBPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLocalidadBPActionPerformed
        CargarConsultaCategoriaBP();
    }//GEN-LAST:event_cboLocalidadBPActionPerformed

    private void tblProveedoresBPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProveedoresBPMouseClicked
        try{
            String id =(String) tblProveedoresBP.getValueAt(tblProveedoresBP.getSelectedRow(), 0);
            String nombre =(String) tblProveedoresBP.getValueAt(tblProveedoresBP.getSelectedRow(), 1);
            String direccion =(String) tblProveedoresBP.getValueAt(tblProveedoresBP.getSelectedRow(), 2);
            String cuit =(String) tblProveedoresBP.getValueAt(tblProveedoresBP.getSelectedRow(), 3);
            String email =(String) tblProveedoresBP.getValueAt(tblProveedoresBP.getSelectedRow(), 4);
            String celular =(String) tblProveedoresBP.getValueAt(tblProveedoresBP.getSelectedRow(), 5);
            String localidad =(String) tblProveedoresBP.getValueAt(tblProveedoresBP.getSelectedRow(), 6);
            Inicio.txtIdP.setText(id);
            Inicio.txtNombreP.setText(nombre);
            Inicio.txtDireccionP.setText(direccion);
            Inicio.txtCuitP.setText(cuit);
            Inicio.txtEmailP.setText(email);
            Inicio.txtCelularP.setText(celular);
            int i= Integer.parseInt(localidad);
            int aa= 0;
            while(i!= Inicio.prov_ar[aa]){
                aa++;
            }
            Inicio.cboLocalidadP.setSelectedIndex(aa);
            Inicio.BuscarUnRegistroProveedores(id);
            this.setVisible(false);
        }catch (Exception e){
            System.out.println(e);
            }
            this.dispose();
    }//GEN-LAST:event_tblProveedoresBPMouseClicked


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuscarProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscarProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscarProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscarProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuscarProveedores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnSeleccionarBP;
    public static javax.swing.JComboBox cboLocalidadBP;
    private javax.swing.JLabel lblLocalidadBP;
    private javax.swing.JLabel lblNombreBP;
    private javax.swing.JPanel pnlBuscarProveedor;
    private javax.swing.JScrollPane spnlBuscarProveedores;
    public static javax.swing.JTable tblProveedoresBP;
    public static javax.swing.JTextPane txtNombreBP;
    // End of variables declaration//GEN-END:variables
}
