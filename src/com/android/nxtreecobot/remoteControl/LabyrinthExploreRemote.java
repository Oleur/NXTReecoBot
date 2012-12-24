package com.android.nxtreecobot.remoteControl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.android.nxtreecobot.comm.NXTComm;
import com.android.nxtreecobot.comm.NXTComm.Direction;
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
	private Direction robotDir;
	
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
    
    //Constants for matching the right piece of labyrinth with respect to the robot's perceptions.
    public static final int OBJECT_CULDESAC = 100;
    public static final int OBJECT_RIGHT_FRONT = 101;
    public static final int OBJECT_RIGHT_LEFT = 102;
    public static final int OBJECT_LEFT_FRONT = 103;
    public static final int OBJECT_FRONT = 104;
    public static final int OBJECT_LEFT = 105;
    public static final int OBJECT_RIGHT = 106;
    
    //Nxt bluetooth connection
    private NXTComm remoteBTco = null;

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
    	
    	//Init BT connection
    	remoteBTco = new NXTComm(this);
    	
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
		int flagMove = 1;
		switch (v.getId()) {
		case R.id.remoteForward:
			remoteBTco.sendNXTcommand(MOTOR_A_C_FORWARD, 180);
			break;
		case R.id.REGoBackward:
			remoteBTco.sendNXTcommand(MOTOR_A_BACKWARD, 180);
			remoteBTco.sendNXTcommand(MOTOR_C_BACKWARD, 180);
			break;
		case R.id.RETurnLeft:
			if (flagMove == 0) {
				remoteBTco.sendNXTcommand(MOTOR_A_FORWARD, 180);
				remoteBTco.sendNXTcommand(MOTOR_C_STOP, 0);
				flagMove = 1;
			} else {
				remoteBTco.sendNXTcommand(MOTOR_A_FORWARD, 180);
			}
			break;
		case R.id.RETurnRight:
			if (flagMove == 0) {
				remoteBTco.sendNXTcommand(MOTOR_C_FORWARD, 180);
				remoteBTco.sendNXTcommand(MOTOR_A_STOP, 0);
				flagMove = 1;
			} else {
				remoteBTco.sendNXTcommand(MOTOR_C_FORWARD, 180);
			}
			break;
		case R.id.REStopBot:
			remoteBTco.sendNXTcommand(MOTOR_A_C_STOP, 0);
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch(buttonView.getId()) {
		case R.id.expBT:
			if (isChecked) {
				exploreLaby.setEnabled(true);
				remoteBTco.createNXTConnexion();
				openDirectionDialog();
			} else {
				exploreLaby.setEnabled(false);
			}
			break;
		case R.id.exploreButton:
			//TODO: Send order to the NXT to explore the labyrinth.
			if (isChecked || expConnecBT.isChecked()) {
				exploreLaby.setEnabled(true);
			} else {
				exploreLaby.setEnabled(false);
			}
		}
		
	}
	
	private void openDirectionDialog() {
    	new AlertDialog.Builder(this)
    	.setTitle("Choose robot direction")
    	.setItems(R.array.direction_menu, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				// Set direction to NORTH
				case 0:
					robotDir = Direction.NORTH;
					break;
				// Set direction to SOUTH
				case 1:
					robotDir = Direction.SOUTH;
					break;
				// Set direction to EAST
				case 2:
					robotDir = Direction.EAST;
					break;
				// Set direction to WEST
				case 3:
					robotDir = Direction.WEST;
					break;
				}
			}
		}).show();
    }

    
}
