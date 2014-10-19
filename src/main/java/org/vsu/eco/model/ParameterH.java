package org.vsu.eco.model;

import javax.persistence.*;

/**
 * Created by arusov on 4/8/14.
 */
@Entity
@Table(name = "parameter")
public class ParameterH {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "h")
    private String h;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taxons_id")
    private Taxon taxon;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Object[] toObjectRow() {
        Object[] row = new Object[9];
        row[0] = getId();
        if (getTaxon() != null) {
            row[1] = getTaxon().getName();
        }
        row[2] = getH();
        return row;
//        return new Object[]{getId(),getParameterH().getTaxon(), getParameterH().getH(), getParameterH().getTaxon().getValue_S(),getParameterH().getTaxon().getValuex(),
//                getParameterH().getTaxon().getValueo(),getParameterH().getTaxon().getValueb(), getParameterH().getTaxon().getValuea(),getParameterH().getTaxon().getValuep()};
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Taxon getTaxon() {
        return taxon;
    }

    public void setTaxon(Taxon taxon) {
        this.taxon = taxon;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }
}
