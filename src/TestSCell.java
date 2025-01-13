import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestSCell {

    @Test
    public void testConstructor() {
        // Test the constructor that initializes SCell with a string
        SCell cell = new SCell("InitialData");

        assertNotNull(cell, "SCell object should be successfully created.");
        assertEquals("InitialData", cell.getData(), "Constructor should correctly initialize the data.");
    }

    @Test
    public void testSetData() {
        // Create an SCell object and update its data
        SCell cell = new SCell("OldData");
        cell.setData("NewData");

        assertEquals("NewData", cell.getData(), "setData should update the cell's data correctly.");
    }

    @Test
    public void testGetData() {
        // Test retrieving data from SCell
        SCell cell = new SCell("SomeData");

        assertEquals("SomeData", cell.getData(), "getData should return the correct data initialized or updated.");
    }

    @Test
    public void testSetTypeAndGetType() {
        // Test setting and getting the type of SCell
        SCell cell = new SCell("Data");

        cell.setType(10); // Update the type
        assertEquals(10, cell.getType(), "getType should return the type value set by setType.");
    }

    @Test
    public void testSetOrderAndGetOrder() {
        // Test setting and retrieving the order of SCell
        SCell cell = new SCell("Data");

        cell.setOrder(5); // Set order
        assertEquals(5, cell.getOrder(), "getOrder should return the order value set by setOrder.");
    }

    @Test
    public void testToString() {
        // Test the toString method
        SCell cell = new SCell("StringData");

        assertEquals("StringData", cell.toString(), "toString should return the string representation of the data.");
    }
}