package org.example.chapter1;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HelloWorldDirect {

    private static final String RESULT = "result/chapter1/HelloWorld_Direct1.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
        document.open();
        PdfContentByte canvas = writer.getDirectContentUnder();
        writer.setCompressionLevel(0); //for showing in notepad without compression

        canvas.saveState();
        canvas.beginText();
        canvas.moveText(36, 788);
        canvas.setFontAndSize(BaseFont.createFont(), 12);
        canvas.showText("hello world!");
        canvas.endText();
        canvas.restoreState();
        document.close();
    }
}
