package cat.esteve.laberint.main;

import cat.esteve.laberint.gfx.MainCanvas;
import cat.esteve.laberint.input.KeyboardInput;
import cat.esteve.laberint.level.Level;
import cat.esteve.laberint.level.LevelLoader;

import javax.swing.*;
import java.awt.*;

public class Main implements Runnable {

    private JFrame frame;
    private final String frame_name = "SCE - Untitled";
    private int WIDTH = 400, HEIGHT = 300;
    private boolean frame_resizeable = true;

    private MainCanvas canvas;
    private Level level;
    private KeyboardInput kb;

    private int fps = 0, tps = 0, ffps = 0, ftps = 0;

    private void start_thread(String thread_name) {
        Thread thread = new Thread(this, thread_name);
        thread.start();
    }

    private void init() {
        this.frame.setSize(1080, 720);
        this.level = new Level();
        this.kb = new KeyboardInput(this);
        canvas.addKeyListener(this.kb);
   }

    @Override
    public void run() {
        this.canvas.requestFocus();
        init();
        int fps = 0;
        long lastTime = System.nanoTime();
        double unprocessed = 0;
        double nsPerTick = 1000000000.0 / 60;
        long lastTimer1 = System.currentTimeMillis();

        while (true) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;
            while (unprocessed >= 1) {
                tps++;
                update();
                unprocessed -= 1;
            }

            fps++;
            render();

            if (System.currentTimeMillis() - lastTimer1 > 1000) {
                lastTimer1 += 1000;
                this.frame.setTitle("FPS: " + fps);
                ffps = fps;
                ftps = tps;
                fps = 0;
                tps = 0;
            }
        }
    }

    private void update() {
        this.level.update();
    }

    private void render() {
        if(!canvas.start()) return;
        canvas.clear();
        this.level.render(canvas);
        renderHUD();
        canvas.end();
    }

    private void renderHUD() {
        int lives = this.level.getMainPlayer().getLives();
        for(int i = 0; i < lives; i++) {
            canvas.fill(i*20 + 10,10, 10, 20, Color.red);
        }
    }

    public void onKeyDown(int key) {
        this.level.onKeyDown(key);
    }

    public void onKeyUp(int key) {
        this.level.onKeyUp(key);
    }

    public Point getFrameCurrentSize() {
        return new Point(this.frame.getSize().width, this.frame.getSize().height);
    }

    public void start() {
        this.frame = new JFrame(this.frame_name);
        this.canvas = new MainCanvas();

        this.frame.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.frame.setMinimumSize(new Dimension(this.WIDTH, this.HEIGHT));

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocation(10, 10);
        this.frame.setResizable(this.frame_resizeable);
        this.frame.setVisible(true);

        this.frame.add(canvas);

        this.frame.requestFocus();

        this.frame.requestFocus();

        this.start_thread("Main Thread");
    }
}

