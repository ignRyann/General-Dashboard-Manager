// https://gyazo.com/30e942f2d4a056fd56e9c1f10cf079c1

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JSONWriter {


    public void addDataToJSONFile(DataFrame dataFrame, String fileName){
        try {
            FileWriter myWriter = new FileWriter("DataSet/" + fileName + ".json");

            myWriter.write("{");

            for (String columnName :   dataFrame.getColumnNames()){
                myWriter.write("\n\t\"" + columnName + "\"" + " : {");

                myWriter.write("\n\t\t\"columnValues\"" + " : [");
                for (int i = 0; i < dataFrame.getRowCount(); i++){

                    if (i != dataFrame.getRowCount()-1) {
                        myWriter.write("\"" + dataFrame.getColumnValues(columnName)[i] + "\", ");
                    }else{
                        myWriter.write("\"" + dataFrame.getColumnValues(columnName)[i] + "\"");
                    }

                }
                myWriter.write("]");

                if (columnName.equals(dataFrame.getColumnNames()[dataFrame.getColumnNames().length -1])){
                    myWriter.write("\n\t}");
                }else{
                    myWriter.write("\n\t},");
                }
            }

            myWriter.write("\n}");

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void createFile(String fileName){
        try {
            File myObj = new File("DataSet/" + fileName + ".json");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void createJSONFile(DataFrame dataFrame, String fileName){
        createFile(fileName);
        addDataToJSONFile(dataFrame, fileName);
    }

}
