package ru.itmo.rogue.view;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.VirtualScreen;
import ru.itmo.rogue.model.state.Delta;
import ru.itmo.rogue.model.state.State;
import java.io.IOException;

public class LanternaView implements View {
    private final static double PLAYGROUND_COEF = 0.7;
    private final static double INVENTORY_COEF = 0.3;
    private final static double STATS_COEF = 0.15;

    private final VirtualScreen screen;
    private Delta lastDelta;
    private TerminalSize lastTerminalSize;
    private State.Focus focus = null;

    public LanternaView(VirtualScreen screen) {
        this.screen = screen;
        this.screen.setMinimumSize(new TerminalSize(130, 40));
        try {
            this.screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean update(Delta delta) {
        this.lastDelta = delta;
        screen.doResizeIfNecessary(); // Actualize size data
        var curSize = screen.getTerminalSize();
        var minSize = screen.getMinimumSize();
        var terminalSize = curSize.getColumns() > minSize.getColumns() && curSize.getRows() > minSize.getRows() ? curSize : minSize;
        var updateType = terminalSize.equals(lastTerminalSize) ? Screen.RefreshType.DELTA : Screen.RefreshType.COMPLETE;
        lastTerminalSize = terminalSize;

        screen.clear();

        if (!delta.getFocus().equals(this.focus)) {
            // for the resizable fields --- pass terminalSize as argument
            drawPlains(screen.getMinimumSize());
            this.focus = delta.getFocus();
        }

        // Refresh after everything
        try {
            screen.refresh(updateType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Display updated!");
        return true;
    }

    private void drawPlains(TerminalSize terminalSize) {
        // draw playground
        var playBorders = lastDelta.getFocus().equals(State.Focus.LEVEL) ? doubled : simple;
        TerminalSize playground = new TerminalSize((int)(terminalSize.getColumns() * LanternaView.PLAYGROUND_COEF),
                terminalSize.getRows() - 5);
        drawSquare(new TerminalPosition(2, 2), playground, playBorders);

        // draw inventory
        var invBorders = lastDelta.getFocus().equals(State.Focus.LEVEL) ? simple : doubled;
        TerminalSize inventory = new TerminalSize((int)(terminalSize.getColumns() * LanternaView.INVENTORY_COEF) - 6,
                terminalSize.getRows() - (int)(terminalSize.getRows() * STATS_COEF) - 6);
        drawSquare(new TerminalPosition(playground.getColumns() + 3, 2), inventory, invBorders);

        // draw statistics
        TerminalSize stats = new TerminalSize((int)(terminalSize.getColumns() * LanternaView.INVENTORY_COEF) - 6,
                (int)(terminalSize.getRows() * STATS_COEF));
        drawSquare(new TerminalPosition(playground.getColumns() + 3, playground.getRows() - (int)(playground.getRows() * STATS_COEF) + 1), stats, simple);
    }

    record SquareChars(char horizontal, char vertical, char[] corners) {
        public SquareChars(int horizontal, int vertical, int[] corners){
            this(getUnicode(horizontal), getUnicode(vertical), getUnicode(corners));
        }
        /*
         *  1 h h h 2
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
            new int[]{0x250D, 0x2511, 0x2515, 0x2519});

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
