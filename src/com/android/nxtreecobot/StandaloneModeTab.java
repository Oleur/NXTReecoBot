package com.android.nxtreecobot;

import com.android.nxtreecobot.remoteControl.Remote;
import com.android.nxtreecobot.standalone.DrivingLabyrinthStandalone;
import com.android.nxtreecobot.standalone.WorldExplorationStandalone;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class StandaloneModeTab extends Activity {
	
	private TabHost tabhost;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_labyrinth);
        
        /*tabhost = getTabHost();
        
        //First tab to drive a virtual NXT.
        TabSpec driveNXTspec = tabhost.newTabSpec("Driving");
        driveNXTspec.setIndicator("Driving", getResources().getDrawable(R.drawable.ic_tab_remote));
        Intent driveIntent = new Intent(this, DrivingLabyrinthStandalone.class);
        driveNXTspec.setContent(driveIntent);
        
        //Second tab where the NXT will explore a map (labyrinth or continuous).
        TabSpec exploreSpec = tabhost.newTabSpec("Exploration");
        exploreSpec.setIndicator("Exploration", getResources().getDrawable(R.drawable.ic_menu_map));
        Intent exploreIntent = new Intent(this, WorldExplorationStandalone.class);
        exploreSpec.setContent(exploreIntent);
        
        //Adding the tabs to the tabhost.
        tabhost.addTab(driveNXTspec);
        tabhost.addTab(exploreSpec);*/
        
	}

}
