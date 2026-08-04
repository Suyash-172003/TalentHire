package com.talenthire.application.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.talenthire.application.dto.JobResponse;
import com.talenthire.application.dto.ResumeResponse;
import com.talenthire.application.dto.UploadResumeResponse;
import com.talenthire.application.entity.ApplicationScreening;
import com.talenthire.application.entity.Resume;
import com.talenthire.application.entity.ResumeSkill;
import com.talenthire.application.entity.ScreeningStatus;
import com.talenthire.application.repository.ApplicationScreeningRepository;
import com.talenthire.application.repository.ResumeRepository;
import com.talenthire.application.util.ResumeTextExtractor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
	
	private final ResumeRepository resumeRepository;
	
	
	private final JobClient jobClient;
	private final ResumeTextExtractor resumeTextExtractor;
	
	@Value("${file.upload-path}")
	private String uploadPath;

	@Override
	public UploadResumeResponse uploadResume(MultipartFile file, Integer candidateId){
		 validateFile(file);

	     String fileName = generateFileName(file, candidateId);

	     try {
			saveFile(file, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	     
	     Resume resume=new Resume();
	     resume.setFileName(fileName);
	     resume.setFileUrl(uploadPath+ "/"+ fileName);
	     resume.setCandidateId(candidateId);
	
	     
//	    	 List<ResumeSkill> resumeSkills = new ArrayList<>();
//
//	    	 for (String skillName : extractedSkills) {
//
//	    	     ResumeSkill resumeSkill = new ResumeSkill();
//
//	    	     resumeSkill.setSkillName(skillName);
//	    	     resumeSkill.setResume(resume);
//
//	    	     resumeSkills.add(resumeSkill);
//	    	 }
//
//	    	 resume.setResumeSkills(resumeSkills);
	    	 

	     Resume saved=resumeRepository.save(resume);
	     
	     
	     
	     
	     UploadResumeResponse response= new UploadResumeResponse();
	     
	     response.setResumeId(saved.getResumeId());
	     response.setFileName(fileName);
	     response.setFilePath(uploadPath+ "/"+ fileName);
	     response.setMessage("Resume Uploaded Successfully");
	     
		return response;
	}

	private void saveFile(MultipartFile file, String fileName) throws IOException {
		Path uploadDirectory =  Paths.get("")
                .toAbsolutePath()
                .resolve(uploadPath);

		  if (!Files.exists(uploadDirectory)) {
		        Files.createDirectories(uploadDirectory);
		    }
		  
		  Path destinationPath=uploadDirectory.resolve(fileName);
		  
		  file.transferTo(destinationPath.toFile());
	}

	private String generateFileName(MultipartFile file, Integer candidateId) {
		String fileName=file.getOriginalFilename();
		
		if (fileName == null || fileName.isBlank()) {
	        fileName = "resume.pdf";
	    }
		
		 fileName = fileName.replaceAll("\\s+", "_");
		 String uniqueId=UUID.randomUUID().toString().substring(0,8);
		
	
		return candidateId + "_" + uniqueId + "_"+  fileName;
	}

	private void validateFile(MultipartFile file) {
		if(file==null || file.isEmpty())
		{
			 throw new RuntimeException("Resume file is required.");
		}
		
		String fileName=file.getOriginalFilename();
		if(!(fileName.endsWith(".pdf")
                || fileName.endsWith(".doc")
                || fileName.endsWith(".docx")))
				{
			throw new RuntimeException("Only PDF, DOC and DOCX files are allowed.");
				}
		
	}

	
	public Resource viewResume(Integer resumeId, Integer candidateId) {

	    Resume resume = resumeRepository.findById(resumeId)
	            .orElseThrow(() -> new RuntimeException("Resume not found"));

	    if (!resume.getCandidateId().equals(candidateId)) {
	        throw new RuntimeException("You are not authorized to view this resume.");
	    }

	    Path path = Paths.get(resume.getFileUrl());

	    Resource resource = new FileSystemResource(path);

	    if (!resource.exists()) {
	        throw new RuntimeException("Resume file not found.");
	    }

	    return resource;
	}

	@Override
	public List<ResumeResponse> getMyResumes(Integer candidateId) {
		List<Resume> resumes =
	            resumeRepository.findAllByCandidateId(candidateId);

	    List<ResumeResponse> response = new ArrayList<>();

	    for (Resume resume : resumes) {

	        ResumeResponse dto = new ResumeResponse();

	        dto.setResumeId(resume.getResumeId());
	        dto.setFileName(resume.getFileName());
	        dto.setFileUrl(resume.getFileUrl());
	        response.add(dto);
	    }

	    return response;
		
	}

	@Override
	public Resource recruiterViewResume(Integer resumeId) {
	    Resume resume = resumeRepository.findById(resumeId)
	            .orElseThrow(() ->
	                    new RuntimeException("Resume not found."));

	    Path path = Paths.get(resume.getFileUrl());

	    Resource resource = new FileSystemResource(path);

	    if (!resource.exists()) {
	        throw new RuntimeException("Resume file not found.");
	    }

	    return resource;

	}

}
