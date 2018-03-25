package interpreter;

import client.Client;
import com.google.common.collect.ImmutableMap;
import commands.Command;
import commands.MultiCommand;
import commands.NopCommand;
import commands.UnknownCommand;
import interpreter.expressions.*;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class MyParser implements Parser {

    private final Client client;
    private Deque<Expression> s;
    private ImmutableMap<String, Expression> expressions;

    public MyParser(Client client) {
        this.client = client;
        this.s = new ArrayDeque<>();
        this.expressions = new ImmutableMap.Builder<String, Expression>()
                .put("put", new TerminalExpressionPUT(client))
                .put("remove", new TerminalExpressionREMOVE(client))
                .put("get", new TerminalExpressionGET(client))
                .put("contains", new TerminalExpressionCONTAINS(client))
                .build();
    }

    @Override
    public void parse(String command) {
        if (command.trim().indexOf("#") == 0) return;

        List<String> list = Arrays.stream(command
                .split("\\s"))
                .map(String::toLowerCase)
                .filter(exp -> exp.length() != 0)
                .collect(Collectors.toList());

        loopLabel:
        for (String expression : list) {
            for (String defaultExp : expressions.keySet()) {
                if (expression.matches(defaultExp)) {
                    s.add(expressions.get(defaultExp));
                    continue loopLabel;
                }
            }

            if (expression.matches("\\d+[.,]\\d+"))
                s.add(new TerminalExpressionSTRING(expression));
            if (expression.matches("put")) {
                s.add(new TerminalExpressionPUT(client));
            } else if (expression.matches("get")) {
                s.add(new TerminalExpressionGET(client));
            } else if (expression.matches("remove")) {
                s.add(new TerminalExpressionREMOVE(client));
            } else if (expression.matches("contains")) {
                s.add(new TerminalExpressionCONTAINS(client));
            }
        }
    }

    @Override
    public Command evaluate() {
        ArrayDeque<Command> commands = new ArrayDeque<>();
        while (!s.isEmpty()) {
            commands.add(s.pollFirst().interpret(s));
        }

        if (!commands
                .stream()
                .filter(command -> command instanceof UnknownCommand)
                .collect(Collectors.toList()).isEmpty()) {
            return new UnknownCommand(commands);
        }

        return commands.size() > 0 ? new MultiCommand(commands) : new NopCommand();
    }
}
