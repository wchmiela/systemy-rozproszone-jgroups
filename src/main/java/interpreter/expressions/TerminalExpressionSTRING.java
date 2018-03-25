package interpreter.expressions;

import commands.Command;
import commands.UnknownCommand;

import java.util.Deque;

public class TerminalExpressionSTRING implements Expression {
    private String value;

    public TerminalExpressionSTRING(String value) {
        this.value = value;
    }

    @Override
    public Command interpret(Deque<Expression> s) {
        return new UnknownCommand(this);
    }

    @Override
    public String getRaw() {
        return value;
    }

    public String getValue() {
        return value;
    }
}
