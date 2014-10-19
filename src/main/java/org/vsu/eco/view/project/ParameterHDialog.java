package org.vsu.eco.view.project;

import org.vsu.eco.model.ParameterH;
import org.vsu.eco.model.Taxon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by arusov on 10/18/14.
 */
public class ParameterHDialog extends JFrame {

    private org.vsu.eco.model.Point point;
    private JPanel mainPanel;
    private JLabel idLabel = new JLabel("Id");
    private JTextField id = new CustomJTextField();
    private JLabel taxonLabel = new JLabel("Таксон");
    private JComboBox taxon = new JComboBox();
    private JLabel hLabel = new JLabel("h");
    private JTextField h = new JTextField();
    private JLabel sLabel = new JLabel("s");
    private JTextField s = new CustomJTextField();
    private JLabel xLabel = new JLabel("x");
    private JTextField x = new CustomJTextField();
    private JLabel oLabel = new JLabel("o");
    private JTextField o = new CustomJTextField();
    private JLabel bLabel = new JLabel("b");
    private JTextField b = new CustomJTextField();
    private JLabel aLabel = new JLabel("a");
    private JTextField a = new CustomJTextField();
    private JLabel pLabel = new JLabel("p");
    private JTextField p = new CustomJTextField();
    private JLabel x1Label = new JLabel("x'");
    private JTextField x1 = new CustomJTextField();
    private JLabel o1Label = new JLabel("o'");
    private JTextField o1 = new CustomJTextField();
    private JLabel b1Label = new JLabel("b'");
    private JTextField b1 = new CustomJTextField();
    private JLabel a1Label = new JLabel("a'");
    private JTextField a1 = new CustomJTextField();
    private JLabel p1Label = new JLabel("p'");
    private JTextField p1 = new CustomJTextField();
    private JLabel _sLabel = new JLabel("S");
    private JTextField _s = new CustomJTextField();
    private JLabel shLabel = new JLabel("sh");
    private JTextField sh = new CustomJTextField();
    private JLabel lineLabel = new JLabel("Ср. линия");
    private JTextField line = new CustomJTextField();
    private JButton saveButton = new JButton("Сохранить");
    private JButton cancelButton = new JButton("Отмена");

    public ParameterHDialog(ParameterH parameterH, PointTable pointTable, org.vsu.eco.model.Point point) {
        super("Создать/изменить таксон");
        this.point = point;
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 3);
        int y = (int) ((dimension.getHeight() - getHeight()) / 3);
        setLocation(x, y);
        setSize(400, 450);
        init(parameterH, pointTable);
    }

    private void init(ParameterH parameterH, PointTable pointTable) {
        add(getMainPanel());
        setValues(parameterH);
        addActions(parameterH, pointTable);
        setUpTaxons(parameterH, pointTable);
    }

    private void setUpTaxons(ParameterH parameterH, PointTable pointTable) {
        List<Taxon> taxons = pointTable.getTaxonDao().getAllTaxons();
        int index = 0;
        for (Taxon item : taxons) {
            taxon.addItem(item);
            if (parameterH.getTaxon() != null) {
                if (item.getId() == parameterH.getTaxon().getId()) {
                    taxon.setSelectedIndex(index);
                }
            }
            index++;
        }

    }

    private void addActions(final ParameterH parameterH, final PointTable pointTable) {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parameterH.setPoint(point);
                Taxon t = (Taxon) taxon.getSelectedItem();
                parameterH.setTaxon(t);
                parameterH.setH(h.getText());
                pointTable.getProjectDao().addParameter(parameterH);
                pointTable.refreshData();
                setVisible(false);
            }
        });
    }

    private void setValues(ParameterH parameterH) {
        id.setText(String.valueOf(parameterH.getId()));
        id.setEditable(false);
        h.setText(parameterH.getH());
        if (parameterH.getTaxon() != null) {
            s.setText(parameterH.getTaxon().getValues());
            x.setText(parameterH.getTaxon().getValuex());
            o.setText(parameterH.getTaxon().getValueo());
            b.setText(parameterH.getTaxon().getValueb());
            a.setText(parameterH.getTaxon().getValuea());
            p.setText(parameterH.getTaxon().getValuep());

            x1.setText(parameterH.getValuex());
            o1.setText(parameterH.getValueo());
            b1.setText(parameterH.getValueb());
            a1.setText(parameterH.getValuea());
            p1.setText(parameterH.getValuep());
            _s.setText(parameterH.getTaxon().getValue_S());
            sh.setText(parameterH.getSh());
            line.setText(parameterH.getLine());
        }
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


        JPanel panelTaxon = new JPanel(new GridLayout());
        panelTaxon.add(taxonLabel);
        panelTaxon.add(taxon);
        mainPanel.add(panelTaxon);

        JPanel hTaxon = new JPanel(new GridLayout());
        hTaxon.add(hLabel);
        hTaxon.add(h);
        mainPanel.add(hTaxon);
//######

        JPanel panelProp = new JPanel(new GridLayout());
        panelProp.add(sLabel);
        panelProp.add(s);
        mainPanel.add(panelProp);


        panelProp = new JPanel(new GridLayout());
        panelProp.add(xLabel);
        panelProp.add(x);
        mainPanel.add(panelProp);

        panelProp = new JPanel(new GridLayout());
        panelProp.add(oLabel);
        panelProp.add(o);
        mainPanel.add(panelProp);

        panelProp = new JPanel(new GridLayout());
        panelProp.add(bLabel);
        panelProp.add(b);
        mainPanel.add(panelProp);

        panelProp = new JPanel(new GridLayout());
        panelProp.add(aLabel);
        panelProp.add(a);
        mainPanel.add(panelProp);

        panelProp = new JPanel(new GridLayout());
        panelProp.add(pLabel);
        panelProp.add(p);
        mainPanel.add(panelProp);

        panelProp = new JPanel(new GridLayout());
        panelProp.add(x1Label);
        panelProp.add(x1);
        mainPanel.add(panelProp);

        panelProp = new JPanel(new GridLayout());
        panelProp.add(o1Label);
        panelProp.add(o1);
        mainPanel.add(panelProp);

        panelProp = new JPanel(new GridLayout());
        panelProp.add(b1Label);
        panelProp.add(b1);
        mainPanel.add(panelProp);

        panelProp = new JPanel(new GridLayout());
        panelProp.add(a1Label);
        panelProp.add(a1);
        mainPanel.add(panelProp);

        panelProp = new JPanel(new GridLayout());
        panelProp.add(p1Label);
        panelProp.add(p1);
        mainPanel.add(panelProp);

        panelProp = new JPanel(new GridLayout());
        panelProp.add(_sLabel);
        panelProp.add(_s);
        mainPanel.add(panelProp);

        panelProp = new JPanel(new GridLayout());
        panelProp.add(shLabel);
        panelProp.add(sh);
        mainPanel.add(panelProp);

        panelProp = new JPanel(new GridLayout());
        panelProp.add(lineLabel);
        panelProp.add(line);
        mainPanel.add(panelProp);
//######

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
            setEditable(false);
        }
    }
}
