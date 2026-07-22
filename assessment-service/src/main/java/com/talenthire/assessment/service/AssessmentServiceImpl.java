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
import com.talenthire.assessment.dto.ExecutionStatus;
import com.talenthire.assessment.dto.RunResult;
import com.talenthire.assessment.dto.TestCaseDto;
import com.talenthire.assessment.dto.TestCaseResultDto;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {
	
	private final DockerExecutor dockerExecutor;

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

	

	
	

}
