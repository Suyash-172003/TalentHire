package com.talenthire.application.util;


import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ResumeTextExtractor
{
    public static String extractText(MultipartFile file) throws IOException
    {
    	  String fileName = file.getOriginalFilename().toLowerCase();

        if(fileName.endsWith(".pdf"))
        {
            return extractPdf(file);
        }

        if(fileName.endsWith(".docx"))
        {
            return extractDocx(file);
        }

        if(fileName.endsWith(".doc"))
        {
            return extractDoc(file);
        }

        throw new IllegalArgumentException("Unsupported file type");
    }

    private static String extractPdf(MultipartFile file) throws IOException
    {
        try(PDDocument document = Loader.loadPDF(file.getBytes()))
        {
            PDFTextStripper stripper = new PDFTextStripper();

            return stripper.getText(document);
        }
    }

    private static String extractDocx(MultipartFile file) throws IOException
    {
    	try (
    	        XWPFDocument document = new XWPFDocument(file.getInputStream());
    	        XWPFWordExtractor extractor = new XWPFWordExtractor(document)
    	    )
    	    {
    	        return extractor.getText();
    	    }
    }

    private static String extractDoc(MultipartFile file) throws IOException
    {
    	try (
    	        HWPFDocument document = new HWPFDocument(file.getInputStream());
    	        WordExtractor extractor = new WordExtractor(document)
    	    )
    	    {
    	        return extractor.getText();
    	    }
    }
}