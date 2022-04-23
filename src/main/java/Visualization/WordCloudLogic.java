package Visualization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import logger.Logger;

import static java.util.stream.Collectors.toMap;

public class WordCloudLogic {
    enum State {
        DisplayClasses,
        DisplayClassMethods
    }
    public State currentState;

    public WordCloudLogic() {
    this.currentState = State.DisplayClasses;
    }

    public static void startWordCloud(String logFilePath) {
        try {
            System.out.println("DEBUG: CMD Argument for wordcloud: " + logFilePath);
            FileInputStream fis = new FileInputStream(logFilePath);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Map<String, Map<String, Integer>> instrumentedMap = (Map) ois.readObject();
            ois.close();
            startWordCloud(instrumentedMap);
        } catch(ClassNotFoundException | IOException e) {
            System.err.println(e.toString());
        }
    }

    public static void startWordCloud() {
        startWordCloud(Logger.getInstance().getClass_map());
    }

    public static void startWordCloud(Map<String, Map<String, Integer>> instrumentedMap) {
        DrawGUI.createAndShowGUI(instrumentedMap, State.DisplayClasses, "");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length != 1 ) {
            System.err.println("Log argument missing.");
            System.exit(1);
        }
        startWordCloud(args[0]);
    }

    // source: https://stackoverflow.com/a/1478314
    public HashMap<String, Integer> calculateFrequency(List<String> words) {
        HashMap<String, Integer> wordsAndCounts = new HashMap<>();
        for (String word : words) {
            if (!wordsAndCounts.containsKey(word)) {
                wordsAndCounts.put(word, 1);
            } else {
                wordsAndCounts.computeIfPresent(word, (key, val) -> val + 1);
            }
        }

        return wordsAndCounts;
    }

    public List<String> orderWordsDescending(HashMap<String, Integer> wordsAndCounts) {
        HashMap<String, Integer> orderedWords = wordsAndCounts
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        return new ArrayList<>(orderedWords.keySet());
    }
}
