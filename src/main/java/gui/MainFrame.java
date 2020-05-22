package gui;

import gui.panels.ControlPanel;
import gui.panels.DesignPanel;
import javax.swing.*;
import java.awt.*;

import gui.panels.PropertiesTablePanel;
import lombok.Getter;

/**
 * class MainFrame of type JFrame, that will also represent the main class of the application.
 * The frame will contain a ControlPanel in the north and a DesignPanel in the center.
 */

@Getter
public class MainFrame extends JFrame {
    private ControlPanel controlPanel;
    private DesignPanel designPanel;
    private PropertiesTablePanel propertiesTablePanel;

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
        propertiesTablePanel = new PropertiesTablePanel(this);
    }

    private void addElements() {
        add(controlPanel, BorderLayout.NORTH);
        add(designPanel, BorderLayout.CENTER);
        add(propertiesTablePanel, BorderLayout.EAST);
    }

    public void updateDesignPanel(DesignPanel designPanel) {
        remove(this.designPanel);
        this.designPanel = designPanel;
        add(designPanel, BorderLayout.CENTER);
        pack();
    }
}