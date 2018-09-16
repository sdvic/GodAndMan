package com.wintrisstech;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class Man implements ActionListener
{
    private int manx;
    private int many;
    private int manHeight = 10;
    private int manWidth = 10;
    private Ellipse2D.Double manShape;

    Man(int startX, int starY)
    {
        manShape = new Ellipse2D.Double(0, 0, manWidth, manHeight);
        manShape.x = startX;
        manShape.y = starY;
    }

    private void moveMan(int manMoveDirection)
    {
        manShape.x -= 1;
    }

    Ellipse2D.Double getManShape()
    {
        return manShape;
    }

    public int getManx()
    {
        return (int) manShape.x;
    }

    void setManx(int manx)

    {
        manShape.x = manx;
    }

    public int getMany()
    {
        return (int) manShape.y;
    }

    void setMany(int many)
    {
        manShape.y = many;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        moveMan(4);
    }
}
