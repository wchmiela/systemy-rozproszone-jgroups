import client.Client;
import commands.Console;
import hashmap.DistributedMap;
import hashmap.JGroupChannelSetup;
import org.jgroups.JChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {

    private static final String address = "224.0.0.7";
    private static final int port = 6789;
    private static final String channelName = "rozprochy";

    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");

        JGroupChannelSetup setup = new JGroupChannelSetup(address, channelName);
        JChannel channel = setup.getChannel();

        DistributedMap distributedMap = new DistributedMap(channel, address, port);
        Console console = new Console();

        Client client = new Client(distributedMap, console);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(client);
    }
}
