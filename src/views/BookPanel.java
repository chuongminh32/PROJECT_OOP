package views;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class BookPanel extends JPanel {
    public BookPanel() {
        JLabel label = new JLabel("Books Management");
        add(label);
    }
}
