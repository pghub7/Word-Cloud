package logger;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private static Logger instance = null;
    // maps class names to another map, which maps functions to their frequency
    private Map<String, Map<String, Integer>> class_map = new HashMap<>();

    public static void initializeLogger() {
        instance = new Logger();
    }

    public static Logger getInstance() { return instance; }

    public Map<String, Map<String, Integer>> getClass_map() {
        return this.class_map;
    }

    public void logMethodCall() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        StackTraceElement originalCaller = stackTrace[1];
        String methodName = originalCaller.getMethodName();
        String className = originalCaller.getClassName();
        Map<String, Integer> method_map = class_map.getOrDefault(className, new HashMap<>());
        method_map.put(methodName, method_map.getOrDefault(methodName, 0) + 1);
        class_map.put(className, method_map);
    }

    public void dumpLogs(String outputFileName) {
        try {
            File file = new File(outputFileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(class_map);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
