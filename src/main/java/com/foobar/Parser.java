package com.foobar;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

/**
 * Created by singhr16 on 5/28/2015.
 */
public class Parser {
    private Scanner input;
    private String currentCommand;

    public Parser(String fileName) throws  FileNotFoundException {
        input = new Scanner(new File(fileName));
        currentCommand = null;
    }

    public boolean hasMoreCommands() {
        return input.hasNextLine();
    }

    public void advance() {
        String line = input.nextLine().trim();
        while (line.isEmpty() || line.startsWith("//")) {
            line = input.nextLine().trim();
        }
        if (line.contains("//")) {
            currentCommand = line.substring(0, line.indexOf("//")).trim();
        } else {
            currentCommand = line;
        }
    }

    public CommandType commandType() {
        if (currentCommand.startsWith("@")) {
            return CommandType.A_COMMAND;
        } else if (currentCommand.startsWith("(") && currentCommand.endsWith(")")) {
            return CommandType.L_COMMAND;
        } else {
            return CommandType.C_COMMAND;
        }
    }

    public String symbol() {
        if (commandType() == CommandType.A_COMMAND) {
            return currentCommand.substring(currentCommand.indexOf("@") + 1);
        } else if (commandType() == CommandType.L_COMMAND){
            return currentCommand.substring(currentCommand.indexOf("(") + 1, currentCommand.lastIndexOf(")"));
        } else
            return null;
    }

    public String dest() {
        if (commandType() == CommandType.C_COMMAND) {
            String[] tokens = currentCommand.split("=");
            if (tokens.length > 1)
                return tokens[0];
            else
                return null;
        } else
            return null;
    }

    public String comp() {
        if (commandType() == CommandType.C_COMMAND) {
            String[] tokens = currentCommand.split(";");
            String[] dc = tokens[0].split("=");
            return dc.length > 1 ? dc[1] : dc[0];
        } else
            return null;
    }

    public String jump() {
        if (commandType() == CommandType.C_COMMAND) {
            String[] tokens = currentCommand.split(";");
            return tokens.length > 1 ? tokens[1] : null;
        } else
            return null;
    }
}
