/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dtos.AlumnoTablaDTO;
import entidad.AlumnoEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import negocio.AlumnoNegocio;

/**
 *
 * @author Rios 233537
 */
public class AlumnoDAO implements IAlumnoDAO {

    private IConexionBD conexionBD;

    private static final Logger LOGGER = Logger.getLogger(AlumnoNegocio.class.getName());

    public AlumnoDAO(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public List<AlumnoEntidad> buscarAlumnosTabla() throws PersistenciaException {
        try {
            List<AlumnoEntidad> alumnosLista = null;

            Connection conexion = this.conexionBD.crearConexion();
            String codigoSQL = "SELECT idAlumno, nombres, apellidoPaterno, apellidoMaterno, eliminado, activo FROM alumnos";
            Statement comandoSQL = conexion.createStatement();
            ResultSet resultado = comandoSQL.executeQuery(codigoSQL);
            while (resultado.next()) {
                if (alumnosLista == null) {
                    alumnosLista = new ArrayList<>();
                }
                AlumnoEntidad alumno = this.convertirAEntidad(resultado);
                alumnosLista.add(alumno);
            }
            conexion.close();
            return alumnosLista;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al buscar alumnos", ex);
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al leer la base de datos, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
        }
    }

    @Override
    public void agregarAlumno(AlumnoEntidad alumno) throws PersistenciaException {
        try {
            Connection conexion = this.conexionBD.crearConexion();
            String codigoSQL = "INSERT INTO alumnos (nombres, apellidoPaterno, apellidoMaterno, eliminado, activo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement comandoSQL = conexion.prepareStatement(codigoSQL);

            comandoSQL.setString(1, alumno.getNombres());
            comandoSQL.setString(2, alumno.getApellidoPaterno());
            comandoSQL.setString(3, alumno.getApellidoMaterno());
            comandoSQL.setBoolean(4, alumno.isEliminado());
            comandoSQL.setBoolean(5, alumno.isActivo());

            comandoSQL.executeUpdate();
            conexion.close();
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al agregar el alumno", ex);
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al leer la base de datos, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
        }
    }

    private AlumnoEntidad convertirAEntidad(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("idAlumno");
        String nombre = resultado.getString("nombres");
        String paterno = resultado.getString("apellidoPaterno");
        String materno = resultado.getString("apellidoMaterno");
        boolean eliminado = resultado.getBoolean("eliminado");
        boolean activo = resultado.getBoolean("activo");
        return new AlumnoEntidad(id, nombre, paterno, materno, eliminado, activo);
    }

    @Override
    public List<AlumnoTablaDTO> buscarPaginadoAlumnosTabla(int limit, int offset) throws PersistenciaException {
        try {
            List<AlumnoTablaDTO> alumnoLista = null;

            Connection conexion = this.conexionBD.crearConexion();
            String codigoSQL = "SELECT idAlumno, nombres, apellidoPaterno, apellidoMaterno, eliminado, activo FROM alumnos "
                    + "LIMIT " + limit + " OFFSET " + offset + ";";
            Statement comandoSQL = conexion.createStatement();
            ResultSet resultado = comandoSQL.executeQuery(codigoSQL);
            while (resultado.next()) {
                if (alumnoLista == null) {
                    alumnoLista = new ArrayList<>();
                }
                AlumnoTablaDTO alumno = this.alumnoTablaDTO(resultado);
                alumnoLista.add(alumno);
            }
            conexion.close();
            return alumnoLista;
        } catch (SQLException ex) {
            // hacer uso de Logger
            System.out.println(ex.getMessage());
            throw new PersistenciaException("Ocurrió un error al leer la base de datos, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
        }
    }

    private AlumnoTablaDTO alumnoTablaDTO(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("idAlumno");
        String nombre = resultado.getString("nombres");
        String paterno = resultado.getString("apellidoPaterno");
        String materno = resultado.getString("apellidoMaterno");
        String estatus = resultado.getBoolean("eliminado") == true ? "Eliminado" : "No Eliminado";
        return new AlumnoTablaDTO(id, nombre, paterno, materno, estatus);
    }

    private AlumnoEntidad alumnoEntidadDTO(ResultSet resultado) throws SQLException {
        int id = resultado.getInt("idAlumno");
        String nombre = resultado.getString("nombres");
        String paterno = resultado.getString("apellidoPaterno");
        String materno = resultado.getString("apellidoMaterno");
        boolean eliminado = resultado.getBoolean("eliminado");
        boolean activo = resultado.getBoolean("activo");
        return new AlumnoEntidad(id, nombre, paterno, materno, eliminado, activo);
    }

    @Override
    public AlumnoEntidad buscarPorIdAlumno(int id) throws PersistenciaException {
        try {
            AlumnoEntidad alumnoEntidad = null;
            Connection conexion = this.conexionBD.crearConexion();
            String codigoSQL = "SELECT idAlumno, nombres, apellidoPaterno, apellidoMaterno, activo, eliminado FROM alumnos "
                    + "WHERE idAlumno=" + id + ";";
            Statement comandoSQL = conexion.createStatement();
            ResultSet resultado = comandoSQL.executeQuery(codigoSQL);
            if (resultado.next()) {
                alumnoEntidad = this.alumnoEntidadDTO(resultado);
            }
            return alumnoEntidad;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new PersistenciaException("Ocurrió un error al leer la base de datos, inténtelo de nuevo y si el error persiste comuníquese con el encargado del sistema.");
        }
    }

    @Override
    public AlumnoEntidad editar(AlumnoEntidad alumno) throws PersistenciaException {
        try {
            AlumnoEntidad alumnoExistente = this.buscarPorIdAlumno(alumno.getIdAlumno());
            if (alumnoExistente == null) {
                throw new PersistenciaException("No existe el alumno a actualizar");
            }
            try ( Connection conexion = this.conexionBD.crearConexion()) {
                String codigoSQL = "UPDATE alumnos SET nombres=?, apellidoPaterno=?, apellidoMaterno=?, activo=?, eliminado=? WHERE IdALumno=?";
                try ( PreparedStatement preparedStatement = conexion.prepareStatement(codigoSQL)) {

                    preparedStatement.setString(1, alumno.getNombres());
                    preparedStatement.setString(2, alumno.getApellidoPaterno());
                    preparedStatement.setString(3, alumno.getApellidoMaterno());
                    preparedStatement.setBoolean(4, alumno.isActivo());
                    preparedStatement.setBoolean(5, alumno.isEliminado());
                    preparedStatement.setInt(6, alumno.getIdAlumno());

                    Statement comandoSQL = conexion.createStatement();
                    int filasAfectadas = preparedStatement.executeUpdate();
                    if (filasAfectadas > 0) {
                        // Actualiza la entidad con los nuevos datos
                        alumnoExistente.setNombres(alumno.getNombres());
                        alumnoExistente.setApellidoPaterno(alumno.getApellidoPaterno());
                        alumnoExistente.setApellidoMaterno(alumno.getApellidoMaterno());
                    } else {
                        throw new PersistenciaException("No se pudo actualizar el cliente");
                    }
                }

                return alumnoExistente;
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo actualizar el cliente");
        }

    }

    @Override
    public AlumnoEntidad eliminar(AlumnoEntidad alumno) throws PersistenciaException {
        try {
            // Verifica si el cliente a eliminar existe
            if (alumno == null) {
                throw new PersistenciaException("No existe el alumno a eliminar");
            }

            // Utiliza try-with-resources para garantizar el cierre de la conexión
            try ( Connection conexion = this.conexionBD.crearConexion()) {
                // Utiliza PreparedStatement para evitar la inyección de SQL
                String codigoSQL = "DELETE FROM alumnos WHERE idAlumno=?";
                try ( PreparedStatement preparedStatement = conexion.prepareStatement(codigoSQL)) {
                    // Asigna el valor al parámetro
                    preparedStatement.setInt(1, alumno.getIdAlumno());

                    // Ejecuta la eliminación
                    int filasAfectadas = preparedStatement.executeUpdate();

                    if (filasAfectadas > 0) {
                        // Eliminación exitosa
                        return alumno;
                    } else {
                        throw new PersistenciaException("No se pudo eliminar el alumno");
                    }
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Error en la base de datos al eliminar el alumno");
        }
    }

}
