package com.keburugaring.tompihariadi.monitoring.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.keburugaring.tompihariadi.monitoring.R;
import com.keburugaring.tompihariadi.monitoring.model.Katalog;

import java.util.List;

/**
 * Created by tompihariadi on 09-Feb-15.
 */
public class KatalogAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<Katalog> katalogItems;

    public KatalogAdapter(Activity activity, List<Katalog> katalogItems){
        this.activity = activity;
        this.katalogItems = katalogItems;
    }
    @Override
    public int getCount() {
        return katalogItems.size();
    }

    @Override
    public Object getItem(int location) {
        return katalogItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (layoutInflater == null)
            layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.listitem,null);
        TextView namadata = (TextView)convertView.findViewById(R.id.srcnamadata);
        TextView satelit = (TextView)convertView.findViewById(R.id.srcsatelit);
        TextView tanggal = (TextView)convertView.findViewById(R.id.srctanggal);

        Katalog kl = katalogItems.get(position);
        namadata.setText(kl.getNama_file());
        satelit.setText(kl.getSatelit());
        tanggal.setText(kl.getTanggal_akusisi());

        return convertView;
    }
}
