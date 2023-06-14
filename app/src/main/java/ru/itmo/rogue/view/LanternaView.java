package ru.itmo.rogue.view;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.VirtualScreen;
import org.jetbrains.annotations.NotNull;
import ru.itmo.rogue.model.state.InventoryUpdate;
import ru.itmo.rogue.model.unit.Position;
import ru.itmo.rogue.model.unit.Unit;
import ru.itmo.rogue.model.state.*;
import ru.itmo.rogue.model.updates.UnitUpdate;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LanternaView {
    private final static double PLAYGROUND_COEF = 0.7;
    private final static double INVENTORY_COEF = 0.3;
    private final static double STATS_COEF = 0.15;

    private final static int LEFT_MARGIN = 0; // 1
    private final static int TOP_MARGIN = 0; // 1
    private final static int RIGHT_MARGIN = 2; // 3
    private final static int BOTTOM_MARGIN = 1; // 2

    private final VirtualScreen screen;
    private final TerminalSize referenceSize;
    private TerminalSize lastTerminalSize;
    private MapView background;

    /**
     * @param screen virtual screen which will be used for the visuals
     */
    public LanternaView(VirtualScreen screen) {
        this.screen = screen;
        this.referenceSize = new TerminalSize(130, 40);
        this.screen.setMinimumSize(referenceSize);
        this.screen.setCursorPosition(null);
        try {
            this.screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param delta latest changes from units, map and inventory
     * @return the positive or negative result of the update process
     */
    // TODO: New delta
//    public boolean update(UnitsDelta delta) {
//        screen.doResizeIfNecessary(); // Actualize size data
//        var terminalSize = screen.getTerminalSize();
//        var updateType = terminalSize.equals(lastTerminalSize) ? Screen.RefreshType.DELTA : Screen.RefreshType.COMPLETE;
//        lastTerminalSize = terminalSize;
//
//        if (delta.getFocus() != null) {
//            // for the resizable fields --- pass terminalSize as argument
//            drawPlains(delta);
//        }
//
//        if (delta.getMap() != null) {
//            clearPlayground(screen.getMinimumSize());
//            drawMap(delta.getMap());
//        }
//
//        if (delta.getUnitChanges() != null && !delta.getUnitChanges().isEmpty()) {
//            var unitChanges = delta.getUnitChanges();
//            for (var change : unitChanges) {
//                drawUnitChanges(change);
//            }
//        }
//
//        if (delta.getInventoryChanges() != null) {
//            drawInventory(delta.getInventoryChanges());
//        }
//
//        drawStatistics(delta.getStatistics());
//
//        // Refresh after everything
//        try {
//            screen.refresh(updateType);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("Display updated!");
//        return true;
//    }

    private TerminalPosition getPlaygroundPosition() {
        return new TerminalPosition(LEFT_MARGIN, TOP_MARGIN);
    }

    private int getPlaygroundWidth() {
        return (int) (referenceSize.getColumns() * PLAYGROUND_COEF) - LEFT_MARGIN;
    }

    private int getPlaygroundHeight() {
        return referenceSize.getRows() - TOP_MARGIN - BOTTOM_MARGIN;
    }

    private TerminalSize getPlaygroundSize() {
        return new TerminalSize(
                getPlaygroundWidth(),
                getPlaygroundHeight());
    }

    private TerminalPosition getInventoryPosition() {
        var playgroundPosition = getPlaygroundPosition();
        int x = playgroundPosition.getColumn() + getPlaygroundWidth() + 1;
        return new TerminalPosition(x, TOP_MARGIN);
    }

    private int getInventoryWidth() {
        return (int)(referenceSize.getColumns() * INVENTORY_COEF) - RIGHT_MARGIN;
    }

    private int getInventoryHeight() {
        return referenceSize.getRows() - getStatisticsHeight() - TOP_MARGIN - BOTTOM_MARGIN - 1;
    }

    private TerminalSize getInventorySize() {
        return new TerminalSize(
                getInventoryWidth(),
                getInventoryHeight());
    }

    private TerminalPosition getStatisticsPosition() {
        var inventoryPosition = getInventoryPosition();
        var y = inventoryPosition.getRow() + getInventoryHeight() + 1;
        return new TerminalPosition(inventoryPosition.getColumn(), y);
    }

    private int getStatisticsWidth() {
        return getInventoryWidth();
    }

    private int getStatisticsHeight() {
        return (int) (referenceSize.getRows() * STATS_COEF);
    }

    private TerminalSize getStatisticsSize() {
        return new TerminalSize(
                getStatisticsWidth(),
                getStatisticsHeight());
    }

    private TerminalPosition shiftPos(TerminalPosition position) {
        return new TerminalPosition(position.getColumn() + 1,
                position.getRow() + 1);
    }

    private TerminalPosition getMapOrigin() {
        return shiftPos(getPlaygroundPosition());
    }

    private TerminalPosition getInventoryOrigin() {
        return shiftPos(getInventoryPosition());
    }

    private TerminalPosition getStatisticsOrigin() {
        return shiftPos(getStatisticsPosition());
    }

    protected void drawPlains(@NotNull Focus focus) {
        // draw playground
        var playBorders = focus.equals(Focus.LEVEL) ? doubled : simple;
        drawSquare(getPlaygroundPosition(), getPlaygroundSize(), playBorders);

        // draw inventory
        var invBorders = focus.equals(Focus.LEVEL) ? simple : doubled;
        drawSquare(getInventoryPosition(), getInventorySize(), invBorders);

        // draw statistics
        drawSquare(getStatisticsPosition(), getStatisticsSize(), simple);
    }

    private void clearPlayground() {
        var playgroundSize = getPlaygroundSize();
        var origin = getMapOrigin();
        var graphics = screen.newTextGraphics();
        // -2 to adjust for borders (which are included in playgroundSize)
        for (int i = origin.getColumn(); i < playgroundSize.getColumns() - 2; i++) {
            for (int j = origin.getRow(); j < playgroundSize.getRows() - 2; j++) {
                graphics.setCharacter(i, j, TextCharacter.DEFAULT_CHARACTER);
            }
        }
    }

    private void cleanStats() {
        var statSize = getStatisticsSize();
        var origin = getStatisticsOrigin();
        var graphics = screen.newTextGraphics();

        for (int i = origin.getColumn(); i < origin.getColumn() + statSize.getColumns() - 2; i++) {
            for (int j = origin.getRow(); j < origin.getRow() + statSize.getRows() - 2; j++) {
                graphics.setCharacter(i, j, TextCharacter.DEFAULT_CHARACTER);
            }
        }
    }

    protected void drawMap(MapView curMap) {
        clearPlayground(referenceSize);

        background = curMap;

        var width = curMap.getWidth();
        var height = curMap.getHeight();

        var graphics = screen.newTextGraphics();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                var screenCoordinates = getScreenIndex(new Position(i, j));
                var tileType = curMap.getTile(new Position(i, j));

                var tileObject = mapObjects.get(tileType);
                graphics.setCharacter(screenCoordinates.x(), screenCoordinates.y(),
                        new TextCharacter(tileObject.tile).withForegroundColor(tileObject.color));
            }
        }
    }


    protected void drawUnitDelta(UnitsDelta delta) {
        // TODO: Implement
    }

    private void drawUnitChange(UnitUpdate unitUpd) {
        Unit unit = unitUpd.getUnit();
        var newPos = unit.getPosition();
        var screenCoordinates = getScreenIndex(newPos);

        var graphics = screen.newTextGraphics();
        var unitChar = unit.isDead() ? unit.getDeadChar() : unit.getAliveChar();
        var curHealth = unit.getHealth();
        var maxHealth = unit.getMaxHealth();
        var color = curHealth / (double)maxHealth > 0.7 ? TextColor.ANSI.GREEN : TextColor.ANSI.YELLOW;
        if (curHealth < 0.3) color = TextColor.ANSI.RED;
        graphics.setCharacter(screenCoordinates.x(), screenCoordinates.y(),
                new TextCharacter(unitChar).withForegroundColor(color));

        if (unitUpd instanceof UnitPositionUpdate) {
            var prevPos = ((UnitPositionUpdate) unitUpd).getOldPosition();
            var prevTile = background.getTile(prevPos);
            var prevTileObject = mapObjects.get(prevTile);

            graphics.setCharacter(getScreenIndex(prevPos).x(), getScreenIndex(prevPos).y(),
                    new TextCharacter(prevTileObject.tile).withForegroundColor(prevTileObject.color));
            graphics.setCharacter(screenCoordinates.x(), screenCoordinates.y(),
                    new TextCharacter(unit.getAliveChar()).withForegroundColor(color));
        }
    }

    private String extendItemString(String name) {
        final var required = getInventoryWidth() - 2;
        var length = name.length();
        var spaces = new char[required - length];
        Arrays.fill(spaces, ' ');
        return name + new String(spaces);
    }

    protected void drawInventory(List<InventoryUpdate> inventoryUpdates) {
        var graphics = screen.newTextGraphics();
        for (var update : inventoryUpdates) {
            if (update.index() == -1) {
                continue; // ignore
            }

            var name = extendItemString(update.name());
            var idx = getInventoryIndex(update.index());
            if (update.focused()) {
                graphics.putString(idx, name, SGR.REVERSE);
            } else {
                graphics.putString(idx, name);
            }
        }
    }

    private void drawStatistics(Statistics statistics) {
        if (statistics == null) {
            return;
        }

        cleanStats();

        var graphics = screen.newTextGraphics();
        var unit = statistics.trackedUnit();

        var origin = getStatisticsOrigin();
        int currX = origin.getColumn();
        int currY = origin.getRow();

        var healthString = unit.getHealth() + "/" + unit.getMaxHealth();
        graphics.setCharacter(currX, currY, getUnicode(0x2665));
        graphics.putString(currX + 1, currY, healthString);

        currX += healthString.length() + 4;
        var strengthString = "STR:" + unit.getStrength();
        graphics.putString(currX, currY, strengthString);

        currX += strengthString.length() + 3;
        var exp = "EXP:" + unit.getExperience() + "/" + unit.levelUpCondition();
        graphics.putString(currX, currY, exp);

        currX += exp.length() + 3;
        var lvl = "LVL:" + unit.getLevel();
        graphics.putString(currX, currY, lvl);

        currX = origin.getColumn();
        currY += 2;

        var room = "ROOM:" + statistics.currentRoom() + " HIGH:" + statistics.previousRoomRecord();
        graphics.putString(currX, currY, room);
    }


    private Position getScreenIndex(Position mapIndex) {
        var x = mapIndex.x();
        var y = mapIndex.y();
        var origin = getMapOrigin();
        var dx = origin.getColumn();
        var dy = origin.getRow();
        return new Position(x + dx, y + dy);
    }

    private Position getMapIndex(Position screenIndex) {
        var x = screenIndex.x();
        var y = screenIndex.y();
        var origin = getMapOrigin();
        var dx = origin.getColumn();
        var dy = origin.getRow();
        return new Position(x - dx, y - dy);
    }

    private TerminalPosition getInventoryIndex(int itemIdx) {
        var origin = getInventoryOrigin();
        var x = origin.getColumn();
        var y = origin.getRow();
        return new TerminalPosition(x, y + itemIdx);
    }

    record MapChars(char tile, TextColor color) {
        public MapChars(int tile, TextColor color) {
            this(getUnicode(tile), color);
        }
    }

    static TextCharacter healthSymbol = new TextCharacter(getUnicode(0x2665));

    static MapChars floor = new MapChars(' ', TextColor.ANSI.DEFAULT);
    static MapChars wall = new MapChars(0x2588, TextColor.ANSI.WHITE);
    static MapChars doorIn = new MapChars(0x2591, TextColor.ANSI.WHITE);
    static MapChars doorOutNormal = new MapChars(0x2588, TextColor.ANSI.GREEN);
    static MapChars doorOurHard = new MapChars(0x2588, TextColor.ANSI.RED);
    static MapChars doorOutTreasure = new MapChars(0x2588, TextColor.ANSI.YELLOW);

    private static final HashMap<MapView.Tile, MapChars> mapObjects = new HashMap<>() {{
        put(MapView.Tile.FLOOR, floor);
        put(MapView.Tile.WALL, wall);
        put(MapView.Tile.DOOR_IN, doorIn);
        put(MapView.Tile.DOOR_OUT_NORMAL, doorOutNormal);
        put(MapView.Tile.DOOR_OUT_HARD, doorOurHard);
        put(MapView.Tile.DOOR_OUT_TREASURE_ROOM, doorOutTreasure);
    }};

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
