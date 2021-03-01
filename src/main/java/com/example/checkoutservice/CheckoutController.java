package com.example.checkoutservice;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class CheckoutController {

    private final Logger logger = LogManager.getLogger(CheckoutController.class);

    @PostMapping("/checkout")
    public ResponseEntity<Object> getVehicles() {
        logger.info("Starting in CheckoutController");

        logger.info("Leaving CheckoutController");

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
