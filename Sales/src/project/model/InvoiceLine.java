/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.model;

/**
 *
 * @author DELL
 */
public class InvoiceLine {
    private String name;
    private double price;
    private int count;
    private InvoiceHeader header;

    public InvoiceLine(String name, double price, int count, InvoiceHeader header) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.header = header;
    }

    public double getTotal() {
        return price * count;
    }
    
    public InvoiceHeader getHeader() {
        return header;
    }

    public void setHeader(InvoiceHeader header) {
        this.header = header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "InvoiceLine{" + "name=" + name + 
                ", price=" + price + ", count=" + count + '}';
    }
    
    
}
