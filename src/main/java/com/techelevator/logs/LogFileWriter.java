package com.techelevator.logs;

import com.techelevator.ApplicationCLI;

import java.io.*;
import java.util.List;


/**
 * This would be a GREAT place to have a public method that could take a formatted String and log it out to a file.
 */
public class LogFileWriter {

    private File logFile;
    private PrintWriter logWriter;

    public void logFileWriter(String filepath) {
        logFile = new File(filepath);
        //if logFile doesn't exist, open it to write
        if(!logFile.exists()){
            try{
                logWriter = new PrintWriter(logFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            try{
                logWriter = new PrintWriter(new FileOutputStream(logFile, true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void logWriter(List<String> logList) {
        for (String log : logList) {
            logWriter.println(log);
        }
        logWriter.close();
    }

    public void writeReport(String path, List<String> list) {
        File reportFile = new File(path);
        try (PrintWriter writer = new PrintWriter(reportFile)){
            for (String str : list) {
                writer.print(str);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



}
