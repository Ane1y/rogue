package ru.itmo.rogue.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.VirtualScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import ru.itmo.rogue.model.state.StateView;

import java.io.IOException;

public class DefaultView implements View {

    private final LanternaView lanternaView;
    private final VirtualScreen screen;
    private StateView lastState;
    private Focus focus;

    public DefaultView() throws IOException {
        // TODO: Move Screen initialization fully to LanternaView
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(new TerminalSize(130, 40));
        var terminalScreen = terminalFactory.createScreen();
        terminalScreen.getTerminal().setCursorVisible(false);
        screen = new VirtualScreen(terminalScreen);
        lanternaView = new LanternaView(screen);
    }

    @Override
    public boolean update(StateView data) {
        return false;
    }

    public Screen getScreen() {
        return screen; // TODO: Get from LanternaView
    }

    @Override
    public Focus toggleFocus() {
        setFocus(focus.equals(Focus.LEVEL) ? Focus.INVENTORY : Focus.LEVEL);
        return focus;
    }

    @Override
    public void setFocus(Focus focus) {
        this.focus = focus;
        lanternaView.drawPlains(focus);
    }

    @Override
    public Focus getFocus() {
        return focus;
    }
}
