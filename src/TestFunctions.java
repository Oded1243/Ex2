import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
public class TestFunctions {

    @Test
    public void testIsNumber() {
        // Valid numbers
        assertTrue(Functions.IsNumber("42"), "Positive integer should return true.");
        assertTrue(Functions.IsNumber("-10"), "Negative integer should return true.");
        assertTrue(Functions.IsNumber("3.14"), "Decimal number should return true.");
        assertTrue(Functions.IsNumber("0.001"), "Small decimal number should return true.");

        // Invalid numbers
        assertFalse(Functions.IsNumber("abc"), "Alphabets should return false.");
        assertFalse(Functions.IsNumber("42abc"), "Numbers followed by text should return false.");
        assertFalse(Functions.IsNumber(""), "Empty string should return false.");
        assertFalse(Functions.IsNumber(null), "Null input should return false.");
    }

    @Test
    public void testIsText() {
        // Valid text
        assertTrue(Functions.IsText("hello"), "Lowercase text should return true.");
        assertTrue(Functions.IsText("Hello World"), "Text with spaces should return true.");
        assertTrue(Functions.IsText("123text"), "Numbers followed by text should return true.");
        assertTrue(Functions.IsText("!@#$%^&*"), "Special characters should return true.");

        // Invalid text
        assertFalse(Functions.IsText(""), "Empty string should return false.");
        assertFalse(Functions.IsText(null), "Null input should return false.");
        assertFalse(Functions.IsText("123"), "Pure numbers should return false.");
    }

    @Test
    public void testIsForm() {
        // Valid formulas
        assertTrue(Functions.IsForm("=A1+B2"), "Formula starting with '=' should return true.");
        assertTrue(Functions.IsForm("=((5+3)*2)-2"), "Complex formulas should return true.");
        assertTrue(Functions.IsForm("=A1+A2"), "Formulas with extra '=' should return true.");

        // Invalid formulas
        assertFalse(Functions.IsForm("A1+B2"), "Strings without '=' should return false.");
        assertFalse(Functions.IsForm(""), "Empty string should return false.");
        assertFalse(Functions.IsForm(null), "Null input should return false.");
    }

    @Test
    public void testCalculateValidFormula() {
        // Test for numerical operations
        assertEquals("Simple number should return correct result." ,Functions.Calculate("2+3"), "5.0" );
        assertEquals("Multiplication should return correct result.", Functions.Calculate("2*3"), "6.0");
        assertEquals("Division should return correct result.", Functions.Calculate("6/2"), "3.0");
        assertEquals("Subtraction should return correct result.", Functions.Calculate("5-5"), "0.0");
        assertEquals("Parentheses should calculate correctly.", Functions.Calculate("2*(3+4)"), "14.0");
        assertEquals("Nested operations should calculate correctly.", Functions.Calculate("7*(5-2)"), "21.0");
    }

    @Test
    public void testCalculateInvalidFormula() {
        // Invalid formulas
        assertThrows(Exception.class, () -> Functions.Calculate("2++3"), "Malformed formulas should throw an exception.");
        assertThrows(Exception.class, () -> Functions.Calculate("2*/3"), "Invalid operators should throw an exception.");
        assertThrows(Exception.class, () -> Functions.Calculate(null), "Null input should throw an exception.");
    }
}

