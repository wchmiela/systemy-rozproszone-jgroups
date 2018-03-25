package commands;

import client.Client;

public class Put implements Command {

    private final Client client;
    private String key;
    private String value;

    public Put(Client client, String key, String value) {
        this.client = client;
        this.key = key;
        this.value = value;
    }


    @Override
    public void execute() {

    }
}
