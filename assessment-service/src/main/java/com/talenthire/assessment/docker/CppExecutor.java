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
public class CppExecutor implements CodeExecutor {

    private static final long COMPILE_TIMEOUT = 60;
    private static final long RUN_TIMEOUT = 10;

    @Override
    public String getLanguage() {
        return "cpp";
    }

    @Override
    public String compile(Path workSpace) {

        String containerName =
                "talenthire-cpp-compile-" +
                UUID.randomUUID().toString().substring(0, 8);

        ProcessBuilder builder = new ProcessBuilder(
                "docker",
                "run",
                "--rm",

                "--name",
                containerName,

                "--cpus=1",

                "--memory=256m",

                "--mount",
                "type=bind,source=" +
                        getHostWorkspacePath(workSpace) +
                        ",target=/workspace",

                "-w",
                "/workspace",

                "talenthire-cpp-runner",

                "g++",
                "main.cpp",
                "-o",
                "main"
        );

        try {

            Process process = builder.start();

            boolean finished =
                    process.waitFor(
                            COMPILE_TIMEOUT,
                            TimeUnit.SECONDS
                    );

            if (!finished) {

                process.destroyForcibly();

                killContainer(containerName);

                return "Compilation Time Limit Exceeded";
            }

            int exitCode =
                    process.exitValue();

            if (exitCode != 0) {

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        process.getErrorStream()
                                )
                        );

                StringBuilder error =
                        new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {

                    error.append(line)
                         .append("\n");
                }

                return error.toString();
            }

        } catch (IOException e) {

            return "Compilation failed: " +
                    e.getMessage();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            return "Compilation interrupted";
        }

        return null;
    }

    @Override
    public RunResult run(
            Path workSpace,
            String input) {

        RunResult result =
                new RunResult();

        String containerName =
                "talenthire-cpp-runner-" +
                UUID.randomUUID().toString().substring(0, 8);

        ProcessBuilder builder = new ProcessBuilder(
                "docker",
                "run",
                "--rm",

                "--name",
                containerName,

                "--cpus=1",

                "--memory=256m",

                "-i",

                "--mount",
                "type=bind,source=" +
                        getHostWorkspacePath(workSpace) +
                        ",target=/workspace",

                "-w",
                "/workspace",

                "talenthire-cpp-runner",

                "./main"
        );

        try {

            long startTime =
                    System.nanoTime();

            Process process =
                    builder.start();

            OutputStream outputStream =
                    process.getOutputStream();

            if (input != null) {

                outputStream.write(
                        input.getBytes()
                );
            }

            outputStream.flush();
            outputStream.close();

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

            while ((line = reader.readLine()) != null) {

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

                while ((line = errorReader.readLine()) != null) {

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
                    "Execution failed: " +
                    e.getMessage()
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

    private String getHostWorkspacePath(Path workSpace) {

        String containerPath =
                workSpace.toAbsolutePath().toString();

        String submissionPath =
                "/submissions";

        String hostSubmissionPath =
                System.getenv("HOST_SUBMISSION_PATH");

        if (hostSubmissionPath == null ||
                hostSubmissionPath.isBlank()) {

            throw new IllegalStateException(
                    "HOST_SUBMISSION_PATH is not configured"
            );
        }

        if (containerPath.startsWith(submissionPath)) {

            String relativePath =
                    containerPath.substring(
                            submissionPath.length()
                    );

            return hostSubmissionPath +
                    relativePath;
        }

        return containerPath;
    }

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