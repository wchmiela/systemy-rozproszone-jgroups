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
//        DistributedMap distributedMap = new DistributedMap(channel);
        DistributedMap distributedMap = new DistributedMap();
        Client client = new Client(distributedMap);

        JGroupChannelSetup setup = new JGroupChannelSetup(client, address, channelName);
        JChannel channel = setup.getChannel();
        distributedMap.setChannel(channel);


        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(client);
    }
}
