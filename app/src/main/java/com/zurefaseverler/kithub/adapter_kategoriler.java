package com.zurefaseverler.kithub;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class adapter_kategoriler extends BaseExpandableListAdapter {

    // kategoriler veri tabani geldiginde oraya aktarilacak
    // ayrica adminin yeni kategori ekleyebilmesi lazim
    String[] kitap_Kategorileri = {"Bilim kurgu", "Romantik", "Edebiyat", "Tarih", "Cocuk", "Egitim", "Felsefe", "Sosyoloji", "Hukuk", "Saglik", "Sasirt Beni"};
    String[] yazi = {"Roman", "Siir", "Hikaye", "Deneme", "Biyografi", "Polisiye", "Elestiri", "Cizgi Roman"};

    String[][] alt_kategoriler = {{"Roman1", "Siir", "Hikaye", "Deneme", "Biyografi", "Polisiye", "Elestiri", "Cizgi Roman"}, // bilim kurgu
            {"Roman2", "Siir", "Hikaye", "Deneme", "Biyografi", "Polisiye", "Elestiri", "Cizgi Roman"}, // Romantik
            {"Roman3", "Siir", "Hikaye", "Deneme", "Biyografi", "Polisiye", "Elestiri", "Cizgi Roman"}, // Edebiyat
            {"Roman4", "Siir", "Hikaye", "Deneme", "Biyografi", "Polisiye", "Elestiri", "Cizgi Roman"}, // Tarih
            {"Roman5", "Siir", "Hikaye", "Deneme", "Biyografi", "Polisiye", "Elestiri", "Cizgi Roman"}, // Cocuk
            {"Roman6", "Siir", "Hikaye", "Deneme", "Biyografi", "Polisiye", "Elestiri", "Cizgi Roman"}, // Egitim
            {"Roman7", "Siir", "Hikaye", "Deneme", "Biyografi", "Polisiye", "Elestiri", "Cizgi Roman"}, // Felsefe
            {"Roman8", "Siir", "Hikaye", "Deneme", "Biyografi", "Polisiye", "Elestiri", "Cizgi Roman"}, // Sosyoloji
            {"Roman9", "Siir", "Hikaye", "Deneme", "Biyografi", "Polisiye", "Elestiri", "Cizgi Roman"}, // Hukuk
            {"Roman10", "Siir", "Hikaye", "Deneme", "Biyografi", "Polisiye", "Elestiri", "Cizgi Roman"}, // Saglik
            {"Roman11", "Siir", "Hikaye", "Deneme", "Biyografi", "Polisiye", "Elestiri", "Cizgi Roman"}  // Sasirt Beni
    };





    private Context c;

    public adapter_kategoriler(Context c){
        this.c = c;
    };

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getGroupCount() {
        return kitap_Kategorileri.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return alt_kategoriler[groupPosition].length;
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
        tv_kitap_kategorileri.setText(kitap_Kategorileri[groupPosition]);
        tv_kitap_kategorileri.setTextSize(20);
        tv_kitap_kategorileri.setPadding(140,10,10, 10);
        tv_kitap_kategorileri.setTextColor(Color.parseColor("#ff5e3a"));
        return tv_kitap_kategorileri;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView tv_alt_kategorileri = new TextView(c);
        tv_alt_kategorileri.setText(alt_kategoriler[groupPosition][childPosition]);
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

