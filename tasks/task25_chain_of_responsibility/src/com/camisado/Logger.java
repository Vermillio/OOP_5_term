package com.camisado;

import java.io.IOException;

public abstract class Logger {

    protected int level;

    protected Logger nextLogger;

    public void attachLogger(Logger nextLogger){
        this.nextLogger = nextLogger;
    }

    public void logMessage(int level, String message) throws IOException {
        if(this.level <= level){
            write(message);
        }
        if(nextLogger !=null){
            nextLogger.logMessage(level, message);
        }
    }

    abstract protected void write(String message) throws IOException;

}