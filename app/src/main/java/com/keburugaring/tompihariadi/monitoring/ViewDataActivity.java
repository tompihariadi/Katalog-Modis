package com.keburugaring.tompihariadi.monitoring;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.File;

/**
 * Created by tompihariadi on 04-Mar-15.
 */
public class ViewDataActivity extends ActionBarActivity {
    String link, namadata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdata);
        Intent intent = getIntent();
        link = intent.getStringExtra("link");
        namadata = intent.getStringExtra("nama");

        ProgressDialog pd = ProgressDialog.show(ViewDataActivity.this, "", "Sedang memuat....",true);
        WebView katalog = (WebView)findViewById(R.id.webViewImage);
        katalog.getSettings().setJavaScriptEnabled(true);
        katalog.getSettings().setBuiltInZoomControls(true);
        katalog.getSettings().setLoadWithOverviewMode(true);
        katalog.getSettings().setUseWideViewPort(true);
        katalog.loadUrl(link);
        katalog.setWebViewClient(new homeClient(pd));
        Toast.makeText(getApplicationContext(), link, Toast.LENGTH_LONG).show();

    }

    private class homeClient extends WebViewClient {

        private ProgressDialog pd;

        public homeClient(ProgressDialog pd) {
            this.pd = pd;
            pd.setCancelable(true);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished (WebView view, String url) {
            if (pd.isShowing()) {
                pd.dismiss();
            }
        }

    }
    public static void toViewDataActivity(Activity activity, String nama_file, String satelit, String tanggal_akusisi){
        Intent intent = new Intent(activity, ViewDataActivity.class);
        String url = "http://modis-catalog.lapan.go.id/monitoring/";
        String tahun = tanggal_akusisi.substring(0,4);
        String bulan = tanggal_akusisi.substring(5,7);
        String tanggal = tanggal_akusisi.substring(8,10);
        String jam = tanggal_akusisi.substring(11, 13);
        String hari = null;
        int dbtgl = Integer.valueOf(tanggal);
        double dbjam = Double.valueOf(jam);
        int dbhari;
        String kodeimg = null;
        if (dbjam > 0 && dbjam < 13){
            kodeimg = ".truecolor.jpg";
            hari = tanggal;
        }
        if (dbjam == 0){
            kodeimg = ".band31.jpg";
            dbhari = dbtgl-1;
            hari = String.valueOf(dbhari);
        }

        String namafile = nama_file.substring(0,13);

        String urlgambar = url+satelit+"/"+tahun+"_"+bulan+"_"+hari+"_"+
                nama_file.substring(5,8)+"/images/"+nama_file.substring(0,13)+kodeimg;

        intent.putExtra("nama",namafile);
        intent.putExtra("link",urlgambar);

        activity.startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.keburugaring.tompihariadi.monitoring.R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_save:
                downloadfile(link);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void downloadfile(String link) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/Katalog Modis");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager)ViewDataActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(link);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Download "+namadata)
                .setDescription("Data modis katalog.")
                .setDestinationInExternalPublicDir("/Katalog Modis", namadata+".jpg");

        mgr.enqueue(request);
    }
}
