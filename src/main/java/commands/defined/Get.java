package commands.defined;

import client.Client;
import commands.Command;

public class Get implements Command {

    private final Client client;
    private String key;

    public Get(Client client, String key) {
        this.client = client;
        this.key = key;
    }

    @Override
    public void execute() {
        String value = client.getMap().get(key);
        System.out.println(value);
    }
}
