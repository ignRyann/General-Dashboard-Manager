import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class Model {

    // currentDataFrame holds the DataFrame of the currently loaded data
    private DataFrame currentDataFrame = new DataFrame();
    // An ArrayList of column headers that should be shown
    private ArrayList<Boolean> shownColumnHeaders;

    // Empties the data
    public void emptyDataFrame(){
        currentDataFrame = new DataFrame();
        shownColumnHeaders = new ArrayList<>();
    }

    // Loads data into the model
    public void loadDataFrame(String filePath){
        currentDataFrame = new DataLoader().loadDataFrame(filePath);
        shownColumnHeaders = new ArrayList<>();
        for (String ignored : currentDataFrame.getColumnNames()){
            shownColumnHeaders.add(true);
        }

    }

    // Returns a String[] of all the column names
    public String[] getColumnNames(){
        return currentDataFrame.getColumnNames();
    }

    // Returns a String[] of all the column names that are shown in the table
    public String[] getShownColumnNames(){
        ArrayList<String> shownColumnNames = new ArrayList<>();
        for (int i = 0; i < shownColumnHeaders.size(); i++){
            if (shownColumnHeaders.get(i)){
                shownColumnNames.add(currentDataFrame.getColumnNames()[i]);
            }
        }

        return shownColumnNames.toArray(new String[0]);
    }

    // Changes the state of a column: shown / hidden
    public void changeColumnState(String columnName, Boolean state){
        for (int i = 0; i < currentDataFrame.getColumnNames().length; i++){
            if (currentDataFrame.getColumnNames()[i].equals(columnName)){
                shownColumnHeaders.set(i, state);
            }
        }
    }

    // Retrieves the table model to show in the JTable
    public DefaultTableModel getTable(){
        DefaultTableModel dataFrameTableModel = new DefaultTableModel(0, 0);

        for (int i = 0; i < shownColumnHeaders.size(); i++){
            if (shownColumnHeaders.get(i)){
                dataFrameTableModel.addColumn(currentDataFrame.getColumnNames()[i], currentDataFrame.getColumnValues(currentDataFrame.getColumnNames()[i]));
            }
        }

        return dataFrameTableModel;
    }

}
