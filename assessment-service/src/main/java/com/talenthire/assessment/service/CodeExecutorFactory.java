package com.talenthire.assessment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.talenthire.assessment.exception.InvalidRequestException;

@Component
public class CodeExecutorFactory {

	    private final Map<String, CodeExecutor> executors = new HashMap<>();

	    public CodeExecutorFactory(List<CodeExecutor> executorList) {

	        for (CodeExecutor executor : executorList) {
	            executors.put(executor.getLanguage(), executor);
	        }
	    }

	    public CodeExecutor getExecutor(String language) {

	        CodeExecutor executor = executors.get(language.toLowerCase());

	        if (executor == null) {
	            throw new InvalidRequestException(
	                    "Language not supported: " + language);
	        }

	        return executor;
	    }
	}

