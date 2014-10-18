package org.vsu.eco.view.taxon;

import org.springframework.context.ApplicationContext;

import javax.swing.*;
import java.awt.*;

/**
 * Created by arusov on 3/9/14.
 */
public class TaxonPanel extends JPanel {

    private JScrollPane scrollPane;

    private ApplicationContext applicationContext;

    private TaxonTable taxonTable;

    public TaxonPanel( ApplicationContext applicationContext) {
        super();
        this.applicationContext=applicationContext;
        init();
        add(getScrollPane());

    }



    private void init() {
        setLayout(new GridLayout(1, 1));
    }
    private JScrollPane getScrollPane() {
        if (scrollPane == null) {
            scrollPane = new JScrollPane(getTaxonTable());
        }
        return scrollPane;
    }

    private TaxonTable getTaxonTable() {
        if (taxonTable == null) {
            taxonTable = new TaxonTable(applicationContext);
        }
        return taxonTable;
    }

}
