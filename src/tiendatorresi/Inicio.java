package tiendatorresi;

import java.awt.event.KeyEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;

public class Inicio extends javax.swing.JFrame {

    public static String url= "jdbc:mysql://localhost/tiendatorresi";
    public static Connection conexion;
    public static Statement sentencia_ar;
    public static Statement sentencia_pr;
    public static Statement sentencia_ru;
    public static Statement sentencia_au;
    public static Statement sentencia_au1;    
    public static String usuario="root";
    public static String cont="root";
    public static int [] rub_ar = new int [400];
    public static int [] rub_pr = new int [400];
    //public static int [] rub = new int [400];
    public static int [] prov_ar = new int [400];
    public static int [] prov_pr = new int [400];
    //public static int [] prov = new int [400];
    public static int [] rubpro = new int [40];
    public String estado;
    //public static ResultSet r;
    public static ResultSet r_ar;
    public static ResultSet r_pr;
    public static ResultSet r_ru;
    public static ResultSet ResRubro;
    public static ResultSet ResProveedor;
    public static ResultSet ResRubPro;
    public int provv;
    public int rb;
    public String pc;
    public String pv;
    public String c;
    public String fa;
    public String nom;
    public static ResultSet ResProv;
    public String em;
    public String dir;
    public String cu;
    public String cel;
    public int lo;
    public int pr;
    public String mo;

    public Inicio() {
        initComponents();
        PrepararBaseDeDatos();
        CargarComboRubroArticulos();
        CargarComboLocalidadProveedores();
        CargarConsultaRubros();
        this.setExtendedState(this.MAXIMIZED_BOTH);
        pnlProveedores.setVisible(false);
        pnlRubros.setVisible(false);
        pnlAumentos.setVisible(false);
    }
    
    public static void PrepararBaseDeDatos(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, cont);
            if (conexion!=null){
                System.out.println("Conexion Exitosa");}
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);}
        try{
            sentencia_ar = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            sentencia_pr = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            sentencia_ru = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            sentencia_au = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            sentencia_au1 = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error de sentencia");}
    }
    
    
    //METODOS ARTICULOS ********************************************************************************************************************************************************************************
    
    
    public static void CargarComboRubroArticulos(){
        try{
            int i=0;
            cboRubroA.removeAllItems();
            ResRubro = sentencia_ar.executeQuery("SELECT * FROM tiendatorresi.rubro ORDER BY rubro.id_rubro");
            ResRubro.last();
            if (ResRubro.getRow()==0){
                JOptionPane.showMessageDialog(null, "Debe cargar un rubro");
                //dejo el btnProvincia habilitado
            }
            else{
                ResRubro.beforeFirst();
                while (ResRubro.next()){
                cboRubroA.addItem(ResRubro.getString("nombre_rubro"));
                rub_ar[i] = ResRubro.getInt("id_rubro");
                i++;
                }
                CargarComboProveedorArticulos();
            }   
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void CargarComboProveedorArticulos(){
        try{
            int i=0;
            cboProveedorA.removeAllItems();
            ResProveedor = sentencia_ar.executeQuery("SELECT * FROM tiendatorresi.proveedor ORDER BY nombre_proveedor");
            ResProveedor.last();
            if (ResProveedor.getRow()==0){
                JOptionPane.showMessageDialog(null, "Debe cargar un proveedor");
                //dejo el btnProvincia habilitado
            }
            else{
                ResProveedor.beforeFirst();
                while (ResProveedor.next()){
                cboProveedorA.addItem(ResProveedor.getString("nombre_proveedor"));
                prov_ar[i] = ResProveedor.getInt("id_proveedor");
                i++;
                }
                CargarConsultaArticulos();
                }   
        }
        catch(Exception e){
            System.out.println(e);
                      
        }
    }
    
    public static void CargarConsultaArticulos(){
        try{
            r_ar = sentencia_ar.executeQuery("SELECT articulo.id_articulo, articulo.nombre_articulo, articulo.precio_costo, articulo.precio_venta, articulo.fecha_actualizacion, articulo.rubro_id_rubro, articulo.proveedor_id_proveedor, articulo.codigo_articulo, proveedor.nombre_proveedor, rubro.nombre_rubro FROM tiendatorresi.articulo, tiendatorresi.proveedor, tiendatorresi.rubro WHERE tiendatorresi.articulo.rubro_id_rubro = tiendatorresi.rubro.id_rubro AND tiendatorresi.articulo.proveedor_id_proveedor = tiendatorresi.proveedor.id_proveedor ORDER BY tiendatorresi.articulo.id_articulo");
            r_ar.next();
            if (r_ar.isFirst()){
                CargarDatosArticulos();
            }
            else{
                //txtCodigo.setEnabled(false);
                txtNombreA.setEnabled(false);
                btnAceptarA.setEnabled(false);
                btnCancelarA.setEnabled(false);
                btnPrimeroA.setEnabled(false);
                btnAnteriorA.setEnabled(false);
                btnSiguienteA.setEnabled(false);
                btnUltimoA.setEnabled(false);
                btnAltaA.setEnabled(true);
                btnBajaA.setEnabled(false);
                btnModificarA.setEnabled(false);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    
    public static void CargarDatosArticulos(){
        int i=0 ,j=0 , In=0;
        try{
            txtIdA.setText(r_ar.getString("id_articulo"));
            txtNombreA.setText(r_ar.getString("nombre_articulo"));
            txtPrecioCostoA.setText(r_ar.getString("precio_costo"));
            
            Date a = r_ar.getDate("fecha_actualizacion");
            
            int año = a.getYear()+1900;
            int mes = a.getMonth()+1;
            int dia = a.getDate();
            String fec = dia + "/" + mes + "/" + año;
            txtFechaActualizacionA.setText(fec);
            
            txtPrecioVentaA.setText(r_ar.getString("precio_venta"));
            txtCodigoA.setText(r_ar.getString("codigo_articulo"));
            In = r_ar.getInt ("proveedor_id_proveedor");
            while (In != prov_ar[i]){
                i++;
            }
            cboProveedorA.setSelectedIndex(i);
            In = r_ar.getInt ("rubro_id_rubro");
            while (In != rub_ar[j]){
                j++;
            }
            cboRubroA.setSelectedIndex(j);
        }catch (Exception e){
            System.out.println("Cargar Datos Articulos: " + e);
        }
    }
    
    public static void BuscarUnRegistroArticulos (String id){
        int i=0 ,j=0 , In=0;
        try{
            r_ar = sentencia_ar.executeQuery("SELECT articulo.id_articulo, articulo.nombre_articulo, articulo.precio_costo, articulo.precio_venta, articulo.fecha_actualizacion, articulo.rubro_id_rubro, articulo.proveedor_id_proveedor, articulo.codigo_articulo, proveedor.nombre_proveedor, rubro.nombre_rubro FROM tiendatorresi.articulo, tiendatorresi.proveedor, tiendatorresi.rubro WHERE tiendatorresi.articulo.rubro_id_rubro = tiendatorresi.rubro.id_rubro AND tiendatorresi.articulo.proveedor_id_proveedor = tiendatorresi.proveedor.id_proveedor ORDER BY tiendatorresi.articulo.id_articulo");
            while(r_ar.next()){
                if(r_ar.getString("id_articulo").equals(id)){
                 
                    break;
                }
            }
            In = r_ar.getInt ("proveedor_id_proveedor");
            while (In != prov_ar[i]){
                i++;
            }
            cboProveedorA.setSelectedIndex(i);
            In = r_ar.getInt ("rubro_id_rubro");
            while (In != rub_ar[j]){
                j++;
            }
            cboRubroA.setSelectedIndex(j);
              
            return;
        }
        catch (Exception e){
        System.out.println("error Buscar Un Registro Articulos: "+e);
        }
    }
    
    public static void BuscarCodigoArticulos(String codigo){
        int band=0;
        try{
            r_ar = sentencia_ar.executeQuery("SELECT articulo.id_articulo, articulo.nombre_articulo, articulo.precio_costo, articulo.precio_venta, articulo.fecha_actualizacion, articulo.rubro_id_rubro, articulo.proveedor_id_proveedor, articulo.codigo_articulo, proveedor.nombre_proveedor, rubro.nombre_rubro FROM tiendatorresi.articulo, tiendatorresi.proveedor, tiendatorresi.rubro WHERE tiendatorresi.articulo.rubro_id_rubro = tiendatorresi.rubro.id_rubro AND tiendatorresi.articulo.proveedor_id_proveedor = tiendatorresi.proveedor.id_proveedor ORDER BY tiendatorresi.articulo.id_articulo");
            while(r_ar.next()){
                if(r_ar.getString("codigo_articulo").equals(codigo)){
                    System.out.println(r_ar.getString("codigo_articulo"));
                    CargarDatosArticulos();
                    band=1;
                    break;
                }
            }
            if (band==0){
                JOptionPane.showMessageDialog(null, "El codigo no es valido" );
            }
            //return;
        }
        catch (Exception e){ }
    }

    
    
    
    //METODOS PROVEEDORES ***********************************************************************************************************************************************************************
    
    public static void CargarDatosProveedores(){
        int i=0 , In=0;
        try{
            txtIdP.setText(r_pr.getString("id_proveedor"));
            txtNombreP.setText(r_pr.getString("nombre_proveedor"));
            txtDireccionP.setText(r_pr.getString("direccion_proveedor"));
            txtCuitP.setText(r_pr.getString("cuit_proveedor"));
            txtEmailP.setText(r_pr.getString("email_proveedor"));
            txtCelularP.setText(r_pr.getString("celular_proveedor"));
            In = r_pr.getInt ("localidad_id_localidad");
            while (In != prov_pr[i]){
                i++;
            }
            cboLocalidadP.setSelectedIndex(i);
        }catch (Exception e){
            System.out.println("error Cargar Datos Proveedores: "+e);
        }
    }
    
    public static void BuscarUnRegistroProveedores (String id){
        int i=0 , In=0;
        try{
            r_pr.beforeFirst();
            while(r_pr.next()){
                if(r_pr.getString("id_proveedor").equals(id)){
                    break;
                }
            }
            while (In != prov_pr[i]){
                i++;
            }
            cboLocalidadP.setSelectedIndex(i);
            return;
        }catch (Exception e){
            System.out.println("error Buscar Un Registro Proveedores: "+e);
        }
    }
    
    public static void CargarConsultaProveedores(){
         try{
             r_pr = sentencia_pr.executeQuery("SELECT proveedor.id_proveedor, proveedor.nombre_proveedor, proveedor.cuit_proveedor, proveedor.direccion_proveedor, proveedor.email_proveedor, proveedor.celular_proveedor,proveedor.localidad_id_localidad,localidad.nombre_localidad FROM tiendatorresi.localidad, tiendatorresi.proveedor WHERE tiendatorresi.proveedor.localidad_id_localidad = tiendatorresi.localidad.id_localidad ORDER BY proveedor.id_proveedor");
             r_pr.next();
             if (r_pr.isFirst()){
                 CargarDatosProveedores();
             }else{
                 
                 btnAceptarP.setEnabled(false);
                 btnCancelarP.setEnabled(false);
                 txtIdP.setEditable(false);
                 txtNombreP.setEnabled(false);
                 txtDireccionP.setEnabled(false);
                 cboLocalidadP.setEnabled(false);
                 txtCelularP.setEnabled(false);
                 txtCuitP.setEnabled(false);
                 txtEmailP.setEnabled(false);
                 btnPrimeroP.setEnabled(false);
                 btnAnteriorP.setEnabled(false);
                 btnSiguienteP.setEnabled(false);
                 btnUltimoP.setEnabled(false);
                 btnBuscarP.setEnabled(false);
                 btnAltaP.setEnabled(true);
                 btnEliminarP.setEnabled(false);
                 btnModificarP.setEnabled(false);
                 btnBuscarP.setEnabled(false);
                 txtIdP.setText("");
                 txtNombreP.setText("");
                 txtDireccionP.setText("");
                 cboLocalidadP.setSelectedIndex(1);
                 txtCuitP.setText("");
                 txtEmailP.setText("");
                 txtCelularP.setText("");
             }
         }catch (Exception e){
             System.out.println("Cargar Consulta Proveedores: "+e);
         }
    
    }
    
    public static void CargarComboLocalidadProveedores(){
        try{
            int i=0;
            cboLocalidadP.removeAllItems();
            ResProv = sentencia_pr.executeQuery("SELECT * FROM tiendatorresi.localidad ORDER BY nombre_localidad");
            ResProv.last();
            if (ResProv.getRow()==0){
                JOptionPane.showMessageDialog(null, "Debe cargar una Localidad");
                //dejo el btnProvincia habilitado
        }else{
                ResProv.beforeFirst();
                while (ResProv.next()){
                cboLocalidadP.addItem(ResProv.getString("nombre_localidad"));
                prov_pr[i] = ResProv.getInt("id_localidad");
                i++;
                }
                CargarConsultaProveedores();
                }   
        }catch(Exception e){
               System.out.println("Cargar Combo Localidad Proveedores: "+e);
                      
                }    
    }
    
    
    
    
    //METODOS RUBROS *****************************************************************************************************************************
    
    public static void BuscarUnRegistroRubros (String id){
        try{
            r_ru = sentencia_ru.executeQuery("SELECT * FROM tiendatorresi.rubro ORDER BY id_rubro");
            r_ru.beforeFirst();
            while(r_ru.next()){
                if(r_ru.getString("id_rubro").equals(id)){
                    break;
                }
            }
            return;
        }catch (Exception e){
        }
    }
    
    public static void CargarDatosRubros (){
        try{
            txtIdR.setText(r_ru.getString("id_rubro"));
            txtNombreR.setText(r_ru.getString("nombre_rubro"));
                btnAceptarR.setEnabled(false);
                btnCancelarR.setEnabled(false);
                txtIdR.setEnabled(false);
                txtNombreR.setEnabled(false);
                btnPrimeroR.setEnabled(true);
                btnAnteriorR.setEnabled(true);
                btnSiguienteR.setEnabled(true);
                btnUltimoR.setEnabled(true);
                btnAltaR.setEnabled(true);
                btnBajaR.setEnabled(true);
                btnModificarR.setEnabled(true);
                btnBuscarR.setEnabled(true);
             
             
        }catch (Exception e){
            System.out.println("Cargar Datos Rubros: "+e);
        }
    }
    
    public static void CargarConsultaRubros(){
        try{
            r_ru = sentencia_ru.executeQuery("SELECT * FROM tiendatorresi.rubro ORDER BY id_rubro");
            r_ru.next();
            if (r_ru.isFirst()){
                CargarDatosRubros();
            }else{
                
                btnAceptarR.setEnabled(false);
                btnCancelarR.setEnabled(false);
                txtIdR.setEnabled(false);
                txtNombreR.setEnabled(false);
                btnPrimeroR.setEnabled(false);
                btnAnteriorR.setEnabled(false);
                btnSiguienteR.setEnabled(false);
                btnUltimoR.setEnabled(false);
                btnAltaR.setEnabled(true);
                btnBajaR.setEnabled(false);
                btnModificarR.setEnabled(false);
                btnBuscarR.setEnabled(true);
            }
        }catch (Exception e){
            System.out.println("Cargar Consulta Rubros: "+e);
        }
    }
    
    
    
    
    //METODOS AUMENTOS **********************************************************************************************************************************************
    
    public static void CargarComboAumentoRubro(){
        try{
            int i = 0;
            cboSeleccionarRubroAu.removeAllItems();
            ResRubPro = sentencia_au.executeQuery("SELECT * FROM tiendatorresi.rubro ORDER BY rubro.id_rubro");
            ResRubPro.last();
            if(ResRubPro.getRow()==0){
                JOptionPane.showMessageDialog(null,"Debe cargar un Rubro");
            }
            else{
            ResRubPro.beforeFirst();
            while (ResRubPro.next()){
            cboSeleccionarRubroAu.addItem(ResRubPro.getString("rubro.nombre_rubro"));
            rubpro[i] = ResRubPro.getInt("rubro.id_rubro");
            i++;
                }
            }
        }
        catch(Exception e){
            System.out.println("Error CargarComboRubro"+e);
            }
    }
    
    public static void CargarComboAumentoProv(){
        try{
            int i = 0;
            cboSeleccionarRubroAu.removeAllItems();
            ResRubPro = sentencia_au.executeQuery("SELECT * FROM tiendatorresi.proveedor ORDER BY proveedor.id_proveedor");
            ResRubPro.last();
            if(ResRubPro.getRow()==0){
                JOptionPane.showMessageDialog(null,"Debe cargar un Proveedor");
            }
            else{
            ResRubPro.beforeFirst();
            while (ResRubPro.next()){
            cboSeleccionarRubroAu.addItem(ResRubPro.getString("proveedor.nombre_proveedor"));
            rubpro[i] = ResRubPro.getInt("proveedor.id_proveedor");
            i++;
                }
            }
        }
        catch(Exception e){
            System.out.println("Error CargarComboProv"+e);
            }
    }
    
    public static void CargarConsultaAumentoRubro(){
        try{
            r_ru = sentencia_au.executeQuery("SELECT rubro.id_rubro, rubro.nombre_rubro FROM tiendatorresi.rubro");
            r_ru.next();
        }
        catch(Exception e){
            System.out.println("Error CargarConsultaRubro "+e);
        }
    }

    public static void CargarConsultaAumentoProv(){
        try{
            r_ru = sentencia_au.executeQuery("SELECT proveedor.id_proveedor, proveedor.nombre_proveedor FROM tiendatorresi.proveedor");
            r_ru.next();
        }
        catch(Exception e){
            System.out.println("Error CargarConsultaProv "+e);
        }
    }
    
    public boolean SoloNum(String aum){
        int num;
        try{
            num = Integer.parseInt(aum);
            return true;
        
        }catch(Exception e){
            return false;
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSeleccion = new javax.swing.JPanel();
        btnArticulos = new javax.swing.JButton();
        btnProveedores = new javax.swing.JButton();
        btnRubros = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnConfiguracion = new javax.swing.JButton();
        btnHistorial = new javax.swing.JButton();
        btnAumentos = new javax.swing.JButton();
        pnlVentana = new javax.swing.JPanel();
        lblVentana = new javax.swing.JLabel();
        pnlArticulos = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btnCerrarA = new javax.swing.JButton();
        btnBajaA = new javax.swing.JButton();
        btnAltaA = new javax.swing.JButton();
        btnBuscar1A = new javax.swing.JButton();
        btnModificarA = new javax.swing.JButton();
        btnUltimoA = new javax.swing.JButton();
        btnSiguienteA = new javax.swing.JButton();
        btnAnteriorA = new javax.swing.JButton();
        btnPrimeroA = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        pnlPrecioCostoA = new javax.swing.JPanel();
        txtPrecioCostoA = new javax.swing.JTextField();
        lblSignoPrecioA = new javax.swing.JLabel();
        cboProveedorA = new javax.swing.JComboBox();
        txtIdA = new javax.swing.JTextField();
        lblIdA = new javax.swing.JLabel();
        lblProveedorA = new javax.swing.JLabel();
        lblPrecioCostoA = new javax.swing.JLabel();
        txtNombreA = new javax.swing.JTextField();
        lblNombreA = new javax.swing.JLabel();
        lblCodigoA = new javax.swing.JLabel();
        txtCodigoA = new javax.swing.JTextField();
        lblRubroA = new javax.swing.JLabel();
        cboRubroA = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        txtPrecioVentaA = new javax.swing.JTextField();
        lblSignoPrecioA1 = new javax.swing.JLabel();
        lblPrecioVentaA = new javax.swing.JLabel();
        lblFechaActualizacionA = new javax.swing.JLabel();
        txtFechaActualizacionA = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnAceptarA = new javax.swing.JButton();
        btnCancelarA = new javax.swing.JButton();
        pnlProveedores = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnCerrarP = new javax.swing.JButton();
        btnEliminarP = new javax.swing.JButton();
        btnAltaP = new javax.swing.JButton();
        btnBuscarP = new javax.swing.JButton();
        btnModificarP = new javax.swing.JButton();
        btnUltimoP = new javax.swing.JButton();
        btnSiguienteP = new javax.swing.JButton();
        btnAnteriorP = new javax.swing.JButton();
        btnPrimeroP = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        cboLocalidadP = new javax.swing.JComboBox();
        txtDireccionP = new javax.swing.JTextField();
        txtIdP = new javax.swing.JTextField();
        lblIdP = new javax.swing.JLabel();
        lblDireccionP = new javax.swing.JLabel();
        lblLocalidadP = new javax.swing.JLabel();
        lblNombreP = new javax.swing.JLabel();
        txtNombreP = new javax.swing.JTextField();
        txtCuitP = new javax.swing.JTextField();
        txtEmailP = new javax.swing.JTextField();
        txtCelularP = new javax.swing.JTextField();
        lblCuitP = new javax.swing.JLabel();
        lblEmailP = new javax.swing.JLabel();
        lblCelularP = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btnAceptarP = new javax.swing.JButton();
        btnCancelarP = new javax.swing.JButton();
        pnlRubros = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        txtIdR = new javax.swing.JTextField();
        txtNombreR = new javax.swing.JTextField();
        lblIdR = new javax.swing.JLabel();
        lblNombreR = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        btnAceptarR = new javax.swing.JButton();
        btnCancelarR = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        btnPrimeroR = new javax.swing.JButton();
        btnAnteriorR = new javax.swing.JButton();
        btnSiguienteR = new javax.swing.JButton();
        btnUltimoR = new javax.swing.JButton();
        btnModificarR = new javax.swing.JButton();
        btnBuscarR = new javax.swing.JButton();
        btnAltaR = new javax.swing.JButton();
        btnBajaR = new javax.swing.JButton();
        btnCerrarR = new javax.swing.JButton();
        pnlAumentos = new javax.swing.JPanel();
        btnAñadirAu = new javax.swing.JButton();
        btnCerrarAu = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        btnAumProv = new javax.swing.JButton();
        btnAumRubro = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        cboSeleccionarRubroAu = new javax.swing.JComboBox();
        lblRubroProveedorAu = new javax.swing.JLabel();
        pnlPorcentajeAu = new javax.swing.JPanel();
        txtAumento = new javax.swing.JTextField();
        lblProciento = new javax.swing.JLabel();
        lblAumento = new javax.swing.JLabel();
        pnlTiendaTorresi = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlSeleccion.setBackground(new java.awt.Color(0, 51, 204));
        pnlSeleccion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnArticulos.setBackground(new java.awt.Color(0, 51, 204));
        btnArticulos.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnArticulos.setForeground(new java.awt.Color(255, 204, 25));
        btnArticulos.setText("Artículos");
        btnArticulos.setOpaque(false);
        btnArticulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArticulosActionPerformed(evt);
            }
        });

        btnProveedores.setBackground(new java.awt.Color(0, 51, 204));
        btnProveedores.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnProveedores.setForeground(new java.awt.Color(255, 204, 25));
        btnProveedores.setText("Proveedores");
        btnProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedoresActionPerformed(evt);
            }
        });

        btnRubros.setBackground(new java.awt.Color(0, 51, 204));
        btnRubros.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnRubros.setForeground(new java.awt.Color(255, 204, 25));
        btnRubros.setText("Rubros");
        btnRubros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRubrosActionPerformed(evt);
            }
        });

        btnReportes.setBackground(new java.awt.Color(0, 51, 204));
        btnReportes.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnReportes.setForeground(new java.awt.Color(255, 204, 25));
        btnReportes.setText("Reportes");
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });

        btnConfiguracion.setBackground(new java.awt.Color(0, 51, 204));
        btnConfiguracion.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnConfiguracion.setForeground(new java.awt.Color(255, 204, 25));
        btnConfiguracion.setText("Configuración");
        btnConfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfiguracionActionPerformed(evt);
            }
        });

        btnHistorial.setBackground(new java.awt.Color(0, 51, 204));
        btnHistorial.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnHistorial.setForeground(new java.awt.Color(255, 204, 25));
        btnHistorial.setText("Historial");
        btnHistorial.setToolTipText("");
        btnHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialActionPerformed(evt);
            }
        });

        btnAumentos.setBackground(new java.awt.Color(0, 51, 204));
        btnAumentos.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAumentos.setForeground(new java.awt.Color(255, 204, 25));
        btnAumentos.setText("Aumentos");
        btnAumentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAumentosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSeleccionLayout = new javax.swing.GroupLayout(pnlSeleccion);
        pnlSeleccion.setLayout(pnlSeleccionLayout);
        pnlSeleccionLayout.setHorizontalGroup(
            pnlSeleccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAumentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlSeleccionLayout.createSequentialGroup()
                .addComponent(btnHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(btnConfiguracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnReportes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnProveedores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnArticulos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRubros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlSeleccionLayout.setVerticalGroup(
            pnlSeleccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSeleccionLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(btnArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btnProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btnRubros, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btnConfiguracion, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btnAumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(btnHistorial, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pnlVentana.setBackground(new java.awt.Color(0, 51, 204));
        pnlVentana.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlVentana.setForeground(new java.awt.Color(255, 255, 51));

        lblVentana.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblVentana.setForeground(new java.awt.Color(255, 255, 255));
        lblVentana.setText("ARTICULOS");

        btnCerrarA.setBackground(new java.awt.Color(255, 204, 25));
        btnCerrarA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnCerrarA.setForeground(new java.awt.Color(0, 51, 204));
        btnCerrarA.setText("Cerrar");
        btnCerrarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarAActionPerformed(evt);
            }
        });

        btnBajaA.setBackground(new java.awt.Color(255, 204, 25));
        btnBajaA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnBajaA.setForeground(new java.awt.Color(0, 51, 204));
        btnBajaA.setText("-");
        btnBajaA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajaAActionPerformed(evt);
            }
        });

        btnAltaA.setBackground(new java.awt.Color(255, 204, 25));
        btnAltaA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAltaA.setForeground(new java.awt.Color(0, 51, 204));
        btnAltaA.setText("+");
        btnAltaA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltaAActionPerformed(evt);
            }
        });

        btnBuscar1A.setBackground(new java.awt.Color(255, 204, 25));
        btnBuscar1A.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnBuscar1A.setForeground(new java.awt.Color(0, 51, 204));
        btnBuscar1A.setText("Buscar");
        btnBuscar1A.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscar1AActionPerformed(evt);
            }
        });

        btnModificarA.setBackground(new java.awt.Color(255, 204, 25));
        btnModificarA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnModificarA.setForeground(new java.awt.Color(0, 51, 204));
        btnModificarA.setText("Modificar");
        btnModificarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarAActionPerformed(evt);
            }
        });

        btnUltimoA.setBackground(new java.awt.Color(255, 204, 25));
        btnUltimoA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnUltimoA.setForeground(new java.awt.Color(0, 51, 204));
        btnUltimoA.setText(">>");
        btnUltimoA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoAActionPerformed(evt);
            }
        });

        btnSiguienteA.setBackground(new java.awt.Color(255, 204, 25));
        btnSiguienteA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnSiguienteA.setForeground(new java.awt.Color(0, 51, 204));
        btnSiguienteA.setText(">");
        btnSiguienteA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteAActionPerformed(evt);
            }
        });

        btnAnteriorA.setBackground(new java.awt.Color(255, 204, 25));
        btnAnteriorA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAnteriorA.setForeground(new java.awt.Color(0, 51, 204));
        btnAnteriorA.setText("<");
        btnAnteriorA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorAActionPerformed(evt);
            }
        });

        btnPrimeroA.setBackground(new java.awt.Color(255, 204, 25));
        btnPrimeroA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnPrimeroA.setForeground(new java.awt.Color(0, 51, 204));
        btnPrimeroA.setText("<<");
        btnPrimeroA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btnPrimeroA, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnAnteriorA, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnSiguienteA, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnUltimoA, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnModificarA)
                .addGap(15, 15, 15)
                .addComponent(btnBuscar1A)
                .addGap(15, 15, 15)
                .addComponent(btnAltaA, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnBajaA, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnCerrarA)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrarA)
                    .addComponent(btnBajaA)
                    .addComponent(btnAltaA)
                    .addComponent(btnBuscar1A)
                    .addComponent(btnModificarA)
                    .addComponent(btnUltimoA)
                    .addComponent(btnSiguienteA)
                    .addComponent(btnAnteriorA)
                    .addComponent(btnPrimeroA)))
        );

        pnlPrecioCostoA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtPrecioCostoA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtPrecioCostoA.setEnabled(false);
        txtPrecioCostoA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioCostoAActionPerformed(evt);
            }
        });

        lblSignoPrecioA.setBackground(new java.awt.Color(255, 255, 255));
        lblSignoPrecioA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblSignoPrecioA.setForeground(new java.awt.Color(0, 51, 204));
        lblSignoPrecioA.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSignoPrecioA.setText(" $ ");

        javax.swing.GroupLayout pnlPrecioCostoALayout = new javax.swing.GroupLayout(pnlPrecioCostoA);
        pnlPrecioCostoA.setLayout(pnlPrecioCostoALayout);
        pnlPrecioCostoALayout.setHorizontalGroup(
            pnlPrecioCostoALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrecioCostoALayout.createSequentialGroup()
                .addComponent(lblSignoPrecioA, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(txtPrecioCostoA, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
        );
        pnlPrecioCostoALayout.setVerticalGroup(
            pnlPrecioCostoALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrecioCostoALayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pnlPrecioCostoALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecioCostoA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSignoPrecioA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        cboProveedorA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cboProveedorA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboProveedorA.setToolTipText("");
        cboProveedorA.setEnabled(false);
        cboProveedorA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProveedorAActionPerformed(evt);
            }
        });

        txtIdA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtIdA.setEnabled(false);

        lblIdA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblIdA.setForeground(new java.awt.Color(0, 51, 204));
        lblIdA.setText("ID:");

        lblProveedorA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblProveedorA.setForeground(new java.awt.Color(0, 51, 204));
        lblProveedorA.setText("Proveedor:");

        lblPrecioCostoA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblPrecioCostoA.setForeground(new java.awt.Color(0, 51, 204));
        lblPrecioCostoA.setText("Precio costo:");

        txtNombreA.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtNombreA.setEnabled(false);

        lblNombreA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblNombreA.setForeground(new java.awt.Color(0, 51, 204));
        lblNombreA.setText("Nombre:");

        lblCodigoA.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        lblCodigoA.setForeground(new java.awt.Color(0, 51, 204));
        lblCodigoA.setText("Código:");

        txtCodigoA.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        txtCodigoA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoAActionPerformed(evt);
            }
        });
        txtCodigoA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoAKeyPressed(evt);
            }
        });

        lblRubroA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblRubroA.setForeground(new java.awt.Color(0, 51, 204));
        lblRubroA.setText("Rubro:");

        cboRubroA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cboRubroA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboRubroA.setEnabled(false);
        cboRubroA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboRubroAActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtPrecioVentaA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtPrecioVentaA.setEnabled(false);

        lblSignoPrecioA1.setBackground(new java.awt.Color(255, 255, 255));
        lblSignoPrecioA1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblSignoPrecioA1.setForeground(new java.awt.Color(0, 51, 204));
        lblSignoPrecioA1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSignoPrecioA1.setText(" $ ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblSignoPrecioA1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(txtPrecioVentaA))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txtPrecioVentaA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblSignoPrecioA1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblPrecioVentaA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblPrecioVentaA.setForeground(new java.awt.Color(0, 51, 204));
        lblPrecioVentaA.setText("Precio venta:");

        lblFechaActualizacionA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblFechaActualizacionA.setForeground(new java.awt.Color(0, 51, 204));
        lblFechaActualizacionA.setText("Fecha act.:");

        txtFechaActualizacionA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtFechaActualizacionA.setEnabled(false);
        txtFechaActualizacionA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActualizacionAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblFechaActualizacionA)
                    .addComponent(lblProveedorA)
                    .addComponent(lblIdA)
                    .addComponent(lblNombreA)
                    .addComponent(lblPrecioCostoA, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombreA, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                    .addComponent(txtFechaActualizacionA, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtIdA, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(200, 200, 200)
                                .addComponent(lblCodigoA))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cboProveedorA, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblRubroA))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(pnlPrecioCostoA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPrecioVentaA)))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboRubroA, javax.swing.GroupLayout.Alignment.LEADING, 0, 200, Short.MAX_VALUE)
                            .addComponent(txtCodigoA, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombreA))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdA)
                    .addComponent(txtIdA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCodigoA)
                    .addComponent(txtCodigoA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProveedorA)
                    .addComponent(cboProveedorA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRubroA)
                    .addComponent(cboRubroA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlPrecioCostoA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPrecioCostoA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFechaActualizacionA)
                            .addComponent(txtFechaActualizacionA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblPrecioVentaA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );

        btnAceptarA.setBackground(new java.awt.Color(255, 204, 25));
        btnAceptarA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAceptarA.setForeground(new java.awt.Color(0, 51, 204));
        btnAceptarA.setText("Aceptar");
        btnAceptarA.setEnabled(false);
        btnAceptarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarAActionPerformed(evt);
            }
        });

        btnCancelarA.setBackground(new java.awt.Color(255, 204, 25));
        btnCancelarA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnCancelarA.setForeground(new java.awt.Color(0, 51, 204));
        btnCancelarA.setText("Cancelar");
        btnCancelarA.setEnabled(false);
        btnCancelarA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAceptarA, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnCancelarA, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(btnAceptarA, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnCancelarA, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout pnlArticulosLayout = new javax.swing.GroupLayout(pnlArticulos);
        pnlArticulos.setLayout(pnlArticulosLayout);
        pnlArticulosLayout.setHorizontalGroup(
            pnlArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlArticulosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlArticulosLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );
        pnlArticulosLayout.setVerticalGroup(
            pnlArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlArticulosLayout.createSequentialGroup()
                .addGroup(pnlArticulosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlArticulosLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlArticulosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)))
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        btnCerrarP.setBackground(new java.awt.Color(255, 204, 25));
        btnCerrarP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnCerrarP.setForeground(new java.awt.Color(0, 51, 204));
        btnCerrarP.setText("Cerrar");
        btnCerrarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarPActionPerformed(evt);
            }
        });

        btnEliminarP.setBackground(new java.awt.Color(255, 204, 25));
        btnEliminarP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnEliminarP.setForeground(new java.awt.Color(0, 51, 204));
        btnEliminarP.setText("-");
        btnEliminarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPActionPerformed(evt);
            }
        });

        btnAltaP.setBackground(new java.awt.Color(255, 204, 25));
        btnAltaP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAltaP.setForeground(new java.awt.Color(0, 51, 204));
        btnAltaP.setText("+");
        btnAltaP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltaPActionPerformed(evt);
            }
        });

        btnBuscarP.setBackground(new java.awt.Color(255, 204, 25));
        btnBuscarP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnBuscarP.setForeground(new java.awt.Color(0, 51, 204));
        btnBuscarP.setText("Buscar");
        btnBuscarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPActionPerformed(evt);
            }
        });

        btnModificarP.setBackground(new java.awt.Color(255, 204, 25));
        btnModificarP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnModificarP.setForeground(new java.awt.Color(0, 51, 204));
        btnModificarP.setText("Modificar");
        btnModificarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarPActionPerformed(evt);
            }
        });

        btnUltimoP.setBackground(new java.awt.Color(255, 204, 25));
        btnUltimoP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnUltimoP.setForeground(new java.awt.Color(0, 51, 204));
        btnUltimoP.setText(">>");
        btnUltimoP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoPActionPerformed(evt);
            }
        });

        btnSiguienteP.setBackground(new java.awt.Color(255, 204, 25));
        btnSiguienteP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnSiguienteP.setForeground(new java.awt.Color(0, 51, 204));
        btnSiguienteP.setText(">");
        btnSiguienteP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguientePActionPerformed(evt);
            }
        });

        btnAnteriorP.setBackground(new java.awt.Color(255, 204, 25));
        btnAnteriorP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAnteriorP.setForeground(new java.awt.Color(0, 51, 204));
        btnAnteriorP.setText("<");
        btnAnteriorP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorPActionPerformed(evt);
            }
        });

        btnPrimeroP.setBackground(new java.awt.Color(255, 204, 25));
        btnPrimeroP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnPrimeroP.setForeground(new java.awt.Color(0, 51, 204));
        btnPrimeroP.setText("<<");
        btnPrimeroP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btnPrimeroP, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnAnteriorP, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnSiguienteP, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnUltimoP, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 54, Short.MAX_VALUE)
                .addComponent(btnModificarP)
                .addGap(15, 15, 15)
                .addComponent(btnBuscarP)
                .addGap(15, 15, 15)
                .addComponent(btnAltaP, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnEliminarP, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnCerrarP, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrarP)
                    .addComponent(btnEliminarP)
                    .addComponent(btnAltaP)
                    .addComponent(btnBuscarP)
                    .addComponent(btnModificarP)
                    .addComponent(btnUltimoP)
                    .addComponent(btnSiguienteP)
                    .addComponent(btnAnteriorP)
                    .addComponent(btnPrimeroP)))
        );

        cboLocalidadP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cboLocalidadP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLocalidadP.setToolTipText("");
        cboLocalidadP.setEnabled(false);
        cboLocalidadP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLocalidadPActionPerformed(evt);
            }
        });

        txtDireccionP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtDireccionP.setEnabled(false);
        txtDireccionP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionPActionPerformed(evt);
            }
        });

        txtIdP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtIdP.setEnabled(false);
        txtIdP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdPActionPerformed(evt);
            }
        });

        lblIdP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblIdP.setForeground(new java.awt.Color(0, 51, 204));
        lblIdP.setText("ID:");

        lblDireccionP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblDireccionP.setForeground(new java.awt.Color(0, 51, 204));
        lblDireccionP.setText("Direccion:");

        lblLocalidadP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblLocalidadP.setForeground(new java.awt.Color(0, 51, 204));
        lblLocalidadP.setText("Localidad:");

        lblNombreP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblNombreP.setForeground(new java.awt.Color(0, 51, 204));
        lblNombreP.setText("Nombre:");

        txtNombreP.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txtNombreP.setEnabled(false);

        txtCuitP.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        txtCuitP.setEnabled(false);
        txtCuitP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCuitPActionPerformed(evt);
            }
        });
        txtCuitP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCuitPKeyPressed(evt);
            }
        });

        txtEmailP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtEmailP.setEnabled(false);

        txtCelularP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtCelularP.setEnabled(false);

        lblCuitP.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        lblCuitP.setForeground(new java.awt.Color(0, 51, 204));
        lblCuitP.setText("Cuit:");

        lblEmailP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblEmailP.setForeground(new java.awt.Color(0, 51, 204));
        lblEmailP.setText("Email:");

        lblCelularP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCelularP.setForeground(new java.awt.Color(0, 51, 204));
        lblCelularP.setText("Celular:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblIdP)
                    .addComponent(lblLocalidadP)
                    .addComponent(lblDireccionP)
                    .addComponent(lblNombreP))
                .addGap(28, 28, 28)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDireccionP)
                                .addComponent(cboLocalidadP, 0, 200, Short.MAX_VALUE))
                            .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCelularP, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblEmailP, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCuitP, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmailP, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(txtCuitP)
                            .addComponent(txtCelularP)))
                    .addComponent(txtNombreP))
                .addGap(0, 0, 0))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreP)
                    .addComponent(txtNombreP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIdP)
                    .addComponent(txtCuitP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCuitP))
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccionP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDireccionP)
                    .addComponent(txtEmailP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmailP))
                .addGap(15, 15, 15)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboLocalidadP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLocalidadP)
                    .addComponent(txtCelularP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCelularP))
                .addGap(0, 0, 0))
        );

        btnAceptarP.setBackground(new java.awt.Color(255, 204, 25));
        btnAceptarP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAceptarP.setForeground(new java.awt.Color(0, 51, 204));
        btnAceptarP.setText("Aceptar");
        btnAceptarP.setEnabled(false);
        btnAceptarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarPActionPerformed(evt);
            }
        });

        btnCancelarP.setBackground(new java.awt.Color(255, 204, 25));
        btnCancelarP.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnCancelarP.setForeground(new java.awt.Color(0, 51, 204));
        btnCancelarP.setText("Cancelar");
        btnCancelarP.setEnabled(false);
        btnCancelarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAceptarP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(btnAceptarP, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnCancelarP, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout pnlProveedoresLayout = new javax.swing.GroupLayout(pnlProveedores);
        pnlProveedores.setLayout(pnlProveedoresLayout);
        pnlProveedoresLayout.setHorizontalGroup(
            pnlProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProveedoresLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlProveedoresLayout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        pnlProveedoresLayout.setVerticalGroup(
            pnlProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProveedoresLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlProveedoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProveedoresLayout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)))
                .addGap(25, 25, 25)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pnlRubros.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtIdR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtIdR.setEnabled(false);
        txtIdR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdRActionPerformed(evt);
            }
        });

        txtNombreR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtNombreR.setEnabled(false);
        txtNombreR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreRActionPerformed(evt);
            }
        });

        lblIdR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblIdR.setForeground(new java.awt.Color(0, 51, 204));
        lblIdR.setText("Id Rubro:");

        lblNombreR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblNombreR.setForeground(new java.awt.Color(0, 51, 204));
        lblNombreR.setText("Nombre:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIdR)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblNombreR)))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIdR, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreR, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreR, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNombreR))
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdR, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIdR))
                .addGap(0, 0, 0))
        );

        btnAceptarR.setBackground(new java.awt.Color(255, 204, 25));
        btnAceptarR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAceptarR.setForeground(new java.awt.Color(0, 51, 204));
        btnAceptarR.setText("Aceptar");
        btnAceptarR.setEnabled(false);
        btnAceptarR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarRActionPerformed(evt);
            }
        });

        btnCancelarR.setBackground(new java.awt.Color(255, 204, 25));
        btnCancelarR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnCancelarR.setForeground(new java.awt.Color(0, 51, 204));
        btnCancelarR.setText("Cancelar");
        btnCancelarR.setEnabled(false);
        btnCancelarR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAceptarR, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarR, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(btnAceptarR, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnCancelarR, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        btnPrimeroR.setBackground(new java.awt.Color(255, 204, 25));
        btnPrimeroR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnPrimeroR.setForeground(new java.awt.Color(0, 51, 204));
        btnPrimeroR.setText("<<");
        btnPrimeroR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroRActionPerformed(evt);
            }
        });

        btnAnteriorR.setBackground(new java.awt.Color(255, 204, 25));
        btnAnteriorR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAnteriorR.setForeground(new java.awt.Color(0, 51, 204));
        btnAnteriorR.setText("<");
        btnAnteriorR.setPreferredSize(new java.awt.Dimension(67, 37));
        btnAnteriorR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorRActionPerformed(evt);
            }
        });

        btnSiguienteR.setBackground(new java.awt.Color(255, 204, 25));
        btnSiguienteR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnSiguienteR.setForeground(new java.awt.Color(0, 51, 204));
        btnSiguienteR.setText(">");
        btnSiguienteR.setPreferredSize(new java.awt.Dimension(67, 37));
        btnSiguienteR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteRActionPerformed(evt);
            }
        });

        btnUltimoR.setBackground(new java.awt.Color(255, 204, 25));
        btnUltimoR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnUltimoR.setForeground(new java.awt.Color(0, 51, 204));
        btnUltimoR.setText(">>");
        btnUltimoR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoRActionPerformed(evt);
            }
        });

        btnModificarR.setBackground(new java.awt.Color(255, 204, 25));
        btnModificarR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnModificarR.setForeground(new java.awt.Color(0, 51, 204));
        btnModificarR.setText("Modificar");
        btnModificarR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarRActionPerformed(evt);
            }
        });

        btnBuscarR.setBackground(new java.awt.Color(255, 204, 25));
        btnBuscarR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnBuscarR.setForeground(new java.awt.Color(0, 51, 204));
        btnBuscarR.setText("Buscar");
        btnBuscarR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarRActionPerformed(evt);
            }
        });

        btnAltaR.setBackground(new java.awt.Color(255, 204, 25));
        btnAltaR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAltaR.setForeground(new java.awt.Color(0, 51, 204));
        btnAltaR.setText("+");
        btnAltaR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltaRActionPerformed(evt);
            }
        });

        btnBajaR.setBackground(new java.awt.Color(255, 204, 25));
        btnBajaR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnBajaR.setForeground(new java.awt.Color(0, 51, 204));
        btnBajaR.setText("-");
        btnBajaR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajaRActionPerformed(evt);
            }
        });

        btnCerrarR.setBackground(new java.awt.Color(255, 204, 25));
        btnCerrarR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnCerrarR.setForeground(new java.awt.Color(0, 51, 204));
        btnCerrarR.setText("Cerrar");
        btnCerrarR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(btnPrimeroR, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnAnteriorR, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnSiguienteR, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnUltimoR, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(btnModificarR)
                .addGap(15, 15, 15)
                .addComponent(btnBuscarR)
                .addGap(15, 15, 15)
                .addComponent(btnAltaR, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnBajaR, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnCerrarR)
                .addGap(0, 0, 0))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnModificarR)
                .addComponent(btnBuscarR)
                .addComponent(btnAltaR)
                .addComponent(btnBajaR)
                .addComponent(btnCerrarR))
            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnAnteriorR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSiguienteR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnUltimoR)
                .addComponent(btnPrimeroR))
        );

        javax.swing.GroupLayout pnlRubrosLayout = new javax.swing.GroupLayout(pnlRubros);
        pnlRubros.setLayout(pnlRubrosLayout);
        pnlRubrosLayout.setHorizontalGroup(
            pnlRubrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRubrosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlRubrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlRubrosLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        pnlRubrosLayout.setVerticalGroup(
            pnlRubrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRubrosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlRubrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlRubrosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        pnlAumentos.setAlignmentX(0.0F);
        pnlAumentos.setAlignmentY(0.0F);
        pnlAumentos.setPreferredSize(new java.awt.Dimension(400, 100));

        btnAñadirAu.setBackground(new java.awt.Color(255, 204, 25));
        btnAñadirAu.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAñadirAu.setForeground(new java.awt.Color(0, 51, 204));
        btnAñadirAu.setText("Añadir Aumento");
        btnAñadirAu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirAuActionPerformed(evt);
            }
        });

        btnCerrarAu.setBackground(new java.awt.Color(255, 204, 25));
        btnCerrarAu.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnCerrarAu.setForeground(new java.awt.Color(0, 51, 204));
        btnCerrarAu.setText("Cerrar");
        btnCerrarAu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarAuActionPerformed(evt);
            }
        });

        btnAumProv.setBackground(new java.awt.Color(255, 204, 25));
        btnAumProv.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAumProv.setForeground(new java.awt.Color(0, 51, 204));
        btnAumProv.setText("Aumentar Proveedor");
        btnAumProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAumProvActionPerformed(evt);
            }
        });

        btnAumRubro.setBackground(new java.awt.Color(255, 204, 25));
        btnAumRubro.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAumRubro.setForeground(new java.awt.Color(0, 51, 204));
        btnAumRubro.setText("Aumentar Rubro");
        btnAumRubro.setEnabled(false);
        btnAumRubro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAumRubroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAumProv, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(btnAumRubro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(btnAumRubro, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnAumProv, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cboSeleccionarRubroAu.setBackground(new java.awt.Color(240, 240, 240));
        cboSeleccionarRubroAu.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cboSeleccionarRubroAu.setForeground(new java.awt.Color(0, 51, 204));
        cboSeleccionarRubroAu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSeleccionarRubroAu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSeleccionarRubroAuActionPerformed(evt);
            }
        });

        lblRubroProveedorAu.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblRubroProveedorAu.setForeground(new java.awt.Color(0, 51, 204));
        lblRubroProveedorAu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblRubroProveedorAu.setText("Rubro");

        pnlPorcentajeAu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtAumento.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtAumento.setToolTipText("");
        txtAumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAumentoActionPerformed(evt);
            }
        });

        lblProciento.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblProciento.setForeground(new java.awt.Color(0, 51, 204));
        lblProciento.setText("%");

        javax.swing.GroupLayout pnlPorcentajeAuLayout = new javax.swing.GroupLayout(pnlPorcentajeAu);
        pnlPorcentajeAu.setLayout(pnlPorcentajeAuLayout);
        pnlPorcentajeAuLayout.setHorizontalGroup(
            pnlPorcentajeAuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPorcentajeAuLayout.createSequentialGroup()
                .addComponent(txtAumento, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addGap(13, 13, 13)
                .addComponent(lblProciento)
                .addGap(18, 18, 18))
        );
        pnlPorcentajeAuLayout.setVerticalGroup(
            pnlPorcentajeAuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPorcentajeAuLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlPorcentajeAuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAumento)
                    .addComponent(lblProciento)))
        );

        lblAumento.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblAumento.setForeground(new java.awt.Color(0, 51, 204));
        lblAumento.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAumento.setText("Aumento");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(lblRubroProveedorAu)
                        .addGap(15, 15, 15)
                        .addComponent(cboSeleccionarRubroAu, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(lblAumento, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(pnlPorcentajeAu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRubroProveedorAu)
                    .addComponent(cboSeleccionarRubroAu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlPorcentajeAu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAumento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout pnlAumentosLayout = new javax.swing.GroupLayout(pnlAumentos);
        pnlAumentos.setLayout(pnlAumentosLayout);
        pnlAumentosLayout.setHorizontalGroup(
            pnlAumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAumentosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(pnlAumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAumentosLayout.createSequentialGroup()
                        .addComponent(btnAñadirAu)
                        .addGap(18, 18, 18)
                        .addComponent(btnCerrarAu, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );
        pnlAumentosLayout.setVerticalGroup(
            pnlAumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAumentosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pnlAumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAumentosLayout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addGroup(pnlAumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAñadirAu)
                            .addComponent(btnCerrarAu)))
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout pnlVentanaLayout = new javax.swing.GroupLayout(pnlVentana);
        pnlVentana.setLayout(pnlVentanaLayout);
        pnlVentanaLayout.setHorizontalGroup(
            pnlVentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVentanaLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnlVentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlVentanaLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lblVentana))
                    .addComponent(pnlRubros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlAumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 703, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlVentanaLayout.setVerticalGroup(
            pnlVentanaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVentanaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblVentana)
                .addGap(10, 10, 10)
                .addComponent(pnlArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlRubros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(pnlAumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlTiendaTorresi.setBackground(new java.awt.Color(0, 51, 204));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiendatorresi/Tienda Torresi.png"))); // NOI18N

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tiendatorresi/tienda logo.png"))); // NOI18N

        javax.swing.GroupLayout pnlTiendaTorresiLayout = new javax.swing.GroupLayout(pnlTiendaTorresi);
        pnlTiendaTorresi.setLayout(pnlTiendaTorresiLayout);
        pnlTiendaTorresiLayout.setHorizontalGroup(
            pnlTiendaTorresiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTiendaTorresiLayout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jLabel15)
                .addGap(277, 277, 277)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlTiendaTorresiLayout.setVerticalGroup(
            pnlTiendaTorresiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTiendaTorresiLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(pnlTiendaTorresiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(pnlSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlVentana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnlTiendaTorresi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pnlTiendaTorresi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnlVentana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresActionPerformed
        pnlArticulos.setVisible(false);
        pnlProveedores.setVisible(true);
        pnlRubros.setVisible(false);
        pnlAumentos.setVisible(false);
        lblVentana.setText("PROVEEDORES");
    }//GEN-LAST:event_btnProveedoresActionPerformed

    private void btnRubrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRubrosActionPerformed
       pnlArticulos.setVisible(false);
       pnlProveedores.setVisible(false);
       pnlRubros.setVisible(true);
       pnlAumentos.setVisible(false);
       lblVentana.setText("RUBROS");
    }//GEN-LAST:event_btnRubrosActionPerformed

    private void btnHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialActionPerformed
        pnlArticulos.setVisible(false);
        pnlProveedores.setVisible(false);
        pnlRubros.setVisible(false);
        pnlAumentos.setVisible(false);
        lblVentana.setText("HISTORIAL");
    }//GEN-LAST:event_btnHistorialActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        pnlArticulos.setVisible(false);
        pnlProveedores.setVisible(false);
        pnlRubros.setVisible(false);
        pnlAumentos.setVisible(false);
        lblVentana.setText("REPORTES");
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnAumentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAumentosActionPerformed
        pnlArticulos.setVisible(false);
        pnlProveedores.setVisible(false);
        pnlRubros.setVisible(false);
        pnlAumentos.setVisible(true);
        lblVentana.setText("AUMENTOS");
    }//GEN-LAST:event_btnAumentosActionPerformed

    private void btnArticulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArticulosActionPerformed
        pnlArticulos.setVisible(true);
        pnlProveedores.setVisible(false);
        pnlRubros.setVisible(false);
        pnlAumentos.setVisible(false);
        lblVentana.setText("ARTICULOS");
    }//GEN-LAST:event_btnArticulosActionPerformed

    private void btnBuscar1AActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar1AActionPerformed
        BuscarArticulos abrirBA = new BuscarArticulos();
        abrirBA.setVisible(true);
    }//GEN-LAST:event_btnBuscar1AActionPerformed

    private void txtCodigoAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoAKeyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ENTER) {

            System.out.println(txtCodigoA.getText());
            BuscarCodigoArticulos(txtCodigoA.getText());

        }
    }//GEN-LAST:event_txtCodigoAKeyPressed

    private void txtCodigoAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoAActionPerformed

    private void cboProveedorAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProveedorAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboProveedorAActionPerformed

    private void btnCancelarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarAActionPerformed
        try{
            if(estado == "Alta"){
                r_ar.last();
                CargarDatosArticulos();
                btnAceptarA.setEnabled(false);
                btnCancelarA.setEnabled(false);
                txtNombreA.setEnabled(false);
                cboProveedorA.setEnabled(false);
                txtPrecioCostoA.setEnabled(false);
                cboRubroA.setEnabled(false);
                txtPrecioVentaA.setEnabled(false);
                txtFechaActualizacionA.setEnabled(false);
                btnPrimeroA.setEnabled(true);
                btnAnteriorA.setEnabled(true);
                btnSiguienteA.setEnabled(true);
                btnUltimoA.setEnabled(true);
                btnBajaA.setEnabled(true);
                btnCerrarA.setEnabled(true);
                btnAltaA.setEnabled(true);
                btnModificarA.setEnabled(true);
            }else{
                if(estado=="modificar"){
                    txtNombreA.setText(nom);
                    txtPrecioCostoA.setText(pv);
                    txtPrecioVentaA.setText(pc);
                    txtCodigoA.setText(c);
                    txtFechaActualizacionA.setText(fa);
                    cboProveedorA.setSelectedIndex(provv);
                    cboRubroA.setSelectedIndex(rb);

                    btnAceptarA.setEnabled(false);
                    btnCancelarA.setEnabled(false);
                    txtNombreA.setEnabled(false);
                    cboProveedorA.setEnabled(false);
                    txtPrecioCostoA.setEnabled(false);
                    cboRubroA.setEnabled(false);
                    txtPrecioVentaA.setEnabled(false);
                    txtFechaActualizacionA.setEnabled(false);
                    btnPrimeroA.setEnabled(true);
                    btnAnteriorA.setEnabled(true);
                    btnSiguienteA.setEnabled(true);
                    btnUltimoA.setEnabled(true);
                    btnBajaA.setEnabled(true);
                    btnCerrarA.setEnabled(true);
                    btnAltaA.setEnabled(true);
                    btnModificarA.setEnabled(true);

                }

            }
        }catch(Exception e){}
    }//GEN-LAST:event_btnCancelarAActionPerformed

    private void btnAceptarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarAActionPerformed
        try{
            String Consulta;
            int aa = 0, bb=0;
            if (estado == "Alta"){
                if(txtNombreA.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Debe completar un nombre");
                    txtNombreA.requestFocus();
                }else if(txtPrecioCostoA.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "debe completar el campo precio de costo");
                    txtPrecioCostoA.requestFocus();
                }else if(txtPrecioVentaA.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "debe completar el campo precio contado");
                    txtPrecioVentaA.requestFocus();
                }else if (txtCodigoA.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "debe completar el campo Código");
                    txtCodigoA.requestFocus();
                }else{
                    while (aa!= cboRubroA.getSelectedIndex()){
                        aa++;
                    }
                    while (bb!= cboProveedorA.getSelectedIndex()){
                        bb++;
                    }
                    int año = Calendar.getInstance().getTime().getYear()+1900;
                    int mes = Calendar.getInstance().getTime().getMonth()+1;
                    int dia = Calendar.getInstance().getTime().getDate();
                    String f = año + "-" + mes + "-" + dia;
                    Consulta = "INSERT INTO tiendatorresi.articulo VALUES ("+ txtIdA.getText()+ ",'"+ txtCodigoA.getText()+ "','" + txtNombreA.getText()+ "',"+ txtPrecioCostoA.getText()+","+ txtPrecioVentaA.getText()+",'"+ f + "',"+ rub_ar[aa]+","+ prov_ar[bb]+")";
                    System.out.println(Consulta);
                    sentencia_ar.executeUpdate(Consulta);
                    CargarConsultaArticulos();
                    r_ar.last();
                    CargarDatosArticulos();
                    btnAceptarA.setEnabled(false);
                    btnCancelarA.setEnabled(false);
                    txtNombreA.setEnabled(false);
                    cboProveedorA.setEnabled(false);
                    txtPrecioCostoA.setEnabled(false);
                    cboRubroA.setEnabled(false);
                    txtPrecioVentaA.setEnabled(false);
                    btnPrimeroA.setEnabled(true);
                    btnAnteriorA.setEnabled(true);
                    btnSiguienteA.setEnabled(true);
                    btnUltimoA.setEnabled(true);
                    btnBajaA.setEnabled(true);
                    btnCerrarA.setEnabled(true);
                    btnAltaA.setEnabled(true);
                    btnModificarA.setEnabled(true);

                }
            }else{
                if (estado == "modificar"){
                    if(txtNombreA.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Debe completar un nombre");
                        txtNombreA.requestFocus();
                    }else if(txtPrecioCostoA.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "debe completar el campo precio de costo");
                        txtPrecioCostoA.requestFocus();
                    }else if(txtPrecioVentaA.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "debe completar el campo precio contado");
                        txtPrecioVentaA.requestFocus();
                    }else if (txtCodigoA.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "debe completar el campo Código");
                        txtCodigoA.requestFocus();
                    }else{
                        while (aa != cboRubroA.getSelectedIndex()){
                            aa++;
                        }
                        while(bb != cboProveedorA.getSelectedIndex()){
                            bb++;
                        }
                        int año = Calendar.getInstance().getTime().getYear()+1900;
                        int mes = Calendar.getInstance().getTime().getMonth()+1;
                        int dia = Calendar.getInstance().getTime().getDate();
                        String f = año + "-" + mes + "-" + dia;
                        Consulta= "UPDATE tiendatorresi.articulo SET nombre_articulo= '" + txtNombreA.getText();
                        Consulta += "' , precio_costo =" + txtPrecioCostoA.getText()+ ", precio_venta=" + txtPrecioVentaA.getText();
                        Consulta += ", fecha_actualizacion = '" + f + "', rubro_id_rubro = " + rub_ar[aa] ;
                        Consulta += ", proveedor_id_proveedor = " + prov_ar[bb] + ", codigo_articulo = '" + txtCodigoA.getText() ;
                        Consulta += "' WHERE id_articulo =" +txtIdA.getText();
                        System.out.println(Consulta);

                        sentencia_ar.executeUpdate(Consulta);
                        //r = sentencia.executeQuery("SELECT localidades.IdLocalidades, localidades.NomLocalidad, localidades.CodPostal, localidades.idProvincia, provincia.Nombre FROM general.localidades, general.provincia WHERE general.localidades.idProvincia = general.provincia.idProvincia ORDER BY localidades.idLocalidades");
                        BuscarUnRegistroArticulos(txtIdA.getText());
                        CargarDatosArticulos();
                        btnAceptarA.setEnabled(false);
                        btnCancelarA.setEnabled(false);
                        txtNombreA.setEnabled(false);
                        cboProveedorA.setEnabled(false);
                        txtPrecioCostoA.setEnabled(false);
                        cboRubroA.setEnabled(false);
                        txtPrecioVentaA.setEnabled(false);
                        txtFechaActualizacionA.setEnabled(false);
                        btnPrimeroA.setEnabled(true);
                        btnAnteriorA.setEnabled(true);
                        btnSiguienteA.setEnabled(true);
                        btnUltimoA.setEnabled(true);
                        btnBajaA.setEnabled(true);
                        btnCerrarA.setEnabled(true);
                        btnAltaA.setEnabled(true);
                        btnModificarA.setEnabled(true);
                    }
                }
            }
        }catch(Exception e){
            System.out.println("btnAceptar:  "+e);
            JOptionPane.showMessageDialog(null,"Error");
        }
    }//GEN-LAST:event_btnAceptarAActionPerformed

    private void btnCerrarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarAActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarAActionPerformed

    private void btnBajaAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaAActionPerformed
        try{
            int resp;
            resp= JOptionPane.showConfirmDialog(null, "Realmente desea borrar el articulo");
            if(resp==0){
                String Consulta= "DELETE FROM tiendatorresi.articulo WHERE id_articulo="+txtIdA.getText();
                sentencia_ar.executeUpdate(Consulta);
                CargarConsultaArticulos();
                r_ar.last();
            }else{
                JOptionPane.showMessageDialog(null, "Cancelado por el ususario");
            }
        }catch(Exception e){
            BuscarUnRegistroArticulos(txtIdA.getText());//agregar
            CargarDatosArticulos();//agregar
            JOptionPane.showMessageDialog(null, "No se puede borrar la Provincia");
        }
    }//GEN-LAST:event_btnBajaAActionPerformed

    private void btnModificarAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarAActionPerformed

        try{
            btnAceptarA.setEnabled(true);
            btnCancelarA.setEnabled(true);
            txtNombreA.setEnabled(true);
            btnPrimeroA.setEnabled(false);
            btnAnteriorA.setEnabled(false);
            btnSiguienteA.setEnabled(false);
            btnUltimoA.setEnabled(false);
            btnBajaA.setEnabled(false);
            btnAltaA.setEnabled(false);
            btnModificarA.setEnabled(false);
            cboProveedorA.setEnabled(true);
            txtPrecioCostoA.setEnabled(true);
            cboRubroA.setEnabled(true);
            txtFechaActualizacionA.setEnabled(true);
            txtPrecioVentaA.setEnabled(true);

            estado= "modificar";
            nom= txtNombreA.getText();
            provv= cboProveedorA.getSelectedIndex();
            rb= cboRubroA.getSelectedIndex();
            pc= txtPrecioVentaA.getText();
            pv= txtPrecioCostoA.getText();
            c= txtCodigoA.getText();
            fa= txtFechaActualizacionA.getText();

        }catch (Exception e){}
    }//GEN-LAST:event_btnModificarAActionPerformed

    private void btnAltaAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaAActionPerformed

        try{
            btnModificarA.setEnabled(false);
            btnPrimeroA.setEnabled(false);
            btnAnteriorA.setEnabled(false);
            btnSiguienteA.setEnabled(false);
            btnUltimoA.setEnabled(false);
            btnBajaA.setEnabled(false);
            txtNombreA.setEnabled(true);
            btnAltaA.setEnabled(false);
            btnAceptarA.setEnabled(true);
            btnCancelarA.setEnabled(true);
            cboProveedorA.setEnabled(true);
            txtPrecioCostoA.setEnabled(true);
            txtCodigoA.setEnabled(true);
            cboRubroA.setEnabled(true);
            txtPrecioVentaA.setEnabled(true);
            estado = "Alta";
            txtNombreA.setText("");
            txtPrecioCostoA.setText("");
            txtCodigoA.setText("");
            int año = Calendar.getInstance().getTime().getYear()+1900;
            int mes = Calendar.getInstance().getTime().getMonth()+1;
            int dia = Calendar.getInstance().getTime().getDate();
            String f = dia + "-" + mes + "-" + año;
            txtFechaActualizacionA.setText(f);

            txtPrecioVentaA.setText("");
            cboRubroA.setSelectedIndex(0);
            cboProveedorA.setSelectedIndex(0);
            r_ar.last();
            int a = r_ar.getInt("id_articulo")+1;
            txtIdA.setText(Integer.toString(a));
        }catch(Exception e){
            txtIdA.setText("1");
        }
    }//GEN-LAST:event_btnAltaAActionPerformed

    private void txtFechaActualizacionAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActualizacionAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActualizacionAActionPerformed

    private void cboRubroAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboRubroAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboRubroAActionPerformed

    private void btnUltimoAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoAActionPerformed
        try{
            r_ar.last();

            CargarDatosArticulos();
        }    catch(Exception e){
        }
    }//GEN-LAST:event_btnUltimoAActionPerformed

    private void btnAnteriorAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorAActionPerformed
        try{
            if(r_ar.isFirst()){
            }else{
                r_ar.previous();

                CargarDatosArticulos();
            }
        }
        catch(Exception e){}
    }//GEN-LAST:event_btnAnteriorAActionPerformed

    private void btnSiguienteAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteAActionPerformed
        try{
            if(r_ar.isLast()){
            }else{
                r_ar.next();

                CargarDatosArticulos();

            }
        }catch(Exception e){}
    }//GEN-LAST:event_btnSiguienteAActionPerformed

    private void btnPrimeroAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroAActionPerformed
        try{
            r_ar.first();
            CargarDatosArticulos();
        }catch(Exception e){
        }
    }//GEN-LAST:event_btnPrimeroAActionPerformed

    private void txtPrecioCostoAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioCostoAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioCostoAActionPerformed

    private void btnConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfiguracionActionPerformed
        pnlArticulos.setVisible(false);
        pnlProveedores.setVisible(false);
        pnlRubros.setVisible(false);
        pnlAumentos.setVisible(false);
        lblVentana.setText("CONFIGURACION");
    }//GEN-LAST:event_btnConfiguracionActionPerformed

    private void btnPrimeroPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroPActionPerformed
    try{
        r_pr.first();
        CargarDatosProveedores();
    }catch(Exception e){
    }    
    }//GEN-LAST:event_btnPrimeroPActionPerformed

    private void btnSiguientePActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguientePActionPerformed
    try{
        if(r_pr.isLast()){
        }else{
              r_pr.next();
              
        CargarDatosProveedores();
                    
        }
    }catch(Exception e){}
    }//GEN-LAST:event_btnSiguientePActionPerformed

    private void btnAnteriorPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorPActionPerformed
    try{
        if(r_pr.isFirst()){
        }else{
            r_pr.previous();
            
        CargarDatosProveedores();
        }
    }
    catch(Exception e){}
    }//GEN-LAST:event_btnAnteriorPActionPerformed

    private void btnUltimoPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoPActionPerformed
    try{
            r_pr.last();
            
        CargarDatosProveedores();
   }    catch(Exception e){
   }
    }//GEN-LAST:event_btnUltimoPActionPerformed

    private void txtDireccionPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionPActionPerformed

    }//GEN-LAST:event_txtDireccionPActionPerformed

    private void btnAltaPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaPActionPerformed
     try{
        btnEliminarP.setEnabled(false);
        btnModificarP.setEnabled(false);
        btnAltaP.setEnabled(false);
        btnBuscarP.setEnabled(false);
        
        btnAceptarP.setEnabled(true);
        btnCancelarP.setEnabled(true);
        btnBuscarP.setEnabled(true);
        
        btnPrimeroP.setEnabled(false);
        btnAnteriorP.setEnabled(false);
        btnSiguienteP.setEnabled(false);
        btnUltimoP.setEnabled(false);
        
        txtNombreP.setEnabled(true);
        txtDireccionP.setEnabled(true);
        txtCuitP.setEnabled(true);
        txtEmailP.setEnabled(true);
        txtCelularP.setEnabled(true);
        cboLocalidadP.setEnabled(true);
        
        estado = "agregar";
        txtNombreP.setText("");
        txtDireccionP.setText("");
        txtCuitP.setText("");
        txtEmailP.setText("");
        txtCelularP.setText("");
        r_pr.last();
        int a = r_pr.getInt("id_proveedor")+1;
        txtIdP.setText(Integer.toString(a));     
        }catch(Exception e){
            System.out.println(e);
         txtIdP.setText("1");
     }
        
        cboLocalidadP.setSelectedIndex(0);
    
    }//GEN-LAST:event_btnAltaPActionPerformed

    private void btnModificarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarPActionPerformed
     try{
        btnAceptarP.setEnabled(true);
        btnCancelarP.setEnabled(true);
        txtNombreP.setEnabled(true);
        txtDireccionP.setEnabled(true);
        cboLocalidadP.setEnabled(true);
        txtCuitP.setEnabled(true);
        txtEmailP.setEnabled(true);
        txtCelularP.setEnabled(true);
        btnPrimeroP.setEnabled(false);
        btnAnteriorP.setEnabled(false);
        btnSiguienteP.setEnabled(false);
        btnUltimoP.setEnabled(false);
        btnEliminarP.setEnabled(false);
        btnBuscarP.setEnabled(false);
        btnAltaP.setEnabled(false);
        btnModificarP.setEnabled(false);
        btnBuscarP.setEnabled(true);
        estado= "Modificar";
        nom= txtNombreP.getText();
        dir= txtDireccionP.getText();
        pr= cboLocalidadP.getSelectedIndex();
        cu= txtCuitP.getText();
        em= txtEmailP.getText();
        cel= txtCelularP.getText();
    }catch (Exception e){}
    
    }//GEN-LAST:event_btnModificarPActionPerformed

    private void btnEliminarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPActionPerformed
try{
        int resp;
        resp= JOptionPane.showConfirmDialog(null, "Realmente desea borrar el proveedor");
        if(resp==0){
            String Consulta= "DELETE FROM tiendatorresi.proveedor WHERE proveedor.id_proveedor = "+txtIdP.getText();
            sentencia_pr.executeUpdate(Consulta);
            CargarConsultaProveedores();
            r_pr.last();
            CargarDatosProveedores();
        }else{
            JOptionPane.showMessageDialog(null, "Cancelado por el ususario");
        }
    }catch(Exception e){
        BuscarUnRegistroProveedores(txtIdP.getText());
        CargarDatosProveedores();
            JOptionPane.showMessageDialog(null, "No se puede borrar la Provincia");
            }
    }//GEN-LAST:event_btnEliminarPActionPerformed

    private void btnCerrarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarPActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarPActionPerformed

    private void btnAceptarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarPActionPerformed
           try{
            String Consulta;
            int aa = 0;
        if (estado == "agregar"){
            if(txtNombreP.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Debe completar un nombre");
                txtNombreP.requestFocus();
            }else{
                while (aa!= cboLocalidadP.getSelectedIndex()){
                    aa++;
                }
            Consulta = "INSERT INTO tiendatorresi.proveedor VALUES ("+ txtIdP.getText()+ ",'"+ txtNombreP.getText()+ "','" +txtCuitP.getText() + "','" + txtDireccionP.getText()+"','"+ txtEmailP.getText()+ "','"+ txtCelularP.getText()+ "'," + prov_pr[aa] +" )";
            System.out.println(Consulta);
            sentencia_pr.executeUpdate(Consulta);
            System.out.println("1");
            CargarConsultaProveedores();
            r_pr.last();
            CargarDatosProveedores();
            btnAceptarP.setEnabled(false);
            btnCancelarP.setEnabled(false);
            
            txtIdP.setEnabled(false);
            txtNombreP.setEnabled(false);
            txtDireccionP.setEnabled(false);
            cboLocalidadP.setEnabled(false);
            txtCuitP.setEnabled(false);
            txtEmailP.setEnabled(false);
            txtCelularP.setEnabled(false);
            
            btnPrimeroP.setEnabled(true);
            btnAnteriorP.setEnabled(true);
            btnSiguienteP.setEnabled(true);
            btnUltimoP.setEnabled(true);
            btnBuscarP.setEnabled(false);
            
            btnAltaP.setEnabled(true);
            btnEliminarP.setEnabled(true);
            btnModificarP.setEnabled(true);
            btnBuscarP.setEnabled(true); 

            }   
        }else{
            if (estado == "Modificar"){
                if(txtNombreP.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Debe completar un nombre");
                    txtNombreP.requestFocus();
                }
                else{
                    while (aa != cboLocalidadP.getSelectedIndex()){
                        aa++;
                    }
                    Consulta= "UPDATE tiendatorresi.proveedor SET nombre_proveedor= '" + txtNombreP.getText()+  "',cuit_proveedor= '"+ txtCuitP.getText() + "',direccion_proveedor= '" + txtDireccionP.getText() + "',email_proveedor= '"+ txtEmailP.getText()+ "', celular_proveedor= '"+ txtCelularP.getText() +"', localidad_id_localidad = "+ prov_pr[aa] + " WHERE id_proveedor =" + txtIdP.getText();
                    System.out.println(Consulta);
                    sentencia_pr.executeUpdate(Consulta);
                    //r = sentencia.executeQuery("SELECT localidad.id_localidades, localidad.nombre_localidad, localidad.id_provincia, provincia.Nombre FROM tiendatorresi.localidad, tienatorresi.provincia WHERE tiendatorresi.localidad.id_provincia = tiendatorresi.provincia.id_provincia ORDER BY localidad.id_localidad");
                    r_pr = sentencia_pr.executeQuery("SELECT proveedor.id_proveedor, proveedor.nombre_proveedor, proveedor.cuit_proveedor, proveedor.direccion_proveedor, proveedor.email_proveedor, proveedor.celular_proveedor,proveedor.localidad_id_localidad,localidad.nombre_localidad FROM tiendatorresi.localidad, tiendatorresi.proveedor WHERE tiendatorresi.proveedor.localidad_id_localidad = tiendatorresi.localidad.id_localidad ORDER BY proveedor.id_proveedor");
                    BuscarUnRegistroProveedores(txtIdP.getText());
                    CargarDatosProveedores();
                    btnAceptarP.setEnabled(false);
                    btnCancelarP.setEnabled(false);
            
                    txtIdP.setEnabled(false);
                    txtNombreP.setEnabled(false);
                    txtDireccionP.setEnabled(false);
                    cboLocalidadP.setEnabled(false);
                    txtCuitP.setEnabled(false);
                    txtEmailP.setEnabled(false);
                    txtCelularP.setEnabled(false);
            
                    btnPrimeroP.setEnabled(true);
                    btnAnteriorP.setEnabled(true);
                    btnSiguienteP.setEnabled(true);
                    btnUltimoP.setEnabled(true);
                    btnBuscarP.setEnabled(false);
            
                    btnAltaP.setEnabled(true);
                    btnEliminarP.setEnabled(true);
                    btnModificarP.setEnabled(true);
                    btnBuscarP.setEnabled(true);
                }
            }
        }
        }catch(Exception e){
            System.out.println("btnAceptar:  "+e);    
                } 
    }//GEN-LAST:event_btnAceptarPActionPerformed

    private void btnCancelarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarPActionPerformed
    try{
        if(estado =="agregar"){
            r_pr.last();
            CargarDatosProveedores();
        }else{
            if(estado=="Modificar"){
                txtNombreP.setText(nom);
                cboLocalidadP.setSelectedIndex(pr);
                txtDireccionP.setText(dir);
                txtCuitP.setText(cu);
                txtEmailP.setText(em);
                txtCelularP.setText(cel);
            
                
            }
            
        }
                btnAceptarP.setEnabled(false);
                btnCancelarP.setEnabled(false);
            
                txtIdP.setEnabled(false);
                txtNombreP.setEnabled(false);
                txtDireccionP.setEnabled(false);
                cboLocalidadP.setEnabled(false);
                txtCuitP.setEnabled(false);
                txtEmailP.setEnabled(false);
                txtCelularP.setEnabled(false);
            
                btnPrimeroP.setEnabled(true);
                btnAnteriorP.setEnabled(true);
                btnSiguienteP.setEnabled(true);
                btnUltimoP.setEnabled(true);
                btnBuscarP.setEnabled(false);
            
                btnAltaP.setEnabled(true);
                btnEliminarP.setEnabled(true);
                btnModificarP.setEnabled(true);
                btnBuscarP.setEnabled(true);
    }catch(Exception e){}
    }//GEN-LAST:event_btnCancelarPActionPerformed

    private void cboLocalidadPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLocalidadPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLocalidadPActionPerformed

    private void txtCuitPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCuitPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCuitPActionPerformed

    private void txtCuitPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCuitPKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCuitPKeyPressed

    private void btnBuscarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPActionPerformed
        BuscarProveedores abrirBP = new BuscarProveedores();
        abrirBP.setVisible(true);
    }//GEN-LAST:event_btnBuscarPActionPerformed

    private void btnAnteriorRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorRActionPerformed
        try{
            if(r_ru.isFirst()) {
            } else{
                r_ru.previous();
                txtIdR.setText (r_ru.getString("id_rubro"));
                txtNombreR.setText (r_ru.getString("nombre_rubro"));
            }
        } catch (Exception e) {}
    }//GEN-LAST:event_btnAnteriorRActionPerformed

    private void btnSiguienteRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteRActionPerformed
        try{
            if(r_ru.isLast()) {
            } else {
                r_ru.next ();
                txtIdR.setText (r_ru.getString ("id_rubro"));
                txtNombreR.setText (r_ru.getString("nombre_rubro"));
            }
        }catch (Exception e) {}
    }//GEN-LAST:event_btnSiguienteRActionPerformed

    private void btnUltimoRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoRActionPerformed
        try{
            r_ru.last();
            txtIdR.setText(r_ru.getString("id_rubro"));
            txtNombreR.setText(r_ru.getString("nombre_rubro"));
        } catch (Exception e) { }
    }//GEN-LAST:event_btnUltimoRActionPerformed

    private void btnModificarRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarRActionPerformed
        mo=txtNombreR.getText();

        try{
            btnAceptarR.setEnabled (true);
            btnCancelarR.setEnabled(true);
            txtNombreR.setEnabled(true);
            btnUltimoR.setEnabled(true);
            btnSiguienteR.setEnabled(true);
            btnAnteriorR.setEnabled(true);
            btnPrimeroR.setEnabled(true);
            btnBajaR.setEnabled(true);
            btnAltaR.setEnabled(true);
            estado = "Modificar";
            mo = txtNombreR.getText();
        }catch (Exception e) {}

    }//GEN-LAST:event_btnModificarRActionPerformed

    private void btnPrimeroRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroRActionPerformed
        try{
            r_ru.first();
            CargarDatosRubros();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnPrimeroRActionPerformed

    private void btnBuscarRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarRActionPerformed
        BuscarRubros abrirBR = new BuscarRubros();
        abrirBR.setVisible(true);
    }//GEN-LAST:event_btnBuscarRActionPerformed

    private void btnAceptarRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarRActionPerformed
        try{
            String Consulta;
            int aa = 0;
            if (estado == "agregar"){
                if(txtNombreR.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Debe completar un nombre");
                    txtNombreR.requestFocus();
                }else{

                    Consulta = "INSERT INTO tiendatorresi.rubro VALUES (" + txtIdR.getText() + " , '" + txtNombreR.getText() + "')";
                    System.out.println(Consulta);
                    sentencia_ru.executeUpdate(Consulta);
                    CargarConsultaRubros();

                    btnAceptarR.setEnabled(false);
                    btnCancelarR.setEnabled(false);

                    txtIdR.setEnabled(false);
                    txtNombreR.setEnabled(false);

                    btnPrimeroR.setEnabled(true);
                    btnAnteriorR.setEnabled(true);
                    btnSiguienteR.setEnabled(true);
                    btnUltimoR.setEnabled(true);
                    btnBuscarR.setEnabled(false);

                    btnAltaR.setEnabled(true);
                    btnBajaR.setEnabled(true);
                    btnModificarR.setEnabled(true);
                    btnBuscarR.setEnabled(true);

                }
            }else{
                if (estado == "Modificar"){
                    if(txtNombreR.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Debe completar un nombre");
                        txtNombreR.requestFocus();
                    }
                    else{
                        Consulta= "UPDATE tiendatorresi.rubro SET nombre_rubro= '" + txtNombreR.getText()+ "' WHERE id_rubro =" + txtIdR.getText();
                        System.out.println(Consulta);
                        sentencia_ru.executeUpdate(Consulta);
                        BuscarUnRegistroRubros(txtIdR.getText());
                        CargarDatosRubros();
                        btnAceptarR.setEnabled(false);
                        btnCancelarR.setEnabled(false);

                        txtIdR.setEnabled(false);
                        txtNombreR.setEnabled(false);

                        btnPrimeroR.setEnabled(true);
                        btnAnteriorR.setEnabled(true);
                        btnSiguienteR.setEnabled(true);
                        btnUltimoR.setEnabled(true);
                        btnBuscarR.setEnabled(false);

                        btnAltaR.setEnabled(true);
                        btnBajaR.setEnabled(true);
                        btnModificarR.setEnabled(true);
                        btnBuscarR.setEnabled(true);
                    }
                }
            }
        }catch(Exception e){
            System.out.println("btnAceptar:  "+e);
        }
    }//GEN-LAST:event_btnAceptarRActionPerformed

    private void btnCancelarRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarRActionPerformed
        try {
            if (estado =="agregar"){ //correjir
                r_ru.last ();
                CargarDatosRubros();
                txtNombreR.setText (r_ru.getString ("Id_rubro"));
                txtNombreR.setText(r_ru.getString("nombre_rubro"));
            } else if (estado =="Modificar"){
                txtNombreR.setText(mo);
            }

            txtNombreR.setEnabled (false);
            btnCancelarR.setEnabled (false);
            btnAceptarR.setEnabled (false);
            btnPrimeroR.setEnabled(true);
            btnAnteriorR.setEnabled(true);
            btnSiguienteR.setEnabled(true);
            btnUltimoR.setEnabled(true);
            btnBajaR.setEnabled(true);
            btnAltaR.setEnabled(true);
            btnModificarR.setEnabled (true);
        }catch(Exception e){}

    }//GEN-LAST:event_btnCancelarRActionPerformed

    private void btnAltaRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaRActionPerformed
        try{
            btnModificarR.setEnabled (false);
            btnPrimeroR.setEnabled(false);
            btnAnteriorR.setEnabled(false);
            btnSiguienteR.setEnabled(false);
            btnUltimoR.setEnabled(false);
            btnBajaR.setEnabled(false);
            btnBuscarR.setEnabled(false);
            txtNombreR.setEnabled (true);
            btnAltaR.setEnabled(false);
            btnAceptarR.setEnabled (true);
            btnCancelarR.setEnabled(true);
            estado = "agregar";
            txtNombreR.setText("");
            r_ru.last ();
            int a = r_ru.getInt ("Id_rubro") +1;
            txtIdR.setText (Integer.toString (a));
        }catch (Exception e) {
            txtIdR.setText("1");
        }

    }//GEN-LAST:event_btnAltaRActionPerformed

    private void btnBajaRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaRActionPerformed
        try{
            int resp;
            resp= JOptionPane.showConfirmDialog(null, "Realmente desea borrar el rubro");
            if(resp==0){
                String Consulta= "DELETE FROM tiendatorresi.rubro WHERE rubro.id_rubro = "+txtIdR.getText();
                System.out.println(Consulta);
                sentencia_ru.executeUpdate(Consulta);
                CargarConsultaRubros();
                r_ru.last();
                CargarDatosRubros();
            }else{
                JOptionPane.showMessageDialog(null, "Cancelado por el ususario");
            }
        }catch(Exception e){
            BuscarUnRegistroRubros(txtIdR.getText());
            CargarDatosRubros();
            JOptionPane.showMessageDialog(null, "No se puede borrar el rubro");
        }

    }//GEN-LAST:event_btnBajaRActionPerformed

    private void btnCerrarRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarRActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarRActionPerformed

    private void txtIdRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdRActionPerformed

    private void txtNombreRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreRActionPerformed

    private void cboSeleccionarRubroAuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSeleccionarRubroAuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSeleccionarRubroAuActionPerformed

    private void txtAumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAumentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAumentoActionPerformed

    private void btnAñadirAuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirAuActionPerformed
        String consulta;
        int aa = 0;
        Calendar cal = Calendar.getInstance();
        boolean s = SoloNum(txtAumento.getText());
        try{
            if(txtAumento.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"El aumento no puede estar en blanco");
                txtAumento.requestFocus();
            }
            else if(s == false){
                JOptionPane.showMessageDialog(null,"Debe escribir un numero entero");
                txtAumento.requestFocus();

            }else{
                int resp = 0;
                resp = JOptionPane.showConfirmDialog(null,"Se aumentará el precio de los artículos seleccionados un "+txtAumento.getText()+"%");
                if (resp == 0 ){
                    while (aa != cboSeleccionarRubroAu.getSelectedIndex()){
                        aa++;
                    }
                    if(estado == "Rubro"){
                        ResRubPro = sentencia_au.executeQuery("SELECT * FROM tiendatorresi.articulo WHERE articulo.rubro_id_rubro = "+rubpro[aa]);
                    }
                    else if(estado == "Prov"){
                        ResRubPro = sentencia_au.executeQuery("SELECT * FROM tiendatorresi.articulo WHERE articulo.proveedor_id_proveedor = "+rubpro[aa]);
                    }
                    while(ResRubPro.next()){
                        String ID = ResRubPro.getString("id_articulo");
                        System.out.println(ID);
                        int año = cal.getTime().getYear()+1900;
                        int mes = cal.getTime().getMonth()+1;
                        int dia = cal.getTime().getDate();
                        String fec = año + "-" + mes + "-" + dia;
                        System.out.println(fec);
                        String pre = ResRubPro.getString("precio_venta");
                        System.out.println(pre);
                        consulta = "INSERT INTO tiendatorresi.historico_precio_articulo VALUES ("+ID+",'"+fec+"',"+pre+")";
                        System.out.println(consulta);
                        sentencia_au1.executeUpdate(consulta);
                        consulta = "UPDATE tiendatorresi.articulo SET articulo.precio_venta = articulo.precio_venta+(articulo.precio_venta*"+txtAumento.getText()+"/100), articulo.fecha_actualizacion = '"+fec+"' WHERE id_articulo = "+ID+"";
                        System.out.println(consulta);
                        sentencia_au1.executeUpdate(consulta);
                    }
                    JOptionPane.showMessageDialog(null,"Aumentado correctamente");
                }else{
                    JOptionPane.showMessageDialog(null,"Cancelado correctamente");
                }
            }
        }catch(Exception e){
            try{
                int resp1 = 0;
                resp1 = JOptionPane.showConfirmDialog(null,"Ya ha aumentado el precio hoy,¿Desea actualizarlo?");
                if (resp1 == 0 ){
                    while (aa != cboSeleccionarRubroAu.getSelectedIndex()){
                        aa++;
                    }
                    if(estado == "Rubro"){
                        ResRubPro = sentencia_au.executeQuery("SELECT * FROM tiendatorresi.articulo WHERE articulo.rubro_id_rubro = "+rubpro[aa]);
                    }
                    else if(estado == "Prov"){
                        ResRubPro = sentencia_au.executeQuery("SELECT * FROM tiendatorresi.articulo WHERE articulo.proveedor_id_proveedor = "+rubpro[aa]);
                    }
                    while(ResRubPro.next()){
                        String ID = ResRubPro.getString("id_articulo");
                        System.out.println(ID);
                        int año = cal.getTime().getYear()+1900;
                        int mes = cal.getTime().getMonth()+1;
                        int dia = cal.getTime().getDate();
                        String fec = año + "-" + mes + "-" + dia;
                        System.out.println(fec);
                        String pre = ResRubPro.getString("precio_venta");
                        System.out.println(pre);
                        consulta = "UPDATE tiendatorresi.historico_precio_articulo SET historico_precio_articulo.precio_venta= " + pre + " WHERE historico_precio_articulo.fecha = '"+fec+"' and historico_precio_articulo.id_articulo = "+ID;
                        System.out.println(consulta);
                        sentencia_au1.executeUpdate(consulta);
                        consulta = "UPDATE tiendatorresi.articulo SET articulo.precio_venta = articulo.precio_venta+(articulo.precio_venta*"+txtAumento.getText()+"/100), articulo.fecha_actualizacion = '"+fec+"' WHERE id_articulo = "+ID+"";
                        System.out.println(consulta);
                        sentencia_au1.executeUpdate(consulta);
                    }
                    JOptionPane.showMessageDialog(null,"Aumentado correctamente");
                }else{
                    JOptionPane.showMessageDialog(null,"Cancelado correctamente");
                }

                System.out.println(e);
            }catch(Exception ex){
            }

        }
    }//GEN-LAST:event_btnAñadirAuActionPerformed

    private void btnCerrarAuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarAuActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCerrarAuActionPerformed

    private void btnAumRubroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAumRubroActionPerformed
        try{
            btnAumProv.setEnabled(true);
            btnAumRubro.setEnabled(false);
            lblRubroProveedorAu.setText("Rubro");
            txtAumento.requestFocus(true);
            CargarComboAumentoRubro();
            CargarConsultaAumentoRubro();
            estado = "Rubro";
        }catch(Exception e){
        }
    }//GEN-LAST:event_btnAumRubroActionPerformed

    private void btnAumProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAumProvActionPerformed
        try{
            btnAumProv.setEnabled(false);
            btnAumRubro.setEnabled(true);
            lblRubroProveedorAu.setText("Provedor");
            txtAumento.requestFocus(true);
            CargarComboAumentoProv();
            CargarConsultaAumentoProv();
            estado = "Prov";
        }catch(Exception e){
        }
    }//GEN-LAST:event_btnAumProvActionPerformed

    private void txtIdPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdPActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
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
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Inicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton btnAceptarA;
    private static javax.swing.JButton btnAceptarP;
    public static javax.swing.JButton btnAceptarR;
    private static javax.swing.JButton btnAltaA;
    private static javax.swing.JButton btnAltaP;
    public static javax.swing.JButton btnAltaR;
    private static javax.swing.JButton btnAnteriorA;
    private static javax.swing.JButton btnAnteriorP;
    public static javax.swing.JButton btnAnteriorR;
    private javax.swing.JButton btnArticulos;
    private javax.swing.JButton btnAumProv;
    private javax.swing.JButton btnAumRubro;
    private javax.swing.JButton btnAumentos;
    private javax.swing.JButton btnAñadirAu;
    private static javax.swing.JButton btnBajaA;
    public static javax.swing.JButton btnBajaR;
    private static javax.swing.JButton btnBuscar1A;
    private static javax.swing.JButton btnBuscarP;
    public static javax.swing.JButton btnBuscarR;
    private static javax.swing.JButton btnCancelarA;
    private static javax.swing.JButton btnCancelarP;
    public static javax.swing.JButton btnCancelarR;
    private static javax.swing.JButton btnCerrarA;
    private javax.swing.JButton btnCerrarAu;
    private static javax.swing.JButton btnCerrarP;
    private javax.swing.JButton btnCerrarR;
    private javax.swing.JButton btnConfiguracion;
    private static javax.swing.JButton btnEliminarP;
    private javax.swing.JButton btnHistorial;
    private static javax.swing.JButton btnModificarA;
    private static javax.swing.JButton btnModificarP;
    public static javax.swing.JButton btnModificarR;
    private static javax.swing.JButton btnPrimeroA;
    private static javax.swing.JButton btnPrimeroP;
    public static javax.swing.JButton btnPrimeroR;
    private javax.swing.JButton btnProveedores;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnRubros;
    private static javax.swing.JButton btnSiguienteA;
    private static javax.swing.JButton btnSiguienteP;
    public static javax.swing.JButton btnSiguienteR;
    private static javax.swing.JButton btnUltimoA;
    private static javax.swing.JButton btnUltimoP;
    public static javax.swing.JButton btnUltimoR;
    public static javax.swing.JComboBox cboLocalidadP;
    public static javax.swing.JComboBox cboProveedorA;
    public static javax.swing.JComboBox cboRubroA;
    private static javax.swing.JComboBox cboSeleccionarRubroAu;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblAumento;
    private static javax.swing.JLabel lblCelularP;
    private static javax.swing.JLabel lblCodigoA;
    private static javax.swing.JLabel lblCuitP;
    private static javax.swing.JLabel lblDireccionP;
    private static javax.swing.JLabel lblEmailP;
    private static javax.swing.JLabel lblFechaActualizacionA;
    private static javax.swing.JLabel lblIdA;
    private static javax.swing.JLabel lblIdP;
    public static javax.swing.JLabel lblIdR;
    private static javax.swing.JLabel lblLocalidadP;
    private static javax.swing.JLabel lblNombreA;
    private static javax.swing.JLabel lblNombreP;
    public static javax.swing.JLabel lblNombreR;
    private static javax.swing.JLabel lblPrecioCostoA;
    private static javax.swing.JLabel lblPrecioVentaA;
    private javax.swing.JLabel lblProciento;
    private static javax.swing.JLabel lblProveedorA;
    private static javax.swing.JLabel lblRubroA;
    private javax.swing.JLabel lblRubroProveedorAu;
    private static javax.swing.JLabel lblSignoPrecioA;
    private static javax.swing.JLabel lblSignoPrecioA1;
    private javax.swing.JLabel lblVentana;
    private static javax.swing.JPanel pnlArticulos;
    public static javax.swing.JPanel pnlAumentos;
    private javax.swing.JPanel pnlPorcentajeAu;
    private static javax.swing.JPanel pnlPrecioCostoA;
    private static javax.swing.JPanel pnlProveedores;
    private javax.swing.JPanel pnlRubros;
    private javax.swing.JPanel pnlSeleccion;
    private javax.swing.JPanel pnlTiendaTorresi;
    private javax.swing.JPanel pnlVentana;
    private javax.swing.JTextField txtAumento;
    public static javax.swing.JTextField txtCelularP;
    public static javax.swing.JTextField txtCodigoA;
    public static javax.swing.JTextField txtCuitP;
    public static javax.swing.JTextField txtDireccionP;
    public static javax.swing.JTextField txtEmailP;
    public static javax.swing.JTextField txtFechaActualizacionA;
    public static javax.swing.JTextField txtIdA;
    public static javax.swing.JTextField txtIdP;
    public static javax.swing.JTextField txtIdR;
    public static javax.swing.JTextField txtNombreA;
    public static javax.swing.JTextField txtNombreP;
    public static javax.swing.JTextField txtNombreR;
    public static javax.swing.JTextField txtPrecioCostoA;
    public static javax.swing.JTextField txtPrecioVentaA;
    // End of variables declaration//GEN-END:variables
}
