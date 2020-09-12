package com.example.githubrepos;

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

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.Holder> {

    Context context;
    List<Repo> repoList;

    public RepoAdapter(Context context, List<Repo> repoList) {
        this.context = context;
        this.repoList = repoList;
    }

    @NonNull
    @Override
    public RepoAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.repo_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoAdapter.Holder holder, int position) { //здесь будем соединять
        holder.name.setText(repoList.get(position).getName());
        holder.login.setText(repoList.get(position).getOwner().getLogin());

        Picasso.get()
                .load(repoList.get(position).getOwner().getAvatarUrl())
                .into(holder.avatar);
        //загрузить откуда и поставить куда


    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView name,login;
        ImageView avatar;

        public Holder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            login = itemView.findViewById(R.id.login);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }
}
