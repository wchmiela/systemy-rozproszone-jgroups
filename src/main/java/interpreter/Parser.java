package interpreter;

import commands.Command;

public interface Parser {

    void parse(String line);

    Command evaluate();
}
