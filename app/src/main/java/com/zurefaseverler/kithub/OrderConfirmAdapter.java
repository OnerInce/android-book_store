package com.zurefaseverler.kithub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class OrderConfirmAdapter extends RecyclerView.Adapter<OrderConfirmAdapter.ViewHolder> {

    private Context context;
    private List<OrderConfirmOrders> list;
    private Intent intent;

    public OrderConfirmAdapter(Context context, List<OrderConfirmOrders> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirm_order_layout, parent, false);
        intent = new Intent(context.getApplicationContext(), OrderDetails.class);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String s = String.format(Locale.ITALY, "%.2f", list.get(position).getTotalamount());

        holder.order_id.setText("Sipariş no: " + list.get(position).getOrderid());
        holder.user_id.setText("İsim: " + list.get(position).getUserName());
        holder.date.setText("Tarih: " + list.get(position).getDate());
        holder.numof_book.setText("Kitap sayısı: " + list.get(position).getNumofbook());
        holder.total_amount.setText("Toplam tutar: " + s + " ₺");
        holder.address.setText("Adres: " + list.get(position).getAddress());
        holder.shipper.setText(list.get(position).getShipper() + "Kargo");

        String status = list.get(position).getStatus();
        if(status.equals("-2")) {
            holder.status.setText("");
            intent.putExtra("status", "show");
        }
        else {
            switch (status) {
                case "0":
                    intent.putExtra("status", "show");
                    holder.status.setText("Durum: Onay Bekliyor");
                    break;
                case "1":
                    intent.putExtra("status", "hide");
                    holder.status.setText("Durum: Onaylandı");
                    break;
                case "-1":
                    intent.putExtra("status", "hide");
                    holder.status.setText("Durum: Reddedildi");
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView order_id, user_id, date, numof_book, total_amount, address, shipper, status;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            order_id = itemView.findViewById(R.id.order_layout_order_id);
            user_id = itemView.findViewById(R.id.order_layout_user_id);
            date = itemView.findViewById(R.id.order_layout_order_date);
            numof_book = itemView.findViewById(R.id.order_layout_order_numof_book);
            total_amount = itemView.findViewById(R.id.order_layout_total_amount);
            address = itemView.findViewById(R.id.order_layout_address);
            shipper = itemView.findViewById(R.id.order_layout_shipper);
            status = itemView.findViewById(R.id.order_layout_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    intent.putExtra("order_id", list.get(pos).getOrderid());
                    intent.putExtra("date", list.get(pos).getDate());
                    intent.putExtra("total_price", Float.toString(list.get(pos).getTotalamount()));
                    intent.putExtra("name", list.get(pos).getUserName());

                    if (pos != RecyclerView.NO_POSITION) {
                        context.startActivity(intent);
                    }

                }


            });
        }}}
