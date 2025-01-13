import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

class TestEx2Sheet {

    @Test
    void testConstructor() {
        Ex2Sheet sheet = new Ex2Sheet(5, 5);
        assertEquals(5, sheet.width());
        assertEquals(5, sheet.height());

        // Check if all cells are initialized properly
        for (int x = 0; x < sheet.width(); x++) {
            for (int y = 0; y < sheet.height(); y++) {
                assertEquals(Ex2Utils.EMPTY_CELL, sheet.value(x, y));
            }
        }
    }

    @Test
    void testValue() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(1, 1, "42");
        assertEquals("42", sheet.value(1, 1));
    }

    @Test
    void testGetByCoordinates() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "123");
        assertEquals("123", sheet.get(0, 0).getData());

        // Test with string coordinates
        sheet.set(1, 1, "456");
        assertEquals("456", sheet.get("B1").getData());
    }

    @Test
    void testWidthHeight() {
        Ex2Sheet sheet = new Ex2Sheet(4, 5);
        assertEquals(4, sheet.width());
        assertEquals(5, sheet.height());
    }


    @Test
    void testIsIn() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        assertTrue(sheet.isIn(0, 0));
        assertFalse(sheet.isIn(-1, 0));
        assertTrue(sheet.isIn(3, 3));
    }


    @Test
    void testParseDependencies() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        Set<String> dependencies = sheet.parseDependencies("=A1 + B2 - C3");

        assertTrue(dependencies.contains("A1"));
        assertTrue(dependencies.contains("B2"));
        assertTrue(dependencies.contains("C3"));
        assertEquals(3, dependencies.size());
    }

    @Test
    void testFindMaxInteger() {
        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int max = Ex2Sheet.findMaxInteger(array);
        assertEquals(9, max);
    }
}