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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import technikum.at.messma.Entities.AccessPoint;
import technikum.at.messma.Entities.GridPoint;
import technikum.at.messma.Entities.Stand;
import technikum.at.messma.Service.APIService;
import technikum.at.messma.Service.AccessPointKeeper;
import technikum.at.messma.Service.RemoteItHandler;
import technikum.at.messma.Views.PathView;

public class MainActivity extends Activity {

    //declare wifiManager stuff
    WifiManager wifiManager;
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
    private AccessPointKeeper apKeeper;

    //declare api service helper
    private APIService api;
    private String baseUrl;
    private RemoteItHandler remoteUrlHandler;
    private Navigate nav;

    //declare button stuff
    private LinearLayout linearLayout;
    private HorizontalScrollView horizontalScrollView;
    private View.OnClickListener buttonClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();
        setContentView(R.layout.activity_main);

        //init bindings
        mTextMessage = (TextView) findViewById(R.id.message);
        navPath = findViewById(R.id.navpath);

        //init shared data
        remoteUrlHandler = new RemoteItHandler();
        new getServerUrl().execute();

        //Init wifiManager
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled() == false) {
            Toast.makeText(getApplicationContext(), "wifiManager is disabled..making it enabled", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                scanWifi();
                //unregisterReceiver(this);
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));


        //init buttons
        generateHorizontalView();
        buttonClick = generateButtonListener();
        LinearLayout horizontalLayout = findViewById(R.id.rootContainer);

        if (horizontalLayout != null) {
            horizontalLayout.addView(horizontalScrollView);
            Log.d("Info", "added");
        }
    }

    private void generateHorizontalView() {
        horizontalScrollView = new HorizontalScrollView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        horizontalScrollView.setLayoutParams(layoutParams);
        linearLayout = new LinearLayout(this);

        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(linearParams);

        horizontalScrollView.addView(linearLayout);
    }

    private void generateButtons() {
        for (Stand stand : stands) {
            Button tmpButton = new Button(this);
            tmpButton.setLayoutParams(getMyLayoutParams());
            tmpButton.setId(stand.getIdStand());
            tmpButton.setOnClickListener(buttonClick);
            tmpButton.setBackgroundResource(stand.getBackgroundSource());
            linearLayout.addView(tmpButton);
        }
    }

    private View.OnClickListener generateButtonListener() {
        buttonClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiManager.startScan();
                try {
                    //Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (accessPoints != null) {
                    nav = new Navigate(v.getId());
                    nav.execute();
                }
            }
        };
        return buttonClick;
    }

    private ViewGroup.LayoutParams getMyLayoutParams() {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.setMargins(30, 20, 30, 0);
        param.gravity = Gravity.CENTER;
        return param;
    }


    private void scanWifi() {
        results = wifiManager.getScanResults();
        size = results.size();
        accessPoints = new ArrayList<>();
        Toast.makeText(this, "Scanning...." + size, Toast.LENGTH_SHORT).show();
        size = size - 1;
        while (size >= 0) {
            AccessPoint ap = new AccessPoint(results.get(size).BSSID, results.get(size).level);
            //accessPoints.add(ap); //nur einschalten wenn filtern aus ist
            //FILTERN
            for (AccessPoint tmpAp : knownAccessPoints) {
                if (tmpAp.getMAC().equals(ap.getMAC())) {
                    accessPoints.add(ap);
                }
            }
            size--;
        }
    }

    private class getServerUrl extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String temp = remoteUrlHandler.getRemoteUrl();
            return temp;
        }

        @Override
        protected void onPostExecute(String result) {
            //super.onPostExecute(result);
            baseUrl = result;
            api = new APIService(baseUrl);
            stands = new ArrayList<>();
            new getStandData().execute();
            knownAccessPoints = new ArrayList<>();
            new getAPData().execute();
        }
    }


    private class getAPData extends AsyncTask<Void, Void, List<AccessPoint>> {

        @Override
        protected List<AccessPoint> doInBackground(Void... voids) {
            knownAccessPoints = api.getAccessPoints();
            return knownAccessPoints;
        }

        @Override
        protected void onPostExecute(List<AccessPoint> result) {
            //super.onPostExecute(result);
        }
    }

    private class getStandData extends AsyncTask<Void, Void, List<Stand>> {

        @Override
        protected List<Stand> doInBackground(Void... voids) {
            stands = api.getStands();
            return stands;
        }

        @Override
        protected void onPostExecute(List<Stand> result) {
            generateButtons();
            //super.onPostExecute(result);
        }
    }

    private class Navigate extends AsyncTask<Void, Integer, Stand> {
        int standid;

        public Navigate(int standid) {
            this.standid = standid;
        }

        @Override
        protected Stand doInBackground(Void... voids) {
            //Toast.makeText(getApplicationContext(), "Setup Navigation...", Toast.LENGTH_SHORT).show();

            GridPoint standGP = null;
            for (final Stand tempStand : stands
            ) {
                if (tempStand.getIdStand() == standid) {
                    //run in UI war mal hier
                    standGP = tempStand.getGridPoint();
                    //tmpGridPoints.clear();
                    //Toast.makeText(getApplicationContext(), "Navigating...", Toast.LENGTH_SHORT).show();

                    tmpGridPoints = api.putNavData(standGP, accessPoints);
                    if (tmpGridPoints.size() > 0) {
                        navPath.drawPath(tmpGridPoints);
                        navPath.postInvalidate();
                    }
                    return tempStand;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Stand tmpStand) {
            //super.onPostExecute(tmpStand);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Stuff that updates the UI
                    if (tmpGridPoints != null && tmpGridPoints.size() > 1) {
                        mTextMessage.setText("Navigation nach " + tmpStand.getName() + " gestartet. - " + tmpStand.getDescription());
                        mTextMessage.invalidate();
                    } else {
                        mTextMessage.setText("Standort konnte nicht ermittelt werden. Stand " + tmpStand.getName() + " wird angezeigt.");
                        mTextMessage.invalidate();
                    }
                }
            });
        }
    }


}
