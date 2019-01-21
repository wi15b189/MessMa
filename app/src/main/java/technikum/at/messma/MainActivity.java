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
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import technikum.at.messma.Entities.AccessPoint;
import technikum.at.messma.Entities.GridPoint;
import technikum.at.messma.Entities.Stand;
import technikum.at.messma.Service.APIService;
import technikum.at.messma.Views.PathView;

public class MainActivity extends Activity {

    //declare wifi stuff
    WifiManager wifi;
    List<ScanResult> results;
    int size = 0;
    List<AccessPoint> accessPoints;

    //declare GUI stuff
    private TextView mTextMessage;
    private PathView navPath;

    //declare shared data
    private List<Stand> stands;
    private List<AccessPoint> knownAccessPoints;
    private List<GridPoint> tmpGridPoints;

    //declare api service helper
    private APIService api = new APIService();
    private Navigate nav;
    private Runnable action;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();
        setContentView(R.layout.activity_main);

        //init bindings
        mTextMessage = (TextView) findViewById(R.id.message);
        navPath = findViewById(R.id.navpath);

        //init shared data
        stands = new ArrayList<>();
        new getStandData().execute();
        knownAccessPoints = new ArrayList<>();
        new getAPData().execute();


        //Init wifi
        wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled() == false) {
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                scanWifi();
                //unregisterReceiver(this);
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        //init buttons
        /*
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/
        final Button button1 = findViewById(R.id.button_1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                wifi.startScan();
                if (accessPoints != null) {
                    nav = new Navigate(1);
                    nav.execute();
                }
            }
        });
        final Button button2 = findViewById(R.id.button_2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                wifi.startScan();
                if (accessPoints != null) {
                    nav = new Navigate(2);
                    nav.execute();
                }
            }
        });
    }

    private void scanWifi() {
        results = wifi.getScanResults();
        size = results.size();
        accessPoints = new ArrayList<>();
        //Toast.makeText(this, "Scanning...." + size, Toast.LENGTH_SHORT).show();
        size = size - 1;
        while (size >= 0) {
            AccessPoint ap = new AccessPoint(results.get(size).BSSID, results.get(size).level);
            //accessPoints.add(ap); //nur einschalten wenn filtern aus ist
            //FILTERN
            for (AccessPoint tmpAp : knownAccessPoints) {
                if (tmpAp.getIdMac().equals(ap.getIdMac())) {
                    accessPoints.add(ap);
                }
            }
            size--;
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.s1:
                    new Navigate(1).execute();
                    return true;
                case R.id.s2:
                    new Navigate(2).execute();
                case R.id.s3:
                    new Navigate(3).execute();
                    return true;
                case R.id.s4:
                    new Navigate(4).execute();
                    return true;
            }
            return false;
        }
    };


    private class getAPData extends AsyncTask<Void, Void, List<AccessPoint>> {

        @Override
        protected List<AccessPoint> doInBackground(Void... voids) {
            knownAccessPoints = api.getAccessPoints();
            return knownAccessPoints;
        }

        protected void onPostExecute(List<AccessPoint> result) {
            super.onPostExecute(result);
        }
    }

    private class getStandData extends AsyncTask<Void, Void, List<Stand>> {

        @Override
        protected List<Stand> doInBackground(Void... voids) {
            stands = api.getStands();
            return stands;
        }

        protected void onPostExecute(List<Stand> result) {
            super.onPostExecute(result);
        }
    }

    private class Navigate extends AsyncTask<Void, Integer, Void> {
        int standid;

        public Navigate(int standid) {
            this.standid = standid;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Toast.makeText(getApplicationContext(), "Setup Navigation...", Toast.LENGTH_SHORT).show();

            GridPoint standGP = null;
            for (final Stand tempStand : stands
            ) {
                if (tempStand.getIdStand() == standid) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Stuff that updates the UI
                            mTextMessage.setText(tempStand.getDescription());
                            mTextMessage.invalidate();
                            wifi.startScan();
                        }
                    });
                    standGP = tempStand.getGridPoint();
                    //tmpGridPoints.clear();
                    //Toast.makeText(getApplicationContext(), "Navigating...", Toast.LENGTH_SHORT).show();

                    tmpGridPoints = api.putNavData(standGP, accessPoints);
                    if (tmpGridPoints.size() > 0) {
                        navPath.drawPath(tmpGridPoints);
                        navPath.postInvalidate();
                    }
                    return null;
                }
            }
            return null;

        }

        protected void onProgressUpdate(Integer... progress) {
            //Toast.makeText(getApplicationContext(), "Scanning "+ progress[0]+ "Access Points", Toast.LENGTH_SHORT).show();
        }
    }


}
