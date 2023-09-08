package org.example.graphic;

import lombok.AllArgsConstructor;

import java.awt.*;
import java.awt.geom.Ellipse2D;

@AllArgsConstructor
public class Cloud {

    public void drawCloud(Graphics2D g){
        Ellipse2D.Double e = new Ellipse2D.Double(200,75,100,100);
        Ellipse2D.Double e1 = new Ellipse2D.Double(235,55,175,145);
        Ellipse2D.Double e2 = new Ellipse2D.Double(350,90,90,90);
        Ellipse2D.Double e3 = new Ellipse2D.Double(380,80,30,30);
        g.setColor(Color.BLUE);
        g.fill(e);
        g.fill(e1);
        g.fill(e2);
        g.fill(e3);
    }
}
