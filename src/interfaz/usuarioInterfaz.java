/*
 * Interfaz de Usuario para Compilador
 * Esta clase implementa la interfaz gráfica principal del compilador,
 * permitiendo al usuario cargar, editar y compilar código fuente
 * con análisis léxico, sintáctico y semántico.
 */
package interfaz;
import java.util.*;
import poo.GestorErrores;
import poo.abrirArchivo;
import poo.Lexico;
import poo.Semantico;
import poo.Sintax;
import poo.Token;

/**
 * Ventana principal de la interfaz del compilador
 * Contiene todas las funcionalidades para interactuar con el analizador
 * @author hsoju
 */
public class usuarioInterfaz extends javax.swing.JFrame {

    /**
     * Constructor que inicializa todos los componentes de la interfaz
     */
    public usuarioInterfaz() {
        initComponents();
    }

    /**
     * Inicializa los componentes de la interfaz gráfica (generado por NetBeans)
     * ADVERTENCIA: No modificar manualmente este código
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pestaniasResultados = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaResultadoSintactico = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        areaResultadoSemantico = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        areaCodigoFuente = new javax.swing.JTextArea();
        botonAbrirArchivo = new javax.swing.JButton();
        compilar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaResultadoLexico = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pestaniasResultados.setBackground(new java.awt.Color(255, 255, 255));
        pestaniasResultados.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        areaResultadoSintactico.setColumns(20);
        areaResultadoSintactico.setRows(5);
        jScrollPane2.setViewportView(areaResultadoSintactico);

        pestaniasResultados.addTab("SINTACTICO", jScrollPane2);

        areaResultadoSemantico.setColumns(20);
        areaResultadoSemantico.setRows(5);
        jScrollPane3.setViewportView(areaResultadoSemantico);

        pestaniasResultados.addTab("SEMANTICO", jScrollPane3);

        areaCodigoFuente.setColumns(20);
        areaCodigoFuente.setRows(5);
        jScrollPane4.setViewportView(areaCodigoFuente);

        botonAbrirArchivo.setText("Abrir");
        botonAbrirArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAbrirArchivoActionPerformed(evt);
            }
        });

        compilar.setText("Compilar");
        compilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compilarActionPerformed(evt);
            }
        });

        areaResultadoLexico.setColumns(20);
        areaResultadoLexico.setRows(5);
        jScrollPane1.setViewportView(areaResultadoLexico);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(137, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonAbrirArchivo)
                        .addGap(18, 18, 18)
                        .addComponent(compilar))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(pestaniasResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 724, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(138, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addComponent(pestaniasResultados, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAbrirArchivo)
                    .addComponent(compilar))
                .addContainerGap(128, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Maneja el evento de apertura de archivos
     * Muestra un diálogo para seleccionar un archivo y lo carga en el área de código
     */
    private void botonAbrirArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirActionPerformed
        // Crea un objeto para manejar la apertura de archivos y lo muestra en el área de código
        botonAbrirArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirArchivo utilidadArchivos = new abrirArchivo();
                utilidadArchivos.abrirArchivo(areaCodigoFuente);
            }
        });
    }//GEN-LAST:event_abrirActionPerformed

    /**
     * Maneja el evento de compilación de código
     * Realiza los análisis léxico, sintáctico y semántico del código fuente
     * y muestra los resultados en las áreas correspondientes
     */
    private void compilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compilarActionPerformed
        // Obtiene el código fuente del área de texto y limpia los errores previos
        String codigoFuente = areaCodigoFuente.getText();
        GestorErrores.limpiarErrores();

        // FASE 1: ANÁLISIS LÉXICO
        // Crea un analizador léxico y procesa el código para obtener los tokens
        Lexico analizadorLexico = new Lexico();
        ArrayList<Token> tokensReconocidos = analizadorLexico.analizar(codigoFuente);

        // Crea una presentación formateada de los tokens para mostrar al usuario
        StringBuilder resultadoAnalisisLexico = new StringBuilder();
        resultadoAnalisisLexico.append("TOKENS:\n");
        resultadoAnalisisLexico.append("=================================================\n");

        // Formatea cada token de manera legible
        for (Token token : tokensReconocidos) {
            resultadoAnalisisLexico.append(String.format("-- %-15s -- %-15s -- línea %-3d -- columna %-3d --\n", 
                        token.getTipo(), 
                        "'" + token.getValor() + "'", 
                        token.getLinea(), 
                        token.getColumna()));
        }
        areaResultadoLexico.setText(resultadoAnalisisLexico.toString());

        // FASE 2: ANÁLISIS SINTÁCTICO (solo si no hay errores léxicos)
        boolean analisisSintacticoExitoso = false;
        if (!GestorErrores.hayErrores()) {
            // Crea un analizador sintáctico y procesa los tokens
            Sintax analizadorSintactico = new Sintax();
            analizadorSintactico.analizar(tokensReconocidos);
            // Verifica si el análisis sintáctico fue exitoso
            analisisSintacticoExitoso = !GestorErrores.hayErrores();
        }

        // FASE 3: ANÁLISIS SEMÁNTICO (solo si no hay errores léxicos ni sintácticos)
        boolean analisisSemanticoExitoso = false;
        if (analisisSintacticoExitoso) {
            // Crea un analizador semántico y procesa los tokens
            Semantico analizadorSemantico = new Semantico(tokensReconocidos);
            analisisSemanticoExitoso = analizadorSemantico.analizar();
        }

        // FASE 4: MOSTRAR RESULTADOS SEGÚN EL TIPO DE ANÁLISIS
        if (GestorErrores.hayErrores()) {
            // Si hay errores, obtener los mensajes y mostrarlos
            String mensajesDeError = GestorErrores.obtenerErroresComoTexto();
            
            // Actualizar áreas según en qué fase falló
            if (!analisisSintacticoExitoso) {
                // Si falló en la sintaxis, muestra los errores en el área sintáctica
                areaResultadoSintactico.setText("ANÁLISIS SINTÁCTICO FALLIDO\n\n" + mensajesDeError);
                areaResultadoSemantico.setText("ANÁLISIS SEMÁNTICO NO REALIZADO\n\nEl análisis sintáctico falló.");
                pestaniasResultados.setSelectedIndex(0); // Muestra la pestaña sintáctica
            } else if (!analisisSemanticoExitoso) {
                // Si falló en la semántica, muestra los errores en el área semántica
                areaResultadoSintactico.setText("ANÁLISIS SINTÁCTICO EXITOSO\n\nLa sintaxis del código es correcta.");
                areaResultadoSemantico.setText("ANÁLISIS SEMÁNTICO FALLIDO\n\n" + mensajesDeError);
                pestaniasResultados.setSelectedIndex(1); // Muestra la pestaña semántica
            }
        } else {
            // Si todo salió bien, muestra mensajes de éxito en ambas áreas
            areaResultadoSintactico.setText("ANÁLISIS SINTÁCTICO EXITOSO\n\nLa sintaxis del código es correcta.");
            areaResultadoSemantico.setText("ANÁLISIS SEMÁNTICO EXITOSO\n\nEl código es semánticamente correcto.");
            pestaniasResultados.setSelectedIndex(1); // Muestra la pestaña semántica para ver el éxito
        }
    }//GEN-LAST:event_compilarActionPerformed

    /**
     * Método principal para iniciar la aplicación
     * Configura el estilo visual y crea la ventana principal
     */
    public static void main(String args[]) {
        /* Configura el estilo visual Nimbus si está disponible */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(usuarioInterfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Crea y muestra la ventana principal */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new usuarioInterfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaCodigoFuente;
    private javax.swing.JTextArea areaResultadoLexico;
    private javax.swing.JTextArea areaResultadoSemantico;
    private javax.swing.JTextArea areaResultadoSintactico;
    private javax.swing.JButton botonAbrirArchivo;
    private javax.swing.JButton compilar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane pestaniasResultados;
    // End of variables declaration//GEN-END:variables
}
