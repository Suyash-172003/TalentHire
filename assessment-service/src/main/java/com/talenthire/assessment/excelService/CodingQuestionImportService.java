package com.talenthire.assessment.excelService;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.talenthire.assessment.entity.Assessment;
import com.talenthire.assessment.entity.CodingQuestion;
import com.talenthire.assessment.entity.CodingTestCase;
import com.talenthire.assessment.repository.AssessmentRepository;
import com.talenthire.assessment.repository.CodingQuestionRepository;
import com.talenthire.assessment.repository.CodingTestCaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodingQuestionImportService {
	
	  
	    private final CodingQuestionRepository codingQuestionRepository;
	    private final  AssessmentRepository assessmentRepository;

	    
	    private final CodingTestCaseRepository codingTestCaseRepository;

	    public void importQuestions(MultipartFile file,
	                                Integer assessmentId)
	            throws IOException {
	    	
	    	Assessment assessment = assessmentRepository.findById(assessmentId)
	                .orElseThrow(() -> new RuntimeException("Assessment not found"));

	        Workbook workbook =
	                WorkbookFactory.create(file.getInputStream());

	        Sheet sheet = workbook.getSheetAt(0);

	        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

	            Row row = sheet.getRow(i);

	            if (row == null)
	                continue;

	            CodingQuestion question = new CodingQuestion();

	            question.setAssessment(assessment);
	            question.setTitle(getCellValue(row.getCell(0)));
	            question.setProblemStatement(getCellValue(row.getCell(1)));
	            question.setMarks(Integer.parseInt(getCellValue(row.getCell(2))));
	            question.setQuestionOrder(Integer.parseInt(getCellValue(row.getCell(3))));

	            CodingQuestion savedQuestion =
	                    codingQuestionRepository.save(question);

	           
	            saveTestCase(
	                    savedQuestion,
	                    getCellValue(row.getCell(4)),
	                    getCellValue(row.getCell(5)),
	                    true);

	           
	            saveTestCase(
	                    savedQuestion,
	                    getCellValue(row.getCell(6)),
	                    getCellValue(row.getCell(7)),
	                    false);

	          
	            saveTestCase(
	                    savedQuestion,
	                    getCellValue(row.getCell(8)),
	                    getCellValue(row.getCell(9)),
	                    false);
	        }
	        
	        List<CodingQuestion> questions =
	                codingQuestionRepository.findByAssessmentAssessmentId(assessmentId);

	        int total = 0;

	        for (CodingQuestion question : questions) {
	            total += question.getMarks();
	        }
	        
	        System.out.print(total);

	        assessment.setCodingTotalMarks(total);
	        
	        assessmentRepository.save(assessment);
	        
	     

	        workbook.close();

}
	    
private void saveTestCase(CodingQuestion question,
                String input,
                String output,
                boolean sample) {

if (input == null || input.isBlank())
return;

CodingTestCase testCase = new CodingTestCase();

testCase.setCodingQuestion(question);
testCase.setInput(input);
testCase.setExpectedOutput(output);
testCase.setSample(sample);

codingTestCaseRepository.save(testCase);
}
	    
	    private String getCellValue(Cell cell) {

	        if (cell == null)
	            return "";

	        switch (cell.getCellType()) {

	            case STRING:
	                return cell.getStringCellValue();

	            case NUMERIC:
	                return String.valueOf((int) cell.getNumericCellValue());

	            case BOOLEAN:
	                return String.valueOf(cell.getBooleanCellValue());

	            default:
	                return "";
	        }
	    }

		
}
