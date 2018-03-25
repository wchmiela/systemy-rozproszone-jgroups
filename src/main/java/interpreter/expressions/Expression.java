package interpreter.expressions;

import commands.Command;

import java.util.ArrayDeque;
import java.util.Deque;

public interface Expression {

    Command interpret(Deque<Expression> s);

    String getRaw();

    static ArrayDeque<Expression> getParameters(Deque<Expression> s) {
        ArrayDeque<Expression> parameters = new ArrayDeque<>();
        ArrayDeque<Expression> nops = new ArrayDeque<>();
        while (!s.isEmpty()) {
            parameters.addLast(s.pollFirst());
        }
        nops.forEach(s::addFirst);
        return parameters;
    }
}
