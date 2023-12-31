/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
    Authors: Apryse Software.

    For more information, please contact iText Software at this address:
    sales@itextpdf.com
 */
package org.example.chapter14;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class Listing_14_01_GetContentStream {
    public static final String DEST
            = "D:/PDF/ItextInActionTutorials/result/example/Listing_14_01_GetContentStream1.txt";
    public static final String RESULT2
            = "D:/PDF/ItextInActionTutorials/result/example/Listing_14_01_GetContentStream2.txt";
    public static final String HELLO_WORLD
            = "D:/PDF/ItextInActionTutorials/result/example/cmp_Listing_01_01_HelloWorld.pdf";
    public static final String HERO
            = "D:/PDF/ItextInActionTutorials/result/example/cmp_Listing_05_15_Hero1.pdf";

    public static void main(String args[]) throws IOException, SQLException {
        new Listing_14_01_GetContentStream().manipulatePdf(DEST);
    }

    protected void manipulatePdf(String dest) throws IOException, SQLException {
        new File(dest).getParentFile().mkdirs();
        readContent(HELLO_WORLD, dest);
        readContent(HERO, RESULT2);
    }

    public void readContent(String src, String result) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src));
        FileOutputStream out = new FileOutputStream(result);
        out.write(pdfDoc.getFirstPage().getContentBytes());
        out.flush();
        out.close();
        pdfDoc.close();
    }
}
