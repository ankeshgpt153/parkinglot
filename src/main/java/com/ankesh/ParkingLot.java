package com.ankesh;

import com.ankesh.exception.ErrorCode;
import com.ankesh.exception.ParkingException;
import com.ankesh.processor.AbstractProcessor;
import com.ankesh.processor.RequestProcessor;
import com.ankesh.service.impl.ParkingServiceImpl;

import java.io.*;

/**
 * ParkingLot Problem
 */
public class ParkingLot {
    public static void main(String[] args) {

        AbstractProcessor processor = new RequestProcessor();
        processor.setService(new ParkingServiceImpl());
        BufferedReader bufferReader = null;
        String input = null;
            System.out.println("\n\n\n\n");
            System.out.println("===================================================================");
            System.out.println("===================     ANKESH PARKING LOT     ====================");
            System.out.println("===================================================================");
            switch (args.length) {
                default:
                    System.out.println("Invalid input. Usage Style: java -jar <jar_file_path> <input_file_path>");
            }
    }

}
