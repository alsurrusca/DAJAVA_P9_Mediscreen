package com.clientui.mediscreen.repository;

import com.clientui.mediscreen.domain.NotesBean;
import com.clientui.mediscreen.domain.PatientAssessmentBean;
import com.clientui.mediscreen.domain.PatientBeans;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ms-patientAssessment", url = "localhost:8083")
public interface MsPatientAssessmentProxy {


@GetMapping("/view/{patId}")
 String getPatientAssessment(@PathVariable("patId") int patId, String noteId);
}
