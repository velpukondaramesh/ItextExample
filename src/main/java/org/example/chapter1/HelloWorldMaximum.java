package org.example.chapter1;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class HelloWorldMaximum {

    private static final String RESULT = "result/chapter1/HelloWorld_Maximum.pdf";

    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        Rectangle page = new Rectangle(14400f, 14400f);
        Document document = new Document(page);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
        writer.setUserunit(75000f);
        document.open();
        document.add(new Paragraph("Hello World!"));
        document.close();
    }
}
