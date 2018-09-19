package com.wintrisstech;
/**************************************************************************/
 /* Initial ver 1.5 9/18/18                                                /
 /* Copyright 2018 Vic Wintriss                                            /
 /*************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;
import java.util.ArrayList;

public class GodAndMan extends JComponent implements ActionListener, Runnable
{
    private int widthOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int heightOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private Point godLocation = new Point(heightOfScreen / 2, heightOfScreen / 2);
    private JFrame mainGameWindow = new JFrame("NewGame");//Makes window with title "NewGame"
    private ArrayList<Ellipse2D.Double> circles = new ArrayList<Ellipse2D.Double>();
    private ArrayList<Line2D.Double> lines = new ArrayList<Line2D.Double>();
    private Timer paintTicker = new Timer(50, this); //Ticks every 20 milliseconds (50 times per second); calls on actionPerformed() when it ticks.
    private int outerRingRadius = heightOfScreen / 2;
    private int universeDiameter = heightOfScreen;
    private Point manStartingPoint = new Point(universeDiameter, universeDiameter/2);
    private Man man = new Man(manStartingPoint);
    private ArrayList<Man> people = new ArrayList<Man>();
    private God god = new God(godLocation);
    private int manDirection = 135;
    private int universePopulation = 100;
    private int heavenPopulation;
    private int lostInSpacePopulation;

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

        for (int innerRingDiameter = 0; innerRingDiameter < universeDiameter + 10; innerRingDiameter += 10)
        {
            int innerRingRadius = innerRingDiameter / 2;
            circles.add(new Ellipse2D.Double(outerRingRadius - innerRingRadius, outerRingRadius - innerRingRadius, innerRingDiameter, innerRingDiameter));
        }
        for (int theta = 0; theta < 360; theta += 1) // Degrees
        {
            int y = (int) (outerRingRadius - (outerRingRadius * Math.sin(Math.toRadians(theta))));
            int x = (int) (outerRingRadius + (outerRingRadius * Math.cos(Math.toRadians(theta))));
            Line2D.Double newLine = (new Line2D.Double(x, y, outerRingRadius, outerRingRadius));
            lines.add(newLine);
        }
        for (int angle = 0; angle < 360; angle++)
        {
            int x = (int) (800 + (800 * Math.cos(Math.toRadians(angle))));
            int y = (int) (800 + (800 * Math.sin(Math.toRadians(angle))));
            people.add(new Man(new Point(x, y)));
        }
        System.out.println("GodAndMan.run...Main thread finished normally");
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, widthOfScreen, heightOfScreen);
        Point2D center = new Point2D.Float(outerRingRadius, outerRingRadius);
        float radius = outerRingRadius;
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {Color.yellow, Color.BLUE};
        RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
        for (Man man : people)
        {
            int xSquared = (int)(Math.pow((man.getManShape().x - 800), 2));
            int ySquared =  (int)(Math.pow((man.getManShape().y - 800), 2));
            int rSquared = (int)Math.pow(outerRingRadius, 2);
            if (xSquared + ySquared > rSquared)
            {
                g2.setColor(Color.red);
                g2.fill(man.getManShape());
                lostInSpacePopulation++;
            }
            if (xSquared + ySquared < rSquared)
            {
                g2.setColor(Color.WHITE);
                g2.fill(man.getManShape());
            }
            manDirection = (int)(Math.random() * 360);
            man.moveMan(manDirection);
            g2.fill(man.getManShape());
            if (xSquared + ySquared < 200)
            {
                man.getManShape().height = 100;
                man.getManShape().width = 100;
                heavenPopulation++;
            }
            if (xSquared + ySquared >200)
            {
                man.getManShape().height = 2;
                man.getManShape().width = 2;
            }
        }
        for (Ellipse2D.Double ellipse : circles)
        {
            g2.setPaint(p);
            g2.draw(ellipse);
        }
        for (Line2D.Double line : lines)
        {
            g2.setPaint(p);
            g2.draw(line);
            if (man.getManShape().getBounds2D().intersectsLine(line))
            {
                manDirection = (int) (Math.random() * 360);
            }
        }
        g2.setColor(Color.white);
        g2.setFont(new Font("Bank Gothic",Font.BOLD , 44));
        g2.drawString("Souls arriving at heaven", 1800, 500);
        g2.drawString("Souls lost in space", 1800, 700);
        g2.drawString(heavenPopulation + "", 1800, 600);
        g2.drawString(  lostInSpacePopulation + "", 1800, 800);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }
}