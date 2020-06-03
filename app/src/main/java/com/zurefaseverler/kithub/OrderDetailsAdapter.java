package com.zurefaseverler.kithub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>{

    private Context context;
    private List<OrderDetailsBookObj> list;

    public OrderDetailsAdapter(Context context, List<OrderDetailsBookObj> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_detail_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bookName.setText(list.get(position).getBookName());
        holder.quantity.setText("Adet: " + list.get(position).getQuantity());
        holder.piecePrice.setText("Adet fiyatı: " + list.get(position).getPiecePrice() + " ₺");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView bookName, quantity, piecePrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.order_details_book_bookname);
            quantity = itemView.findViewById(R.id.order_details_book_quantity);
            piecePrice = itemView.findViewById(R.id.order_details_book_piece_price);

        }
    }

}
