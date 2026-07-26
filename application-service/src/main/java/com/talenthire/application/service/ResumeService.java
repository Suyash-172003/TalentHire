package com.talenthire.application.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.talenthire.application.dto.UploadResumeResponse;

public interface ResumeService {

	 UploadResumeResponse uploadResume(MultipartFile file, Integer candidateId);

	 Resource viewResume(Integer candidateId);

}
