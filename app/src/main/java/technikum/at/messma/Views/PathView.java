package technikum.at.messma.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

import technikum.at.messma.Entities.GridPoint;

public class PathView extends View {

    Paint blue;
    Paint red;
    Paint green;
    Path path;

    int startX = 2000;
    int startY = 2000;
    int endX = 2000;
    int endY = 2000;
    float radius = 50.0f;

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
        blue = new Paint();
        blue.setColor(Color.BLUE);
        blue.setStrokeWidth(6);
        blue.setStyle(Paint.Style.STROKE);

        path = new Path();

        red = new Paint();
        red.setColor(Color.RED);
        red.setStrokeWidth(6);
        red.setStyle(Paint.Style.FILL);

        green = new Paint();
        green.setColor(Color.GREEN);
        green.setStrokeWidth(6);
        green.setStyle(Paint.Style.FILL);

        CornerPathEffect cornerPathEffect =
                new CornerPathEffect(radius);

        blue.setPathEffect(cornerPathEffect);
    }

    public void drawPath(List<GridPoint> gps) {

        if (gps.size() > 1) {
            startX = gps.get(0).getPosX();
            startY = gps.get(0).getPosY();

            path.moveTo(startX, startY);

            gps.remove(0);

            for (GridPoint gp : gps) {
                path.lineTo(gp.getPosX(), gp.getPosY());
            }
            endX = gps.get(gps.size() - 1).getPosX();
            endY = gps.get(gps.size() - 1).getPosY();
        }else {
            startX = 2000;
            startY = 2000;
            endX = gps.get(0).getPosX();
            endY = gps.get(0).getPosY();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, blue);
        canvas.drawCircle(startX, startY, 20, red);
        canvas.drawCircle(endX, endY, 20, green);
        path.reset();
    }


}