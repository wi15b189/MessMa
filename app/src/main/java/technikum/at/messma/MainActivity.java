package technikum.at.messma;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import technikum.at.messma.Entities.GridPoint;
import technikum.at.messma.Entities.Stand;
import technikum.at.messma.Service.APScanner;
import technikum.at.messma.Service.NavigationAPIService;
import technikum.at.messma.Views.PathView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private PathView navPath;
    private NavigationAPIService APIService = new NavigationAPIService();
    //private APScanner scanner = new APScanner(getApplicationContext());
    private List<Stand> stands;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //Initialize StandGP
            GridPoint standGP = null;
            switch (item.getItemId()) {
                case R.id.s1:
                    for (Stand tempStand:stands
                            ) {
                        if(tempStand.getIdStand()==1){
                            standGP = tempStand.getGridPoint();
                            List<GridPoint> tempGrids = APIService.navigate(standGP , null);
                            mTextMessage.setText("Navigating to Stand1");
                            navPath.drawPath(tempGrids);
                            navPath.postInvalidate();
                            return true;
                        }
                    }
                case R.id.s2:
                    //Initialize StandGP
                    for (Stand tempStand:stands
                            ) {
                        if(tempStand.getIdStand()==2){
                            standGP = tempStand.getGridPoint();
                            List<GridPoint> tempGrids = APIService.navigate(standGP , null);
                            mTextMessage.setText("Navigating to Stand2");
                            navPath.drawPath(tempGrids);
                            navPath.postInvalidate();
                            return true;
                        }
                    }
                case R.id.s3:
                    //Initialize StandGP
                    for (Stand tempStand:stands
                            ) {
                        if(tempStand.getIdStand()==3){
                            standGP = tempStand.getGridPoint();
                            List<GridPoint> tempGrids = APIService.navigate(standGP , null);
                            mTextMessage.setText("Navigating to Stand3");
                            navPath.drawPath(tempGrids);
                            navPath.postInvalidate();
                            return true;
                        }
                    }
                case R.id.s4:
                    //Initialize StandGP
                    for (Stand tempStand:stands
                            ) {
                        if(tempStand.getIdStand()==4){
                            standGP = tempStand.getGridPoint();
                            List<GridPoint> tempGrids = APIService.navigate(standGP , null);
                            mTextMessage.setText("Navigating to Stand4");
                            navPath.drawPath(tempGrids);
                            navPath.postInvalidate();
                            return true;
                        }
                    }
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stands = APIService.getStands();


        mTextMessage = (TextView) findViewById(R.id.message);
        navPath = findViewById(R.id.navpath);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
