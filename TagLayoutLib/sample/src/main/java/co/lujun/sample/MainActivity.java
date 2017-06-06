package co.lujun.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import co.hkm.soltag.TagContainerLayout;
import co.hkm.soltag.LayouMode;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String[] list3 = new String[]{"Persian", "波斯语", "فارسی", "Hello", "你好", "سلام"};
        String[] list4 = new String[]{"Adele", "Whitney Houston", "Hotel", "Hospital", "Ice House", "Ice Land"};

        // After you set your own attributes for TagView, then set tag(s) or add tag(s)
        SampleCollections.ROUND_CORNER
                .render(this)
                .setMode(LayouMode.SINGLE_CHOICE)
                .define(this)
                .setTags(Util.getListLanguages());

        SampleCollections.COUNTRY_LIST.render(this).define(this).setTags(Util.getCountryList());

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
                .setTags(Util.getLetterSizeList());

        SampleCollections.PRESELECTED
                .render(this)
                .setMode(LayouMode.SINGLE_CHOICE)
                .define(this)
                .setTags(Util.getListLanguages());

        SampleCollections.MUTLIPLE_SELECTION_X_PRESELECTION
                .render(this)
                .setMode(LayouMode.MULTIPLE_CHOICE)
                .define(this)
                .setTags(Util.getLetterSizeList());

        Util.addAmount(19, new ArrayList<String>());

        final TagContainerLayout cont = SampleCollections.SPEAICAL_DRAWABLE_SELECTION_ITEM
                .render(this)
                .setMode(LayouMode.SINGLE_CHOICE_OVERLAY_PRESET)
                .define(this);

        cont.setTags(Util.toTag(Util.getListLanguages()));
        Button b = (Button) findViewById(R.id.showall);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "You have selected %s items";
                Toast.makeText(MainActivity.this, String.format(str, cont.getSelectedItems().size()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}
