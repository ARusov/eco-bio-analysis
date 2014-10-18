package org.vsu.eco.view.project;

import org.springframework.context.ApplicationContext;
import org.vsu.eco.dao.ProjectDao;
import org.vsu.eco.dao.TaxonDao;
import org.vsu.eco.model.ParameterH;
import org.vsu.eco.model.Taxon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

/**
 * Created by arusov on 4/2/14.
 */
public class TaxonComboBox extends JComboBox {

    private ApplicationContext applicationContext;
    private PointTable parent;


    public TaxonComboBox(ApplicationContext applicationContext, PointTable parent) {
        this.applicationContext = applicationContext;
        this.parent = parent;
        setUp();

    }

    private void setUp() {
        List<Taxon> taxons = getTaxonDAO().getAllTaxons();
        for (Taxon t : taxons) {
            addItem(t);
        }
        addItemListener(new ItemChangeListener());
    }

    private TaxonDao getTaxonDAO() {
        return (TaxonDao) applicationContext.getBean("taxonDao");
    }

    private ProjectDao getProjectDao() {
        return (ProjectDao) applicationContext.getBean("projectDao");
    }

    class ItemChangeListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent event) {
              if(event.getStateChange()==ItemEvent.SELECTED){
                  System.out.println("###############");
                  ParameterH h = parent.getParameterHfromRow(parent.getSelectedRow());
                  Taxon taxon=(Taxon) getSelectedItem();
                  h.setTaxon(taxon);
                  getProjectDao().addParameter(h);
              }
        }
    }


}
