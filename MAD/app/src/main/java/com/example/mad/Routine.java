package com.example.mad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Routine extends AppCompatActivity {

    private Spinner breedSpinner, ageSpinner, healthSpinner;
    private TextView exerciseSuggestion;
    private ListView fitnessGoalsList;
    private Button startTimerButton, addFitnessGoalButton;
    private ArrayList<String> fitnessGoals;
    private ArrayAdapter<String> goalsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        // Initialize views
        breedSpinner = findViewById(R.id.pet_breed_spinner);
        ageSpinner = findViewById(R.id.pet_age_spinner);
        healthSpinner = findViewById(R.id.pet_health_conditions_spinner);
        exerciseSuggestion = findViewById(R.id.exercise_suggestion);
        fitnessGoalsList = findViewById(R.id.fitness_goals_list);
        startTimerButton = findViewById(R.id.start_timer_btn);
        addFitnessGoalButton = findViewById(R.id.add_fitness_goal_btn);

        // Initialize data
        fitnessGoals = new ArrayList<>();
        goalsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fitnessGoals);
        fitnessGoalsList.setAdapter(goalsAdapter);

        // Populate spinners with dummy data
        setupSpinners();

        // Suggest exercise based on inputs
        addFitnessGoalButton.setOnClickListener(view -> addFitnessGoal());

        // Timer functionality
        startTimerButton.setOnClickListener(view -> startExerciseTimer(60000)); // 60 seconds
    }

    private void setupSpinners() {
        // Dummy data for spinners
        String[] breeds = {"Golden Retriever", "Beagle", "Bulldog"};
        String[] ages = {"Puppy", "Adult", "Senior"};
        String[] healthConditions = {"Healthy", "Overweight", "Joint Issues"};

        breedSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, breeds));
        ageSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ages));
        healthSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, healthConditions));
    }

    private void addFitnessGoal() {
        // Get selected values
        String breed = breedSpinner.getSelectedItem().toString();
        String age = ageSpinner.getSelectedItem().toString();
        String healthCondition = healthSpinner.getSelectedItem().toString();

        // Determine suggested activities based on inputs
        String suggestion;
        if (healthCondition.equals("Overweight")) {
            suggestion = "Long Walks, Swimming";
        } else if (healthCondition.equals("Joint Issues")) {
            suggestion = "Short Walks, Gentle Fetch";
        } else {
            suggestion = "Fetch, Agility Training";
        }

        // Add to the list and update suggestion
        String goal = "Breed: " + breed + ", Age: " + age + ", Activities: " + suggestion;
        fitnessGoals.add(goal);
        goalsAdapter.notifyDataSetChanged();

        // Update suggestion text
        exerciseSuggestion.setText(suggestion);

        Toast.makeText(this, "Fitness Goal Added!", Toast.LENGTH_SHORT).show();
    }

    private void startExerciseTimer(long durationInMillis) {
        new CountDownTimer(durationInMillis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                startTimerButton.setText("Time Left: " + secondsLeft + "s");
            }

            @Override
            public void onFinish() {
                startTimerButton.setText("Start Exercise Timer");

                // Show alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Routine.this);
                builder.setTitle("Exercise Timer")
                        .setMessage("Time's up! Great job completing the exercise.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        }.start();
    }
}
