package co.lujun.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import co.hkm.soltag.TagContainerLayout;
import co.hkm.soltag.ext.LayouMode;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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


        ArrayList<String> list5 = new ArrayList<String>();
        list5.add("M");
        list5.add("K");
        list5.add("US10");
        list5.add("SMALL");
        list5.add("T");
        list5.add("F");

        String[] list3 = new String[]{"Persian", "波斯语", "فارسی", "Hello", "你好", "سلام"};
        String[] list4 = new String[]{"Adele", "Whitney Houston", "Hotel", "Hospital", "Ice House", "Ice Land"};


        // After you set your own attributes for TagView, then set tag(s) or add tag(s)
        SampleCollections.ROUND_CORNER
                .render(this)
                .setMode(LayouMode.SINGLE_CHOICE)
                .define(this)
                .setTags(list1);

        SampleCollections.COUNTRY_LIST.render(this).define(this).setTags(list2);

        SampleCollections.SPECIAL_TEXT
                .render(this)

                .define(this)
                .setTags(list3);


        SampleCollections.MULTIPLE_CHOICE_SAMPLE
                .render(this)
                .setMode(LayouMode.MULTIPLE_CHOICE)
                .define(this)
                .setTags(list4);

        SampleCollections.HBX_STYLE
                .render(this)
                .setMode(LayouMode.SINGLE_CHOICE)
                .define(this)
                .setTags(list5);

        SampleCollections.PRESELECTED
                .render(this)
                .setMode(LayouMode.SINGLE_CHOICE)
                .define(this)
                .setTags(list2);

        SampleCollections.MUTLIPLE_SELECTION_X_PRESELECTION
                .render(this)
                .setMode(LayouMode.MULTIPLE_CHOICE)
                .define(this)
                .setTags(list5);


        knif.addAmount(19, list5);

        final TagContainerLayout cont = SampleCollections.SPEAICAL_DRAWABLE_SELECTION_ITEM
                .render(this)
                .setMode(LayouMode.MULTIPLE_CHOICE)
                .define(this);

        cont.setTags(knif.toTag(list5));
        Button b = (Button) findViewById(R.id.showall);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String casstring = "You have selected %s items";
                Toast.makeText(MainActivity.this, String.format(casstring, cont.getSelectedItems().size()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}
