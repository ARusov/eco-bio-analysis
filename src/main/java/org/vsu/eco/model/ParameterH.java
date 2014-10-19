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
    private String h = "1";
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taxons_id")
    private Taxon taxon;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;
    @Transient
    private String valuex;
    @Transient
    private String valueo;
    @Transient
    private String valueb;
    @Transient
    private String valuea;
    @Transient
    private String valuep;
    @Transient
    private String sh;
    @Transient
    private String line;

    private double getDouble(String value) {
        double x = 0;
        try {
            x = Double.parseDouble(value);
        } catch (NullPointerException e) {
            return 0;
        } catch (NumberFormatException e) {
            return 0;
        }
        return x;
    }

    public String getLine() {
//        (o+b+x)-(a+p)
        if (getTaxon() != null) {

            double x = getDouble(getTaxon().getValuex());
            double b = getDouble(getTaxon().getValueb());
            double o = getDouble(getTaxon().getValueo());
            double a = getDouble(getTaxon().getValuea());
            double p = getDouble(getTaxon().getValuep());

            double lineN = (o + b + x) - (a + p);
            line = String.valueOf(lineN);


        }
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getValuex() {
        //h*x
        if (getTaxon() != null) {
            try {
                double x = Double.parseDouble(getTaxon().getValuex());
                double h = Double.parseDouble(getH());
                double x1 = x * h;
                valuex = String.valueOf(x1);
            } catch (NumberFormatException e) {
                valuex = getTaxon().getValuex();
            } catch (NullPointerException e) {
                valuex = getTaxon().getValuex();
            }
        }
        return valuex;
    }

    public void setValuex(String valuex) {
        this.valuex = valuex;
    }

    public String getValueo() {
        if (getTaxon() != null) {
            try {
                double o = Double.parseDouble(getTaxon().getValueo());
                double h = Double.parseDouble(getH());
                double o1 = o * h;
                valueo = String.valueOf(o1);
            } catch (NumberFormatException e) {
                return getTaxon().getValueo();
            } catch (NullPointerException npe) {

            }
        }
        return valueo;
    }

    public void setValueo(String valueo) {
        this.valueo = valueo;
    }

    public String getValueb() {
        if (getTaxon() != null) {
            try {
                double o = Double.parseDouble(getTaxon().getValueb());
                double h = Double.parseDouble(getH());
                double o1 = o * h;
                valueb = String.valueOf(o1);
            } catch (NumberFormatException e) {
                return getTaxon().getValueb();
            } catch (NullPointerException npe) {

            }
        }
        return valueb;
    }

    public void setValueb(String valueb) {
        this.valueb = valueb;
    }

    public String getValuea() {
        if (getTaxon() != null) {
            try {
                double o = Double.parseDouble(getTaxon().getValuea());
                double h = Double.parseDouble(getH());
                double o1 = o * h;
                valuea = String.valueOf(o1);
            } catch (NumberFormatException e) {
                return getTaxon().getValuea();
            } catch (NullPointerException npe) {

            }
        }
        return valuea;
    }

    public void setValuea(String valuea) {
        this.valuea = valuea;
    }

    public String getValuep() {
        if (getTaxon() != null) {
            try {
                double o = Double.parseDouble(getTaxon().getValuep());
                double h = Double.parseDouble(getH());
                double o1 = o * h;
                valuep = String.valueOf(o1);
            } catch (NumberFormatException e) {
                return getTaxon().getValuep();
            } catch (NullPointerException npe) {

            }
        }
        return valuep;
    }

    public void setValuep(String valuep) {
        this.valuep = valuep;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Object[] toObjectRow() {
        Object[] row = new Object[17];
        row[0] = getId();
        if (getTaxon() != null) {
            row[1] = getTaxon().getName();
            row[3] = getTaxon().getValues();

            row[4] = getTaxon().getValuex();
            row[5] = getTaxon().getValueo();
            row[6] = getTaxon().getValueb();
            row[7] = getTaxon().getValuea();
            row[8] = getTaxon().getValuep();

            row[9] = getValuex();
            row[10] = getValueo();
            row[11] = getValueb();
            row[12] = getValuea();
            row[13] = getValuep();
            row[14] = getTaxon().getValue_S();
            row[15] = getSh();
            row[16] = getLine();


        }
        row[2] = getH();

        return row;
//        return new Object[]{getId(),getParameterH().getTaxon(), getParameterH().getH(), getParameterH().getTaxon().getValue_S(),getParameterH().getTaxon().getValuex(),
//                getParameterH().getTaxon().getValueo(),getParameterH().getTaxon().getValueb(), getParameterH().getTaxon().getValuea(),getParameterH().getTaxon().getValuep()};
    }

    public String getSh() {
        if (getTaxon() != null) {
            try {
                double o = Double.parseDouble(getTaxon().getValue_S());
                double h = Double.parseDouble(getH());
                double o1 = o * h;
                sh = String.valueOf(o1);
            } catch (NumberFormatException e) {
                return getTaxon().getValue_S();
            } catch (NullPointerException npe) {

            }
        }
        return sh;
    }

    public void setSh(String sh) {
        this.sh = sh;
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
