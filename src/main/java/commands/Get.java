package commands;

import client.Client;

public class Get implements Command {

    private final Client client;
    private String key;

    public Get(Client client, String key) {
        this.client = client;
        this.key = key;
    }

    @Override
    public void execute() {

    }
}
