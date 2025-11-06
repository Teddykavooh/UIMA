package com.teddykavooh.uima.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teddykavooh.uima.R;
import com.teddykavooh.uima.model.Patient;

import java.util.List;

// Populate RecycleViews with Patient data
public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {
    private List<Patient> patients;

    public PatientAdapter(List<Patient> patients) {
        this.patients = patients;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient, parent,
                false);
        return new PatientViewHolder(view);

    }

    @Override
    public void onBindViewHolder(PatientViewHolder holder, int position) {
        Patient patient = patients.get(position);
        holder.tvName.setText(patient.getFirstName() + " " + patient.getLastName());
        holder.tvAge.setText("DOB: " + patient.getDob());
        holder.tvsStatus.setText("Gender: " + patient.getGender());
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
        notifyDataSetChanged();
    }

    public static class PatientViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName, tvAge, tvsStatus;

        public PatientViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvPatientName);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvsStatus = itemView.findViewById(R.id.tvBMIStatus);
        }

    }
}
