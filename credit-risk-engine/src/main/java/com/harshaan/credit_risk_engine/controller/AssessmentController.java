package com.harshaan.credit_risk_engine.controller;
import com.harshaan.credit_risk_engine.api.AssessmentResponse;
import com.harshaan.credit_risk_engine.service.AssessmentService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Validated
@RestController
@RequestMapping("/v1/assessments")
public class AssessmentController {
    private final AssessmentService service;

    public AssessmentController(AssessmentService service) {
        this.service = service;
    }

    //@PostMapping("/{applicationId}")
    @PostMapping("/{applicationId}")
    public AssessmentResponse assess(@PathVariable @Positive long applicationId) {
        return service.assessOrGetLatest(applicationId);
    }

    // Browser-friendly GET: fetch latest without creating a new one
    @GetMapping("/{applicationId}/latest")
    public AssessmentResponse latest(@PathVariable @Positive long applicationId) {
        return service.getLatest(applicationId);
    }
}
