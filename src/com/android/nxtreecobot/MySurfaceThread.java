package com.android.nxtreecobot;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MySurfaceThread extends Thread {
	
	private SurfaceHolder myThreadSurfaceHolder;
	private LabyrinthView myThreadSurfaceView;
	private boolean myThreadRun = false;
	
	public MySurfaceThread(SurfaceHolder surfaceHolder, LabyrinthView labyrinthView) {
		myThreadSurfaceHolder = surfaceHolder;
		myThreadSurfaceView = labyrinthView;
    }
	
	public void setRunning(boolean b) {
		myThreadRun = b;
		}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//super.run();
		while (myThreadRun) {
            Canvas c = null;
            try {
                c = myThreadSurfaceHolder.lockCanvas(null);
                synchronized (myThreadSurfaceHolder) {
                    myThreadSurfaceView.onDraw(c);
                }
            } finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (c != null) {
                	myThreadSurfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
	}
}
