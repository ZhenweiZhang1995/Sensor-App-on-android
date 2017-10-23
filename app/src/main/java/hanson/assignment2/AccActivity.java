package hanson.assignment2;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by apple on 10/16/17.
 */

public class AccActivity extends AppCompatActivity implements SensorEventListener{

    float acc_value = 0;
    long lastime = 0;
    SensorManager sm = null;
    Sensor acceleromter = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acc);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        acceleromter = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sm.registerListener(this, acceleromter, 100000);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        acc_value = (float)Math.sqrt(sensorEvent.values[0] * sensorEvent.values[0] + sensorEvent.values[1] * sensorEvent.values[1]
                    + sensorEvent.values[2] * sensorEvent.values[2] );
        AccView cView = (AccView) findViewById(R.id.cv);
        cView.addPoint(acc_value);
        cView.invalidate();

        TextView head=(TextView)findViewById(R.id.head);
//        head.setText("Value is " + acc_value);

        String text = "<font color=#ff0000>Value</font> <font color=#0000ff>&nbsp&nbsp&nbsp&nbsp Mean</font><font color=#00FFFF>&nbsp&nbsp&nbsp &nbsp &nbsp Std</font>";
        head.setText(Html.fromHtml(text));




        TextView acc=(TextView)findViewById(R.id.acc_value);
        acc.setText("Value is " + acc_value);

        TextView x_axis=(TextView)findViewById(R.id.x_axis);
        x_axis.setText("0         2         4          6          8        10");

        TextView y_axis=(TextView)findViewById(R.id.y_axis);
        y_axis.setText("25 \n\n20 \n\n\n15 \n\n10 \n\n\n 5");

        ImageView i = (ImageView) findViewById(R.id.imv);
        if(acc_value < 12 && acc_value > 8 ){
            i.setBackgroundResource(R.drawable.stand);
        }else if(acc_value >12 && acc_value <16 && acc_value <8 && acc_value >4){
            i.setBackgroundResource(R.drawable.walk);
        }else{
            i.setBackgroundResource(R.drawable.run);
        }

    }

    public void back(View v){
        onBackPressed();
    }

}
