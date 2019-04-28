package technikum.at.messma.Service;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import technikum.at.messma.Entities.AccessPoint;
import technikum.at.messma.Entities.GridPoint;
import technikum.at.messma.Entities.Stand;


public class APIService {
    String baseURL;

    private AccessPoint tempAP;
    private List<AccessPoint> tempAPList;
    private Stand tempStand;
    private List<Stand> tempStandList;
    private static final String TAG = APIService.class.getSimpleName();

    public APIService(String baseUrl) {
        this.baseURL = baseUrl+"/api/";
    }

    public List<AccessPoint> getAccessPoints() {
        tempAPList = new LinkedList<>();
        String URL = baseURL + "getAllAccessPoints";
        String jsonStr = makeServiceCall(URL);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                // Getting JSON Array node
                JSONArray data = jsonObj.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                    JSONObject d = data.getJSONObject(i);
                    String mac = d.getString("mac");
                    Integer type = d.getInt("type");
                    Boolean activity = d.getBoolean("activity");
                    tempAP = new AccessPoint(mac, type, activity);
                    tempAPList.add(tempAP);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
        return tempAPList;
    }

    public List<Stand> getStands() {
        tempStandList = new LinkedList<>();
        GridPoint tmpGP;
        String URL = baseURL + "getAllStands";
        String jsonStr = makeServiceCall(URL);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                // Getting JSON Array node
                JSONArray data = jsonObj.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                    JSONObject d = data.getJSONObject(i);
                    int id = d.getInt("id");
                    String name = d.getString("name");
                    String description = d.getString("description");
                    String logo = d.getString("logo");
                    JSONObject gridPointData = d.getJSONObject("gridPoint");
                    String idGP = gridPointData.getString("id");
                    int posx = gridPointData.getInt("posX");
                    int posy = gridPointData.getInt("posY");
                    tmpGP = new GridPoint(idGP, posx, posy);
                    tempStand = new Stand(id, name, description, logo, tmpGP);
                    tempStandList.add(tempStand);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
        return tempStandList;
    }

    public List<GridPoint> putNavData(GridPoint standGP, List<AccessPoint> accessPoints) {
        String URL = baseURL + "getPosition";
        String response = null;
        try {
            //setup connection
            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.connect();
            // send data
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(ObjectToJsonString(standGP, accessPoints));
            wr.flush();
            wr.close();
            //read data
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return responseStringToObject(response, standGP);
    }

    private String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @NonNull
    private String ObjectToJsonString(GridPoint standGP, List<AccessPoint> accessPoints) {
        //TODO JSON String aus standgp und APs erstellen
        JSONObject destination = new JSONObject();
        JSONArray temp = new JSONArray();
        try {
            destination.put("destination", standGP.getIdGridPoint());
            for (AccessPoint AP : accessPoints) {
                JSONObject postData = new JSONObject();
                postData.put("mac", AP.getMAC());
                postData.put("power", String.valueOf(AP.getAverageSignal()));
                temp.put(postData);
            }
            destination.put("ReceivedSignals", temp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return destination.toString();
    }

    @NonNull
    private List<GridPoint> responseStringToObject(String response, GridPoint standGP) {
        //TODO Response formatieren und zurück geben
        List<GridPoint> tmpGP = new ArrayList<>();
        if (response != null) {
            try {
                JSONObject jsonObj = new JSONObject(response);
                // Getting JSON Array node
                JSONArray data = jsonObj.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject d = data.getJSONObject(i);
                    String idGP = d.getString("id");
                    int posx = (int) (d.getInt("posY") * 1);
                    int posy = (int) (d.getInt("posX") * 1);
                    tmpGP.add(new GridPoint(idGP, posx, posy));
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
        return tmpGP;
        /*
        List<GridPoint> temp = new LinkedList<GridPoint>();
        temp.add(new GridPoint("1", 800, 80));
        temp.add(new GridPoint("2", 850, 200));
        temp.add(new GridPoint("3", 850, 300));
        temp.add(standGP);
        return temp;*/
    }

}
