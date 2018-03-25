package interpreter.expressions;

import client.Client;
import commands.Command;

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
        return null;
    }

    @Override
    public String getRaw() {
        return null;
    }
}
