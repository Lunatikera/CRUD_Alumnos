package com.mycompany.alumnos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import negocio.AlumnoNegocio;
import negocio.IAlumnoNegocio;
import persistencia.AlumnoDAO;
import persistencia.ConexionBD;
import persistencia.IAlumnoDAO;
import persistencia.IConexionBD;
import presentacion.frmCRUD;

/**
 *
 * @author Rios 233537
 */
public class main {

    public static void main(String[] args) {
        
        IConexionBD conexionBD = new ConexionBD();
        IAlumnoDAO alumnoDAO = new AlumnoDAO(conexionBD);
        IAlumnoNegocio alumnoNegocio = new AlumnoNegocio(alumnoDAO);
        
        frmCRUD frmCrud = new frmCRUD(alumnoNegocio);
        frmCrud.show();
        
    }
//        try
//        {
//
//            final String SERVER = "localhost"; // Servidor de la base de datos
//
//            final String BASE_DATOS = "activistasecologicos"; // Nombre de la base de datos
//
//            final String CADENA_CONEXION = "jdbc:mysql://" + SERVER + "/" + BASE_DATOS; // URL de conexión a la base de datos
//
//            final String USUARIO = "root"; // Usuario de la base de datos
//
//            final String CONTRASEÑA = ""; // Contraseña del usuario de la base de datos
//
//// Establecer la conexión a la base de datos
//            Connection conexion = DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASEÑA);
//
//// Sentencia SQL para insertar un nuevo alumno en la tabia alumnos
//            String sentenciasql = "INSERT INTO alumnos (nombre, apellidoPeterno, apellidoMaterno) VALUES (?, ?, ?);"; // Freparar la sentencia SQL, permitiendo obtener las claves generadas automáticamente PreparedStatement preparedStatement conexion.prepareStatement (sentenciaSql, Statement.RETURN GENERATED_KEYS); // Establecer los valores para los parámetros de la sentencia SOL preparedStatement.setString(1, "Edgar Alonso");
//
//            PreparedStatement preparedStatement = conexion.prepareStatement(sentenciasql, Statement.RETURN_GENERATED_KEYS);
//
//            preparedStatement.setString(1, "Edgar Alonso");
//            preparedStatement.setString(2, "Panduro");
//            preparedStatement.setString(3, "Jocobi");
//
//            preparedStatement.executeUpdate();
//
//            ResultSet resultado = preparedStatement.getGeneratedKeys();
//
//// Ejecutar la sentencia SQL de inserción preparedStatement.executeUpdate();
//// Obtener las claves generadas automáticamente (por ejemplo, el ID del nuevo registro) ResultSet resultado preparedStatement.getGeneratedKeys();
//            while (resultado.next())
//            {
//                System.out.println(resultado.getInt(1));
//            }
//
//// Imprimir el ID generado para el nuevo registro System.out.println(resultado.getInt(1));
//        } catch (SQLException ex)
//        {
//            System.out.println("Ocurrio un error " + ex.getMessage());
//        }
//    }

}
