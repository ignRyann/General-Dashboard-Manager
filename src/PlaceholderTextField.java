import javax.swing.*;
import java.awt.*;

// Extension of JTextField - Allows for a placeholder to be set
public class PlaceholderTextField extends JTextField {
    private final String placeholder;

    public PlaceholderTextField(String placeholderText){
        placeholder = placeholderText;
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder == null || placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        g.drawString(placeholder, getInsets().left, pG.getFontMetrics().getMaxAscent() + 2*getInsets().top);
    }

}