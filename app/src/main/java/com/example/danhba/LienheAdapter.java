package com.example.danhba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class LienheAdapter extends ArrayAdapter<LienheActivity>{
    private Context context;
    private int resource;
    private List<LienheActivity> arrayLienhe;

    public LienheAdapter( Context context, int resource,  List<LienheActivity> objects) {
        super(context, resource, objects);
        this.context =context;
        this.resource =resource;
        this.arrayLienhe =objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_activity,parent,false);
            viewHolder.imgAvatar =(ImageView) convertView.findViewById(R.id.img_avatar);
            viewHolder.tvtenlienhe =(TextView) convertView.findViewById(R.id.tv_lienhe);
            viewHolder.tvsdt =(TextView) convertView.findViewById(R.id.tv_sdt);

            convertView.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder) convertView.getTag();
        }
        LienheActivity contact = arrayLienhe.get(position);

        viewHolder.tvtenlienhe.setText(contact.getmName());
        viewHolder.tvsdt.setText(contact.getmNumber());

        if (contact.isNam()){
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.nam);
        }else{
            viewHolder.imgAvatar.setBackgroundResource(R.drawable.nu);
        }

        return convertView;
    }
    public void removeContact(int position) {
        arrayLienhe.remove(position);
        notifyDataSetChanged();
    }

    public class ViewHolder{
        ImageView imgAvatar;
        TextView tvtenlienhe;
        TextView tvsdt;

    }
}
