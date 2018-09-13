package com.wintrisstech;
/**************************************************************************
 * Initial ver 1.0 9/11/18
 * Copyright 2018 Vic Wintriss
 **************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GodAndMan extends JComponent implements ActionListener, Runnable
{
    private int widthOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int heightOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private JFrame mainGameWindow = new JFrame("NewGame");//Makes window with title "NewGame"
    private Rectangle2D.Double universe = new Rectangle2D.Double(0, 0, heightOfScreen, heightOfScreen);
    private Ellipse2D.Double limit = new Ellipse2D.Double(1, 1, 1, 1);
    private ArrayList<Ellipse2D.Double> circles = new ArrayList<Ellipse2D.Double>();
    private ArrayList<Line2D.Double> lines = new ArrayList<Line2D.Double>();
    private Timer paintTicker = new Timer(50, this); //Ticks every 20 milliseconds (50 times per second); calls on actionPerformed() when it ticks.
    private int outerRingRadius = heightOfScreen / 2;
    private int outerRingDiameter = heightOfScreen;
    private int innerRingRadius;
    private int innerRingDiameter;
    private int theta;
    float[] dist = { 0f, 1f};
    Color[] colors = { new Color(0, 0, 0, 0), new Color(0, 0, 0, 255)};
    Point2D.Double center = new Point2D.Double(outerRingRadius, outerRingRadius);

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new GodAndMan());
    }

    public void run()
    {
        System.out.println("Running");
        mainGameWindow.setTitle("God And Man");
        mainGameWindow.setSize(widthOfScreen, heightOfScreen);
        mainGameWindow.add(this);//Adds the paint method to the JFrame
        mainGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGameWindow.setVisible(true);
        paintTicker.start();
        for (innerRingDiameter = 0; innerRingDiameter < outerRingDiameter + 10; innerRingDiameter += 10)
        {
            innerRingRadius = innerRingDiameter / 2;
            circles.add(new Ellipse2D.Double(outerRingRadius - innerRingRadius, outerRingRadius - innerRingRadius, innerRingDiameter, innerRingDiameter));
        }
        int i = 0;
        for (theta = 0; theta < 360; theta += 1) // Degrees
        {
            int y = (int) (outerRingRadius - (outerRingRadius * Math.sin(Math.toRadians(theta))));
            int x = (int) (outerRingRadius + (outerRingRadius * Math.cos(Math.toRadians(theta))));
            Line2D.Double newLine = (new Line2D.Double(x, y, outerRingRadius, outerRingRadius));
            lines.add(newLine);
        }
        paintTicker.stop();
        System.out.println("GodAndMan.run finished normally");
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        drawVisibilityCircle(g2);
        for (Ellipse2D.Double ellipse : circles)
        {
            g2.setColor(Color.white);
            g2.draw(ellipse);
        }
        for (Line2D.Double line : lines)
        {
            g2.draw(line);
        }
    }
    private static void drawVisibilityCircle(Graphics2D g2d){
        Point center = new Point(800, 800);
        float radius = 800;
        float[] dist = { 0f, 1f};
        Color[] colors = { new Color(255, 255, 0, 0), new Color(0, 0, 0, 255)};
        //workaround to prevent background color from showing
        drawBackGroundCircle(g2d, radius, Color.WHITE, center);
        drawGradientCircle(g2d, radius, dist, colors, center);
    }

    private static void drawBackGroundCircle(Graphics2D g2d, float radius, Color color, Point2D center){

        g2d.setColor(color);
        radius -= 1;//make radius a bit smaller to prevent fuzzy edge
        g2d.fill(new Ellipse2D.Double(center.getX() - radius, center.getY()
                - radius, radius * 2, radius * 2));
    }

    private static void drawGradientCircle(Graphics2D g2d, float radius, float[] dist, Color[] colors, Point2D center){
        RadialGradientPaint rgp = new RadialGradientPaint(center, radius, dist, colors);
        g2d.setPaint(rgp);
        g2d.fill(new Ellipse2D.Double(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2));
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }
}