package com.ankesh;

import com.ankesh.exception.ErrorCode;
import com.ankesh.exception.ParkingException;
import com.ankesh.service.ParkingService;
import com.ankesh.service.impl.ParkingServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for ParkingLot App.
 */
public class ParkingLotTest {

    private static final String CREATED_PARKING_LOT_MESSAGE = "createdparkinglotwith65slots";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

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
}
