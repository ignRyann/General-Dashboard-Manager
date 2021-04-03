import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DataLoader{

    private final DataFrame loadedDataFrame = new DataFrame();

    // Opens a CSV File and appends each entry in an ArrayList made up of String Arrays
    private ArrayList<String[]> openCsvFile(String filePath){
        ArrayList<String[]> csvData = new ArrayList<>();

        try{
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
            String row;
            while (((row = csvReader.readLine()) != null)){
                String[] data = row.split(",");
                System.out.println(Arrays.toString(data));
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



        return csvData;
    }

    // Takes in an ArrayList of String[] in the format of {[columnNames], [Row 1 Data], [Row 2 Data]..}
    // Loads the data into the dataframe
    private void createDataFrameFromCsvData(ArrayList<String[]> csvData){
        for ( int counter = 0; counter != csvData.get(0).length; counter++){
            ArrayList<String> columnData = new ArrayList<>();
            for ( String[] record : csvData ){

                try {
                    columnData.add(record[counter]);
                }catch (ArrayIndexOutOfBoundsException e){
                    columnData.add("");
                }

            }
            String columnName = columnData.get(0);
            columnData.remove(0);
            loadedDataFrame.addColumn(columnName, columnData);
        }

    }

    // Opens a JSON File and stores the data to an ArrayList of String Arrays
    public ArrayList<String[]> openJSONFile(String filePath){
        ArrayList<String[]> jsonData = new ArrayList<>();

        try{
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
            String row;
            while (((row = csvReader.readLine()) != null)){
                if ( !((row.equals("{")) || (row.equals("}"))) ){
                    row = row.replaceAll(" : ", ",").replaceAll("\\[", "").replaceAll("]", "").replaceAll("\"", "").replaceAll("\t", "").replaceAll("\\s+", "");
                    String[] columnData = row.split(",");
                    jsonData.add(columnData);
                }
            }
            csvReader.close();
        } catch(FileNotFoundException e){
            System.out.println("The file could not be located.");
        } catch( IOException e){
            e.printStackTrace();
        }
        return jsonData;
    }

    // Takes in an ArrayList of String[] in the format of {[columnName, columnData..], [columnName, columnData..]..}
    // Loads the data into the dataframe
    private void createDataFrameFromJSONData(ArrayList<String[]> jsonData){
        for ( String[] columnData : jsonData ){
            String columnName = columnData[0];
            loadedDataFrame.addColumn(columnName);

            for (int i = 1; i < columnData.length; i++){
                loadedDataFrame.addValue(columnName, columnData[i]);
            }

        }
    }

    // Main body of the code; Returns the loaded data frame given the file path
    public DataFrame loadDataFrame(String filePath){
        if (filePath.endsWith(".csv")){
            ArrayList<String[]> csvData = openCsvFile(filePath);
            createDataFrameFromCsvData(csvData);
        }else{
            ArrayList<String[]> jsonData = openJSONFile(filePath);
            createDataFrameFromJSONData(jsonData);
        }
        return loadedDataFrame;
    }


}
