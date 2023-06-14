package ru.itmo.rogue.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.VirtualScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import ru.itmo.rogue.model.state.StateView;

import java.io.IOException;

public class DefaultView implements View {

    private final VirtualScreen screen;
    private final LanternaView lanternaView;
    private StateView lastState;
    private Focus focus;

    private final LevelView levelView;
    private final InventoryView inventoryView;

    public DefaultView() throws IOException {
        // LANTERNA INIT
        // TODO: Move Screen initialization fully to LanternaView
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(new TerminalSize(130, 40));
        var terminalScreen = terminalFactory.createScreen();
        terminalScreen.getTerminal().setCursorVisible(false);
        screen = new VirtualScreen(terminalScreen);
        lanternaView = new LanternaView(screen);

        // INIT
        levelView = new LevelView(lanternaView);
        inventoryView = new InventoryView(lanternaView);
    }

    @Override
    public boolean update(StateView state) {
        var ret = levelView.update(state) && inventoryView.update(state);
        lastState = state;
        return ret;
    }

    public Screen getScreen() {
        return screen; // TODO: Get from LanternaView
    }

    public Focus toggleFocus() {
        setFocus(focus.equals(Focus.LEVEL) ? Focus.INVENTORY : Focus.LEVEL);
        return focus;
    }

    public void setFocus(Focus focus) {
        this.focus = focus;
        lanternaView.drawPlains(focus);
    }

    public Focus getFocus() {
        return focus;
    }
}
