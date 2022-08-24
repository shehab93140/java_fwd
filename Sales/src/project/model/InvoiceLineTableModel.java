
package project.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoiceLineTableModel extends AbstractTableModel {

    private String[] columns = {"Item", "Price", "Count", "Total"};
    private ArrayList<InvoiceLine> lines;

    public InvoiceLineTableModel(ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }
    
    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLine line = lines.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return line.getName();
            case 1: return line.getPrice();
            case 2: return line.getCount();
            case 3: return line.getTotal();
        }
        
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
    
    
}
