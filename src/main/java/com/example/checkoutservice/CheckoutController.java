package com.example.checkoutservice;

import com.example.checkoutservice.model.CheckoutRequest;
import com.example.checkoutservice.model.Customer;
import com.example.checkoutservice.model.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@RestController
@Slf4j
public class CheckoutController {

    private final Logger logger = LogManager.getLogger(CheckoutController.class);

    private final RestTemplate restTemplate;

    private final String customerBaseUrl;
    private final String vehicleBaseUrl;

    @Autowired
    public CheckoutController(@Value("${service.customer.baseUrl}") String customerBaseUrl,
                              @Value("${service.vehicle.baseUrl}") String vehicleBaseUrl,
                              RestTemplate restTemplate) {
        this.customerBaseUrl = customerBaseUrl;
        this.vehicleBaseUrl = vehicleBaseUrl;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Object> getVehicles(@RequestBody CheckoutRequest checkoutRequest) {
        logger.info(String.format("Customer '%s' is attempting to purchase vehicle '%s'",
                checkoutRequest.getCustomerId(),
                checkoutRequest.getVehicleId())
        );

        Customer customer = getCustomerById(checkoutRequest.getCustomerId());
        Vehicle vehicle = getVehicleById(checkoutRequest.getVehicleId());

        if (vehicle.getMinCreditScore() <= customer.getCreditScore()) {
            logger.info(String.format("[CHECKOUT-OUTCOME-SUCCESS] " +
                            "Customer '%s' is approved to purchase vehicle '%s'",
                    checkoutRequest.getCustomerId(),
                    checkoutRequest.getVehicleId())
            );
            return new ResponseEntity<>(new CheckoutResponse(true, customer, vehicle), HttpStatus.OK);
        } else {
            logger.info(String.format("[CHECKOUT-OUTCOME-FAIL, reason: credit] " +
                            "Customer '%s' is not approved to purchase vehicle '%s'",
                    checkoutRequest.getCustomerId(),
                    checkoutRequest.getVehicleId())
            );
            return new ResponseEntity<>(new CheckoutResponse(false, customer, vehicle), HttpStatus.OK);
        }
    }

    private Customer getCustomerById(String customerId) {
        String url = customerBaseUrl + "/" + customerId;

        try {
            Thread.sleep(new Random().nextInt(5) + 3 * 1000L);
            return restTemplate.getForObject(url, Customer.class);
        } catch (Exception e) {
            logger.error("Exception looking up customer ID " + customerId, e);
        }

        return null;
    }

    private Vehicle getVehicleById(String vehicleId) {
        String url = vehicleBaseUrl + "/" + vehicleId;

        try {
            return restTemplate.getForObject(url, Vehicle.class);
        } catch (Exception e) {
            logger.error("Exception looking up vehicle ID " + vehicleId, e);
        }

        return null;
    }
}
