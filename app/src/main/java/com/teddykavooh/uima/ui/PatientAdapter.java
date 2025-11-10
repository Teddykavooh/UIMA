package com.teddykavooh.uima.ui;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.teddykavooh.uima.R;
import com.teddykavooh.uima.domain.BmiCalculator;
import com.teddykavooh.uima.model.Patient;
import com.teddykavooh.uima.model.PatientWithVitals;
import com.teddykavooh.uima.model.Vitals;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

// Use ListAdapter for more efficient list updates
public class PatientAdapter extends ListAdapter<PatientWithVitals, PatientAdapter.PatientViewHolder> {

    public PatientAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<PatientWithVitals> DIFF_CALLBACK = new DiffUtil.ItemCallback<PatientWithVitals>() {
        @Override
        public boolean areItemsTheSame(@NonNull PatientWithVitals oldItem, @NonNull PatientWithVitals newItem) {
            // Use direct field access
            return oldItem.patient.getUniqueId().equals(newItem.patient.getUniqueId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull PatientWithVitals oldItem, @NonNull PatientWithVitals newItem) {
            // This is a simplified check. For full robustness, Patient and Vitals should have equals() implemented.
            return Objects.equals(oldItem.patient, newItem.patient) &&
                   Objects.equals(oldItem.vitals, newItem.vitals);
        }
    };

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        PatientWithVitals data = getItem(position);
        if (data == null || data.patient == null) {
            return;
        }

        Patient patient = data.patient;
        List<Vitals> vitalsList = data.vitals;

        // Find the most recent vitals from the list by comparing the visit_date string.
        // Because the date is in YYYY-MM-DD format, a standard string comparison works correctly.
        Vitals latestVitals = null;
        if (vitalsList != null && !vitalsList.isEmpty()) {
            latestVitals = vitalsList.stream()
                    .max(Comparator.comparing(Vitals::getVisit_date))
                    .orElse(null);
        }

        // Bind data to the views
        holder.tvName.setText(patient.getFirstName() + " " + patient.getLastName());
        holder.tvAge.setText(String.valueOf(BmiCalculator.calculateAge(patient.getDob())));

        // Use the latestVitals for the BMI calculation
        if (latestVitals != null && latestVitals.getWeight() != null &&
                !latestVitals.getWeight().isEmpty() && latestVitals.getHeight() !=
                null && !latestVitals.getHeight().isEmpty()) {
            try {
                double weightKg = Double.parseDouble(latestVitals.getWeight());
                double heightM = Double.parseDouble(latestVitals.getHeight());

                double bmi = BmiCalculator.calculate(weightKg, heightM);

                holder.tvsStatus.setText(String.format("%.1f", bmi));
            } catch (NumberFormatException e) {
                holder.tvsStatus.setText("N/A");
                e.printStackTrace();
            }
        } else {
            holder.tvsStatus.setText("No Vitals");
        }

        // Icon to lead to vitals
        holder.ivVitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event here: direct to add vitals, carry patient id
                Intent intent = new Intent(v.getContext(), AddVitalsActivity.class);
                intent.putExtra("patientId", patient.getUniqueId());
                // Add logger
                Log.d("PatientAdapter", "Patient ID: " + patient.getUniqueId());
                v.getContext().startActivity(intent);
            }
        });
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName, tvAge, tvsStatus;
        private final ImageView ivVitals;

        public PatientViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvPatientName);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvsStatus = itemView.findViewById(R.id.tvBMIStatus);
            ivVitals = itemView.findViewById(R.id.ivAddVitals);

        }
    }
}
