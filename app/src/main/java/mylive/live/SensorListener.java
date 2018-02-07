package mylive.live;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alina on 28.01.2018.
 */

public class SensorListener implements SensorEventListener{
    private SensorManager _sensorManager;
    private final float[] mAccelerometerReading = new float[3];
    private final float[] mMagnetometerReading = new float[3];

    private  float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];

    private float[] orientationAngles = new float[3];

    private static final int MAX_SAMPLE_SIZE = 5;


    List<Float>[] rollingAverage = new List[3];

    public SensorListener(SensorManager sensorManager)
    {
        _sensorManager = sensorManager;
        rollingAverage[0] = new ArrayList<Float>();
        rollingAverage[1] = new ArrayList<Float>();
        rollingAverage[2] = new ArrayList<Float>();
        register();
    }

    public float[] getRotationMatrix(){
        return mRotationMatrix;
    }

    public float[] getOrientationMatrix(){

        return orientationAngles;
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
        if (mAccelerometerReading != null && mMagnetometerReading != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mAccelerometerReading, mMagnetometerReading);
            if (success) {
                SensorManager.getOrientation(R, mOrientationAngles);
            }
            mRotationMatrix = R;
            roll(rollingAverage[0], mOrientationAngles[0]);
            roll(rollingAverage[1], mOrientationAngles[1]);
            roll(rollingAverage[2], mOrientationAngles[2]);
            orientationAngles[0] = averageList(rollingAverage[0]);
            orientationAngles[1] = averageList(rollingAverage[1]);
            orientationAngles[2] = averageList(rollingAverage[2]);
        }

    }

    public List<Float> roll(List<Float> list, float newMember){
        if(list.size() == MAX_SAMPLE_SIZE){
            list.remove(0);
        }
        list.add(newMember);
        return list;
    }

    public float averageList(List<Float> tallyUp){

        float total=0;
        for(float item : tallyUp ){
            total+=item;
        }
        total = total/tallyUp.size();

        return total;
    }
}
