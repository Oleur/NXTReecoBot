package com.android.nxtreecobot.standalone;

import com.android.nxtreecobot.R;
import com.android.nxtreecobot.R.layout;
import com.android.nxtreecobot.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class WorldExplorationStandalone extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world_exploration_standalone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_world_exploration_standalone, menu);
        return true;
    }

    
}
