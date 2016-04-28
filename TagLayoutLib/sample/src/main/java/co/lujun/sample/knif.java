package co.lujun.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by hesk on 28/4/16.
 */
public class knif {
    public static void addAmount(int howMany, List<String> list) {
        for (int i = 0; i < howMany; i++) {
            UUID uuid = UUID.randomUUID();
            // TimeLineModel time = new TimeLineModel();
            // time.setAge(uuid.variant());
            //time.setName(uuid.toString());
            list.add(uuid.toString());
        }
    }

    public static String[] toTag(ArrayList<String> list) {
        String[] h = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            h[i] = list.get(i);
        }
        return h;
    }
}
