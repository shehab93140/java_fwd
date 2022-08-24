/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import project.model.InvoiceHeader;
import project.model.InvoiceHeaderTableModel;
import project.model.InvoiceLine;
import project.model.InvoiceLineTableModel;
import project.view.InvoiceDialog;
import project.view.LineDialog;
import project.view.SIGFrame;

/**
 *
 * @author DELL
 */
public class Controller implements ActionListener, ListSelectionListener {

    private SIGFrame frame;
    private InvoiceDialog invDialog;
    private LineDialog lineDialog;
    
    public Controller(SIGFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Create New Item":
                createNewItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "Load Files":
                loadFiles(null, null);
                break;
            case "Save Data":
                savData();
                break;
            case "createInvoiceOK":
                createInvoiceOK();
                break;
            case "createInvoiceCancel":
                createInvoiceCancel();
                break;
            case "createLineOK":
                createLineOK();
                break;
            case "createLineCancel":
                createLineCancel();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedRow = frame.getHeaderTable().getSelectedRow();
        if (selectedRow >= 0) {
            InvoiceHeader inv = frame.getInvoices().get(selectedRow);
            frame.getInvNumLbl().setText(""+inv.getNum());
            frame.getCustNameLbl().setText(inv.getName());
            frame.getInvDateLbl().setText(SIGFrame.sdf.format(inv.getDate()));
            frame.getInvTotalLbl().setText(""+inv.getTotal());
            
            frame.getLineTable().setModel(new InvoiceLineTableModel(inv.getLines()));
        }
    }

    private void createNewInvoice() {
        invDialog = new InvoiceDialog(frame);
        invDialog.setVisible(true);
    }

    private void deleteInvoice() {
    }

    private void createNewItem() {
        lineDialog = new LineDialog(frame);
        lineDialog.setVisible(true);
    }

    private void deleteItem() {
    }

    public void loadFiles(String headrPath, String linePath) {
        File headerFile = null;
        File lineFile = null;
        if (headrPath == null && linePath == null) {
            JFileChooser fc = new JFileChooser();
            int result = fc.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                headerFile = fc.getSelectedFile();
                result = fc.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    lineFile = fc.getSelectedFile();
                }
            }
        } else {
            headerFile = new File(headrPath);
            lineFile = new File(linePath);
        }

        if (headerFile != null && lineFile != null) {
            try {
                /*
                1,22-11-2020,Ali
                2,13-10-2021,Saleh
                 */
                // collection streams
                List<String> headerLines = Files.lines(Paths.get(headerFile.getAbsolutePath())).collect(Collectors.toList());
                /*
                1,Mobile,3200,1
                1,Cover,20,2
                1,Headphone,130,1	
                2,Laptop,9000,1
                2,Mouse,135,1
                 */
                List<String> lineLines = Files.lines(Paths.get(lineFile.getAbsolutePath())).collect(Collectors.toList());
                //ArrayList<Invoice> invoices = new ArrayList<>();
                frame.getInvoices().clear();
                for (String headerLine : headerLines) {
                    String[] parts = headerLine.split(",");  // "1,22-11-2020,Ali"  ==>  ["1", "22-11-2020", "Ali"]
                    String numString = parts[0];
                    String dateString = parts[1];
                    String name = parts[2];
                    int num = Integer.parseInt(numString);
                    Date date = frame.sdf.parse(dateString);
                    InvoiceHeader inv = new InvoiceHeader(num, date, name);
                    //invoices.add(inv);
                    frame.getInvoices().add(inv);
                }
                System.out.println("Check point");
                for (String lineLine : lineLines) {
                    // lineLine = "1,Mobile,3200,1"
                    String[] parts = lineLine.split(",");
                    /*
                    parts = ["1", "Mobile", "3200", "1"]
                     */
                    int num = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int count = Integer.parseInt(parts[3]);
                    InvoiceHeader inv = frame.getInvoiceByNum(num);
                    InvoiceLine line = new InvoiceLine(name, price, count, inv);
                    inv.getLines().add(line);
                }
                System.out.println("Check point");
                frame.setHeaderTableModel(new InvoiceHeaderTableModel(frame.getInvoices()));
                frame.getHeaderTable().setModel(frame.getHeaderTableModel());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void savData() {
    }

    private void createInvoiceOK() {
        String dateStr = invDialog.getInvDateField().getText();
        String name = invDialog.getCustNameField().getText();
        invDialog.setVisible(false);
        invDialog.dispose();
        invDialog = null;
        try {
            Date date = SIGFrame.sdf.parse(dateStr);
            int num = frame.getNextInvNum();
            InvoiceHeader header = new InvoiceHeader(num, date, name);
            frame.getInvoices().add(header);
            frame.getHeaderTableModel().fireTableDataChanged();
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(frame, "Error in Date format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createInvoiceCancel() {
        invDialog.setVisible(false);
        invDialog.dispose();
        invDialog = null;
    }

    private void createLineOK() {
        
    }

    private void createLineCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

}
