package org.example.chapter1;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class HelloWorld_Mergins {

    private static final String RESULT = "result/chapter1/HelloWorld_Margins.pdf";

    public static void main(String[] args) throws FileNotFoundException, DocumentException {
        Rectangle page = new Rectangle(216f,720f);
        Document document = new Document(page,36f,72f,108f,180f);
        PdfWriter.getInstance(document, new FileOutputStream(RESULT));
        document.open();
        document.add(new Paragraph("Hello World!"));
        document.close();
    }
}
