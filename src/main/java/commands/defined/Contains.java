package commands.defined;

import client.Client;
import commands.Command;

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
                ? String.format("Hashmapa zawiera %s o wartosci %s", key, client.getMap().get(key)) : String.format("Hashmapa nie zawiera %s", key);
        System.out.println(answer);
    }
}
