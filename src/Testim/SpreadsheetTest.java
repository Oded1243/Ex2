package Testim;

import ClassesToChange.Ex2Sheet;
import ClassesToChange.SCell;
import org.junit.jupiter.api.Test;

public class SpreadsheetTest {

    /**
     * Gets the value of a Cell from the Table
     */
    @Test
    public void computeFormFromCellTest() {
        Ex2Sheet sheet = new Ex2Sheet();
        SCell[] cells = new SCell[2];
        sheet.set(0,0,"=2+5"); //7
        sheet.set(0,1,"=A0"); //7
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new SCell(sheet.get(0,i).getData());
        }

        for (int i = 0; i < cells.length; i++) {
            System.out.println("Cell in index " + i + ", value: " + cells[i].getData());
        }

        for (int i = 0; i < cells.length; i++) {
            System.out.println("Value of Cell in index " + i + ", value: " + sheet.eval(0,i));
        }

    }
}
