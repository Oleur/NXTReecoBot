package com.android.nxtreecobot.standalone;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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

public class DrivingLabyrinthStandalone extends Activity implements OnClickListener {
	
	//Custom view and button to move the robot.
	private LabyrinthView labyrinth = null;
	private Button explore;
	private Button forward;
	private Button backward;
	private Button left;
	private Button right;
	
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
        
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.labyGoForward:
			//TODO: Move the robot forward.
			break;
		case R.id.labyGoBackward:
			//TODO: Move backward.
			break;
		case R.id.labyTurnLeft:
			//TODO: Turn Left.
			break;
		case R.id.labyTurnRight:
			//TODO: Turn right.
			break;
		}
	}
    
}
