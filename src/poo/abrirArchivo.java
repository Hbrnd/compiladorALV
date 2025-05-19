/*
 * Utilidad para Abrir Archivos
 * Esta clase proporciona funcionalidad para seleccionar y cargar
 * archivos de texto en un área de texto de la interfaz gráfica.
 */
package poo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;

/**
 * Clase utilitaria para operaciones de apertura de archivos
 * Permite al usuario seleccionar un archivo mediante un diálogo
 * y cargar su contenido en un área de texto específica.
 * 
 * @author hsoju
 */
public class abrirArchivo {
    /**
     * Muestra un diálogo para seleccionar un archivo y carga su contenido
     * 
     * Este método realiza las siguientes acciones:
     * 1. Muestra un diálogo para que el usuario seleccione un archivo
     * 2. Lee el contenido del archivo seleccionado
     * 3. Carga el contenido en el área de texto especificada
     * 4. Maneja posibles errores durante el proceso
     * 
     * @param areaCodigoDestino El área de texto donde se mostrará el contenido del archivo
     */
    public void abrirArchivo(JTextArea areaCodigoDestino) {
        try {
            // Crear y configurar el selector de archivos
            JFileChooser selectorDeArchivos = new JFileChooser();
            selectorDeArchivos.setDialogTitle("Selecciona un archivo .txt");

            // Mostrar el diálogo y comprobar si el usuario seleccionó un archivo
            int resultadoSeleccion = selectorDeArchivos.showOpenDialog(null);
            
            if (resultadoSeleccion == JFileChooser.APPROVE_OPTION) {
                // Obtener el archivo seleccionado
                File archivoSeleccionado = selectorDeArchivos.getSelectedFile();
                
                // Crear un lector para el archivo
                BufferedReader lectorArchivo = new BufferedReader(
                        new FileReader(archivoSeleccionado));

                // Leer el contenido línea por línea
                StringBuilder contenidoArchivo = new StringBuilder();
                String lineaActual;
                
                while ((lineaActual = lectorArchivo.readLine()) != null) {
                    contenidoArchivo.append(lineaActual).append("\n");
                }
                
                // Cerrar el lector para liberar recursos
                lectorArchivo.close();

                // Mostrar el contenido en el área de texto
                areaCodigoDestino.setText(contenidoArchivo.toString());
            }
        } catch (Exception errorApertura) {
            // Mostrar mensaje de error en caso de problemas al abrir el archivo
            areaCodigoDestino.setText("Error al abrir archivo: " + 
                    errorApertura.getMessage());
        }
    }
}
