package org.vsu.eco.model;

import javax.persistence.*;

/**
 * Created by arusov on 3/9/14.
 */
@Entity
@Table(name = "taxons")
public class Taxon {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String values;

    private String valuex;

    private String valueo;

    private String valueb;

    private String valuea;

    private String valuep;

    private String valueG;

    private String value_S;

    private String comment;

    public Taxon() {
    }

    public Taxon(String name, String values, String valuex, String valueo, String valueb, String valuea, String valuep, String valueG, String valueS, String comment) {
        super();
        this.name = name;
        this.values = values;
        this.valuex = valuex;
        this.valueo = valueo;
        this.valueb = valueb;
        this.valuea = valuea;
        this.valuep = valuep;
        this.valueG = valueG;
        this.value_S = valueS;
        this.comment = comment;
    }

    public Object[] toObject() {
        return new Object[]{getId(), getName(), getValues(), getValuex(), getValueo(), getValueb(), getValuea(), getValuep(), getValueG(), getValue_S(), getComment()};
    }

    public static String[] getColumnNames() {
        return new String[]{"Id", "Таксон", "s", "x", "o", "b", "a", "p", "G", "S", "Комментари"};
    }

    @Override
    public String toString() {
        return getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getValuex() {
        return valuex;
    }

    public void setValuex(String valuex) {
        this.valuex = valuex;
    }

    public String getValueo() {
        return valueo;
    }

    public void setValueo(String valueo) {
        this.valueo = valueo;
    }

    public String getValueb() {
        return valueb;
    }

    public void setValueb(String valueb) {
        this.valueb = valueb;
    }

    public String getValuea() {
        return valuea;
    }

    public void setValuea(String valuea) {
        this.valuea = valuea;
    }

    public String getValuep() {
        return valuep;
    }

    public void setValuep(String valuep) {
        this.valuep = valuep;
    }

    public String getValueG() {
        return valueG;
    }

    public void setValueG(String valueG) {
        this.valueG = valueG;
    }

    public String getValue_S() {
        return value_S;
    }

    public void setValue_S(String value_S) {
        this.value_S = value_S;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
