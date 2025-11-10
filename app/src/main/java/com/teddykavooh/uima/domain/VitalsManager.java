package com.teddykavooh.uima.domain;

import com.teddykavooh.uima.data.repository.VitalsRepository;
import com.teddykavooh.uima.model.Vitals;

public class VitalsManager {
    private final VitalsRepository vitalsRepository;

    public VitalsManager(VitalsRepository vitalsRepository) {
        this.vitalsRepository = vitalsRepository;
    }

    /**
     * Calculates BMI and saves the vitals record locally.
     * @param visitDate The date of the visit.
     * @param heightStr The height as a string (assumed to be in meters).
     * @param weightStr The weight as a string (in kg).
     * @param patientID The ID of the patient.
     * @param patient_id_remote The ID of the patient on the remote server.

     */
    public void addVitals(String visitDate, String heightStr, String weightStr, String patientID,
                          String patient_id_remote) {
        double height = 0.0;
        double weight = 0.0;

        try {
            height = Double.parseDouble(heightStr);
            weight = Double.parseDouble(weightStr);
        } catch (NumberFormatException e) {
            // Log the error or handle it more gracefully
            e.printStackTrace();
            return; // Prevents creating a record with invalid data
        }

        // 1. Calculate BMI
        double bmiValue = BmiCalculator.calculate(weight, height);

        // 2. Format the BMI to a string with one decimal place
        String bmiString = String.format("%.1f", bmiValue);

        // 3. Create the Vitals object, now including the calculated BMI
        Vitals vitals = new Vitals(visitDate, heightStr, weightStr, bmiString, patientID,
                patient_id_remote);

        // 4. Save the new record
        vitalsRepository.saveLocal(vitals);
    }

    // TODO: Before sync tie remote patient ID to local
    // Sync Vitals to remote server
    public void syncVitals(){
        vitalsRepository.syncVitals();
    }
}
