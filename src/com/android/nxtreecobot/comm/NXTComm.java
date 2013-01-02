package com.android.nxtreecobot.comm;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.widget.Toast;

public class NXTComm {
	
	//Bluetooth variables
    private BluetoothSocket nxtBTsocket = null;
    private DataOutputStream nxtDos = null;
	private Context context;
	
	//Constants for moving the NXT and get sensor values.
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
    public static final int ACTION = 10;
    public static final int DISCONNECT = 99;

    public static enum Direction {
    	NORTH, 
    	SOUTH, 
    	EAST,
    	WEST
    }
	
	public NXTComm(Context context) {
		this.context = context;
	}
	
	public void destroyNXTConnexion() {
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
            Toast toast = Toast.makeText(context, "Problem at closing the connection", Toast.LENGTH_SHORT);
            toast.show();
        }
	}

	public void sendNXTcommand(int command, int value) {
		if (nxtDos == null) {
            return;
        }

        try {
            nxtDos.writeInt(command);
            nxtDos.writeInt(value);
            nxtDos.flush();
        } catch (IOException ioe) { 
            Toast toast = Toast.makeText(context, "Problem at writing command", Toast.LENGTH_SHORT);
            toast.show();            
        }
	}
	
	public Integer readMessage(String nxt_name){
	    BluetoothSocket connSock;
	    int n;
	    
	    if(nxt_name.equals(NXT_NAME)){
	        connSock=nxtBTsocket;
	    } else {
	        connSock = null;
	    }

	    if(connSock != null){
	        try {
	            //InputStreamReader in = new InputStreamReader(nxtBTsocket.getInputStream());
	            nxtBTsocket.getInputStream().available();
	            n = nxtBTsocket.getInputStream().read();
	            System.out.println("plop: "+n);
	            return n;
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            return -1;
	        }
	    }else{
	        //Error
	        return -1;
	    }
	}

	public void createNXTConnexion() {
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
         
            
            Toast.makeText(context, "Size: "+bondedDevices.size(), Toast.LENGTH_SHORT).show();
            for (BluetoothDevice bluetoothDevice : bondedDevices) {
            	Toast.makeText(context, "Name: "+bluetoothDevice.getName(), Toast.LENGTH_SHORT).show();
                if (bluetoothDevice.getName().equals(NXT_NAME)) {
                    nxtDevice = bluetoothDevice;
                    Toast.makeText(context, "NXT: "+NXT_NAME, Toast.LENGTH_SHORT).show();
                    break;
                }
            } 

            if (nxtDevice == null) {
                Toast toast = Toast.makeText(context, "No paired NXT device found", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }             

            nxtBTsocket = nxtDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            nxtBTsocket.connect();
            nxtDos = new DataOutputStream(nxtBTsocket.getOutputStream());
        } catch (IOException e) {
            Toast toast = Toast.makeText(context, "Problem at creating a connection", Toast.LENGTH_SHORT);
            toast.show();
        }
	}

}
