package aditya.com.weather;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Aditya Karthik on 3/8/2015.
 */
public class HandleJSON {
    private String country = "county";
    private String temperature = "temperature";
    private String humidity = "humidity";
    private String pressure = "pressure";
    private String urlString = null;
    private String description="description";
    private String temp_min="temp_min";
    private String temp_max="temp_max";

    public String getTemp_min() {
        float res_min= (float) (Float.parseFloat(temp_min));
        res_min=Math.round(res_min-273.15F);
        String result_min=Double.toString(res_min);
        return result_min;
    }

    public String getTemp_max() {
        float res_max= (float) (Float.parseFloat(temp_max));
        res_max=Math.round(res_max-273.15F);
        String result_max=Double.toString(res_max);
        return result_max;

    }
    public String getTemperature()
    {
        float res= (float) (Float.parseFloat(temperature));
        res=Math.round(res-273.15F);
        String result=Double.toString(res);
        return result;
    }

    public String getDescription() {
        return description;
    }

    public String getClimate() {
        return climate;
    }

    public volatile boolean parsingComplete = true;
    private String climate="climate";

    public HandleJSON(String url)
    {
        this.urlString = url;
    }

    public String getCountry()
    {
        return country;
    }



    public String getHumidity()
    {
        return humidity;
    }

    public String getPressure()
    {
        return pressure;
    }

    @SuppressLint("NewApi")
    public void readAndParseJSON(String in)
    {
        try
        {
            JSONObject reader=new JSONObject(in);
            JSONObject sys  = reader.getJSONObject("sys");
            country = sys.getString("country");

            JSONObject obj  = reader.getJSONObject("main");
            temperature = obj.getString("temp");
            pressure = obj.getString("pressure");
            humidity = obj.getString("humidity");
            temp_min=obj.getString("temp_min");
            temp_max=obj.getString("temp_max");

            JSONArray weather=reader.getJSONArray("weather");
            for(int i=0;i<weather.length();i++)
            {
                  JSONObject one = weather.getJSONObject(i);
                    //Pulling items from the array
                   climate = one.getString("main");
                 description = one.getString("description");

            }


            parsingComplete = false;


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void fetchJSON()
    {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    URL url=new URL(urlString);
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setReadTimeout(10000 /* milliseconds */);
                    httpURLConnection.setConnectTimeout(15000 /* milliseconds */);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoInput(true);
                    // Starts the query
                    httpURLConnection.connect();
                    InputStream stream = httpURLConnection.getInputStream();

                    String data = convertStreamToString(stream);

                    readAndParseJSON(data);
                    stream.close();

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        thread.start();
    }
    static String convertStreamToString(InputStream is)
    {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
