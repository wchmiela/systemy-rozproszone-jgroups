package interpreter.expressions;

import client.Client;
import commands.Command;
import commands.Remove;
import commands.UnknownCommand;

import java.util.ArrayDeque;
import java.util.Deque;

public class TerminalExpressionREMOVE implements Expression {

    private final Client client;
    private String raw;

    public TerminalExpressionREMOVE(Client client) {
        this.client = client;
        this.raw = "Remove";
    }

    @Override
    public Command interpret(Deque<Expression> s) {
        ArrayDeque<Expression> parameters = Expression.getParameters(s);
        if (parameters.size() != 1 || !(parameters.peekFirst() instanceof TerminalExpressionSTRING))
            return new UnknownCommand(this, parameters);

        String key = ((TerminalExpressionSTRING) parameters.pop()).getValue();

        return new Remove(client, key);
    }

    @Override
    public String getRaw() {
        return raw;
    }
}
