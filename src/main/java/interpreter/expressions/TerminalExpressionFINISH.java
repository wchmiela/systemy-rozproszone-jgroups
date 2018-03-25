package interpreter.expressions;

import commands.Command;
import commands.defined.Finish;

import java.util.Deque;

public class TerminalExpressionFINISH implements Expression {

    private String raw;

    public TerminalExpressionFINISH() {
        this.raw = "Finish";
    }

    @Override
    public Command interpret(Deque<Expression> s) {
        return new Finish();
    }

    @Override
    public String getRaw() {
        return raw;
    }
}
