/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task.goeuro.goeurotest.api;

import task.goeuro.goeurotest.objects.SuggestedPosition;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import task.goeuro.goeurotest.helpers.DateHelper;

/**
 * This class is working as HTTP Client, de-serialize the response and generate
 * CSV file
 *
 * @author Mohamed Ali
 */
public class Client {

    private boolean requestFailed;
    private String responseMessage;
    private final String cityName;
    private String responseBodyContent;
    private final Date requestDate;
    private final List<SuggestedPosition> suggestedPositions;
    private final Object[] FILE_HEADER = {"ID", "Name", "Type", "Latitude", "Longitude"};

    /**
     * The default and only constructor
     *
     * @param cityName : user query string
     */
    public Client(String cityName) {
        this.cityName = cityName;
        suggestedPositions = new ArrayList<>();
        requestDate = new Date();
    }

    /**
     * Method to create HTTP Client, check if response is valid and create error
     * message if exist
     */
    public void query() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://api.goeuro.com/api/v2/position/suggest/en/" + cityName)
                    .get()
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "75b78b74-62f1-e9f6-d9d9-8e25dfd82341")
                    .build();
            Response response = client.newCall(request).execute();
            if (response.code() != 200) {
                requestFailed = true;
                responseMessage = response.message();
                return;
            }
            responseMessage = "Request Success";
            InputStream in = response.body().byteStream();
            String encoding = response.headers().get("charset");
            encoding = encoding == null ? "UTF-8" : encoding;
            responseBodyContent = IOUtils.toString(in, encoding);
        } catch (Exception ex) {
            requestFailed = true;
            responseMessage = "Request Couldn't be Initiated";
        }
    }

    /**
     * Method to de-serialize JSON response array to JAVA objects
     */
    public void deSerialize() {
        JsonElement jelement = new JsonParser().parse(responseBodyContent);
        JsonArray asJsonArray = jelement.getAsJsonArray();
        if (asJsonArray.size() != 0) {
            SuggestedPosition suggestedPosition;
            Gson gson = new Gson();
            for (JsonElement jsonElement : asJsonArray) {
                // Deserialize and Add to Suggested Positions List
                suggestedPosition = gson.fromJson(jsonElement, SuggestedPosition.class);
                suggestedPositions.add(suggestedPosition);
            }
        }
    }

    /**
     * Method to generate CSV File
     */
    public void generateCSV() {
        String fileName = DateHelper.timeFormat(requestDate) + " " + DateHelper.dateFormat(requestDate) + " " + cityName + " Query";
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT;
        try {
            //initialize FileWriter object
            fileWriter = new FileWriter(fileName);
            //initialize CSVPrinter object
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            //Create CSV file header
            csvFilePrinter.printRecord(FILE_HEADER);
            //Write a new student object list to the CSV file
            for (SuggestedPosition suggestedPosition : suggestedPositions) {
                List positionRecords = new ArrayList();
                positionRecords.add(String.valueOf(suggestedPosition.getId()));
                positionRecords.add(suggestedPosition.getName());
                positionRecords.add(suggestedPosition.getType());
                positionRecords.add(String.valueOf(suggestedPosition.getGeographicalPosition().getLatitude()));
                positionRecords.add(String.valueOf(suggestedPosition.getGeographicalPosition().getLongitude()));
                csvFilePrinter.printRecord(positionRecords);
            }
            System.out.println("CSV Response File Created Successfully");
        } catch (Exception e) {
            System.out.println("Error in CSV File Writer");
        } finally {
            try {
                fileWriter.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                System.out.println("Error While Creating CSV Rsponse File");
            }
        }
    }

    /**
     *
     * @return if the request failed or not
     */
    public boolean isRequestFailed() {
        return requestFailed;
    }

    /**
     *
     * @return suggested positions returned from the API
     */
    public List<SuggestedPosition> getSuggestedPositions() {
        return suggestedPositions;
    }

    /**
     *
     * @return response Message
     */
    public String getResponseMessage() {
        return responseMessage;
    }

}
