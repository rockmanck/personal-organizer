package com.jediorganizer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Health check controller for basic application monitoring.
 * 
 * Provides endpoints to verify that the application is running correctly.
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    /**
     * Basic health check endpoint.
     * 
     * @return ResponseEntity with health status
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "timestamp", LocalDateTime.now(),
            "service", "jedi-organizer-backend",
            "version", "1.0.0-SNAPSHOT"
        ));
    }

    /**
     * Detailed system information endpoint.
     * 
     * @return ResponseEntity with system details
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Runtime runtime = Runtime.getRuntime();
        
        return ResponseEntity.ok(Map.of(
            "application", Map.of(
                "name", "Jedi Organizer Backend",
                "version", "1.0.0-SNAPSHOT",
                "description", "Personal productivity application implementing Jedi Techniques methodology"
            ),
            "system", Map.of(
                "java.version", System.getProperty("java.version"),
                "java.vendor", System.getProperty("java.vendor"),
                "os.name", System.getProperty("os.name"),
                "os.version", System.getProperty("os.version"),
                "memory.total", runtime.totalMemory(),
                "memory.free", runtime.freeMemory(),
                "memory.max", runtime.maxMemory()
            ),
            "timestamp", LocalDateTime.now()
        ));
    }
}
