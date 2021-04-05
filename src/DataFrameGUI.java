import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Objects;

public class DataFrameGUI extends JFrame implements ActionListener{

    // Variables for DataFrame
    private final Model currentData = new Model();

    // Variables for the backPanel
    private JPanel backPanel;

    // Variables for the menuBar
    private JMenuBar menuBar;

    private JMenu fileMenu;
    private JMenu displayMenu;
    private JMenu tableHeadColourSubMenu;
    private JMenu helpMenu;

    private JMenuItem clearDataFrameItem;
    private JMenuItem loadDataFrameItem;
    private JMenuItem saveDataFrameItem;
    private JMenuItem yellowTableHeaderColourItem;
    private JMenuItem orangeTableHeaderColourItem;
    private JMenuItem greenTableHeaderColourItem;
    private JMenuItem cyanTableHeaderColourItem;
    private JMenuItem magentaTableHeaderColourItem;
    private JMenuItem redTableHeaderColourItem;
    private JMenuItem lightGrayTableHeaderColourItem;

    private ImageIcon clearIcon;
    private ImageIcon loadIcon;
    private ImageIcon saveIcon;

    // Variables for the dataSelectionPanel
    private JPanel dataSelectionPanel;
    private JButton showAllColumnsButton;
    private JButton hideAllColumnsButton;

    // Variables for the displayDataPanel
    private JPanel displayDataPanel;
    private JScrollPane scrollableDataFrameTable;
    private JTable dataFrameTable;

    // Variables for searchBarPanel
    private JPanel searchBarPanel;
    private JComboBox<String> searchColumnComboBox;
    private DefaultComboBoxModel<String> searchColumnComboBoxModel;
    private PlaceholderTextField searchBarTextField;
    private JLabel searchBarMatchesLabel;

    // Variables for saving the data to a .json file
    private JTextField fileNameTextField;
    private String fileName;
    private String statusMessage;
    private JCheckBox overwriteFileCheckBox;

    // Main body for the DataFrameGUI
    public DataFrameGUI(){
        createGUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setMinimumSize(new Dimension(1000, 400 ));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Creates the individual components and adds them together
    private void createGUI(){
        setTitle("DataFrame Dashboard");
        addMenuBar();
        createDataSelectionPanel();
        createDisplayDataPanel();
        createSearchBarPanel();
        addBackPanel();

    }

    // Creates the Menu Bar and adds it
    private void addMenuBar(){
        menuBar = new JMenuBar();

        // Creates the menu headers
        fileMenu = new JMenu("File");
        displayMenu = new JMenu("Display Options");
        tableHeadColourSubMenu = new JMenu("Change Table Head Colour");
        helpMenu = new JMenu("Help");

        // Creates the menu items for 'File'
        clearDataFrameItem = new JMenuItem("Clear");
        loadDataFrameItem = new JMenuItem("Load");
        saveDataFrameItem = new JMenuItem("Save");

        // Creates the Submenu items for 'Change Table Head Colour'
        yellowTableHeaderColourItem = new JMenuItem("Yellow");
        orangeTableHeaderColourItem = new JMenuItem("Orange");
        greenTableHeaderColourItem = new JMenuItem("Green");
        cyanTableHeaderColourItem = new JMenuItem("Cyan");
        magentaTableHeaderColourItem = new JMenuItem("Magenta");
        redTableHeaderColourItem = new JMenuItem("Red");
        lightGrayTableHeaderColourItem = new JMenuItem("Light Gray");

        // Sets the menu item's images
        clearIcon = new ImageIcon("img/clear.png");
        loadIcon = new ImageIcon("img/load.png");
        saveIcon = new ImageIcon("img/save.png");

        // Adds the menu item's images to the menu option
        clearDataFrameItem.setIcon(clearIcon);
        loadDataFrameItem.setIcon(loadIcon);
        saveDataFrameItem.setIcon(saveIcon);

        // Sets the function for when the menu item is clicked
        clearDataFrameItem.addActionListener(this);
        loadDataFrameItem.addActionListener(this);
        saveDataFrameItem.addActionListener(this);

        yellowTableHeaderColourItem.addActionListener(this);
        orangeTableHeaderColourItem.addActionListener(this);
        greenTableHeaderColourItem.addActionListener(this);
        cyanTableHeaderColourItem.addActionListener(this);
        magentaTableHeaderColourItem.addActionListener(this);
        redTableHeaderColourItem.addActionListener(this);
        lightGrayTableHeaderColourItem.addActionListener(this);

        // Shortcuts added for easier usability
        fileMenu.setMnemonic(KeyEvent.VK_F); // Keyboard shortcut : [Alt + f] or [Ctrl + Option + f] for File
        displayMenu.setMnemonic(KeyEvent.VK_D); // Keyboard shortcut : [Alt + d] or [Ctrl + Option + d] for Checklist
        clearDataFrameItem.setMnemonic(KeyEvent.VK_C); // Keyboard shortcut : [c] for Clear
        loadDataFrameItem.setMnemonic(KeyEvent.VK_L); // Keyboard shortcut : [l] for Load
        saveDataFrameItem.setMnemonic(KeyEvent.VK_S); // Keyboard shortcut : [s] for Save

        // Adds the menu item's to the menu headers
        fileMenu.add(clearDataFrameItem);
        fileMenu.add(loadDataFrameItem);
        fileMenu.add(saveDataFrameItem);

        tableHeadColourSubMenu.add(yellowTableHeaderColourItem);
        tableHeadColourSubMenu.add(orangeTableHeaderColourItem);
        tableHeadColourSubMenu.add(greenTableHeaderColourItem);
        tableHeadColourSubMenu.add(cyanTableHeaderColourItem);
        tableHeadColourSubMenu.add(magentaTableHeaderColourItem);
        tableHeadColourSubMenu.add(redTableHeaderColourItem);
        tableHeadColourSubMenu.add(lightGrayTableHeaderColourItem);
        displayMenu.add(tableHeadColourSubMenu);

        // Adds the menu headers to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        // Adds the menu bar to the GUI
        setJMenuBar(menuBar);

    }

    // Creates the dataSelectionPanel where the user will be able to select which columns are displayed/hidden
    private void createDataSelectionPanel(){
        dataSelectionPanel = new JPanel();
        dataSelectionPanel.setPreferredSize(new Dimension(200, 750));
        dataSelectionPanel.setBorder(BorderFactory.createEtchedBorder());

    }

    // Creates the displayDataPanel
    private void createDisplayDataPanel(){
        displayDataPanel = new JPanel(new BorderLayout());
        displayDataPanel.setPreferredSize(new Dimension(1400, 750));
        displayDataPanel.setBorder(BorderFactory.createEtchedBorder());

        // Creates a new JTable
        dataFrameTable = new JTable(){
            @Override
            // Makes it so the every cell is uneditable
            public boolean isCellEditable(int row, int column){
                return false;
            }

            // Sets the column width such that it is able to display all values it holds
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int rendererWidth = component.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                return component;
            }
        };

        // Commands for better readability of the data
        dataFrameTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        dataFrameTable.getTableHeader().setResizingAllowed(false);
        dataFrameTable.setCellSelectionEnabled(true);
        dataFrameTable.getColumnModel().setColumnMargin(20);
        dataFrameTable.getTableHeader().setBackground(Color.MAGENTA);

        // Adds it onto a scrollable component and then adds it to the displayDataPanel
        scrollableDataFrameTable = new JScrollPane(dataFrameTable);
        displayDataPanel.add(scrollableDataFrameTable, BorderLayout.CENTER);
    }

    // Creates the searchBarPanel
    private void createSearchBarPanel(){
        searchBarPanel = new JPanel(new FlowLayout());
        searchBarPanel.setBorder(BorderFactory.createEtchedBorder());

        // Creates the searchColumnComboBox
        searchColumnComboBoxModel = new DefaultComboBoxModel<>(new String[]{"All"});
        searchColumnComboBox = new JComboBox<>(searchColumnComboBoxModel);
        searchColumnComboBox.setPreferredSize(new Dimension(250, 30));
        searchColumnComboBox.addActionListener(this);

        // Creates the searchBarTextField
        searchBarTextField = new PlaceholderTextField();
        searchBarTextField.setPlaceholder(" Search Bar");
        searchBarTextField.setPreferredSize(new Dimension(500, 30));

        searchBarMatchesLabel = new JLabel("0 matches found.");

        // Filters the table depending on the text from searchBarTextField
        searchBarTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable(e);
            }

            // Searches for instances of the text and filters the table
            private void filterTable(DocumentEvent e){
                try{
                    String text = e.getDocument().getText(0, e.getDocument().getLength());

                    if (!text.equals("")) {
                        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(((DefaultTableModel) dataFrameTable.getModel()));

                        if (Objects.equals(searchColumnComboBox.getSelectedItem(), "All")) {
                            sorter.setRowFilter(RowFilter.regexFilter(text));
                        }else{
                            sorter.setRowFilter(RowFilter.regexFilter(text, searchColumnComboBox.getSelectedIndex() -1));
                        }

                        dataFrameTable.setRowSorter(sorter);
                        searchBarMatchesLabel.setText(dataFrameTable.getRowCount() + " matches found.");
                    }else {
                        dataFrameTable.setAutoCreateRowSorter(true);
                        searchBarMatchesLabel.setText("0 matches found.");
                    }
                }catch (BadLocationException e1){
                    e1.printStackTrace();
                }
            }
        });

        // Adds the components onto the searchBarPanel
        searchBarPanel.add(searchColumnComboBox, BorderLayout.CENTER);
        searchBarPanel.add(searchBarTextField, BorderLayout.CENTER);
        searchBarPanel.add(searchBarMatchesLabel, BorderLayout.EAST);
    }

    // Creates the backPanel and adds each of the components to it
    private void addBackPanel(){
        backPanel = new JPanel(new BorderLayout());

        backPanel.add(dataSelectionPanel, BorderLayout.WEST);
        backPanel.add(displayDataPanel, BorderLayout.CENTER);
        backPanel.add(searchBarPanel, BorderLayout.SOUTH);
        add(backPanel, BorderLayout.CENTER);

    }

    // Resets the DataFrame Dashboard
    private void clearData() {
        // Empties the DataFrame and updates the table and checklist Panel
        currentData.emptyDataFrame();
        dataFrameTable.setModel(currentData.getTable());

        updateMenuBar(false);
        updateDataSelectionPanel();
        updateSearchBarPanel(false);
    }

    // Loads the file into the DataFrame Dashboard
    private void loadFile() {
        JFileChooser fc = new JFileChooser(".");
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            String fileName = file.getName();

            try {
                if ( fileName.endsWith(".csv") || fileName.endsWith(".json")){
                    currentData.loadDataFrame(file.getAbsolutePath());
                    dataFrameTable.setModel(currentData.getTable());
                    dataFrameTable.setAutoCreateRowSorter(true);

                    updateMenuBar(true);
                    updateDataSelectionPanel();
                    updateSearchBarPanel(true);
                }else {
                    JOptionPane.showMessageDialog(this, "The selected file is not supported. Please choose a .csv file");
                }
            }catch (StringIndexOutOfBoundsException e){
                JOptionPane.showMessageDialog(this, "The selected file does not exit. Please choose a valid file");
            }
        }

    }

    // Retrieves the file name the user wishes to save the data as
    private void getFileName(){
        fileNameTextField = new JTextField();
        Object[] msgContent = {"Please enter the name of the file you wish to save the file as: ", fileNameTextField};
        JOptionPane.showConfirmDialog(this,  msgContent,  "Save File", JOptionPane.YES_NO_OPTION);

        fileName = fileNameTextField.getText();
    }

    // Main body to save the data as a .json file
    private void saveFile(){
        getFileName();
        // Saves the status message to display later
        statusMessage = currentData.saveToJSONFile(fileName);

        // If the filename already exists, give the user the option to replace it
        if (statusMessage.equals("The file " + fileName + ".json already exists")){
            overwriteFileCheckBox = new JCheckBox("Overwrite " + fileName + ".json");
            Object[] msgContent = {statusMessage, overwriteFileCheckBox};
            JOptionPane.showConfirmDialog(this,  msgContent,  null, JOptionPane.YES_NO_OPTION);

            if (overwriteFileCheckBox.isSelected()){
                File myObj = new File("DataSet/" + fileName + ".json");
                if (myObj.delete()) {
                    JOptionPane.showMessageDialog(this, currentData.saveToJSONFile(fileName), null, JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(this, "Unable to delete the file", null, JOptionPane.INFORMATION_MESSAGE);
                }
            }

        }else {
            // Display the status message
            JOptionPane.showMessageDialog(this, statusMessage, null, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Updates the dataSelectionPanel with the Column Names of the DataFrame loaded in
    private void updateDataSelectionPanel(){
        // Removes any previous data and creates a new layout
        dataSelectionPanel.removeAll();
        dataSelectionPanel.setLayout(new GridLayout(currentData.getColumnNames().length +2, 1, 10, 0));

        if (!currentData.isEmpty()){
            showAllColumnsButton = new JButton("Show All Columns");
            hideAllColumnsButton = new JButton("Hide All Columns");

            showAllColumnsButton.addActionListener(e -> checkAllBoxes(true));
            hideAllColumnsButton.addActionListener(e -> checkAllBoxes(false));

            dataSelectionPanel.add(showAllColumnsButton);
            dataSelectionPanel.add(hideAllColumnsButton);

            // Creates a checkbox for each Column Name in the DataFrame
            for (String columnName : currentData.getColumnNames()) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setText(columnName);
                checkBox.setSelected(true);

                // Depending on the state of the checkbox, it should show/hide the corresponding column
                checkBox.addItemListener(event -> {
                    JCheckBox cb = (JCheckBox) event.getSource();
                    currentData.changeColumnState(checkBox.getText(), cb.isSelected());
                    updateSearchBarPanel(true);
                    dataFrameTable.setModel(currentData.getTable());
                });

                dataSelectionPanel.add(checkBox);
            }
        }

        // Refreshes the dataSelectionPanel
        dataSelectionPanel.revalidate();
        dataSelectionPanel.repaint();
    }

    // Updates the searchBarPanel
    private void updateSearchBarPanel(Boolean dataLoadedIn){
        searchColumnComboBoxModel.removeAllElements();
        searchColumnComboBoxModel.addElement("All");
        searchBarTextField.setText("");

        // if data is loaded in the Dashboard Manager, add the column names that are displayed in the table
        if (dataLoadedIn) {
            for (String column : currentData.getShownColumnNames()) {
                searchColumnComboBoxModel.addElement(column);
            }
        }
    }

    // Updates the menu bar everytime the data is changed
    private void updateMenuBar(Boolean dataLoadedIn){
        menuBar.removeAll();
        menuBar.add(fileMenu);
        if (dataLoadedIn){
            menuBar.add(displayMenu);
        }
        menuBar.add(helpMenu);

    }

    // Selects or Deselects all the checkboxes within dataSelectionPanel
    private void checkAllBoxes(Boolean state){
        Component[] components = dataSelectionPanel.getComponents();

        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkbox = (JCheckBox) component;
                checkbox.setSelected(state);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Actions for when one of the File Menu Options have been selected
        if (e.getSource() == clearDataFrameItem){
            clearData();
        }
        else if (e.getSource() == loadDataFrameItem){
            loadFile();
        }
        else if (e.getSource() == saveDataFrameItem){
            saveFile();
        }

        // Action set for when the selected data in searchColumnComboBox has changed
        if (e.getSource() == searchColumnComboBox){
            searchBarTextField.setText("");
        }

        // If the user wishes to change the colour of the table headers
        if (e.getSource() == yellowTableHeaderColourItem){
            dataFrameTable.getTableHeader().setBackground(Color.YELLOW);
        }
        else if (e.getSource() == orangeTableHeaderColourItem){
            dataFrameTable.getTableHeader().setBackground(Color.ORANGE);
        }
        else if (e.getSource() == greenTableHeaderColourItem) {
            dataFrameTable.getTableHeader().setBackground(Color.GREEN);
        }
        else if (e.getSource() == cyanTableHeaderColourItem) {
            dataFrameTable.getTableHeader().setBackground(Color.CYAN);
        }
        else if (e.getSource() == magentaTableHeaderColourItem) {
            dataFrameTable.getTableHeader().setBackground(Color.MAGENTA);
        }
        else if (e.getSource() == redTableHeaderColourItem) {
            dataFrameTable.getTableHeader().setBackground(Color.RED);
        }
        else if (e.getSource() == lightGrayTableHeaderColourItem) {
            dataFrameTable.getTableHeader().setBackground(Color.LIGHT_GRAY);
        }


    }

}