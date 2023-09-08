package org.example.chapter1;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class CropPages {

    private static final String SOURCE = "result/chapter1/HelloWorld_Column.pdf";
    private static final String DEST = "result/chapter1/HelloWorld_Column_2.pdf";

    public static void main(String[] args) throws IOException, DocumentException {

        PdfReader reader = new PdfReader(SOURCE);
        int numberOfPages = reader.getNumberOfPages();
        PdfDictionary pageDict;
        PdfRectangle rect = new PdfRectangle(0, 0, PageSize.A5.getWidth(), PageSize.A5.getHeight());
        for (int i = 1; i <= numberOfPages; i++) {

            pageDict = reader.getPageN(i);
            pageDict.put(PdfName.MEDIABOX, rect);
        }
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(DEST));
        stamper.close();
    }
}
