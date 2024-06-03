/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package persistencia;

import dtos.AlumnoTablaDTO;
import entidad.AlumnoEntidad;
import java.util.List;

/**
 *
 * @author Rios 233537
 */
public interface IAlumnoDAO {

    public List<AlumnoTablaDTO> buscarPaginadoAlumnosTabla(int limit, int offset) throws PersistenciaException;

    public List<AlumnoEntidad> buscarAlumnosTabla() throws PersistenciaException;

    public void agregarAlumno(AlumnoEntidad alumno) throws PersistenciaException;

    public AlumnoEntidad buscarPorIdAlumno(int id) throws PersistenciaException;

    public AlumnoEntidad eliminar(AlumnoEntidad alumno) throws PersistenciaException;

    public AlumnoEntidad editar(AlumnoEntidad alumno) throws PersistenciaException;
}
