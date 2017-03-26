package com.erjill.guitartoolkit;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Erjill on 24-Mar-17.
 */

public class NotesData {


    public List<String> notes = Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B");

    //Scales will store the main template i.e for C major
    int[] major = {1, 3, 5, 6, 8, 10, 12};
    int[] minor = {1, 3, 4, 6, 8, 9, 11};
    int[] majorPentatonic = {1, 3, 5, 8, 10};
    int [] minorPentatonic = {1, 4, 6, 8, 11};
    int[] blues = {1, 4, 6, 7, 8, 11};

    Map<String, int[]> scales = new LinkedHashMap<String, int[]>();

    //currScales will store the scales after applying the base scales template to the other root notes
    Map<String,ArrayList> currScales = new LinkedHashMap<String,ArrayList>();

    //Store all scales as their string value
    ArrayList<String> allScalesNotes = new ArrayList();

    //Store the key of the matched scales from the user input
    //Used to display on drawer
    ArrayList<String> matchedScales = new ArrayList();


    //tuning form highest string to lowest
    int[] tuning = {5,12,8,3,10,5};

    public NotesData(){

        //Store scale template to scales HashMap

        scales.put("Major",major);
        scales.put("Minor",minor);
        scales.put("Major Pentatonic",majorPentatonic);
        scales.put("Minor Pentatonic",minorPentatonic);
        scales.put("Blues",blues);

        //Notes that user will input
        //Hardcode for testing
        //notesInput.add(1);
        //notesInput.add(5);
        //notesInput.add(8);

        populateNotesList();
    }


    public ArrayList<String> areNotesInScale(ArrayList<Integer> notesInput){

        matchedScales.clear();
        //Convert to number notes as notesInput is formated XY, where X is fret number and Y is string number
        ArrayList<Integer> notesInputConverted = new ArrayList();
        for(int i:notesInput){
            notesInputConverted.add(convertToNumNote(i));
        }

        ArrayList<String> noteInputToString = convertToNotes(notesInputConverted);

        String listString = "";
        for (String s : noteInputToString){
            listString += s+" ";
        }

        Log.d("notesInput",listString);

        for(Map.Entry<String,ArrayList> entry: currScales.entrySet()){
            ArrayList iterScale = entry.getValue();


            if(iterScale.containsAll(notesInputConverted)){
                String ls = "";
                ArrayList<String> temp = convertToNotes(entry.getValue());
                for (String s : temp){
                    ls += s+" ";
                }

                matchedScales.add(entry.getKey() +" : "+ ls);
                Log.d("isInScale",entry.getKey() +" "+ ls);
            }
        }

        return matchedScales;
    }

    //Convert button tags e.g 01 (fret, string) to number notes e.g 1 for C, 2 for C#
    public int convertToNumNote(int i){
        int temp;
        temp = (tuning[i%10 -1] + i/10);
        temp = temp>12? temp%12:temp;
        temp = temp==0? 12:temp;
        return temp;
    }

    public ArrayList convertToNotes(ArrayList<Integer> al){
        ArrayList<String> g = new ArrayList();
        for(int x = 0; x<al.size(); x+=1){
            g.add(x,notes.get((al.get(x))-1));
        }

        return g;
    }

    public void getNotes(int n){

        for(Map.Entry<String,int[]> entry:scales.entrySet()){
            ArrayList<Integer> rootMajor = new ArrayList();
            int[] temp = entry.getValue();
            for (int x= 0; x<temp.length;x+=1){
                int j = ((temp[x]+n)<13)? temp[x]+n : temp[x]+ n - 12;
                rootMajor.add(j);
            }

            String listString = "";
            for (int s : rootMajor){
                listString += Integer.toString(s)+" ";
            }
            currScales.put(notes.get(rootMajor.get(0)-1)+" " + entry.getKey(),rootMajor);
            //Log.d("a",entry.getKey()+Arrays.toString(entry.getValue()));
        }
    }

    public void populateNotesList(){
        for(int x=0; x<12; x += 1){
            getNotes(x);
        }

        for(Map.Entry<String,ArrayList> entry:currScales.entrySet()){

            String listString = "";
            ArrayList<Integer> temp = new ArrayList();
            temp = entry.getValue();

            ArrayList<String> tempString = new ArrayList();
            tempString = convertToNotes(temp);

            for (String s : tempString){
                listString += s+" ";
            }
            allScalesNotes.add(entry.getKey()+ " : " + listString);
        }

        for(String i : allScalesNotes){
            Log.d("allScalesNotes",i);
        }
    }

}



