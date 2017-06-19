package hu.elte;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.jdatepicker.DefaultComponentFactory;
import org.jdatepicker.JDatePicker;

public class DateCellEditor extends DefaultCellEditor implements ActionListener {

    public DateCellEditor() {
        super(new JTextField());
        delegate = new EditorDelegate() {
            private final Calendar calendar = Calendar.getInstance();
            private JDatePicker jDatePicker;

            @Override
            public void setValue(Object value) {
                calendar.setTime((Date) value);
                jDatePicker = (JDatePicker) new DefaultComponentFactory().createJDatePicker(calendar);
                jDatePicker.addActionListener(this);
                editorComponent = (JComponent) jDatePicker;
            }

            @Override
            public Object getCellEditorValue() {
                Object valuue = jDatePicker.getModel().getValue();
                if(valuue == null) {
                    return new Date();
                }
                return ((Calendar) valuue).getTime();
            }

        };
    }

    private int oldHeight;
    private JTable table;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        oldHeight = table.getRowHeight(row);
        table.setRowHeight(row, editorComponent.getPreferredSize().height);
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }

    @Override
    public boolean stopCellEditing() {
        table.setRowHeight(oldHeight);
        return super.stopCellEditing();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        EventQueue.invokeLater(() -> {
            stopCellEditing();
        });
    }

}
