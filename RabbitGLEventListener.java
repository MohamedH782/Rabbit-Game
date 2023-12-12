package RabbitGame.Man;

import Texture.TextureReader;
import com.sun.opengl.util.GLUT;
import com.sun.opengl.util.j2d.TextRenderer;

import javax.sound.sampled.*;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class RabbitGLEventListener extends RabbitListener {

    private final TextRenderer textRenderer = new TextRenderer(new Font("SansSerif", Font.PLAIN, 24));
    int lives = 4;
    int animationIndex = 0;
    int ir;
    int jr;
    long starttime;
    long endtime;
    int maxWidth = 100;
    int maxHeight = 100;
    int xclicked = 200;
    int yclicked = 200;
    int xmove;
    int ymove;
    int xpos;
    int ypos;
    GLUT glut = new GLUT();
    boolean displayed;
    boolean mouseClick = false;
    int x = maxWidth / 2, y = maxHeight / 2;
    boolean sound, changeState, levels, back, easy = false , puase=false , restart=false;
    ;
    int x2 = 0, y2 = 0;
    int[][] HoleIndex = new int[3][3];
    File file = new File("Assets\\Background.wav");
    File Bonkfile = new File("Assets\\Bonk.wav");
    String textureNames[] = {"Rabbit.png", "Hammer.png", "Hole.png", "Boom.png", "Hit.png", "Back.png",
            "play.png", "exit.png", "soundOn.png", "soundOff.png", "easy.png", "medium.png", "hard.png",
            "backbtn.png", "hammer3.png", "HowToPlay.png", "playAgain.png", "home.png",
            "restart.png", "resume.png", "back1.jpg", "Hamme2r.png", "Hamer3.png", "Hammer4.png" , "puase.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];
    private boolean gameStarted = false;
    private int mouseX, mouseY;
    private long timertime;
    private int score = 0;
    private boolean meduim;
    private boolean hard;

    public RabbitGLEventListener() {
        timertime = System.currentTimeMillis();

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();

        Component c = e.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();

        xclicked = (int) ((x / width) * 100);
        yclicked = ((int) ((y / height) * 100));
//reversing direction of y axis
        yclicked = 100 - yclicked;
        mouseClick = true;
        handleMousePressed();

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        double X = e.getX();
        double Y = e.getY();
        Component c = e.getComponent();
        double canvasWidth = c.getWidth();
        double canvasHeight = c.getHeight();

        // Convert mouse coordinates to our '100' based coordinate system.
        xmove = (int) ((X / canvasWidth) * 100);
        ymove = (int) (100 - ((Y / canvasHeight) * 100));

    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);
        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(RabbitFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        //Backgound Audio
        AudioInputStream audiostream;

        {
            try {
                audiostream = AudioSystem.getAudioInputStream(file);
            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Clip clip;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            clip.open(audiostream);
            clip.loop(clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        starttime = System.currentTimeMillis();
    }

    public void DrawSprite(GL gl, int x, int y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawSprite2(GL gl, int x, int y, int index, float scalex, float scaley) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0), y / (maxHeight / 2.0), 0);
        gl.glScaled(0.1 * scalex, 0.1 * scaley, 1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[5]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    public void DrawBackground2(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[20]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        DrawBackground(gl);
             if ((easy && levels)||(easy && levels&&restart)){
            changev(gl, 1000);
        } else if ((meduim && levels)||(meduim && levels&&restart)) {
            changev(gl, 700);
        } else if ((hard && levels)||(hard && levels&&restart)) {
            changev(gl, 400);
        }
        else if (puase && levels==false) {
            DrawBackground2(gl);
            Drawpuasemenu(gl, x,y );

        }
//             else if (restart  && levels && easy ){
//                 changev(gl, 1000);
//
//             }
//             else if (restart && levels && meduim){
//                 changev(gl, 700);
//
//             }
//             else if (restart && levels && hard){
//                 changev(gl, 400);
//             }

        //start menu
        else if (levels && back == false) {
            DrawBackground2(gl);
            DrawLevels(gl, x, y);
            DrawSound(gl, x, y, 8);
        } else if (back && levels) {
            DrawBackground2(gl);
            DrawPlay(gl, x, y, 6);
            DrawHowToPlay(gl, x, y, 15);
            DrawExit(gl, x, y, 7);
            DrawSound(gl, x, y, 8);
            back = false;
            levels = false;
        } else {
            DrawBackground2(gl);

            DrawPlay(gl, x, y, 6);
            DrawExit(gl, x, y, 7);
            DrawHowToPlay(gl, x, y, 15);
            DrawSound(gl, x, y, 8);
        }

    }



    public void changev(GL gl, double v) {
        //first row
        DrawSprite(gl, x - 30, y, 2, 2);
        DrawSprite(gl, x, y, 2, 2);
        DrawSprite(gl, x + 30, y, 2, 2);

        //second row
        DrawSprite(gl, x - 30, y - 15, 2, 2);
        DrawSprite(gl, x, y - 15, 2, 2);
        DrawSprite(gl, x + 30, y - 15, 2, 2);

        //third row
        DrawSprite(gl, x - 30, y - 30, 2, 2);
        DrawSprite(gl, x, y - 30, 2, 2);
        DrawSprite(gl, x + 30, y - 30, 2, 2);
        animationIndex = animationIndex % 4;
        GenerateRabbit(v);
        DrawRabbit(gl);
        Drawpuase( gl,  x,  y);

        DrawHammer(gl);
        if (xclicked >= xpos - 5 && xclicked <= xpos + 10) {
            if (yclicked >= ypos - 10 && yclicked <= ypos + 16) {
                DrawBackground(gl);

                //first row
                DrawSprite(gl, x - 30, y, 2, 2);
                DrawSprite(gl, x, y, 2, 2);
                DrawSprite(gl, x + 30, y, 2, 2);

                //second row
                DrawSprite(gl, x - 30, y - 15, 2, 2);
                DrawSprite(gl, x, y - 15, 2, 2);
                DrawSprite(gl, x + 30, y - 15, 2, 2);

                //third row
                DrawSprite(gl, x - 30, y - 30, 2, 2);
                DrawSprite(gl, x, y - 30, 2, 2);
                DrawSprite(gl, x + 30, y - 30, 2, 2);

                DrawSprite(gl, xpos, ypos, 3, 2);
                xclicked = 200;
                yclicked = 200;
                score++;
                BonksoundDisplay();
            }
        }
        drawScore(gl);
        DrawTimer(gl);

    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    public void GenerateRabbit(double v) {
        endtime = System.currentTimeMillis();
        if (endtime - starttime > v) {
            starttime = endtime;
            ir = (int) (Math.random() * 3);
            jr = (int) (Math.random() * 3);
        }

    }

    public void DrawRabbit(GL gl) {
        xpos = x - 30 + ir * 30;
        ypos = y - jr * 15;
        DrawSprite(gl, xpos, ypos, 4, 3);
    }

    public void DrawHammer(GL gl) {
        if (mouseClick) {
//            DrawSprite(gl, xmove - 5, ymove - 5, 14, 1f);
            DrawSprite(gl, xmove - 5, ymove - 5, 21, 1f);
            DrawSprite(gl, xmove - 5, ymove - 5, 22, 1f);
            DrawSprite(gl, xmove - 5, ymove - 5, 23, 1f);
            DrawSprite(gl, xmove - 5, ymove - 5, 14, 1f);

        } else {
            DrawSprite(gl, xmove - 5, ymove - 5, 1, 1f);
        }

        mouseClick = false;
    }

    void DrawPlay(GL gl, int x, int y, int index) {
        DrawSprite2(gl, x - 50, y - 30, index, 2.5f, 1.5f);
    }

    void DrawHowToPlay(GL gl, int x, int y, int index) {

        //  gl.glTranslated(0,-0.4,0);
        DrawSprite2(gl, x - 50, y - 50, index, 2.5f, 1.5f);
    }

    void DrawExit(GL gl, int x, int y, int index) {

        //  gl.glTranslated(0,-0.4,0);
        DrawSprite2(gl, x - 50, y - 70, index, 2.5f, 1.5f);
    }

    void DrawSound(GL gl, int x, int y, int index) {
        //  gl.glTranslated(0.85,0.85,0);
        if (changeState == false && sound == false) {
            DrawSprite2(gl, x - 15, y - 8, index, 0.9f, 0.9f);
        } else if (changeState && sound == false) {
            DrawSprite2(gl, x - 15, y - 8, 9, 0.85f, 0.85f);
            sound = true;
            changeState = false;
        } else if (sound && changeState == false) {
            DrawSprite2(gl, x - 15, y - 8, 9, 0.85f, 0.85f);
        } else if (changeState && sound == true) {
            DrawSprite2(gl, x - 15, y - 8, index, 0.85f, 0.85f);
            sound = false;
            changeState = false;
        }
    }

    void DrawLevels(GL gl, int x, int y) {
        DrawSprite2(gl, x - 50, y - 30, 10, 2.5f, 1.5f);

        DrawSprite2(gl, x - 50, y - 50, 11, 2.5f, 1.5f);

        DrawSprite2(gl, x - 50, y - 70, 12, 2.5f, 1.5f);

        DrawSprite2(gl, x - 92, y - 8, 13, 0.9f, 0.9f);

        //  DrawSound(gl, x, y, 2);
    }
    void Drawpuase(GL gl, int x, int y) {
        DrawSprite2(gl, x - 8, y - 8, 24, 0.85f, 0.85f);

    }
    void Drawpuasemenu(GL gl, int x, int y) {


        DrawSprite2(gl, x-50, y-30, 19, 2.5f,1.5f);
        DrawSprite2(gl, x - 50, y - 50, 18, 2.5f, 1.5f);


    }

    void handleMousePressed() {
        if (xclicked >= 88 && xclicked <= 95 && yclicked <= 96 && yclicked >= 90 && levels) {
            // Pause button clicked
            puase = true;
            levels = false;
            System.out.println("Paused");
        } else if (xclicked >= 38 && xclicked <= 61 && yclicked <= 58 && yclicked >= 44 && puase) {


                restartGame();
                puase = false;
                levels = true;  // Set the levels flag to true to start the game from levels
                System.out.println("Restarted");

//
//



        }
        else if (xclicked >= 88 && xclicked <= 95 && yclicked <= 96 && yclicked >= 90) {
            changeState = true;
        } else if (xclicked >= 38 && xclicked <= 60 && yclicked <= 77 && yclicked >= 65 && levels == false) {
            levels = true;
            System.out.println("play");

        }
        else if (xclicked >= 38 && xclicked <= 61 && yclicked <= 38 && yclicked >= 24 && levels == false) {
            System.out.println("Exit");
            System.exit(0);
        }
        else if (xclicked >= 4 && xclicked <= 10 && yclicked <= 96 && yclicked >= 90) {
            back = true;
        } else if (xclicked >= 38 && xclicked <= 60 && yclicked <= 77 && yclicked >= 65 && levels) {
            easy = true;

        } else if (xclicked >= 38 && xclicked <= 61 && yclicked <= 58 && yclicked >= 44 && levels) {
            meduim = true;

        } else if (xclicked >= 38 && xclicked <= 61 && yclicked <= 38 && yclicked >= 24 && levels) {
            hard = true;

        }
//        else if (xclicked >= 38 && xclicked <= 61 && yclicked <= 58 && yclicked >= 44 && puase) {
//
//            restart=true;
//
////

//        }
System.out.println(xclicked+""+yclicked);


    }

    public void DrawTimer(GL gl) {
        long currentTimeMillis = System.currentTimeMillis();
        long elapsedTimeSeconds = (currentTimeMillis - timertime) / 1000;

        gl.glRasterPos2d(-0.9f, 0.9f);
        String timer = "Timer: " + elapsedTimeSeconds;

        for (char t : timer.toCharArray()) {
            glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, t);
        }
    }

    private void drawScore(GL gl) {
        gl.glRasterPos2f(-0.9f, .8f);
        String scoreString = "Score: " + score;
        for (char c : scoreString.toCharArray()) {
            glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, (char) c);
        }
    }
    public void restartGame() {
        lives = 4;
        animationIndex = 0;
        score = 0;
        timertime = System.currentTimeMillis();
        // Reset any other variables as needed
    }

    public void BonksoundDisplay() {
        AudioInputStream audiostream;

        {
            try {
                audiostream = AudioSystem.getAudioInputStream(Bonkfile);
            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        try {
            clip.open(audiostream);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        clip.start();

    }
}
