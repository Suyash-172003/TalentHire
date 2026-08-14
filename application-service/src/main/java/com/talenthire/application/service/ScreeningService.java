package com.talenthire.application.service;

import com.talenthire.application.dto.ScreenApplicationResponse;

public interface ScreeningService {
	ScreenApplicationResponse screenApplication(Integer applicationId,String resumeText);

}
