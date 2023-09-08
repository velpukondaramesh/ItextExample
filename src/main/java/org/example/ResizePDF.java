package org.example;

import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;

import java.util.Locale;

public class ResizePDF {

    public static final String RESIZE_CONTENT = "D:\\PDF\\ItextInActionTutorials\\result\\issue_1.pdf";
    public static final String SRC = "D:\\PDF\\ItextInActionTutorials\\result\\issue.pdf";

    public static final boolean convertToLandscape = false;
    public static final int size = 4;

    public static void main(String[] args) throws Exception {
        //new ResizePDF().reduceContentPdf(SRC, RESIZE_CONTENT);
        PdfReader reader = new PdfReader(SRC);
        PdfWriter pdfWriter = new PdfWriter(RESIZE_CONTENT);
        PdfDocument pdfDoc = new PdfDocument(reader, pdfWriter);
        //Rectangle pageSize = PageSize.A4.rotate(); // Landscape become to portrait / Portrait become to landscape
        //Rectangle pageSize = PageSize.A4; // it is maintain original orientation
        scale(pdfDoc);
        pdfDoc.close();
    }

    protected void manipulatePdf(String src, String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));

        for (int p = 1; p <= pdfDoc.getNumberOfPages(); p++) {
            PdfPage page = pdfDoc.getPage(p);
            Rectangle media = page.getCropBox();
            if (media == null) {
                media = page.getMediaBox();
            }

            // Shrink the page to 50%
            //Rectangle crop = new Rectangle(0, 0, media.getWidth() / 2, media.getHeight() / 2);
            Rectangle crop = new Rectangle(0, 0, PageSize.A4.getWidth(), PageSize.A4.getHeight());
            page.setMediaBox(crop);
            page.setCropBox(crop);

            // The content, placed on a content stream before, will be rendered before the other content
            // and, therefore, could be understood as a background (bottom "layer")
            new PdfCanvas(page.newContentStreamBefore(),
                    page.getResources(), pdfDoc).writeLiteral("\nq 0.5 0 0 0.5 0 0 cm\nq\n");

            // The content, placed on a content stream after, will be rendered after the other content
            // and, therefore, could be understood as a foreground (top "layer")
            new PdfCanvas(page.newContentStreamAfter(),
                    page.getResources(), pdfDoc).writeLiteral("\nQ\nQ\n");
        }

        pdfDoc.close();
    }

    protected void reduceContentPdf(String src, String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));

        // Please note that we don't change the page size in this example, but only shrink the content (in this case to 80%)
        // and the content is shrunk to center of the page, leaving bigger margins to the top, bottom, left and right
        float percentage = 0.8f;
        for (int p = 1; p <= pdfDoc.getNumberOfPages(); p++) {
            PdfPage pdfPage = pdfDoc.getPage(p);
            Rectangle pageSize = pdfPage.getPageSize();

            // Applying the scaling in both X, Y direction to preserve the aspect ratio.
            float offsetX = (pageSize.getWidth() * (1 - percentage)) / 2;
            float offsetY = (pageSize.getHeight() * (1 - percentage)) / 2;

            // The content, placed on a content stream before, will be rendered before the other content
            // and, therefore, could be understood as a background (bottom "layer")
            new PdfCanvas(pdfPage.newContentStreamBefore(), pdfPage.getResources(), pdfDoc)
                    .writeLiteral(String.format(Locale.ENGLISH, "\nq %s 0 0 %s %s %s cm\nq\n",
                            percentage, percentage, offsetX, offsetY));

            // Shrink the page to 50%
            //Rectangle crop = new Rectangle(0, 0, media.getWidth() / 2, media.getHeight() / 2);
            Rectangle crop = new Rectangle(0, 0, PageSize.A4.getWidth(), PageSize.A4.getHeight());
            pdfPage.setMediaBox(crop);
            pdfPage.setCropBox(crop);

            // The content, placed on a content stream before, will be rendered before the other content
            // and, therefore, could be understood as a background (bottom "layer")
            new PdfCanvas(pdfPage.newContentStreamBefore(),
                    pdfPage.getResources(), pdfDoc).writeLiteral("\nq 0.5 0 0 0.5 0 0 cm\nq\n");

            // The content, placed on a content stream after, will be rendered after the other content
            // and, therefore, could be understood as a foreground (top "layer")
            new PdfCanvas(pdfPage.newContentStreamAfter(), pdfPage.getResources(), pdfDoc)
                    .writeLiteral("\nQ\nQ\n");
        }

        pdfDoc.close();
        //manipulatePdf(RESIZE_CONTENT, RESIZE_PAGE);
    }

    static void scale(PdfDocument pdfDocument) {
        int n = pdfDocument.getNumberOfPages();
        Rectangle pageBodySize;
        for (int i = 1; i <= n; i++) {
            PdfPage page = pdfDocument.getPage(i);
            //for portrait page to landscape
            System.out.println("W:" + page.getPageSizeWithRotation().getWidth());
            System.out.println("H:" + page.getPageSizeWithRotation().getHeight());
            Rectangle pageSize = getPageSize(size, !convertToLandscape);
            if (convertToLandscape) {
                if (page.getPageSizeWithRotation().getWidth() <= page.getPageSizeWithRotation().getHeight()) {
                    System.out.println("convert portrait page to landscape");
                    pageSize = getPageSize(size, false);
                }
            } else {
                if (page.getPageSizeWithRotation().getWidth() >= page.getPageSizeWithRotation().getHeight()) {
                    System.out.println("convert landscape page to portrait");
                    pageSize = getPageSize(size, true);
                }
            }
            pageBodySize = pageSize.clone().applyMargins(36, 36, 36, 36, false);
            MarginFinder marginFinder = new MarginFinder();
            PdfCanvasProcessor pdfCanvasProcessor = new PdfCanvasProcessor(marginFinder);
            pdfCanvasProcessor.processPageContent(page);
            Rectangle boundingBox = marginFinder.getBoundingBox();
            if (boundingBox == null || boundingBox.getWidth() == 0 || boundingBox.getHeight() == 0) {
                System.err.printf("Cannot scale page %d contents with bounding box %s\n", i, boundingBox);
                continue;
            } else {
                // Scale and move content into A4 with margin
                double scale = 0, xDiff = 0, yDiff = 0;
                double xScale = pageBodySize.getWidth() / boundingBox.getWidth();
                double yScale = pageBodySize.getHeight() / boundingBox.getHeight();
                if (xScale < yScale) {
                    yDiff = boundingBox.getHeight() * (yScale / xScale - 1) / 2;
                    scale = xScale;
                } else {
                    xDiff = boundingBox.getWidth() * (xScale / yScale - 1) / 2;
                    scale = yScale;
                }

                AffineTransform transform = AffineTransform.getTranslateInstance(pageBodySize.getLeft() + xDiff, pageBodySize.getBottom() + yDiff);
                transform.scale(scale, scale);
                transform.translate(-boundingBox.getLeft(), -boundingBox.getBottom());
                new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDocument)
                        .concatMatrix(transform);
            }
            page.setMediaBox(pageSize);
            page.setCropBox(pageSize);
        }
    }

    private static Rectangle getPageSize(int size, boolean isPortrait) {
        switch (size) {
            case 0:
                if (isPortrait) {
                    return PageSize.A0;
                }
                return PageSize.A0.rotate();
            case 1:
                if (isPortrait) {
                    return PageSize.A1;
                }
                return PageSize.A1.rotate();
            case 2:
                if (isPortrait) {
                    return PageSize.A2;
                }
                return PageSize.A2.rotate();
            case 3:
                if (isPortrait) {
                    return PageSize.A3;
                }
                return PageSize.A3.rotate();
            case 4:
                if (isPortrait) {
                    return PageSize.A4;
                }
                return PageSize.A4.rotate();
            case 5:
                if (isPortrait) {
                    return PageSize.A5;
                }
                return PageSize.A5.rotate();
            case 6:
                if (isPortrait) {
                    return PageSize.A6;
                }
                return PageSize.A6.rotate();
            case 7:
                if (isPortrait) {
                    return PageSize.A7;
                }
                return PageSize.A7.rotate();
            case 8:
                if (isPortrait) {
                    return PageSize.A8;
                }
                return PageSize.A8.rotate();
            case 9:
                if (isPortrait) {
                    return PageSize.A9;
                }
                return PageSize.A9.rotate();
            case 10:
                if (isPortrait) {
                    return PageSize.A10;
                }
                return PageSize.A10.rotate();
            case 11:
                if (isPortrait) {
                    return PageSize.B0;
                }
                return PageSize.B0.rotate();
            case 12:
                if (isPortrait) {
                    return PageSize.B1;
                }
                return PageSize.B1.rotate();
            case 13:
                if (isPortrait) {
                    return PageSize.B2;
                }
                return PageSize.B2.rotate();
            case 14:
                if (isPortrait) {
                    return PageSize.B3;
                }
                return PageSize.B3.rotate();
            case 15:
                if (isPortrait) {
                    return PageSize.B4;
                }
                return PageSize.B4.rotate();
            case 16:
                if (isPortrait) {
                    return PageSize.B5;
                }
                return PageSize.B5.rotate();
            case 17:
                if (isPortrait) {
                    return PageSize.B6;
                }
                return PageSize.B6.rotate();
            case 18:
                if (isPortrait) {
                    return PageSize.B7;
                }
                return PageSize.B7.rotate();
            case 19:
                if (isPortrait) {
                    return PageSize.B8;
                }
                return PageSize.B8.rotate();
            case 20:
                if (isPortrait) {
                    return PageSize.B9;
                }
                return PageSize.B9.rotate();
            case 21:
                if (isPortrait) {
                    return PageSize.B10;
                }
                return PageSize.B10.rotate();
            case 22:
                if (isPortrait) {
                    return PageSize.DEFAULT;
                }
                return PageSize.DEFAULT.rotate();
            case 23:
                if (isPortrait) {
                    return PageSize.EXECUTIVE;
                }
                return PageSize.EXECUTIVE.rotate();
            case 24:
                if (isPortrait) {
                    return PageSize.LEDGER;
                }
                return PageSize.LEDGER.rotate();
            case 25:
                if (isPortrait) {
                    return PageSize.LEGAL;
                }
                return PageSize.LEGAL.rotate();
            case 26:
                if (isPortrait) {
                    return PageSize.LETTER;
                }
                return PageSize.LETTER.rotate();
            case 27:
                if (isPortrait) {
                    return PageSize.TABLOID;
                }
                return PageSize.TABLOID.rotate();
            default:
                return PageSize.A4;
        }
    }
}
