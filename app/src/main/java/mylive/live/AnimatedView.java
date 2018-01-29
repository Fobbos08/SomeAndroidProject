package mylive.live;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Alina on 28.01.2018.
 */

public class AnimatedView extends android.support.v7.widget.AppCompatImageView {

    private Context mContext;
    int x = -1;
    int y = -1;
    private int xVelocity = 10;
    private int yVelocity = 5;
    private Handler h;
    private final int FRAME_RATE = 30;
    private SensorListener _sensorListener;
    Matrix rotatedM = new Matrix();

    private Bitmap animatedBitmap;

    public AnimatedView(Context context) {
        super(context);
        mContext = context;
        h = new Handler();
    }
    public AnimatedView(Context context, AttributeSet attrs)  {
        super(context, attrs);
        mContext = context;
        h = new Handler();
    }

    public void setBitmap(Bitmap bmp)
    {
        animatedBitmap = bmp;
    }

    public void setSensorListener(SensorListener sensorListener)
    {
        _sensorListener = sensorListener;
    }

    private Runnable r = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    protected void onDraw(Canvas c) {
        if(animatedBitmap != null)
        {
            _sensorListener.updateOrientationAngles();
            float[] angles = _sensorListener.getOrientationMatrix();

            rotatedM.setRotate((int)(angles[0]*180/3.14));
            Bitmap rotatedBitmap = Bitmap.createBitmap(animatedBitmap , 0, 0, animatedBitmap.getWidth(), animatedBitmap.getHeight(), rotatedM, true);

            c.drawBitmap(rotatedBitmap, x, y, null);
        }

        h.postDelayed(r, FRAME_RATE);
    }
}
