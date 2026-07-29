package com.talenthire.assessmentservice.service;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

public class ExcelHelper {

    private ExcelHelper() {
        // Prevent object creation
    }

    // Excel MIME Type
    public static final String TYPE =
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    // Check whether uploaded file is Excel
    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    // Create Workbook from uploaded file
    public static Workbook getWorkbook(MultipartFile file) throws IOException {
        return WorkbookFactory.create(file.getInputStream());
    }
}