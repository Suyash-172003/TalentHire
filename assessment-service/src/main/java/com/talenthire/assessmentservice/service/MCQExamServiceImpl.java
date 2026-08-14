package com.talenthire.assessmentservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talenthire.assessmentservice.dto.AssessmentResultResponse;
import com.talenthire.assessmentservice.dto.ExamResultResponse;
import com.talenthire.assessmentservice.dto.StartExamRequest;
import com.talenthire.assessmentservice.dto.StartExamResponse;
import com.talenthire.assessmentservice.dto.SubmitAnswerRequest;
import com.talenthire.assessmentservice.dto.SubmitExamRequest;
import com.talenthire.assessmentservice.entity.Assessment;
import com.talenthire.assessmentservice.entity.MCQExamAttempt;
import com.talenthire.assessmentservice.entity.MCQQuestion;
import com.talenthire.assessmentservice.enums.ExamStatus;
import com.talenthire.assessmentservice.repository.AssessmentRepository;
import com.talenthire.assessmentservice.repository.MCQExamAttemptRepository;
import com.talenthire.assessmentservice.repository.MCQQuestionRepository;

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
        attemptRepository.findByAssessment_IdAndCandidateId(
                request.getAssessmentId(),
                request.getCandidateId())
                .ifPresent(existing -> {

                    if (existing.getStatus() == ExamStatus.COMPLETED) {
                        throw new RuntimeException("You have already completed this assessment.");
                    }

                    if (existing.getStatus() == ExamStatus.IN_PROGRESS) {
                        throw new RuntimeException(
                                "Exam already started. Continue with Attempt ID : "
                                        + existing.getId());
                    }

                });

        // Fetch questions
        List<MCQQuestion> questions =
                mcqQuestionRepository.findByAssessment_Id(request.getAssessmentId());

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
                .assessmentId(assessment.getId())
                .candidateId(request.getCandidateId())
                .startTime(attempt.getStartTime())
                .duration(assessment.getDuration())
                .totalQuestions(attempt.getTotalQuestions())
                .totalMarks(attempt.getTotalMarks())
                .message("Exam Started Successfully")
                .build();
    }

    @Override
    public ExamResultResponse submitExam(Long attemptId,
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
                attempt.getAssessment().getId())
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
    public ExamResultResponse getResult(Long attemptId) {

        MCQExamAttempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        Assessment assessment = assessmentRepository.findById(
                attempt.getAssessment().getId())
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
    public List<AssessmentResultResponse> getAssessmentResults(Long assessmentId) {

        List<MCQExamAttempt> attempts =
                attemptRepository.findByAssessment_Id(assessmentId);

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