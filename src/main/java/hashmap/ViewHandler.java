package hashmap;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.MergeView;
import org.jgroups.View;

import java.util.List;

public class ViewHandler implements Runnable {

    private JChannel channel;
    private MergeView view;

    public ViewHandler(JChannel channel, MergeView view) {
        this.channel = channel;
        this.view = view;
    }

    @Override
    public void run() {
        List<View> subgroups = view.getSubgroups();
        View tmp_view = subgroups.get(0);
        Address local_addr = channel.getAddress();

        if (!tmp_view.getMembers().contains(local_addr)) {
            String answer = String.format("Not member of the new primary partition (%s), will re-acquire the state", tmp_view);
            System.out.println(answer);
            try {
                channel.getState(null, 30000);
            } catch (Exception ex) {
                System.out.println("Problemy z getState");
            }
        } else {
            String answer = String.format("Not member of the new primary partition (%s), will do nothing", tmp_view);
            System.out.println(answer);
        }
    }
}
