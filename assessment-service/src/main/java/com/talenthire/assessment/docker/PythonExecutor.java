package com.talenthire.assessment.docker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.talenthire.assessment.dto.RunResult;
import com.talenthire.assessment.service.CodeExecutor;

@Component
public class PythonExecutor implements CodeExecutor {
	
	@Override
    public String getLanguage() {
        return "python";
    }

    @Override
    public String compile(Path workspace) {
        // Python is interpreted.
        return null;
    }

    @Override
    public RunResult run(Path workspace, String input) {

        RunResult result = new RunResult();

        ProcessBuilder builder = new ProcessBuilder(
                "docker",
                "run",
                "--rm",
                "-i",
                "-v",
                workspace.toAbsolutePath() + ":/workspace",
                "-w",
                "/workspace",
                "talenthire-python-runner",
                "python3",
                "main.py"
        );

        try {

            long startTime = System.nanoTime();

            Process process = builder.start();

            OutputStream os = process.getOutputStream();

            os.write(input.getBytes());
            os.flush();
            os.close();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder output = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();

            long endTime = System.nanoTime();

            result.setExecutionTime(
                    TimeUnit.NANOSECONDS.toMillis(endTime - startTime));

            if (exitCode != 0) {

                BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()));

                StringBuilder error = new StringBuilder();

                while ((line = errorReader.readLine()) != null) {
                    error.append(line).append("\n");
                }

                result.setRuntimeError(error.toString());
                result.setOutput(null);

            } else {

                result.setOutput(output.toString());
                result.setRuntimeError(null);

            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
