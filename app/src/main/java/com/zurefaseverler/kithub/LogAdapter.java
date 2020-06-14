package com.zurefaseverler.kithub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.setDetails>{

    private Context context;
    private List<LogObj> list;

    public LogAdapter(Context context, List<LogObj> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public setDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.log_item, parent, false);
        return new setDetails(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull setDetails holder, int position) {

        holder.text1.setVisibility(View.VISIBLE);
        holder.text2.setVisibility(View.VISIBLE);
        holder.text3.setVisibility(View.VISIBLE);
        holder.text4.setVisibility(View.VISIBLE);
        holder.text5.setVisibility(View.VISIBLE);
        holder.text6.setVisibility(View.VISIBLE);

        holder.text1.setText("Tarih: " + list.get(position).getlogDate());
        holder.text2.setText("Kullanıcı id: " + list.get(position).getUserid());
        switch (list.get(position).getLog_type()){

            case("Giriş yapma"):
            case("Çıkış yapma"):
                holder.text3.setText("email: " + list.get(position).getUseremail());
                holder.text4.setVisibility(View.GONE);
                holder.text5.setVisibility(View.GONE);
                holder.text6.setVisibility(View.GONE);
                break;
            case("Kaydolma"):
                holder.text3.setText("email: " + list.get(position).getUseremail());
                holder.text4.setText("Telefon: " + list.get(position).getPhone());
                holder.text5.setVisibility(View.GONE);
                holder.text6.setVisibility(View.GONE);
                break;
            case("Sepet İşlemleri"):

                holder.text3.setText("İşlem türü: " + list.get(position).getCartOpType());
                holder.text4.setText("Kitap: " + list.get(position).getBookId());
                holder.text5.setVisibility(View.GONE);
                holder.text6.setVisibility(View.GONE);
                break;
            case("Sipariş verme"):
                holder.text3.setText("Sipariş no: " + list.get(position).getOrderId());
                holder.text4.setText("Toplam tutar: " + list.get(position).getTotalAmount() + " ₺");
                holder.text5.setText("Kitap sayısı: " + list.get(position).getNumOfBook());
                holder.text6.setVisibility(View.GONE);
                break;
            case("Yorum yapma"):
                holder.text3.setText("Yorum no: " + list.get(position).getCommentID());
                holder.text6.setText("Puan: " + list.get(position).getCommentRate());
                holder.text5.setText("Yorum: " + list.get(position).getComment());
                holder.text4.setText("Kitap: " + list.get(position).getBookId());
                break;
            default:
                holder.text3.setText("İsim: " + list.get(position).getUsername());
                holder.text4.setText("email: " + list.get(position).getUseremail());
                holder.text5.setText("Adres: " + list.get(position).getAdress());
                holder.text6.setText("Tel: " + list.get(position).getPhone());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class setDetails extends RecyclerView.ViewHolder{

        TextView text1, text2, text3, text4, text5, text6;


        public setDetails(@NonNull View itemView) {
            super(itemView);

            text1 = itemView.findViewById(R.id.log_text1);
            text2 = itemView.findViewById(R.id.log_text2);
            text3 = itemView.findViewById(R.id.log_text3);
            text4 = itemView.findViewById(R.id.log_text4);
            text5 = itemView.findViewById(R.id.log_text5);
            text6 = itemView.findViewById(R.id.log_text6);

        }
    }
}
