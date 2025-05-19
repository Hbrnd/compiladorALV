/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo;

/**
 * Representa un error detectado durante el proceso de compilación
 * 
 * Cada instancia de esta clase contiene información detallada sobre un error:
 * - El tipo de error (léxico, sintáctico o semántico)
 * - Un mensaje descriptivo que explica el problema
 * - La ubicación precisa del error (línea y columna en el código fuente)
 * 
 * Esta información permite al usuario identificar y corregir los problemas
 * en su código fuente de manera eficiente.
 * 
 * @author hsoju
 */
public class Error {
    /**
     * Categoría del error (Léxico, Sintáctico, Semántico)
     * Indica en qué fase del análisis se detectó el problema
     */
    private String tipoError;
    
    /**
     * Descripción detallada del error encontrado
     * Explica el problema de manera comprensible para el usuario
     */
    private String mensajeDescriptivo;
    
    /**
     * Número de línea donde se detectó el error en el código fuente
     * La numeración comienza en 1 para la primera línea
     */
    private int numeroLinea;
    
    /**
     * Posición de columna donde se detectó el error
     * La numeración comienza en 1 para la primera columna
     */
    private int posicionColumna;

    /**
     * Constructor que inicializa un nuevo error con toda su información
     * 
     * @param tipoError Categoría del error (Léxico, Sintáctico, Semántico)
     * @param mensajeDescriptivo Descripción detallada del problema
     * @param numeroLinea Número de línea donde ocurrió el error
     * @param posicionColumna Posición de columna donde ocurrió el error
     */
    public Error(String tipoError, String mensajeDescriptivo, int numeroLinea, int posicionColumna) {
        this.tipoError = tipoError;
        this.mensajeDescriptivo = mensajeDescriptivo;
        this.numeroLinea = numeroLinea;
        this.posicionColumna = posicionColumna;
    }

    /**
     * Obtiene el tipo de error
     * @return Categoría del error (Léxico, Sintáctico, Semántico)
     */
    public String getTipoError() {
        return tipoError;
    }

    /**
     * Obtiene el mensaje descriptivo del error
     * @return Descripción detallada del problema
     */
    public String getMensajeDescriptivo() {
        return mensajeDescriptivo;
    }

    /**
     * Obtiene el número de línea donde ocurrió el error
     * @return Número de línea en el código fuente
     */
    public int getNumeroLinea() {
        return numeroLinea;
    }

    /**
     * Obtiene la posición de columna donde ocurrió el error
     * @return Posición de columna en el código fuente
     */
    public int getPosicionColumna() {
        return posicionColumna;
    }

    /**
     * Genera una representación textual del error
     * 
     * El formato del mensaje de error es:
     * [TipoError] Línea X, Columna Y: Mensaje descriptivo
     * 
     * @return Cadena de texto formateada con la información del error
     */
    @Override
    public String toString() {
        return "[" + tipoError + "] Línea " + numeroLinea + ", Columna " + posicionColumna + ": " + mensajeDescriptivo;
    }
}
