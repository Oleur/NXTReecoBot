package com.android.nxtreecobot;

import com.android.nxtreecobot.R;
import com.android.nxtreecobot.comm.NXTComm.Direction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	private int flagBot = 0;
	private int robotX = 225;
	private int robotY = 275;
	private Bitmap labyp1, labyp2, labyp3, labyp4, labyp5, labyp6, labyp7, 
			labyp8, labyp9, labyp10, labyp11, labyp12, labyp13, labyp14 = null;

	private boolean drawing = false;
	private Direction direction;
	private int objectType = 0;
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	//Constants for matching the right piece of labyrinth with respect to the robot's perceptions.
    public static final int OBJECT_CULDESAC = 100;
    public static final int OBJECT_RIGHT_FRONT = 101;
    public static final int OBJECT_RIGHT_LEFT = 102;
    public static final int OBJECT_LEFT_FRONT = 103;
    public static final int OBJECT_FRONT = 104;
    public static final int OBJECT_LEFT = 105;
    public static final int OBJECT_RIGHT = 106;

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//super.onDraw(canvas);
		//Paints for the labyrinth.
		Paint recPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		recPaint.setStyle(Paint.Style.STROKE);
		recPaint.setColor(Color.RED);
		recPaint.setStrokeWidth(3);
		
		//Rectangle for the labyrinth contour.
		canvas.drawRect(25, 25, 475, 575, recPaint);
		
		/*if(drawing && flagCircle<1){
			canvas.drawCircle(targetX, targetY, 2, paint);
			canvas.drawCircle(targetX, targetY, 20, paint);
			canvas.drawCircle(targetX, targetY, 40, paint);
			flagCircle++;
		}*/
		
		if(drawing && flagBot == 1) {
			moveRobot(canvas, flagBot, direction, objectType);
			drawing =  false;
		} else if (drawing && flagBot == 2) {
			moveRobot(canvas, flagBot, direction, objectType);
			drawing = false;
		} else if (drawing && flagBot == 3) {
			moveRobot(canvas, flagBot, direction, objectType);
			drawing = false;
		} else if (drawing && flagBot == 4) {
			moveRobot(canvas, flagBot, direction, objectType);
			drawing = false;
		} else {
			drawing = false;
		}
	}

	public int getFlagBot() {
		return flagBot;
	}

	public void setFlagBot(int flagBot) {
		this.flagBot = flagBot;
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
		
		//Creation of the bitmaps for the pieces of labyrinth.
		labyp1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.laby_p1);
		labyp2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.laby_p2);
		labyp3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.laby_p3);
		labyp4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.laby_p4);
		labyp5 = BitmapFactory.decodeResource(this.getResources(), R.drawable.laby_p5);
		labyp6 = BitmapFactory.decodeResource(this.getResources(), R.drawable.laby_p6);
		labyp7 = BitmapFactory.decodeResource(this.getResources(), R.drawable.laby_p7);
		labyp8 = BitmapFactory.decodeResource(this.getResources(), R.drawable.laby_p8);
		labyp9 = BitmapFactory.decodeResource(this.getResources(), R.drawable.laby_p9);
		labyp10 = BitmapFactory.decodeResource(this.getResources(), R.drawable.laby_p10);
		labyp11 = BitmapFactory.decodeResource(this.getResources(), R.drawable.laby_p11);
		labyp12 = BitmapFactory.decodeResource(this.getResources(), R.drawable.laby_p12);
		labyp13 = BitmapFactory.decodeResource(this.getResources(), R.drawable.laby_p13);
		labyp14 = BitmapFactory.decodeResource(this.getResources(), R.drawable.laby_p14);
		
		setFocusable(true); // make sure we get key events
		
		paint.setFilterBitmap(true);
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
	 * Make the robot moving on a given direction.
	 */
	//TODO: Add parameter: object type.
	public void moveRobot(Canvas c, int flag, Direction dir, int oType) {
		drawing = true;
		direction = dir;
		objectType = oType;
		if(flag == 1) {
			/****************
			 * Move forward *
			 ****************/
			if (dir == Direction.NORTH) {
				if (objectType == OBJECT_CULDESAC) {
					robotY -= 25;
					c.drawBitmap(labyp5, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotY -= 25;
					c.drawBitmap(labyp7, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotY -= 25;
					c.drawBitmap(labyp2, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotY -= 25;
					c.drawBitmap(labyp8, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotY -= 25;
					c.drawBitmap(labyp12, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotY -= 25;
					c.drawBitmap(labyp11, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotY -= 25;
					c.drawBitmap(labyp14, robotX, robotY, paint);
					flagBot = 0;
				}
			} else if (dir == Direction.SOUTH) {
				if (objectType == OBJECT_CULDESAC) {
					robotY += 25;
					c.drawBitmap(labyp6, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotY += 25;
					c.drawBitmap(labyp10, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotY += 25;
					c.drawBitmap(labyp2, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotY += 25;
					c.drawBitmap(labyp9, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotY += 25;
					c.drawBitmap(labyp13, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotY += 25;
					c.drawBitmap(labyp14, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotY += 25;
					c.drawBitmap(labyp11, robotX, robotY, paint);
					flagBot = 0;
				}
			} else if (dir == Direction.EAST) {
				if (objectType == OBJECT_CULDESAC) {
					robotX += 25;
					c.drawBitmap(labyp4, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotX += 25;
					c.drawBitmap(labyp9, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotX += 25;
					c.drawBitmap(labyp1, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotX += 25;
					c.drawBitmap(labyp7, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotX += 25;
					c.drawBitmap(labyp14, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotX += 25;
					c.drawBitmap(labyp12, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotX += 25;
					c.drawBitmap(labyp13, robotX, robotY, paint);
					flagBot = 0;
				}
			} else if (dir == Direction.WEST) {
				if (objectType == OBJECT_CULDESAC) {
					robotX -= 25;
					c.drawBitmap(labyp3, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotX -= 25;
					c.drawBitmap(labyp8, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotX -= 25;
					c.drawBitmap(labyp1, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotX -= 25;
					c.drawBitmap(labyp10, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotX -= 25;
					c.drawBitmap(labyp11, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotX -= 25;
					c.drawBitmap(labyp13, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotX -= 25;
					c.drawBitmap(labyp12, robotX, robotY, paint);
					flagBot = 0;
				}
			}
		} else if (flag == 2) {
			/* ***************
			 * Move backward *
			 *****************/
			if (dir == Direction.NORTH) {
				if (objectType == OBJECT_CULDESAC) {
					robotY += 25;
					c.drawBitmap(labyp5, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotY += 25;
					c.drawBitmap(labyp7, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotY += 25;
					c.drawBitmap(labyp2, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotY += 25;
					c.drawBitmap(labyp8, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotY += 25;
					c.drawBitmap(labyp12, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotY += 25;
					c.drawBitmap(labyp11, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotY += 25;
					c.drawBitmap(labyp14, robotX, robotY, paint);
					flagBot = 0;
				}
			} else if (dir == Direction.SOUTH) {
				if (objectType == OBJECT_CULDESAC) {
					robotY -= 25;
					c.drawBitmap(labyp6, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotY -= 25;
					c.drawBitmap(labyp10, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotY -= 25;
					c.drawBitmap(labyp2, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotY -= 25;
					c.drawBitmap(labyp9, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotY -= 25;
					c.drawBitmap(labyp13, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotY -= 25;
					c.drawBitmap(labyp14, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotY -= 25;
					c.drawBitmap(labyp11, robotX, robotY, paint);
					flagBot = 0;
				}
			} else if (dir == Direction.EAST) {
				if (objectType == OBJECT_CULDESAC) {
					robotX -= 25;
					c.drawBitmap(labyp4, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotX -= 25;
					c.drawBitmap(labyp9, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotX -= 25;
					c.drawBitmap(labyp1, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotX -= 25;
					c.drawBitmap(labyp7, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotX -= 25;
					c.drawBitmap(labyp14, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotX -= 25;
					c.drawBitmap(labyp12, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotX -= 25;
					c.drawBitmap(labyp13, robotX, robotY, paint);
					flagBot = 0;
				}
			} else if (dir == Direction.WEST) {
				if (objectType == OBJECT_CULDESAC) {
					robotX += 25;
					c.drawBitmap(labyp3, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotX += 25;
					c.drawBitmap(labyp8, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotX += 25;
					c.drawBitmap(labyp1, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotX += 25;
					c.drawBitmap(labyp10, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotX += 25;
					c.drawBitmap(labyp11, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotX += 25;
					c.drawBitmap(labyp13, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotX += 25;
					c.drawBitmap(labyp12, robotX, robotY, paint);
					flagBot = 0;
				}
			}
		} else if (flag == 3) {
			//Move on the right.
			if (dir == Direction.NORTH) {
				if (objectType == OBJECT_CULDESAC) {
					robotX += 25;
					c.drawBitmap(labyp3, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotX += 25;
					c.drawBitmap(labyp8, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotX += 25;
					c.drawBitmap(labyp1, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotX += 25;
					c.drawBitmap(labyp10, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotX += 25;
					c.drawBitmap(labyp11, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotX += 25;
					c.drawBitmap(labyp13, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotX += 25;
					c.drawBitmap(labyp12, robotX, robotY, paint);
					flagBot = 0;
				}
			} else if (dir == Direction.SOUTH) {
				if (objectType == OBJECT_CULDESAC) {
					robotX -= 25;
					c.drawBitmap(labyp4, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotX -= 25;
					c.drawBitmap(labyp9, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotX -= 25;
					c.drawBitmap(labyp1, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotX -= 25;
					c.drawBitmap(labyp7, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotX -= 25;
					c.drawBitmap(labyp14, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotX -= 25;
					c.drawBitmap(labyp12, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotX -= 25;
					c.drawBitmap(labyp13, robotX, robotY, paint);
					flagBot = 0;
				}
			} else if (dir == Direction.EAST) {
				if (objectType == OBJECT_CULDESAC) {
					robotY += 25;
					c.drawBitmap(labyp6, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotY += 25;
					c.drawBitmap(labyp10, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotY += 25;
					c.drawBitmap(labyp2, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotY += 25;
					c.drawBitmap(labyp9, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotY += 25;
					c.drawBitmap(labyp13, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotY += 25;
					c.drawBitmap(labyp14, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotY += 25;
					c.drawBitmap(labyp11, robotX, robotY, paint);
					flagBot = 0;
				}
			} else if (dir == Direction.WEST) {
				if (objectType == OBJECT_CULDESAC) {
					robotY -= 25;
					c.drawBitmap(labyp5, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotY -= 25;
					c.drawBitmap(labyp7, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotY -= 25;
					c.drawBitmap(labyp2, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotY -= 25;
					c.drawBitmap(labyp8, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotY -= 25;
					c.drawBitmap(labyp12, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotY -= 25;
					c.drawBitmap(labyp11, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotY -= 25;
					c.drawBitmap(labyp14, robotX, robotY, paint);
					flagBot = 0;
				}
			}
		} else if (flag == 4) {
			/* *****************
			 * Move on the left.
			 ********************/
			if (dir == Direction.NORTH) {
				if (objectType == OBJECT_CULDESAC) {
					robotX -= 25;
					c.drawBitmap(labyp3, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotX -= 25;
					c.drawBitmap(labyp8, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotX -= 25;
					c.drawBitmap(labyp1, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotX -= 25;
					c.drawBitmap(labyp10, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotX -= 25;
					c.drawBitmap(labyp11, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotX -= 25;
					c.drawBitmap(labyp13, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotX -= 25;
					c.drawBitmap(labyp12, robotX, robotY, paint);
					flagBot = 0;
				}
			} else if (dir == Direction.SOUTH) {
				if (objectType == OBJECT_CULDESAC) {
					robotX += 25;
					c.drawBitmap(labyp4, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotX += 25;
					c.drawBitmap(labyp9, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotX += 25;
					c.drawBitmap(labyp1, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotX += 25;
					c.drawBitmap(labyp7, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotX += 25;
					c.drawBitmap(labyp14, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotX += 25;
					c.drawBitmap(labyp12, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotX += 25;
					c.drawBitmap(labyp13, robotX, robotY, paint);
					flagBot = 0;
				}
			} else if (dir == Direction.EAST) {
				if (objectType == OBJECT_CULDESAC) {
					robotY -= 25;
					c.drawBitmap(labyp5, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotY -= 25;
					c.drawBitmap(labyp7, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotY -= 25;
					c.drawBitmap(labyp2, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotY -= 25;
					c.drawBitmap(labyp8, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotY -= 25;
					c.drawBitmap(labyp12, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotY -= 25;
					c.drawBitmap(labyp11, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotY -= 25;
					c.drawBitmap(labyp14, robotX, robotY, paint);
					flagBot = 0;
				}
			} else if (dir == Direction.WEST) {
				if (objectType == OBJECT_CULDESAC) {
					robotY += 25;
					c.drawBitmap(labyp6, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_FRONT) {
					robotY += 25;
					c.drawBitmap(labyp10, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT_LEFT) {
					robotY += 25;
					c.drawBitmap(labyp2, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT_FRONT) {
					robotY += 25;
					c.drawBitmap(labyp9, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_FRONT) {
					robotY += 25;
					c.drawBitmap(labyp13, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_LEFT) {
					robotY += 25;
					c.drawBitmap(labyp14, robotX, robotY, paint);
					flagBot = 0;
				} else if (objectType == OBJECT_RIGHT) {
					robotY += 25;
					c.drawBitmap(labyp11, robotX, robotY, paint);
					flagBot = 0;
				}
			}
		} else {
			flagBot = 0;
		}
	}

}
