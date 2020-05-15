package gui;

import gui.panels.ControlPanel;
import gui.panels.DesignPanel;
import javax.swing.*;
import java.awt.*;
import lombok.Getter;

/**
 * class MainFrame of type JFrame, that will also represent the main class of the application.
 * The frame will contain a ControlPanel in the north and a DesignPanel in the center.
 */

@Getter
public class MainFrame extends JFrame {
    private ControlPanel controlPanel;
    private DesignPanel designPanel;

    public MainFrame() {
        super("Dynamic_Swing_Designer");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initPanels();
        addElements();
        pack();
    }

    private void initPanels() {
        controlPanel = new ControlPanel(this);
        designPanel = new DesignPanel(this, 700, 600);
    }


    private void addElements() {
        add(controlPanel, BorderLayout.NORTH);
        add(designPanel, BorderLayout.CENTER);
    }

}