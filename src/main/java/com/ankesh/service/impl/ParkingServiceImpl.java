/**
 *
 */
package com.ankesh.service.impl;

import com.ankesh.constants.Constants;
import com.ankesh.exception.ErrorCode;
import com.ankesh.exception.ParkingException;
import com.ankesh.model.Vehicle;
import com.ankesh.service.BillingService;
import com.ankesh.service.ParkingService;
import com.ankesh.strategy.ParkingDataManager;
import com.ankesh.strategy.ParkingStrategy;
import com.ankesh.strategy.impl.NearestFirstParkingStrategy;
import com.ankesh.strategy.impl.ParkingManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * This class has to be made singleton and used as service to be injected in
 * RequestProcessor
 *
 * @author ankesh
 *
 */
public class ParkingServiceImpl implements ParkingService {
    private ParkingDataManager<Vehicle> parkingDataManager = null;

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private BillingService billingService = null;

    @Override
    public void createParkingLot(int capacity) throws ParkingException {
        if (parkingDataManager != null)
            throw new ParkingException(ErrorCode.PARKING_ALREADY_EXIST.getMessage());
        List<ParkingStrategy> parkingStrategies = new ArrayList<>();
        parkingStrategies.add(new NearestFirstParkingStrategy());
        this.parkingDataManager = ParkingManager.getInstance(capacity, parkingStrategies);
        System.out.println("Created parking lot with " + capacity + " slots");
    }

    @Override
    public Optional<Integer> park(Vehicle vehicle) throws ParkingException {
        Optional<Integer> value = Optional.empty();
        lock.writeLock().lock();
        validateParkingLot();
        try {
            value = Optional.of(parkingDataManager.parkCar(vehicle));
            if (value.get() == Constants.NOT_AVAILABLE)
                System.out.println("Sorry, parking lot is full");
            else if (value.get() == Constants.VEHICLE_ALREADY_EXIST)
                System.out.println("Sorry, vehicle is already parked.");
            else
                System.out.println("Allocated slot number: " + value.get());
        } catch (Exception e) {
            throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.writeLock().unlock();
        }
        return value;
    }

    /**
     * @throws ParkingException
     */
    private void validateParkingLot() throws ParkingException {
        if (parkingDataManager == null) {
            throw new ParkingException(ErrorCode.PARKING_NOT_EXIST_ERROR.getMessage());
        }
    }

    @Override
    public void unPark(String registrationNumber, int duration) throws ParkingException {
        billingService = new BillingServiceImpl();
        lock.writeLock().lock();
        validateParkingLot();
        try {
            int slotNumber = getSlotNoFromRegistrationNo(registrationNumber);
            if (parkingDataManager.leaveCar(slotNumber)) {
                System.out.println("Registration number " + registrationNumber + "with Slot Number " + slotNumber + " is free with Charge " + billingService.calculateBill(duration));
            } else
                System.out.println("Registration number " + registrationNumber + " not found");
        } catch (Exception e) {
            throw new ParkingException(ErrorCode.INVALID_VALUE.getMessage().replace("{variable}", "slot_number"), e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void getStatus() throws ParkingException {
        lock.readLock().lock();
        validateParkingLot();
        try {
            System.out.println("Slot No.\tRegistration No.");
            List<String> statusList = parkingDataManager.getStatus();
            if (statusList.size() == 0)
                System.out.println("Sorry, parking lot is empty.");
            else {
                for (String statusSting : statusList) {
                    System.out.println(statusSting);
                }
            }
        } catch (Exception e) {
            throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.readLock().unlock();
        }
    }

    public Optional<Integer> getAvailableSlotsCount() throws ParkingException {
        lock.readLock().lock();
        Optional<Integer> value = Optional.empty();
        validateParkingLot();
        try {
            value = Optional.of(parkingDataManager.getAvailableSlotsCount());
        } catch (Exception e) {
            throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.readLock().unlock();
        }
        return value;
    }

    @Override
    public void getRegNumberForColor(String color) throws ParkingException {
        lock.readLock().lock();
        validateParkingLot();
        try {
            List<String> registrationList = parkingDataManager.getRegNumberForColor(color);
            if (registrationList.size() == 0)
                System.out.println("Not Found");
            else
                System.out.println(String.join(",", registrationList));
        } catch (Exception e) {
            throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void getSlotNumbersFromColor(String color) throws ParkingException {
        lock.readLock().lock();
        validateParkingLot();
        try {
            List<Integer> slotList = parkingDataManager.getSlotNumbersFromColor(color);
            if (slotList.size() == 0)
                System.out.println("Not Found");
            StringJoiner joiner = new StringJoiner(",");
            for (Integer slot : slotList) {
                joiner.add(slot + "");
            }
            System.out.println(joiner.toString());
        } catch (Exception e) {
            throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.readLock().unlock();
        }
    }


    @Override
    public int getSlotNoFromRegistrationNo(String registrationNo) throws ParkingException {
        int value = -1;
        lock.readLock().lock();
        validateParkingLot();
        try {
            value = parkingDataManager.getSlotNoFromRegistrationNo(registrationNo);
            System.out.println(value != -1 ? value : "Not Found");
        } catch (Exception e) {
            throw new ParkingException(ErrorCode.PROCESSING_ERROR.getMessage(), e);
        } finally {
            lock.readLock().unlock();
        }
        return value;
    }

    @Override
    public void doCleanup() {
        if (parkingDataManager != null)
            parkingDataManager.doCleanUp();
    }

}
