package com.zurefaseverler.kithub;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class adapter_kategoriler extends BaseExpandableListAdapter {

    // kategoriler veri tabani geldiginde oraya aktarilacak
    // ayrica adminin yeni kategori ekleyebilmesi lazim
    static ArrayList<String> categories;
    static Map<String,ArrayList<String>> bookTypes;
    private Context c;


    public adapter_kategoriler(Context c,ArrayList<String> categories,Map<String,ArrayList<String>> bookTypes){
        this.c = c;
        adapter_kategoriler.categories = categories;
        adapter_kategoriler.bookTypes = bookTypes;
    };

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
        TextView tv_kitap_kategorileri = new TextView(c);
        tv_kitap_kategorileri.setText(categories.get(groupPosition));
        tv_kitap_kategorileri.setTextSize(20);
        tv_kitap_kategorileri.setPadding(140,10,10, 10);
        tv_kitap_kategorileri.setTextColor(Color.parseColor("#ff5e3a"));
        return tv_kitap_kategorileri;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView tv_alt_kategorileri = new TextView(c);
        tv_alt_kategorileri.setText(bookTypes.get(categories.get(groupPosition)).get(childPosition));
        tv_alt_kategorileri.setTextSize(16);
        tv_alt_kategorileri.setPadding(170,10,10, 10);
        tv_alt_kategorileri.setTextColor(Color.parseColor("#272e4f"));
        return tv_alt_kategorileri;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

