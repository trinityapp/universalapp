package com.trinity.dynamicforms.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * Created by fdffd on 10/17/2016.
 */

public class SignatureView extends View {
    private float STROKE_WIDTH = 5f;
    private float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;

    private Paint paint = new Paint();
    private Path path = new Path();
    private Bitmap mBitmap;
    private float lastTouchX;
    private float lastTouchY;
    private final RectF dirtyRect = new RectF();
    public Boolean isDrawEnabled = true;
    public SignatureView(Context context ) {

        super(context);

        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(STROKE_WIDTH);

        // set the bg color as white
        this.setBackgroundColor(Color.LTGRAY);

        // width and height should cover the screen
        this.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

    }

    /**
     * Get signature
     *
     * @return
     */
    public Bitmap getSignature() {

        Bitmap signatureBitmap = null;

        // set the signature bitmap
        if (signatureBitmap == null) {
            signatureBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.RGB_565);
        }

        // important for saving signature
        final Canvas canvas = new Canvas(signatureBitmap);
        this.draw(canvas);

        return signatureBitmap;
    }

    /**
     * clear signature canvas
     */
    public void clearSignature() {
        path.reset();
        //v.setBackgroundColor(Color.WHITE);
        this.invalidate();

    }

    public void save(View v, String path) {
        if (mBitmap == null) {
            mBitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),
                    Bitmap.Config.RGB_565);

        }

        Canvas canvas = new Canvas(mBitmap);
        try {

            FileOutputStream mFileOutStream = new FileOutputStream(path);
            v.draw(canvas);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 70, mFileOutStream);
            mFileOutStream.flush();
            mFileOutStream.close();
            if (mBitmap != null) {
                mBitmap.recycle();
                mBitmap = null;
            }

        } catch (Exception e) {

        }

    }

    public void add(String path) {
        Bitmap imgthumBitmap = null;
        try {

            final int THUMBNAIL_SIZE = 240;

            FileInputStream fis = new FileInputStream(path);
            imgthumBitmap = BitmapFactory.decodeStream(fis);

            imgthumBitmap = Bitmap.createScaledBitmap(imgthumBitmap,
                    THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        assert imgthumBitmap != null;
        Canvas canvas = new Canvas(imgthumBitmap);
        this.draw(canvas);
    }

    public void removeSign(String url ){
        File fdelete = new File(url);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + url);
            } else {
                System.out.println("file not Deleted :" + url);
            }
        }
    }
    // all touch events during the drawing
    @Override
    public void onDraw(Canvas canvas) {
        if(isDrawEnabled) {
            canvas.drawPath(this.path, this.paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                path.moveTo(eventX, eventY);

                lastTouchX = eventX;
                lastTouchY = eventY;
                return true;

            case MotionEvent.ACTION_MOVE:

            case MotionEvent.ACTION_UP:

                resetDirtyRect(eventX, eventY);
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);

                    expandDirtyRect(historicalX, historicalY);
                    path.lineTo(historicalX, historicalY);
                }
                path.lineTo(eventX, eventY);
                break;

            default:

                return false;
        }

        invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

        lastTouchX = eventX;
        lastTouchY = eventY;

        return true;
    }

    public void expandDirtyRect(float historicalX, float historicalY) {
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX;
        } else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX;
        }

        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY;
        } else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY;
        }
    }

    public void resetDirtyRect(float eventX, float eventY) {
        dirtyRect.left = Math.min(lastTouchX, eventX);
        dirtyRect.right = Math.max(lastTouchX, eventX);
        dirtyRect.top = Math.min(lastTouchY, eventY);
        dirtyRect.bottom = Math.max(lastTouchY, eventY);
    }

    public void isDrawEnabled(boolean b) {
        isDrawEnabled = b;
    }
}
