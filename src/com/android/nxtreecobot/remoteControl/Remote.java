package com.android.nxtreecobot.remoteControl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
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
import com.android.nxtreecobot.comm.NXTComm;

public class Remote extends Activity implements OnClickListener, OnLongClickListener, OnCheckedChangeListener {
	
	//UI buttons.
	private ToggleButton activeBT;
	private ImageButton robotForward;
	private ImageButton robotBackward;
	private ImageButton robotStop;
	private ImageButton robotTLeft;
	private ImageButton robotTRight;
	
	//Captor about the NXT.
	public static final String NXT_NAME = "GI_10"; 
    public static final int MOTOR_A_C_STOP = 0;
    public static final int MOTOR_A_FORWARD = 1;
    public static final int MOTOR_A_BACKWARD = 2;
    public static final int MOTOR_C_FORWARD = 3;
    public static final int MOTOR_C_BACKWARD = 4;
    public static final int MOTOR_A_STOP = 5;
    public static final int MOTOR_C_STOP = 6;
    public static final int MOTOR_A_C_FORWARD = 7;
    public static final int TACHOCOUNT_RESET = 8;
    public static final int TACHOCOUNT_READ = 9;
    public static final int ACTION=10;
    public static final int DISCONNECT = 99;   
    
    //Bluetooth variables
    private BluetoothSocket nxtBTsocket = null;
    private DataOutputStream nxtDos = null;
    private NXTComm nxtComm = null;
    private long timeDataSent = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        
        activeBT = (ToggleButton) findViewById(R.id.connecBluetooth);
        robotForward = (ImageButton) findViewById(R.id.remoteForward);
        robotBackward = (ImageButton) findViewById(R.id.remoteBackward);
        robotTLeft = (ImageButton) findViewById(R.id.remoteTLeft);
        robotTRight = (ImageButton) findViewById(R.id.remoteTRight);
        robotStop = (ImageButton) findViewById(R.id.aboutTurn);
        
        //Creation of the BT communication with the NXT.
        nxtComm = new NXTComm(this);
        
        //Adding the listener to the buttons.
        activeBT.setOnCheckedChangeListener(this);
        robotForward.setOnClickListener(this);
        robotBackward.setOnClickListener(this);
        robotTLeft.setOnClickListener(this);
        robotTRight.setOnClickListener(this);
        robotStop.setOnClickListener(this);
        
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		int flagMove = 1;
		switch(v.getId()) {
		case R.id.remoteForward:
			flagMove = 0;
			nxtComm.sendNXTcommand(MOTOR_A_C_FORWARD, 180);
			break;
		case R.id.remoteBackward:
			nxtComm.sendNXTcommand(MOTOR_A_BACKWARD, 180);
			nxtComm.sendNXTcommand(MOTOR_C_BACKWARD, 180);
			break;
		case R.id.remoteTLeft:
			if (flagMove == 0) {
				nxtComm.sendNXTcommand(MOTOR_A_FORWARD, 180);
				nxtComm.sendNXTcommand(MOTOR_C_STOP, 0);
				flagMove = 1;
			} else {
				nxtComm.sendNXTcommand(MOTOR_A_FORWARD, 180);
			}
			break;
		case R.id.remoteTRight:
			if (flagMove == 0) {
				nxtComm.sendNXTcommand(MOTOR_C_FORWARD, 180);
				nxtComm.sendNXTcommand(MOTOR_A_STOP, 0);
				flagMove = 1;
			} else {
				nxtComm.sendNXTcommand(MOTOR_C_FORWARD, 180);
			}
			break;
		case R.id.aboutTurn:
			//TODO: Make it stop.
			nxtComm.sendNXTcommand(MOTOR_A_C_STOP,0);
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
				if (nxtBTsocket == null) {
					nxtComm.createNXTConnexion();
				} else {
					nxtComm.destroyNXTConnexion();
				}
			} else {
				nxtComm.destroyNXTConnexion();
				Toast.makeText(this, "Bluetooth shut down", Toast.LENGTH_SHORT).show();
			}
		}
	}

}
