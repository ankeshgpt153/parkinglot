/**
 *
 */
package com.ankesh.processor;


import com.ankesh.constants.Constants;
import com.ankesh.exception.ErrorCode;
import com.ankesh.exception.ParkingException;
import com.ankesh.model.Car;
import com.ankesh.service.ParkingService;

/**
 *
 * @author ankesh
 *
 * Class to process request
 */
public class RequestProcessor implements AbstractProcessor {
    private ParkingService parkingService;

    @Override
    public void execute(String input) throws ParkingException {
        String[] inputs = input.split(" ");
        String key = inputs[0];
        switch (key) {
            case Constants.CREATE_PARKING_LOT:
                try {
                    int capacity = Integer.parseInt(inputs[1]);
                    parkingService.createParkingLot(capacity);
                } catch (NumberFormatException e) {
                    throw new ParkingException(ErrorCode.INVALID_VALUE.getMessage().replace("{variable}", "capacity"));
                }
                break;
            case Constants.PARK:
                parkingService.park(new Car(inputs[1], inputs[2]));
                break;
            case Constants.LEAVE:
                try {
                    int timeDuration = Integer.parseInt(inputs[2]);
                    parkingService.unPark(inputs[1], timeDuration);
                } catch (NumberFormatException e) {
                    throw new ParkingException(
                            ErrorCode.INVALID_VALUE.getMessage().replace("{variable}", "timeDuration"));
                }
                break;
            case Constants.STATUS:
                parkingService.getStatus();
                break;
            case Constants.REG_NUMBER_FOR_CARS_WITH_COLOR:
                parkingService.getRegNumberForColor(inputs[1]);
                break;
            case Constants.SLOTS_NUMBER_FOR_CARS_WITH_COLOR:
                parkingService.getSlotNumbersFromColor(inputs[1]);
                break;
            case Constants.SLOTS_NUMBER_FOR_REG_NUMBER:
                parkingService.getSlotNoFromRegistrationNo(inputs[1], true);
                break;
            default:
                break;
        }
    }

    @Override
    public void setParkingService(ParkingService service) {
        this.parkingService = (ParkingService) service;
    }

}
