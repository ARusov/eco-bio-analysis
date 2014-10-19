package org.vsu.eco.view.project;

import org.jdesktop.swingx.JXTable;
import org.springframework.context.ApplicationContext;
import org.vsu.eco.dao.ProjectDao;
import org.vsu.eco.dao.TaxonDao;
import org.vsu.eco.model.ParameterH;
import org.vsu.eco.model.Taxon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arusov on 4/2/14.
 */
public class PointTable extends JXTable {

    private DefaultTableModelNotEditable tableModel;
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

    public void refreshData() {
        setData();

    }

    private void setData() {
        setModel(new DefaultTableModelNotEditable());
        getTableModel().setColumnIdentifiers(getColumnNames());
        getColumnModel().removeColumn(getColumnModel().getColumn(0));
        if (point != null) {
            List<ParameterH> parameterHs = getProjectDao().getHforPoint(point);
            if (parameterHs.size() > 1) {
                setVisibleRowCount(parameterHs.size());
            } else {
                setVisibleRowCount(2);
            }
            for (ParameterH h : parameterHs) {
                getTableModel().addRow(h.toObjectRow());
            }
        }
    }

    public void init() {
        setData();
        setComponentPopupMenu(getPointTableMenu());
        addRightClickSelection();
        addDoubleClick();


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
        tableModel = (DefaultTableModelNotEditable) getModel();
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
                    saveParameterHDialog(new ParameterH(), point);
                }
            });
        }
        return pointAddTaxonMenu;
    }

    private void saveParameterHDialog(ParameterH parameterH, org.vsu.eco.model.Point point) {
        ParameterHDialog hDialog = new ParameterHDialog(parameterH, this, point);
        hDialog.setVisible(true);
    }

    public JMenuItem getPointRemoveTaxonMenu() {
        if (pointRemoveTaxonMenu == null) {
            pointRemoveTaxonMenu = new JMenuItem("Удалить таксон");
            pointRemoveTaxonMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ParameterH h = getParameterHByRow(getSelectedRow());
                    getProjectDao().removeParamH(h);
                    refreshData();
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

    public ProjectDao getProjectDao() {
        return (ProjectDao) applicationContext.getBean("projectDao");
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

    private PointTable getMe() {
        return this;
    }

    private void addDoubleClick() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    ParameterH parameterH = getParameterHByRow(row);
                    saveParameterHDialog(parameterH, point);
                }
            }
        });
    }

    private ParameterH getParameterHByRow(int row) {
        long paramId = (Long) getTableModel().getValueAt(row, 0);
        return getProjectDao().getH(paramId);
    }

    public TaxonDao getTaxonDao() {
        return (TaxonDao) applicationContext.getBean("taxonDao");
    }

    public class DefaultTableModelNotEditable extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

}
