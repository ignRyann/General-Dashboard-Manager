import java.util.ArrayList;

public class DataFrame{

    // Stores the columns within the dataframe as an ArrayList
    private final ArrayList<Column> Columns = new ArrayList<>();

    // Adds a column to the DataFrame given a name and a set of values
    public void addColumn(String name, ArrayList<String> rowValues){
        Column newColumn = new Column(name, rowValues);
        Columns.add(newColumn);
    }

    // Returns a String containing a list of the column names
    public String[] getColumnNames(){
        ArrayList<String> columnNames = new ArrayList<>();

        for (Column col : Columns) {
            columnNames.add(col.getName());
        }

        return columnNames.toArray(new String[0]);
    }

    // Returns the number of rows in the DataFrame
    public int getRowCount(){
        int rowCount = 0;

        for (Column col : Columns) {
            if (rowCount < col.getSize()) {
                rowCount = col.getSize();
            }
        }

        return rowCount;
    }

    // Gets the value within a row in a column
    public String getValue(String columnName, int row){
        String rowValue = "-1";

        for (Column col : Columns) {
            if (col.getName().equals(columnName)){
                rowValue = col.getRowValue(row);
                break;
            }
        }

        return rowValue;
    }

    // Sets the value at a given row and column with a value specified
    public void putValue(String columnName, int row, String value){

        for (Column col : Columns){
            if (col.getName().equals(columnName)){
                col.setRowValue(row, value);
            }
        }

    }

    // Adds a new value to the end of the column with a value specified
    public void addValue(String columnName, String value){

        for (Column col : Columns){
            if (col.getName().equals(columnName)){
                col.addRowValue(value);
            }
        }

    }

    // Gets the values within the column
    public String[] getColumnValues(String columnName){
        ArrayList<String> columnValues = new ArrayList<>();

        for (Column col : Columns){
            if (col.getName().equals(columnName)){
                columnValues = col.getColumnValues();
                break;
            }
        }

        return columnValues.toArray(new String[0]);
    }

    public Boolean isEmpty(){
        return Columns.size() == 0;
    }

}
