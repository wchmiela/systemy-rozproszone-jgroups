package client;

import commands.Command;
import commands.Console;
import commands.UnknownCommand;
import hashmap.DistributedMap;
import interpreter.MyParser;
import interpreter.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client implements Runnable {

    private final DistributedMap map;
    private final BufferedReader stdInput;

    public Client(DistributedMap map, Console console) {
        this.map = map;
        this.stdInput = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        System.out.println("Witojcie");

        Parser parser = new MyParser(this);

        try {
            while (true) {
                String userInput;
                while ((userInput = stdInput.readLine()) != null) {
                    parser.parse(userInput);
                    parser.evaluate();
                    executeCommand(parser.evaluate());
                }
            }
        } catch (IOException e) {
            //todo fill
        }


//        HashMapOperationProtos.HashMapOperation operation;
//        operation = HashMapOperationProtos.HashMapOperation.newBuilder()
//                .setValue("test" + new Random().nextInt())
//                .setType(HashMapOperationProtos.HashMapOperation.OperationType.PUT)
//                .build();
//
//        byte[] sendBuffer = operation.toByteArray();
//
//        Message message = new Message(null, null, sendBuffer);
//        try {
//            map.getChannel().send(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private void executeCommand(Command command) {
        if (!(command instanceof UnknownCommand))
            command.execute();
    }
}
