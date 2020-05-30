package com.zurefaseverler.kithub;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class OldCommentAdapter extends RecyclerView.Adapter<OldCommentAdapter.ViewHolder>{

    private Context context;
    private List<OldCommentsObj> list;

    public OldCommentAdapter(Context context, List<OldCommentsObj> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.old_comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bookname.setText(list.get(position).getBookname());
        holder.comment.setText(list.get(position).getComment());
        holder.date.setText(list.get(position).getDate());
        holder.ratingBar.setRating(list.get(position).getRate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView bookname, comment, date;
        public RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookname = itemView.findViewById(R.id.book_name);
            comment = itemView.findViewById(R.id.comment);
            date = itemView.findViewById(R.id.date);
            ratingBar = itemView.findViewById(R.id.rate);
        }
    }
}
