/**
 *
 */
package com.ankesh.processor;


import com.ankesh.constants.InputMap;
import com.ankesh.exception.ParkingException;
import com.ankesh.service.BillingService;
import com.ankesh.service.ParkingService;

/**
 * @author ankesh
 *
 */
public interface AbstractProcessor {
    public void setParkingService(ParkingService service);

    public void execute(String action) throws ParkingException;

    public default boolean validate(String inputString) {
        // Split the input string to validate command and input value
        boolean valid = true;
        try {
            String[] inputs = inputString.split(" ");
            int params = InputMap.getInputParameterMap().get(inputs[0]);
            switch (inputs.length) {
                case 1:
                    if (params != 0) // e.g status -> inputs = 1
                        valid = false;
                    break;
                case 2:
                    if (params != 1) // create_parking_lot 6 -> inputs = 2
                        valid = false;
                    break;
                case 3:
                    if (params != 2) // park KA-01-P-333 White -> inputs = 3 leave KA-01-P-333 4 -> inputs = 3
                        valid = false;
                    break;
                default:
                    valid = false;
            }
        } catch (Exception e) {
            valid = false;
        }
        return valid;
    }
}
