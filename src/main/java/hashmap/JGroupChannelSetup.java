package hashmap;

import client.Client;
import org.jgroups.JChannel;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import org.jgroups.stack.Protocol;
import org.jgroups.stack.ProtocolStack;

import java.net.InetAddress;

public class JGroupChannelSetup {

    private Client client;
    private String address;
    private String channelName;
    private JChannel channel;

    public JGroupChannelSetup(Client client, String address, String channelName) {
        this.client = client;
        this.address = address;
        this.channelName = channelName;
        try {
            this.setupChannel();
        } catch (Exception e) {
            System.out.println("JGroup Channel Error During Configuration");
        }
    }

    private ProtocolStack setupProtocolStack() throws Exception {
        ProtocolStack stack = new ProtocolStack();

        Protocol udpProtocol = new UDP();
        udpProtocol.setValue("mcast_group_addr", InetAddress.getByName(address));

        stack.addProtocol(udpProtocol)
                .addProtocol(new PING())
                .addProtocol(new MERGE3())
                .addProtocol(new FD_SOCK())
                .addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
                .addProtocol(new VERIFY_SUSPECT())
                .addProtocol(new BARRIER())
                .addProtocol(new NAKACK2())
                .addProtocol(new UNICAST3())
                .addProtocol(new STABLE())
                .addProtocol(new GMS())
                .addProtocol(new UFC())
                .addProtocol(new MFC())
                .addProtocol(new FRAG2())
                .addProtocol(new SEQUENCER())
                .addProtocol(new STATE_TRANSFER())
                .addProtocol(new FLUSH());

        return stack;
    }

    private void setupChannel() throws Exception {
        this.channel = new JChannel(false);

        ProtocolStack protocolStack = setupProtocolStack();
        this.channel.setReceiver(client);
        this.channel.setProtocolStack(protocolStack);
        protocolStack.init();

        this.channel.connect(channelName);
        this.channel.getState(null, 30000);
    }

    public JChannel getChannel() {
        return channel;
    }
}
