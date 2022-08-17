package com.tilen.Engine.Utils;

import com.tilen.Engine.Game;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class DrawUtils {

    protected Graphics2D g2d;

    public void setGraphics(Graphics2D g) {
        this.g2d = g;
        this.g2d.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    public DrawUtils() {}

    public DrawUtils(Graphics2D g) { this.g2d = g; }

    public DrawUtils push() {
        return new DrawUtils((Graphics2D) this.g2d.create());
    }

    public static Graphics2D push(Graphics2D g) {
        return (Graphics2D) g.create();
    }

    public void pop() {
        this.g2d.dispose();
    }

    public static void pop(Graphics2D g) {
        g.dispose();
    }

    public static boolean fill = true;

    public void noFill() {
        fill = false;
    }

    public void storke(float strokeWeight) {
        this.g2d.setStroke(new BasicStroke(strokeWeight));
    }

    public void fill(int r, int g, int b) {
        fill(r, g, b, 255);
    }

    public void fill(int r, int g, int b, int a) {
        fill = true;
        Color c = new Color(r, g, b, a);
        this.g2d.setColor(c);
        this.g2d.setPaint(c);
    }

    public void fill(int gs) {
        fill(gs, gs, gs);
    }

    public void fill(Color c) {
        fill = true;
        this.g2d.setColor(c);
        this.g2d.setPaint(c);
    }

    public void background(int r, int g, int b) {
        this.g2d.setColor(new Color(r, g, b));
        Rectangle rect = new Rectangle(0,0, Game.WIDTH, Game.HEIGHT);
        this.g2d.fill(rect);
        this.g2d.setColor(Color.WHITE);
    }

    public void rect(double x, double y, double w, double h) {
        if(!fill) {
           this.g2d.draw(new Rectangle2D.Double(x, y, w, h));
        } else {
           this.g2d.fill(new Rectangle2D.Double(x, y, w, h));
        }
    }

    public void ellipse(double x,  double y, double r1, double r2) {
        this.g2d.fill(new Ellipse2D.Double(x, y, r1, r2));
    }

    public void rotate(double angle, double x, double y) {
        g2d.rotate(angle, x, y);
    }

    public void rectFromPoints(double x, double y, double x2, double y2) {
        double xSize = Double.MAX_VALUE;
        double ySize = Double.MAX_VALUE;
        double drawX = Double.MAX_VALUE;
        double drawY = Double.MAX_VALUE;

        // x1 < x2 && y < y2
        if (x < x2 && y < y2) {
            xSize = x2 - x;
            ySize = y2 - y;
            drawX = x;
            drawY = y;
        }
        // x1 < x2 && y < y2
        else if (x > x2 && y < y2) {
            xSize = x - x2;
            ySize = y2 - y;
            drawX = x2;
            drawY = y;
        }
        // x1 < x2 && y < y2
        else if (x > x2 && y > y2) {
            xSize = x - x2;
            ySize = y - y2;
            drawX = x2;
            drawY = y2;
        }
        else if (x < x2 && y > y2) {
            xSize = x2 - x;
            ySize = y - y2;

            drawX = x;
            drawY = y2;
        }

        if(!fill) {
            g2d.draw(new Rectangle2D.Double(drawX, drawY, xSize, ySize));
        } else {
            g2d.fill(new Rectangle2D.Double(drawX, drawY, xSize, ySize));
        }
    }

    public void line(double x1, double y1, double x2, double y2) {
        this.g2d.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    public void translate(double width, double height) {
        this.g2d.translate(width, height);
    }

    public void setFontName(String fontName) {
        this.g2d.setFont(new Font(fontName, this.g2d.getFont().getStyle(), this.g2d.getFont().getSize()));
    }

    public void setFontSize(int fontSize) {
        this.g2d.setFont(new Font(this.g2d.getFont().getFontName(), this.g2d.getFont().getStyle(), fontSize));
    }

    public void setFontWeight(int fontWeight) {
        this.g2d.setFont(new Font(this.g2d.getFont().getFontName(), fontWeight, this.g2d.getFont().getSize()));
    }

    public void text(Object text, float x, float y) {
        this.g2d.drawString(text.toString(), x, y+this.g2d.getFont().getSize());
    }

    public void textCentered(Object text, float x, float y) {
        String s = text.toString().toUpperCase();
        this.g2d.drawString(s, x - s.length() * 6, y+this.g2d.getFont().getSize());
    }

    public Font getFont() {
        return this.g2d.getFont();
    }
}