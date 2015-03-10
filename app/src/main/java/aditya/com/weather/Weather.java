package aditya.com.weather;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Weather extends ActionBarActivity {
    private String url="http://api.openweathermap.org/data/2.5/weather?q=";
    TextView tv,count,temp,desc,min,max;
    HandleJSON handleJSON;
    public String locationURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        tv=(TextView)findViewById(R.id.textView);
        Intent intent=getIntent();
        String locationalUrl = intent.getStringExtra(MainActivity.URL);
        locationURL=locationalUrl;
        count=(TextView)findViewById(R.id.textView);
        temp=(TextView)findViewById(R.id.textView3);
        desc=(TextView)findViewById(R.id.description);
        min=(TextView)findViewById(R.id.min);
        max=(TextView)findViewById(R.id.max);
        String finalUrl=url+locationalUrl;
        Log.d("Tag", finalUrl);
        getWeather(finalUrl);
    }

    public void getWeather(String finalUrl) {
        handleJSON=new HandleJSON(finalUrl);
        handleJSON.fetchJSON();
        while(handleJSON.parsingComplete);
        count.setText(locationURL);
        temp.setText(handleJSON.getTemperature());
        desc.setText(handleJSON.getDescription());
        min.setText(handleJSON.getTemp_min());
        max.setText(handleJSON.getTemp_max());
        String weather=handleJSON.getClimate();
        RelativeLayout ll=(RelativeLayout)findViewById(R.id.myLinearLayout);
        if(weather.equalsIgnoreCase("clouds")) {
            ll.setBackgroundResource(R.drawable.clearsky);
        }
        else if(weather.equalsIgnoreCase("rain"))
        {
            ll.setBackgroundResource(R.drawable.rainy);
        }
        else if(weather.equalsIgnoreCase("snow"))
        {
            ll.setBackgroundResource(R.drawable.snow);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void back(View view)
    {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }

    public void changeConversion(View view)
    {
        String changeTemp=temp.getText().toString();

    }
}
