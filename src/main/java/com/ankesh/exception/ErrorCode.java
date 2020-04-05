/**
 *
 */
package com.ankesh.exception;

/**
 * @author ankesh
 *
 */
public enum ErrorCode {
    PARKING_ALREADY_EXIST("Sorry Parking Already Created, It Can not be recreated again."),
    PARKING_NOT_EXIST_ERROR("Sorry, Car Parking Does not Exist"),
    INVALID_VALUE("{variable} value is incorrect"),
    PROCESSING_ERROR("Processing Error "),
	INVALID_FILE("Invalid File"),
    INVALID_REQUEST("Invalid Request");

    private String message = "";

    /**
     * @param message
     */
    private ErrorCode(String message) {
        this.message = message;
    }

    /**
     * @return String
     */
    public String getMessage() {
        return message;
    }
}
