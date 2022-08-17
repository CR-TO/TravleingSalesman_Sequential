package com.tilen.Engine;

import com.tilen.Engine.Utils.DrawUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;

public abstract class Game extends DrawUtils implements Runnable {

    public static int WIDTH, HEIGHT;
    public int fps = Integer.MAX_VALUE;
    private String title;
    public Display display;

    public Thread thread;
    public boolean running = false;
    private boolean looping = true;
    private BufferStrategy bs;
    private Graphics2D g;

    public static double mouseX;
    public static double mouseY;

    public Game(int width, int height, String title) {
        WIDTH = width;
        HEIGHT = height;
        this.title = title;
    }

    private void init() {
        display = new Display(WIDTH, HEIGHT, title);
        setup();

        this.display.canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseClickedEvent(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mousePressedEvent(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseReleasedEvent(e);
            }
        });

        this.display.canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseDraggedEvent(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                mouseMovedEvent(e);
            }
        });
    }

    public void mouseClickedEvent(MouseEvent e) {}

    public void mousePressedEvent(MouseEvent e) {}

    public void mouseReleasedEvent(MouseEvent e) {}

    public void mouseMovedEvent(MouseEvent e) {}

    public void mouseDraggedEvent(MouseEvent e) {}

    public void setFPS(int fps) {
        this.fps = fps;
    }

    protected abstract void setup();

    protected abstract void draw(Graphics2D g);

    protected Graphics2D getGraphics() { return this.g; }

    private void render() {
        bs = display.canvas.getBufferStrategy();
        if(bs == null) {
            display.canvas.createBufferStrategy(3);
            return;
        }

        g = (Graphics2D) bs.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        setGraphics(g);

        //draw here
        draw(g);

        bs.show();
        g.dispose();
    }

    // main game loop
    public void run() {
        init();

        double tickDuration = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        while(running) {

            if(looping){
                now = System.nanoTime();
                delta += (now - lastTime) / tickDuration;
                lastTime = now;

                if(delta >= 1) {
                    render();
                    delta--;
                }
            }

        }

        stop();
    }

    public void noLoop() {
        looping = false;
    }

    public void loop() {
        looping = true;
    }

    public synchronized void start() {
        if(running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if(!running) return;
        running = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }
}