package com.talenthire.assessment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talenthire.assessment.dto.AssessmentResultResponse;
import com.talenthire.assessment.dto.ExamResultResponse;
import com.talenthire.assessment.dto.StartExamRequest;
import com.talenthire.assessment.dto.StartExamResponse;
import com.talenthire.assessment.dto.SubmitAnswerRequest;
import com.talenthire.assessment.dto.SubmitExamRequest;
import com.talenthire.assessment.entity.Assessment;
import com.talenthire.assessment.entity.MCQExamAttempt;
import com.talenthire.assessment.entity.MCQQuestion;
import com.talenthire.assessment.entity.ExamStatus;
import com.talenthire.assessment.repository.AssessmentRepository;
import com.talenthire.assessment.repository.MCQExamAttemptRepository;
import com.talenthire.assessment.repository.MCQQuestionRepository;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MCQExamServiceImpl implements MCQExamService {

    private final AssessmentRepository assessmentRepository;
    private final MCQQuestionRepository mcqQuestionRepository;
    private final MCQExamAttemptRepository attemptRepository;

    @Override
    public StartExamResponse startExam(StartExamRequest request) {

        Assessment assessment = assessmentRepository.findById(request.getAssessmentId())
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        // Check existing attempt
//        attemptRepository.findByAssessment_AssessmentIdAndCandidateId(
//                request.getAssessmentId(),
//                request.getCandidateId())
//                .ifPresent(existing -> {
//
//                    if (existing.getStatus() == ExamStatus.COMPLETED) {
//                        throw new RuntimeException("You have already completed this assessment.");
//                    }
//
//                    if (existing.getStatus() == ExamStatus.IN_PROGRESS) {
//                        throw new RuntimeException(
//                                "Exam already started. Continue with Attempt ID : "
//                                        + existing.getId());
//                    }
//
//                });
        
        Optional<MCQExamAttempt> existingAttempt =
                attemptRepository.findByAssessment_AssessmentIdAndCandidateId(
                        request.getAssessmentId(),
                        request.getCandidateId());

        if (existingAttempt.isPresent()) {

            MCQExamAttempt existing = existingAttempt.get();

            if (existing.getStatus() == ExamStatus.COMPLETED) {
                throw new RuntimeException("You have already completed this assessment.");
            }

            if (existing.getStatus() == ExamStatus.IN_PROGRESS) {

                return StartExamResponse.builder()
                        .attemptId(existing.getId())
                        .assessmentId(existing.getAssessment().getAssessmentId())
                        .candidateId(existing.getCandidateId())
                        .startTime(existing.getStartTime())
                        .duration(existing.getAssessment().getDuration())
                        .totalQuestions(existing.getTotalQuestions())
                        .totalMarks(existing.getTotalMarks())
                        .message("Resuming existing attempt")
                        .build();
            }
        }

        // Fetch questions
        List<MCQQuestion> questions =
        		mcqQuestionRepository.findByAssessment_AssessmentId(request.getAssessmentId());

        int totalMarks = questions.stream()
                .mapToInt(MCQQuestion::getMarks)
                .sum();

        MCQExamAttempt attempt = MCQExamAttempt.builder()
                .assessment(assessment)
                .candidateId(request.getCandidateId())
                .startTime(LocalDateTime.now())
                .totalQuestions(questions.size())
                .totalMarks(totalMarks)
                .obtainedMarks(0)
                .submitted(false)
                .status(ExamStatus.IN_PROGRESS)
                .build();

        attempt = attemptRepository.save(attempt);

        return StartExamResponse.builder()
                .attemptId(attempt.getId())
                .assessmentId(assessment.getAssessmentId())
                .candidateId(request.getCandidateId())
                .startTime(attempt.getStartTime())
                .duration(assessment.getDuration())
                .totalQuestions(attempt.getTotalQuestions())
                .totalMarks(attempt.getTotalMarks())
                .message("Exam Started Successfully")
                .build();
    }

	    @Override
	    public ExamResultResponse submitExam(Integer attemptId,
	                                         SubmitExamRequest request) {
	
	        MCQExamAttempt attempt = attemptRepository.findById(attemptId)
	                .orElseThrow(() -> new RuntimeException("Attempt not found"));
	
	        if (attempt.getSubmitted()) {
	            throw new RuntimeException("Exam already submitted.");
	        }
	
	        int obtainedMarks = 0;
	
	        for (SubmitAnswerRequest answer : request.getAnswers()) {
	
	            MCQQuestion question = mcqQuestionRepository.findById(answer.getQuestionId())
	                    .orElseThrow(() -> new RuntimeException("Question not found"));
	
	            if (question.getCorrectAnswer().equalsIgnoreCase(answer.getSelectedAnswer())) {
	                obtainedMarks += question.getMarks();
	            }
	        }
	
	        attempt.setObtainedMarks(obtainedMarks);
	        attempt.setEndTime(LocalDateTime.now());
	        attempt.setSubmitted(true);
	        attempt.setStatus(ExamStatus.COMPLETED);
	
	        attemptRepository.save(attempt);
	
	        Assessment assessment = assessmentRepository.findById(
	                attempt.getAssessment().getAssessmentId())
	                .orElseThrow(() -> new RuntimeException("Assessment not found"));
	
	        double percentage =
	                ((double) obtainedMarks / attempt.getTotalMarks()) * 100;
	
	        String result =
	                obtainedMarks >= assessment.getPassMarks()
	                        ? "PASS"
	                        : "FAIL";
	
	        return ExamResultResponse.builder()
	                .attemptId(attempt.getId())
	                .totalQuestions(attempt.getTotalQuestions())
	                .totalMarks(attempt.getTotalMarks())
	                .obtainedMarks(obtainedMarks)
	                .percentage(percentage)
	                .result(result)
	                .build();
	    }

    @Override
    @Transactional(readOnly = true)
    public ExamResultResponse getResult(Integer attemptId) {

        MCQExamAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        Assessment assessment = assessmentRepository.findById(
                attempt.getAssessment().getAssessmentId())
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        double percentage =
                ((double) attempt.getObtainedMarks() / attempt.getTotalMarks()) * 100;

        String result =
                attempt.getObtainedMarks() >= assessment.getPassMarks()
                        ? "PASS"
                        : "FAIL";

        return ExamResultResponse.builder()
                .attemptId(attempt.getId())
                .totalQuestions(attempt.getTotalQuestions())
                .totalMarks(attempt.getTotalMarks())
                .obtainedMarks(attempt.getObtainedMarks())
                .percentage(percentage)
                .result(result)
                .build();
    }
    
    @Override
    public List<AssessmentResultResponse> getAssessmentResults(Integer assessmentId) {

        List<MCQExamAttempt> attempts =
                attemptRepository.findByAssessment_AssessmentId(assessmentId);

        return attempts.stream()
                .map(attempt -> {

                    double percentage =
                            ((double) attempt.getObtainedMarks()
                                    / attempt.getTotalMarks()) * 100;

                    String result =
                            attempt.getObtainedMarks() >=
                            attempt.getAssessment().getPassMarks()
                                    ? "PASS"
                                    : "FAIL";

                    return AssessmentResultResponse.builder()
                            .attemptId(attempt.getId())
                            .candidateId(attempt.getCandidateId())
                            .obtainedMarks(attempt.getObtainedMarks())
                            .totalMarks(attempt.getTotalMarks())
                            .percentage(percentage)
                            .result(result)
                            .startTime(attempt.getStartTime())
                            .endTime(attempt.getEndTime())
                            .status(attempt.getStatus().name())
                            .build();
                })
                .collect(Collectors.toList());
    }
}