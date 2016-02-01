package co.lujun.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import co.hkm.soltag.TagContainerLayout;
import co.hkm.soltag.TagView;
import co.hkm.soltag.ext.LayouMode;

/**
 * Created by hesk on 1/2/16.
 */
public enum SampleCollections {

    ROUND_CORNER(R.id.tagcontainerLayout1) {
        @Override
        protected TagContainerLayout define(final Activity activity) {
            container.setMode(LayouMode.SINGLE_CHOICE);
            // Set custom click listener
            container.setOnTagClickListener(new TagView.OnTagClickListener() {
                @Override
                public void onTagClick(int position, String text) {
                    Toast.makeText(activity, "click-position:" + position + ", text:" + text,
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTagLongClick(final int position, String text) {
                    AlertDialog dialog = new AlertDialog.Builder(activity)
                            .setTitle("long click")
                            .setMessage("You will delete this tag!")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    container.removeTag(position);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    dialog.show();
                }
            });


            final EditText text = (EditText) activity.findViewById(R.id.text_tag);
            Button btnAddTag = (Button) activity.findViewById(R.id.btn_add_tag);
            btnAddTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    container.addTag(text.getText().toString());
                    // Add tag in the specified position
//                mTagContainerLayout1.addTag(text.getText().toString(), 4);
                }
            });

            return container;
        }
    },
    COUNTRY_LIST(R.id.tagcontainerLayout2) {
        @Override
        protected TagContainerLayout define(final Activity act) {
            Typeface typeface = Typeface.createFromAsset(act.getAssets(), "iran_sans.ttf");
            container.setTagTypeface(typeface);
            return container;
        }
    }, SPECIAL_TEXT(R.id.tagcontainerLayout3) {
        @Override
        protected TagContainerLayout define(final Activity act) {

            return container;
        }
    }, HBX_STYLE(R.id.tagcontainerLayout5) {
        @Override
        protected TagContainerLayout define(final Activity act) {
            container.setThemeOnActive(R.style.tagactive);
            container.setTheme(R.style.tagnormal);
            container.setMode(LayouMode.SINGLE_CHOICE);
            container.setOnTagClickListener(new TagView.OnTagClickListener() {
                @Override
                public void onTagClick(int position, String text) {
                    Toast.makeText(act, "click-position:" + position + ", text:" + text,
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTagLongClick(final int position, String text) {
                    AlertDialog dialog = new AlertDialog.Builder(act)
                            .setTitle("long click")
                            .setMessage("You will delete this tag!")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    container.removeTag(position);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    dialog.show();
                }
            });

            return container;
        }
    };
    private int layoutid;
    protected TagContainerLayout container;

    SampleCollections(@IdRes final int layout) {
        layoutid = layout;
    }

    public SampleCollections render(Activity view) {
        container = (TagContainerLayout) view.findViewById(layoutid);
        return this;
    }

    public SampleCollections render(View view) {
        container = (TagContainerLayout) view.findViewById(layoutid);
        return this;
    }

    protected abstract TagContainerLayout define(final Activity act);

    public void setTag(String n) {
        container.addTag(n);
    }

    public void setTags(List<String> n) {
        container.setTags(n);
    }

    public void setTags(String... n) {
        container.setTags(n);
    }
}
