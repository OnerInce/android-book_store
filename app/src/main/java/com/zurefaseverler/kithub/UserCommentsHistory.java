package com.zurefaseverler.kithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class UserCommentsHistory extends AppCompatActivity {

    List<OldCommentsObj> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_comments_history);

        fill();
        RecyclerView view = findViewById(R.id.old_comments);
        OldCommentAdapter adapter = new OldCommentAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        view.setLayoutManager(layoutManager);
        view.setAdapter(adapter);

    }
    public void fill(){

        for(int i= 0 ; i< 12 ; i++) {
            OldCommentsObj temp = new OldCommentsObj("bir kitab adı", "bir şetlre dsds c sd s d s cs s fd fs sdf sd fd fdvdfsdv d vds v dc svd v sdfsdsf fd sf ds fds fsdfds fd sf sdf sdf sd fsd fsd fsd fsd fsd f sdf sd fds fsd fsd dsf dsfsdf sdf sf sdf ds fsd fd sf sdf dsf ds fsd fsd fds fd sf sdf ds fsd", "12.12.2020", 3);
            list.add(temp);
        }
    }


}
