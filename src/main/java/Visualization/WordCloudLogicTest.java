package Visualization;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordCloudLogicTest {
    @Test
    void testCalculateFrequencyAndOrdering() {
        // Create a HashMap and add some values
        ArrayList<String> words = new ArrayList<>();
        words.add("This");
        words.add("is");
        words.add("is");
        words.add("a");
        words.add("a");
        words.add("a");
        words.add("test");
        words.add("test");
        words.add("test");
        words.add("test");
        WordCloudLogic wcl = new WordCloudLogic();
        HashMap<String, Integer> wordsAndCounts = wcl.calculateFrequency(words);
        assertEquals(wordsAndCounts.get("This"), 1);
        assertEquals(wordsAndCounts.get("is"), 2);
        assertEquals(wordsAndCounts.get("a"), 3);
        assertEquals(wordsAndCounts.get("test"), 4);

        List<String> orderedWords = wcl.orderWordsDescending(wordsAndCounts);
        assertEquals(orderedWords.get(0), "test");
        assertEquals(orderedWords.get(1), "a");
        assertEquals(orderedWords.get(2), "is");
        assertEquals(orderedWords.get(3), "This");
    }

    @Test
    void testCollisionTrue() {
        WordBoundingBox box1 = new WordBoundingBox(0, 0, 10, 10, "word1");
        WordBoundingBox box2 = new WordBoundingBox(5, 5, 10, 10, "word2");
        assertTrue(box1.collides(box2));
    }

    @Test
    void testCollisionTrueBackwards() {
        WordBoundingBox box1 = new WordBoundingBox(5, 5, 10, 10, "word1");
        WordBoundingBox box2 = new WordBoundingBox(0, 0, 10, 10, "word2");
        assertTrue(box1.collides(box2));
    }

    @Test
    void testCollisionFalse() {
        WordBoundingBox box1 = new WordBoundingBox(0, 0, 10, 10, "word1");
        WordBoundingBox box2 = new WordBoundingBox(15, 15, 10, 10, "word2");
        assertFalse(box1.collides(box2));
    }

    @Test
    void testCollisionSameBox() {
        WordBoundingBox box1 = new WordBoundingBox(0, 0, 10, 10, "word1");
        WordBoundingBox box2 = new WordBoundingBox(0, 0, 10, 10, "word2");
        assertTrue(box1.collides(box2));
    }
}