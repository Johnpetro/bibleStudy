package com.beng22coe.biblestudy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView recentStudiesRecyclerView;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize views
        welcomeText = findViewById(R.id.welcome_text);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        recentStudiesRecyclerView = findViewById(R.id.recent_studies_recycler);

        // Set up bottom navigation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                // Already on home
                return true;
            } else if (itemId == R.id.nav_bible) {
                Toast.makeText(MainActivity.this, "Bible section coming soon", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_study) {
                Toast.makeText(MainActivity.this, "Study section coming soon", Toast.LENGTH_SHORT).show();
                return true;
            } else if (itemId == R.id.nav_profile) {
                Toast.makeText(MainActivity.this, "Profile section coming soon", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // Set up card click listeners
        setupCardClickListeners();

        // Set up recent studies recycler view
        setupRecentStudies();

        // Set up welcome message with username
        setupWelcomeMessage();

        // Set up debug button (visible in debug builds)
        setupDebugButton();
    }

    private void setupCardClickListeners() {
        MaterialCardView bibleCard = findViewById(R.id.bible_card);
        MaterialCardView notesCard = findViewById(R.id.notes_card);
        MaterialCardView prayerCard = findViewById(R.id.prayer_card);
        MaterialCardView communityCard = findViewById(R.id.community_card);

        bibleCard.setOnClickListener(v -> 
            Toast.makeText(MainActivity.this, "Bible reading coming soon", Toast.LENGTH_SHORT).show());
        
        notesCard.setOnClickListener(v -> 
            Toast.makeText(MainActivity.this, "Study notes coming soon", Toast.LENGTH_SHORT).show());
        
        prayerCard.setOnClickListener(v -> 
            Toast.makeText(MainActivity.this, "Prayer list coming soon", Toast.LENGTH_SHORT).show());
        
        communityCard.setOnClickListener(v -> 
            Toast.makeText(MainActivity.this, "Community features coming soon", Toast.LENGTH_SHORT).show());

        findViewById(R.id.daily_verse_button).setOnClickListener(v -> 
            Toast.makeText(MainActivity.this, "Daily verse feature coming soon", Toast.LENGTH_SHORT).show());
    }

    private void setupRecentStudies() {
        // Create sample data
        List<RecentStudy> recentStudies = new ArrayList<>();
        recentStudies.add(new RecentStudy("The Beatitudes", "A study of Matthew 5:1-12", "May 15, 2023"));
        recentStudies.add(new RecentStudy("Fruits of the Spirit", "Exploring Galatians 5:22-23", "May 10, 2023"));
        recentStudies.add(new RecentStudy("The Lord's Prayer", "Understanding Matthew 6:9-13", "May 5, 2023"));

        // Set up adapter and layout manager
        RecentStudiesAdapter adapter = new RecentStudiesAdapter(recentStudies);
        recentStudiesRecyclerView.setAdapter(adapter);
        recentStudiesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupWelcomeMessage() {
        // In a real app, you would get the username from SharedPreferences or a database
        // For now, we'll use a placeholder
        String username = "Friend"; // Default value
        
        // Try to get the actual username if available
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        if (prefs.contains("username")) {
            username = prefs.getString("username", "Friend");
        }
        
        welcomeText.setText("Welcome, " + username);
    }

    private void setupDebugButton() {
        View debugButton = findViewById(R.id.debug_button);
        
        // Always show the debug button for now
        debugButton.setVisibility(View.VISIBLE);
        debugButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DatabaseViewerActivity.class);
            startActivity(intent);
        });
        
        // Note: In a production app, you would use BuildConfig.DEBUG to conditionally show this
        // but we're simplifying for now to avoid build issues
    }

    // Model class for recent studies
    private static class RecentStudy {
        String title;
        String description;
        String date;

        RecentStudy(String title, String description, String date) {
            this.title = title;
            this.description = description;
            this.date = date;
        }
    }

    // Adapter for recent studies
    private class RecentStudiesAdapter extends RecyclerView.Adapter<RecentStudiesAdapter.ViewHolder> {
        private List<RecentStudy> studies;

        RecentStudiesAdapter(List<RecentStudy> studies) {
            this.studies = studies;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_recent_study, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            RecentStudy study = studies.get(position);
            holder.titleText.setText(study.title);
            holder.descriptionText.setText(study.description);
            holder.dateText.setText(study.date);
            
            holder.itemView.setOnClickListener(v -> 
                Toast.makeText(MainActivity.this, "Opening " + study.title, Toast.LENGTH_SHORT).show());
        }

        @Override
        public int getItemCount() {
            return studies.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView titleText;
            TextView descriptionText;
            TextView dateText;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                titleText = itemView.findViewById(R.id.study_title);
                descriptionText = itemView.findViewById(R.id.study_description);
                dateText = itemView.findViewById(R.id.study_date);
            }
        }
    }
}