package com.talenthire.assessment.docker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.talenthire.assessment.dto.RunResult;
import com.talenthire.assessment.service.CodeExecutor;

@Component
public class JavaExecutor implements CodeExecutor {
	
	

	public String compile(Path workSpace) {
		
		ProcessBuilder builder = new ProcessBuilder(
		        "docker",
		        "run",
		        "--rm",
		        "-v",
		        workSpace.toAbsolutePath() + ":/workspace",
		        "-w",
		        "/workspace",
		        "talenthire-java-runner",
		        "javac",
		        "Main.java"
		);
		
		
		try {
			Process process=builder.start();
			
			int exitCode=process.waitFor();
			
			if(exitCode!=0)
			{
				BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(process.getErrorStream()));
				
				StringBuilder sb=new StringBuilder();
				String line;
				
				while((line=bufferedReader.readLine()) != null)
				{
					sb.append(line).append("\n");
				}
				
				return sb.toString();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	public RunResult run(Path workSpace, String input) {
		
		RunResult result=new RunResult();
		ProcessBuilder builder = new ProcessBuilder(
		        "docker",
		        "run",
		        "--rm",
		        "-i",
		        "-v",
		        workSpace.toAbsolutePath() + ":/workspace",
		        "-w",
		        "/workspace",
		        "talenthire-java-runner",
		        "java",
		        "Main"
		);
		
		try {
			
			long startTime = System.nanoTime();
			Process process = builder.start();
			
			OutputStream outputStream=process.getOutputStream();
			outputStream.write(input.getBytes());
			outputStream.flush();
			outputStream.close();
			
			
			BufferedReader reader = new BufferedReader(
			        new InputStreamReader(process.getInputStream()));

			StringBuilder output = new StringBuilder();

			String line;

			while ((line = reader.readLine()) != null)
			{
			    output.append(line).append("\n");
			}
			
			int exitCode = process.waitFor();
			long endTime = System.nanoTime();
			
			result.setExecutionTime(TimeUnit.NANOSECONDS.toMillis(endTime-startTime));
			
			 if (exitCode != 0) {

		            BufferedReader errorReader = new BufferedReader(
		                    new InputStreamReader(process.getErrorStream()));

		            StringBuilder error = new StringBuilder();

		            while ((line = errorReader.readLine()) != null) {
		                error.append(line).append("\n");
		            }

		           result.setRuntimeError(error.toString());
		           result.setOutput(null);
		        }
			 else
			 {
				 result.setOutput(output.toString());
				 result.setRuntimeError(null);
			 }
			 
			
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	@Override
	public String getLanguage() {
		return "java";
	}

	

}
