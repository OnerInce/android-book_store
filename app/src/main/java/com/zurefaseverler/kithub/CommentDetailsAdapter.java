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

public class CommentDetailsAdapter extends RecyclerView.Adapter<CommentDetailsAdapter.ViewHolder> {

    private List<CommentObj> list;

    public CommentDetailsAdapter(Context context, List<CommentObj> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CommentDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_comment, parent, false);
        return new CommentDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentDetailsAdapter.ViewHolder holder, int position) {

        holder.userName.setText(list.get(position).getUserName());
        holder.usersComment.setText(list.get(position).getUsersComment());
        holder.date.setText(list.get(position).getDate());
        holder.commentRate.setRating(list.get(position).getUserRate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public TextView userName, usersComment, date;
        public RatingBar commentRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name);
            usersComment = itemView.findViewById(R.id.users_comment);
            date = itemView.findViewById(R.id.comment_date);
            commentRate = itemView.findViewById(R.id.users_book_rate);

        }
    }
}
