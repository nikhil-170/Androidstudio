package com.example.inclass09;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contacts> {

    ArrayList<Contacts> list;

    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<Contacts> objects) {
        super(context, resource, objects);
        list = (ArrayList<Contacts>) objects;
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row_item,parent,false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.contactName = convertView.findViewById(R.id.contactNameTV);
            viewHolder.deleteContactButton = convertView.findViewById(R.id.buttonDel);
            convertView.setTag(viewHolder);
        }

        final Contacts contacts = getItem(position);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.contactName.setText(contacts.name);

        viewHolder.deleteContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactsListFragment.deleteData(contacts);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contacts contacts = list.get(position);
                ContactsListFragment.mListener.SendDataToDisplay(contacts);
            }
        });

        return convertView;
    }

    private static class ViewHolder{
        TextView contactName;
        Button deleteContactButton;
    }
}
