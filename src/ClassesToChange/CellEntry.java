package ClassesToChange;

import ClassesNotToChange.Ex2Utils;
import Interfaces.Cell;
import Interfaces.Index2D;

public class CellEntry implements Index2D {
    int x;
    int y;
    private String cellIndex;

    public CellEntry(String cellIndex) {

        this.cellIndex = cellIndex;
    }

    public CellEntry(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public CellEntry cell2Place(String cellIndex) {
        char letter = cellIndex.charAt(0); // Letter from A to Z
        int number = Integer.parseInt(cellIndex.substring(1, cellIndex.length() -1)); // The Number
        int letterValue = letter - 'A';
        return new CellEntry(letterValue, number);
    }

    /**
     * Checks if the string representation of this index is valid "XY" where X is a letter "A-Z" (or "a-z"),
     * and Y is an integer [0-99].
     * @return true if this is a valid 2D index.
     */
    @Override
    public boolean isValid() {
        if (cellIndex == null || cellIndex.isEmpty()) {
            return false;
        }

        // Match the pattern: a letter (A-Z or a-z) followed by a number (0-99)
        return cellIndex.matches("[A-Za-z][0-9]{1,2}");
    }

    /**
     * Extracts the x value (integer) of this index.
     * X corresponds to the letter, converted to an integer (A=0, B=1, ..., Z=25).
     * @return the x value (integer) of this index, or Ex2Utils.ERR if invalid.
     */
    @Override
    public int getX() {
        if (!isValid()) {
            return Ex2Utils.ERR;
        }

        char letter = cellIndex.charAt(0);
        if (letter >= 'A' && letter <= 'Z') {
            return letter - 'A';
        } else if (letter >= 'a' && letter <= 'z') {
            return letter - 'a';
        }
        return Ex2Utils.ERR;
    }

    /**
     * Extracts the y value (integer) of this index.
     * Y corresponds to the integer part of the string (after the letter).
     * @return the y value (integer) of this index, or Ex2Utils.ERR if invalid.
     */
    @Override
    public int getY() {
        if (!isValid()) {
            return Ex2Utils.ERR;
        }
        try {
            return Integer.parseInt(cellIndex.substring(1));
        } catch (NumberFormatException e) {
            return Ex2Utils.ERR;
        }
    }
}