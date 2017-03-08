package app.mamac.albadiya;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.telephony.gsm.SmsManager;



/**
 * Created by T on 29-11-2016.
 */

public class EmailActivity extends Activity {
    Button btnSendSms;
    EditText txtMessage;
    EditText txtPhoneNo;
   @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.email_activity);
       btnSendSms = (Button) findViewById(R.id.btnSendSMS);
       txtMessage = (EditText) findViewById(R.id.txtMessage);
       txtPhoneNo = (EditText) findViewById(R.id.txtPhoneNo);

       btnSendSms.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String phoneNo = txtPhoneNo.getText().toString();
               String message = txtMessage.getText().toString();
               if (phoneNo.length()>0 && message.length()>0)
                   sendSms(phoneNo,message);
               else
                   Toast.makeText(EmailActivity.this,"please enter both number and message",Toast.LENGTH_SHORT).show();
           }
       });


   }

   private void sendSms(String phoneNumber,String message){
       String SENT = "SMS_SENT";
       String DELIVERED = "SMS_DELIVERED";

       PendingIntent sentPI = PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
       PendingIntent deliveredPI = PendingIntent.getBroadcast(this,0,new Intent(DELIVERED),0);
       registerReceiver(new BroadcastReceiver() {
           @Override
           public void onReceive(Context arg0, Intent arg1) {
               switch (getResultCode())
               {
                   case Activity.RESULT_OK:
                       Toast.makeText(EmailActivity.this,"SMS Sent",Toast.LENGTH_SHORT).show();
                       break;
                   case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                       Toast.makeText(EmailActivity.this,"Generic failure",Toast.LENGTH_SHORT).show();
                       break;
                   case SmsManager.RESULT_ERROR_NO_SERVICE:
                       Toast.makeText(EmailActivity.this,"No service",Toast.LENGTH_SHORT).show();
                       break;
                   case SmsManager.RESULT_ERROR_NULL_PDU:
                       Toast.makeText(EmailActivity.this,"Null PDU",Toast.LENGTH_SHORT).show();
                       break;
                   case SmsManager.RESULT_ERROR_RADIO_OFF:
                       Toast.makeText(EmailActivity.this,"Radio OFF",Toast.LENGTH_SHORT).show();
                       break;
               }

           }
       },new IntentFilter(SENT));

       registerReceiver(new BroadcastReceiver() {
           @Override
           public void onReceive(Context arg0, Intent arg1) {
               switch (getResultCode()){
                   case Activity.RESULT_OK:
                       Toast.makeText(EmailActivity.this,"SMS delivered",Toast.LENGTH_SHORT).show();
                       break;
                   case Activity.RESULT_CANCELED:
                       Toast.makeText(EmailActivity.this, "SMS not delivered",Toast.LENGTH_SHORT).show();
                       break;
               }

           }
       }, new IntentFilter(DELIVERED));
       SmsManager sms = SmsManager.getDefault();
       sms.sendTextMessage(phoneNumber,null,message,sentPI,deliveredPI);
}



}


