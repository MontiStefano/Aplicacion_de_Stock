package tiendatorresi;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class BuscarArticulos extends javax.swing.JFrame {

 public static String url= "jdbc:mysql://localhost/tiendatorresi";
    public static Connection conexion;
    public static Statement sentencia;
    public static String usuario="root";
    public static String cont="root";
    public static ResultSet ResProv;
    public static int [] prov_ar = new int [400];
    public static ResultSet r_ar;
    public TableRowSorter trs;
    
    public BuscarArticulos() {
        initComponents();
        PrepararBaseDeDatos();
        CargarCombo();
        CargarConsulta();
        CargarDatosBA();
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
            cboRubro.removeAllItems();
            prov_ar[0]= 0;
            cboRubro.addItem("Todos");
            ResProv = sentencia.executeQuery("SELECT * FROM tiendatorresi.rubro ORDER BY tiendatorresi.rubro.id_rubro");
            ResProv.beforeFirst();
            while(ResProv.next()){
                cboRubro.addItem(ResProv.getString("nombre_rubro"));
                prov_ar[i]= ResProv.getInt("id_rubro");
                i++;
            }
        }catch(Exception e){
               System.out.println(e);
        }
    }
    
    public void CargarConsulta(){
         try{
             r_ar = sentencia.executeQuery("SELECT id_articulo, nombre_articulo, codigo_articulo, precio_costo, precio_venta, fecha_actualizacion, id_rubro FROM rubro, articulo WHERE rubro.id_rubro = articulo.rubro_id_rubro ORDER BY id_articulo");
                     
         }catch(Exception e){
             System.out.println("Error CargarConsulta" + e);
         }   
     }
    
    public void CargarDatosBA(){
        DefaultTableModel table =(DefaultTableModel) tblArticulos.getModel();
        int i=0 , a=0;
        a = table.getRowCount();
        for(i=1; i<=a; i++){
            table.removeRow(0);
        }
        try{
            int f = 0;
            r_ar.beforeFirst();
            while(r_ar.next()){
                table.addRow(new Object []{"","","","",""});
                table.setValueAt(r_ar.getString("id_articulo"),f,0);
                table.setValueAt(r_ar.getString("codigo_articulo"), f, 1);
                table.setValueAt(r_ar.getString("nombre_articulo"), f, 2);
                table.setValueAt(r_ar.getString("precio_costo"), f, 3);
                table.setValueAt(r_ar.getString("precio_venta"), f, 4);
                Date b = r_ar.getDate("fecha_actualizacion");
                int año = b.getYear()+1900;
                int mes = b.getMonth()+1;
                int dia = b.getDate();
                String fec = dia + "/" + mes + "/" + año;
                table.setValueAt((fec), f, 5);
                table.setValueAt(r_ar.getString("id_rubro"), f, 6);
                f++;
            }  
        }catch(Exception e){
            System.out.println("Error Cargar Datos BA: " + e);
        }
     }
    
  private void CargarConsultaCategoria(){
        try{
            DefaultTableModel table = (DefaultTableModel) tblArticulos.getModel();
            int i=1, a=0;
            a = table.getRowCount();
            for(int q=1; q<=a; q++){
                table.removeRow(0);
            }
            if(cboRubro.getSelectedIndex()==0){
                CargarConsulta();
            } else{
                while(i!= cboRubro.getSelectedIndex()){
                    i++;
                }
                r_ar = sentencia.executeQuery("SELECT id_articulo, nombre_articulo, codigo_articulo, precio_costo, precio_venta, fecha_actualizacion, id_rubro FROM tiendatorresi.rubro, tiendatorresi.articulo WHERE articulo.rubro_id_rubro = rubro.id_rubro AND articulo.rubro_id_rubro = "+prov_ar[i]+" ORDER BY articulo.id_articulo");
                
            }
            CargarDatosBA();
        }catch(Exception e){
            System.out.println("Error CargarConsultaCategoria" + e);
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

        pnlBuscarArticulos = new javax.swing.JPanel();
        lblNombreBA = new javax.swing.JLabel();
        lblRubroBA = new javax.swing.JLabel();
        txtNombreBA = new javax.swing.JTextPane();
        cboRubro = new javax.swing.JComboBox();
        btnSeleccionarBA = new javax.swing.JButton();
        spnlBuscarArticulos = new javax.swing.JScrollPane();
        tblArticulos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        pnlBuscarArticulos.setBackground(new java.awt.Color(0, 51, 204));

        lblNombreBA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblNombreBA.setForeground(new java.awt.Color(255, 204, 25));
        lblNombreBA.setText("Nombre");

        lblRubroBA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblRubroBA.setForeground(new java.awt.Color(255, 204, 25));
        lblRubroBA.setText("Rubro");

        txtNombreBA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtNombreBA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreBAKeyTyped(evt);
            }
        });

        cboRubro.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cboRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboRubroActionPerformed(evt);
            }
        });

        btnSeleccionarBA.setBackground(new java.awt.Color(255, 204, 25));
        btnSeleccionarBA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnSeleccionarBA.setForeground(new java.awt.Color(0, 51, 204));
        btnSeleccionarBA.setText("Seleccionar");
        btnSeleccionarBA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarBAActionPerformed(evt);
            }
        });

        tblArticulos = new javax.swing.JTable(){

            public boolean isCellEditable(int row, int column){
                for(int i = 0; i < tblArticulos.getRowCount();i++){
                    if(row==i){
                        return false;
                    }
                }
                return true;
            }
        };
        tblArticulos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Codigo", "Nombre", "Costo ($)", "Venta ($)", "Fecha", "ID Rubro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblArticulos.getTableHeader().setReorderingAllowed(false);
        tblArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblArticulosMouseClicked(evt);
            }
        });
        spnlBuscarArticulos.setViewportView(tblArticulos);
        if (tblArticulos.getColumnModel().getColumnCount() > 0) {
            tblArticulos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblArticulos.getColumnModel().getColumn(0).setMaxWidth(50);
            tblArticulos.getColumnModel().getColumn(1).setPreferredWidth(0);
            tblArticulos.getColumnModel().getColumn(1).setMaxWidth(75);
            tblArticulos.getColumnModel().getColumn(2).setPreferredWidth(0);
            tblArticulos.getColumnModel().getColumn(2).setMaxWidth(300);
            tblArticulos.getColumnModel().getColumn(3).setPreferredWidth(0);
            tblArticulos.getColumnModel().getColumn(3).setMaxWidth(75);
            tblArticulos.getColumnModel().getColumn(4).setPreferredWidth(0);
            tblArticulos.getColumnModel().getColumn(4).setMaxWidth(75);
            tblArticulos.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblArticulos.getColumnModel().getColumn(5).setMaxWidth(125);
            tblArticulos.getColumnModel().getColumn(6).setMinWidth(0);
            tblArticulos.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblArticulos.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        javax.swing.GroupLayout pnlBuscarArticulosLayout = new javax.swing.GroupLayout(pnlBuscarArticulos);
        pnlBuscarArticulos.setLayout(pnlBuscarArticulosLayout);
        pnlBuscarArticulosLayout.setHorizontalGroup(
            pnlBuscarArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarArticulosLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pnlBuscarArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spnlBuscarArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlBuscarArticulosLayout.createSequentialGroup()
                        .addGroup(pnlBuscarArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblRubroBA)
                            .addComponent(lblNombreBA))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlBuscarArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreBA, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(62, 62, 62)
                        .addComponent(btnSeleccionarBA)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        pnlBuscarArticulosLayout.setVerticalGroup(
            pnlBuscarArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarArticulosLayout.createSequentialGroup()
                .addGroup(pnlBuscarArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBuscarArticulosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlBuscarArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreBA)
                            .addComponent(txtNombreBA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlBuscarArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRubroBA)
                            .addComponent(cboRubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlBuscarArticulosLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(btnSeleccionarBA)))
                .addGap(18, 18, 18)
                .addComponent(spnlBuscarArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBuscarArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBuscarArticulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreBAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreBAKeyTyped
txtNombreBA.addKeyListener(new KeyAdapter (){
          @Override
    public void keyReleased (KeyEvent Ke){
        trs.setRowFilter(RowFilter.regexFilter("(?i)"+ txtNombreBA.getText(),1));
    }
    });
        trs=new TableRowSorter(tblArticulos.getModel());
         tblArticulos.setRowSorter(trs); 
    }//GEN-LAST:event_txtNombreBAKeyTyped

    private void btnSeleccionarBAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarBAActionPerformed
        try{
            String id =(String) tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 0);
            String codigo =(String) tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 1);
            String nombre =(String) tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 2);
            String precio_costo =(String) tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 3);
            String precio_venta =(String) tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 4);
            String fecha_actualizacion =(String) tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 5);
            String rubro =(String) tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 6);
            Inicio.BuscarUnRegistroArticulos(id);
            Inicio.txtIdA.setText(id);
            Inicio.txtCodigoA.setText(codigo);
            Inicio.txtNombreA.setText(nombre);
            Inicio.txtPrecioCostoA.setText(precio_costo);
            Inicio.txtPrecioVentaA.setText(precio_venta);
            Inicio.txtFechaActualizacionA.setText(fecha_actualizacion);
            int i= Integer.parseInt(rubro);
            int aa= 0;
            while(i!= Inicio.prov_ar[aa]){
                aa++;
            }
            Inicio.cboRubroA.setSelectedIndex(aa);
            this.setVisible(false);
        }catch (Exception e){
            System.out.println(e);
            }
            this.dispose();
    }//GEN-LAST:event_btnSeleccionarBAActionPerformed

    private void cboRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboRubroActionPerformed
        CargarConsultaCategoria();
    }//GEN-LAST:event_cboRubroActionPerformed

    private void tblArticulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblArticulosMouseClicked
        try{
            String id =(String) tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 0);
            String codigo =(String) tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 1);
            String nombre =(String) tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 2);
            String precio_costo =(String) tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 3);
            String precio_venta =(String) tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 4);
            String fecha_actualizacion =(String) tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 5);
            String rubro =(String) tblArticulos.getValueAt(tblArticulos.getSelectedRow(), 6);
            Inicio.txtIdA.setText(id);
            Inicio.txtCodigoA.setText(codigo);
            Inicio.txtNombreA.setText(nombre);
            Inicio.txtPrecioCostoA.setText(precio_costo);
            Inicio.txtPrecioVentaA.setText(precio_venta);
            Inicio.txtFechaActualizacionA.setText(fecha_actualizacion);
            int i= Integer.parseInt(rubro);
            int aa= 0;
            while(i!= Inicio.prov_ar[aa]){
                aa++;
            }
            Inicio.cboRubroA.setSelectedIndex(aa);
            Inicio.BuscarUnRegistroArticulos(id);
            this.setVisible(false);
        }catch (Exception e){
            System.out.println(e);
            }
            this.dispose();
    }//GEN-LAST:event_tblArticulosMouseClicked


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BuscarArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BuscarArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BuscarArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BuscarArticulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BuscarArticulos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnSeleccionarBA;
    public static javax.swing.JComboBox cboRubro;
    private javax.swing.JLabel lblNombreBA;
    private javax.swing.JLabel lblRubroBA;
    private javax.swing.JPanel pnlBuscarArticulos;
    private javax.swing.JScrollPane spnlBuscarArticulos;
    public static javax.swing.JTable tblArticulos;
    public static javax.swing.JTextPane txtNombreBA;
    // End of variables declaration//GEN-END:variables
}
