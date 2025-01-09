import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellEntryTest {

    @Test
    public void testIsValidValidCases() {
        CellEntry cell1 = new CellEntry("A1");
        CellEntry cell2 = new CellEntry("Z99");
        CellEntry cell3 = new CellEntry("b0");

        assertTrue(cell1.isValid(), "A1 should be valid");
        assertTrue(cell2.isValid(), "Z99 should be valid");
        assertTrue(cell3.isValid(), "b0 should be valid");
    }

    @Test
    public void testIsValidInvalidCases() {
        CellEntry cell1 = new CellEntry("1A");
        CellEntry cell2 = new CellEntry("AA10");
        CellEntry cell3 = new CellEntry("A100");
        CellEntry cell4 = new CellEntry("");
        CellEntry cell5 = new CellEntry(null);

        assertFalse(cell1.isValid(), "1A should be invalid");
        assertFalse(cell2.isValid(), "AA10 should be invalid");
        assertFalse(cell3.isValid(), "A100 should be invalid");
        assertFalse(cell4.isValid(), "Empty string should be invalid");
        assertFalse(cell5.isValid(), "Null should be invalid");
    }

    @Test
    public void testGetXValidCases() {
        CellEntry cell1 = new CellEntry("A1");
        CellEntry cell2 = new CellEntry("Z99");
        CellEntry cell3 = new CellEntry("b0");

        assertEquals(0, cell1.getX(), "X value for A1 should be 0");
        assertEquals(25, cell2.getX(), "X value for Z99 should be 25");
        assertEquals(1, cell3.getX(), "X value for b0 should be 1");
    }

    @Test
    public void testGetXInvalidCases() {
        CellEntry cell1 = new CellEntry("1A");
        CellEntry cell2 = new CellEntry("A100");

        assertEquals(Ex2Utils.ERR, cell1.getX(), "X value for invalid 1A should return ERR");
        assertEquals(Ex2Utils.ERR, cell2.getX(), "X value for invalid A100 should return ERR");
    }

    @Test
    public void testGetYValidCases() {
        CellEntry cell1 = new CellEntry("A1");
        CellEntry cell2 = new CellEntry("Z99");
        CellEntry cell3 = new CellEntry("b0");

        assertEquals(1, cell1.getY(), "Y value for A1 should be 1");
        assertEquals(99, cell2.getY(), "Y value for Z99 should be 99");
        assertEquals(0, cell3.getY(), "Y value for b0 should be 0");
    }

    @Test
    public void testGetYInvalidCases() {
        CellEntry cell1 = new CellEntry("A100");
        CellEntry cell2 = new CellEntry("AA10");

        assertEquals(Ex2Utils.ERR, cell1.getY(), "Y value for invalid A100 should return ERR");
        assertEquals(Ex2Utils.ERR, cell2.getY(), "Y value for invalid AA10 should return ERR");
    }
}
