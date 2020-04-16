package com.zurefaseverler.kithub;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class adapter_kategoriler extends BaseExpandableListAdapter {

    // kategoriler veri tabani geldiginde oraya aktarilacak
    // ayrica adminin yeni kategori ekleyebilmesi lazim
    ArrayList<String> bookCategories;
    ArrayList<String[]> bookTypes;
    private Context c;


    public adapter_kategoriler(Context c,ArrayList<String> bookCategories, ArrayList<String[]> bookTypes){
        this.c = c;
        this.bookCategories = bookCategories;
        this.bookTypes = bookTypes;
    };

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getGroupCount() {
        return bookCategories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return bookTypes.get(groupPosition).length;
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
        tv_kitap_kategorileri.setText(bookCategories.get(groupPosition));
        tv_kitap_kategorileri.setTextSize(20);
        tv_kitap_kategorileri.setPadding(140,10,10, 10);
        tv_kitap_kategorileri.setTextColor(Color.parseColor("#ff5e3a"));
        return tv_kitap_kategorileri;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView tv_alt_kategorileri = new TextView(c);
        tv_alt_kategorileri.setText(bookTypes.get(groupPosition)[childPosition]);
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

