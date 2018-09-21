package com.wintrisstech;
/**************************************************************************/
 /* Initial ver 1.7 9/20/18                                                /
 /* Copyright 2018 Vic Wintriss                                            /
 /*************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GodAndMan extends JComponent implements ActionListener, Runnable
{
    private int widthOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int heightOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private Point godLocation = new Point(heightOfScreen / 2, heightOfScreen / 2);
    private God god = new God(godLocation);
    private int manDirection = 135;
    private Point soulPoint;
    private JFrame mainGameWindow = new JFrame("NewGame");//Makes window with title "NewGame"
    private Timer paintTicker = new Timer(50, this); //Ticks every 20 milliseconds (50 times per second); calls on actionPerformed() when it ticks.
    private int outerRingRadius = heightOfScreen / 2;
    private int universeDiameter = heightOfScreen;
    private Point manStartingPoint = new Point(universeDiameter, universeDiameter/2);
    private Man man = new Man(manStartingPoint);
    private ArrayList<Ellipse2D.Double> circles = new ArrayList<Ellipse2D.Double>();
    private Iterator<Ellipse2D.Double> circleIt;
    private ArrayList<Line2D.Double> lines = new ArrayList<Line2D.Double>();
    private Iterator<Line2D.Double> lineIt;
    private ArrayList<Man> searchingSouls = new ArrayList<Man>();
    private Iterator<Man> ssIt;
    private ArrayList<Man> lostSouls = new ArrayList<Man>();
    private Iterator<Man> lsIt;
    private ArrayList<Man> christianSouls = new ArrayList<Man>();
    private Iterator<Man> csIt;
    private ArrayList<Man> heavenSouls = new ArrayList<Man>();
    private Iterator<Man> hsIt;
    private int rowSpacing = 25;
    private int columnSpacing = 250;
    private int textColumnStartPosition = 1800;
    private int textRowStartingPosition = 600;
    private int dataColumnStartPosition = textColumnStartPosition + columnSpacing;
    private int textRow1Position = textRowStartingPosition;
    private int textRow2Position = textRow1Position + rowSpacing;
    private int textRow3Position = textRow1Position +  2 * rowSpacing;

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new GodAndMan());
    }

    public void run()
    {
        GodAndManSetup();
        fillCircleList();
        fillLineList();
        fillInitialSearchingSoulList();
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
        lsIt = lostSouls.iterator();
        csIt = christianSouls.iterator();
        hsIt = heavenSouls.iterator();
        RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
        ssIt = searchingSouls.iterator();
        while(ssIt.hasNext())
        {
            Man soul = ssIt.next();
            int xSquared = (int)(Math.pow((soul.getManShape().x - 800), 2));
            int ySquared =  (int)(Math.pow((soul.getManShape().y - 800), 2));
            int rSquared = (int)Math.pow(outerRingRadius, 2);
            if (xSquared + ySquared > rSquared)
            {
                lostSouls.add(soul);
                ssIt.remove();
                g2.setColor(Color.red);
                g2.fill(soul.getManShape());
            }
            if (xSquared + ySquared < rSquared)
            {
                g2.setColor(Color.WHITE);
                g2.fill(soul.getManShape());
            }
            manDirection = (int)(Math.random() * 360);
            soul.moveMan(manDirection);
            g2.fill(soul.getManShape());
            if (xSquared + ySquared < 200)
            {
                soul.getManShape().height = 100;
                soul.getManShape().width = 100;
                heavenSouls.add(soul);
                ssIt.remove();
            }
            if (xSquared + ySquared >200)
            {
                soul.getManShape().height = 2;
                soul.getManShape().width = 2;
            }
        }
        g2.setColor(Color.white);
        g2.setFont(new Font("Bank Gothic",Font.BOLD , 22));
        g2.drawString("Souls in heaven", textColumnStartPosition, textRow1Position);
        g2.drawString("Souls lost in space", textColumnStartPosition, textRow2Position);
        g2.drawString("Souls still searching", textColumnStartPosition, textRow3Position);
        g2.drawString(heavenSouls.size() + "", dataColumnStartPosition, textRow1Position);
        g2.drawString(  lostSouls.size() + "", dataColumnStartPosition, textRow2Position);
        g2.drawString(  searchingSouls.size() + "", dataColumnStartPosition, textRow3Position);
    }

    private void GodAndManSetup()
    {
        System.out.println("Running");
        mainGameWindow.setTitle("God And Man");
        mainGameWindow.setSize(widthOfScreen, heightOfScreen);
        mainGameWindow.add(this);//Adds the paint method to the JFrame
        mainGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGameWindow.setVisible(true);
        paintTicker.start();
    }
    private void fillCircleList()
    {
        for (int innerRingDiameter = 0; innerRingDiameter < universeDiameter + 10; innerRingDiameter += 10)
        {
            int innerRingRadius = innerRingDiameter / 2;
            circles.add(new Ellipse2D.Double(outerRingRadius - innerRingRadius, outerRingRadius - innerRingRadius, innerRingDiameter, innerRingDiameter));
        }
    }
    private void fillInitialSearchingSoulList()
    {
        for (int angle = 0; angle < 360; angle++)
        {
            int x = (int) (800 + (800 * Math.cos(Math.toRadians(angle))));
            int y = (int) (800 + (800 * Math.sin(Math.toRadians(angle))));
            soulPoint = new Point(x, y);
            searchingSouls.add(new Man(soulPoint));
        }
    }
    private void fillLineList()
    {
        for (int theta = 0; theta < 360; theta += 1) // Degrees
        {
            int y = (int) (outerRingRadius - (outerRingRadius * Math.sin(Math.toRadians(theta))));
            int x = (int) (outerRingRadius + (outerRingRadius * Math.cos(Math.toRadians(theta))));
            Line2D.Double newLine = (new Line2D.Double(x, y, outerRingRadius, outerRingRadius));
            lines.add(newLine);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }
}