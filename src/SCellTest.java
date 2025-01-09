import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SCell implementation.
 */
public class SCellTest {

    @Test
    public void testSetDataAndGetData() {
        SCell cell = new SCell("Hello");
        assertEquals("Hello", cell.getData());

        cell.setData("123");
        assertEquals("123", cell.getData());
    }

    @Test
    public void testGetTypeForText() {
        SCell cell = new SCell("Hello");
        assertEquals(Ex2Utils.TEXT, cell.getType());
    }

    @Test
    public void testGetTypeForNumber() {
        SCell cell = new SCell("123.45");
        assertEquals(Ex2Utils.NUMBER, cell.getType());
    }

    @Test
    public void testGetTypeForFormula() {
        SCell cell = new SCell("=A1+B2");
        assertEquals(Ex2Utils.FORM, cell.getType());
    }

    @Test
    public void testSetType() {
        SCell cell = new SCell("Hello");
        cell.setType(Ex2Utils.NUMBER);
        assertEquals(Ex2Utils.NUMBER, cell.getType());
    }

    @Test
    public void testGetOrderForText() {
        SCell cell = new SCell("Hello");
        assertEquals(0, cell.getOrder());
    }

    @Test
    public void testGetOrderForNumber() {
        SCell cell = new SCell("123.45");
        assertEquals(0, cell.getOrder());
    }

    @Test
    public void testSetOrder() {
        SCell cell = new SCell("=A1+B2");
        cell.setOrder(3);
        assertEquals(3, cell.getOrder());
    }

    @Test
    public void testToString() {
        SCell cell = new SCell("Hello");
        assertEquals("Hello", cell.toString());

        cell.setData("123");
        assertEquals("123", cell.toString());
    }
}

