package com.keburugaring.tompihariadi.monitoring;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.keburugaring.tompihariadi.monitoring.adapter.KatalogAdapter;
import com.keburugaring.tompihariadi.monitoring.model.Katalog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tompihariadi on 06-Feb-15.
 */
public class SearchbyDate extends ActionBarActivity {
    private static final String TAG = SearchbyDate.class.getSimpleName();
    private ProgressDialog progressDialog;
    String urlJsonarray = "http://modis-catalog.lapan.go.id/monitoring/searchbydate?keyword=";
    ListView lvkatalog;
    int current_page = 1;
    String jsonResponse, searchkey,url;
    private ArrayList<Katalog> katalogItems = new ArrayList<Katalog>();
    private KatalogAdapter katalogAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.katalog_layout);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        Intent intent = getIntent();
        searchkey = intent.getStringExtra("keyword");

        makeJsonArrayRequest();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Data tanggal : " + searchkey);
        actionBar.setHomeButtonEnabled(true);

        lvkatalog = (ListView)findViewById(R.id.lvkatalog);
        katalogAdapter = new KatalogAdapter(SearchbyDate.this, katalogItems);
        lvkatalog.setAdapter(katalogAdapter);

        View loadingfooter = ((LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loading_footer,null,false);
        lvkatalog.addFooterView(loadingfooter);
        lvkatalog.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                int threshold = 1;
                int count = lvkatalog.getCount();

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (lvkatalog.getLastVisiblePosition() >= count
                            - threshold) {
                        // Execute LoadMoreDataTask AsyncTask
                        loadMoreJsonData();
                    }
                }

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }


        });
        lvkatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                KatalogActivity.showData(SearchbyDate.this,katalogItems.get(position).getNama_file(),
                        katalogItems.get(position).getSatelit(),katalogItems.get(position).getTanggal_akusisi(),
                        katalogItems.get(position).getNama_data(),katalogItems.get(position).getLevel());
            }
        });

    }

    private void makeJsonArrayRequest() {
        showpDialog();
        String urlJsonarray = "http://modis-catalog.lapan.go.id/monitoring/searchbydate?keyword="+searchkey;


        JsonArrayRequest req = new JsonArrayRequest(urlJsonarray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject katalog = (JSONObject) response.get(i);

                                String nama_file = katalog.getString("nama_file");
                                String satelit = katalog.getString("satelit");
                                String tanggal_akusisi = katalog.getString("tanggal_akusisi");
                                String nama_data = katalog.getString("nama_data");
                                String level = katalog.getString("level");
                                katalogItems.add(new Katalog(nama_file,satelit,tanggal_akusisi, nama_data,level));
                            }

                            katalogAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
                    }




                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void loadMoreJsonData() {
        showpDialog();
        current_page += 1;
        String urlJsonarray = "http://modis-catalog.lapan.go.id/monitoring/searchbydate?keyword="+searchkey+"&page="+current_page;

        JsonArrayRequest req = new JsonArrayRequest(urlJsonarray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject katalog = (JSONObject) response.get(i);

                                String nama_file = katalog.getString("nama_file");
                                String satelit = katalog.getString("satelit");
                                String tanggal_akusisi = katalog.getString("tanggal_akusisi");
                                String nama_data = katalog.getString("nama_data");
                                String level = katalog.getString("level");
                                katalogItems.add(new Katalog(nama_file,satelit,tanggal_akusisi, nama_data,level));
                            }
                            katalogAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
                    }




                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void hidepDialog() {
        if (progressDialog.isShowing())progressDialog.dismiss();
    }

    private void showpDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SearchbyDate.this, MainActivity.class);
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
