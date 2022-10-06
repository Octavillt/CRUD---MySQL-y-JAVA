/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crud_java_mysql;


import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;   
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author octav    
 */
public class CAlumnos {
    
    int codigo;
    String nombreALumnos;
    String apellidosAlumnos;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombreALumnos() {
        return nombreALumnos;
    }

    public void setNombreALumnos(String nombreALumnos) {
        this.nombreALumnos = nombreALumnos;
    }

    public String getApellidosAlumnos() {
        return apellidosAlumnos;
    }

    public void setApellidosAlumnos(String apellidosAlumnos) {
        this.apellidosAlumnos = apellidosAlumnos;
    }
    
    public void InsertarAlumno(JTextField paramNombres, JTextField paramApellidos){
    
        setNombreALumnos(paramNombres.getText());
        setApellidosAlumnos(paramApellidos.getText());
        
        CConexion objetoConexion = new CConexion();
        
        
        String consulta ="INSERT INTO Alumnos(nombre_alumno,apellido_alumno) VALUES(?,?);";
        
        try {
            
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            
            cs.setString(1, getNombreALumnos());
            cs.setString(2, getApellidosAlumnos());
            
            cs.execute();
            
            
            JOptionPane.showMessageDialog(null, "Alumno Agregado Correctamente.");
            
            
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error al Agregar al Almumo, error: "+e.toString());
        }
    }

    public void MostrarAlumnos(JTable paramTotalAlumnos){

        CConexion objetoConexion = new CConexion();
        DefaultTableModel modelo = new DefaultTableModel();

        TableRowSorter<TableModel> ordenarTabla = new TableRowSorter<>(modelo);
        paramTotalAlumnos.setRowSorter(ordenarTabla);

        String sql = "";

        modelo.addColumn("Id Alumno");
        modelo.addColumn("Nombre(s)");
        modelo.addColumn("Apellidos");

        paramTotalAlumnos.setModel(modelo);

        sql = "SELECT * FROM Alumnos";

        String[] datos = new String[3];
        Statement st;

        try{
            st = objetoConexion.estableceConexion().createStatement();

            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);

                modelo.addRow(datos);
            }
                paramTotalAlumnos.setModel(modelo);
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, "Error al Mostrar a los Alumnos, error: "+e.toString());
        }
    }

    public void SeleccionarAlumno(JTable paramTablaAlumnos, JTextField paramId, JTextField paramNombres,
    JTextField paramApellidos){

        try{
            int fila = paramTablaAlumnos.getSelectedRow();

            if(fila >= 0){
                paramId.setText((paramTablaAlumnos.getValueAt(fila, 0).toString()));
                paramNombres.setText((paramTablaAlumnos.getValueAt(fila, 1).toString()));
                paramApellidos.setText((paramTablaAlumnos.getValueAt(fila, 2).toString()));
            }else{
                JOptionPane.showMessageDialog(null, "Fila No Seleccionada.");
            }

        }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Error de Seleccion, Error: "+ e.toString());
        }
    }
    
    public void ModificarAlumnos(JTextField paramCodigo, JTextField paramNombres, JTextField paramApellidos){
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        setNombreALumnos(paramNombres.getText());
        setApellidosAlumnos(paramApellidos.getText());

        CConexion objetoConexion = new CConexion();

        String consulta = "UPDATE alumnos SET nombre_alumno = ?, apellido_alumno = ? WHERE id_alumno = ?;";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, getNombreALumnos());
            cs.setString(2, getApellidosAlumnos());
            cs.setInt(3, getCodigo());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Modificacion Exitosa.");
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "Error al Modificar, Error: "+ e.toString());
            
        }

    }

    public void EliminarAmunos(JTextField paramCodigo){
        setCodigo(Integer.parseInt(paramCodigo.getText()));

        CConexion objetoConexion = new CConexion();

        String consulta = "DELETE FROM alumnos WHERE id_alumno = ?;";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, getCodigo());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Alumno Eliminado Correctamnte.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al Eliminar, Error: "+ e.toString());
        }
    }
}