import java.util.ArrayList;

public class Column{

    // Stores the name of the Column
    private final String columnName;
    // Stores the values within the column as an array list
    private final ArrayList<String> arrayOfRows;

    // Function to initialise the column with its name and values
    public Column(String name, ArrayList<String> values){
        columnName = name;
        arrayOfRows = values;
    }

    // Function to initialise the column with just its name
    public Column(String name){
        columnName = name;
        arrayOfRows = new ArrayList<>();
    }

    // Returns the name of the column
    public String getName(){
        return columnName;
    }

    // Returns the size of the column
    public int getSize(){
        return arrayOfRows.size();
    }

    // Returns the value of the row at rowIndex
    public String getRowValue(int rowIndex){
        return arrayOfRows.get(rowIndex);
    }

    // Sets the value at rowIndex to value
    public void setRowValue(int rowIndex, String value){
        arrayOfRows.set(rowIndex, value);
    }

    // Adds a new row with the value being rowValue
    public void addRowValue(String rowValue){
        arrayOfRows.add(rowValue);
    }

    // Returns the values of the column
    public ArrayList<String> getColumnValues(){
        return arrayOfRows;
    }

}
