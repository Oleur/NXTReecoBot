package com.android.nxtreecobot.remoteControl;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.ExportedProperty;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.android.nxtreecobot.R;
import com.android.nxtreecobot.standalone.LabyrinthView;

public class LabyrinthExploreRemote extends Activity implements OnClickListener, OnCheckedChangeListener {
	
	private ToggleButton expConnecBT;
	private ToggleButton exploreLaby;
	private Button driveF;
	private Button driveB;
	private Button driveR;
	private Button driveL;
	private Button stopBot;
	private LabyrinthView labyrinth;
	
	//Constant for moving the NXT.
	public static final String NXT_NAME = "GI_10"; 
    public static final int MOTOR_A_C_STOP = 0;
    public static final int MOTOR_A_FORWARD = 1;
    public static final int MOTOR_A_BACKWARD = 2;
    public static final int MOTOR_C_FORWARD = 3;
    public static final int MOTOR_C_BACKWARD = 4;
    public static final int MOTOR_A_STOP = 5;
    public static final int MOTOR_C_STOP = 6;
    public static final int MOTOR_A_C_FORWARD = 7;
    public static final int ACTION = 10;
    public static final int DISCONNECT = 99;   

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labyrinth_explore_remote);
        
        expConnecBT = (ToggleButton) findViewById(R.id.expBT);
    	exploreLaby = (ToggleButton) findViewById(R.id.exploreButton);
    	exploreLaby.setEnabled(false);
    	
    	driveF = (Button) findViewById(R.id.REGoForward);
    	driveB = (Button) findViewById(R.id.REGoBackward);
    	driveR = (Button) findViewById(R.id.RETurnRight);
    	driveL = (Button) findViewById(R.id.RETurnLeft);
    	stopBot = (Button) findViewById(R.id.REStopBot);
    	labyrinth = (LabyrinthView) findViewById(R.id.RElabyrinthView);
    	
    	expConnecBT.setOnCheckedChangeListener(this);
    	exploreLaby.setOnCheckedChangeListener(this);
    	driveF.setOnClickListener(this);
    	driveB.setOnClickListener(this);
    	driveL.setOnClickListener(this);
    	driveR.setOnClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_labyrinth_explore_remote, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch(buttonView.getId()) {
		case R.id.expBT:
			if (isChecked) {
				exploreLaby.setEnabled(true);
				
			} else {
				exploreLaby.setEnabled(false);
			}
			break;
		case R.id.exploreButton:
			//TODO: Send order to the NXT to explore the labyrinth.
			if (isChecked) {
				exploreLaby.setEnabled(true);
			} else {
				exploreLaby.setEnabled(false);
			}
		}
		
	}

    
}
