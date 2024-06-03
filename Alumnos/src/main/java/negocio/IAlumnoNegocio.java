/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio;

import dtos.AlumnoDTO;
import dtos.AlumnoTablaDTO;
import entidad.AlumnoEntidad;
import java.util.List;

/**
 *
 * @author Rios 233537
 */
public interface IAlumnoNegocio {

    public List<AlumnoTablaDTO> buscarAlumnosTabla() throws NegocioException;

    public List<AlumnoTablaDTO> buscarPaginadosAlumnosTabla(int limit, int offset) throws NegocioException;

    public void agregarAlumno(AlumnoTablaDTO alumno) throws NegocioException;

    public AlumnoDTO buscarPorIdAlumno(int id) throws NegocioException;

    public AlumnoDTO editar(AlumnoDTO alumno) throws NegocioException;

    public AlumnoDTO eliminar(AlumnoDTO alumno) throws NegocioException;

}
