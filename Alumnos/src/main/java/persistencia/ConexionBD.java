/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Rios 233537
 */
public class ConexionBD implements IConexionBD {

    private static final String SERVER = "localhost";
    private static final String BASE_DATOS = "BDCRUD";
    private static final String CADENA_CONEXION = "jdbc:mysql://" + SERVER + "/" + BASE_DATOS;
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "Soleyne1804";

    @Override
    public Connection crearConexion() throws SQLException {
        Connection conexion = DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASENA);
        return conexion;
    }
}
