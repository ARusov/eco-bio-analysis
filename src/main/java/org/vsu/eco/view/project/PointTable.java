package org.vsu.eco.view.project;

import org.jdesktop.swingx.JXTable;
import org.springframework.context.ApplicationContext;
import org.vsu.eco.dao.ProjectDao;
import org.vsu.eco.dao.TaxonDao;
import org.vsu.eco.model.*;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Created by arusov on 4/2/14.
 */
public class PointTable extends JXTable {

    private DefaultTableModel tableModel;

    private ApplicationContext applicationContext;

    private JPopupMenu pointTableMenu;

    private JMenuItem pointAddTaxonMenu;

    private JMenuItem pointRemoveTaxonMenu;

    private JMenuItem pointRemovePointMenu;

    private org.vsu.eco.model.Point point;

    public PointTable(ApplicationContext applicationContext, org.vsu.eco.model.Point point) {
        this.applicationContext = applicationContext;
        this.point = point;
        init();
    }

    private void init() {
        getTableModel().setColumnIdentifiers(getColumnNames());
        setVisibleRowCount(3);
        getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new TaxonComboBox(applicationContext, this)));
        setComponentPopupMenu(getPointTableMenu());
        addRightClickSelection();
        addChangeListener();

        if (point != null) {
            List<ParameterH> parameterHs = getProjectDao().getHforPoint(point);
            for (ParameterH h : parameterHs) {
                getTableModel().addRow(h.toObjectRow());
            }
        }

    }

    private void addChangeListener() {
        getDefaultEditor(Object.class).addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                ParameterH h = getParameterHfromRow(getSelectedRow());
                getProjectDao().addParameter(h);

            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        });
    }

    private void addRightClickSelection() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    Point p = e.getPoint();
                    int rowNumber = rowAtPoint(p);
                    ListSelectionModel model = getSelectionModel();
                    model.setSelectionInterval(rowNumber, rowNumber);
                }
            }
        });
    }

    public String[] getColumnNames() {
        return org.vsu.eco.model.Point.getColumnNames();
    }

    public DefaultTableModel getTableModel() {
        tableModel = (DefaultTableModel) getModel();
        return tableModel;
    }


    public JPopupMenu getPointTableMenu() {
        if (pointTableMenu == null) {
            pointTableMenu = new JPopupMenu();
            pointTableMenu.add(getPointAddTaxonMenu());
            pointTableMenu.add(getPointRemoveTaxonMenu());
            pointTableMenu.add(getPointRemovePointMenu());
        }
        return pointTableMenu;
    }

    public JMenuItem getPointAddTaxonMenu() {
        if (pointAddTaxonMenu == null) {
            pointAddTaxonMenu = new JMenuItem("Добавить таксон");
            pointAddTaxonMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ParameterH h = new ParameterH();
//                    point.getParameterH().add(h);
                    h.setPoint(point);
                    getProjectDao().addParameter(h);
                    getTableModel().addRow(h.toObjectRow());
                }
            });
        }
        return pointAddTaxonMenu;
    }



    public JMenuItem getPointRemoveTaxonMenu() {
        if (pointRemoveTaxonMenu == null) {
            pointRemoveTaxonMenu = new JMenuItem("Удалить таксон");
            pointRemoveTaxonMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ParameterH h = getParameterHfromRow(getSelectedRow());
                    getProjectDao().removeParamH(h);
                    getTableModel().removeRow(getSelectedRow());
                }
            });
        }
        return pointRemoveTaxonMenu;
    }

    public JMenuItem getPointRemovePointMenu() {
        if (pointRemovePointMenu == null) {
            pointRemovePointMenu = new JMenuItem("Удалить точку");
            pointRemovePointMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getProjectDao().removePoint(point);
                    getMe().getParent().remove(getMe());
                    getMe().getParent().validate();
                    getMe().getParent().repaint();
                }
            });
        }
        return pointRemovePointMenu;
    }

    private ProjectDao getProjectDao() {
        return (ProjectDao) applicationContext.getBean("projectDao");
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column == 3 || column == 4 || column == 5 || column == 6 || column == 7 || column == 8) {
            return false;
        }
        return true;
    }


    protected ParameterH getParameterHfromRow(int row) {

        ParameterH h = getProjectDao().getH((Long) getTableModel().getValueAt(row, 0));
        h.setTaxon((Taxon) getTableModel().getValueAt(row, 1));
        h.setH((String) getTableModel().getValueAt(row, 2));
        return h;
    }

    public List<ParameterH> getParameterHList() {
        List<ParameterH> hList = new ArrayList<ParameterH>();
        for (int i = 0; i < getTableModel().getRowCount(); i++) {
            ParameterH h = getParameterHfromRow(i);
            hList.add(h);
        }
        return hList;
    }

    private PointTable getMe(){
        return this;
    }

}
