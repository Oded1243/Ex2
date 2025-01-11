package Testim;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormulaTest {

    // The method being tested
    private List<String> extractReferences(String formula) {
        List<String> refs = new ArrayList<>();
        Matcher matcher = Pattern.compile("[A-Z]+\\d+").matcher(formula);
        while (matcher.find()) {
            refs.add(matcher.group());
        }
        return refs;
    }

    @Test
    public void testExtractReferences() {
        // Test case 1: Simple formula with valid references
        String formula1 = "A1 + B2 - C3";
        List<String> result1 = extractReferences(formula1);
        assertEquals(List.of("A1", "B2", "C3"), result1);

        // Test case 2: Formula with no valid references
        String formula2 = "10 + 20 - 30";
        List<String> result2 = extractReferences(formula2);
        assertEquals(List.of(), result2);

        // Test case 3: Formula with mixed content
        String formula3 = "(A1+A5) + D10 * E20";
        List<String> result3 = extractReferences(formula3);
        assertEquals(List.of("A1", "A5", "D10", "E20"), result3);

        // Test case 5: References with extra spaces/tabs around
        String formula5 = "  A1\t +   B2 \n   -     C3 ";
        List<String> result5 = extractReferences(formula5);
        assertEquals(List.of("A1", "B2", "C3"), result5);
    }
}