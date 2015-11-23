package com.foobar;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

/**
 * Assembler
 */
public class App 
{
    private Parser parser;
    private Code code;
    private SymbolTable symbolTable;
    static int addr = 16;

    public App(String asmFile) throws FileNotFoundException {
        parser = new Parser(asmFile);
        code = new Code();
        symbolTable = new SymbolTable();
    }

    public String getHack() {
        if (parser.hasMoreCommands()) {
            parser.advance();
        } else {
            return null;
        }
        if (parser.commandType() == CommandType.A_COMMAND) {
            String bin = null;
            try {
                bin = Integer.toBinaryString(Integer.parseInt(parser.symbol()));
            } catch (NumberFormatException e) {
                if (!symbolTable.contains(parser.symbol())) {
                    symbolTable.addEntry(parser.symbol(), App.addr++);
                    bin = Integer.toBinaryString(symbolTable.getAddress(parser.symbol()));
                } else {
                    bin = Integer.toBinaryString(symbolTable.getAddress(parser.symbol()));
                }
            }
            int n = 15 - bin.length();
            StringBuilder hack = new StringBuilder(bin);
            for (int i = 0; i < n; i++) {
                hack.insert(0, "0");
            }
            hack.insert(0, "0");
            return hack.toString();
        } else if (parser.commandType() == CommandType.C_COMMAND) {
            return "111" + code.comp(parser.comp()) + code.dest(parser.dest()) + code.jump(parser.jump());
        } else if (CommandType.L_COMMAND == parser.commandType()) {
            return "";
        }
        return null;
    }

    public static void main( String[] args ) throws FileNotFoundException, IOException
    {
        String asmFile = null;

        if (args.length > 0) {
            asmFile = args[0];
        } else {
            System.out.println("Please provide asm file name");
        }

        if (StringUtils.isNotEmpty(asmFile)) {
            String outFileName = asmFile.substring(0, asmFile.lastIndexOf(".")) + ".hack";
            BufferedWriter output = new BufferedWriter(new PrintWriter(new File(outFileName)));
            App app = new App(asmFile);

            int instructionAddress = 0;
            app.parser.advance();

            // first pass: find label addresses
            while (app.parser.hasMoreCommands()) {
                app.parser.advance();
                if (CommandType.L_COMMAND == app.parser.commandType()) {
                    app.symbolTable.addEntry(app.parser.symbol(), instructionAddress + 1);
                } else {
                    instructionAddress++;
                }
            }

            // second pass: replace variables
            app.parser = new Parser(asmFile);
            String hack = "";
            while ((hack = app.getHack())!=null) {
                if (!hack.isEmpty()) {
                    output.write(hack);
                    output.newLine();
                }
            }
            output.close();
        }
    }
}