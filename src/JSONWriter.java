import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONWriter {

    private String message;

    // Makes sure that the file name is valid
    public boolean fileNameValid(String fileName) {
        if (!fileName.equals("")) {
            Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
            Matcher matcher = pattern.matcher(fileName);
            return !matcher.find();
        }else{
            return false;
        }
    }

    // Creates the file to which the DataFrame data will be saved to
    public void createFile(String folderLocation, String fileName){
        try {
            File myObj = new File(folderLocation + "/" + fileName + ".json");
            if (myObj.createNewFile()) {
                message = "The DataFrame has been saved to " + fileName + ".json";
            } else {
                message = "The file " + fileName + ".json already exists";
            }
        } catch (IOException e) {
            message = "An error has occurred whilst saving the current DataFrame to " + fileName + ".json";
            e.printStackTrace();
        }
    }

    // Adds the data from dataFrame to the file fileName
    public void addDataToFile(DataFrame dataFrame, String folderLocation, String fileName){
        try {
            FileWriter myWriter = new FileWriter(folderLocation + "/" + fileName + ".json");

            myWriter.write("{");

            for (String columnName : dataFrame.getColumnNames()){
                myWriter.write("\n\t\"" + columnName + "\" : \"");
                myWriter.write(Arrays.toString(dataFrame.getColumnValues(columnName)) + "\"");

                if (!columnName.equals(dataFrame.getColumnNames()[dataFrame.getColumnNames().length - 1])) {
                    myWriter.write(",");
                }
            }

            myWriter.write("\n}");

            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Main body for saving the dataFrame as a .json file
    public String saveAsJSONFile(DataFrame dataFrame, String folderLocation, String fileName){
        if (fileNameValid(fileName)) {
            createFile(folderLocation, fileName);
            if (new File(folderLocation + "/" + fileName + ".json").isFile()) {
                addDataToFile(dataFrame, folderLocation, fileName);
            }else{
                message = "The file could not be created";
            }
        }else{
            message = "The file name must only contains letters and numbers";
        }

        return message;
    }

}
