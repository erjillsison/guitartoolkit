package com.erjill.guitartoolkit;

import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.onClick;

public class MainActivity extends AppCompatActivity {
    NotesData notesData = new NotesData();
    private List<ToggleButton> buttons;
    private ArrayList<Integer> notesInput = new ArrayList();

    ListView lv;
    TextView tv;


    private static final int[] BUTTON_IDS={
            R.id.toggleButton01,
            R.id.toggleButton02,
            R.id.toggleButton03,
            R.id.toggleButton04,
            R.id.toggleButton05,
            R.id.toggleButton06,
            R.id.toggleButton11,
            R.id.toggleButton12,
            R.id.toggleButton13,
            R.id.toggleButton14,
            R.id.toggleButton15,
            R.id.toggleButton16,
            R.id.toggleButton21,
            R.id.toggleButton22,
            R.id.toggleButton23,
            R.id.toggleButton24,
            R.id.toggleButton25,
            R.id.toggleButton26,
            R.id.toggleButton31,
            R.id.toggleButton32,
            R.id.toggleButton33,
            R.id.toggleButton34,
            R.id.toggleButton35,
            R.id.toggleButton36,
            R.id.toggleButton41,
            R.id.toggleButton42,
            R.id.toggleButton43,
            R.id.toggleButton44,
            R.id.toggleButton45,
            R.id.toggleButton46,
            R.id.toggleButton51,
            R.id.toggleButton52,
            R.id.toggleButton53,
            R.id.toggleButton54,
            R.id.toggleButton55,
            R.id.toggleButton56,
            R.id.toggleButton61,
            R.id.toggleButton62,
            R.id.toggleButton63,
            R.id.toggleButton64,
            R.id.toggleButton65,
            R.id.toggleButton66,
            R.id.toggleButton71,
            R.id.toggleButton72,
            R.id.toggleButton73,
            R.id.toggleButton74,
            R.id.toggleButton75,
            R.id.toggleButton76,
            R.id.toggleButton81,
            R.id.toggleButton82,
            R.id.toggleButton83,
            R.id.toggleButton84,
            R.id.toggleButton85,
            R.id.toggleButton86,
            R.id.toggleButton91,
            R.id.toggleButton92,
            R.id.toggleButton93,
            R.id.toggleButton94,
            R.id.toggleButton95,
            R.id.toggleButton96,
            R.id.toggleButton101,
            R.id.toggleButton102,
            R.id.toggleButton103,
            R.id.toggleButton104,
            R.id.toggleButton105,
            R.id.toggleButton106,
            R.id.toggleButton111,
            R.id.toggleButton112,
            R.id.toggleButton113,
            R.id.toggleButton114,
            R.id.toggleButton115,
            R.id.toggleButton116,
            R.id.toggleButton121,
            R.id.toggleButton122,
            R.id.toggleButton123,
            R.id.toggleButton124,
            R.id.toggleButton125,
            R.id.toggleButton126
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Integer> tags = new ArrayList();
        for(int x=0; x<13; x+=1){
            for(int y=1; y<7; y+=1){
                String tag = Integer.toString(x) + Integer.toString(y);
                tags.add(Integer.parseInt(tag));
            }
        }
        lv = (ListView) findViewById(R.id.listView);
        tv = (TextView) findViewById(R.id.txtNumOfScales);
        buttons = new ArrayList<ToggleButton>();

        int x = 0;
        for(int id:BUTTON_IDS){
            ToggleButton button = (ToggleButton)findViewById(id);
            button.setTag(tags.get(x));
            buttons.add(button);
            x+=1;
        }
    }

    public void buttonOnClick(View v){
        int tagInt = (Integer)v.getTag();
        if(notesInput.contains(tagInt)){
            notesInput.remove(notesInput.indexOf(tagInt));
        }else {
            notesInput.add(tagInt);
        }



        if(notesInput.size()>=3){
            ArrayList<String> ms;

            ms = notesData.areNotesInScale(notesInput);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    ms);
            lv.setVisibility(View.VISIBLE);
            lv.setAdapter(adapter);
            tv.setText(Integer.toString(ms.size())+" scales found");
        }else{
            lv.setVisibility(View.GONE);
            tv.setText("Enter at least 3 notes on the fretboard above");
        }

        //print notesinput
        String listString = "";
        for(int i : notesInput){
            listString+= Integer.toString(i) + " ";
        }
        //Log.d("onClick",listString);
        //Log.d("onClick", v.getTag().toString());
    }
}

