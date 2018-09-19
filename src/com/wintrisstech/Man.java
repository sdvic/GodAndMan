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
    private int manSpeed = 25;

    Man(Point manStartingPoing)
    {
        manShape = new Ellipse2D.Double(0, 0, manWidth, manHeight);
        manShape.x = manStartingPoing.x;
        manShape.y = manStartingPoing.y;
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
