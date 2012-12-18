package com.android.nxtreecobot.standalone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LabyrinthView extends SurfaceView implements SurfaceHolder.Callback {
	
	private MySurfaceThread thread;
	
	private float targetX, targetY, radius;
	private int flagCircle = 0;

	private boolean drawing = false;
	
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//super.onDraw(canvas);
		//Paints for the labyrinth.
		
		Paint recPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Paint pLabyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		recPaint.setStyle(Paint.Style.STROKE);
		recPaint.setColor(Color.RED);
		recPaint.setStrokeWidth(3);
		
		pLabyPaint.setColor(Color.RED);
		//Rectangle for the labyrinth contour.
		canvas.drawRect(25, 25, 450, 575, recPaint);
		//Rectangle for the shapes inside the labyrinth.
		canvas.drawRect(25, 75, 300, 150, pLabyPaint);
		canvas.drawRect(150, 150, 300, 225, pLabyPaint);
		canvas.drawRect(350, 75, 450, 225, pLabyPaint);
		canvas.drawRect(350, 275, 450, 375, pLabyPaint);
		canvas.drawRect(350, 425, 450, 525, pLabyPaint);
		canvas.drawRect(75, 425, 300, 525, pLabyPaint);
		canvas.drawRect(25, 275, 300, 375, pLabyPaint);
		canvas.drawRect(25, 200, 100, 275, pLabyPaint);
		
		
		
		if(drawing && flagCircle<1){
			canvas.drawCircle(targetX, targetY, 2, paint);
			canvas.drawCircle(targetX, targetY, 20, paint);
			canvas.drawCircle(targetX, targetY, 40, paint);
			flagCircle++;
		}
	}

	@SuppressWarnings("unused")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//return super.onTouchEvent(event);
		int action = event.getAction();
		if (action==MotionEvent.ACTION_MOVE){
			float x = event.getX();
			float y = event.getY();
		} else if (action == MotionEvent.ACTION_DOWN) {
			paint.setColor(Color.BLUE);
			targetX = event.getX();
			targetY = event.getY();
			radius = 1;
			drawing = true;
			
		} else if (action==MotionEvent.ACTION_UP) {
			float x2 = event.getX();
			float y2 = event.getY();
			drawing = false;
		}
		return true;
	}

	public LabyrinthView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public LabyrinthView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public LabyrinthView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init(){
		getHolder().addCallback(this);
		thread = new MySurfaceThread(getHolder(), this);
		
		setFocusable(true); // make sure we get key events
		
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		paint.setColor(Color.WHITE);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
        }
	}
	
	/**
	 * Make the robot going forward.
	 */
	public void goForward(Canvas c) {
		
	}
	
	/**
	 * Make the robot going backward.
	 */
	public void goBackward(Canvas c) {
		
	}
	/**
	 * Make the robot turning on the left.
	 */
	public void turnLeft(Canvas c) {
		
	}
	
	/**
	 * Make the robot turning on the right.
	 */
	public void turnRight(Canvas c) {
		
	}

}
