package hashmap;

import org.jgroups.JChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DistributedMap implements SimpleStringMap {

    private Map<String, String> map;
    private JChannel channel;

    public DistributedMap() {
        this.map = new ConcurrentHashMap<>();
    }

    public void setChannel(JChannel channel) {
        this.channel = channel;
    }

    public DistributedMap(JChannel channel) {
        this.channel = channel;

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

    public JChannel getChannel() {
        return channel;
    }

    public Map<String, String> getMap() {
        return map;
    }
}
