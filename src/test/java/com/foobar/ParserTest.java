package com.foobar;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * Created by singhr16 on 5/28/2015.
 */
public class ParserTest {

    private Parser parser;

    @Test
    public void ParseAddASM() throws FileNotFoundException {
        parser = new Parser("C:\\pers\\nand2tetris\\nand2tetris\\projects\\06\\add\\Add.asm");
        assertTrue(parser.hasMoreCommands());
        parser.advance();
        assertTrue(parser.hasMoreCommands());

        //  A command
        assertEquals(CommandType.A_COMMAND, parser.commandType());
        assertEquals("2", parser.symbol());
        assertNull(parser.dest());
        assertNull(parser.comp());
        assertNull(parser.jump());

        assertTrue(parser.hasMoreCommands());
        parser.advance();

        // C Command with unconditional jump
        assertEquals(CommandType.C_COMMAND, parser.commandType());
        assertNull(parser.symbol());
        assertEquals("D", parser.dest());
        assertEquals("A", parser.comp());
        assertNull(parser.jump());

        assertTrue(parser.hasMoreCommands());
        parser.advance();

        //  Another A command
        assertEquals(CommandType.A_COMMAND, parser.commandType());
        assertEquals("3", parser.symbol());
        assertNull(parser.dest());
        assertNull(parser.comp());
        assertNull(parser.jump());

        assertTrue(parser.hasMoreCommands());
        parser.advance();

        // C Command with unconditional jump
        assertEquals(CommandType.C_COMMAND, parser.commandType());
        assertNull(parser.symbol());
        assertEquals("D", parser.dest());
        assertEquals("D+A", parser.comp());
        assertNull(parser.jump());

        assertTrue(parser.hasMoreCommands());
        parser.advance();

        //  Another A command
        assertEquals(CommandType.A_COMMAND, parser.commandType());
        assertEquals("0", parser.symbol());
        assertNull(parser.dest());
        assertNull(parser.comp());
        assertNull(parser.jump());

        assertTrue(parser.hasMoreCommands());
        parser.advance();

        // C Command with unconditional jump
        assertEquals(CommandType.C_COMMAND, parser.commandType());
        assertNull(parser.symbol());
        assertEquals("M", parser.dest());
        assertEquals("D", parser.comp());
        assertNull(parser.jump());

        assertFalse(parser.hasMoreCommands());
    }
}
