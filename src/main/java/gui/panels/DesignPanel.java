package gui.panels;

import gui.MainFrame;
import lombok.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.Serializable;

/**
 * The DesignPanel represents a simple JPanel,
 * using absolute positioning of its components and
 * containing Swing components added by our application.
 */
@NoArgsConstructor
public class DesignPanel extends JPanel implements Serializable {
    private MainFrame frame;
    private int width;
    private int height;

    public DesignPanel(MainFrame frame, int width, int height) {
        this.frame = frame;
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(this.width, this.height));
    }

    public void addComponent(Component component) {
        add(component);
        revalidate();
        repaint();
    }

    /**
     * Whenever the user sets the focus on an added component,
     * its properties should be displayed in a JTable component.
     * Use the java.beans.Introspector class to get the properties
     * of a Swing component.
     */
    public void addFocusListenerToComponent(Component component) {
        component.addFocusListener(new FocusListener() {
            @SneakyThrows
            @Override
            public void focusGained(FocusEvent e) {
                Class<?> componentClass = component.getClass();
                BeanInfo info = Introspector.getBeanInfo(componentClass);
                int i = 0;
                DefaultTableModel model = (DefaultTableModel) frame.getPropertiesTablePanel().getPropertiesTable().getModel();
                for (PropertyDescriptor propertyDescriptor : info.getPropertyDescriptors()) {
                    model.setValueAt(String.valueOf(propertyDescriptor.getPropertyType()), i, 0);
                    model.setValueAt(String.valueOf(propertyDescriptor.getName()), i, 1);
                    i++;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
    }
}