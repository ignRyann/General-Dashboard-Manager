import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
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
    private JMenu checklistMenu;
    private JMenu helpMenu;
    private JMenuItem clearDataFrameItem;
    private JMenuItem loadDataFrameItem;
    private JMenuItem saveDataFrameItem;
    private JMenuItem showAllColumnsItem;
    private JMenuItem hideAllColumnsItem;
    private ImageIcon clearIcon;
    private ImageIcon loadIcon;
    private ImageIcon saveIcon;

    // Variables for the dataSelectionPanel
    private JPanel dataSelectionPanel;

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

    // Main body for the DataFrameGUI
    public DataFrameGUI(){
        createGUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Creates the individual components and adds them together
    private void createGUI(){
        setTitle("DataFrame Dashboard");
        addMenuBar();
        createColumnChecklistPanel();
        createDisplayDataPanel();
        createSearchBarPanel();
        addBackPanel();

    }

    // Creates the Menu Bar and adds it
    private void addMenuBar(){
        menuBar = new JMenuBar();

        // Creates the menu headers
        fileMenu = new JMenu("File");
        checklistMenu = new JMenu("Checklist");
        helpMenu = new JMenu("Help");

        // Creates the menu items
        clearDataFrameItem = new JMenuItem("Clear");
        loadDataFrameItem = new JMenuItem("Load");
        saveDataFrameItem = new JMenuItem("Save");
        showAllColumnsItem = new JMenuItem("Show All Columns");
        hideAllColumnsItem = new JMenuItem("Hide All Columns");

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
        showAllColumnsItem.addActionListener(this);
        hideAllColumnsItem.addActionListener(this);

        // Shortcuts added for easier usability
        fileMenu.setMnemonic(KeyEvent.VK_F); // Keyboard shortcut : [Alt + f] or [Ctrl + Option + f] for File
        checklistMenu.setMnemonic(KeyEvent.VK_C); // Keyboard shortcut : [Alt + c] or [Ctrl + Option + c] for Checklist
        clearDataFrameItem.setMnemonic(KeyEvent.VK_C); // Keyboard shortcut : [c] for Clear
        loadDataFrameItem.setMnemonic(KeyEvent.VK_L); // Keyboard shortcut : [l] for Load
        saveDataFrameItem.setMnemonic(KeyEvent.VK_S); // Keyboard shortcut : [s] for Save
        showAllColumnsItem.setMnemonic(KeyEvent.VK_S); // Keyboard shortcut : [s] for Show All Columns
        hideAllColumnsItem.setMnemonic(KeyEvent.VK_H); // Keyboard shortcut : [h] for Hide All Columns

        // Adds the menu item's to the menu headers
        fileMenu.add(clearDataFrameItem);
        fileMenu.add(loadDataFrameItem);
        fileMenu.add(saveDataFrameItem);
        checklistMenu.add(showAllColumnsItem);
        checklistMenu.add(hideAllColumnsItem);

        // Adds the menu headers to the menu bar
        menuBar.add(fileMenu);
        menuBar.add(checklistMenu);
        menuBar.add(helpMenu);

        // Adds the menu bar to the GUI
        setJMenuBar(menuBar);

    }

    // Creates the dataSelectionPanel where the user will be able to select which columns are displayed/hidden
    private void createColumnChecklistPanel(){
        dataSelectionPanel = new JPanel(new GridLayout(currentData.getColumnNames().length, 1, 10, 0));
        dataSelectionPanel.setPreferredSize(new Dimension(200, 750));
        dataSelectionPanel.setBorder(BorderFactory.createEtchedBorder());

    }

    // Creates the displayDataPanel
    private void createDisplayDataPanel(){
        displayDataPanel = new JPanel(new BorderLayout());
        displayDataPanel.setPreferredSize(new Dimension(1500, 750));
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
        dataFrameTable.setCellSelectionEnabled(true);
        dataFrameTable.getTableHeader().setReorderingAllowed(false);
        dataFrameTable.getColumnModel().setColumnMargin(20);
        dataFrameTable.getTableHeader().setBackground(Color.LIGHT_GRAY);

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
                        dataFrameTable.setRowSorter(null);
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
        updateDataSelectionPanel();

        // Resets the components within the searchBarPanel
        searchBarTextField.setText("");
        searchColumnComboBoxModel.removeAllElements();
        searchColumnComboBoxModel.addElement("All");
    }

    // Loads the file into the DataFrame Dashboard
    private void loadFile() {
        JFileChooser fc = new JFileChooser(".");
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            String fileName = file.getName();

            try {
                if (fileName.substring(fileName.lastIndexOf(".")).equals(".csv")) {
                    currentData.loadDataFrame(file.getAbsolutePath());
                    dataFrameTable.setModel(currentData.getTable());
                    dataFrameTable.setAutoCreateRowSorter(true);

                    updateDataSelectionPanel();
                    searchBarTextField.setText("");
                    updateSearchColumnComboBoxModel();
                } else {
                    JOptionPane.showMessageDialog(this, "The selected file is not supported. Please choose a .csv file");
                }
            }catch (StringIndexOutOfBoundsException e){
                JOptionPane.showMessageDialog(this, "The selected file does not exit. Please choose a valid file");
            }
        }

    }

    // Updates the dataSelectionPanel with the Column Names of the DataFrame loaded in
    private void updateDataSelectionPanel(){
        // Removes any previous data and creates a new layout
        dataSelectionPanel.removeAll();
        dataSelectionPanel.setLayout(new GridLayout(currentData.getColumnNames().length, 1, 10, 0));

        // Creates a checkbox for each Column Name in the DataFrame
        for (String columnName : currentData.getColumnNames()){
            JCheckBox checkBox = new JCheckBox();
            checkBox.setText(columnName);
            checkBox.setSelected(true);

            // Depending on the state of the checkbox, it should show/hide the corresponding column
            checkBox.addItemListener(event -> {
                JCheckBox cb = (JCheckBox) event.getSource();
                currentData.changeColumnState(checkBox.getText(), cb.isSelected());
                updateSearchColumnComboBoxModel();
                dataFrameTable.setModel(currentData.getTable());
            });

            dataSelectionPanel.add(checkBox);
        }

        // Refreshes the dataSelectionPanel
        dataSelectionPanel.revalidate();
        dataSelectionPanel.repaint();
    }

    // Updates the searchColumnComboBoxModel to show the column names that are displayed
    private void updateSearchColumnComboBoxModel(){
        searchColumnComboBoxModel.removeAllElements();
        searchColumnComboBoxModel.addElement("All");
        for (String column : currentData.getShownColumnNames()){
            searchColumnComboBoxModel.addElement(column);
        }
    }

    // Selects or Deselects all the checkboxes within dataSelectionPanel
    private void checkAllBoxes(Boolean state){
        for (int i = 0; i < dataSelectionPanel.getComponentCount(); i++) {
            JCheckBox checkBox = (JCheckBox) dataSelectionPanel.getComponent(i);
            checkBox.setSelected(state);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Actions for when one of the File Menu Options have been selected
        if (e.getSource() == clearDataFrameItem){
            clearData();
        }
        if (e.getSource() == loadDataFrameItem){
            loadFile();
        }
        if (e.getSource() == saveDataFrameItem){
            System.out.println("Saved the data to a .csv file");
        }

        // Action set for when the selected data in searchColumnComboBox has changed
        if (e.getSource() == searchColumnComboBox){
            searchBarTextField.setText("");
        }

        // Actions for when the user chooses to either show/hide all columns
        if (e.getSource() == showAllColumnsItem){
            checkAllBoxes(true);
        }

        if (e.getSource() == hideAllColumnsItem){
            checkAllBoxes(false);
        }

    }

}