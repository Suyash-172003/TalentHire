package com.talenthire.assessment.docker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.talenthire.assessment.dto.RunResult;
import com.talenthire.assessment.service.CodeExecutor;

@Component
public class PythonExecutor implements CodeExecutor {

    private static final long RUN_TIMEOUT = 10;

    @Override
    public String getLanguage() {
        return "python";
    }

    @Override
    public String compile(Path workspace) {

       
        return null;
    }

    @Override
    public RunResult run(
            Path workspace,
            String input) {

        RunResult result = new RunResult();

        String containerName =
                "talenthire-python-runner-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8);


        ProcessBuilder builder = new ProcessBuilder(
                "docker",
                "run",
                "--rm",

                "--name",
                containerName,

                // CPU limit
                "--cpus=1",

                // Memory limit
                "--memory=256m",

                "-i",

                "-v",
                workspace.toAbsolutePath()
                        + ":/workspace",

                "-w",
                "/workspace",

                "talenthire-python-runner",

                "python3",
                "main.py"
        );


        try {

            long startTime =
                    System.nanoTime();

            Process process =
                    builder.start();


          

            OutputStream os =
                    process.getOutputStream();

            os.write(input.getBytes());

            os.flush();
            os.close();


            

            boolean finished =
                    process.waitFor(
                            RUN_TIMEOUT,
                            TimeUnit.SECONDS
                    );


            long endTime =
                    System.nanoTime();


            result.setExecutionTime(
                    TimeUnit.NANOSECONDS.toMillis(
                            endTime - startTime
                    )
            );


            
            if (!finished) {

                process.destroyForcibly();

                killContainer(containerName);

                result.setRuntimeError(
                        "Time Limit Exceeded"
                );

                result.setOutput(null);

                return result;
            }


           

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    process.getInputStream()
                            )
                    );

            StringBuilder output =
                    new StringBuilder();

            String line;

            while ((line =
                    reader.readLine()) != null) {

                output.append(line)
                     .append("\n");
            }


           
            int exitCode =
                    process.exitValue();


            if (exitCode != 0) {

                BufferedReader errorReader =
                        new BufferedReader(
                                new InputStreamReader(
                                        process.getErrorStream()
                                )
                        );

                StringBuilder error =
                        new StringBuilder();

                while ((line =
                        errorReader.readLine()) != null) {

                    error.append(line)
                         .append("\n");
                }

                result.setRuntimeError(
                        error.toString()
                );

                result.setOutput(null);

            } else {

                result.setOutput(
                        output.toString()
                );

                result.setRuntimeError(null);
            }


            return result;


        } catch (IOException e) {

            result.setRuntimeError(
                    "Execution failed: "
                    + e.getMessage()
            );

            return result;


        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            result.setRuntimeError(
                    "Execution interrupted"
            );

            return result;
        }
    }


    // ==============================
    // KILL CONTAINER
    // ==============================

    private void killContainer(
            String containerName) {

        try {

            Process killProcess =
                    new ProcessBuilder(
                            "docker",
                            "kill",
                            containerName
                    ).start();

            killProcess.waitFor(
                    2,
                    TimeUnit.SECONDS
            );

        } catch (Exception e) {

            // Container may already be stopped
        }
    }
}