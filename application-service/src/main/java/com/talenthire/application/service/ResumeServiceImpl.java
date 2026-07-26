package com.talenthire.application.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.talenthire.application.dto.UploadResumeResponse;
import com.talenthire.application.entity.Resume;
import com.talenthire.application.entity.ResumeSkill;
import com.talenthire.application.repository.ResumeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
	
	private final ResumeRepository resumeRepository;
	
	@Value("${file.upload-path}")
	private String uploadPath;

	@Override
	public UploadResumeResponse uploadResume(MultipartFile file, Integer candidateId) {
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
	     
	     List<ResumeSkill> skills=new ArrayList();
	     
	     ResumeSkill skill1 = new ResumeSkill();
	     skill1.setSkillName("Java");
	     skill1.setResume(resume);
	     skills.add(skill1);

	     ResumeSkill skill2 = new ResumeSkill();
	     skill2.setSkillName("Spring Boot");
	     skill2.setResume(resume);
	     skills.add(skill2);
	     
	     resume.setResumeSkills(skills);
	     
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
		
		 fileName = fileName.replaceAll("\\s+", "_");
		
	
		return candidateId + "_"+ fileName;
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

	
	public Resource viewResume(Integer candidateId) {
		Resume resume= resumeRepository.findByCandidateId(candidateId).orElseThrow(()-> new RuntimeException("Resume not found"));
	Path path=Paths.get(resume.getFileUrl());
	
	Resource resource = new FileSystemResource(path); 
	if (!resource.exists())
	{ throw new RuntimeException("Resume file not found.");
	}
		
		return resource;
	}

}
