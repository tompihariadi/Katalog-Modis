package com.keburugaring.tompihariadi.monitoring;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by tompihariadi on 30-Apr-15.
 */
public class SingleViewActivity extends ActionBarActivity {

    public static String formtype = "formtype";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        TextView tvdetail1 = (TextView)findViewById(R.id.tvdetail1);
        TextView tvdetail2 = (TextView)findViewById(R.id.tvdetail2);
        TextView tvdetail3 = (TextView)findViewById(R.id.tvdetail3);
        TextView tvdetail4 = (TextView)findViewById(R.id.tvdetail4);
        TextView tvdetail5 = (TextView)findViewById(R.id.tvdetail5);


        Intent intent = getIntent();
        String namafile = intent.getStringExtra("namafile");
        String namadata = intent.getStringExtra("namadata");
        String satelit = intent.getStringExtra("satelit");
        String tanggal = intent.getStringExtra("tanggal");
        String level = intent.getStringExtra("level");

        tvdetail1.setText(getString(R.string.nama)+namadata);
        tvdetail2.setText(getString(R.string.file)+namafile);
        tvdetail3.setText(getString(R.string.satelit)+satelit);
        tvdetail4.setText(getString(R.string.tanggal)+tanggal);
        tvdetail5.setText(getString(R.string.level)+level);

    }

    public static void toDetailActivity(Activity activity, String nama_file, String satelit, String tanggal_akusisi, String nama_data,
                                        String level){
        Intent intent = new Intent(activity, SingleViewActivity.class);
        intent.putExtra("namafile",nama_file);
        intent.putExtra("satelit",satelit);
        intent.putExtra("tanggal",tanggal_akusisi);
        intent.putExtra("namadata", nama_data);
        intent.putExtra("level", level);

        activity.startActivity(intent);
    }


}
