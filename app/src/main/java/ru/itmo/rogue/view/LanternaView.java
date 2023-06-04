package ru.itmo.rogue.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.AbstractTextGraphics;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.VirtualScreen;
import ru.itmo.rogue.model.state.Delta;

import java.io.IOException;

public class LanternaView implements View {

    private final static int MIN_SCREEN_COLUMN = 80;
    private final static int MAX_SCREEN_ROW = 24;

    private VirtualScreen screen;
    private Delta delta;
    public LanternaView(VirtualScreen screen) {
        this.screen = screen;
//        screen.setMinimumSize(new TerminalSize(100, 50));
        screen.doResizeIfNecessary();
        try {
            this.screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean update(Delta delta) {
        this.delta = delta;
        // TODO: move to the creation of screen (should not be executed at every update)???
        // TODO: запоминать предыдущее расширение. Если отличается --- полный апдейт, если нет --- частичный
        drawWindowBorders();
        drawPlains();
        System.out.println("Display updated!");
        return true;
    }

    private void drawWindowBorders() {
        screen.doResizeIfNecessary(); // Actualize size
        // Border for window
        TerminalSize termSize = screen.getTerminalSize();
        TerminalSize borderSize = new TerminalSize(
                termSize.getColumns() - 2,
                termSize.getRows() - 2);
        drawSquare(new TerminalPosition(1, 1), borderSize, doubled);

        // Examples:
        var size = new TerminalSize(5, 5);
        var pos1 = new TerminalPosition(2, 2);
        var pos2 = new TerminalPosition(8, 2);
        var pos3 = new TerminalPosition(2, 8);
        var pos4 = new TerminalPosition(8, 8);

        drawSquare(pos1, size, simple);
        drawSquare(pos2, size, bold);
        drawSquare(pos3, size, dash);
        drawSquare(pos4, size, doubled);

        try {
            screen.refresh(Screen.RefreshType.COMPLETE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawPlains() {

    }

    private void checkBorders() {
            // TODO: draw warning
        TextGraphics graphics = screen.newTextGraphics();
        graphics.drawLine(new TerminalPosition(2, 2), new TerminalPosition(5,5), '*');
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private enum SquareType {
        NORMAL, BOLD
    }

    record SquareChars(char horizontal, char vertical, char[] corners) {
        public SquareChars(int horizontal, int vertical, int[] corners){
            this(getUnicode(horizontal), getUnicode(vertical), getUnicode(corners));
        }
        /*  1 h h h 2
         *  v       v
         *  v       v
         *  v       v
         *  3 h h h 4
         */
    }

    static SquareChars simple = new SquareChars(
            0x2500,
            0x2502,
            new int[]{0x250C, 0x2510, 0x2514, 0x2518});

    static SquareChars dash = new SquareChars(
            0x2505,
            0x2506,
            new int[]{0x250C, 0x2510, 0x2514, 0x2518});

    static SquareChars bold = new SquareChars(
            0x2501,
            0x2503,
            new int[]{0x250F, 0x2513, 0x2517, 0x251B});

    static SquareChars doubled = new SquareChars(
            0x2550,
            0x2551,
            new int[]{0x2554, 0x2557, 0x255A, 0x255D});


    private void drawSquare(TerminalPosition position, TerminalSize size, SquareChars chars) {
        var graphics = screen.newTextGraphics();
        var x = position.getColumn();
        var y = position.getRow();
        var dx = size.getColumns();
        var dy = size.getRows();

        // horizontal lines
        graphics.drawLine(x + 1, y, x + dx - 1, y, chars.horizontal);
        graphics.drawLine(x + 1, y + dy, x + dx - 1, y + dy, chars.horizontal);
        // vertical lines
        graphics.drawLine(x, y + 1, x, y + dy - 1, chars.vertical);
        graphics.drawLine(x + dx, y + 1, x + dx, y + dy - 1, chars.vertical);
        // corners
        graphics.setCharacter(x, y, chars.corners[0]);
        graphics.setCharacter(x + dx, y, chars.corners[1]);
        graphics.setCharacter(x, y + dy, chars.corners[2]);
        graphics.setCharacter(x + dx, y + dy, chars.corners[3]);
    }

    private static char getUnicode(int unicode) {
        return Character.toChars(unicode)[0];
    }

    private static char[] getUnicode(int[] unicode) {
        var chars = new char[unicode.length];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = getUnicode(unicode[i]);
        }
        return chars;
    }
}
