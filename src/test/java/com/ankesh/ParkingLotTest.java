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
    private static final String CAR_PARKING_DOES_NOT_EXIST_MESSAGE="Sorry,CarParkingDoesnotExist";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    String content = outContent.toString().trim().replace(" ", "");

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


    @Test
    public void testParkingCapacity() throws Exception {
        thrown.expect(ParkingException.class);
        thrown.expectMessage(CoreMatchers.is(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage()));

        instance.park(new Car("KA-01-HH-1234", "White"));
        assertEquals(CAR_PARKING_DOES_NOT_EXIST_MESSAGE, content);

        instance.createParkingLot(11);
        instance.park(new Car("KA-01-HH-1234", "White"));
        instance.park(new Car("KA-01-HH-9999", "White"));
        instance.park(new Car("KA-01-BB-0001", "Black"));

        assertEquals(3, instance.getAvailableSlotsCount());
        instance.doCleanup();
    }

    @Test
    public void testEmptyParkingLot() throws Exception {
        thrown.expect(ParkingException.class);
        thrown.expectMessage(CoreMatchers.is(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage()));

        instance.getStatus();
        assertTrue(CAR_PARKING_DOES_NOT_EXIST_MESSAGE.equalsIgnoreCase(content));

        instance.createParkingLot(6);
        instance.getStatus();
        assertTrue(
                "Sorry,CarParkingDoesnotExist\ncreatedparkinglotwith6slots\nSlotNo.\tRegistrationNo.\tColor\nSorry,parkinglotisempty."
                        .equalsIgnoreCase(content));
        instance.doCleanup();
    }

    @Test
    public void testParkingLotIsFull() throws Exception {
        thrown.expect(ParkingException.class);
        thrown.expectMessage(CoreMatchers.is(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage()));

        instance.park(new Car("KA-01-HH-1234", "White"));
        assertEquals(CAR_PARKING_DOES_NOT_EXIST_MESSAGE, content);

        instance.createParkingLot(2);
        instance.park(new Car("KA-01-HH-1234", "White"));
        instance.park(new Car("KA-01-HH-9999", "White"));
        instance.park(new Car("KA-01-BB-0001", "Black"));
        assertTrue("createdparkinglotwith2slots\\nAllocatedslotnumber:1\nAllocatedslotnumber:2\nSorry,parkinglotisfull"
                .equalsIgnoreCase(content));
        instance.doCleanup();
    }

    @Test
    public void testNearestSlotAllotment() throws Exception {
        thrown.expect(ParkingException.class);
        thrown.expectMessage(CoreMatchers.is(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage()));

        instance.park(new Car("KA-01-HH-1234", "White"));
        assertEquals(CAR_PARKING_DOES_NOT_EXIST_MESSAGE, content);

        instance.createParkingLot(5);
        instance.park(new Car("KA-01-HH-1234", "White"));
        instance.park(new Car("KA-01-HH-9999", "White"));
        instance.getSlotNoFromRegistrationNo("KA-01-HH-1234", true);
        instance.getSlotNoFromRegistrationNo("KA-01-HH-9999", true);
        assertTrue("createdparkinglotwith5slots\nAllocatedslotnumber:1\nAllocatedslotnumber:2"
                .equalsIgnoreCase(content));
        instance.doCleanup();
    }

    @Test
    public void leave() throws Exception {
        thrown.expect(ParkingException.class);
        thrown.expectMessage(CoreMatchers.is(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage()));

        instance.unPark("2", 2);
        assertEquals(CAR_PARKING_DOES_NOT_EXIST_MESSAGE, content);

        instance.createParkingLot(6);
        instance.park(new Car("KA-01-HH-1234", "White"));
        instance.park(new Car("KA-01-HH-9999", "White"));
        instance.park(new Car("KA-01-BB-0001", "Black"));
        instance.unPark("4", 5);
        assertTrue(
                "Sorry,CarParkingDoesnotExist\ncreatedparkinglotwith6slots\nAllocatedslotnumber:1\nAllocatedslotnumber:2\nAllocatedslotnumber:3\nSlotnumber4isfree"
                        .equalsIgnoreCase(content));
        instance.doCleanup();
    }

    @Test
    public void testWhenVehicleAlreadyPresent() throws Exception {
        thrown.expect(ParkingException.class);
        thrown.expectMessage(CoreMatchers.is(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage()));

        instance.park(new Car("KA-01-HH-1234", "White"));
        assertEquals(CAR_PARKING_DOES_NOT_EXIST_MESSAGE, content);

        instance.createParkingLot(3);
        instance.park(new Car("KA-01-HH-1234", "White"));
        instance.park(new Car("KA-01-HH-1234", "White"));
        assertTrue(
                "Sorry,CarParkingDoesnotExist\ncreatedparkinglotwith3slots\nAllocatedslotnumber:1\nSorry,vehicleisalreadyparked."
                        .equalsIgnoreCase(content));
        instance.doCleanup();
    }

    @Test
    public void testWhenVehicleAlreadyPicked() throws Exception {
        thrown.expect(ParkingException.class);
        thrown.expectMessage(CoreMatchers.is(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage()));

        instance.park(new Car("KA-01-HH-1234", "White"));
        assertEquals(CAR_PARKING_DOES_NOT_EXIST_MESSAGE, content);

        instance.createParkingLot(99);
        instance.park(new Car("KA-01-HH-1234", "White"));
        instance.park(new Car("KA-01-HH-9999", "White"));
        instance.unPark("1", 4);
        instance.unPark("1", 4);
        assertTrue(
                "Sorry,CarParkingDoesnotExist\ncreatedparkinglotwith99slots\nAllocatedslotnumber:1\nAllocatedslotnumber:2\nSlotnumberisEmptyAlready."
                        .equalsIgnoreCase(content));
        instance.doCleanup();
    }

    @Test
    public void testStatus() throws Exception {
        thrown.expect(ParkingException.class);
        thrown.expectMessage(CoreMatchers.is(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage()));

        instance.getStatus();
        assertEquals(CAR_PARKING_DOES_NOT_EXIST_MESSAGE, content);

        instance.createParkingLot(8);
        instance.park(new Car("KA-01-HH-1234", "White"));
        instance.park(new Car("KA-01-HH-9999", "White"));
        instance.getStatus();
        assertTrue(
                "Sorry,CarParkingDoesnotExist\ncreatedparkinglotwith8slots\nAllocatedslotnumber:1\nAllocatedslotnumber:2\nSlotNo.\tRegistrationNo.\tColor\n1\tKA-01-HH-1234\tWhite\n2\tKA-01-HH-9999\tWhite"
                        .equalsIgnoreCase(content));
        instance.doCleanup();

    }

    @Test
    public void testGetSlotsByRegNo() throws Exception {
        thrown.expect(ParkingException.class);
        thrown.expectMessage(CoreMatchers.is(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage()));

        instance.getSlotNoFromRegistrationNo("KA-01-HH-1234", true);
        assertEquals(CAR_PARKING_DOES_NOT_EXIST_MESSAGE, content);

        instance.createParkingLot(10);
        instance.park(new Car("KA-01-HH-1234", "White"));
        instance.park(new Car("KA-01-HH-9999", "White"));
        instance.getSlotNoFromRegistrationNo("KA-01-HH-1234", true);
        assertEquals("Sorry,CarParkingDoesnotExist\n" + "Createdparkinglotwith6slots\n" + "\n"
                        + "Allocatedslotnumber:1\n" + "\n" + "Allocatedslotnumber:2\n1",
                content);
        instance.getSlotNoFromRegistrationNo("KA-01-HH-1235", true);
        assertEquals("Sorry,CarParkingDoesnotExist\n" + "Createdparkinglotwith10slots\n" + "\n"
                        + "Allocatedslotnumber:1\n" + "\n" + "Allocatedslotnumber:2\n1\nNotFound",
                content);
        instance.doCleanup();
    }

    @Test
    public void testGetSlotsByColor() throws Exception {
        thrown.expect(ParkingException.class);
        thrown.expectMessage(CoreMatchers.is(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage()));

        instance.getRegNumberForColor("white");
        assertEquals(CAR_PARKING_DOES_NOT_EXIST_MESSAGE, content);

        instance.createParkingLot(7);
        instance.park(new Car("KA-01-HH-1234", "White"));
        instance.park(new Car("KA-01-HH-9999", "White"));
        instance.getStatus();
        instance.getRegNumberForColor("Cyan");
        assertEquals(
                "Sorry,CarParkingDoesnotExist\n" + "Createdparkinglotwith7slots\n" + "\n" + "Allocatedslotnumber:1\n"
                        + "\n" + "Allocatedslotnumber:2\nKA-01-HH-1234,KA-01-HH-9999",
                content);
        instance.getRegNumberForColor("Red");
        assertEquals(
                "Sorry,CarParkingDoesnotExist\n" + "Createdparkinglotwith6slots\n" + "\n" + "Allocatedslotnumber:1\n"
                        + "\n" + "Allocatedslotnumber:2\n" + "KA-01-HH-1234,KA-01-HH-9999,Notfound",
                content);
        instance.doCleanup();

    }

}