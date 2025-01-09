package Testim;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for SCell implementation.
 */
public class SCellTest {

    @Test
    public void testSetDataAndGetData() {
        SCell cell = new SCell("Hello");
        Assertions.assertEquals("Hello", cell.getData());

        cell.setData("123");
        Assertions.assertEquals("123", cell.getData());
    }

    @Test
    public void testGetTypeForText() {
        SCell cell = new SCell("Hello");
        Assertions.assertEquals(Ex2Utils.TEXT, cell.getType());
    }

    @Test
    public void testGetTypeForNumber() {
        SCell cell = new SCell("123.45");
        Assertions.assertEquals(Ex2Utils.NUMBER, cell.getType());
    }

    @Test
    public void testGetTypeForFormula() {
        SCell cell = new SCell("=A1+B2");
        Assertions.assertEquals(Ex2Utils.FORM, cell.getType());
    }

    @Test
    public void testSetType() {
        SCell cell = new SCell("Hello");
        cell.setType(Ex2Utils.NUMBER);
        Assertions.assertEquals(Ex2Utils.NUMBER, cell.getType());
    }

    @Test
    public void testGetOrderForText() {
        SCell cell = new SCell("Hello");
        Assertions.assertEquals(0, cell.getOrder());
    }

    @Test
    public void testGetOrderForNumber() {
        SCell cell = new SCell("123.45");
        Assertions.assertEquals(0, cell.getOrder());
    }

    @Test
    public void testSetOrder() {
        SCell cell = new SCell("=A1+B2");
        cell.setOrder(3);
        Assertions.assertEquals(3, cell.getOrder());
    }

    @Test
    public void testToString() {
        SCell cell = new SCell("Hello");
        Assertions.assertEquals("Hello", cell.toString());

        cell.setData("123");
        Assertions.assertEquals("123", cell.toString());
    }
}

