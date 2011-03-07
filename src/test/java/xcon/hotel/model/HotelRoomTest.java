package xcon.hotel.model;

import static org.junit.Assert.fail;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class HotelRoomTest {

    private long recNo;
    private String name;
    private String location;
    private String size;
    private String smokingAllowed;
    private String rate;
    private String date;
    private String owner;

    @Before
    public void setup() {
        recNo = 72654L;
        name = "myName";
        location = "myLocation";
        size = "1234";
        smokingAllowed = "y";
        rate = "44,95";
        date = "13-mei-2011";
        owner = "2234523452";
    }

    private String[] getFields() {
        return new String[] {
                name, location, size, smokingAllowed, rate, date, owner
        };
    }

    @Test
    public void testHappyCreation() {
        new HotelRoom(recNo, getFields());
    }

    @Test
    public void testUnhappyCreation() {
        try {
            new HotelRoom(recNo, null);
            fail("Expected exception");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void testInvalidSize() {
        size = "not an integer";
        try {
            new HotelRoom(recNo, getFields());
            fail("expected exception");
        }
        catch (NumberFormatException e) {
            // expected
        }
    }

    @Test
    public void testInvalidOwner() {

        owner = "not an integer";
        try {
            new HotelRoom(recNo, getFields());
            fail("expected exception");
        }
        catch (NumberFormatException e) {
            // expected
        }
    }

    @Test
    public void testNoOwner() {

        HotelRoom instance;
        owner = "";
        instance = new HotelRoom(recNo, getFields());
        Assert.assertNull(instance.getOwner());

        owner = null;
        instance = new HotelRoom(recNo, getFields());
        Assert.assertNull(instance.getOwner());
    }

    @Test
    public void testHappyConvertToArray() {
        HotelRoom instance = new HotelRoom(recNo, getFields());
        String[] result = instance.convertToArray();
        Assert.assertEquals(name, result[0]);
        Assert.assertEquals(location, result[1]);
        Assert.assertEquals(size, result[2]);
        Assert.assertEquals(smokingAllowed, result[3]);
        Assert.assertEquals(rate, result[4]);
        Assert.assertEquals(date, result[5]);
        Assert.assertEquals(owner, result[6]);
    }
}
