package Testim;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DependencyParserTest {

    @Test
    public void testExtractDependencies() {
        String formula = "A1 + B2 - C3";
        List<CellEntry> dependencies = extractDependencies(formula);

        assertEquals(3, dependencies.size());
        assertEquals("A1", dependencies.get(0).getReference());
        assertEquals("B2", dependencies.get(1).getReference());
        assertEquals("C3", dependencies.get(2).getReference());
    }

    @Test
    public void testExtractDependenciesWithNoMatches() {
        String formula = "123 + 456 - 789";
        List<CellEntry> dependencies = extractDependencies(formula);

        assertEquals(0, dependencies.size());
    }

    @Test
    public void testParseCellIndexValid() {
        Index2D index = parseCellIndex("B2");
        assertNotNull(index);
        assertEquals(1, index.getX()); // Row 2 -> zero-based index = 1
        assertEquals(1, index.getY()); // Column B -> zero-based index = 1
    }

    @Test
    public void testParseCellIndexInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> parseCellIndex("2B"));
        assertThrows(IllegalArgumentException.class, () -> parseCellIndex(""));
        assertThrows(IllegalArgumentException.class, () -> parseCellIndex(null));
    }

    @Test
    public void testParseCellIndexOutOfBounds() {
        Index2D index = parseCellIndex("ZZ9999");
        assertNotNull(index);
        assertTrue(index.getX() >= 0);
        assertTrue(index.getY() >= 0);
    }

    // Mock extractDependencies function
    public List<CellEntry> extractDependencies(String formula) {
        // This pattern matches cell references like A1, B2, C3, etc.
        Pattern pattern = Pattern.compile("[A-Za-z]+[0-9]+");
        Matcher matcher = pattern.matcher(formula);
        List<CellEntry> dependencies = new ArrayList<>();

        // Find all matches in the formula
        while (matcher.find()) {
            String cellRef = matcher.group();
            // Create a new CellEntry for each reference and add it to the list
            dependencies.add(new CellEntry(cellRef));
        }

        return dependencies;
    }

    // Mock parseCellIndex function
    public static Index2D parseCellIndex(String cords) {
        if (cords == null || cords.isEmpty() || !cords.matches("[A-Za-z]+\\d+")) {
            throw new IllegalArgumentException("Invalid cell coordinates format: " + cords);
        }

        int column = 0;
        int row = -1;
        int i = 0;

        // Parse the column (letters)
        while (i < cords.length() && Character.isLetter(cords.charAt(i))) {
            column = column * 26 + (Character.toUpperCase(cords.charAt(i)) - 'A' + 1);
            i++;
        }
        column--; // Convert to zero-based index

        // Parse the row (numbers)
        StringBuilder rowBuilder = new StringBuilder();
        while (i < cords.length() && Character.isDigit(cords.charAt(i))) {
            rowBuilder.append(cords.charAt(i));
            i++;
        }

        if (rowBuilder.length() > 0) {
            row = Integer.parseInt(rowBuilder.toString()) - 1; // Convert to zero-based index
        }

        if (row < 0 || column < 0 || i < cords.length()) {
            throw new IllegalArgumentException("Invalid cell coordinates: row=" + row + ", column=" + column);
        }

        int finalRow = row;
        int finalColumn = column;
        return new Index2D() {
            @Override
            public int getX() {
                return finalRow;
            }

            @Override
            public int getY() {
                return finalColumn;
            }
        };
    }

    // Mock CellEntry class
    public class CellEntry {
        private String reference;

        public CellEntry(String reference) {
            this.reference = reference;
        }

        public String getReference() {
            return reference;
        }
    }

    // Mock Index2D interface
    public interface Index2D {
        int getX();
        int getY();
    }
}
