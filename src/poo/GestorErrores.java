/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo;

import java.util.ArrayList;

/**
 * Sistema centralizado para la gestión de errores del compilador
 * 
 * Esta clase estática permite:
 * - Registrar errores desde cualquier fase del compilador (léxico, sintáctico, semántico)
 * - Verificar si se han producido errores durante el proceso
 * - Obtener un informe formateado de todos los errores detectados
 * - Limpiar el registro de errores entre ejecuciones
 * 
 * Al centralizar la gestión de errores, facilita la generación de informes
 * coherentes y el manejo de errores entre los diferentes componentes del compilador.
 * 
 * @author hsoju
 */
public class GestorErrores {
    /**
     * Colección que almacena todos los errores detectados
     * Se implementa como un ArrayList estático para permitir
     * acceso global desde cualquier componente del compilador
     */
    private static ArrayList<Error> listaErroresDetectados = new ArrayList<>();

    /**
     * Registra un nuevo error con tipo específico
     * 
     * @param tipoError Categoría del error (Léxico, Sintáctico, Semántico)
     * @param mensajeDescriptivo Descripción detallada del problema
     * @param numeroLinea Línea donde se detectó el error
     * @param posicionColumna Columna donde se detectó el error
     */
    public static void agregarError(String tipoError, String mensajeDescriptivo, int numeroLinea, int posicionColumna) {
        listaErroresDetectados.add(new Error(tipoError, mensajeDescriptivo, numeroLinea, posicionColumna));
    }

    /**
     * Método sobrecargado para mantener compatibilidad con código existente
     * Registra un error genérico sin especificar tipo
     * 
     * @param mensajeDescriptivo Descripción detallada del problema
     * @param numeroLinea Línea donde se detectó el error
     * @param posicionColumna Columna donde se detectó el error
     */
    public static void agregarError(String mensajeDescriptivo, int numeroLinea, int posicionColumna) {
        agregarError("Error", mensajeDescriptivo, numeroLinea, posicionColumna);
    }

    /**
     * Verifica si se ha registrado algún error
     * 
     * @return true si hay al menos un error registrado, false en caso contrario
     */
    public static boolean hayErrores() {
        return !listaErroresDetectados.isEmpty();
    }

    /**
     * Genera un informe de texto con todos los errores registrados
     * Cada error se muestra en una línea separada con su tipo, ubicación y mensaje
     * 
     * @return Cadena de texto formateada con la lista completa de errores
     */
    public static String obtenerErroresComoTexto() {
        StringBuilder constructorTexto = new StringBuilder();
        for (Error errorIndividual : listaErroresDetectados) {
            constructorTexto.append(errorIndividual.toString()).append("\n");
        }
        return constructorTexto.toString();
    }

    /**
     * Elimina todos los errores registrados
     * Se debe llamar antes de cada nuevo proceso de compilación
     * para evitar que errores antiguos persistan
     */
    public static void limpiarErrores() {
        listaErroresDetectados.clear();
    }
}
