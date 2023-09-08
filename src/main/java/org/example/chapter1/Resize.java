package org.example.chapter1;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class Resize {

    private static final String SOURCE = "result/chapter1/in.pdf";
    private static final String DEST = "result/chapter1/out.pdf";

    public static void main(String[] args) throws DocumentException, IOException {
        resize();
    }

    private static void resize() throws IOException, DocumentException {
        float width = 4.1f * 72;
        float height = 5.8f * 72;
        float tolerance = 1f;

        PdfReader reader = new PdfReader(SOURCE);

        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            Rectangle cropBox = reader.getCropBox(i);
            float widthToAdd = width - cropBox.getWidth();
            float heightToAdd = height - cropBox.getHeight();
            if (Math.abs(widthToAdd) > tolerance || Math.abs(heightToAdd) > tolerance) {
                float[] newBoxValues = new float[]{
                        cropBox.getLeft() - widthToAdd / 2,
                        cropBox.getBottom() - heightToAdd / 2,
                        cropBox.getRight() + widthToAdd / 2,
                        cropBox.getTop() + heightToAdd / 2
                };
                PdfArray newBox = new PdfArray(newBoxValues);

                PdfDictionary pageDict = reader.getPageN(i);
                pageDict.put(PdfName.CROPBOX, newBox);
                pageDict.put(PdfName.MEDIABOX, newBox);
            }
        }

        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(DEST));
        stamper.close();
    }
}
