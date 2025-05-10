import java.awt.*;
public class AWTExample {
    public static void main (String[] args) {
        Frame frame = new Frame("OOP class - Lab04");
        frame.setSize(500, 300);
        Label label = new Label("AWT Example");
        frame.add(label);
        frame.setVisible(true);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }
}