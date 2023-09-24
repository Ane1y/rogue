package ru.itmo.rogue.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.VirtualScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import ru.itmo.rogue.model.state.StateView;

import java.io.IOException;

/**
 * View, initializes lanterna screen, manages focus, creates subviews and delegates corresponding functions to them
 */
public class DefaultView implements View {

    private final VirtualScreen screen;
    private final LanternaView lanternaView;
    private Focus focus = Focus.LEVEL;

    private final LevelView levelView;
    private final InventoryView inventoryView;
    private final StatisticsView statisticsView;

    /**
     * Initializes lanterna screen, can throw
     * @throws IOException lanterna exception
     */
    public DefaultView() throws IOException {
        // LANTERNA INIT
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(new TerminalSize(130, 40));
        var terminalScreen = terminalFactory.createScreen();
        terminalScreen.getTerminal().setCursorVisible(false);
        screen = new VirtualScreen(terminalScreen);
        lanternaView = new LanternaView(screen);

        // INIT
        levelView = new LevelView(lanternaView);
        inventoryView = new InventoryView(lanternaView);
        statisticsView = new StatisticsView(lanternaView);

        lanternaView.drawPlains(focus);
    }

    @Override
    public boolean update(StateView state) {
        return levelView.update(state) &&
                inventoryView.update(state) &&
                statisticsView.update(state) &&
                refreshScreen();
    }

    public Screen getScreen() {
        return screen;
    }

    public void toggleFocus() {
        setFocus(focus.equals(Focus.LEVEL) ? Focus.INVENTORY : Focus.LEVEL);
    }

    public void setFocus(Focus focus) {
        this.focus = focus;
        lanternaView.drawPlains(focus);
        refreshScreen();
    }

    public Focus getFocus() {
        return focus;
    }

    public void itemFocusUp() {
        inventoryView.focusUp();
        refreshScreen();
    }

    public void itemFocusDown() {
        inventoryView.focusDown();
        refreshScreen();
    }

    public int getFocusedItem() {
        return inventoryView.getFocusedItem();
    }

    private boolean refreshScreen() {
        try {
            screen.refresh(Screen.RefreshType.DELTA);
        } catch (IOException e) {
            System.err.println("VIEW error occurred");
            System.err.println(e.toString());
            return false;
        }
        return true;
    }

}
