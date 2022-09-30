package com.example.musicappcsia;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class PersonListAdapter extends ArrayAdapter<Person> {

    private static final String TAG = "PersonListAdapter";
    private Context mContext;
    int mResource;

    public PersonListAdapter(Context context, int resource, ArrayList<Person> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
    }

    //responsible for getting view and attaching it to the list view
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the person's information
        String name = getItem(position).getName();
        String score = getItem(position).getScore();

        //Create the person object with the information
        Person person = new Person(name, score);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView tvScore = (TextView) convertView.findViewById(R.id.textViewScore);

        tvName.setText(name);
        tvScore.setText(score);

        return convertView;
    }

}
