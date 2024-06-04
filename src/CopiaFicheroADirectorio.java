
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class CopiaFicheroADirectorio extends JFrame {
    private JTextArea logTextArea;
    private JButton selectFileButton, selectDestinationButton, copyButton;
    private File selectedFile, destinationDirectory;

    public CopiaFicheroADirectorio() {
        super("Aplicación Copia Fichero");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Configurar logTextArea
        logTextArea = new JTextArea(10, 50);
        logTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        add(scrollPane, BorderLayout.SOUTH);

        // Configurar botones y sus acciones
        JPanel buttonPanel = new JPanel();
        selectFileButton = new JButton("Selecciona Archivo");
        selectDestinationButton = new JButton("Selecciona Destino");
        copyButton = new JButton("Copia Archivo!!");
        copyButton.setEnabled(false); // Se habilitará cuando se hayan seleccionado ambos, archivo y destino

        buttonPanel.add(selectFileButton);
        buttonPanel.add(selectDestinationButton);
        buttonPanel.add(copyButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Listeners para los botones
        selectFileButton.addActionListener(e -> elegirArchivo());
        selectDestinationButton.addActionListener(e -> elegirDestino());
        copyButton.addActionListener(e -> copiarArchivo());

        setVisible(true);
    }

    private void elegirArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
        	 File[] selectedFiles = fileChooser.getSelectedFiles();
            selectedFile = fileChooser.getSelectedFile();
            logTextArea.append("Archivo seleccionado: " + selectedFile.getAbsolutePath() + "\n");
            validaSeleccion();
        }
    }

    private void elegirDestino() {
        JFileChooser dirChooser = new JFileChooser();
        dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = dirChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            destinationDirectory = dirChooser.getSelectedFile();
            logTextArea.append("Destino Seleccionado: " + destinationDirectory.getAbsolutePath() + "\n");
            validaSeleccion();
        }
    }

    private void copiarArchivo() {
        if (selectedFile != null && destinationDirectory != null) {
            try {
                File destinationFile = new File(destinationDirectory, selectedFile.getName());
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                logTextArea.append("Fichero copiado con éxito a: " + destinationFile.getAbsolutePath() + "\n");
            } catch (IOException ex) {
                logTextArea.append("Error al copiar fichero: " + ex.getMessage() + "\n");
            }
        }
    }

    private void validaSeleccion() {
        if (selectedFile != null && destinationDirectory != null) {
            copyButton.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        new CopiaFicheroADirectorio();
    }
}
