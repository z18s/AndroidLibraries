package com.example.githubclient.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubclient.R;
import com.example.githubclient.mvp.presenter.list.IRepositoryListPresenter;
import com.example.githubclient.mvp.view.IRepositoryItemView;

public class RepositoryRVAdapter extends RecyclerView.Adapter<RepositoryRVAdapter.ViewHolder> {
    private final IRepositoryListPresenter presenter;

    public RepositoryRVAdapter(IRepositoryListPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View repoView = inflater.inflate(R.layout.item_repo, parent, false);
        return new ViewHolder(repoView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryRVAdapter.ViewHolder holder, int position) {
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements IRepositoryItemView {
        TextView textView;
        int position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.tv_repo);
        }

        @Override
        public void setRepositoryName(String text) {
            textView.setText(text);
        }

        @Override
        public int getPos() {
            return position;
        }
    }
}