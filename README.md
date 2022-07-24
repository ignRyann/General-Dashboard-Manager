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


