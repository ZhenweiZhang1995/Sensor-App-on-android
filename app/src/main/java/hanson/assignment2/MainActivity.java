package hanson.assignment2;

        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Color;
        import android.hardware.Sensor;
        import android.hardware.SensorEvent;
        import android.hardware.SensorEventListener;
        import android.hardware.SensorManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.TextView;

        import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    SensorManager sm = null;
    Sensor acceleromter = null;
    Sensor lightSensor = null;
    List<Sensor> sl = null;
    long lastime = 0;
    float acc_value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        lightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        TextView light_status=(TextView)findViewById(R.id.light_status);
        light_status.setText("Resolution is " + lightSensor.getResolution() +"\n" +"MaximumRange is "
                + lightSensor.getMaximumRange() + "\n" + "MinDelay is " + lightSensor.getMinDelay()
                + "\n" + "MaxDelay is " +lightSensor.getMaxDelay());





        acceleromter = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        TextView acc_status=(TextView)findViewById(R.id.acc_status);
        acc_status.setText("Resolution is " + acceleromter.getResolution() +"\n" +"MaximumRange is "
                + acceleromter.getMaximumRange() + "\n" + "MinDelay is " + acceleromter.getMinDelay()
                + "\n" + "MaxDelay is " +acceleromter.getMaxDelay());



        sm.registerListener(this, acceleromter, 10000000);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        double dt = 0;
        dt = sensorEvent.timestamp - lastime;

        PackageManager manager = getPackageManager();
        TextView acc=(TextView)findViewById(R.id.myview_acc);


        TextView light=(TextView)findViewById(R.id.myview_light);


        if(dt >= 1000000000){

            checkLight(manager);
            checkAcc(manager);


            acc_value = (float)Math.sqrt(sensorEvent.values[0] * sensorEvent.values[0] + sensorEvent.values[1] * sensorEvent.values[1]
                    + sensorEvent.values[2] * sensorEvent.values[2] );

            acc.setText("Time = " + dt + ", Value (X, Y, Z)= "
                    + sensorEvent.values[0] + ", " + sensorEvent.values[1] + ", "
                    + sensorEvent.values[2] + "\n" + "value is " + acc_value);

//            Log.v("MyTag", "Time");
            lastime = sensorEvent.timestamp;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void checkLight(PackageManager manager){
        boolean hasLight = manager.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT);
        TextView light_present=(TextView)findViewById(R.id.light_present);
        if (hasLight){
            light_present.setText("Status: Light is present");
            light_present.setTextColor(Color.rgb(29,224,51));
        }else{
            light_present.setText("Status: Light is not present");
            light_present.setTextColor(Color.RED);
        }

    }

    public void checkAcc(PackageManager manager){
        boolean hasAccelerometer = manager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);
        TextView acc_present=(TextView)findViewById(R.id.acc_present);
        if (hasAccelerometer){
            acc_present.setText("Status: Acceleromter is present");
            acc_present.setTextColor(Color.rgb(29,224,51));
        }else{
            acc_present.setText("Status: Acceleromter is not present");
            acc_present.setTextColor(Color.RED);
        }
    }

    public void showAccDetail(View v){
        startActivity(new Intent(MainActivity.this, AccActivity.class));
    }

    public void showLightDetail(View v){
        startActivity(new Intent(MainActivity.this, LightActivity.class));
    }


}

