package interpreter;

import commands.Command;

public interface Parser {

    boolean parse(String line);

    Command evaluate();
}
