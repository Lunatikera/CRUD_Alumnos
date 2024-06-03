/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package convertidores;

import dtos.AlumnoDTO;
import entidad.AlumnoEntidad;

/**
 *
 * @author Rios 233537
 */
public class AlumnoConvertidor {

    public static AlumnoDTO convertirAlumnoAAlumnoDTO(AlumnoEntidad alumno) {
        AlumnoDTO aLumnoDTO = new AlumnoDTO();
        aLumnoDTO.setIdAlumno(alumno.getIdAlumno());
        aLumnoDTO.setNombres(alumno.getNombres());
        aLumnoDTO.setApellidoPaterno(alumno.getApellidoPaterno());
        aLumnoDTO.setApellidoMaterno(alumno.getApellidoMaterno());
        aLumnoDTO.setEstatus(alumno.isActivo() == true ? "No Eliminado" : "Eliminado");
        return aLumnoDTO;
    }

    public static AlumnoEntidad convertirAlumnoDTOAAlumno(AlumnoDTO alumnoDTO) {
        AlumnoEntidad alumno = new AlumnoEntidad();
        alumno.setIdAlumno(alumnoDTO.getIdAlumno());
        alumno.setNombres(alumnoDTO.getNombres());
        alumno.setApellidoPaterno(alumnoDTO.getApellidoPaterno());
        alumno.setApellidoMaterno(alumnoDTO.getApellidoMaterno());
        alumno.setActivo("No Eliminado".equals(alumnoDTO.getEstatus()));
        alumno.setEliminado("Eliminado".equals(alumnoDTO.getEstatus()));
        return alumno;
    }

}
