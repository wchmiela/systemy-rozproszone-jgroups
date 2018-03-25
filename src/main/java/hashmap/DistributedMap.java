package hashmap;

import org.jgroups.JChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DistributedMap implements SimpleStringMap {

    private Map<String, String> map;
    private JChannel channel;
    private String address;
    private int port;

    public DistributedMap(JChannel channel, String address, int port) {
        this.channel = channel;
        this.address = address;
        this.port = port;

        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public boolean containsKey(String key) {
        return map.get(key) != null;
    }

    @Override
    public String get(String key) {
        return map.get(key);
    }

    @Override
    public String put(String key, String value) {
        return map.put(key, value);
    }

    @Override
    public String remove(String key) {
        return map.remove(key);
    }

    public Map<String, String> getMap() {
        return map;
    }

    public JChannel getChannel() {
        return channel;
    }
}
