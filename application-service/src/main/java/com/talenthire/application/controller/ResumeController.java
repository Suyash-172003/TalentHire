package com.talenthire.application.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.talenthire.application.dto.UploadResumeResponse;
import com.talenthire.application.service.ResumeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {
	
	private final ResumeService resumeService;
	
	
	@PostMapping("/upload")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file,
            @RequestHeader("X-User-Id") Integer candidateId) {

        UploadResumeResponse response =
                resumeService.uploadResume(file, candidateId);

        return ResponseEntity.ok(response);
    }
	
	@GetMapping("/view")
	public ResponseEntity<?> viewResume(
	        @RequestHeader("X-User-Id") Integer candidateId) {

		Resource resource = resumeService.viewResume(candidateId);

	    return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_PDF)
	            .header(HttpHeaders.CONTENT_DISPOSITION,
	                    "inline; filename=\"resume.pdf\"")
	            .body(resource);
	}

}
