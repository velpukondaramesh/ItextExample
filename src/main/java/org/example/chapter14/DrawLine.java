package org.example.chapter14;

import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DrawLine {
    public static final String DEST = "D:\\PDF\\ItextInActionTutorials\\result\\example\\draw.pdf";

    public static void main(String[] args) throws IOException {
        //drawLine();
        changeTheLocationOfDrawLine();
    }

    private static void changeTheLocationOfDrawLine() throws IOException {
        PdfDocument document = new PdfDocument(new PdfReader(DEST));
        PdfPage page = document.getPage(1);
        int contentStreamCount = page.getContentStreamCount();
        System.out.println(contentStreamCount);
        PdfStream contentStream = page.getContentStream(0);
        System.out.println(contentStream.getLength());

    }

    private static void drawLine() throws FileNotFoundException {
        PdfDocument document = new PdfDocument(new PdfWriter(DEST));
        PdfCanvas canvas = new PdfCanvas(document.addNewPage());
        //50, 720, 80, 20
        canvas.saveState();
        canvas.setStrokeColor(new DeviceGray(0.2f));
        canvas.setFillColor(new DeviceGray(0.9f));
        canvas.moveTo(50, 100);
        canvas.lineTo(50+80, 100);
        canvas.closePathEoFillStroke();
        canvas.restoreState();
        document.close();
    }
}
