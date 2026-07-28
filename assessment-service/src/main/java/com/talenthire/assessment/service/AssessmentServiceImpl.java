package com.talenthire.assessment.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.talenthire.assessment.docker.DockerExecutor;
import com.talenthire.assessment.dto.CodeExecutionRequest;
import com.talenthire.assessment.dto.CodeExecutionResponse;
import com.talenthire.assessment.dto.CreateAssessmentRequest;
import com.talenthire.assessment.dto.CreateAssessmentResponse;
import com.talenthire.assessment.dto.CreateCodingQuestionRequest;
import com.talenthire.assessment.dto.CreateCodingQuestionResponse;
import com.talenthire.assessment.dto.CreateTestCaseRequest;
import com.talenthire.assessment.dto.ExecutionStatus;
import com.talenthire.assessment.dto.RunResult;
import com.talenthire.assessment.dto.TestCaseDto;
import com.talenthire.assessment.dto.TestCaseResultDto;
import com.talenthire.assessment.dto.VerifyJobResponse;
import com.talenthire.assessment.entity.Assessment;
import com.talenthire.assessment.entity.CodingQuestion;
import com.talenthire.assessment.entity.CodingTestCase;
import com.talenthire.assessment.repository.AssessmentRepository;
import com.talenthire.assessment.repository.CodingQuestionRepository;
import com.talenthire.assessment.repository.CodingTestCaseRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {
	
	private final DockerExecutor dockerExecutor;
	private final AssessmentRepository assessmentRepository;
	private final JobClient jobClient;
	private final CodingQuestionRepository codingQuestionRepository;
	private final CodingTestCaseRepository codingTestCaseRepository;

	@Override
	public CodeExecutionResponse execute(CodeExecutionRequest request) {
		// TODO Auto-generated method stub
		CodeExecutionResponse response = new CodeExecutionResponse();

		
		try {
			Path workSpace=createWorkSpace();
			
			Path javaFile=workSpace.resolve("Main.java");
			
			Files.writeString(javaFile,request.getSourceCode());
			
			String compileError=dockerExecutor.compile(workSpace);
			
			if(compileError!=null)
			{
				
				response.setStatus(ExecutionStatus.COMPILATION_ERROR);

				response.setCompileError(compileError);

				response.setPassedTestCases(0);

				response.setTotalTestCases(request.getTestcases().size());

				response.setResults(Collections.emptyList());

				response.setRuntimeError(null);

				response.setExecutionTime(null);

				return response;
			}
			
			List<TestCaseResultDto> results = new ArrayList<>();
			
			int passed=0;
			
			 long executionTime = 0;
			
			for(int i=0;i<request.getTestcases().size();i++)
			{
				
				TestCaseDto testCase=request.getTestcases().get(i);
				
				RunResult output=dockerExecutor.run(workSpace,testCase.getInput());
				
				executionTime=output.getExecutionTime();
				
				if(output.getRuntimeError()!=null)
				{


					response.setStatus(ExecutionStatus.RUNTIME_ERROR);

					response.setCompileError(null);

					response.setPassedTestCases(passed);

					response.setTotalTestCases(request.getTestcases().size());

					response.setResults(results);

					response.setRuntimeError(output.getRuntimeError());

					response.setExecutionTime(output.getExecutionTime());

					return response;
				}
				
				 TestCaseResultDto result = new TestCaseResultDto();
				 
				 result.setTestCaseNumber(i+1);
				 result.setExpectedOutput(testCase.getExpectedOutput());
				 result.setActualOutput(output.getOutput());
				result.setInput(testCase.getInput());
				
				boolean isPassed=output.getOutput().trim().equals(testCase.getExpectedOutput().trim());
				
				result.setPassed(isPassed);
				
				if(isPassed)
				{
					
					passed++;
				}
				
				results.add(result);
				
			
			}
			

			response.setStatus(ExecutionStatus.SUCCESS);

			response.setCompileError(null);

			response.setPassedTestCases(passed);

			response.setTotalTestCases(request.getTestcases().size());

			response.setResults(results);

			response.setRuntimeError(null);

			response.setExecutionTime(executionTime);

			return response;
			
			
		} catch (IOException e) {
			throw new RuntimeException("Failed to create workspace.", e);

		}
		
		
	}

	private Path createWorkSpace() throws IOException {
		
		Path path=Path.of("C:\\Submission");
		return Files.createTempDirectory(path,"submission-");
	}

	@Override
	public CreateAssessmentResponse createAssessment(CreateAssessmentRequest request, Integer recruiterId) {
		

	    VerifyJobResponse job =
	            jobClient.getJobById(request.getJobId());

	    if (!job.getRecruiterId().equals(recruiterId)) {
	        throw new RuntimeException("You are not authorized.");
	    }

	    Assessment assessment = new Assessment();

	    assessment.setJobId(request.getJobId());
	    assessment.setTitle(request.getTitle());
	    assessment.setDescription(request.getDescription());
	    assessment.setDuration(request.getDuration());
	  

	    Assessment saved =
	            assessmentRepository.save(assessment);

	    CreateAssessmentResponse response =
	            new CreateAssessmentResponse();

	    response.setAssessmentId(saved.getAssessmentId());
	    response.setStatus(saved.getStatus().name());
	    response.setMessage("Assessment created successfully.");
	    

	    return response;
	}

	public CreateCodingQuestionResponse addCodingQuestion(Integer assessmentId, Integer recruiterId,
			CreateCodingQuestionRequest request) {
		 Assessment assessment = assessmentRepository.findById(assessmentId)
		            .orElseThrow(() ->
		                    new RuntimeException("Assessment not found"));

		    VerifyJobResponse job =
		            jobClient.getJobById(assessment.getJobId());

		    if (!job.getRecruiterId().equals(recruiterId)) {
		        throw new RuntimeException("Unauthorized.");
		    }

		    CodingQuestion question = new CodingQuestion();

		    question.setAssessment(assessment);
		    question.setTitle(request.getTitle());
		    question.setProblemStatement(request.getProblemStatement());
		    question.setMarks(request.getMarks());
		    question.setQuestionOrder(request.getQuestionOrder());

		    CodingQuestion saved =
		            codingQuestionRepository.save(question);

		    assessment.setTotalMarks(
		            assessment.getTotalMarks() + request.getMarks());

		    assessmentRepository.save(assessment);

		    CreateCodingQuestionResponse response =
		            new CreateCodingQuestionResponse();

		    response.setCodingQuestionId(saved.getCodingQuestionId());
		    response.setMessage("Coding Question Added Successfully.");

		    return response;
	}

	@Override
	public String addTestCase(Integer codingQuestionId, Integer recruiterId, CreateTestCaseRequest request) {
		 CodingQuestion question =
		            codingQuestionRepository.findById(codingQuestionId)
		            .orElseThrow(() ->
		                    new RuntimeException("Coding Question not found"));

		    VerifyJobResponse job =
		            jobClient.getJobById(
		                    question.getAssessment().getJobId());

		    if (!job.getRecruiterId().equals(recruiterId)) {
		        throw new RuntimeException("Unauthorized.");
		    }

		    CodingTestCase testCase = new CodingTestCase();

		    testCase.setCodingQuestion(question);
		    testCase.setInput(request.getInput());
		    testCase.setExpectedOutput(request.getExpectedOutput());
		    testCase.setSample(request.getIsSample());

		    CodingTestCase saved =
		            codingTestCaseRepository.save(testCase);

		   

		    return "Test Case Added Successfully.";
	}
	
	

	

	
	

}
