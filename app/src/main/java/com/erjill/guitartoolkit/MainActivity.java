package com.erjill.guitartoolkit;

import android.content.Intent;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.onClick;

public class MainActivity extends AppCompatActivity{
    NotesData notesData = new NotesData();
    private List<ToggleButton> buttons;
    private ArrayList<Integer> notesInput = new ArrayList();

    ListView lv;
    TextView tv;
    Button clearButton;
    SlidingUpPanelLayout layout;

    ArrayList<String> ms;
    ArrayAdapter<String> adapter;

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

        layout= (SlidingUpPanelLayout)findViewById(R.id.myPanel);
        layout.setScrollableView(findViewById (R.id.listView));
        layout.setFadeOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        //Store views to variables
        lv = (ListView) findViewById(R.id.listView);
        tv = (TextView) findViewById(R.id.txtNumOfScales);
        buttons = new ArrayList<ToggleButton>();
        clearButton = (Button) findViewById(R.id.btnClear);

        //Generate tags to be applied to buttons. Tags are formated 00, fret number then string number
        ArrayList<Integer> tags = new ArrayList();
        for(int x=0; x<13; x+=1){
            for(int y=1; y<7; y+=1){
                String tag = Integer.toString(x) + Integer.toString(y);
                tags.add(Integer.parseInt(tag));
            }
        }

        //Create the buttons, apply tag
        int x = 0;
        for(int id:BUTTON_IDS) {
            ToggleButton button = (ToggleButton) findViewById(id);
            button.setTag(tags.get(x));
            buttons.add(button);
            x += 1;
        }

        displayAllScales();

        //Togggle Buttons Listener
        CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Initial check if user input
                if (buttonView.isPressed()) {
                    int tagInt = (Integer) buttonView.getTag();
                    if (notesInput.contains(tagInt) && !buttonView.isChecked()) {
                        Log.d("remove", "removed");
                        notesInput.remove(notesInput.indexOf(tagInt));
                    } else if (buttonView.isChecked()) {
                        Log.d("add", "Added");
                        notesInput.add(tagInt);
                    }


                    if (notesInput.size() >= 3) {
                        ms = notesData.areNotesInScale(notesInput);
                        adapter = new ArrayAdapter<String>(
                                MainActivity.this,
                                android.R.layout.simple_list_item_1,
                                ms);

                        lv.setVisibility(View.VISIBLE);
                        lv.setAdapter(adapter);
                        tv.setText(Integer.toString(ms.size()) + " scales found");
                    } else {
                        displayAllScales();
                        tv.setText("Enter at least 3 notes on the fretboard above");
                    }
                }
            }
        };

        //Add all toggle buttons to listener
        for(ToggleButton b : buttons) {
            b.setOnCheckedChangeListener(multiListener);
        }

        //ListView Listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resetButtons();
                String item = ((TextView)view).getText().toString();
                //ListView is populated with textviews. getText() includes the scale name and notes
                //Split is used to get the scale name which will be used to find the scale in notesData.currScales<String,ArrayList>
                String[] sep = item.split(" : ");
                ArrayList<Integer> s = notesData.currScales.get(sep[0]);

                for(ToggleButton b : buttons){
                    int tag = (Integer)b.getTag();
                    int buttonNumNote = notesData.convertToNumNote(tag);
                    b.setClickable(false);

                    if(s.contains(buttonNumNote)){
                        if(buttonNumNote == s.get(0)){
                            b.setBackgroundResource(R.drawable.root);
                        }
                        b.setChecked(true);

                    }

                }

                String listString = "";
                for (int i : s){
                    listString += Integer.toString(i)+" ";
                }
            }
        });

    }

    void displayAllScales(){
        adapter = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                notesData.allScalesNotes);
        lv.setVisibility(View.VISIBLE);
        lv.setAdapter(adapter);
    }

    public void clearButtons(View v){

       resetButtons();

        displayAllScales();

        tv.setText("Enter at least 3 notes on the fretboard above");
        notesInput.clear();
    }

    void resetButtons(){
        for(ToggleButton b : buttons){
            b.setChecked(false);
            b.setClickable(true);
            b.setEnabled(true);
            b.setBackgroundResource(R.drawable.toggle_selector);
        }

        tv.setText("Enter at least 3 notes on the fretboard above");
        notesInput.clear();
    }
}

