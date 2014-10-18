package org.vsu.eco.view.taxon;

import org.springframework.context.ApplicationContext;
import org.vsu.eco.dao.TaxonDao;
import org.vsu.eco.model.Taxon;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

/**
 * Created by arusov on 3/9/14.
 */

public class TaxonTable extends JTable {

    private DefaultTableModel tableModel;
    private JPopupMenu deletePopupMenu;
    private ApplicationContext applicationContext;

    public TaxonTable(ApplicationContext applicationContext) {
        super();
        this.applicationContext = applicationContext;
        init();
    }

    private void init() {
        getTableModel().setColumnIdentifiers(getColumnNames());
        getColumnModel().removeColumn(getColumnModel().getColumn(0));
        setData();
        addEmptyRow();
        addChangeListener();
        addRightClick();
    }

    private void addChangeListener() {
        getDefaultEditor(String.class).addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                Taxon taxon = getTaxon(getSelectedRow());
                if (taxon.getId() == 0) {
                    getTaxonDAO().createTaxon(taxon);
                    refreshData();
                } else {
                    getTaxonDAO().updateTaxon(taxon);
                    refreshData();
                }
            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        });

    }

    private void refreshData() {
        setModel(new DefaultTableModel());
        getTableModel().setColumnIdentifiers(getColumnNames());
        setData();
        addEmptyRow();
    }

    private void addRightClick() {
        addRightClickSelection();
        setComponentPopupMenu(getDeletePopupMenu());
    }

    private DefaultTableModel getTableModel() {
        tableModel= (DefaultTableModel) getModel();
        return tableModel;
    }

    private JPopupMenu getDeletePopupMenu() {
        if (deletePopupMenu == null) {
            deletePopupMenu = new JPopupMenu();
        }
        JMenuItem deleteItem = new JMenuItem("Удалить");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTaxonDAO().removeTaxon(getTaxon(getSelectedRow()));
                refreshData();
            }
        });
        deletePopupMenu.add(deleteItem);
        return deletePopupMenu;
    }

    public void addEmptyRow() {
        Taxon taxon = new Taxon();
        getTableModel().addRow(taxon.toObject());
    }

    public void setData() {
        List<Taxon> taxons = getTaxonDAO().getAllTaxons();
        if (taxons != null) {
            for (Taxon t : taxons) {
                getTableModel().addRow(t.toObject());
            }
        }

    }

    private String[] getColumnNames() {
        String[] columnNames = {"ID", "Таксоны", "s", "X", "o", "b", "a", "p", "G", "S", "Примечание"};
        return columnNames;
    }

    private TaxonDao getTaxonDAO() {
        return (TaxonDao) applicationContext.getBean("taxonDao");
    }

    private Taxon getTaxon(int rowIndex) {
        Taxon taxon = new Taxon();
        taxon.setId((Integer) getTableModel().getValueAt(rowIndex, 0));
        taxon.setName((String) getTableModel().getValueAt(rowIndex, 1));
        taxon.setValues((String) getTableModel().getValueAt(rowIndex, 2));
        taxon.setValuex((String) getTableModel().getValueAt(rowIndex, 3));
        taxon.setValueo((String) getTableModel().getValueAt(rowIndex, 4));
        taxon.setValueb((String) getTableModel().getValueAt(rowIndex, 5));
        taxon.setValuea((String) getTableModel().getValueAt(rowIndex, 6));
        taxon.setValuep((String) getTableModel().getValueAt(rowIndex, 7));
        taxon.setValueG((String) getTableModel().getValueAt(rowIndex, 8));
        taxon.setValue_S((String) getTableModel().getValueAt(rowIndex, 9));
        taxon.setComment((String) getTableModel().getValueAt(rowIndex, 10));
        return taxon;
    }
    private void addRightClickSelection() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)){
                    Point p = e.getPoint();
                    int rowNumber = rowAtPoint( p );
                    ListSelectionModel model = getSelectionModel();
                    model.setSelectionInterval( rowNumber, rowNumber );
                }
            }
        });
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        JComponent jc = (JComponent)c;

        if (isRowSelected(row)){
            int top = (row > 0 && isRowSelected(row-1))?1:2;
            int left = column == 0?2:0;
            int bottom = (row < getRowCount()-1 && isRowSelected(row + 1))?1:2;
            int right = column == getColumnCount()-1?2:0;

            jc.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, this.getSelectionBackground()));
        }
        else
            jc.setBorder(null);

        return c;
    }
}
