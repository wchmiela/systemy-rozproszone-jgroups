package commands;

import client.Client;

public class Contains implements Command {

    private final Client client;
    private String key;

    public Contains(Client client, String key) {
        this.client = client;
        this.key = key;
    }


    @Override
    public void execute() {
        boolean contains = client.getMap().containsKey(key);
        String answer = contains
                ? String.format("Hashmapa zawiera %s", key) : String.format("Hashmapa nie zawiera %s", key);
        System.out.println(answer);
    }
}
