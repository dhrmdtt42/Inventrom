package com.dharam.rdinventrom;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.File;

/**
 * Created by Dharam on 12/08/2017.
 * Fragment that handles and displays all the file names as a list, present in the user designated folder
 */

public class ShowList extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.showlist, container, false);
        TextView tv=(TextView)rootView.findViewById(R.id.text_v);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/RDInventrom".toString();  // path where files have been saved

        File f = new File(path);
        if (!f.exists ())   //if directory doesn't exist then create it
            f.mkdir();
        File file[] = f.listFiles();
        StringBuffer buffer = new StringBuffer();
        for (int i = file.length-2; i >=0; i--) {
            buffer.append(file[i].getName());                       //add names of the files to the sting buffer
            buffer.append(System.getProperty("line.separator"));    // returns a new line
            buffer.append(System.getProperty("line.separator"));

            Log.d("Files", "FileName:" + file[i].getName());        //logging
        }
        tv.setText(buffer);                                         // buffer is saved to TEXTVIEW

        return rootView;
    }
}
