package com.example.kassa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Movie> movieList = new ArrayList<>();
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        movieList.add(new Movie("Мстители", 100));
        movieList.add(new Movie("Адвокат дьявола", 30));
        movieList.add(new Movie("Иван Васильевич меняет профессию", 50));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieAdapter = new MovieAdapter(movieList, position -> {
            Movie selectedMovie = movieList.get(position);
            if (selectedMovie.getTicketCount() > 0) {
                selectedMovie.setTicketCount(selectedMovie.getTicketCount() - 1);
                movieAdapter.notifyItemChanged(position);
                Toast.makeText(MainActivity.this, "Билет куплен: " + selectedMovie.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Билеты закончились: " + selectedMovie.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(movieAdapter);

        Button buyTicketsBtn = findViewById(R.id.buyTicketsBtn);
        buyTicketsBtn.setOnClickListener(view -> {
            finish();
            startActivity(getIntent());
        });

        ImageView imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnClickListener(view -> {
            openFragment(new DeveloperInfoFragment());
        });
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
