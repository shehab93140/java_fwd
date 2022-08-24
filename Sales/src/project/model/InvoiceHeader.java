/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author DELL
 */
public class InvoiceHeader {
    private int num;
    private Date date;
    private String name;
    private ArrayList<InvoiceLine> lines;

    public InvoiceHeader(int num, Date date, String name) {
        this.num = num;
        this.date = date;
        this.name = name;
    }

    public ArrayList<InvoiceLine> getLines() {
        // Lazy Loading
        if (lines == null)
            lines = new ArrayList<>();
        return lines;
    }
    
    public double getTotal() {
        double total = 0.0;
        
        for (InvoiceLine line : getLines()) {
            total += line.getTotal();
        }
        
        return total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" + "num=" + num + 
                ", date=" + date + ", name=" + name + '}';
    }
    
}
