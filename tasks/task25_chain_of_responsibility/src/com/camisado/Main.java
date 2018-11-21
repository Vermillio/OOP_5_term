package com.camisado;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ConsoleLogger consoleLogger = new ConsoleLogger(1);
        FileLogger fileLogger = new FileLogger(2, "file.log");
        consoleLogger.attachLogger(fileLogger);

        for (int i = 0; i < 10; ++i) {
            consoleLogger.write("This message is hangled only by Console logger");
            consoleLogger.logMessage(2, "This message is handled by both Console logger and File logger");
        }
    }
}
