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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ExDAdapter extends ArrayAdapter {
    public ExDAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }
        TextView name=convertView.findViewById(R.id.name);
        ImageButton delete=convertView.findViewById(R.id.delete);
        ImageButton edit=convertView.findViewById(R.id.edit);

        final Data_D myItem= (Data_D) getItem(position);
        name.setText(myItem.getName());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("bbbbbbbbbbbbbb","delete");
                ((SettingDy)(parent.getContext())).deleteData(myItem.getName());
                ((SettingDy)(parent.getContext())).updateListView();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("bbbbbbbbbbbbbbbbbbb","edit");
                Intent intent=new Intent(parent.getContext(),Plus_D.class);

                intent.putExtra("ex",true);
                intent.putExtra("name",myItem.getName());
                intent.putExtra("names",myItem.getNames());
                intent.putExtra("set1",myItem.getSet1());
                intent.putExtra("set2",myItem.getSet2());
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}
