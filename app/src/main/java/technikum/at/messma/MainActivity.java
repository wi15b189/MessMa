package technikum.at.messma;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import technikum.at.messma.Entities.AccessPoint;
import technikum.at.messma.Entities.GridPoint;
import technikum.at.messma.Entities.Stand;
import technikum.at.messma.Service.APIService;
import technikum.at.messma.Service.NavigationAPIService;
import technikum.at.messma.Views.PathView;

public class MainActivity extends Activity {

    WifiManager wifi;
    List<ScanResult> results;
    int size = 0;
    List<AccessPoint> accessPoints = new LinkedList<>();

    private TextView mTextMessage;
    private PathView navPath;

    private List<Stand> stands;
    private List<AccessPoint> knownAccessPoints;
    private List<GridPoint> tmpGridPoints;

    private NavigationAPIService APIService = new NavigationAPIService();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init
        stands = new ArrayList<>();
        //new getStandData().execute();
        stands = APIService.getStands();
        knownAccessPoints = new ArrayList<>();
        new getAPData().execute();
        //scanner = new APScanner(wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE), this);

        mTextMessage = (TextView) findViewById(R.id.message);
        navPath = findViewById(R.id.navpath);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled() == false)
        {
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }
        registerReceiver(new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context c, Intent intent)
            {
                results = wifi.getScanResults();
                size = results.size();
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

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
                        if(tempStand.getIdStand()== 1){
                            standGP = tempStand.getGridPoint();
                            accessPoints.clear();
                            wifi.startScan();
                            //Toast.makeText(this, "Scanning...." + size, Toast.LENGTH_SHORT).show();
                            try
                            {
                                size = size - 1;
                                while (size >= 0)
                                {
                                    AccessPoint ap = new AccessPoint(results.get(size).SSID, results.get(size).level);
                                    accessPoints.add(ap);
                                    //FILTERN
                                    //for (AccessPoint tmpAp:knownAccessPoints) {
                                    //    if(ap.getIdMac().equalsIgnoreCase(tmpAp.getIdMac())){
                                    //        accessPoints.add(ap);
                                    //    }
                                    //}
                                    size--;
                                }
                            }
                            catch (Exception e)
                            { }
                            List<GridPoint> tempGrids = APIService.navigate(standGP , accessPoints);
                            mTextMessage.setText(tempStand.getDescription());
                            mTextMessage.invalidate();
                            //new putNavData(standGP, accessPoints).execute();
                            navPath.drawPath(tempGrids);
                            navPath.postInvalidate();
                            return true;
                        }
                    }

                case R.id.s2:
                    for (Stand tempStand:stands
                            ) {
                        if(tempStand.getIdStand()==2){
                            standGP = tempStand.getGridPoint();
                            accessPoints.clear();
                            wifi.startScan();
                            //Toast.makeText(this, "Scanning...." + size, Toast.LENGTH_SHORT).show();
                            try
                            {
                                size = size - 1;
                                while (size >= 0)
                                {
                                    AccessPoint ap = new AccessPoint(results.get(size).SSID, results.get(size).level);
                                    for (AccessPoint tmpAp:knownAccessPoints) {
                                        if(ap.getIdMac().equalsIgnoreCase(tmpAp.getIdMac())){
                                            accessPoints.add(ap);
                                        }
                                    }
                                    size--;
                                }
                            }
                            catch (Exception e)
                            { }
                            List<GridPoint> tempGrids = APIService.navigate(standGP , accessPoints);
                            mTextMessage.setText("Navigating to Stand2");
                            mTextMessage.invalidate();
                            navPath.drawPath(tempGrids);
                            navPath.postInvalidate();
                            return true;
                        }
                    }
                case R.id.s3:
                    for (Stand tempStand:stands
                            ) {
                        if(tempStand.getIdStand()==3){
                            standGP = tempStand.getGridPoint();
                            accessPoints.clear();
                            wifi.startScan();
                            //Toast.makeText(this, "Scanning...." + size, Toast.LENGTH_SHORT).show();
                            try
                            {
                                size = size - 1;
                                while (size >= 0)
                                {
                                    AccessPoint ap = new AccessPoint(results.get(size).SSID, results.get(size).level);
                                    for (AccessPoint tmpAp:knownAccessPoints) {
                                        if(ap.getIdMac().equalsIgnoreCase(tmpAp.getIdMac())){
                                            accessPoints.add(ap);
                                        }
                                    }
                                    size--;
                                }
                            }
                            catch (Exception e)
                            { }
                            List<GridPoint> tempGrids = APIService.navigate(standGP , accessPoints);
                            mTextMessage.setText("Navigating to Stand2");
                            mTextMessage.invalidate();
                            navPath.drawPath(tempGrids);
                            navPath.postInvalidate();
                            return true;
                        }
                    }
                case R.id.s4:
                    for (Stand tempStand:stands
                            ) {
                        if(tempStand.getIdStand()==4){
                            standGP = tempStand.getGridPoint();
                            accessPoints.clear();
                            wifi.startScan();
                            //Toast.makeText(this, "Scanning...." + size, Toast.LENGTH_SHORT).show();
                            try
                            {
                                size = size - 1;
                                while (size >= 0)
                                {
                                    AccessPoint ap = new AccessPoint(results.get(size).SSID, results.get(size).level);
                                    for (AccessPoint tmpAp:knownAccessPoints) {
                                        if(ap.getIdMac().equalsIgnoreCase(tmpAp.getIdMac())){
                                            accessPoints.add(ap);
                                        }
                                    }
                                    size--;
                                }
                            }
                            catch (Exception e)
                            { }
                            List<GridPoint> tempGrids = APIService.navigate(standGP , accessPoints);
                            mTextMessage.setText("Navigating to Stand2");
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


    private class getAPData extends AsyncTask<Void, Void, List<AccessPoint>> {

        @Override
        protected List<AccessPoint> doInBackground(Void... voids) {
            APIService api = new APIService();
            knownAccessPoints = api.getAccessPoints();
            return api.getAccessPoints();
        }

        protected void onPostExecute(List<AccessPoint> result) {
            super.onPostExecute(result);
        }
    }

    private class getStandData extends AsyncTask<Void, Void, List<Stand>> {

        @Override
        protected List<Stand> doInBackground(Void... voids) {
            APIService api = new APIService();
            stands = api.getStands();
            return api.getStands();
        }

        protected void onPostExecute(List<Stand> result) {
            super.onPostExecute(result);
        }
    }

    private class putNavData extends AsyncTask<Void, Void, List<GridPoint>>{
        GridPoint standGP;
        List<AccessPoint> accessPoints;

        public putNavData(GridPoint standGP, List<AccessPoint> accessPoints) {
            this.standGP = standGP;
            this.accessPoints = accessPoints;
        }

        @Override
        protected List<GridPoint> doInBackground(Void... voids) {
            APIService api = new APIService();
            tmpGridPoints = api.putNavData(standGP, accessPoints);
            return api.putNavData(standGP, accessPoints);
        }
    }
}


