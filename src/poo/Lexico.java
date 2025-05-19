/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Analizador léxico que transforma código fuente en tokens
 * 
 * Esta clase realiza las siguientes funciones principales:
 * - Recorre el código fuente carácter por carácter
 * - Identifica patrones como identificadores, números, operadores, etc.
 * - Crea tokens para cada unidad léxica reconocida
 * - Registra errores cuando encuentra caracteres no válidos
 * - Mantiene un seguimiento de líneas y columnas para la ubicación de errores
 * 
 * @author hsoju
 */
public class Lexico {

    /**
     * Analiza un código fuente y lo convierte en una secuencia de tokens
     * 
     * @param codigoFuenteCompleto El texto completo del código a analizar
     * @return Lista de tokens identificados en el código fuente
     */
    public ArrayList<Token> analizar(String codigoFuenteCompleto) {
        ArrayList<Token> listaTokensReconocidos = new ArrayList<>();
        GestorErrores.limpiarErrores(); // Limpiar errores de ejecuciones previas

        // Variables para rastrear posición en el código fuente
        int numeroLinea = 1;
        int posicionColumna = 1;
        int posicionActual = 0;
        
        // Recorrer todo el código fuente carácter por carácter
        while (posicionActual < codigoFuenteCompleto.length()) {
            char caracterActual = codigoFuenteCompleto.charAt(posicionActual);

            // CASO 1: Saltar espacios en blanco y tabulaciones
            if (caracterActual == ' ' || caracterActual == '\t') {
                posicionColumna++;
                posicionActual++;
                continue;
            }

            // CASO 2: Manejo de saltos de línea
            if (caracterActual == '\n') {
                numeroLinea++;        // Avanzar a la siguiente línea
                posicionColumna = 1;  // Reiniciar posición de columna
                posicionActual++;
                continue;
            }

            // CASO 3: Identificadores y palabras reservadas
            if (Character.isLetter(caracterActual)) {
                int posicionInicioToken = posicionColumna;
                StringBuilder constructorLexema = new StringBuilder();
                
                // Leer caracteres válidos para identificadores (letras, números, guiones bajos)
                while (posicionActual < codigoFuenteCompleto.length() && 
                       (Character.isLetterOrDigit(codigoFuenteCompleto.charAt(posicionActual)) || 
                        codigoFuenteCompleto.charAt(posicionActual) == '_')) {
                    constructorLexema.append(codigoFuenteCompleto.charAt(posicionActual));
                    posicionActual++;
                    posicionColumna++;
                }
                
                // Determinar si es palabra reservada o identificador
                String tipoToken = detectarTipo(constructorLexema.toString());
                listaTokensReconocidos.add(new Token(tipoToken, constructorLexema.toString(), numeroLinea, posicionInicioToken));
                continue;
            }

            // CASO 4: Números enteros
            if (Character.isDigit(caracterActual)) {
                int posicionInicioToken = posicionColumna;
                StringBuilder constructorLexema = new StringBuilder();
                
                // Leer dígitos consecutivos
                while (posicionActual < codigoFuenteCompleto.length() && 
                       Character.isDigit(codigoFuenteCompleto.charAt(posicionActual))) {
                    constructorLexema.append(codigoFuenteCompleto.charAt(posicionActual));
                    posicionActual++;
                    posicionColumna++;
                }
                
                listaTokensReconocidos.add(new Token("NUMERO", constructorLexema.toString(), numeroLinea, posicionInicioToken));
                continue;
            }

            // CASO 5: Operadores y símbolos especiales
            String operadorOSimbolo = String.valueOf(caracterActual);
            String tipoToken = detectarTipo(operadorOSimbolo);
            
            if (!tipoToken.equals("DESCONOCIDO")) {
                listaTokensReconocidos.add(new Token(tipoToken, operadorOSimbolo, numeroLinea, posicionColumna));
                posicionColumna++;
                posicionActual++;
                continue;
            }

            // CASO 6: Carácter no reconocido (ERROR)
            GestorErrores.agregarError("Léxico", "Símbolo no válido: '" + caracterActual + "'", numeroLinea, posicionColumna);
            posicionColumna++;
            posicionActual++;
        }

        return listaTokensReconocidos;
    }

    /**
     * Determina el tipo de un lexema basado en su patrón
     * 
     * @param lexemaAnalizado El texto del lexema a clasificar
     * @return El tipo del token correspondiente al lexema
     */
    private String detectarTipo(String lexemaAnalizado) {
        // Verificar si es un número
        if (lexemaAnalizado.matches("\\d+")) return "NUMERO";
        
        // Verificar si es un identificador o palabra reservada
        if (lexemaAnalizado.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            if (esReservada(lexemaAnalizado)) return "RESERVADA";
            return "IDENTIFICADOR";
        }
        
        // Verificar operadores específicos
        if (lexemaAnalizado.equals("=")) return "ASIGNACION";
        if (lexemaAnalizado.equals(";")) return "PUNTO_COMA";
        
        // Operadores aritméticos
        if (lexemaAnalizado.matches("[+\\-*/]")) return "OPERADOR_ARITMETICO";
        
        // Paréntesis y delimitadores
        if (lexemaAnalizado.equals("(")) return "PARENTESIS_ABRE";
        if (lexemaAnalizado.equals(")")) return "PARENTESIS_CIERRA";
        if (lexemaAnalizado.equals("[")) return "CORCHETE_ABRE";
        if (lexemaAnalizado.equals("]")) return "CORCHETE_CIERRA";
        if (lexemaAnalizado.equals("{")) return "LLAVE_ABRE";
        if (lexemaAnalizado.equals("}")) return "LLAVE_CIERRA";
        
        // Otros símbolos
        if (lexemaAnalizado.matches("[,.]")) return "SIMBOLO";
        
        return "DESCONOCIDO";
    }

    /**
     * Verifica si una palabra es una palabra reservada del lenguaje
     * 
     * @param palabraAnalizada La palabra a verificar
     * @return true si es una palabra reservada, false en caso contrario
     */
    private boolean esReservada(String palabraAnalizada) {
        // Lista de palabras reservadas del lenguaje
        String[] palabrasReservadas = {"if", "else", "while", "for", "int", "float", "return", "void"};
        
        // Verificar coincidencia con alguna palabra reservada
        for (String palabraReservada : palabrasReservadas) {
            if (palabraReservada.equals(palabraAnalizada)) return true;
        }
        
        return false;
    }
}
