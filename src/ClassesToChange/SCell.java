package ClassesToChange;

import ClassesNotToChange.Ex2Utils;
import Interfaces.Cell;

public class SCell implements Cell {
    private String line;
    private int type;
    private int order;

    /**
     * Constructor that initializes the cell with data.
     * @param s the initial data of the cell
     */
    public SCell(String s) {
        setData(s);
    }

    /**
     * Computes the natural order of this cell.
     * If the cell is of type TEXT or NUMBER, returns 0.
     * Otherwise, computes 1 + the maximum order of dependent cells (mock implementation here).
     * @return an integer representing the number of rounds needed to compute this cell
     */
    @Override
    public int getOrder() {
        if (type == Ex2Utils.TEXT || type == Ex2Utils.NUMBER) {
            return 0;
        }
        // Mock computation for dependent cells (as no dependencies are implemented)
        // Replace this with actual dependency logic.
        return order;
    }

    /**
     * Returns the string representation of this cell.
     * @return the input text (string) of the cell
     */
    @Override
    public String toString() {

        return getData();
    }

    /**
     * Sets the data of the cell.
     * @param s the new data string to set
     */
    @Override
    public void setData(String s) {
        line = s;
        // Automatically set type based on content (mock implementation)
        if (isNumber(s)) {
            type = Ex2Utils.NUMBER;
        } else if (s.startsWith("=")) {
            type = Ex2Utils.FORM;
        } else {
            type = Ex2Utils.TEXT;
        }
    }

    /**
     * Gets the data of the cell.
     * @return the data string
     */
    @Override
    public String getData() {

        return line;
    }

    /**
     * Gets the type of the cell.
     * @return the type of the cell as defined in Ex2Utils
     */
    @Override
    public int getType() {

        return type;
    }

    /**
     * Sets the type of the cell.
     * @param t the type to set
     */
    @Override
    public void setType(int t) {
        type = t;
    }

    /**
     * Sets the order of the cell.
     * @param t the order to set
     */
    @Override
    public void setOrder(int t) {
        order = t;
    }

    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
