import org.junit.Test;

import static org.junit.Assert.*;

public class TestsPart1 {
    @Test
    public void isForm() {
        assertTrue(Main.isForm("(1+2)*3/(2/5)"));
        assertTrue(Main.isForm("1+3*2"));
        assertTrue(Main.isForm("(1-5)-(3*5)"));
        assertTrue(Main.isForm("(5+2)*5"));
        assertFalse(Main.isForm("5+-2"));
        assertFalse(Main.isForm("5*(5/*3)"));
        assertFalse(Main.isForm("5a+7"));
    }

    @Test
    public void isText() {
        assertTrue(Main.isText("=2a"));
        assertTrue(Main.isText("=2A"));
        assertTrue(Main.isText("asd"));
        assertFalse(Main.isText("123"));
        assertTrue(Main.isText("{2}"));
    }

    @Test
    public void isNumber() {
        assertTrue(Main.isNumber("32"));
        assertTrue(Main.isNumber("-32"));
        assertTrue(Main.isNumber("5"));
        assertFalse(Main.isNumber("3a"));
        assertFalse(Main.isNumber("a"));
    }

    @Test
    public void testSimpleAddition() throws Exception {
        String expression = "1+2";
        double expected = 3.0;
        double result = ExpressionEvaluator.evaluate(expression);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testParenthesesWithMultiplication() throws Exception {
        String expression = "(1+2)* 3";
        double expected = 9.0;
        double result = ExpressionEvaluator.evaluate(expression);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testSimpleDivision() throws Exception {
        String expression = "10/2";
        double expected = 5.0;
        double result = ExpressionEvaluator.evaluate(expression);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testDivisionWithParentheses() throws Exception {
        String expression = "10/(2+3)";
        double expected = 2.0;
        double result = ExpressionEvaluator.evaluate(expression);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testMixedAdditionAndSubtraction() throws Exception {
        String expression = "10-5+3";
        double expected = 8.0;
        double result = ExpressionEvaluator.evaluate(expression);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testParenthesesWithMixedOperations() throws Exception {
        String expression = "(10-5)*(3+2)";
        double expected = 25.0;
        double result = ExpressionEvaluator.evaluate(expression);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testComplexMixedOperations() throws Exception {
        String expression = "2*(3+5)/4";
        double expected = 4.0;
        double result = ExpressionEvaluator.evaluate(expression);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testNestedParentheses() throws Exception {
        String expression = "(1+2)*(3/(2/5))";
        double expected = 22.5;
        double result = ExpressionEvaluator.evaluate(expression);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testDivisionByZero() {
        String expression = "2/0";
        assertThrows(ArithmeticException.class, () -> ExpressionEvaluator.evaluate(expression));
    }

    @Test
    public void testNoParenthesesWithPrecedence() throws Exception {
        String expression = "3+5*2-8/4";
        double expected = 11.0;
        double result = ExpressionEvaluator.evaluate(expression);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testDecimalsAddition() throws Exception {
        String expression = "3.5+2.1";
        double expected = 5.6;
        double result = ExpressionEvaluator.evaluate(expression);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testDecimalsWithParentheses() throws Exception {
        String expression = "(1.5+2.5)*4";
        double expected = 16.0;
        double result = ExpressionEvaluator.evaluate(expression);
        assertEquals(expected, result, 1e-6);
    }

    @Test
    public void testDeeplyNestedParentheses() throws Exception {
        String expression = "((1+2)*3)/(2+4)";
        double expected = 1.5;
        double result = ExpressionEvaluator.evaluate(expression);
        assertEquals(expected, result, 1e-6);
    }
}