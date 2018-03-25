package interpreter.expressions;

import client.Client;
import commands.Command;
import commands.defined.Put;
import commands.UnknownCommand;

import java.util.ArrayDeque;
import java.util.Deque;

public class TerminalExpressionPUT implements Expression {

    private final Client client;
    private String raw;

    public TerminalExpressionPUT(Client client) {
        this.client = client;
        this.raw = "Put";
    }

    @Override
    public Command interpret(Deque<Expression> s) {
        ArrayDeque<Expression> parameters = Expression.getParameters(s);
        if (parameters.size() != 2 || !(parameters.peekFirst() instanceof TerminalExpressionSTRING))
            return new UnknownCommand(this, parameters);

        String key = ((TerminalExpressionSTRING) parameters.pop()).getValue();
        String value = ((TerminalExpressionSTRING) parameters.pop()).getValue();

        return new Put(client, key, value);
    }

    @Override
    public String getRaw() {
        return raw;
    }
}
