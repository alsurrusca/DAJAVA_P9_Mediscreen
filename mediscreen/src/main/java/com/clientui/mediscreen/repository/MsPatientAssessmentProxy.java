package com.clientui.mediscreen.repository;

import com.clientui.mediscreen.domain.PatientAssessmentBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="ms-patientAssessment", url = "localhost:8083")
public interface MsPatientAssessmentProxy {


@GetMapping("/view/{patId}")
 String getCalculateRisk(@PathVariable("patId") int patId);
}
