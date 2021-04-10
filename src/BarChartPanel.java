import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Map;

public class BarChartPanel extends JPanel {

    // Space to draw Chart Information
    public static final int TOP_BUFFER = 30;
    public static final int AXIS_OFFSET = 20;

    private final Map<String, Integer> data;

    private int chartWidth, chartHeight, chartX, chartY;

    private final String xLabel;

    public BarChartPanel(Map<String, Integer> frequencyTableData, String xl){
        super();
        this.data = frequencyTableData;

        xLabel = xl;

    }

    public void paintComponent(Graphics g) {
        computeSize();

        Graphics2D g2 = (Graphics2D) g;
        drawBars(g2);
        drawAxes(g2);
        drawText(g2);
    }

    private void computeSize() {

        int width = this.getWidth();
        int height = this.getHeight();

        // chart area size
        chartWidth = width - 2*AXIS_OFFSET;
        chartHeight = height - 2*AXIS_OFFSET - TOP_BUFFER;

        // Chart origin coords
        chartX = AXIS_OFFSET;
        chartY = height - AXIS_OFFSET;

    }

    public void drawBars(Graphics2D g2) {

        Color original = g2.getColor();

        double numBars = data.keySet().size();
        double max = 0.;

        for (Integer wrapper : data.values()) {
            if (max < wrapper)
                max = wrapper;
        }
        int barWidth = (int) (chartWidth /numBars);

        int value, height, xLeft, yTopLeft;
        int counter = 0;

        for (String bar : data.keySet()) {
            value = data.get(bar);

            double height2 = (value/max)* chartHeight;
            height = (int) height2;

            xLeft = AXIS_OFFSET + counter * barWidth;
            yTopLeft = chartY - height;
            Rectangle rec = new Rectangle(xLeft, yTopLeft, barWidth, height);

            g2.draw(rec);

            counter++;
        }

        g2.setColor(original);
    }

    private void drawAxes(Graphics2D g2) {
        int rightX = chartX + chartWidth;
        int topY = chartY - chartHeight;

        g2.drawLine(chartX, chartY, rightX, chartY);

        g2.drawLine(chartX, chartY, chartX, topY);

        g2.drawString(xLabel, chartX + chartWidth /2, chartY + AXIS_OFFSET/2 +3) ;

        // draw vertical string

        Font original = g2.getFont();

        Font font = new Font(null, original.getStyle(), original.getSize());
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(-90), 0, 0);
        Font rotatedFont = font.deriveFont(affineTransform);
        g2.setFont(rotatedFont);
        String yLabel = "Frequency Count";
        g2.drawString(yLabel,AXIS_OFFSET/2+3, chartY - chartHeight/4);
        g2.setFont(original);


    }

    private void drawText(Graphics2D g2) {
        int size = data.keySet().size();

        g2.drawString("Number of Bars: " + size, AXIS_OFFSET +10, 15);
    }


}