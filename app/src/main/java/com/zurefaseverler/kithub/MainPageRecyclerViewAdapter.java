package com.zurefaseverler.kithub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MainPageRecyclerViewAdapter extends RecyclerView.Adapter<MainPageRecyclerViewAdapter.ViewHolder>{
    @NonNull
    private Context context;
    private List<MainPageBook> list;
    private MainPageBookListener MainBookListener;
    private String tag;

    public MainPageRecyclerViewAdapter(@NonNull Context context, List<MainPageBook> list, MainPageBookListener mainPageListener, String tag) {
        this.context = context;
        this.list = list;
        this.MainBookListener = mainPageListener;
        this.tag = tag;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_page_book_layout, parent, false);
        return new ViewHolder(view, MainBookListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bookname.setText(list.get(position).getBookname());
        holder.authorname.setText(list.get(position).getAuthorname());
        holder.discountamount.setText(list.get(position).getDiscountamount());
        holder.price.setText(list.get(position).getPrice() + " ₺");

        String[] temp = list.get(position).getImagepath().split("html/");
        Picasso.get().load("http://18.204.251.116/"+temp[1]).into(holder.bookimage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements MainPageBookListener{

        public ImageView bookimage;
        public TextView bookname, authorname, discountamount, price;
        MainPageBookListener mainPageListener;

        public ViewHolder(@NonNull View itemView, MainPageBookListener mainPageListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            this.mainPageListener = mainPageListener;

            bookimage = itemView.findViewById(R.id.BookImageMainPage);
            bookname = itemView.findViewById(R.id.BookNameMainPage);
            authorname = itemView.findViewById(R.id.AuthorNameMainPage);
            discountamount = itemView.findViewById(R.id.discountAmountMainPage);
            price = itemView.findViewById(R.id.mainPagePrice);

            itemView.setOnClickListener();

        }


        @Override
        public void onClick(int position, String tag) {

        }
    }

    public interface MainPageBookListener{
        void onClick(int position, String tag);
    }
}
