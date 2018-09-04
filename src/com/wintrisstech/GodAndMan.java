package com.wintrisstech;
/**************************************************************************
 * Initial ver 0.3 9/3/18
 * Copyright 2018 Vic Wintriss
 **************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
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
    private Timer paintTicker = new Timer(500, this); //Ticks every 20 milliseconds (50 times per second); calls on actionPerformed() when it ticks.
    private AffineTransform universeOffset = new AffineTransform();
    private int q = 0;
    private int outerRingRadius = heightOfScreen/2;
    private int outerRingDiameter = heightOfScreen;
    private int xOffset = heightOfScreen/2;
    private int yOffset = heightOfScreen/2;
    private int innerRingRadius;
    private int innerRingDiameter;

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
        for ( innerRingDiameter = 0; innerRingDiameter < outerRingDiameter; innerRingDiameter += 10)
        {
            innerRingRadius = innerRingDiameter/2;
            circles.add(new Ellipse2D.Double(outerRingRadius - innerRingRadius, outerRingRadius - innerRingRadius, innerRingDiameter, innerRingDiameter));
        }
        for (int i = 0; i < 360; i++)
        {
            int x1 = (int) ((Math.cos(Math.toRadians(i)) * outerRingRadius)) + xOffset;
            int y1 = (int) -((Math.sin(Math.toRadians(i)) * outerRingRadius)) + yOffset;
            lines.add(new Line2D.Double(x1 + outerRingRadius, y1 + outerRingRadius, outerRingRadius, outerRingRadius));
        }
        for (Line2D.Double line : lines)
        {
            System.out.println("line[" + lines.indexOf(line) + "]  x1/y1 = " + line.getX1() + "/" + line.getY1() + " x2/y2 = " + line.getX2() + "/" + line.getY2());
        }
        System.out.println("GodAndMan.run terminated");
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.pink);
        g2.fill(universe);
        g2.setColor(Color.black);
        for (Ellipse2D.Double ellipse : circles)
        {
            g2.draw(ellipse);
        }
        q+=10;
        for (Line2D.Double line : lines)
        {
            g2.draw(lines.get(q));
            g2.setFont(new Font("Bank Gothic", Font.BOLD, 99));
            g2.drawString("Angle = " + q, 1500, 100 );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("tick, " + q + " degrees");
        repaint();
    }
}