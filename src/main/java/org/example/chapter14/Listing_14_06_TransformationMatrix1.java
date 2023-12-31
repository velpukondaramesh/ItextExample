/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2023 Apryse Group NV
    Authors: Apryse Software.

    For more information, please contact iText Software at this address:
    sales@itextpdf.com
 */
package org.example.chapter14;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;

import java.io.File;
import java.io.IOException;

public class Listing_14_06_TransformationMatrix1 {
    public static final String DEST
            = "D:\\PDF\\ItextInActionTutorials\\result\\example\\Listing_14_06_TransformationMatrix1.pdf";
    public static final String RESOURCE
            = "D:\\PDF\\ItextInActionTutorials\\result\\example\\logo.pdf";

    public static void main(String args[]) throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();

        new Listing_14_06_TransformationMatrix1().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        pdfDoc.setDefaultPageSize(new PageSize(new Rectangle(-595, -842, 595 * 2, 842 * 2)));

        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
        canvas.moveTo(-595, 0)
                .lineTo(595, 0)
                .moveTo(0, -842)
                .lineTo(0, 842)
                .stroke();
        // Read the PDF containing the logo
        PdfDocument srcDoc = new PdfDocument(new PdfReader(RESOURCE));
        PdfPage curPage = srcDoc.getPage(1);
        PdfFormXObject xObject = curPage.copyAsFormXObject(pdfDoc);
        // add it at different positions using different transformations
        //{ "scaleX", "shearY", "shearX", "scaleY", "translateX", "translateY" }
        canvas.saveState()
                .addXObjectAt(xObject, 0, 0)
                .concatMatrix(1f, 0, 0, 1f, -595, -800)
                .addXObjectAt(xObject, 0, 0)
                /*.addXObjectAt(xObject, 0, 0)
                .concatMatrix(1, 0, 0, 1, 595, 595)
                .addXObjectAt(xObject, 0, 0)*/
                .restoreState();

        /*canvas.saveState()
                .concatMatrix(1, 0, 0.4f, 1, -750, -650)
                .addXObjectAt(xObject, 0, 0)
                .restoreState();

        canvas.saveState()
                .concatMatrix(0, -1, -1, 0, 650, 0)
                .addXObjectAt(xObject, 0, 0)
                .concatMatrix(0.2f, 0, 0, 0.5f, 0, 300)
                .addXObjectAt(xObject, 0, 0)
                .restoreState();*/

        pdfDoc.close();
        srcDoc.close();
    }
}
