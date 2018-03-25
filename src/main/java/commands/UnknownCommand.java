package commands;

import interpreter.expressions.Expression;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;

public class UnknownCommand implements Command {
    private static final Logger log = LogManager.getLogger(UnknownCommand.class);
    private String logMessage;

    public UnknownCommand(ArrayDeque<Command> commands) {
        StringBuilder builder = new StringBuilder()
                .append("Cannot execute command").append("\n");
        commands.forEach(command -> {
            if (command instanceof UnknownCommand) {
                builder.append("\n")
                        .append(command)
                        .append("\n");
            } else {
                builder.append(command).append(" ");
            }
        });
        logMessage = builder.toString();
    }

    public UnknownCommand(Expression expression, ArrayDeque<Expression> parameters) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Unknown command '%s' with%s parameters ",
                expression.getRaw(), parameters.isEmpty() ? " no" : ""));
        for (Expression raw : parameters) {
            builder.append(String.format("'%s'", raw.getRaw())).append(" ");
        }
        logMessage = builder.toString();

    }

    public UnknownCommand(Expression expression) {
        logMessage = "Unknown command " + expression.getRaw();

    }

    @Override
    public void execute() {
        log.warn(logMessage);
    }
}
