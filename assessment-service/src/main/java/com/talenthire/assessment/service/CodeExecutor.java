package com.talenthire.assessment.service;

import java.nio.file.Path;

import com.talenthire.assessment.dto.RunResult;

public interface CodeExecutor {

	public String getLanguage();

	public String compile(Path workspace);

	public  RunResult run(Path workspace, String input);
}
