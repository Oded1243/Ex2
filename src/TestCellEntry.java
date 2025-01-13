import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class TestCellEntry {

    @Test
    public void testConstructorWithCoordinates() {
        // Testing the constructor that accepts integer coordinates
        CellEntry cell = new CellEntry(3, 5);

        assertEquals(3, cell.getX(), "The X coordinate should be 3.");
        assertEquals(5, cell.getY(), "The Y coordinate should be 5.");
    }

    @Test
    public void testConstructorWithCellName() {
        // Testing the constructor that accepts a cell name
        CellEntry cell = new CellEntry("B2");

        assertEquals(1, cell.getX(), "The X coordinate for 'B2' should be 1.");
        assertEquals(2, cell.getY(), "The Y coordinate for 'B2' should be 2.");

    }

    @Test
    public void testConstructorWithInvalidCellName() {
        // Assuming invalid cell names throw an exception
        assertThrows(IllegalArgumentException.class, () -> {
            new CellEntry("InvalidName");
        }, "Creating a cell with an invalid name should throw an exception.");
    }

    @Test
    public void testIsValidForValidCell() {
        // Create a valid cell
        CellEntry cell = new CellEntry(2, 3);

        assertTrue(cell.isValid(), "The cell should be valid for non-negative coordinates.");
    }

    @Test
    public void testToString() {
        // Assuming a cell at (0, 0) corresponds to "A1"
        CellEntry cell1 = new CellEntry(0, 0);
        assertEquals("A0", cell1.toString(), "The cell at (0, 0) should be represented as 'A0'.");

        // Assuming a cell at (1, 2) corresponds to "B3"
        CellEntry cell2 = new CellEntry(1, 3);
        assertEquals("B3", cell2.toString(), "The cell at (1, 3) should be represented as 'B3'.");
    }

    @Test
    public void testToCords() {
        // Testing the toCords method
        CellEntry cell = new CellEntry(4, 7);
        ArrayList<Integer> coords = cell.toCords();

        assertEquals(2, coords.size(), "The coordinates list should have a size of 2.");
        assertEquals(4, coords.get(0), "The X coordinate should be 4.");
        assertEquals(7, coords.get(1), "The Y coordinate should be 7.");
    }

    @Test
    public void testGetXAndGetY() {
        // Testing getX() and getY() methods
        CellEntry cell = new CellEntry(6, 9);

        assertEquals(6, cell.getX(), "The X coordinate should be 6.");
        assertEquals(9, cell.getY(), "The Y coordinate should be 9.");
    }
}