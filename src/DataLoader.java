import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class DataLoader{

    private final DataFrame loadedDataFrame = new DataFrame();

    // Opens a CSV File and appends each entry in an ArrayList made up of String Arrays
    private ArrayList<ArrayList<String>> openCsvFile(String filePath){
        ArrayList<ArrayList<String>> csvDataCorrectFormat = new ArrayList<>();
        ArrayList<String[]> csvData = new ArrayList<>();

        // Loads the data from .csv file and appends each row into csvData
        try{
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
            String row;
            while (((row = csvReader.readLine()) != null)){
                String[] data = row.split(",");
                csvData.add(data);
            }
            csvReader.close();
        }
        catch(FileNotFoundException e){
            System.out.println("The file could not be located.");
        }
        catch(IOException e){
            e.printStackTrace();
        }

        // Rearranges the data such that each ArrayList<String> contains the columnName and the data it holds
        for ( int counter = 0; counter != csvData.get(0).length; counter++){
            ArrayList<String> columnData = new ArrayList<>();
            for ( String[] record : csvData ){

                try {
                    columnData.add(record[counter]);
                }catch (ArrayIndexOutOfBoundsException e){
                    columnData.add("");
                }

            }
            csvDataCorrectFormat.add(columnData);
        }

        return csvDataCorrectFormat;
    }

    // Opens a JSON File and stores the data to an ArrayList of String Arrays
    private ArrayList<ArrayList<String>> openJSONFile(String filePath){
        ArrayList<ArrayList<String>> jsonData = new ArrayList<>();

        try{
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
            String row;
            while (((row = csvReader.readLine()) != null)){
                if ( !((row.equals("{")) || (row.equals("}"))) ){
                    // Makes it such that the row contains only the data itself separated by commas
                    row = row.replaceAll("\",", "").replaceAll(" :", ",").replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "").replaceAll("\t", "").replaceAll(", ", ",  ");
                    String[] columnData = row.split(", ");

                    for (int i = 0; i < columnData.length; i++){
                        // In the case of empty values
                        if (!columnData[i].isBlank()){
                            columnData[i] = columnData[i].trim();
                        }
                    }
                    jsonData.add(new ArrayList<>(Arrays.asList(columnData)));
                }
            }
            csvReader.close();
        }
        catch(FileNotFoundException e){
            System.out.println("The file could not be located.");
        }
        catch( IOException e){
            e.printStackTrace();
        }
        return jsonData;
    }

    // Takes in an ArrayList of String[] in the format of {[columnName, columnData..], [columnName, columnData..], ..}
    // Loads the data into the dataframe
    private void createDataFrame(ArrayList<ArrayList<String>> fileData){
        for ( ArrayList<String> columnData : fileData ){
            String columnName = columnData.get(0);
            columnData.remove(0);
            loadedDataFrame.addColumn(columnName, columnData);
        }
    }

    // Main body of the code; Returns the loaded data frame given the file path
    public DataFrame loadDataFrame(String filePath){
        ArrayList<ArrayList<String>> data;

        if (filePath.endsWith(".csv")){
            data = openCsvFile(filePath);
        } else {
            data = openJSONFile(filePath);
        }

        createDataFrame(data);
        return loadedDataFrame;
    }

}
