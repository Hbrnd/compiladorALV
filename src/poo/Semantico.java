/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poo;

import java.util.ArrayList;

/**
 * Analizador semántico que verifica la coherencia del programa
 * 
 * Esta clase realiza las siguientes validaciones semánticas:
 * - Verificar que cada variable sea declarada antes de usarse
 * - Comprobar que no existan declaraciones duplicadas de variables
 * - Registrar errores semánticos para mostrarlos al usuario
 * 
 * @author hsoju
 */
public class Semantico {
    /**
     * Lista de tokens obtenidos del análisis sintáctico
     * Estos tokens serán analizados en busca de errores semánticos
     */
    private ArrayList<Token> listaTokensAnalizados;
    
    /**
     * Constructor que inicializa el analizador semántico con tokens
     * 
     * @param listaTokensAnalizados Tokens que serán sometidos al análisis semántico
     */
    public Semantico(ArrayList<Token> listaTokensAnalizados) {
        this.listaTokensAnalizados = listaTokensAnalizados;
    }

    /**
     * Ejecuta el análisis semántico completo
     * 
     * Recorre los tokens validando aspectos semánticos como:
     * - Declaración previa de variables antes de su uso
     * - Evitar declaraciones duplicadas de la misma variable
     * 
     * @return true si no se encontraron errores semánticos, false en caso contrario
     */
    public boolean analizar() {
        boolean analisisExitoso = true;
        ArrayList<String> tablaVariablesDeclaradas = new ArrayList<>();

        // Recorrer todos los tokens analizando aspectos semánticos
        for (int posicionActual = 0; posicionActual < listaTokensAnalizados.size(); posicionActual++) {
            Token tokenActual = listaTokensAnalizados.get(posicionActual);

            // ANÁLISIS 1: Detectar declaraciones de variables (int x, float y, etc.)
            if (tokenActual.getTipo().equals("RESERVADA") && 
                (tokenActual.getValor().equals("int") || tokenActual.getValor().equals("float"))) {
                
                // Verificar que exista un token siguiente (el identificador)
                if (posicionActual + 1 < listaTokensAnalizados.size()) {
                    Token tokenSiguiente = listaTokensAnalizados.get(posicionActual + 1);

                    // Comprobar que el siguiente token sea un identificador
                    if (tokenSiguiente.getTipo().equals("IDENTIFICADOR")) {
                        String nombreVariable = tokenSiguiente.getValor();

                        // Verificar si la variable ya fue declarada (error semántico)
                        if (tablaVariablesDeclaradas.contains(nombreVariable)) {
                            GestorErrores.agregarError("Semántico", 
                                                      "La variable '" + nombreVariable + "' ya fue declarada anteriormente.",
                                                      tokenSiguiente.getLinea(), 
                                                      tokenSiguiente.getColumna());
                            analisisExitoso = false;
                        } else {
                            // Registrar la variable como declarada
                            tablaVariablesDeclaradas.add(nombreVariable);
                        }
                    }
                }
            }

            // ANÁLISIS 2: Verificar uso de variables no declaradas
            if (tokenActual.getTipo().equals("IDENTIFICADOR")) {
                String nombreVariable = tokenActual.getValor();
                
                // Verificar si es parte de una declaración o un uso
                // Si es un uso (no está precedido por int/float), verificar que esté declarada
                if (posicionActual == 0 || !listaTokensAnalizados.get(posicionActual-1).getTipo().equals("RESERVADA")) {
                    if (!tablaVariablesDeclaradas.contains(nombreVariable)) {
                        GestorErrores.agregarError("Semántico", 
                                                  "La variable '" + nombreVariable + "' se usa sin haber sido declarada previamente.",
                                                  tokenActual.getLinea(), 
                                                  tokenActual.getColumna());
                        analisisExitoso = false;
                    }
                }
            }
        }

        return analisisExitoso;
    }
}
