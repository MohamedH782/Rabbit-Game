

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

public class RabbitGLEventListener6 extends RabbitListener {

    private final TextRenderer textRenderer = new TextRenderer(new Font("SansSerif", Font.PLAIN, 24));
    
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
    int lives = 4;
    int x = maxWidth / 2, y = maxHeight / 2;
    boolean sound, changeState, levels, back, easy, howToPlay, home, gameOver = false;
    int x2 = 0, y2 = 0;
    int[][] HoleIndex = new int[3][3];
    File file = new File("Assets\\Background.wav");
    File Bonkfile = new File("Assets\\Bonk.wav");
    String textureNames[] = {"Rabbit.png", "Hammer.png", "Hole.png", "Boom.png", "Hit.png", "Back.png",
            "play.png", "exit.png", "soundOn.png", "soundOff.png", "easy.png", "medium.png", "hard.png",
            "backbtn.png", "hammer3.png", "HowToPlay.png", "playAgain.png", "home.png",
            "restart.png", "resume.png", "back1.jpg", "Hamme2r.png", "Hamer3.png", "Hammer4.png", "gameOver.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];
    private boolean gameStarted = false;
    private int mouseX, mouseY;
    private long timertime;
    private int score = 0;
    private boolean meduim;
    private boolean hard;


    public RabbitGLEventListener6() {
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
        if (gameStarted && !(xclicked >= xpos - 5 && xclicked <= xpos + 10 &&
                yclicked >= ypos - 10 && yclicked <= ypos + 10)) {
            lives--;
        }

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
        if (lives > 0) {
            if (easy) {
                changev(gl, 1000);
            } else if (meduim && levels) {
                changev(gl, 700);
            } else if (hard && levels) {
                changev(gl, 700);
            }

            //start menu
            else if (levels && back == false) {
                DrawBackground2(gl);
                DrawLevels(gl, x, y);
                //DrawSound(gl, x, y, 8);
            } else if (back && levels) {
                DrawBackground2(gl);
                DrawPlay(gl, x, y, 6);
                DrawHowToPlay(gl, x, y, 15);
                DrawExit(gl, x, y, 7);
               // DrawSound(gl, x, y, 8);
                back = false;
                levels = false;
            } else if (howToPlay && back == false) {
                DrawBackground2(gl);
                DrawSprite2(gl, x - 92, y - 8, 13, 0.9f, 0.9f);
               // DrawSound(gl, x, y, 8);
                instructions(gl);

            } else if (home) {
                levels = false;
            } else {
                DrawBackground2(gl);

                DrawPlay(gl, x, y, 6);
                DrawExit(gl, x, y, 7);
                DrawHowToPlay(gl, x, y, 15);
                //DrawSound(gl, x, y, 8);
            }
            
        } else {

            DrawGameOverMessage(gl);
        }
    }

    void instructions(GL gl) {
        //  gl.glColor3f(0,0,0);
        gl.glRasterPos2f(-0.9f, 0f);

        gl.glScaled(2, 2, 0);
        String msg = "You should hit the rabbit with the hammer to get a score & if you hit an empty hole more than 3 times you'll lose";


        for (char c : msg.toCharArray()) {
            glut.glutBitmapString(GLUT.BITMAP_TIMES_ROMAN_24, msg);

        }

    }

    void startMenu(GL gl) {
        DrawBackground2(gl);

        DrawPlay(gl, x, y, 6);
        DrawExit(gl, x, y, 7);
        DrawHowToPlay(gl, x, y, 15);
        //DrawSound(gl, x, y, 8);
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

        DrawHammer(gl);
        if (xclicked >= xpos - 5 && xclicked <= xpos + 10) {
            if (yclicked >= ypos - 10 && yclicked <= ypos + 10) {
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
               // BonksoundDisplay();
            }
        }
        drawScore(gl);
        DrawTimer(gl);
        drawlives(gl);
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

        //default
        if (changeState == false && sound == false) {
            DrawSprite2(gl, x - 8, y - 8, index, 0.9f, 0.9f);
        //sounf off
        } else if (changeState && sound == false) {
            DrawSprite2(gl, x - 8, y - 8, 9, 0.85f, 0.85f);
            sound = true;
            changeState = false;
        } else if (sound && changeState == false) {
            DrawSprite2(gl, x - 8, y - 8, 9, 0.85f, 0.85f);
            //sound on
        } else if (changeState && sound == true) {
            DrawSprite2(gl, x - 8, y - 8, index, 0.85f, 0.85f);
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

    void handleMousePressed() {
        if (xclicked >= 88 && xclicked <= 95 && yclicked <= 96 && yclicked >= 90) {
            changeState = true;
        } else if (xclicked >= 38 && xclicked <= 60 && yclicked <= 77 && yclicked >= 65 && levels == false) {
            levels = true;

        } else if (xclicked >= 4 && xclicked <= 10 && yclicked <= 96 && yclicked >= 90) {
            back = true;
        } else if (xclicked >= 38 && xclicked <= 60 && yclicked <= 77 && yclicked >= 65 && levels) {
            easy = true;
            gameStarted = true;
            

        } else if (xclicked >= 38 && xclicked <= 61 && yclicked <= 58 && yclicked >= 44 && levels) {
            meduim = true;
            gameStarted = true;


        } else if (xclicked >= 38 && xclicked <= 61 && yclicked <= 38 && yclicked >= 24 && levels) {
            hard = true;
            gameStarted = true;

        } else if (xclicked >= 38 && xclicked <= 60 && yclicked <= 57 && yclicked >= 46 && levels == false) {
            howToPlay = true;
        }


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

    private void drawlives(GL gl) {
        gl.glRasterPos2f(-0.9f, .7f);
        String livesString = "Lives: " + lives;
        for (char c : livesString.toCharArray()) {
            glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, (char) c);
        }
    }
    public void displayBackGroundSound() {
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

    private void DrawGameOverMessage(GL gl) {
        gameOver = true;
        DrawBackground(gl);
        DrawSprite(gl, x - 5, y + 5, 24, 6);

        gl.glRasterPos2f(-0.2f, -0.2f);
        String scoreString = "Highest score: " + score;
        for (char c : scoreString.toCharArray()) {
            glut.glutBitmapCharacter(GLUT.BITMAP_HELVETICA_18, (char) c);
        }
        //  System.out.println(xclicked);

        DrawSprite2(gl, x - 70, y - 70, 17, 2.5f, 1.5f);
        DrawSprite2(gl, x - 30, y - 70, 16, 2.5f, 1.5f);

        if (xclicked >= 19 && xclicked <= 40 && yclicked <= 37 && yclicked >= 26) {
           gameOver = false;
        startMenu(gl);
        // Reset game variables and state as needed
        lives = 4;
        score = 0;
        easy = false;
        meduim = false;
        hard = false;
        gameStarted = false;
        levels = false;
        back = false;
        howToPlay = false;
        home = false;
            
        } else if (xclicked >= 58 && xclicked <= 79 && yclicked <= 37 && yclicked >= 26) {
            
            if (easy && gameOver) {
                gameOver = false;
                lives=4;
                score=0;
                DrawBackground(gl);
                changev(gl, 1000);
                
            }
            

        }
      //lives=4;
    }
}

