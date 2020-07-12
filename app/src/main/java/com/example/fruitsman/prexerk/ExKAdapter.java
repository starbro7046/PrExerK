package com.example.fruitsman.prexerk;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ExKAdapter extends ArrayAdapter {


    public ExKAdapter(@NonNull Context context, int resource, @NonNull List objects) {
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
        final Data_K myItem= (Data_K) getItem(position);
        name.setText(myItem.getName());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("bbbbbbbbbbbbbb","delete");
                ((SettingSta)(parent.getContext())).deleteData(myItem.getName());
                ((SettingSta)(parent.getContext())).updateListView();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("bbbbbbbbbbbbbbbbbbb","edit");
                Intent intent=new Intent(parent.getContext(),Plus_K.class);
                intent.putExtra("ex",true);
                intent.putExtra("name",myItem.getName());
                intent.putExtra("left1",myItem.getLeft1());
                intent.putExtra("left2",myItem.getLeft2());
                intent.putExtra("left3",myItem.getLeft3());
                intent.putExtra("left4",myItem.getLeft4());
                intent.putExtra("right1",myItem.getRight1());
                intent.putExtra("right2",myItem.getRight2());
                intent.putExtra("right3",myItem.getRight3());
                intent.putExtra("right4",myItem.getRight4());
                intent.putExtra("time",myItem.getTime());
                intent.putExtra("acc",myItem.getAccuracy());
                parent.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
