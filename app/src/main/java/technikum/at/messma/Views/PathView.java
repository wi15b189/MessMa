package technikum.at.messma.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
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
        path = new Path();
    }

    public void drawPath(List<GridPoint> gps) {

        if(gps.size() > 1) {
            int x = gps.get(0).getPosX();
            int y = gps.get(0).getPosY();

            path.moveTo(x,y);

            //drawCircle(x,y );
            //path.addCircle(x,y,5, null);
            gps.remove(0);

            for (GridPoint gp : gps) {
                path.lineTo(gp.getPosX(), gp.getPosY());
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