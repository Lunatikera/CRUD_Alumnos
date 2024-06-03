/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import convertidores.AlumnoConvertidor;
import dtos.AlumnoDTO;
import dtos.AlumnoTablaDTO;
import entidad.AlumnoEntidad;
import java.util.ArrayList;
import java.util.List;
import persistencia.IAlumnoDAO;
import persistencia.PersistenciaException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilerias.Utilidades;

/**
 *
 * @author Rios 233537
 */
public class AlumnoNegocio implements IAlumnoNegocio {

    private IAlumnoDAO alumnoDAO;

    private static final Logger LOGGER = Logger.getLogger(AlumnoNegocio.class.getName());

    public AlumnoNegocio(IAlumnoDAO alumnoDAO) {
        this.alumnoDAO = alumnoDAO;
    }

    private List<AlumnoTablaDTO> convertirAlumnoTablaDTO(List<AlumnoEntidad> alumnos) throws NegocioException {
        if (alumnos == null) {
            throw new NegocioException("No se pudieron obtener los alumnos");
        }

        List<AlumnoTablaDTO> alumnosDTO = new ArrayList<>();
        for (AlumnoEntidad alumno : alumnos) {
            AlumnoTablaDTO dto = new AlumnoTablaDTO();
            dto.setIdAlumno(alumno.getIdAlumno());
            dto.setNombres(alumno.getNombres());
            dto.setApellidoPaterno(alumno.getApellidoPaterno());
            dto.setApellidoMaterno(alumno.getApellidoMaterno());
            dto.setEstatus(alumno.isActivo() == true ? "Activo" : "Inactivo");
            alumnosDTO.add(dto);
        }
        return alumnosDTO;
    }

    @Override
    public List<AlumnoTablaDTO> buscarAlumnosTabla() throws NegocioException {
        try {
            List<AlumnoEntidad> alumnos = this.alumnoDAO.buscarAlumnosTabla();
            return this.convertirAlumnoTablaDTO(alumnos);
        } catch (PersistenciaException ex) {
            LOGGER.log(Level.SEVERE, "Error al buscar alumnos", ex);
            System.out.println(ex.getMessage());
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public void agregarAlumno(AlumnoTablaDTO alumno) throws NegocioException {
        try {
            AlumnoEntidad entidad = new AlumnoEntidad(0,
                    alumno.getNombres(),
                    alumno.getApellidoPaterno(),
                    alumno.getApellidoMaterno(),
                    false,
                    "Activo".equalsIgnoreCase(alumno.getEstatus())
            );

            this.alumnoDAO.agregarAlumno(entidad);
        } catch (PersistenciaException ex) {
            LOGGER.log(Level.SEVERE, "Error al agregar el alumno", ex);
            System.out.println(ex.getMessage());
            throw new NegocioException(ex.getMessage());
        }
    }

    @Override
    public List<AlumnoTablaDTO> buscarPaginadosAlumnosTabla(int limit, int pagina) throws NegocioException {
        try {
            if (this.esNumeroNegativo(limit)) {
                throw new NegocioException("El parametro limite no puede ser negativo");
            }
            if (this.esNumeroNegativo(pagina)) {
                throw new NegocioException("El parametro pagina no puede ser negativo");
            }

            int offset = this.obtenerOFFSETMySQL(limit, pagina);
            List<AlumnoTablaDTO> clientesLista = this.alumnoDAO.buscarPaginadoAlumnosTabla(limit, offset);
            if (clientesLista == null && pagina == 1) {
                throw new NegocioException("No existen alumnos registrados");
            }

            return clientesLista;
        } catch (PersistenciaException ex) {
            // hacer uso de Logger
            System.out.println(ex.getMessage());
            throw new NegocioException(ex.getMessage());
        }
    }

    private boolean esNumeroNegativo(int numero) {
        return numero < 0;
    }

    private int obtenerOFFSETMySQL(int limit, int pagina) {
        return new Utilidades().RegresarOFFSETMySQL(limit, pagina);
    }

    @Override
    public AlumnoDTO buscarPorIdAlumno(int id) throws NegocioException {
        if (this.esNumeroNegativo(id)) {
            throw new NegocioException("No ingrese numeros Negativos");
        }
        try {
            return AlumnoConvertidor.convertirAlumnoAAlumnoDTO(this.alumnoDAO.buscarPorIdAlumno(id));
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @Override
    public AlumnoDTO editar(AlumnoDTO alumno) throws NegocioException {
        try {
            AlumnoEntidad alumnoEntidad = AlumnoConvertidor.convertirAlumnoDTOAAlumno(alumno);
            AlumnoEntidad updatedAlumnoEntidad = this.alumnoDAO.editar(alumnoEntidad);
            return AlumnoConvertidor.convertirAlumnoAAlumnoDTO(updatedAlumnoEntidad);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    @Override
    public AlumnoDTO eliminar(AlumnoDTO alumno) throws NegocioException {
        try {
            alumno = this.buscarPorIdAlumno(alumno.getIdAlumno());
            AlumnoEntidad alumnoEntidad = AlumnoConvertidor.convertirAlumnoDTOAAlumno(alumno);
            return AlumnoConvertidor.convertirAlumnoAAlumnoDTO(this.alumnoDAO.eliminar(alumnoEntidad));
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}
