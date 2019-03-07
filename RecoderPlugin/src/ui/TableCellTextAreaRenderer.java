package ui;

import sun.swing.DefaultLookup;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by chenchi on 18/5/22.
 */
public class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer {
    private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    private static final Border DEFAULT_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    protected static Border noFocusBorder = DEFAULT_NO_FOCUS_BORDER;
    public TableCellTextAreaRenderer() {
        setLineWrap(true);
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

//        if (hasFocus) {
//            Border border = null;
//            if (isSelected) {
//                border = DefaultLookup.getBorder(this, ui, "Table.focusSelectedCellHighlightBorder");
//            }
//            if (border == null) {
//                border = DefaultLookup.getBorder(this, ui, "Table.focusCellHighlightBorder");
//            }
//            setBorder(border);
//
//            if (!isSelected && jtable.isCellEditable(row, column)) {
//                Color col;
//                col = DefaultLookup.getColor(this, ui, "Table.focusCellForeground");
//                if (col != null) {
//                    super.setForeground(col);
//                }
//                col = DefaultLookup.getColor(this, ui, "Table.focusCellBackground");
//                if (col != null) {
//                    super.setBackground(col);
//                }
//            }
//        } else {
//            setBorder(getNoFocusBorder());
//        }
        int currentColumnWidth = jtable.getColumnModel().getColumn(column).getWidth();
        setText(obj == null ? "" : obj.toString());
        setForeground(Color.black);
        setSize(currentColumnWidth, getPreferredSize().height);
        if (jtable.getRowHeight(row) != getPreferredSize().height) {  // 少了这行则处理器瞎忙
            jtable.setRowHeight(row, getPreferredSize().height);
        }
        return this;
    }

    private Border getNoFocusBorder() {
        Border border = DefaultLookup.getBorder(this, ui, "Table.cellNoFocusBorder");
        if (System.getSecurityManager() != null) {
            if (border != null) return border;
            return SAFE_NO_FOCUS_BORDER;
        } else if (border != null) {
            if (noFocusBorder == null || noFocusBorder == DEFAULT_NO_FOCUS_BORDER) {
                return border;
            }
        }
        return noFocusBorder;
    }
}
