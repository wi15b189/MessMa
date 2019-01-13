package technikum.at.messma;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import technikum.at.messma.Entities.GridPoint;
import technikum.at.messma.Views.PathView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private PathView navPath;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);

                    GridPoint gp1 = new GridPoint("2", "200","200");
                    GridPoint gp2 = new GridPoint("3", "700","200");
                    GridPoint gp3 = new GridPoint("4", "700","500");
                    GridPoint gp4 = new GridPoint("5", "500","500");



                    List<GridPoint> gps = new LinkedList<>();
                    gps.add(gp1);
                    gps.add(gp2);
                    gps.add(gp3);
                    gps.add(gp4);

                    navPath.drawPath(gps);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.Toilet:
                    mTextMessage.setText("Go to the toilet");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextMessage = (TextView) findViewById(R.id.message);
        navPath = findViewById(R.id.navpath);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
