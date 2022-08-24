
package project.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import project.view.SIGFrame;

public class InvoiceHeaderTableModel extends AbstractTableModel {

    private String[] columns = {"Inv Num", "Customer Name", "Inv Date", "Total"};
    private ArrayList<InvoiceHeader> invoices;
    
    public InvoiceHeaderTableModel(ArrayList<InvoiceHeader> invoices) {
        this.invoices = invoices;
    }
    
    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader inv = invoices.get(rowIndex);
        switch (columnIndex) {
            case 0: return inv.getNum();
            case 1: return inv.getName();
            case 2: return SIGFrame.sdf.format(inv.getDate());
            case 3: return inv.getTotal();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
}
