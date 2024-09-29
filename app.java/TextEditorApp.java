import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditorApp {
    private JFrame frame;
    private JTextArea textArea;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem newFileItem;
    private JMenuItem openFileItem;
    private JMenuItem saveFileItem;
    private JMenuItem printFileItem;
    private JMenuItem exitItem;
    private JMenu editMenu;
    private JMenuItem cutItem;
    private JMenuItem copyItem;
    private JMenuItem pasteItem;

    public TextEditorApp() {
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Text Editor App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea(20, 40);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");

        newFileItem = new JMenuItem("New");
        newFileItem.addActionListener(new NewFileListener());
        openFileItem = new JMenuItem("Open");
        openFileItem.addActionListener(new OpenFileListener());
        saveFileItem = new JMenuItem("Save");
        saveFileItem.addActionListener(new SaveFileListener());
        printFileItem = new JMenuItem("Print");
        printFileItem.addActionListener(new PrintFileListener());
        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ExitListener());

        cutItem = new JMenuItem("Cut");
        cutItem.addActionListener(new CutListener());
        copyItem = new JMenuItem("Copy");
        copyItem.addActionListener(new CopyListener());
        pasteItem = new JMenuItem("Paste");
        pasteItem.addActionListener(new PasteListener());

        fileMenu.add(newFileItem);
        fileMenu.add(openFileItem);
        fileMenu.add(saveFileItem);
        fileMenu.add(printFileItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private class NewFileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            textArea.setText("");
        }
    }

    private class OpenFileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    FileReader reader = new FileReader(file);
                    textArea.read(reader, null);
                    reader.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error opening file: " + ex.getMessage());
                }
            }
        }
    }

    private class SaveFileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    FileWriter writer = new FileWriter(file);
                    textArea.write(writer);
                    writer.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage());
                }
            }
        }
    }

    private class PrintFileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                textArea.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(frame, "Error printing file: " + ex.getMessage());
            }
        }
    }

    private class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class CutListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            textArea.cut();
        }
    }

    private class CopyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            textArea.copy();
        }
    }

    private class PasteListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            textArea.paste();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TextEditorApp();
            }
        });
    }
}