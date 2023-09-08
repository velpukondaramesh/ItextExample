package org.example.graphic;

import lombok.AllArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

@AllArgsConstructor
public class DrawingCanvas extends JComponent {

    private int width;
    private int height;
    private Cloud cloud;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        graphics2D.addRenderingHints(rh);

        /*Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, width, height);
        graphics2D.setColor(new Color(100, 149, 237));
        graphics2D.fill(rect);

        Ellipse2D.Double e = new Ellipse2D.Double(200,75,100,100);
        graphics2D.setColor(Color.GREEN);
        graphics2D.fill(e);

        Line2D.Double line = new Line2D.Double(100,250,300,75);
        graphics2D.setColor(Color.BLACK);
        graphics2D.draw(line);*/

        //cloud.drawCloud(graphics2D);

       /* Path2D.Double p = new Path2D.Double();
        p.moveTo(100,300);
        p.lineTo(150,200);
        p.lineTo(200,300);
        p.closePath();
        graphics2D.draw(p);*/

        /*Path2D.Double p = new Path2D.Double();
        p.moveTo(250,400);
        p.curveTo(350,300,500,300,600,400);
        graphics2D.draw(p);*/


        //https://www.youtube.com/watch?v=zCiMlbu1-aQ&t=210s
        /*Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, 100, 100);
        Rectangle2D.Double rect1 = new Rectangle2D.Double(150, 100, 100, 100);
        graphics2D.setColor(Color.BLUE);
        AffineTransform reset = graphics2D.getTransform(); // it hold the position of before transformation
        graphics2D.translate(150,100);
        graphics2D.fill(rect);
        graphics2D.fill(rect1);
        graphics2D.translate(300,100);
        graphics2D.fill(rect);

        //graphics2D.translate(-450,-200);
        graphics2D.setTransform(reset); //we can apply this to reset transformation
        graphics2D.fill(rect);*/

        Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, 100, 100);
        graphics2D.setColor(Color.BLUE);
        graphics2D.fill(rect);
        Rectangle2D.Double rect1 = new Rectangle2D.Double(100, 100, 100, 100);
        //{ "scaleX", "shearY", "shearX", "scaleY", "translateX", "translateY" }
        AffineTransform transform = new AffineTransform(0.5,0,0,0.5,0,0);
        graphics2D.setTransform(transform);
        graphics2D.setColor(Color.BLUE);
        graphics2D.fill(rect);

    }
}
