package gui.panels;

import gui.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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


    public ControlPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.componentClassTextField = initializeTextField("Component name");
        this.componentTextField = initializeTextField("Default text");
        addButton = new JButton("Add");
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
        addElementsToContainer();
        addListenersToButtons();
    }

    private void addElementsToContainer() {
        add(componentClassTextField);
        add(componentTextField);
        add(addButton);
    }

    private void addListenersToButtons() {
        addButton.addActionListener(this::add);
    }


    private void add(ActionEvent event) {
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
                System.out.println(e.getClass() + ": " + e.getMessage());
                return;
            }
        }
        this.mainFrame.getDesignPanel().addComponent(componentInstance);
    }

}