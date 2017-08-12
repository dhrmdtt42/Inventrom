package com.dharam.rdinventrom;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Dharam on 8/11/2017.
 */

public class ShowList extends Fragment{

    TextView text_v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.showlist, container, false);

        text_v = (TextView)rootView.findViewById(R.id.text_v);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Inventrom".toString();
        File f = new File(path);
        File file[] = f.listFiles();
        StringBuffer stringBuffer = new StringBuffer();
        int i;
        for(i=0; i>file.length-1;i++);
        {
            stringBuffer.append(file[i].getName());
            stringBuffer.append(System.getProperty("line.seperator"));
            stringBuffer.append(System.getProperty("line.seperator"));
         //   Log.d("Files","Filename:",+)
        }
        text_v.setText(stringBuffer);
        return rootView;
    }
}
