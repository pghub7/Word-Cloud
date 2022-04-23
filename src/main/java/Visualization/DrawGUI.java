package Visualization;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class DrawGUI {

    public static void createAndShowGUI(Map<String, Map<String, Integer>> map, WordCloudLogic.State state, String wordClicked) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                int frameWidth = (int) ((int) screenSize.getWidth() * 0.75);
                int frameHeight = (int) ((int) screenSize.getHeight() * 0.75);
                JFrame frame = new JFrame();
                DrawingCanvas dc = new DrawingCanvas(frameWidth, frameHeight, map, state, wordClicked);
                // assuming resolution is an evenly divisible number
                frame.setSize(frameWidth, frameHeight);
                frame.setTitle("Word Cloud");
                if (state == WordCloudLogic.State.DisplayClasses) {
                    // MainFrame implies the window that displays all the classes
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    // opens window that displays the methods of a class.
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }
                frame.add(dc);
                frame.setVisible(true);
            }
        });
    }
}
