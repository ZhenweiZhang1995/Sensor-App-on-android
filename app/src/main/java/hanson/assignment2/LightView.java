package hanson.assignment2;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class LightView extends View {

    ArrayList<Float> myArray = new ArrayList<>();
    ArrayList<Float> myMeanArray = new ArrayList<>();
    ArrayList<Float> myStdArray = new ArrayList<>();
    int count = 0;
    float meanValue = 0;
    float stdValue =0;
    float sum = 0;
    float cx = 0;
    float cy = 0;

    public LightView(Context context) {
        super(context);
    }

    public LightView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public LightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);

    }

    public void clearList(){
        myArray.clear();
    }

    public void addPoint(float number){
        myArray.add(number);
        if(myArray.size()>10){
            myArray.remove(0);
        }
    }

    public void addMean(float number){
        myMeanArray.add(number);
        if(myMeanArray.size()>10){
            myMeanArray.remove(0);
        }
    }

    public void addStd(float number){
        myStdArray.add(number);
        if(myStdArray.size()>10){
            myStdArray.remove(0);
        }
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        Paint grid = new Paint();
        grid.setColor(Color.GRAY);

        for (int i =0; i<= 5; i++){
            canvas.drawLine(i*width/5,0,i*width/5,width,grid);
        }
        for (int i =0; i<= 5; i++){
            canvas.drawLine(0,i*height/5,width,i*height/5,grid);
        }

        Paint value = new Paint();
        value.setColor(Color.RED);

        Paint mean = new Paint();
        mean.setColor(Color.BLUE);

        Paint std = new Paint();
        std.setColor(Color.CYAN);

        float preMean = 0;
        float preStd =0;

        if(myArray.size()>0){
            for(int i =0; i < myArray.size();i++){
                cx = i;
                cy = myArray.get(i);

                int size = myArray.size();
                if(size>2){
                    sum =  myArray.get(size-1) + myArray.get(size-2)+ myArray.get(size-3);
                    stdValue = (float)Math.sqrt((cy - meanValue) * (cy - meanValue) / 3);
                    meanValue = sum / 3;
                    canvas.drawLine((i-1)*width/10, height-preStd*height/25, i*width/10, height-stdValue*height/25,std);
                    canvas.drawLine((i-1)*width/10, height-preMean*height/25, i*width/10, height-meanValue*height/25,mean);
                    preMean = meanValue;
                    preStd = stdValue;
                }else if(size==2){

                    sum = myArray.get(size-1) + myArray.get(size-2);
                    stdValue = (float)Math.sqrt((cy - meanValue) * (cy - meanValue) / 2);
                    meanValue = sum / 2;
                    preMean = myArray.get(size -1);
                    preStd = 0;

                    canvas.drawLine((i-1)*width/10, height-preStd*height/25, i*width/10, height-stdValue*height/25,std);
                    canvas.drawLine((i-1)*width/10, height-myMeanArray.get(i-1)*height/25, i*width/10, height-meanValue*height/25,mean);

                }else{
                    sum = myArray.get(size -1);
                    meanValue = sum;
                    stdValue = 0;
                }

                addMean(meanValue);
                addStd(stdValue);

                canvas.drawCircle(cx*width/10,height-cy*height/25,5,value);
                canvas.drawCircle(cx*width/10,height-meanValue*height/25,5,mean);
                canvas.drawCircle(cx*width/10,height-stdValue*height/25,5,std);

                Log.v("MyTag", "Std is " + stdValue);

                if(i != 0){
                    canvas.drawLine((i-1)*width/10, height-myArray.get(i-1)*height/25, i*width/10, height-cy*height/25, value);
                }

            }
        }

    }


}
