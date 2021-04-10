import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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

    // Saves the current DataFrame as a JSON File as fileName at folderLocation
    public String saveToJSONFile(String folderLocation, String fileName){
        if (currentDataFrame.isEmpty()) {
            return "The DataFrame is currently empty";
        } else {
            return new JSONWriter().saveAsJSONFile(currentDataFrame, folderLocation, fileName);
        }
    }

    // True if the DataFrame is empty, otherwise false
    public Boolean isEmpty(){
        return currentDataFrame.isEmpty();
    }

    // Takes in an unsorted HashMap and returns the sorted HashMap
    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortedMap)
    {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortedMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));
    }

    // Returns a sorted Hashmap of the integer values in descending order
    // The HashMap contains the frequency table data with the String being the Value and the Integer being the frequency
    private Map<String, Integer> getFrequencyTableData(String columnName){
        Map<String, Integer> frequencyTableData = new HashMap<>();

        for (String value : currentDataFrame.getColumnValues(columnName)) {
            if (frequencyTableData.containsKey(value)) {
                frequencyTableData.put(value, frequencyTableData.get(value) + 1);
            } else {
                frequencyTableData.put(value, 1);
            }
        }

        frequencyTableData = sortByValue(frequencyTableData);
        return frequencyTableData;
    }

    // Gets the Frequency Table Bar Chart Panel
    public JPanel getFrequencyDataChartsPanel(String columnName){
        JPanel frequencyDataChart = new JPanel(new GridLayout(2, 1));

        JTable frequencyTable = new JTable(){
            @Override
            // Makes it so the every cell is uneditable
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        // Loads the Frequency Data into the JTable
        DefaultTableModel frequencyTableModel = new DefaultTableModel(0, 0);
        frequencyTableModel.setColumnIdentifiers(new String[]{"Value", "Frequency"});

        getFrequencyTableData(columnName).forEach((k,v) -> {
            if (!k.equals("")){
                frequencyTableModel.addRow(new String[]{k, String.valueOf(v)});
            }else{
                frequencyTableModel.addRow(new String[]{"Null", String.valueOf(v)});
            }
        });

        frequencyTable.setModel(frequencyTableModel);

        // Changing frequencyTable settings for better readability of the data
        frequencyTable.getColumnModel().setColumnMargin(20);
        frequencyTable.getTableHeader().setResizingAllowed(false);
        frequencyTable.getTableHeader().setBackground(Color.LIGHT_GRAY);


        // Adding both graphs onto the same panel
        BarChartPanel barChartPanel = new BarChartPanel(getFrequencyTableData(columnName), columnName + "'s");
        frequencyDataChart.add(new JScrollPane(barChartPanel));
        frequencyDataChart.add(new JScrollPane(frequencyTable));

        return frequencyDataChart;
    }

}
