package com.zurefaseverler.kithub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class MainPageRecyclerViewAdapter extends RecyclerView.Adapter<MainPageRecyclerViewAdapter.ViewHolder>{
    @NonNull
    private Context context;
    private List<MainPageBook> list;
    private String tag;

    public MainPageRecyclerViewAdapter(@NonNull Context context, List<MainPageBook> list) {
        this.context = context;
        this.list = list;
        this.tag = tag;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_page_book_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(!list.get(position).getDiscountamount().equals("") && !list.get(position).getDiscountamount().equals("0"))
            holder.discountamount.setText("İndirim: %" + list.get(position).getDiscountamount());
        else
            holder.discountamount.setText("");

        holder.bookname.setText(list.get(position).getBookname());
        holder.authorname.setText(list.get(position).getAuthorname());

        String s = String.format(Locale.ITALY, "%.2f", list.get(position).getPrice());
        holder.price.setText(s + " ₺");


        String[] temp = list.get(position).getImagepath().split("html/");

        if(temp.length == 1){
            Picasso.get().load(list.get(position).getImagepath()).into(holder.bookimage);
        }
        else{
            Picasso.get().load("http://18.204.251.116/" + temp[1]).into(holder.bookimage);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView bookimage;
        public TextView bookname, authorname, discountamount, price ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            bookimage = itemView.findViewById(R.id.BookImageMainPage);
            bookname = itemView.findViewById(R.id.BookNameMainPage);
            authorname = itemView.findViewById(R.id.AuthorNameMainPage);
            discountamount = itemView.findViewById(R.id.discountAmountMainPage);
            price = itemView.findViewById(R.id.mainPagePrice);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context.getApplicationContext(), BookPage.class);
                        intent.putExtra("book_id", list.get(pos).getId());
                        context.startActivity(intent);
                    }
                }
            });

        }

    }


}