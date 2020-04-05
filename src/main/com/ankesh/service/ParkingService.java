/**
 *
 */
package com.ankesh.service;

import com.ankesh.exception.ParkingException;
import com.ankesh.model.Vehicle;

import java.util.Optional;

/**
 * @author ankesh
 *
 */
public interface ParkingService {

    public void createParkingLot(int capacity) throws ParkingException;

    public Optional<Integer> park(Vehicle vehicle) throws ParkingException;

    public void unPark(int slotNumber) throws ParkingException;

    public void getStatus() throws ParkingException;

    public Optional<Integer> getAvailableSlotsCount() throws ParkingException;

    public void getRegNumberForColor(String color) throws ParkingException;

    public void getSlotNumbersFromColor(String colour) throws ParkingException;

    public int getSlotNoFromRegistrationNo(String registrationNo) throws ParkingException;

    public void doCleanup();
}
