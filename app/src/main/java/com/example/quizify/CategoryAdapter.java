package com.example.quizify;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    public CategoryAdapter(List<String> catList) {
        this.catList = catList;
    }

    private final List<String> catList;

    @Override
    public int getCount() {
        return catList.size();
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

        if (convertView == null)
        {
            view = LayoutInflater.from(parent.getContext()) .inflate(R.layout.itemlayout,parent,false);
        }
        else {
            view = convertView;
        }

        view.setOnClickListener(v -> {
            Intent intent = new Intent(parent.getContext(),SetsActivity.class);
            intent.putExtra("CATEGORY",catList.get(position));
            intent.putExtra("CATEGORY_ID",position + 1);
            parent.getContext().startActivity(intent);
        });

        ((TextView) view.findViewById(R.id.categoryName)).setText(catList.get(position));

        return view;
    }
}
