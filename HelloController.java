package com.example.labapril9;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.util.regex.Pattern;

public class HelloController {

    @FXML
    private Label StatusLabel;

    @FXML
    private PasswordField pinField;

    @FXML
    private PasswordField confirmPinField;

    private static final Pattern PIN_PATTERN = Pattern.compile("\\d{4}");

    @FXML
    protected void onHelloButtonClick() {

        String pin = pinField.getText().trim();
        String confirmPin = confirmPinField.getText().trim();

        // Reset styles first
        resetStyles();

        // Validate format (4 digits)
        if (!PIN_PATTERN.matcher(pin).matches()) {
            showError("Invalid: PIN must contain exactly 4 digits");
            return;
        }

        // Prevent repeated digits
        if (isAllSame(pin)) {
            showError("Invalid: PIN cannot contain all identical digits");
            return;
        }

        // Prevent sequential digits
        if (isSequential(pin)) {
            showError("Invalid: PIN cannot be sequential");
            return;
        }

        // Confirm both PINs match
        if (!pin.equals(confirmPin)) {
            showError("PINs do not match! Please try again.");
            return;
        }

        // Sucess
        StatusLabel.setText("PIN Set Successfully");
        setSuccessStyles();
    }

    // helper methods


    private boolean isAllSame(String pin) {
        return pin.chars().distinct().count() == 1;
    }

    private boolean isSequential(String pin) {
        boolean ascending = true;
        boolean descending = true;

        for (int i = 0; i < pin.length() - 1; i++) {
            int current = pin.charAt(i);
            int next = pin.charAt(i + 1);

            if (next != current + 1) ascending = false;
            if (next != current - 1) descending = false;
        }

        return ascending || descending;
    }

    private void showError(String message) {
        StatusLabel.setText(message);
        pinField.setStyle("-fx-border-color: red;");
        confirmPinField.setStyle("-fx-border-color: red;");
    }

    private void setSuccessStyles() {
        pinField.setStyle("-fx-border-color: green;");
        confirmPinField.setStyle("-fx-border-color: green;");
    }

    private void resetStyles() {
        pinField.setStyle("");
        confirmPinField.setStyle("");
    }
}