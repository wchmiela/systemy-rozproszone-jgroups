package commands;

import java.util.ArrayDeque;

public class MultiCommand implements Command {

    private final ArrayDeque<Command> commands;

    public MultiCommand(ArrayDeque<Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute() {
        commands.forEach(Command::execute);
    }
}
