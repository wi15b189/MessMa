package technikum.at.messma.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

import technikum.at.messma.Entities.GridPoint;

public class PathView extends View{

    Paint paint;
    Path path;
    Paint red;

    int startX;
    int startY;
    int endX;
    int endY;

    public PathView(Context context) {
        super(context);
        init();
        //super.setBackgroundColor(Color.TRANSPARENT);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(6);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();

        red = new Paint();
        red.setColor(Color.RED);
        red.setStrokeWidth(6);
        red.setStyle(Paint.Style.FILL);
    }

    public void drawPath(List<GridPoint> gps) {

        if(gps.size() > 1) {
            startX = gps.get(0).getPosX();
            startY = gps.get(0).getPosY();

            path.moveTo(startX,startY);

            gps.remove(0);

            for (GridPoint gp : gps) {
                path.lineTo(gp.getPosX(), gp.getPosY());
            }
            endX = gps.get(gps.size()-1).getPosX();
            endY = gps.get(gps.size()-1).getPosY();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        canvas.drawPath(path, paint);
        canvas.drawCircle(startX,startY, 15, red);
        canvas.drawCircle(endX,endY, 15, red);
    }


}