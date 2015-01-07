package com.example.junquera.signaldroid;

import android.app.Activity;
import android.content.Context;
import android.telephony.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends Activity {

    public TextView t;

    public TelephonyManager telephonyManager;
    MyPhoneStateListener mpsl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t =  (TextView) findViewById(R.id.texto);
        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        mpsl = new MyPhoneStateListener(t, telephonyManager);
        telephonyManager.listen(mpsl, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        //CellSignalStrengthWcdma cssw = (CellSignalStrengthWcdma) ((CellInfoWcdma) telephonyManager.getAllCellInfo().get(0)).getCellSignalStrength();
    }

    @Override
    protected void onStart() {

        super.onStart();





    }

    public void editaSenal(){
        CellSignalStrengthWcdma cssw = (CellSignalStrengthWcdma) ((CellInfoWcdma) telephonyManager.getAllCellInfo().get(0)).getCellSignalStrength();
        t.setText(cssw.getDbm() + "");
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
    TextView t;
    TelephonyManager tm;

    public MyPhoneStateListener(TextView t, TelephonyManager tm){
        super();
        this.t = t;
        this.tm = tm;
    }

    public void onSignalStrengthsChanged(SignalStrength signalStrength){
        super.onSignalStrengthsChanged(signalStrength);

        int signalWcdma = ((CellInfoWcdma) tm.getAllCellInfo().get(0)).getCellSignalStrength().getAsuLevel();
        int signalGsm = signalStrength.getGsmSignalStrength();

        String signal = "WCDMA: ";

        if(signalWcdma == 99){
            signal += "=(";
        }else{
            signal += signalWcdma;
        }

        signal += "\nGSM: ";

        if(signalGsm == 99){
            signal += "=(";
        } else{
            signal += signalStrength.getGsmSignalStrength();
        }

        t.setText(signal);

    }
}