package com.android.nxtreecobot.remoteControl;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.android.nxtreecobot.R;

public class LabyrinthExploreRemote extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labyrinth_explore_remote);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_labyrinth_explore_remote, menu);
        return true;
    }

    
}
