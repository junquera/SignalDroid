package es.junquera.signaldroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;


public class MainActivity extends Activity {


    private TelephonyManager telephonyManager;
    private MyPhoneStateListener mpsl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        mpsl = new MyPhoneStateListener((TextView) findViewById(R.id.wcdma),(TextView) findViewById(R.id.gsm), telephonyManager);
        telephonyManager.listen(mpsl, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

    }

    @Override
    protected void onStart() {

        super.onStart();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}

class MyPhoneStateListener extends PhoneStateListener{
    TextView wcdma, gsm;
    TelephonyManager tm;

    public MyPhoneStateListener(TextView wcdma, TextView gsm, TelephonyManager tm){
        super();
        this.wcdma = wcdma;
        this.tm = tm;
        this.gsm = gsm;
    }

    public void onSignalStrengthsChanged(SignalStrength signalStrength){
        super.onSignalStrengthsChanged(signalStrength);


        List<CellInfo> lci = tm.getAllCellInfo();



        int signalWcdma = lci == null? 99 : ((CellInfoWcdma) lci.get(0)).getCellSignalStrength().getAsuLevel();
        int signalGsm = signalStrength.getGsmSignalStrength();


        if(signalWcdma == 99){
            wcdma.setText("=(");
        }else{
            wcdma.setText(signalWcdma + "");
        }

        if(signalGsm == 99){
            gsm.setText("=(");
        } else{
            gsm.setText(signalGsm + "");
        }


    }
}