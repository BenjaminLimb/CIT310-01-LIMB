package dough.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;

import org.quickconnect.json.JSONException;
import org.quickconnect.json.JSONInputStream;
import org.quickconnect.json.JSONOutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DoughnutClientActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	update();
 
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    }
    @Override
    public void onResume(){
    	update();
    }
    public void update(){
    	TextView display = (TextView)findViewById(R.id.doughnutDisplay);
    	display.setText("Loading...");
    	
    	Calendar today = Calendar.getInstance();
    	int dayOfWeek = today.get(Calendar.DAY_OF_WEEK);
    	
    	try {
			Socket toServer = new Socket("10.17.174.60",8080);
			JSONInputStream in = new JSONInputStream(toServer.getInputStream());
			JSONOutputStream out = new JSONOutputStream(toServer.getOutputStream());
			out.writeObject(dayOfWeek);
			String doughnut = (String) in.readObject();
			display.setText(doughnut);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
}