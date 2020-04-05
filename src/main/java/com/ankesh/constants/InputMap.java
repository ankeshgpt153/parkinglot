package com.ankesh.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ankesh
 */
public class InputMap {
    private static volatile Map<String, Integer> inputParameterMap = new HashMap<String, Integer>();

    static {
        inputParameterMap.put(Constants.CREATE_PARKING_LOT, 1);
        inputParameterMap.put(Constants.PARK, 2);
        inputParameterMap.put(Constants.LEAVE, 2);
        inputParameterMap.put(Constants.STATUS, 0);
        inputParameterMap.put(Constants.REG_NUMBER_FOR_CARS_WITH_COLOR, 1);
        inputParameterMap.put(Constants.SLOTS_NUMBER_FOR_CARS_WITH_COLOR, 1);
        inputParameterMap.put(Constants.SLOTS_NUMBER_FOR_REG_NUMBER, 1);
    }

    /**
     * @return the inputParameterMap
     */
    public static Map<String, Integer> getInputParameterMap() {
        return inputParameterMap;
    }

    /**
     * @param command,parameterCount the inputParameterMap to set
     */
    public static void addCommand(String command, int parameterCount) {
        inputParameterMap.put(command, parameterCount);
    }

}
