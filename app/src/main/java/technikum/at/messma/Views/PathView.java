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

        GridPoint gp1 = new GridPoint("2", "200","200");
        GridPoint gp2 = new GridPoint("3", "700","200");
        GridPoint gp3 = new GridPoint("4", "700","500");
        GridPoint gp4 = new GridPoint("5", "500","500");

        List<GridPoint> gps = new LinkedList<>();
        gps.add(gp1);
        gps.add(gp2);
        gps.add(gp3);
        gps.add(gp4);
        path = new Path();

        //drawPath(gps);
    }

    public void drawPath(List<GridPoint> gps) {

        if(gps.size() > 1) {
            int x = Integer.parseInt(gps.get(0).getPosX());
            int y = Integer.parseInt(gps.get(0).getPosY());

            path.moveTo(x,y);

            //drawCircle(x,y );
            //path.addCircle(x,y,5, null);
            gps.remove(0);

            for (GridPoint gp : gps) {
                path.lineTo(Integer.parseInt(gp.getPosX()), Integer.parseInt(gp.getPosY()));
            }


        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);

    }

    public void drawStartPoint(GridPoint gridPoint) {

    }

    public void drawEndPoint(GridPoint gridPoint) {

    }
}