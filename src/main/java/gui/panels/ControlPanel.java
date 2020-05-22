package gui.panels;

import gui.MainFrame;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import java.lang.reflect.*;

/**
 * ControlPanel will allow the user to specify any class name of a Swing component,
 * a default text for that component (if applicable) and a button for creating
 * and adding an instance of the specified component to the DesignPanel.
 */
public class ControlPanel extends JPanel {
    private final MainFrame mainFrame;
    private final JTextField componentClassTextField;
    private final JTextField componentTextField;
    private final JButton addButton;
    private final JButton loadButton;
    private final JButton saveButton;

    public ControlPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.componentClassTextField = initializeTextField("Component name");
        this.componentTextField = initializeTextField("Default text");
        addButton = new JButton("Add");
        loadButton = new JButton("Load");
        saveButton = new JButton("Save");
        init();
    }

    private JTextField initializeTextField(String text) {
        JTextField textField = new JTextField(text, 10);
        textField.setForeground(Color.GRAY);
        textField.addFocusListener(new FocusListener() {
            //the name is introduced
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(text)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            //the default name
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(text);
                }
            }
        });

        return textField;
    }

    private void init() {
        setLayout(new GridLayout(1, 3));
        addElements();
        addListenersToButtons();
    }

    private void addElements() {
        add(componentClassTextField);
        add(componentTextField);
        add(addButton);
        add(loadButton);
        add(saveButton);
    }

    private void addListenersToButtons() {
        addButton.addActionListener(this::addComponent);
        loadButton.addActionListener(this::loadDocument);
        saveButton.addActionListener(this::savePanel);
    }


    private void addComponent(ActionEvent event) {
        String componentName = "javax.swing." + componentClassTextField.getText();
        Class<?> componentClass = null;
        Constructor<?> constructor;
        Component componentInstance;
        try {
            componentClass = Class.forName(componentName);
            Class<?>[] signature = new Class[]{String.class};
            constructor = componentClass.getConstructor(signature);
            String text = componentTextField.getText();
            componentInstance = (Component) constructor.newInstance(text);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            System.out.println(exception.getMessage());
            return;
        } catch (NoSuchMethodException exception) {
            try {
                System.out.println(exception.getMessage());
                constructor = componentClass.getConstructor();
                componentInstance = (Component) constructor.newInstance();
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        this.mainFrame.getDesignPanel().addComponent(componentInstance);
        this.mainFrame.getDesignPanel().addFocusListenerToComponent(componentInstance);
    }

    private void loadDocument(ActionEvent event){
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Select a document");
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
        fileChooser.addChoosableFileFilter(filter);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getPath());
            try {
                XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
                DesignPanel designPanel = (DesignPanel) decoder.readObject();
                decoder.close();
                this.mainFrame.updateDesignPanel(designPanel);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void savePanel(ActionEvent event){
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Choose where to save your file");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile() + "\\image.xml");
            try {
                XMLEncoder encoder = new XMLEncoder(
                        new BufferedOutputStream(
                                new FileOutputStream(file)));

                encoder.writeObject(this.mainFrame.getDesignPanel());
                encoder.close();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}