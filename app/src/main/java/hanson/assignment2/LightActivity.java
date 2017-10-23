package hanson.assignment2;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by apple on 10/16/17.
 */

public class LightActivity extends AppCompatActivity implements SensorEventListener {

    float light_value = 0;
    SensorManager sm = null;
    Sensor light = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        light = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sm.registerListener(this, light, 100000);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        light_value = sensorEvent.values[0];
        LightView cView = (LightView) findViewById(R.id.cv);
        cView.addPoint(light_value);
        cView.invalidate();

        TextView head=(TextView)findViewById(R.id.head);

        String text = "<font color=#ff0000>Value</font> <font color=#0000ff>&nbsp&nbsp&nbsp&nbsp Mean</font><font color=#00FFFF>&nbsp&nbsp&nbsp &nbsp &nbsp Std</font>";
        head.setText(Html.fromHtml(text));


        TextView acc=(TextView)findViewById(R.id.light_value);
        acc.setText("Value is " + light_value);

        TextView x_axis=(TextView)findViewById(R.id.x_axis);
        x_axis.setText("0         2         4          6          8        10");

        TextView y_axis=(TextView)findViewById(R.id.y_axis);
        y_axis.setText(" 5 \n\n 4 \n\n\n 3 \n\n 2 \n\n\n 1");

        ImageView i = (ImageView) findViewById(R.id.imv);
        if(light_value < 1 ){
            i.setBackgroundResource(R.drawable.dark);
        }else{
            i.setBackgroundResource(R.drawable.light);
        }

    }

    public void back(View v){
        onBackPressed();
    }



}

