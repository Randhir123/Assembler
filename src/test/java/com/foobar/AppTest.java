package com.foobar;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    public void test() throws FileNotFoundException {
        App app = new App("C:\\pers\\nand2tetris\\nand2tetris\\projects\\06\\pong\\PongL.asm");
        Scanner out = new Scanner(new File("C:\\pers\\nand2tetris\\nand2tetris\\projects\\06\\pong\\PongL.hack"));

        while (out.hasNextLine()) {
            assertEquals(out.nextLine(), app.getHack());
        }
    }
}
