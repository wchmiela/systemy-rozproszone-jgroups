package client;

import com.google.protobuf.InvalidProtocolBufferException;
import commands.Command;
import commands.Receiver;
import commands.UnknownCommand;
import commands.defined.Put;
import commands.defined.Remove;
import hashmap.DistributedMap;
import hashmap.HashMapOperationProtos;
import hashmap.ViewHandler;
import interpreter.MyParser;
import interpreter.Parser;
import org.jgroups.*;

import java.io.*;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client extends ReceiverAdapter implements Runnable {

    private final DistributedMap map;
    private final BufferedReader stdInput;

    public Client(DistributedMap map) {
        this.map = map;
        this.stdInput = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        boolean running = true;
        System.out.println("Jgroups Distributed Hashmap");

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
            System.out.println("Program ulegl natychmiastowemu zakonczeniu");
            System.exit(0);
        }
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

    @Override
    public void receive(Message msg) {
        HashMapOperationProtos.HashMapOperation message = null;
        try {
            message = HashMapOperationProtos.HashMapOperation.parseFrom(msg.getBuffer());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        HashMapOperationProtos.HashMapOperation.OperationType type = message.getType();

        String key = message.getKey();
        String value = message.getValue();

        HashMapOperationProtos.HashMapOperation.OperationType put
                = HashMapOperationProtos.HashMapOperation.OperationType.PUT;

        HashMapOperationProtos.HashMapOperation.OperationType remove
                = HashMapOperationProtos.HashMapOperation.OperationType.REMOVE;

        Optional<Receiver> receiver = Optional.empty();
        if (type.equals(put)) {
            receiver = Optional.of(new Put(this, key, value));
        } else if (type.equals(remove)) {
            receiver = Optional.of(new Remove(this, key));
        }

        receiver.ifPresent(Receiver::apply);
    }

    @Override
    public void getState(OutputStream output) throws Exception {
        synchronized (map.getMap()) {
            HashMapOperationProtos.HashMapState.Builder stateBuilder = HashMapOperationProtos.HashMapState.newBuilder();

            map.getMap().forEach((key, value) -> stateBuilder.addEntriesBuilder()
                    .setKey(key)
                    .setValue(value));

            HashMapOperationProtos.HashMapState state = stateBuilder.build();
            state.writeTo(output);
        }
    }

    @Override
    public void setState(InputStream input) throws Exception {
        synchronized (map.getMap()) {
            HashMapOperationProtos.HashMapState state = HashMapOperationProtos.HashMapState.parseFrom(input);
            map.getMap().clear();

            state.getEntriesList().forEach(entry -> map.getMap().put(entry.getKey(), entry.getValue()));
        }
    }

    @Override
    public void viewAccepted(View view) {
        if (view instanceof MergeView) {
            ViewHandler handler = new ViewHandler(map.getChannel(), (MergeView) view);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(handler);
        }
    }
}
