package technikum.at.messma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import technikum.at.messma.Entities.AccessPoint;
import technikum.at.messma.Entities.GridPoint;
import technikum.at.messma.Entities.Stand;
import technikum.at.messma.Service.APScanner;
import technikum.at.messma.Service.NavigationAPIService;
import technikum.at.messma.Views.PathView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private PathView navPath;
    private NavigationAPIService APIService = new NavigationAPIService();
    private List<Stand> stands;
    private APScanner scanner;

    private WifiManager wifiManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //Initialize StandGP
            GridPoint standGP = null;
            //navPath.Clean();
            switch (item.getItemId()) {
                case R.id.s1:
                    for (Stand tempStand:stands
                            ) {
                        if(tempStand.getIdStand()==1){
                            standGP = tempStand.getGridPoint();
                            List<GridPoint> tempGrids = APIService.navigate(standGP , getScanResults());
                            mTextMessage.setText("Navigating to Stand1");
                            mTextMessage.invalidate();
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
                            List<GridPoint> tempGrids = APIService.navigate(standGP , getScanResults());
                            mTextMessage.setText("Navigating to Stand2");
                            mTextMessage.invalidate();
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
                            List<GridPoint> tempGrids = APIService.navigate(standGP , getScanResults());
                            mTextMessage.setText("Navigating to Stand3");
                            mTextMessage.invalidate();
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
                            List<GridPoint> tempGrids = APIService.navigate(standGP , getScanResults());
                            mTextMessage.setText("Navigating to Stand4");
                            mTextMessage.invalidate();
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
        //scanner = new APScanner(wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE), this);
        wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mTextMessage = (TextView) findViewById(R.id.message);
        navPath = findViewById(R.id.navpath);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private ArrayList<AccessPoint> arrayList = new ArrayList<>();
    private List<ScanResult> tmpScanResults;

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            tmpScanResults = wifiManager.getScanResults();
            //https://androidforums.com/threads/wifimanager-getscanresults-always-returns-empty-list.1266068/
            //need to enable permission to access localization service
            for (ScanResult scanResult : tmpScanResults) {
                //if(filterAP(scanResult.BSSID)){
                arrayList.add(new AccessPoint(scanResult.BSSID, 0, true, scanResult.SSID, scanResult.level));
                //}
            }
            unregisterReceiver(this);
        }
    };

    private void scanWifi(){
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        //Toast.makeText(this, "Scanning WiFi ...", Toast.LENGTH_SHORT).show();
    }



    public List<AccessPoint> getScanResults(){
        arrayList.clear();
        scanWifi();
        return arrayList;
    }
}
