package com.zurefaseverler.kithub;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class AdapterCategories extends BaseExpandableListAdapter {

    static ArrayList<String> categories;
    static Map<String,ArrayList<String>> bookTypes;
    private Context c;

    AdapterCategories(Context c, ArrayList<String> categories, Map<String, ArrayList<String>> bookTypes){
        this.c = c;
        AdapterCategories.categories = categories;
        AdapterCategories.bookTypes = bookTypes;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getGroupCount() {
        return categories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return bookTypes.get(categories.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }



    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView tv_book_categories = new TextView(c);
        tv_book_categories.setText(categories.get(groupPosition));
        tv_book_categories.setTextSize(20);
        tv_book_categories.setPadding(140,10,10, 10);
        tv_book_categories.setTextColor(Color.parseColor("#ff5e3a"));
        return tv_book_categories;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView tv_sub_categories = new TextView(c);
        tv_sub_categories.setText(bookTypes.get(categories.get(groupPosition)).get(childPosition));
        tv_sub_categories.setTextSize(16);
        tv_sub_categories.setPadding(170,10,10, 10);
        tv_sub_categories.setTextColor(Color.parseColor("#272e4f"));
        return tv_sub_categories;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

