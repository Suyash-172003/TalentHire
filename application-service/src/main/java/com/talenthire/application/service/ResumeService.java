package com.talenthire.application.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.talenthire.application.dto.ResumeResponse;
import com.talenthire.application.dto.UploadResumeResponse;

public interface ResumeService {

	 UploadResumeResponse uploadResume(MultipartFile file, Integer candidateId);

	 Resource viewResume(Integer resumeId,Integer candidateId);

	 List<ResumeResponse> getMyResumes(Integer candidateId);

}
