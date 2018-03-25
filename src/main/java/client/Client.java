package client;

import commands.Command;
import commands.UnknownCommand;
import hashmap.DistributedMap;
import interpreter.MyParser;
import interpreter.Parser;
import org.jgroups.Channel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client implements Runnable {

    private final DistributedMap map;
    private final BufferedReader stdInput;

    public Client(DistributedMap map) {
        this.map = map;
        this.stdInput = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {

        boolean running = true;
        System.out.println("Witojcie");

        Parser parser = new MyParser(this);

        try {
            while (running) {
                String userInput;
                while ((userInput = stdInput.readLine()) != null) {
                    running = parser.parse(userInput);

                    if (!running) break;

                    executeCommand(parser.evaluate());
                }
            }
        } catch (IOException e) {
            //todo fill
        }

        System.exit(0);
    }

    private void executeCommand(Command command) {
        if (!(command instanceof UnknownCommand))
            command.execute();
    }

    public Channel getChannel() {
        return map.getChannel();
    }

    public DistributedMap getMap() {
        return map;
    }
}
