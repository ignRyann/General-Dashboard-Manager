import java.io.*;
import java.util.ArrayList;

public class DataLoader{

    private final DataFrame loadedDataFrame = new DataFrame();

    // Opens the CSV File and appends each entry in an ArrayList made up of String Arrays
    private ArrayList<String[]> openCsvFile(String filePath){
        ArrayList<String[]> csvData = new ArrayList<>();

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
        return csvData;
    }

    // Takes in an ArrayList of String Arrays and loads it into a new DataFrame
    private void createDataFrame(ArrayList<String[]> csvData){
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

    // Main body of the code; Returns the loaded data frame given the .cvs file path
    public DataFrame loadDataFrame(String filePath){
        ArrayList<String[]> csvData = openCsvFile(filePath);
        createDataFrame(csvData);
        return loadedDataFrame;
    }


}
