package com.wintrisstech;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

class Man
{
    private int manHeight = 2;
    private int manWidth = 2;
    private Ellipse2D.Double manShape;
    private int manSpeed = 2;

    Man(int startX, int starY)
    {
        manShape = new Ellipse2D.Double(0, 0, manWidth, manHeight);
        manShape.x = startX;
        manShape.y = starY;
    }

    void moveMan(int manDirection)//degrees
    {
        manShape.x -= (Math.sin(Math.toRadians(manDirection)) * manSpeed);
        manShape.y += (Math.cos(Math.toRadians(manDirection)) * manSpeed);
    }

    Ellipse2D.Double getManShape()
    {
        return manShape;
    }
}
