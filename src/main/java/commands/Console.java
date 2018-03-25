package commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class Console implements Runnable {

    private final Deque<Command> commands;
    private final BufferedReader stdInput;

    public Console() {
        this.commands = new ArrayDeque<>();
        stdInput = new BufferedReader(new InputStreamReader(System.in));
    }

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    public Deque<Command> getCommands() {
        return commands;
    }

    @Override
    public void run() {

    }

    private void executeCommand(Command command) {
        if (!(command instanceof UnknownCommand))
            command.execute();
    }
}
