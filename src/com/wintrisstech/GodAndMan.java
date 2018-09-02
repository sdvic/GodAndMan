package com.wintrisstech;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

public class GodAndMan extends JComponent implements ActionListener, Runnable
{
    private int widthOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
    private int heightOfScreen = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
    private JFrame mainGameWindow = new JFrame("NewGame");//Makes window with title "NewGame"
    private Rectangle2D.Double universe = new Rectangle2D.Double(0, 0, widthOfScreen, heightOfScreen);
    private Timer paintTicker = new Timer(20, this); //Ticks every 20 milliseconds (50 times per second); calls on actionPerformed() when it ticks.

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new GodAndMan());
    }

    public void run()
    {
        mainGameWindow.setTitle("God And Man");
        mainGameWindow.setSize(widthOfScreen, heightOfScreen);
        mainGameWindow.add(this);//Adds the paint method to the JFrame
        mainGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGameWindow.setVisible(true);
        paintTicker.start();
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.pink);
        g2.fill(universe);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
            repaint();
    }
}