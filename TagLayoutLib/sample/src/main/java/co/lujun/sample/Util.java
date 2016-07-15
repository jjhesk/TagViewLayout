package co.lujun.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by hesk on 28/4/16.
 */
public class Util {
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

    public static String[] toTag(List<String> list) {
        String[] h = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            h[i] = list.get(i);
        }
        return h;
    }

    public static List<String> getListLanguages() {

        List<String> list1 = new ArrayList<String>();
        list1.add("Java");
        list1.add("C++");
        list1.add("Python");
        list1.add("Swift");
        list1.add("你好，这是一个TAG。你好，这是一个TAG。你好，这是一个TAG。你好，这是一个TAG。");
        list1.add("PHP");
        list1.add("JavaScript");
        list1.add("Html");
        list1.add("Welcome to use AndroidTagView!");

        return list1;
    }

    public static List<String> getCountryList() {

        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("China");
        list2.add("USA");
        list2.add("Austria");
        list2.add("Japan");
        list2.add("Sudan");
        list2.add("Spain");
        list2.add("UK");
        list2.add("Germany");
        list2.add("Niger");
        list2.add("Poland");
        list2.add("Norway");
        list2.add("Uruguay");
        list2.add("Brazil");

        return list2;
    }

    public static List<String> getLetterSizeList() {
        ArrayList<String> list5 = new ArrayList<String>();
        list5.add("M");
        list5.add("K");
        list5.add("US10");
        list5.add("SMALL");
        list5.add("T");
        list5.add("F");
        return list5;
    }
}
