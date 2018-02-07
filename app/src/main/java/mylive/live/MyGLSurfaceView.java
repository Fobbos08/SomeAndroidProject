package mylive.live;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Alina on 04.02.2018.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    private SensorListener _sensorListener;
    private Handler h;


    public MyGLSurfaceView(Context context){
        super(context);
        h = new Handler();
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        //requestRender();
    }

    public void setSensorListener(SensorListener sensorListener)
    {
        _sensorListener = sensorListener;
        mRenderer.setSensorListener(sensorListener);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs)  {
        super(context, attrs);
        h = new Handler();
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    public void Rend(){
        requestRender();
    }


}
