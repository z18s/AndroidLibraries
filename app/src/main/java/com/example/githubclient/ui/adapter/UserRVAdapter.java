package com.example.githubclient.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubclient.R;
import com.example.githubclient.mvp.presenter.list.IUserListPresenter;
import com.example.githubclient.mvp.view.IUserItemView;
import com.example.githubclient.mvp.view.image.GlideImageLoader;
import com.example.githubclient.mvp.view.image.IImageLoader;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.ViewHolder> {
    private final IUserListPresenter presenter;
    private static final IImageLoader<ImageView> imageLoader = new GlideImageLoader();

    public UserRVAdapter(IUserListPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.item_user, parent, false);
        return new ViewHolder(userView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.position = position;

        holder.itemView.setOnClickListener((view) -> {
            presenter.onItemClick(holder);
        });

        presenter.bindView(holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements IUserItemView {
        TextView textView;
        ImageView avatarView;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_login);
            avatarView = itemView.findViewById(R.id.iv_avatar);
        }

        @Override
        public void setLogin(String text) {
            textView.setText(text);
        }

        @Override
        public void loadAvatar(String url) {
            imageLoader.loadImage(url, avatarView);
        }

        @Override
        public int getPos() {
            return position;
        }
    }
}