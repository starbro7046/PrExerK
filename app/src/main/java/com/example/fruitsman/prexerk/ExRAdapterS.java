package com.example.fruitsman.prexerk;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ExRAdapterS extends ArrayAdapter {
    public ExRAdapterS(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view, parent, false);
        }
        TextView name=convertView.findViewById(R.id.name);
        ImageButton viewDetail=convertView.findViewById(R.id.view);

        final Data_R myItem= (Data_R) getItem(position);
        name.setText(myItem.getName());

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("bbbbbbbbbbbbbb","select");
                ((SeleRou)(parent.getContext())).seleData(myItem.getName());
                ((SeleRou)(parent.getContext())).updateListView();
            }
        });
        viewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("bbbbbbbbbbbbbbbbbbb","view");
                Intent intent=new Intent(parent.getContext(),RouDetail.class);

                intent.putExtra("name",myItem.getName());
                intent.putExtra("exs",myItem.getExs());
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}