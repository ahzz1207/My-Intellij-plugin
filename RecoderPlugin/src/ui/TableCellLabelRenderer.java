package ui;

import sun.swing.DefaultLookup;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by chenchi on 18/6/6.
 */
public class TableCellLabelRenderer extends DefaultTableCellRenderer{
    private Color color;

    public TableCellLabelRenderer(){
        color = Color.black;
    }

    public TableCellLabelRenderer(Color color){
        this.color = color;
    }

    public void setValue(Object value){
        super.setValue(value);
        setForeground(color);
    }

    public Component getTableCellRendererComponent(JTable jtable, Object obj,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        Color fg = null;
        Color bg = new Color(253,251,220);
        JTable.DropLocation dropLocation = jtable.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsertRow()
                && !dropLocation.isInsertColumn()
                && dropLocation.getRow() == row
                && dropLocation.getColumn() == column) {

            fg = DefaultLookup.getColor(this, ui, "Table.dropCellForeground");
            bg = DefaultLookup.getColor(this, ui, "Table.dropCellBackground");

            isSelected = true;
        }

        if (isSelected) {
            super.setForeground(fg == null ? jtable.getSelectionForeground()
                    : fg);
            super.setBackground(bg == null ? jtable.getSelectionBackground()
                    : bg);
        } else {
            Color background = jtable.getBackground();
            if (background == null || background instanceof javax.swing.plaf.UIResource) {
                Color alternateColor = DefaultLookup.getColor(this, ui, "Table.alternateRowColor");
                if (alternateColor != null && row % 2 != 0) {
                    background = alternateColor;
                }
            }
            super.setForeground(jtable.getForeground());
            super.setBackground(background);
        }
        setFont(jtable.getTableHeader().getFont());
        setForeground(color);
        setText(obj == null ? "" : obj.toString());
        return this;
    }

}
