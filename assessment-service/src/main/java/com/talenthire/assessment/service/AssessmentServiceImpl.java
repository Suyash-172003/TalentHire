package com.talenthire.assessment.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.talenthire.assessment.dto.AssessmentRequest;
import com.talenthire.assessment.dto.AssessmentResponse;
import com.talenthire.assessment.dto.CodeExecutionRequest;
import com.talenthire.assessment.dto.CodeExecutionResponse;
import com.talenthire.assessment.dto.CodingQuestionResponse;
import com.talenthire.assessment.dto.CreateAssessmentRequest;
import com.talenthire.assessment.dto.CreateAssessmentResponse;
import com.talenthire.assessment.dto.CreateCodingQuestionRequest;
import com.talenthire.assessment.dto.CreateCodingQuestionResponse;
import com.talenthire.assessment.dto.CreateTestCaseRequest;
import com.talenthire.assessment.dto.ExecutionStatus;
import com.talenthire.assessment.dto.RunResult;
import com.talenthire.assessment.dto.SampleTestCaseResponse;
import com.talenthire.assessment.dto.TestCaseDto;
import com.talenthire.assessment.dto.TestCaseResultDto;
import com.talenthire.assessment.dto.VerifyJobResponse;
import com.talenthire.assessment.entity.Assessment;
import com.talenthire.assessment.entity.CodingQuestion;
import com.talenthire.assessment.entity.CodingTestCase;
import com.talenthire.assessment.mapper.AssessmentMapper;
import com.talenthire.assessment.repository.AssessmentRepository;
import com.talenthire.assessment.repository.CodingQuestionRepository;
import com.talenthire.assessment.repository.CodingTestCaseRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {
	
	
	private final CodeExecutorFactory executorFactory;
	private final AssessmentRepository assessmentRepository;
	private final JobClient jobClient;
	private final CodingQuestionRepository codingQuestionRepository;
	private final CodingTestCaseRepository codingTestCaseRepository;
	private final AssessmentMapper assessmentMapper;

	@Override
	public CodeExecutionResponse execute(CodeExecutionRequest request) {
		// TODO Auto-generated method stub
		
		CodeExecutor executor =
		        executorFactory.getExecutor(request.getLanguage());
		CodeExecutionResponse response = new CodeExecutionResponse();

		
		try {
			Path workSpace=createWorkSpace();
			
			String fileName;

			switch (request.getLanguage().toLowerCase()) {

			    case "java":
			        fileName = "Main.java";
			        break;

			    case "cpp":
			        fileName = "main.cpp";
			        break;

			    case "python":
			        fileName = "main.py";
			        break;

			    default:
			        throw new IllegalArgumentException("Unsupported language");
			}
			
			Path javaFile=workSpace.resolve(fileName);
			
			Files.writeString(javaFile,request.getSourceCode());
			
			String compileError=executor.compile(workSpace);
			
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
				
				RunResult output=executor.run(workSpace,testCase.getInput());
				
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

	@Override
	public List<CodingQuestionResponse> getCodingQuestions(Integer assessmentId) {

	    List<CodingQuestion> questions =
	            codingQuestionRepository
	                    .findByAssessmentAssessmentIdOrderByQuestionOrder(
	                            assessmentId);

	    List<CodingQuestionResponse> response = new ArrayList<>();

	    for (CodingQuestion question : questions) {

	        CodingQuestionResponse dto = new CodingQuestionResponse();

	        dto.setCodingQuestionId(question.getCodingQuestionId());
	        dto.setTitle(question.getTitle());
	        dto.setProblemStatement(question.getProblemStatement());
	        dto.setMarks(question.getMarks());
	        dto.setQuestionOrder(question.getQuestionOrder());

	        List<SampleTestCaseResponse> sampleTestCases = question.getTestCases()
	                .stream()
	                .filter(CodingTestCase::getSample)
	                .map(testCase -> new SampleTestCaseResponse(
	                        testCase.getInput(),
	                        testCase.getExpectedOutput()))
	                .toList();

	        dto.setSampleTestCases(sampleTestCases);

	        response.add(dto);
	    }
	    

	    return response;
	}
	
	
	@Override
    public AssessmentResponse createAssessment(AssessmentRequest request) {

        Assessment assessment = assessmentMapper.toEntity(request);

        Assessment savedAssessment = assessmentRepository.save(assessment);

        return assessmentMapper.toResponse(savedAssessment);
    }

    @Override
    public AssessmentResponse getAssessmentById(Integer id) {

        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        return assessmentMapper.toResponse(assessment);
    }

    @Override
    public List<AssessmentResponse> getAllAssessments() {

        List<Assessment> assessments = assessmentRepository.findAll();

        return assessments.stream()
                .map(assessmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AssessmentResponse updateAssessment(Integer id, AssessmentRequest request) {

        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        assessment.setJobId(request.getJobId());
        assessment.setTitle(request.getTitle());
        assessment.setAssessmentType(request.getAssessmentType());
        assessment.setDuration(request.getDuration());
        assessment.setPassMarks(request.getPassMarks());
        assessment.setStartTime(request.getStartTime());
        assessment.setEndTime(request.getEndTime());
        assessment.setCreatedBy(request.getCreatedBy());
        assessment.setUpdatedAt(LocalDateTime.now());

        Assessment updatedAssessment = assessmentRepository.save(assessment);

        return assessmentMapper.toResponse(updatedAssessment);
    }

    @Override
    public void deleteAssessment(Integer id) {

        Assessment assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        assessmentRepository.delete(assessment);
    }
    
    @Override
    public AssessmentResponse getAssessmentByJobId(Integer jobId) {

        Assessment assessment = assessmentRepository
                .findFirstByJobId(jobId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        return assessmentMapper.toResponse(assessment);
    }

	

	
	

}
