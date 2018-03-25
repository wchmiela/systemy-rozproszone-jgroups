package commands.defined;

import client.Client;
import commands.Command;
import commands.Receiver;
import hashmap.HashMapOperationProtos;
import org.jgroups.Message;

public class Put implements Command, Receiver {

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
        HashMapOperationProtos.HashMapOperation operation;
        operation = HashMapOperationProtos.HashMapOperation.newBuilder()
                .setType(HashMapOperationProtos.HashMapOperation.OperationType.PUT)
                .setKey(key)
                .setValue(value)
                .build();

        byte[] sendBuffer = operation.toByteArray();

        Message message = new Message(null, null, sendBuffer);

        try {
            client.getChannel().send(message);
        } catch (Exception e) {
            client.getChannel().close();
        }

        apply();
    }

    @Override
    public void apply() {
        client.getMap().put(key, value);
    }
}
