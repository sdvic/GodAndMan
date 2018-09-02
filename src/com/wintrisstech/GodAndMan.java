package com.wintrisstech;
/**************************************************************************
 * Initial ver 0.1 9/1/18
 * Copyright 2018 Vic Wintriss
 **************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class GodAndMan extends JComponent implements ActionListener, Runnable
{
    private int widthOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int heightOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private JFrame mainGameWindow = new JFrame("NewGame");//Makes window with title "NewGame"
    private Rectangle2D.Double universe = new Rectangle2D.Double(-heightOfScreen/2, -heightOfScreen/2, heightOfScreen, heightOfScreen);
    private Ellipse2D.Double limit = new Ellipse2D.Double(1,1,1,1);
    private ArrayList<Ellipse2D.Double> circles = new ArrayList<Ellipse2D.Double>();
    private Timer paintTicker = new Timer(500, this); //Ticks every 20 milliseconds (50 times per second); calls on actionPerformed() when it ticks.

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
        for (int i = 1; i < widthOfScreen/2; i+=5)
        {
            circles.add(new Ellipse2D.Double(-heightOfScreen/2, -heightOfScreen/2, i, i));
        }
        System.out.println("The size of circles is " + circles.size());
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.pink);
        g2.fill(universe);
        g2.setColor(Color.black);
        g2.translate(heightOfScreen/2, heightOfScreen/2);
        for (Ellipse2D.Double ellipse : circles)
        {
            g2.draw(ellipse);
            System.out.println("circle x/y = " + ellipse.getX() + "/" + ellipse.getY());
        }

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
            repaint();
    }
}
