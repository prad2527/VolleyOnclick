package com.stp.volleyonclick.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;

import com.google.protobuf.Internal;
import com.stp.volleyonclick.R;
import com.stp.volleyonclick.model.InsideData;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterDetail extends ArrayAdapter<InsideData> implements View.OnClickListener {

    private List<InsideData> areaRecs = new ArrayList<>();
    private Context mContext;

    @Override
    public void onClick(View v) {

        Toast.makeText(mContext, "Show result", Toast.LENGTH_SHORT).show();

    }

    private static class ViewHolder {
        TextView txtName;
        TextView txtLat;
        TextView txtLong;
    }

    public ListAdapterDetail(@NonNull List<InsideData> area, Context context) {
        super(context, R.layout.layout_custom_data , area);
        this.areaRecs = area;
        this.mContext = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        InsideData areaRecs = getItem(position);


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_custom_data, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.txtLat = (TextView) convertView.findViewById(R.id.txt_lat);
            viewHolder.txtLong= (TextView) convertView.findViewById(R.id.txt_long);

            result=convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtName.setText(areaRecs.getArea_name());
        viewHolder.txtLat.setText(areaRecs.getLatitude());
        viewHolder.txtLong.setText(areaRecs.getLatitude());

        //for click on list
        convertView.setOnClickListener(this::onClick);

        return convertView;
    }

}
