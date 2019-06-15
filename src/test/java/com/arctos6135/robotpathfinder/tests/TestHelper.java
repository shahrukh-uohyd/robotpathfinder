package com.arctos6135.robotpathfinder.tests;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public final class TestHelper {

    // Maps classes to instances of TestHelper
    // Each class can only have one instance
    // Use ConcurrentHashMaps since tests might be multithreaded
    private static Map<Class<?>, TestHelper> instances = new ConcurrentHashMap<>();

    private TestHelper() {}

    public static TestHelper getInstance(Class<?> testClass) {
        // If instance exists, return it
        if(instances.containsKey(testClass)) {
            return instances.get(testClass);
        }
        // Otherwise create the instance, add it to the map and return it
        else {
            TestHelper instance = new TestHelper();
            instances.put(testClass, instance);
            return instance;
        }
    }

    // Maps method/test names to logs
    // Each method/test has its own log
    private Map<String, StringBuffer> methodLogs = new ConcurrentHashMap<>();

    private StringBuffer getCallerLogs() {
        // Take the 4th element of the stack trace
        // 0 - getStackTrace()
        // 1 - getCallerLogs()
        // 2 - method calling getCallerLogs()
        // 3 - method calling the calling method
        String caller = Thread.currentThread().getStackTrace()[3].getMethodName();
        if(methodLogs.containsKey(caller)) {
            return methodLogs.get(caller);
        }
        else {
            StringBuffer log = new StringBuffer(caller + ":\n");
            methodLogs.put(caller, log);
            return log;
        }
    }
    
    private Random rand = new Random();

    public double getDouble(String name, double max) {
        return getDouble(name, 0, max);
    }
    public double getDouble(String name, double min, double max) {
        // Generate the double value
        double val = rand.nextDouble() * (max - min) + min;
        // Log it
        StringBuffer log = getCallerLogs();
        log.append("[VALUE.DOUBLE] " + name + ": " + val + "\n");
        return val;
    }

    public static final String LOG_LOCATION = "build" + File.separator + "testLogs" + File.separator;

    public static void flushAll() throws IOException {
        for(var entry : instances.entrySet()) {
            // Get the log file name and path from the class name
            // Names are in the form package/of/class/Class.log
            String logFileName = entry.getKey().getName().replace(".", File.separator) + ".log";
            // Get the full path
            File logFile = new File(LOG_LOCATION + logFileName);
            // Create file and all dirs
            File parent = logFile.getParentFile();
            if(!parent.exists() && !parent.mkdirs()) {
                throw new IllegalStateException("Could not create directories: " + parent);
            }
            // Write logs
            FileWriter writer = new FileWriter(logFile, false);
            for(StringBuffer methodLog : entry.getValue().methodLogs.values()) {
                writer.append(methodLog.toString());
                // Separate using a blank line
                writer.append("\n");
            }
            writer.close();
        }
    }
}
