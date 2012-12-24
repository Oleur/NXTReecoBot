package com.android.nxtreecobot.standalone;

import java.io.DataOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.drm.DrmEvent;
import android.drm.DrmManagerClient;
import android.drm.DrmManagerClient.OnEventListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;

import com.android.nxtreecobot.R;
import com.android.nxtreecobot.comm.NXTComm.Direction;

public class DrivingLabyrinthStandalone extends Activity implements OnClickListener {
	
	//Custom view and button to move the robot.
	private LabyrinthView labyrinth = null;
	private Button explore;
	private Button forward;
	private Button backward;
	private Button left;
	private Button right;
	private Direction direction;
	
    //Constants for matching the right piece of labyrinth with respect to the robot's perceptions.
    public static final int OBJECT_CULDESAC = 100;
    public static final int OBJECT_RIGHT_FRONT = 101;
    public static final int OBJECT_RIGHT_LEFT = 102;
    public static final int OBJECT_LEFT_FRONT = 103;
    public static final int OBJECT_FRONT = 104;
    public static final int OBJECT_LEFT = 105;
    public static final int OBJECT_RIGHT = 106;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_driving_labyrinth);
        
        explore = (Button) findViewById(R.id.autoNavButton);
        forward = (Button) findViewById(R.id.labyGoForward);
        backward = (Button) findViewById(R.id.labyGoBackward);
        left = (Button) findViewById(R.id.labyTurnLeft);
        right = (Button) findViewById(R.id.labyTurnRight);
        labyrinth = (LabyrinthView) findViewById(R.id.labyrinthView);
        
        explore.setOnClickListener(this);
        forward.setOnClickListener(this);
        backward.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        
        direction = Direction.EAST;
    }

	@Override
	public void onClick(View v) {
		Canvas c = new Canvas();
		switch (v.getId()) {
		case R.id.labyGoForward:
			if (direction == Direction.NORTH) {
				//Move forward to the north.
				labyrinth.moveRobot(c, 1, Direction.NORTH);
				labyrinth.setFlagBot(1);
			} else if (direction == Direction.SOUTH) {
				//Move forward to the south.
				labyrinth.moveRobot(c, 1, Direction.SOUTH);
				labyrinth.setFlagBot(1);
			} else if (direction == Direction.EAST) {
				//Move forward to the east
				labyrinth.moveRobot(c, 1, Direction.EAST);
				labyrinth.setFlagBot(1);
			} else if (direction == Direction.WEST) {
				//Move forward to the west.
				labyrinth.moveRobot(c, 1, Direction.WEST);
				labyrinth.setFlagBot(1);
			}
			break;
		case R.id.labyGoBackward:
			labyrinth.moveRobot(c, 2, Direction.NORTH);
			labyrinth.setFlagBot(2);
			break;
		case R.id.labyTurnRight:
			labyrinth.moveRobot(c, 3, Direction.NORTH);
			labyrinth.setFlagBot(3);
			break;
		case R.id.labyTurnLeft:
			if (direction == Direction.NORTH) {
				//Move forward to the north.
				labyrinth.moveRobot(c, 4, Direction.NORTH);
				labyrinth.setFlagBot(4);
			} else if (direction == Direction.SOUTH) {
				//Move forward to the south.
				labyrinth.moveRobot(c, 4, Direction.SOUTH);
				labyrinth.setFlagBot(4);
			} else if (direction == Direction.EAST) {
				//Move forward to the east
				labyrinth.moveRobot(c, 4, Direction.EAST);
				labyrinth.setFlagBot(4);
			} else if (direction == Direction.WEST) {
				//Move forward to the west.
				labyrinth.moveRobot(c, 4, Direction.WEST);
				labyrinth.setFlagBot(4);
			}
			break;
		}
	}
    
}
