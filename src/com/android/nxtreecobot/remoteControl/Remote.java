package com.android.nxtreecobot.remoteControl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.nxtreecobot.R;

public class Remote extends Activity implements OnClickListener, OnLongClickListener, OnCheckedChangeListener {
	
	private ToggleButton activeBT;
	private ImageButton robotForward;
	private ImageButton robotBackward;
	private ImageButton robotATurn;
	private ImageButton robotTLeft;
	private ImageButton robotTRight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        
        activeBT = (ToggleButton) findViewById(R.id.connecBluetooth);
        robotForward = (ImageButton) findViewById(R.id.remoteForward);
        robotBackward = (ImageButton) findViewById(R.id.remoteBackward);
        robotTLeft = (ImageButton) findViewById(R.id.remoteTLeft);
        robotTRight = (ImageButton) findViewById(R.id.remoteTRight);
        robotATurn = (ImageButton) findViewById(R.id.aboutTurn);
        
        //Adding the listener to the buttons.
        activeBT.setOnCheckedChangeListener(this);
        robotForward.setOnClickListener(this);
        robotBackward.setOnClickListener(this);
        robotTLeft.setOnClickListener(this);
        robotTRight.setOnClickListener(this);
        robotATurn.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.remoteForward:
			//TODO: Make the robot going forward
			break;
		case R.id.remoteBackward:
			//TODO: Make the robot going backward.
			break;
		case R.id.remoteTLeft:
			//TODO: Make the robot turning on the left.
			break;
		case R.id.remoteTRight:
			//TODO: Make the robot turning on the right.
			break;
		case R.id.aboutTurn:
			//TODO: Make a U-turn.
			break;
		}
	}

	@Override
	public boolean onLongClick(View arg0) {
		switch(arg0.getId()) {
		case R.id.remoteForward:
			//TODO: Make the robot going forward
			break;
		case R.id.remoteBackward:
			//TODO: Make the robot going backward.
			break;
		case R.id.remoteTLeft:
			//TODO: Make the robot turning on the left.
			break;
		case R.id.remoteTRight:
			//TODO: Make the robot turning on the right.
			break;
		case R.id.aboutTurn:
			//TODO: Make a U-turn.
			break;
		}
		return false;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch(buttonView.getId()) {
		case R.id.connecBluetooth:
			if (isChecked) {
				//TODO: Bluetooth connection
				Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Bluetooth shut down", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
