import client.Client;
import hashmap.DistributedMap;
import hashmap.JGroupChannelSetup;
import org.jgroups.JChannel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner {

    private static final String address = "224.0.0.7";
    private static final String channelName = "rozprochyy";

    public static void main(String[] args) {
        JGroupChannelSetup setup = new JGroupChannelSetup(address, channelName);
        JChannel channel = setup.getChannel();

        DistributedMap distributedMap = new DistributedMap(channel);

        Client client = new Client(distributedMap);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(client);
    }
}
