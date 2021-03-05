package com.example.checkoutservice;

import com.example.checkoutservice.model.Customer;
import com.example.checkoutservice.model.Vehicle;

public class CheckoutResponse {

    private final boolean approved;
    private final String message;

    public CheckoutResponse(boolean outcome, Customer customer, Vehicle vehicle) {
        this.approved = outcome;

        if (this.approved) {

            this.message = String.format("[CHECKOUT-OUTCOME-SUCCESS] Customer '%s' (credit score = %s)" +
                            " is approved to purchase vehicle '%s' (minimum credit score = %s)",
                    customer.getCustomerId(),
                    customer.getCreditScore(),
                    vehicle.getVehicleId(),
                    vehicle.getMinCreditScore());
        } else {
            this.message = String.format("[CHECKOUT-OUTCOME-FAIL] Customer '%s' (credit score = %s)" +
                            " is NOT approved to purchase vehicle '%s' (minimum credit score = %s)",
                    customer.getCustomerId(),
                    customer.getCreditScore(),
                    vehicle.getVehicleId(),
                    vehicle.getMinCreditScore());
        }
    }

    public boolean getApproved() {
        return this.approved;
    }

    public String getMessage() {
        return this.message;
    }

}
