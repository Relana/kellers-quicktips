package main.java;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * QuicktipLogger is a class that can be used to write to a logfile.
 */
public class QuicktipLogger {
    public static Logger logger = Logger.getLogger(GeneratorController.class.getName());

    /**
     * initialize initializes the logger. This method is only called by QuicktipGenerator's main method
     * therefore all of the tests write log messages to the console.
     */
    public static void initialize() {
        // to stop log messages being passed to console output:
        LogManager.getLogManager().reset();

        logger.setLevel(Level.INFO);
        try {
            FileHandler fileHandler = new FileHandler(System.getProperty("user.dir") +
                    System.getProperty("file.separator") + "logfile.log", true);
            logger.addHandler(fileHandler);
            info("Logger was initialized.");
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.WARNING, "An error occurred while initializing logger.", e);
        }
    }

    /**
     * info logs info messages
     * @param message - a String containing the message to be logged
     */
    public static void  info(String message) {
        logger.info(message);
    }

    /**
     * warn logs warning messages
     * @param message - a String containing the log message
     * @param exception - an exception that was caught
     */
    public static void  warn(String message, Exception exception) {
        logger.log(Level.WARNING, message, exception);
    }
}
