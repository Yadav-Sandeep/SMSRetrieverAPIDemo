package com.saluto.smsretrieverapidemo

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.phone.SmsRetriever

class MainActivity : AppCompatActivity(), MySMSBroadcastReceiver.OTPReceiveListener {
    val TAG = MainActivity::class.java.simpleName
    private var smsReceiver: MySMSBroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appSignatureHashHelper = AppSignatureHelper(this)

        // This code requires one time to get Hash keys do comment and share key

        // This code requires one time to get Hash keys do comment and share key
        Log.i(TAG, "HashKey: " + appSignatureHashHelper.getAppSignatures().get(0))

        startSMSListener();
    }

    private fun startSMSListener() {
        try {
            smsReceiver = MySMSBroadcastReceiver()
            smsReceiver!!.setOTPListener(this)
            val intentFilter = IntentFilter()
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
            this.registerReceiver(smsReceiver, intentFilter)
            val client = SmsRetriever.getClient(this)
            val task = client.startSmsRetriever()
            task.addOnSuccessListener {
                // API successfully started
            }
            task.addOnFailureListener {
                // Fail to start API
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onOTPReceived(otp: String?) {
        if (otp != null) {
            showToast(otp)
        }
    }

    override fun onOTPTimeOut() {
        TODO("Not yet implemented")
    }

    override fun onOTPReceivedError(error: String?) {
        TODO("Not yet implemented")
    }
}