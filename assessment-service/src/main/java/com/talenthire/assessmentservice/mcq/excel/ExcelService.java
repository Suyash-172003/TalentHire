package com.talenthire.assessmentservice.mcq.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.talenthire.assessmentservice.assessment.entity.Assessment;
import com.talenthire.assessmentservice.assessment.repository.AssessmentRepository;
import com.talenthire.assessmentservice.mcq.entity.MCQQuestion;
import com.talenthire.assessmentservice.mcq.repository.MCQQuestionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExcelService {

    private final AssessmentRepository assessmentRepository;
    private final MCQQuestionRepository mcqQuestionRepository;

    private final DataFormatter formatter = new DataFormatter();

    public void uploadQuestions(Long assessmentId, MultipartFile file) {

        // Validate Excel file
        if (!ExcelHelper.hasExcelFormat(file)) {
            throw new RuntimeException("Please upload a valid Excel (.xlsx) file.");
        }

        try (Workbook workbook = ExcelHelper.getWorkbook(file)) {

            // Read first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Find Assessment
            Assessment assessment = assessmentRepository.findById(assessmentId)
                    .orElseThrow(() -> new RuntimeException("Assessment not found with ID : " + assessmentId));

            List<MCQQuestion> questions = new ArrayList<>();

            Iterator<Row> rows = sheet.iterator();

            // Skip Header Row
            if (rows.hasNext()) {
                rows.next();
            }

            while (rows.hasNext()) {

                Row currentRow = rows.next();

                // Skip empty rows
                if (currentRow == null || getCellValue(currentRow.getCell(0)).isBlank()) {
                    continue;
                }

                MCQQuestion question = MCQQuestion.builder()
                        .assessment(assessment)
                        .questionText(getCellValue(currentRow.getCell(0)))
                        .optionA(getCellValue(currentRow.getCell(1)))
                        .optionB(getCellValue(currentRow.getCell(2)))
                        .optionC(getCellValue(currentRow.getCell(3)))
                        .optionD(getCellValue(currentRow.getCell(4)))
                        .correctAnswer(getCellValue(currentRow.getCell(5)))
                        .marks(Integer.parseInt(getCellValue(currentRow.getCell(6))))
                        .difficulty(getCellValue(currentRow.getCell(7)))
                        .questionOrder(currentRow.getRowNum())
                        .build();

                questions.add(question);
            }

            // Save all questions together
            mcqQuestionRepository.saveAll(questions);

        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file.", e);
        }
    }

    // Read cell value safely
    private String getCellValue(Cell cell) {

        if (cell == null) {
            return "";
        }

        return formatter.formatCellValue(cell).trim();
    }

}