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
    private String[] kitap_Kategorileri = {"Edebiyat", "Çocuk ve Gençlik", "Eğitim ve Gelişim", "Araştırma ve Tarih", "Din Tasavvuf", "Yabancı Dil", "Ders ve Sınav", "Sanat - Tasarım", "Felsefe", "Bilim", "Mizah",
            "Sesli kitap"};
    String[] yazi = {"Roman", "Siir", "Hikaye", "Deneme", "Biyografi", "Polisiye", "Elestiri", "Cizgi Roman"};

    private String[][] alt_kategoriler = {{"Roman", "Siir", "Deneme", "Öykü", "Edebiyat İnceleme", "Biyografi & Oto biyografi", "Anlatı", "Dergi"},     // Edebiyat
            {"Okul Çağı 6-10 Yaş", "Okul Öncesi 6 Ay-5 Yaş", "Gençlik 10+ Yaş", "Okul Kitapları"},                                                      // Çocuk ve Gençlik
            {"İş - Hukuk", "Kişisel Gelişim", "Aile Çocuk", "Psikoloji", "Eğitim", "Gezi", "Sağlık", "Etimoloji","Sözlük"},                             // Eğitim ve Gelişim
            {"Tarih", "Politika", "Sosyoloji"},                                                                                                         // Tarih
            {"İslamiyet", "Tasavvuf", "Din", "Hristiyanlık", "Diğer İnançlar", "Musevilik"},                                                            // Din Tasavvuf
            {"Dil", "Edebiyat ve Roman", "Çocuk ve Genç", "Çizgi Roman"},                                                                               // Yabancı Dil
            {"Üniversite Ders Kitapları", "Sınav Kitapları", "Ders Kitapları/Testler"},                                                                 // Ders ve Sınav
            {"Tiyatro", "Müzik", "Sinema", "Sanat Tarihi ve Kuramı", "Mimari", "Senaryo"},                                                              // Sanat - Tasarım
            {"Felsefe Bilimi", "Felsefeciler", "Felsefi Romanlar"},                                                                                     // Felsefe
            {"Popüler Bilim", "Matematik", "Mühendislik", "Bilim Tarihi ve Felsefesi", "Arkeoloji", "Antropoloji", "Biyoloji", "Fizik",                 // Bilim
                    "Coğrafya","Bilim İnsan","Jeoloji","Kimya","Astronomi","Zooloji"},
            {"Mizah Ramanı-Öykü", "Karikatür", "Fıkra"},                                                                                                 // Mizah
            {"Sesli Çocuk Kitabı"}                                                                                                                       // Sesli kitap
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

