package com.android.nxtreecobot;

import com.android.nxtreecobot.remoteControl.Remote;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class RemoteControlTab extends TabActivity {
	
private TabHost tabhost;
	
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tab_standalone);
        
        tabhost = getTabHost();
        
        //First tab to control manually the NXT robot.
        TabSpec tabRCspec = tabhost.newTabSpec("Remote Control");
        tabRCspec.setIndicator("Remote Control");
        Intent remote = new Intent(this, Remote.class);
        tabRCspec.setContent(remote);
        
        //Second tab which launch the NXT in the labyrinth exploration mode.
        TabSpec tabExploreSpec = tabhost.newTabSpec("Labyrinth Exploration");
        tabExploreSpec.setIndicator("Labyrinth Exploration");
        Intent remoteExploreIntent = new Intent(this, Remote.class);
        tabExploreSpec.setContent(remoteExploreIntent);
        
        //Third tab which launch the NXT in the world exploration mode.
        TabSpec tabContinuousWorldSpec = tabhost.newTabSpec("World Exploration");
        tabContinuousWorldSpec.setIndicator("World Exploration");
        Intent tabCWordIntent = new Intent(this, Remote.class);
        tabContinuousWorldSpec.setContent(tabCWordIntent);
        
        tabhost.addTab(tabRCspec);
        tabhost.addTab(tabExploreSpec);
        tabhost.addTab(tabContinuousWorldSpec);
        
	}

}
