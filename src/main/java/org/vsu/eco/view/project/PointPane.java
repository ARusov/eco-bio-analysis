package org.vsu.eco.view.project;

import org.springframework.context.ApplicationContext;
import org.vsu.eco.model.ParameterH;
import org.vsu.eco.model.Point;
import org.vsu.eco.model.Project;

import javax.swing.*;
import java.util.List;

/**
 * Created by arusov on 4/2/14.
 */
public class PointPane extends JScrollPane {

    private ApplicationContext applicationContext;

    private PointTable pointTable;

    private Point point;


    public PointPane(ApplicationContext applicationContext,Point point) {
        this.applicationContext=applicationContext;
        this.point=point;
        setViewportView(getPointTable());
    }

    public PointTable getPointTable(){
        if(pointTable==null){
            pointTable= new PointTable(applicationContext, point);
        }
        return pointTable;

    }
    public List<ParameterH> getParameterHList(){
        return getPointTable().getParameterHList();
    }


}
