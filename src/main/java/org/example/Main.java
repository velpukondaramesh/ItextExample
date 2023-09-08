package org.example;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static final String SRC = "D:\\PDF\\ItextInActionTutorials\\result\\issue.pdf";
    public static final String DEST = "D:\\PDF\\ItextInActionTutorials\\result\\issue_1.pdf";

    public static void main(String[] args) throws IOException {
        float percentage = 0.7f;
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(DEST));
        pdfDoc.setDefaultPageSize(new PageSize(new Rectangle(0, 0, PageSize.A5.getWidth(), PageSize.A5.getHeight())));

        PdfDocument srcDoc = new PdfDocument(new PdfReader(SRC));
        for (int i = 1; i <= srcDoc.getNumberOfPages(); i++) {
            //PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage());
            PdfPage pdfPage = pdfDoc.addNewPage();
            PdfPage page = srcDoc.getPage(i);
            Rectangle orig = page.getPageSizeWithRotation();
            /*PdfFormXObject xObject = page.copyAsFormXObject(pdfDoc);
            canvas.saveState()
                    .concatMatrix(0.5f, 0, 0, 0.5f, 10, 10)
                    .addXObjectAt(xObject, 0, 0)
                    .restoreState();*/
            /*AffineTransform transformationMatrix = AffineTransform.getScaleInstance(
                    pdfPage.getPageSize().getWidth() / orig.getWidth() / 2,
                    pdfPage.getPageSize().getHeight() / orig.getHeight() / 2);*/
            float offsetX = (orig.getWidth() * (1 - percentage)) / 2;
            float offsetY = (orig.getHeight() * (1 - percentage)) / 2;
            PdfCanvas canvas = new PdfCanvas(pdfPage);
            //canvas.concatMatrix(transformationMatrix);
            PdfFormXObject pageCopy = page.copyAsFormXObject(pdfDoc);
            canvas.saveState()
                    .concatMatrix(percentage, 0, 0, percentage, 0,0)
                    .addXObjectAt(pageCopy, 0, 0)
                    .restoreState();
        }
        pdfDoc.close();
        srcDoc.close();
    }
}