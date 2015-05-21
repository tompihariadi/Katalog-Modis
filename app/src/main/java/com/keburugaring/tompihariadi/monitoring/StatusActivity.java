package com.keburugaring.tompihariadi.monitoring;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by tompihariadi on 24-Feb-15.
 */
public class StatusActivity extends ActionBarActivity {
    private static String TAG = StatusActivity.class.getSimpleName();
    String url = "http://modis-catalog.lapan.go.id/monitoring/status";
    String tag_string_req = "string_raq";
    String result = "";
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statuslayout);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        status = (TextView)findViewById(R.id.tvstatus);

    }
    public String getString(final VolleyCallback callback){
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                result = response;
                callback.onSuccess(result);
                pDialog.hide();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq,tag_string_req);
        return result;
    }
    public interface VolleyCallback{
        void onSuccess(String result);
    }

    @Override
    public void onResume() {
        super.onResume();
        getString(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                status.setText(Html.fromHtml(result));
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
