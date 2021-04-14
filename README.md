# General-Dashboard-Manager

##### 1. Description
##### 2. General Dashboard Manager Breakdown

## Description
The General Dashboard Manager is a program, written in Java, that completes Requirements 1 - 9 set for UCL COMP0004 : Object-Oriented Programming Coursework 2. The Requirements were set out as the following:

### {
Implement a program for the requirements listed below.
Assume that all the data held in a DataFrame or Column is always of type String even if there are numerical values or dates. The requirements form a specification for the dashboard program, so they don’t have to be answered in order and you may well find it easier to work on each requirement in stages as you develop your program.

#### Requirement 1. 
Write a class Column, where a column has a name and an ArrayList of rows. Provide methods 
- getName
- getSize 
- getRowValue 
- setRowValue
- addRowValue (to add a new row)
Determine what the parameters and return types should be. If you find additional methods are needed when answering later questions then add them, but only when you need them.
#### Requirement 2. 
Write a class DataFrame to hold a collection of Columns. It should have the following methods:
- addColumn
- getColumnNames (a list of names in the same order as they are stored in the frame).
- getRowCount (the number of rows in a column, all columns should have the same number of rows when the frame is fully loaded with data).
- getValue(columnName, row) to get a row value from a column.
- putValue(columnName, row, value) to put a value into a row in a column.
- addValue(columnName, value) to add a value to a column (at the end).
Again you can add methods if you find you need them when answering the later questions. Also remember that all the data is in String format.
#### Requirement 3. 
Write a class DataLoader that can read a .csv data file and load the data into an empty DataFrame. The Column names are found as the first row in the .csv data file. It should have a method that returns a filled DataFrame.
Note that the file input methods will require you to manage IOExceptions that can be thrown (e.g., trying to open a file that doesn’t exist). You will have to decide how to manage these. Your program should not crash or terminate if an exception is thrown!
#### Requirement 4. 
The program should have a Swing GUI which should communicate with a class Model to access the data to display. The GUI should only use the Model to get data and not use or know about DataFrames or Columns. Class Model provides a wrapper or Facade that hides how data is stored, providing a set of public methods the GUI can use as needed. While not part of this coursework the aim of having the model is to allow alternate data storage (e.g., a SQL database) to be used without having to change any of the code that uses the Model.
Write a class Model that manages a DataFrame internally and provides the methods that your GUI needs, and uses the DataLoader to load data into a frame.
#### Requirement 5. 
Write a Swing GUI that allows the data to be viewed in various ways. An example of this could be a simple GUI with the column names displayed as check boxes that can be selected to determine what to display in the main panel, and there is a load data file button. You can design your own GUI and provide more (or better) ways of displaying the data!
#### Requirement 6. 
Write a Main class that contains the main method and any code to initialise the program, such as creating the GUI and Model objects. This class should contain the minimum code needed to get the program running, so will be quite short. The code for building the GUI out of Swing components should be in the GUI class.
#### Requirement 7. 
Add the ability to search the data and perform operations such as finding the oldest person, the number of people living in the same place, and so on. The GUI will need to be extended to give access to this functionality. Remember that the GUI only displays data it can get from the Model, so the implementation of search and other functions should be in the Model or other classes, not in the GUI code. Input from the GUI should be passed as parameters in method calls to the Model.
#### Requirement 8. 
Write a class JSONWriter that given a DataFrame writes out the data to a file in JSON format. The JSON should be well-formed. You have to write all the code to generate the JSON yourself and cannot use a JSON library. As you will be doing a lot of String manipulation investigate the StringBuilder class, as this is much more efficient at building strings than the String class itself. Add a save to JSON format functionality to your GUI.
Also write a JSONReader that can read data files in JSON format, rather than the .csv format, and add the data into a DataFrame.
#### Requirement 9. 
Add the ability to display graphs or charts to show the data, for example distribution by age. You must write all the code to display a graph or chart yourself, and cannot use a library that you might find on the web somewhere.
### }

## Dashboard Manager Breakdown

### Keyboard Shorcuts
The following Keyboard Shortcuts were implemented for better and faster functionality of the program. The Keyboard shortcuts do differ depending on the Operating System you are using. The Keyboard Shorcuts will be listed out for Mac OS however if you are using Windows OS, use [Alt] instead of [Ctrl + Option]. The Keyboard Shortcuts are as follows:
- [Ctrl + Option + f] for 'File' Menu
- [Ctrl + Option + v] for 'Visual Settings' Menu
- [Ctrl + Option + h] for 'Help' Menu

When within the 'File' Menu, you can easily navigate through the different menu items using these shortcuts:
- [c] for 'Clear'
- [l] for 'Load'
- [s] for 'Save'

### The Menu Bar
When launching the General Dashboard Manager, the menu bar only displays the 'File' menu and the 'Help' menu. The 'File' menu consists of 3 menu items; Clear, Load, Save. 
##### Clear Menu Item
The menu item will clear the General DashBoard Manager. This empties the dataframe and resets the visual settings as if the program was just launched.
##### Load Menu Item
The menu item will allow the user to select a file they wish to load its contents into the Dashboard Manager. The accepted file types are those .json and .csv and an error message will display if neither are selected.
##### Save Menu Item
The menu item will save the the data contents of the DataFrame to a valid .json file if the DataFrame is not empty. The user is able to choose a file name and the folder location they wish to save it in. The file name must only consist of letters and numbers.

#### Visual Settings Menu Item
When data has been loaded in to the General Dashboard Manager, the menu bar will also display the 'Visual Settings' menu. This has been added so that the user can change the way the data is displayed to their own preference. The user is given 3 visual features they can change within the program:
###### - Change Table Head Colour
The user can change the Table Header Colour of each JTable to any of the following options: Yellow, Orange, Green, Cyan, Magenta, Red and Light Gray. The default option is Light Gray.
###### - Change Text Size
The user can change the size of the text anywhere between the sizes of 8 and 16 inclusive. The default option is size 12.
###### - Change Text Style
The user can change the style of the text; plain, italic, bold. The default option is plain.

### The Main Tab
The main tab is where the loaded data contents are shown and is titled after the file name. Each column has been resized to ensure that the user is able to view all its contents and in the case where the table is larger than what the panel can display, the user is able to scroll to view all the data. The user is unable to edit the JTable but is able to move the table columns if they prefer to do so. Clicking once on the column header sorts the table depending on the values of the column selected in ascending order. If the user clicks again on the same column, the table is sorted in descending order.

### The Data Selection Panel
The data selection panel is located to the left of the main tab where the contents of the file are displayed. The user is able to show/hide specific columns from the display if they only require certain data to be displayed. 2 buttons have been added for easier usability: 'Show All Columns' and 'Hide All Columns'.

### The Search Bar Panel
The search bar panel is located to the bottom of the General Dashboard Manager. The user is able to search the table by typing their search query within the 'Search Bar' text field. The table will display rows that contain the search query and the number of matches will be displayed to the right of the 'Search Bar'. The user is also given the option to search a specific column by selecting the column name within the combo box, located to the left of the 'Search Bar'. The combo box only displays the columns that are shown within the table. So if you hide a column, the user will not be able to search within that column.

### The Frequency Data Tabs
The Main Tab is the first tab within a JTabbedPane. The rest of the tabs are named after each column and display the frequency data graphs for each one. In a frequency data tab, the user is shown a frequency bar chart displaying the frequency table data in descending order. They are also shown a frequency table on the bottom which corresponds with the frequency bar graph. The user is able to see all the unique strings, the amount of times it appears within the column as well as a ranking of how frequent it is.

In the case where a large file is loaded in, the user will be able to scroll through the bar graph and table to view the rest of the data.


