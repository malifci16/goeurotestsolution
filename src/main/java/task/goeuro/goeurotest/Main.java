package task.goeuro.goeurotest;

import task.goeuro.goeurotest.api.Client;

/**
 *
 * @author Mohamed Ali
 *
 * The main class for the task
 */
public class Main {

    /**
     * The main method for the task
     *
     * @param args
     */
    public static void main(String[] args) {

        // Check if Parameter Sent or Not
        if (args.length != 0) {
            Client aPIRequest = new Client(args[0]);
            aPIRequest.query();
            if (aPIRequest.isRequestFailed()) {
                // Request Failed
                System.out.println(aPIRequest.getResponseMessage());
            } else // Request Success
            {
                aPIRequest.deSerialize();
                if (!aPIRequest.getSuggestedPositions().isEmpty()) {
                    aPIRequest.generateCSV();
                } else {
                    // Handle No Values Returned
                    System.out.println("No Results Found");
                }
            }
        } else {
            System.out.println("City Name Parameter is Required");
        }
    }
}
