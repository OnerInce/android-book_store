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

public class OrderConfirmAdapter extends RecyclerView.Adapter<OrderConfirmAdapter.ViewHolder> {

    private Context context;
    private List<OrderConfirmOrders> list;

    public OrderConfirmAdapter(Context context, List<OrderConfirmOrders> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirm_order_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.order_id.setText("Sipariş no: " + list.get(position).getOrderid());
        holder.user_id.setText("Kullanıcı no: " + list.get(position).getUserid());
        holder.date.setText("Tarih: " + list.get(position).getDate());
        holder.numof_book.setText("Kitap sayısı: " + list.get(position).getNumofbook());
        holder.total_amount.setText("Toplam tutar: " + list.get(position).getTotalamount() + " ₺");
        holder.address.setText("Adres: " + list.get(position).getAddress());
        holder.shipper.setText(list.get(position).getShipper() + "Kargo");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView order_id, user_id, date, numof_book, total_amount, address, shipper;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            order_id = itemView.findViewById(R.id.order_layout_order_id);
            user_id = itemView.findViewById(R.id.order_layout_user_id);
            date = itemView.findViewById(R.id.order_layout_order_date);
            numof_book = itemView.findViewById(R.id.order_layout_order_numof_book);
            total_amount = itemView.findViewById(R.id.order_layout_total_amount);
            address = itemView.findViewById(R.id.order_layout_address);
            shipper = itemView.findViewById(R.id.order_layout_shipper);

        }
    }
}
