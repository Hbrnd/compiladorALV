/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Clase Token para Representación de Unidades Léxicas
 * 
 * Esta clase encapsula la información de una unidad léxica (token) reconocida
 * durante el análisis léxico. Cada token contiene información sobre su tipo,
 * valor, y ubicación precisa en el código fuente original.
 */
package poo;

/**
 * Representa una unidad léxica (token) del código fuente
 * 
 * Un token es la unidad básica resultante del análisis léxico y contiene:
 * - El tipo de token (ej: NUMERO, IDENTIFICADOR, RESERVADA)
 * - El valor o lexema exacto encontrado en el código fuente
 * - La ubicación precisa (línea y columna) donde se encontró
 * 
 * Esta información es utilizada por las fases posteriores del compilador
 * (análisis sintáctico y semántico) y para generar mensajes de error precisos.
 * 
 * @author hsoju
 */
public class Token {
    /**
     * Categoría gramatical del token
     * Ejemplos: "IDENTIFICADOR", "NUMERO", "RESERVADA", "OPERADOR", "SIMBOLO"
     */
    private String tipoToken;
    
    /**
     * Secuencia de caracteres que forma el token en el código fuente
     * Es el texto exacto reconocido por el analizador léxico
     */
    private String valorLexema;
    
    /**
     * Número de línea donde se encontró el token en el código fuente
     * La numeración comienza en 1 para la primera línea
     */
    private int numeroLinea;
    
    /**
     * Posición de columna donde comienza el token
     * La numeración comienza en 1 para la primera columna
     */
    private int posicionColumna;

    /**
     * Constructor que inicializa un nuevo token con toda su información
     * 
     * @param tipoToken Categoría gramatical del token
     * @param valorLexema Secuencia de caracteres que forma el token
     * @param numeroLinea Línea donde se encontró el token
     * @param posicionColumna Columna donde comienza el token
     */
    public Token(String tipoToken, String valorLexema, int numeroLinea, int posicionColumna) {
        this.tipoToken = tipoToken;
        this.valorLexema = valorLexema;
        this.numeroLinea = numeroLinea;
        this.posicionColumna = posicionColumna;
    }

    /**
     * Obtiene el tipo del token
     * @return Categoría gramatical del token
     */
    public String getTipo() {
        return tipoToken;
    }

    /**
     * Obtiene el valor/lexema del token
     * @return Texto exacto reconocido en el código fuente
     */
    public String getValor() {
        return valorLexema;
    }

    /**
     * Obtiene el número de línea donde se encontró el token
     * @return Número de línea en el código fuente
     */
    public int getLinea() {
        return numeroLinea;
    }

    /**
     * Obtiene la posición de columna donde comienza el token
     * @return Número de columna en el código fuente
     */
    public int getColumna() {
        return posicionColumna;
    }

    /**
     * Genera una representación textual del token
     * 
     * @return Cadena con información completa del token en formato legible
     */
    @Override
    public String toString() {
        return String.format("Token[tipo=%s, valor='%s', línea=%d, columna=%d]", 
                             tipoToken, valorLexema, numeroLinea, posicionColumna);
    }
}
