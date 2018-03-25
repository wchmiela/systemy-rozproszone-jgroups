package commands;

public class Remove implements Command {

    private final Console console;
    private String key;

    public Remove(Console console) {
        this.console = console;
    }

    @Override
    public void execute() {

    }
}
