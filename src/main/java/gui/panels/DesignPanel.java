package gui.panels;

import gui.MainFrame;
import lombok.*;
import javax.swing.*;
import java.awt.*;
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

}