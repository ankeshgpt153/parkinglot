package com.ankesh;

import com.ankesh.exception.ErrorCode;
import com.ankesh.exception.ParkingException;
import com.ankesh.model.Car;
import com.ankesh.service.ParkingService;
import com.ankesh.service.impl.ParkingServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for ParkingLot App.
 */
public class ParkingLotTest {

    private static final String CREATED_PARKING_LOT_MESSAGE = "createdparkinglotwith65slots";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    String content=outContent.toString().trim().replace(" ", "");

    ParkingService instance = new ParkingServiceImpl();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void init() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUp() {
        System.setOut(null);
    }


    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }


    @Test
    public void createParkingLot() throws Exception {

        instance.createParkingLot(65);

        assertTrue(CREATED_PARKING_LOT_MESSAGE.equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
        instance.doCleanup();
    }

    @Test
    public void alreadyExistParkingLot() throws Exception {

        instance.createParkingLot(65);

        assertTrue(CREATED_PARKING_LOT_MESSAGE.equalsIgnoreCase(outContent.toString().trim().replace(" ", "")));
        thrown.expect(ParkingException.class);
        thrown.expectMessage(is(ErrorCode.PARKING_ALREADY_EXIST.getMessage()));
        instance.createParkingLot(65);
        instance.doCleanup();
    }


    @Test(expected = ParkingException.class)
    public void testParkingCapacity() throws Exception {

        thrown.expectMessage(CoreMatchers.is(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage()));

        instance.park(new Car("KA-01-HH-1234", "White"));
        assertEquals("Sorry,CarParkingDoesnotExist", content);

        instance.createParkingLot(11);
        instance.park(new Car("KA-01-HH-1234", "White"));
        instance.park(new Car("KA-01-HH-9999", "White"));
        instance.park(new Car("KA-01-BB-0001", "Black"));

        assertEquals(3, instance.getAvailableSlotsCount());
        instance.doCleanup();
    }

    @Test
    public void testEmptyParkingLot() throws Exception {
        ParkingService instance = new ParkingServiceImpl();
        thrown.expect(ParkingException.class);
        thrown.expectMessage(CoreMatchers.is(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage()));
        instance.getStatus();
        assertTrue("Sorry,CarParkingDoesnotExist".equalsIgnoreCase(content));
        instance.createParkingLot(6);
        instance.getStatus();
        assertTrue(
                "Sorry,CarParkingDoesnotExist\ncreatedparkinglotwith6slots\nSlotNo.\tRegistrationNo.\tColor\nSorry,parkinglotisempty."
                        .equalsIgnoreCase(content));
        instance.doCleanup();
    }

    @Test
    public void testParkingLotIsFull() throws Exception {
        ParkingService instance = new ParkingServiceImpl();
        thrown.expect(ParkingException.class);
        thrown.expectMessage(CoreMatchers.is(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage()));
        instance.park(new Car("KA-01-HH-1234", "White"));
        assertEquals("Sorry,CarParkingDoesnotExist", content);
        instance.createParkingLot(2);
        instance.park(new Car("KA-01-HH-1234", "White"));
        instance.park(new Car("KA-01-HH-9999", "White"));
        instance.park(new Car("KA-01-BB-0001", "Black"));
        assertTrue("createdparkinglotwith2slots\\nAllocatedslotnumber:1\nAllocatedslotnumber:2\nSorry,parkinglotisfull"
                .equalsIgnoreCase(content));
        instance.doCleanup();
    }


}
