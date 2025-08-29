package tiendatorresi;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class BuscarRubros extends javax.swing.JFrame {

 public static String url= "jdbc:mysql://localhost/tiendatorresi";
    public static Connection conexion;
    public static Statement sentencia;
    public static String usuario="root";
    public static String cont="root";
    public static ResultSet ResProv;
    public static int [] prov_ru = new int [40];
    public static ResultSet r_ru;
    public TableRowSorter trs;
    
    public BuscarRubros() {
        initComponents();
        PrepararBaseDeDatos();
        CargarConsultaBR();
        CargarDatosBR();
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
    
    
    public void CargarConsultaBR(){
         try{
             r_ru= sentencia.executeQuery("SELECT id_rubro, nombre_rubro FROM tiendatorresi.rubro ORDER BY id_rubro");
                     
         }catch(Exception e){
             System.out.println("Error CargarConsulta" + e);
         }   
     }
    
    public void CargarDatosBR(){
        DefaultTableModel table =(DefaultTableModel) tblRubrosBR.getModel();
        int i=0 , a=0;
        a = table.getRowCount();
        for(i=1; i<=a; i++){
            table.removeRow(0);
        }
        try{
            int f = 0;
            r_ru.beforeFirst();
            while(r_ru.next()){
                table.addRow(new Object []{"","","","",""});
                table.setValueAt(r_ru.getString("id_rubro"),f,0);
                table.setValueAt(r_ru.getString("nombre_rubro"), f, 1);
                f++;
            }  
        }catch(Exception e){
            System.out.println("Error CargarDatos BR: " + e);
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

        pnlBuscarRubros = new javax.swing.JPanel();
        lblNombreBR = new javax.swing.JLabel();
        txtNombreBR = new javax.swing.JTextPane();
        btnSeleccionarBR = new javax.swing.JButton();
        spnlBuscarProveedores = new javax.swing.JScrollPane();
        tblRubrosBR = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        pnlBuscarRubros.setBackground(new java.awt.Color(0, 51, 204));

        lblNombreBR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblNombreBR.setForeground(new java.awt.Color(255, 204, 25));
        lblNombreBR.setText("Nombre");

        txtNombreBR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtNombreBR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreBRKeyTyped(evt);
            }
        });

        btnSeleccionarBR.setBackground(new java.awt.Color(255, 204, 25));
        btnSeleccionarBR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnSeleccionarBR.setForeground(new java.awt.Color(0, 51, 204));
        btnSeleccionarBR.setText("Seleccionar");
        btnSeleccionarBR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarBRActionPerformed(evt);
            }
        });

        tblRubrosBR = new javax.swing.JTable(){

            public boolean isCellEditable(int row, int column){
                for(int i = 0; i < tblRubrosBR.getRowCount();i++){
                    if(row==i){
                        return false;
                    }
                }
                return true;
            }
        };
        tblRubrosBR.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblRubrosBR.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRubrosBR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblRubrosBRMouseClicked(evt);
            }
        });
        spnlBuscarProveedores.setViewportView(tblRubrosBR);

        javax.swing.GroupLayout pnlBuscarRubrosLayout = new javax.swing.GroupLayout(pnlBuscarRubros);
        pnlBuscarRubros.setLayout(pnlBuscarRubrosLayout);
        pnlBuscarRubrosLayout.setHorizontalGroup(
            pnlBuscarRubrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarRubrosLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pnlBuscarRubrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spnlBuscarProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlBuscarRubrosLayout.createSequentialGroup()
                        .addComponent(lblNombreBR)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNombreBR, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(btnSeleccionarBR)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        pnlBuscarRubrosLayout.setVerticalGroup(
            pnlBuscarRubrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBuscarRubrosLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(pnlBuscarRubrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBuscarRubrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSeleccionarBR)
                        .addComponent(lblNombreBR))
                    .addComponent(txtNombreBR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(spnlBuscarProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBuscarRubros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlBuscarRubros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreBRKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreBRKeyTyped
txtNombreBR.addKeyListener(new KeyAdapter (){
          @Override
    public void keyReleased (KeyEvent Ke){
        trs.setRowFilter(RowFilter.regexFilter("(?i)"+ txtNombreBR.getText(),1));
    }
    });
        trs=new TableRowSorter(tblRubrosBR.getModel());
         tblRubrosBR.setRowSorter(trs); 
    }//GEN-LAST:event_txtNombreBRKeyTyped

    private void btnSeleccionarBRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarBRActionPerformed
        try{
            String id =(String) tblRubrosBR.getValueAt(tblRubrosBR.getSelectedRow(), 0);
            String nombre =(String) tblRubrosBR.getValueAt(tblRubrosBR.getSelectedRow(), 1);
            Inicio.BuscarUnRegistroRubros(id);
            Inicio.txtIdR.setText(id);
            Inicio.txtNombreR.setText(nombre);
            this.setVisible(false);
        }catch (Exception e){
            }
            this.dispose();
    }//GEN-LAST:event_btnSeleccionarBRActionPerformed

    private void tblRubrosBRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblRubrosBRMouseClicked
        try{
            String id =(String) tblRubrosBR.getValueAt(tblRubrosBR.getSelectedRow(), 0);
            String nombre =(String) tblRubrosBR.getValueAt(tblRubrosBR.getSelectedRow(), 1);
            Inicio.BuscarUnRegistroRubros(id);
            Inicio.txtIdR.setText(id);
            Inicio.txtNombreR.setText(nombre);
            this.setVisible(false);
        }catch (Exception e){
            }
            this.dispose();
    }//GEN-LAST:event_tblRubrosBRMouseClicked


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

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
    public static javax.swing.JButton btnSeleccionarBR;
    private javax.swing.JLabel lblNombreBR;
    private javax.swing.JPanel pnlBuscarRubros;
    private javax.swing.JScrollPane spnlBuscarProveedores;
    public static javax.swing.JTable tblRubrosBR;
    public static javax.swing.JTextPane txtNombreBR;
    // End of variables declaration//GEN-END:variables
}
