package com.example.kassa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final List<Movie> movieList;
    private final OnBuyTicketClickListener onBuyTicketClickListener;

    public interface OnBuyTicketClickListener {
        void onBuyTicketClick(int position);
    }

    public MovieAdapter(List<Movie> movieList, OnBuyTicketClickListener onBuyTicketClickListener) {
        this.movieList = movieList;
        this.onBuyTicketClickListener = onBuyTicketClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.bind(movie, onBuyTicketClickListener);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView ticketCountTextView;
        private final Button buyTicketButton;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            ticketCountTextView = itemView.findViewById(R.id.ticketCountTextView);
            buyTicketButton = itemView.findViewById(R.id.buyTicketButton);
        }

        public void bind(Movie movie, OnBuyTicketClickListener onBuyTicketClickListener) {
            nameTextView.setText(movie.getName());
            ticketCountTextView.setText(String.valueOf(movie.getTicketCount()));

            buyTicketButton.setOnClickListener(v -> {
                if (onBuyTicketClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onBuyTicketClickListener.onBuyTicketClick(position);
                    }
                }
            });
        }
    }
}
