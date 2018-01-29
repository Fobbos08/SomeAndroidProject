package mylive.live;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Alina on 28.01.2018.
 */

public class SensorListener implements SensorEventListener{
    private SensorManager _sensorManager;
    private final float[] mAccelerometerReading = new float[3];
    private final float[] mMagnetometerReading = new float[3];

    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];

    public SensorListener(SensorManager sensorManager)
    {
        _sensorManager = sensorManager;
        register();
    }

    public float[] getRotationMatrix(){
        return mRotationMatrix;
    }

    public float[] getOrientationMatrix(){
        return mOrientationAngles;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    ///
    //use on continue
    ///
    public void register(){
        _sensorManager.registerListener(
                this,
                _sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI);
        _sensorManager.registerListener(
                this,
                _sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI);
    }

    ///
    //unregister on pause
    ///
    public void unregister() {
        _sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mAccelerometerReading,
                    0, mAccelerometerReading.length);
        }
        else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mMagnetometerReading,
                    0, mMagnetometerReading.length);
        }
    }

    public void updateOrientationAngles() {
        _sensorManager.getRotationMatrix(mRotationMatrix, null,
                mAccelerometerReading, mMagnetometerReading);
        _sensorManager.getOrientation(mRotationMatrix, mOrientationAngles);
    }
}
