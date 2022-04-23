package Visualization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

public class DrawingCanvas extends JPanel implements MouseListener {
    private int width;
    private int height;
    private int x;
    private int y;
    public List<WordBoundingBox> wordBoundingBoxes;
    private Point topLeft;
    private Point bottomRight;
    List<String> cloudWords;
    Map<String, Integer> wordFrequency;
    WordCloudLogic.State state;
    String wordClicked;
    Map<String, Point> wordCoord;
    Map<String, Rectangle2D> wordRect;

    Map<String, Map<String, Integer>> m;
    boolean wordCloudPosFound;
    Map<String, Color> wordColor;

    public DrawingCanvas(int w, int h, Map<String, Map<String, Integer>> map, WordCloudLogic.State state, String wordClicked) {
        width = w;
        height = h;
        // first word in center of frame
        x = w/2;
        y = h/2;
        wordBoundingBoxes = new ArrayList<>();
        wordCoord = new HashMap<>();
        wordRect = new HashMap<>();
        addMouseListener(this);
        setToolTipText("");
        topLeft = new Point(x, y);
        bottomRight = new Point(x, y);
        cloudWords = new ArrayList<>();
        wordFrequency = new HashMap<>();
        this.state = state;
        this.wordClicked = wordClicked;
        wordCloudPosFound = false;
        wordColor = new HashMap<>();

        // This is what the map retrieved by instrumentation should look like:
        // {Maths={fibbonacci=177}, Circle={area=5}, Hailstorm={collatz=54}, Main={main=1}}
        m = new HashMap<>(map);
        
        if (state == WordCloudLogic.State.DisplayClasses) {
            for(var entry: map.entrySet()) {
                cloudWords.add(entry.getKey());
                int classFrequency = 0;
                for(var e : entry.getValue().entrySet()) {
                    classFrequency += e.getValue();
                }
                wordFrequency.put(entry.getKey(), classFrequency);
            }
        } else {
            for(var entry: map.entrySet()) {
                if (Objects.equals(this.wordClicked, entry.getKey())) {
                    for (var e : entry.getValue().entrySet()) {
                        cloudWords.add(e.getKey());
                        wordFrequency.put(e.getKey(), e.getValue());
                    }
                }
            }
        }
        for (String word : cloudWords) {
            wordColor.put(word, getRandomColor());
        }
    }

    public Color getRandomColor() {
        // get random numbers between 10-245 for R, G, and B values
        int random1 = 10 + (int) (Math.random() * (245-10)+1);
        int random2 = 10 + (int) (Math.random() * (245-10)+1);
        int random3 = 10 + (int) (Math.random() * (245-10)+1);
        return new Color(random1, random2, random3);
    }

    // no need to call explicitly, only need to add an instance of this class to the JFrame
    // This is how java makes it work
    protected void paintComponent(Graphics g) {
        if (!wordCloudPosFound) {
            generateWordPos(g);
            wordCloudPosFound = true;
        }
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        for(String word : cloudWords) {
            g2d.setFont(new Font("Purisa", Font.PLAIN, scaleFrequency(wordFrequency.get(word))));
            g2d.setColor(wordColor.get(word));
            Point coords = wordCoord.get(word);
            g2d.drawString(word, coords.x, coords.y);
        }
        g2d.dispose();
    }


    public void generateWordPos(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        FontRenderContext frc = g2d.getFontRenderContext();

        boolean updateX = true;
        int updateValue;
        for (String word : cloudWords) {
            g2d.setFont(new Font("Purisa", Font.PLAIN, scaleFrequency(wordFrequency.get(word))));
            updateValue = 1;
            x = width/2;
            y = height/2;
            Rectangle2D textBounds = g2d.getFont().getStringBounds(word, frc);
            WordBoundingBox b = new WordBoundingBox(
                    textBounds.getX() + x,
                    textBounds.getY() + y,
                    textBounds.getWidth(),
                    textBounds.getHeight(),
                    word);
            if (wordBoundingBoxes.isEmpty()) {
                wordBoundingBoxes.add(b);
                updateWordCoords(word, textBounds, g2d);
            } else {
                if (updateWordCoordsUsingUsedArea(word, textBounds, g2d)) { // check total used area for space to insert the word
                    // if drawing is possible, checkAreaAndDraw will do it, so do nothing here for now
                } else { // otherwise, increase the spiral
                    while (collidesWithAnyPreviousWord(b)) {
                        // update x and y coordinates, then search for collisions again
                        if (updateX) {
                            x += updateValue;
                            updateX = false;
                        } else {
                            y += updateValue;
                            if (updateValue > 0) {
                                updateValue++;
                            } else {
                                updateValue--;
                            }
                            updateValue *= -1;
                            updateX = true;
                        }
                        // update b
                        b.updatePosition(textBounds.getX() + x, textBounds.getY() + y);
                    }
                    wordBoundingBoxes.add(b);
                    updateWordCoords(word, textBounds, g2d);
                }
            }
        }
    }

    private int scaleFrequency(Integer i) {
        Double d = Double.valueOf(i);

        int logVal = (int) Math.log(d);
        if (logVal == 0) {
            logVal = 1;
        }
        return (int) logVal * 10;
    }

    public void updateWordCoords(String word, Rectangle2D textBounds, Graphics2D g2d) {
        wordCoord.put(word, new Point(x,y));
        updateTotalArea(textBounds);
        wordRect.put(word, textBounds);
    }

    public void updateTotalArea(Rectangle2D textBounds) {
        // update topLeft and bottomRight
        if (textBounds.getX() + x < topLeft.x) {
            topLeft.x = (int) textBounds.getX() + x;
        }
        if (textBounds.getY() + y < topLeft.y) {
            topLeft.y = (int) textBounds.getY() + y;
        }
        if (textBounds.getX() + x + textBounds.getWidth() > bottomRight.x) {
            bottomRight.x = (int) (textBounds.getX() + x + textBounds.getWidth());
        }
        if (textBounds.getY() + y + textBounds.getHeight() > bottomRight.y) {
            bottomRight.y = (int) (textBounds.getY() + y + textBounds.getHeight());
        }
    }

    public boolean collidesWithAnyPreviousWord(WordBoundingBox box) {
        for (WordBoundingBox wordBoundingBox : wordBoundingBoxes) {
            if (box.collides(wordBoundingBox)) {
                return true;
            }
        }
        return false;
    }

    public boolean updateWordCoordsUsingUsedArea(String word, Rectangle2D textBounds, Graphics2D g2d) {
        for (int xCoord = topLeft.x; xCoord < bottomRight.x; xCoord++) {
            for (int yCoord = topLeft.y; yCoord < bottomRight.y; yCoord++) {
                WordBoundingBox b = new WordBoundingBox(xCoord + textBounds.getX(), yCoord + textBounds.getY(), textBounds.getWidth(), textBounds.getHeight(), word);
                if (!collidesWithAnyPreviousWord(b)) {
                    wordBoundingBoxes.add(b);
                    wordCoord.put(word, new Point(xCoord, yCoord));
                    wordRect.put(word, textBounds);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getToolTipText(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (WordBoundingBox word : wordBoundingBoxes) {
            Point topLeft = word.topLeft;
            Point bottomRight = word.bottomRight;
            if (x >= topLeft.x && x <= bottomRight.x && y >= topLeft.y && y <= bottomRight.y) {
                int NumCalls = this.wordFrequency.get(word.word);
                return "# calls for " + word.word + ": " + NumCalls;
            }
        }
        return null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.state == WordCloudLogic.State.DisplayClasses) {
            int x = e.getX();
            int y = e.getY();

            for (WordBoundingBox word : wordBoundingBoxes) {
                Point topLeft = word.topLeft;
                Point bottomRight = word.bottomRight;
                if (x >= topLeft.x && x <= bottomRight.x && y >= topLeft.y && y <= bottomRight.y) {
                    System.out.printf("Clicked at word: %s\n", word.word);
                    // Create and open new frame
                    DrawGUI.createAndShowGUI(this.m, WordCloudLogic.State.DisplayClassMethods, word.word);
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }
}
