package technikum.at.messma.Service;


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
import java.util.LinkedList;
import java.util.List;

import technikum.at.messma.Entities.AccessPoint;
import technikum.at.messma.Entities.GridPoint;
import technikum.at.messma.Entities.Stand;

public class APIService  {
    private AccessPoint tempAP;
    private List<AccessPoint> tempAPList;
    private Stand tempStand;
    private List<Stand> tempStandList;
    private static final String TAG = APIService.class.getSimpleName();

    public List<AccessPoint> getAccessPoints(){
        tempAPList = new LinkedList<AccessPoint>();
        String URL = "http://192.168.0.233:9000/api/getAllAccessPoints";
        String jsonStr = makeServiceCall(URL);

        if (jsonStr!=null){
            try{
                JSONObject jsonObj = new JSONObject(jsonStr);
                // Getting JSON Array node
                JSONArray data = jsonObj.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                    JSONObject d = data.getJSONObject(i);
                    String mac = d.getString("mac");
                    Integer type = d.getInt("type");
                    Boolean activity = d.getBoolean("activity");
                    tempAP = new AccessPoint(mac,type,activity);
                    tempAPList.add(tempAP);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
        if (tempAPList != null){
            return tempAPList;
        }else {
            return null;
        }
    }

    public List<Stand> getStands(){
        tempStandList = new LinkedList<Stand>();
        String URL = "http://192.168.0.233:9000/api/getAllStands";
        String jsonStr = makeServiceCall(URL);

        if (jsonStr!=null){
            try{
                JSONObject jsonObj = new JSONObject(jsonStr);
                // Getting JSON Array node
                JSONArray data = jsonObj.getJSONArray("data");

                for (int i = 0; i < data.length(); i++) {
                    JSONObject d = data.getJSONObject(i);
                    int id = d.getInt("id");
                    String name = d.getString("name");
                    String description = d.getString("description");
                    String logo = d.getString("logo");
                    JSONArray gridPointData = jsonObj.getJSONArray("GridPoint");
                    GridPoint tmpGP = null;
                    for (int j = 0; j < gridPointData.length(); j++) {
                        JSONObject g = gridPointData.getJSONObject(j);
                        String idGP = g.getString("id");
                        int posx = g.getInt("posx");
                        int posy = g.getInt("posy");
                        tmpGP = new GridPoint(idGP, posx, posy);
                    }
                    tempStand = new Stand(id, name,description,logo,tmpGP);

                    tempStandList.add(tempStand);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
        if (tempStandList != null){
            return tempStandList;
        }else {
            return null;
        }
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

    public List<GridPoint> putNavData(GridPoint standGP, List<AccessPoint> accessPoints) {
        //Build Send string for JSON
        JSONObject postData = new JSONObject();
        try{
            postData.put("destination", standGP.getIdGridPoint());
            JSONArray arr = generateJSONArray(accessPoints);
            postData.put("ReceivedSignals", arr);
            String JSONString = postData.toString();


        String data = "";
        String URL = "http://192.168.0.233:9000/api/getPosition";
        try{
            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();

            String JSONstring = "{\"destination\": \"1\", \"ReceivedSignals\":[" +
                    "{\"mac\": \"84:78:ac:b8:bb:b0\",\"power\": \"81\"" +
                    "},{\"mac\": \"84:78:ac:b8:d4:80\",\"power\": \"83\"}," +
                    "{\"mac\": \"84:78:ac:b8:e2:f0\",\"power\": \"20\"" + "}]}";

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes("Data=" + JSONstring);
            wr.flush();
            wr.close();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            data = convertStreamToString(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONArray generateJSONArray(List<AccessPoint> ListAP) {
        JSONArray temp = new JSONArray();
        try{
            for (AccessPoint AP : ListAP){
                JSONObject postData = new JSONObject();
                postData.put("mac", AP.getIdMac());
                postData.put("power", AP.getSignal());
                temp.put(postData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
