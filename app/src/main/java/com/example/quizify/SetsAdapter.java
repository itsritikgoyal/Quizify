package com.example.quizify;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SetsAdapter extends BaseAdapter {

    private final int noofsets;

    public SetsAdapter(int noofsets) {
        this.noofsets = noofsets;
    }

    @Override
    public int getCount() {
        return noofsets;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.setsitemlayout,parent,false);

        }else
            view = convertView;
        view.setOnClickListener(v -> {
            Intent intent = new Intent(parent.getContext(),QuestionsActivity.class);
            intent.putExtra("SETNO",position+1);
            parent.getContext().startActivity(intent);
        });
        ((TextView) view.findViewById(R.id.setsno)).setText(String.valueOf(position+1));
        return view;
    }
}
