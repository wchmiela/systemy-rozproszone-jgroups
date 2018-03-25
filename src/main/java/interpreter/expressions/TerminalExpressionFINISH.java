package interpreter.expressions;

import commands.Command;
import commands.Finish;

import java.util.Deque;

public class TerminalExpressionFINISH implements Expression {

    @Override
    public Command interpret(Deque<Expression> s) {
        return new Finish();
    }

    @Override
    public String getRaw() {
        return "Finish";
    }
}
