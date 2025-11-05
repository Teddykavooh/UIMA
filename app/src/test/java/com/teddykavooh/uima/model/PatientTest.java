package com.teddykavooh.uima.model;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class PatientTest {

    @Test
    // Verifies that the Patient model correctly stores and retrieves data.
    public void patientModelShouldFailToSetAndReturnCorrectData() {
        Patient patient = new Patient();
        patient.setFirstName("Jane");

        // This assertion will fail
        assertNull("First name should be null", patient.getFirstName());
    }

    /**
     * Verifies that the Patient model correctly stores and retrieves all registration data.
     * This test checks the integrity of the data after a Patient object is created,
     * simulating a patient's registration.
     */
    @Test
    public void patientShouldBeRegisteredWithCorrectDetails() {
        // Arrange: Create date formats and patient data
        DateFormat dob = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat regDate = new SimpleDateFormat("yyyy-MM-dd");

        // Act: Create a new patient with registration details
        Patient patient = new Patient(
                "John",
                "Doe",
                "JD12345",
                dob,
                "Male",
                regDate
        );

        // Assert: Verify all details are stored and retrieved correctly
        assertEquals("John", patient.getFirstName());
        assertEquals("Doe", patient.getLastName());
        assertEquals("JD12345", patient.getUniqueId());
        assertEquals(dob, patient.getDob());
        assertEquals("Male", patient.getGender());
        assertEquals(regDate, patient.getReg_date());
    }
}
