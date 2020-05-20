package gui.panels;

import gui.MainFrame;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


/**
 * PropertiesTablePanel
 * Whenever the user sets the focus on an added component,
 * its properties should be displayed in a JTable component.
 */
@Getter
public class PropertiesTablePanel extends JPanel {
    private final MainFrame mainFrame;
    private final JTable propertiesTable;

    public PropertiesTablePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.propertiesTable = new JTable(new DefaultTableModel(new String[]{"Type", "Name"}, 50));
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        propertiesTable.setFillsViewportHeight(true);
        JScrollPane scrollTable = new JScrollPane(propertiesTable);
        add(scrollTable, BorderLayout.CENTER);
    }
}
