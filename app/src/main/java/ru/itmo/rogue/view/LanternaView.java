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
        TerminalSize termSize = screen.getTerminalSize();
//        while (termSize.getColumns() < LanternaView.MIN_SCREEN_COLUMN || termSize.getRows() < LanternaView.MAX_SCREEN_ROW) {
//            checkBorders();
//            screen.doResizeIfNecessary();
//            termSize = screen.getTerminalSize();
//        }


        for (int column = 0; column < termSize.getColumns(); column++) {
            screen.setCharacter(column, 0, new TextCharacter('-', TextColor.ANSI.RED,  TextColor.ANSI.DEFAULT));
            screen.setCharacter(column, termSize.getRows() - 1, new TextCharacter('-', TextColor.ANSI.RED,  TextColor.ANSI.DEFAULT));
        }

        for (int row = 0; row < termSize.getRows(); row++) {
            screen.setCharacter(0, row, new TextCharacter('|', TextColor.ANSI.RED,  TextColor.ANSI.DEFAULT));
            screen.setCharacter(termSize.getColumns() - 1, row, new TextCharacter('|', TextColor.ANSI.RED, TextColor.ANSI.DEFAULT));
        }
        TextGraphics graphics = screen.newTextGraphics();
        graphics.drawRectangle(new TerminalPosition(2, 2), new TerminalSize(5,5), Character.toChars(0x2591)[0]);
        // 2588 --- полностью (в фокусе)
        // 2591 --- рябящий

        drawSquare();
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

    static char getUnicode(int unicode) {
        return Character.toChars(unicode)[0];
    }

    private void drawSquare(TerminalPosition position, TerminalSize size, SquareType type) {
        // TODO
    }
}
