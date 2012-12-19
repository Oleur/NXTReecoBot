package com.android.nxtreecobot.remoteControl;

import java.io.DataOutputStream;
import java.io.IOException;
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
			sendNXTcommand(MOTOR_A_C_FORWARD, 180);
			break;
		case R.id.remoteBackward:
			sendNXTcommand(MOTOR_A_BACKWARD, 180);
			sendNXTcommand(MOTOR_C_BACKWARD, 180);
			break;
		case R.id.remoteTLeft:
			if (flagMove == 0) {
				sendNXTcommand(MOTOR_A_FORWARD, 180);
				sendNXTcommand(MOTOR_C_STOP, 0);
				flagMove = 1;
			} else {
				sendNXTcommand(MOTOR_A_FORWARD, 180);
			}
			break;
		case R.id.remoteTRight:
			if (flagMove == 0) {
				sendNXTcommand(MOTOR_C_FORWARD, 180);
				sendNXTcommand(MOTOR_A_STOP, 0);
				flagMove = 1;
			} else {
				sendNXTcommand(MOTOR_C_FORWARD, 180);
			}
			break;
		case R.id.aboutTurn:
			//TODO: Make it stop.
            sendNXTcommand(MOTOR_A_C_STOP,0);
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
					createNXTConnexion();
				} else {
					destroyNXTConnexion();
				}
			} else {
				destroyNXTConnexion();
				Toast.makeText(this, "Bluetooth shut down", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void destroyNXTConnexion() {
		try {
            if (nxtBTsocket != null) {
                // send one close message 
                sendNXTcommand(MOTOR_A_C_STOP,0);
                sendNXTcommand(DISCONNECT,0);
                nxtBTsocket.close();
                nxtBTsocket = null;
            }
            nxtDos = null;            
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Problem at closing the connection", Toast.LENGTH_SHORT);
            toast.show();
        }
	}

	private void sendNXTcommand(int command, int value) {
		if (nxtDos == null) {
            return;
        }

        try {
            nxtDos.writeInt(command);
            nxtDos.writeInt(value);
            nxtDos.flush();
        } catch (IOException ioe) { 
            Toast toast = Toast.makeText(this, "Problem at writing command", Toast.LENGTH_SHORT);
            toast.show();            
        }
	}

	private void createNXTConnexion() {
		try {
            BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
            //If Bluetooth not enable then do it
            /*if(btAdapter.isEnabled()==false){
                btAdapter.enable();
                while(!(btAdapter.isEnabled())){
                	
                }
            }
            BluetoothDevice nxt_device = btAdapter.getRemoteDevice("00:16:53:08:F4:E5");
            */
            Set<BluetoothDevice> bondedDevices = btAdapter.getBondedDevices();
            BluetoothDevice nxtDevice = null;
         
            
            Toast.makeText(this, "Size: "+bondedDevices.size(), Toast.LENGTH_SHORT).show();
            for (BluetoothDevice bluetoothDevice : bondedDevices) {
            	Toast.makeText(this, "Name: "+bluetoothDevice.getName(), Toast.LENGTH_SHORT).show();
                if (bluetoothDevice.getName().equals(NXT_NAME)) {
                    nxtDevice = bluetoothDevice;
                    Toast.makeText(this, "NXT: "+NXT_NAME, Toast.LENGTH_SHORT).show();
                    break;
                }
            } 

            if (nxtDevice == null) {
                Toast toast = Toast.makeText(this, "No paired NXT device found", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }             

            nxtBTsocket = nxtDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            nxtBTsocket.connect();
            nxtDos = new DataOutputStream(nxtBTsocket.getOutputStream());
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Problem at creating a connection", Toast.LENGTH_SHORT);
            toast.show();
        }
	}

}
