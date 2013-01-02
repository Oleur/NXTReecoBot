package com.android.nxtreecobot.standalone;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.android.nxtreecobot.LabyrinthView;
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
	private int objectType = 0;
	private List<Integer> listOT = null;

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
        
        //Init direction and laby object type
        direction = Direction.NORTH;
        objectType = OBJECT_RIGHT_LEFT;
        
        //Example for the labyrinth exploration.
        listOT = new ArrayList<Integer>();
        listOT.add(OBJECT_RIGHT_LEFT);
        listOT.add(OBJECT_RIGHT_LEFT);
        listOT.add(OBJECT_RIGHT_FRONT);
    }

	@Override
	public void onClick(View v) {
		Canvas c = new Canvas();
		objectType = getObjectTypeStandalone();
		switch (v.getId()) {
		case R.id.labyGoForward:
			if (direction == Direction.NORTH) {
				//Move forward to the north.
				labyrinth.moveRobot(c, 1, Direction.NORTH, objectType);
				labyrinth.setFlagBot(1);
			} else if (direction == Direction.SOUTH) {
				//Move forward to the south.
				labyrinth.moveRobot(c, 1, Direction.SOUTH, objectType);
				labyrinth.setFlagBot(1);
			} else if (direction == Direction.EAST) {
				//Move forward to the east
				labyrinth.moveRobot(c, 1, Direction.EAST, objectType);
				labyrinth.setFlagBot(1);
			} else if (direction == Direction.WEST) {
				//Move forward to the west.
				labyrinth.moveRobot(c, 1, Direction.WEST, objectType);
				labyrinth.setFlagBot(1);
			}
			break;
		case R.id.labyGoBackward:
			if (direction == Direction.NORTH) {
				//Move backward to the north.
				labyrinth.moveRobot(c, 2, Direction.NORTH, objectType);
				labyrinth.setFlagBot(2);
			} else if (direction == Direction.SOUTH) {
				//Move forward to the south.
				labyrinth.moveRobot(c, 2, Direction.SOUTH, objectType);
				labyrinth.setFlagBot(2);
			} else if (direction == Direction.EAST) {
				//Move forward to the east
				labyrinth.moveRobot(c, 2, Direction.EAST, objectType);
				labyrinth.setFlagBot(2);
			} else if (direction == Direction.WEST) {
				//Move forward to the west.
				labyrinth.moveRobot(c, 2, Direction.WEST, objectType);
				labyrinth.setFlagBot(2);
			}
			break;
		case R.id.labyTurnRight:
			if (direction == Direction.NORTH) {
				//Move on the right and change of direction
				labyrinth.moveRobot(c, 3, direction, objectType);
				labyrinth.setFlagBot(3);
				direction = Direction.EAST;
			} else if (direction == Direction.SOUTH) {
				//Move on the right and change of direction
				labyrinth.moveRobot(c, 3, direction, objectType);
				labyrinth.setFlagBot(3);
				direction = Direction.WEST;
			} else if (direction == Direction.EAST) {
				//Move on the right and change of direction
				labyrinth.moveRobot(c, 3, direction, objectType);
				labyrinth.setFlagBot(3);
				direction = Direction.SOUTH;
			} else if (direction == Direction.WEST) {
				//Move on the right and change of direction
				labyrinth.moveRobot(c, 3, direction, objectType);
				labyrinth.setFlagBot(3);
				direction = Direction.NORTH;
			}
			break;
		case R.id.labyTurnLeft:
			if (direction == Direction.NORTH) {
				//Move on the left and change of direction
				labyrinth.moveRobot(c, 4, direction, objectType);
				labyrinth.setFlagBot(4);
				direction = Direction.WEST;
			} else if (direction == Direction.SOUTH) {
				//Move on the left and change of direction
				labyrinth.moveRobot(c, 4, direction, objectType);
				labyrinth.setFlagBot(4);
				direction = Direction.EAST;
			} else if (direction == Direction.EAST) {
				//Move forward to the east
				labyrinth.moveRobot(c, 4, direction, objectType);
				labyrinth.setFlagBot(4);
				direction = Direction.NORTH;
			} else if (direction == Direction.WEST) {
				//Move forward to the west.
				labyrinth.moveRobot(c, 4, direction, objectType);
				labyrinth.setFlagBot(4);
				direction = Direction.SOUTH;
			}
			break;
		case R.id.autoNavButton:
			for (Integer object : listOT) {
				labyrinth.moveRobot(c, 1, direction, object);
				labyrinth.setFlagBot(1);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

    private int getObjectTypeStandalone() {
		// TODO Auto-generated method stub
    	int oType = OBJECT_RIGHT_LEFT;
		return oType;
	}

	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}
    
}
