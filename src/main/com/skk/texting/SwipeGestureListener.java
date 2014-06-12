package com.skk.texting;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final float THRESHOLD_DISTANCE = 100;
    private SwipeGestureHandler handler;

    public SwipeGestureListener(SwipeGestureHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //Determining left or right is more important for this application as up and down are anyways ignored
        Direction swipeDirection = horizontalDirection(e1, e2);
        swipeDirection = swipeDirection.equals(Direction.None) ? verticalDirection(e1, e2) : swipeDirection;

        switch (swipeDirection){
            case Right:
                handler.onSwipeRight();break;
            case Left:
                handler.onSwipeLeft();break;
            case Up:
                handler.onSwipeUp();break;
            case Down:
                handler.onSwipeDown();break;
        }
        return true;
    }

    private Direction horizontalDirection(MotionEvent start, MotionEvent end) {
        float displacementX = end.getX() - start.getX();
        Direction direction = (displacementX > 0) ? Direction.Right : Direction.Left;
        return Math.abs(displacementX) >= THRESHOLD_DISTANCE ? direction : Direction.None;
    }

    private Direction verticalDirection(MotionEvent start, MotionEvent end) {
        float displacementY = end.getY() - start.getY();
        Direction direction = (displacementY > 0) ? Direction.Down : Direction.Up;
        return Math.abs(displacementY) >= THRESHOLD_DISTANCE ? direction : Direction.None;
    }

}

enum Direction{
    Right, Left, Up, Down, None
}

