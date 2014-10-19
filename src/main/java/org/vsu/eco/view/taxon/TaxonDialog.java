package org.vsu.eco.view.taxon;

import org.vsu.eco.model.Taxon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by arusov on 10/18/14.
 */
public class TaxonDialog extends JFrame {

    private JPanel mainPanel;
    private JLabel idLabel = new JLabel("Id");
    private JLabel nameLabel = new JLabel("Имя таксона");
    private JLabel valuesLabel = new JLabel("s");
    private JLabel valuexLabel = new JLabel("x");
    private JLabel valueoLabel = new JLabel("o");
    private JLabel valuebLabel = new JLabel("b");
    private JLabel valueaLabel = new JLabel("a");
    private JLabel valuepLabel = new JLabel("p");
    private JLabel valueGLabel = new JLabel("G");
    private JLabel value_SLabel = new JLabel("S");
    private JLabel commentLabel = new JLabel("Примечание");
    private JTextField id = new CustomJTextField();
    private JTextField name = new CustomJTextField();
    private JTextField values = new CustomJTextField();
    private JTextField valuex = new CustomJTextField();
    private JTextField valueo = new CustomJTextField();
    private JTextField valueb = new CustomJTextField();
    private JTextField valuea = new CustomJTextField();
    private JTextField valuep = new CustomJTextField();
    private JTextField valueG = new CustomJTextField();
    private JTextField value_S = new CustomJTextField();
    private JTextField comment = new CustomJTextField();
    private JButton saveButton = new JButton("Сохранить");
    private JButton cancelButton = new JButton("Отмена");

    public TaxonDialog(Taxon taxon, TaxonTable taxonTable) {
        super("Создать/изменить таксон");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 3);
        int y = (int) ((dimension.getHeight() - getHeight()) / 3);
        setLocation(x, y);
        setSize(400, 400);
        init(taxon, taxonTable);
    }

    private void init(Taxon taxon, TaxonTable taxonTable) {
        add(getMainPanel());
        setValues(taxon);
        addActions(taxon, taxonTable);
    }

    private void addActions(final Taxon taxon, final  TaxonTable taxonTable) {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taxon.setName(name.getText());
                taxon.setValues(values.getText());
                taxon.setValuex(valuex.getText());
                taxon.setValueo(valueo.getText());
                taxon.setValueb(valueb.getText());
                taxon.setValuea(valuea.getText());
                taxon.setValuep(valuep.getText());
                taxon.setValueG(valueG.getText());
                taxon.setValue_S(value_S.getText());
                taxon.setComment(comment.getText());
                taxonTable.getTaxonDAO().saveTaxon(taxon);
                taxonTable.refreshData();
                setVisible(false);
            }
        });
    }

    private void setValues(Taxon taxon) {
        id.setText(String.valueOf(taxon.getId()));
        id.setEditable(false);
        name.setText(taxon.getName());
        values.setText(taxon.getValues());
        valuex.setText(taxon.getValuex());
        valueo.setText(taxon.getValueo());
        valueb.setText(taxon.getValueb());
        valuea.setText(taxon.getValuea());
        valuep.setText(taxon.getValuep());
        valueG.setText(taxon.getValueG());
        value_S.setText(taxon.getValue_S());
        comment.setText(taxon.getComment());
    }

    public JPanel getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel();
        }
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        JPanel panelId = new JPanel(new GridLayout());
        panelId.add(idLabel);
        panelId.add(id);
        mainPanel.add(panelId);

        JPanel panelName = new JPanel(new GridLayout());
        panelName.add(nameLabel);
        panelName.add(name);
        mainPanel.add(panelName);

        JPanel valuesPanel = new JPanel(new GridLayout());
        valuesPanel.add(valuesLabel);
        valuesPanel.add(values);
        mainPanel.add(valuesPanel);

        JPanel valuexPanel = new JPanel(new GridLayout());
        valuexPanel.add(valuexLabel);
        valuexPanel.add(valuex);
        mainPanel.add(valuexPanel);

        JPanel valueoPanel = new JPanel(new GridLayout());
        valueoPanel.add(valueoLabel);
        valueoPanel.add(valueo);
        mainPanel.add(valueoPanel);

        JPanel valuebPanel = new JPanel(new GridLayout());
        valuebPanel.add(valuebLabel);
        valuebPanel.add(valueb);
        mainPanel.add(valuebPanel);

        JPanel valueaPanel = new JPanel(new GridLayout());
        valueaPanel.add(valueaLabel);
        valueaPanel.add(valuea);
        mainPanel.add(valueaPanel);

        JPanel valuepPanel = new JPanel(new GridLayout());
        valuepPanel.add(valuepLabel);
        valuepPanel.add(valuep);
        mainPanel.add(valuepPanel);

        JPanel valueGPanel = new JPanel(new GridLayout());
        valueGPanel.add(valueGLabel);
        valueGPanel.add(valueG);
        mainPanel.add(valueGPanel);

        JPanel value_SPanel = new JPanel(new GridLayout());
        value_SPanel.add(value_SLabel);
        value_SPanel.add(value_S);
        mainPanel.add(value_SPanel);

        JPanel commentPanel = new JPanel(new GridLayout());
        commentPanel.add(commentLabel);
        commentPanel.add(comment);
        mainPanel.add(commentPanel);

        JPanel buttonPanel = new JPanel(new GridLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel);


        return mainPanel;
    }

    public class CustomJTextField extends JTextField {
        public CustomJTextField() {
            super();
            setSize(150, idLabel.getHeight());
        }
    }
}
