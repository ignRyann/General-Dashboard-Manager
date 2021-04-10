import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Map;

public class BarChartPanel extends JPanel {

    // Space to draw Chart Information
    public static final int TOP_BUFFER = 30;
    public static final int AXIS_OFFSET = 20;

    // Hashmap datatype holds the frequency of the String within the columnValues
    private final Map<String, Integer> data;

    // Stores dimension values
    private int chartWidth;
    private int chartHeight;
    private int chartX;
    private int chartY;

    // Stores the x-axis label to show on the graph
    private final String xLabel;

    public BarChartPanel(Map<String, Integer> frequencyTableData, String columnName){
        super();
        setBackground(Color.white);
        data = frequencyTableData;
        xLabel = columnName;

        if (((getWidth() - AXIS_OFFSET) / data.keySet().size()) < 5) {
            setPreferredSize(new Dimension(5*data.keySet().size() + 50, getHeight()));
        }

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        computeSize();
        drawBars(g2);
        drawLabels(g2);
        drawChartInfo(g2);
    }

    // Retrieves the size of panel
    private void computeSize() {
        // Gets the chart's area size
        chartWidth = this.getWidth() - AXIS_OFFSET;
        chartHeight = this.getHeight() - 2*AXIS_OFFSET - TOP_BUFFER;

        // Get's the chart's coords for the origin
        chartX = AXIS_OFFSET;
        chartY = this.getHeight() - AXIS_OFFSET;
    }

    // Draws the Bar Chart depending on the values and its frequency stored within data
    public void drawBars(Graphics2D g2) {
        int numberOfBars = data.keySet().size();
        double maxHeight = 0;

        // Finds the height of the highest bar
        for (Integer freq : data.values()) {
            if (maxHeight < freq)
                maxHeight = freq;
        }

        // Calculates width of bar
        int barWidth = chartWidth / numberOfBars;
        if ((chartWidth / numberOfBars) < 5){
            barWidth = 5;
        }

        int value;
        int barHeight;
        int xLeftCord;
        int yTopLeftCord;
        int counter = 0;
        g2.setColor(Color.black);
        // Draws the bar for every key in the hashmap
        for (String bar : data.keySet()) {
            value = data.get(bar);

            double barHeightDecimal = (value/maxHeight)* chartHeight;
            barHeight = (int) barHeightDecimal;

            xLeftCord = AXIS_OFFSET + counter * barWidth;
            yTopLeftCord = chartY - barHeight;
            Rectangle rec = new Rectangle(xLeftCord, yTopLeftCord, barWidth, barHeight);

            g2.draw(rec);

            counter++;
        }
    }

    // Draws x-axis and y-axis labels
    private void drawLabels(Graphics2D g2) {
        // Draws the x-axis label
        g2.drawString(xLabel, chartX + chartWidth /2, chartY + AXIS_OFFSET/2 +3) ;

        // Saving the original font settings to revert back to it
        Font original = g2.getFont();

        // Change font settings to write vertically
        Font font = new Font(null, original.getStyle(), original.getSize());
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(-90), 0, 0);
        Font rotatedFont = font.deriveFont(affineTransform);
        g2.setFont(rotatedFont);

        // Draws the y-axis label
        g2.drawString("Frequency",AXIS_OFFSET/2+3, chartY - chartHeight/4);
        // Revert back to original font settings
        g2.setFont(original);
    }

    // Draws chart information
    private void drawChartInfo(Graphics2D g2) {
        int size = data.keySet().size();
        g2.drawString("Number of Unique Values: " + size, AXIS_OFFSET +10, 15);
    }



}