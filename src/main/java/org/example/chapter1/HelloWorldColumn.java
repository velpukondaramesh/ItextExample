package org.example.chapter1;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class HelloWorldColumn {

    private static final String RESULT = "result/chapter1/HelloWorld_Column.pdf";

    public static void main(String[] args) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
        document.open();
        PdfContentByte canvas = writer.getDirectContentUnder();
        writer.setCompressionLevel(0); //for showing in notepad without compression

        /*canvas.saveState();
        canvas.beginText();
        canvas.moveText(36, 788);
        canvas.setFontAndSize(BaseFont.createFont(), 12);
        canvas.showText("hello world!");
        canvas.endText();
        canvas.restoreState();*/

        Phrase phrase = new Phrase("Hello World");
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,phrase,36,788,0);

        document.close();
    }
}
