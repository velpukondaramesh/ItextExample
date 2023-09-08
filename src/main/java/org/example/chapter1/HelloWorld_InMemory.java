package org.example.chapter1;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;

public class HelloWorld_InMemory {

    private static final String RESULT = "result/chapter1/HelloWorld_InMemory.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        Document document = new Document();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();//In Memory creation
        PdfWriter.getInstance(document, byteArray);
        document.open();
        document.setPageSize(PageSize.A5);
        document.setMargins(36, 72, 108, 180);
        document.add(new Paragraph("Hello World!"));
        document.close();

        FileOutputStream fileOutputStream = new FileOutputStream(RESULT);
        fileOutputStream.write(byteArray.toByteArray());
        fileOutputStream.close();
    }
}
