/*
 * Analizador Sintáctico del Compilador
 * 
 * Esta clase implementa el análisis sintáctico, segunda fase del proceso de compilación.
 * Su función es verificar que las secuencias de tokens sigan las reglas gramaticales
 * definidas para el lenguaje, como declaraciones de variables y asignaciones.
 */
package poo;

import java.util.ArrayList;
import java.util.List;

/**
 * Analizador sintáctico que valida la estructura gramatical del código
 * 
 * Esta clase realiza las siguientes validaciones sintácticas:
 * - Verifica que las declaraciones de variables tengan la forma "tipo identificador;"
 * - Asegura que las asignaciones sigan el patrón "identificador = valor;"
 * - Registra errores cuando encuentra construcciones sintácticas inválidas
 * - Proporciona mensajes de error específicos para ayudar en la corrección
 * 
 * @author hsoju
 */
public class Sintax {
    /**
     * Lista de tokens a analizar sintácticamente
     */
    private List<Token> listaTokensAnalizar;
    
    /**
     * Posición actual dentro de la lista de tokens durante el análisis
     */
    private int posicionTokenActual;

    /**
     * Analiza la estructura sintáctica de una secuencia de tokens
     * 
     * Recorre todos los tokens verificando que formen instrucciones válidas
     * según las reglas gramaticales del lenguaje. Registra errores cuando
     * encuentra construcciones no válidas.
     * 
     * @param listaTokensAnalizar Lista de tokens obtenidos del analizador léxico
     */
    public void analizar(List<Token> listaTokensAnalizar) {
        this.listaTokensAnalizar = listaTokensAnalizar;
        this.posicionTokenActual = 0;

        // Procesar todos los tokens hasta el final
        while (posicionTokenActual < listaTokensAnalizar.size()) {
            // Verificar si la instrucción actual es válida
            if (!esInstruccionValida()) {
                // Cuando una instrucción no es válida, generar mensaje de error apropiado
                Token tokenProblematico = listaTokensAnalizar.get(posicionTokenActual);
                String mensajeError = obtenerMensajeError(tokenProblematico);
                
                // Registrar el error con el gestor centralizado
                GestorErrores.agregarError("[Sintáctico] " + mensajeError, 
                                         tokenProblematico.getLinea(), 
                                         tokenProblematico.getColumna());
                
                // Avanzar hasta el final de la instrucción actual para continuar con la siguiente
                avanzarHastaFinDeInstruccion();
            }
        }
    }

    /**
     * Determina si la secuencia de tokens actual forma una instrucción válida
     * 
     * Intenta reconocer los dos tipos de instrucciones soportadas:
     * 1. Declaración de variable (int x;)
     * 2. Asignación de variable (x = 10;)
     * 
     * @return true si la instrucción es válida, false en caso contrario
     */
    private boolean esInstruccionValida() {
        // Guardar posición inicial para restaurar en caso de fallo
        int posicionRestauracion = posicionTokenActual;
        
        // 1. Intentar reconocer una declaración de variable: int x;
        if (esDeclaracionVariable()) {
            return true;
        }
        
        // Si no es declaración, restaurar posición e intentar con asignación
        posicionTokenActual = posicionRestauracion;
        
        // 2. Intentar reconocer una asignación: x = 10;
        if (esAsignacionVariable()) {
            return true;
        }
        
        // Si llegamos aquí, no es una instrucción válida
        posicionTokenActual = posicionRestauracion + 1; // Avanzar para el siguiente intento
        return false;
    }
    
    /**
     * Verifica si la secuencia actual de tokens corresponde a una declaración de variable
     * 
     * Una declaración de variable válida tiene la forma: TIPO IDENTIFICADOR PUNTO_COMA
     * Ejemplo: "int contador;"
     * 
     * @return true si es una declaración válida, false en caso contrario
     */
    private boolean esDeclaracionVariable() {
        // Verificar si hay suficientes tokens restantes para formar una declaración completa
        if (posicionTokenActual + 2 >= listaTokensAnalizar.size()) return false;
        
        // Leer los tres tokens que deberían formar la declaración
        Token tokenTipoVariable = listaTokensAnalizar.get(posicionTokenActual++);
        Token tokenIdentificador = listaTokensAnalizar.get(posicionTokenActual++);
        Token tokenPuntoComa = listaTokensAnalizar.get(posicionTokenActual++);
        
        // Verificar que sigan el patrón RESERVADA(int/float) IDENTIFICADOR PUNTO_COMA
        if (tokenTipoVariable.getTipo().equals("RESERVADA") && 
            (tokenTipoVariable.getValor().equals("int") || tokenTipoVariable.getValor().equals("float")) &&
            tokenIdentificador.getTipo().equals("IDENTIFICADOR") &&
            tokenPuntoComa.getTipo().equals("PUNTO_COMA")) {
            return true;
        }
        
        // Si no coincide con el patrón, restaurar el índice y retornar falso
        posicionTokenActual -= 3;  // Volver a la posición inicial
        return false;
    }
    
    /**
     * Verifica si la secuencia actual de tokens corresponde a una asignación de variable
     * 
     * Una asignación válida tiene la forma: IDENTIFICADOR ASIGNACION NUMERO PUNTO_COMA
     * Ejemplo: "contador = 10;"
     * 
     * @return true si es una asignación válida, false en caso contrario
     */
    private boolean esAsignacionVariable() {
        // Verificar que haya al menos un identificador y operador de asignación
        if (posicionTokenActual + 2 >= listaTokensAnalizar.size()) return false;
        
        int posicionInicial = posicionTokenActual;
        
        // Leer el identificador y operador de asignación
        Token tokenIdentificador = listaTokensAnalizar.get(posicionTokenActual++);
        Token tokenOperadorAsignacion = listaTokensAnalizar.get(posicionTokenActual++);
        
        // Verificar el patrón básico: IDENTIFICADOR ASIGNACION
        if (!tokenIdentificador.getTipo().equals("IDENTIFICADOR") ||
            !tokenOperadorAsignacion.getTipo().equals("ASIGNACION")) {
            posicionTokenActual = posicionInicial;
            return false;
        }
        
        // Verificar que hay una expresión válida
        if (!esExpresionValida()) {
            posicionTokenActual = posicionInicial;
            return false;
        }
        
        // Verificar que la expresión termina con punto y coma
        if (posicionTokenActual >= listaTokensAnalizar.size() || 
            !listaTokensAnalizar.get(posicionTokenActual++).getTipo().equals("PUNTO_COMA")) {
            posicionTokenActual = posicionInicial;
            return false;
        }
        
        return true;
    }

    /**
     * Verifica si la secuencia actual de tokens forma una expresión aritmética válida
     */
    private boolean esExpresionValida() {
        int posicionInicial = posicionTokenActual;
        boolean esValida = parseTermino();
        
        // Después de un término válido, puede haber + o - seguido de otro término
        while (posicionTokenActual < listaTokensAnalizar.size() && esValida) {
            Token tokenActual = listaTokensAnalizar.get(posicionTokenActual);
            
            if (tokenActual.getTipo().equals("OPERADOR_ARITMETICO") && 
               (tokenActual.getValor().equals("+") || tokenActual.getValor().equals("-"))) {
                posicionTokenActual++;
                esValida = parseTermino();
            } else {
                break;
            }
        }
        
        if (!esValida) {
            posicionTokenActual = posicionInicial;
        }
        
        return esValida;
    }

    /**
     * Analiza un término (producto o división de factores)
     */
    private boolean parseTermino() {
        int posicionInicial = posicionTokenActual;
        boolean esValido = parseFactor();
        
        // Después de un factor válido, puede haber * o / seguido de otro factor
        while (posicionTokenActual < listaTokensAnalizar.size() && esValido) {
            Token tokenActual = listaTokensAnalizar.get(posicionTokenActual);
            
            if (tokenActual.getTipo().equals("OPERADOR_ARITMETICO") && 
               (tokenActual.getValor().equals("*") || tokenActual.getValor().equals("/"))) {
                posicionTokenActual++;
                esValido = parseFactor();
            } else {
                break;
            }
        }
        
        if (!esValido) {
            posicionTokenActual = posicionInicial;
        }
        
        return esValido;
    }

    /**
     * Analiza un factor (número, identificador o expresión entre paréntesis)
     */
    private boolean parseFactor() {
        if (posicionTokenActual >= listaTokensAnalizar.size()) {
            return false;
        }
        
        Token tokenActual = listaTokensAnalizar.get(posicionTokenActual);
        
        // Un factor puede ser un número
        if (tokenActual.getTipo().equals("NUMERO")) {
            posicionTokenActual++;
            return true;
        }
        
        // Un factor puede ser un identificador
        if (tokenActual.getTipo().equals("IDENTIFICADOR")) {
            posicionTokenActual++;
            return true;
        }
        
        // Un factor puede ser una expresión entre paréntesis
        if (tokenActual.getTipo().equals("PARENTESIS_ABRE")) {
            posicionTokenActual++;
            
            boolean expresionValida = esExpresionValida();
            
            if (!expresionValida) {
                return false;
            }
            
            // Verificar que hay un paréntesis que cierra
            if (posicionTokenActual >= listaTokensAnalizar.size() || 
                !listaTokensAnalizar.get(posicionTokenActual).getTipo().equals("PARENTESIS_CIERRA")) {
                return false;
            }
            
            posicionTokenActual++;
            return true;
        }
        
        // Un factor puede ser una expresión entre corchetes
        if (tokenActual.getTipo().equals("CORCHETE_ABRE")) {
            posicionTokenActual++;
            
            boolean expresionValida = esExpresionValida();
            
            if (!expresionValida) {
                return false;
            }
            
            // Verificar que hay un corchete que cierra
            if (posicionTokenActual >= listaTokensAnalizar.size() || 
                !listaTokensAnalizar.get(posicionTokenActual).getTipo().equals("CORCHETE_CIERRA")) {
                return false;
            }
            
            posicionTokenActual++;
            return true;
        }
        
        // Un factor puede ser una expresión entre llaves
        if (tokenActual.getTipo().equals("LLAVE_ABRE")) {
            posicionTokenActual++;
            
            boolean expresionValida = esExpresionValida();
            
            if (!expresionValida) {
                return false;
            }
            
            // Verificar que hay una llave que cierra
            if (posicionTokenActual >= listaTokensAnalizar.size() || 
                !listaTokensAnalizar.get(posicionTokenActual).getTipo().equals("LLAVE_CIERRA")) {
                return false;
            }
            
            posicionTokenActual++;
            return true;
        }
        
        return false;
    }
    
    /**
     * Avanza el índice actual hasta encontrar el final de la instrucción actual
     * 
     * Esto permite al analizador recuperarse de errores y continuar con la siguiente
     * instrucción después de un punto y coma, evitando múltiples errores en cascada.
     */
    private void avanzarHastaFinDeInstruccion() {
        // Avanzar hasta encontrar un punto y coma o llegar al final
        while (posicionTokenActual < listaTokensAnalizar.size()) {
            Token tokenActual = listaTokensAnalizar.get(posicionTokenActual++);
            if (tokenActual.getTipo().equals("PUNTO_COMA")) {
                break;  // Encontró el final de la instrucción
            }
        }
    }
    
    /**
     * Genera un mensaje de error descriptivo basado en el contexto sintáctico actual
     * 
     * Analiza el token que causó el error y su contexto (tokens anteriores)
     * para proporcionar un mensaje claro sobre lo que el compilador esperaba encontrar.
     * 
     * @param tokenErroneo Token que causó el error sintáctico
     * @return Mensaje descriptivo del error para mostrar al usuario
     */
    private String obtenerMensajeError(Token tokenErroneo) {
        // Si estamos al inicio de una instrucción
        if (posicionTokenActual == 0 || 
            (posicionTokenActual > 0 && listaTokensAnalizar.get(posicionTokenActual - 1).getTipo().equals("PUNTO_COMA"))) {
            
            if (tokenErroneo.getTipo().equals("RESERVADA"))
                return "Se esperaba 'int' o 'float' seguido de un identificador";
            else if (!tokenErroneo.getTipo().equals("IDENTIFICADOR"))
                return "Se esperaba un identificador para comenzar una instrucción";
        }
        
        // Generar mensaje según el contexto
        if (posicionTokenActual > 0) {
            Token tokenAnterior = listaTokensAnalizar.get(posicionTokenActual - 1);
            
            if (tokenAnterior.getTipo().equals("RESERVADA")) {
                if (!tokenErroneo.getTipo().equals("IDENTIFICADOR"))
                    return "Se esperaba un identificador después de '" + tokenAnterior.getValor() + "'";
            }
            else if (tokenAnterior.getTipo().equals("IDENTIFICADOR")) {
                if (!tokenErroneo.getTipo().equals("ASIGNACION"))
                    return "Se esperaba un operador de asignación '=' después del identificador";
            }
            else if (tokenAnterior.getTipo().equals("ASIGNACION")) {
                if (!tokenErroneo.getTipo().equals("NUMERO") && 
                    !tokenErroneo.getTipo().equals("IDENTIFICADOR") && 
                    !tokenErroneo.getTipo().equals("PARENTESIS_ABRE") &&
                    !tokenErroneo.getTipo().equals("CORCHETE_ABRE") &&
                    !tokenErroneo.getTipo().equals("LLAVE_ABRE"))
                    return "Se esperaba un valor, identificador o expresión después del '='";
            }
            else if (tokenAnterior.getTipo().equals("PARENTESIS_ABRE") || 
                     tokenAnterior.getTipo().equals("CORCHETE_ABRE") || 
                     tokenAnterior.getTipo().equals("LLAVE_ABRE")) {
                if (!tokenErroneo.getTipo().equals("NUMERO") && 
                    !tokenErroneo.getTipo().equals("IDENTIFICADOR") && 
                    !tokenErroneo.getTipo().equals("PARENTESIS_ABRE"))
                    return "Se esperaba una expresión después de '" + tokenAnterior.getValor() + "'";
            }
            else if (tokenAnterior.getTipo().equals("OPERADOR_ARITMETICO")) {
                if (!tokenErroneo.getTipo().equals("NUMERO") && 
                    !tokenErroneo.getTipo().equals("IDENTIFICADOR") && 
                    !tokenErroneo.getTipo().equals("PARENTESIS_ABRE"))
                    return "Se esperaba un valor o expresión después del operador '" + tokenAnterior.getValor() + "'";
            }
            else if (tokenAnterior.getTipo().equals("NUMERO") || 
                     tokenAnterior.getTipo().equals("IDENTIFICADOR")) {
                if (!tokenErroneo.getTipo().equals("OPERADOR_ARITMETICO") && 
                    !tokenErroneo.getTipo().equals("PUNTO_COMA") &&
                    !tokenErroneo.getTipo().equals("PARENTESIS_CIERRA") &&
                    !tokenErroneo.getTipo().equals("CORCHETE_CIERRA") &&
                    !tokenErroneo.getTipo().equals("LLAVE_CIERRA"))
                    return "Se esperaba un operador o punto y coma después del valor";
            }
            else if (tokenAnterior.getTipo().equals("PARENTESIS_CIERRA") ||
                     tokenAnterior.getTipo().equals("CORCHETE_CIERRA") ||
                     tokenAnterior.getTipo().equals("LLAVE_CIERRA")) {
                if (!tokenErroneo.getTipo().equals("OPERADOR_ARITMETICO") && 
                    !tokenErroneo.getTipo().equals("PUNTO_COMA"))
                    return "Se esperaba un operador o punto y coma después de '" + tokenAnterior.getValor() + "'";
            }
        }
        
        // Validación de paréntesis, corchetes y llaves
        boolean hayParentesisAbierto = false;
        boolean hayCorcheteAbierto = false;
        boolean hayLlaveAbierta = false;
        
        for (int i = 0; i < posicionTokenActual; i++) {
            Token token = listaTokensAnalizar.get(i);
            if (token.getTipo().equals("PARENTESIS_ABRE")) hayParentesisAbierto = true;
            if (token.getTipo().equals("PARENTESIS_CIERRA")) hayParentesisAbierto = false;
            if (token.getTipo().equals("CORCHETE_ABRE")) hayCorcheteAbierto = true;
            if (token.getTipo().equals("CORCHETE_CIERRA")) hayCorcheteAbierto = false;
            if (token.getTipo().equals("LLAVE_ABRE")) hayLlaveAbierta = true;
            if (token.getTipo().equals("LLAVE_CIERRA")) hayLlaveAbierta = false;
        }
        
        if (hayParentesisAbierto && tokenErroneo.getTipo().equals("PUNTO_COMA"))
            return "Falta cerrar paréntesis ')'";
        if (hayCorcheteAbierto && tokenErroneo.getTipo().equals("PUNTO_COMA"))
            return "Falta cerrar corchete ']'";
        if (hayLlaveAbierta && tokenErroneo.getTipo().equals("PUNTO_COMA"))
            return "Falta cerrar llave '}'";
        
        // Mensaje genérico para otros casos
        return "Instrucción inválida cerca de '" + tokenErroneo.getValor() + "'";
    }
}
