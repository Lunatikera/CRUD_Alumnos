/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author Rios 233537
 */
public class NegocioException extends Exception{

    public NegocioException(String mensaje) {
        super(mensaje);
        
        
    }

    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}
