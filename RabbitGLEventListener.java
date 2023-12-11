package RabbitGame.Man;

import Texture.TextureReader;
import com.sun.opengl.util.j2d.TextRenderer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class RabbitGLEventListener extends RabbitListener {


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
        boolean displayed;
         boolean mouseClick = false;
        int x = maxWidth / 2, y = maxHeight / 2;
        boolean sound, changeState, levels, back, easy = false , meduim = false , hard= false;
        private int mouseX, mouseY;
         int x2 = 0, y2 = 0;

        int[][] HoleIndex = new int[3][3];
      private final TextRenderer textRenderer = new TextRenderer(new Font("SansSerif", Font.PLAIN, 24));
        String textureNames[] = {"Rabbit.png", "Hammer.png", "Hole.png", "Boom.png", "Hit.png", "Back.png",
                "play.png","exit.png","soundOn.png", "soundOff.png" , "easy.png", "medium.png", "hard.png",
                "backbtn.png", "hammmmer3.png","HowToPlay.png","back1.jpg"};
        TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
        int textures[] = new int[textureNames.length];

        @Override
        public void mouseClicked (MouseEvent e){
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
            System.out.println(xclicked + "  " + yclicked);



    }
        @Override
        public void mousePressed (MouseEvent e){
           // mouseX = e.getX();
           // mouseY = e.getY();


            double X = e.getX();
            double Y = e.getY();
            Component c = e.getComponent();
            double canvasWidth = c.getWidth();
            double canvasHeight = c.getHeight();

            // Convert mouse coordinates to our '100' based coordinate system.
            mouseX = (int) ((X / canvasWidth) * 100);
            mouseY = (int) (100 - ((Y / canvasHeight) * 100));
            System.out.println(mouseX);
            handleMousePressed();
    }

        @Override
        public void mouseReleased (MouseEvent e){

    }

        @Override
        public void mouseEntered (MouseEvent e){

    }

        @Override
        public void mouseExited (MouseEvent e){

    }

        @Override
        public void mouseDragged (MouseEvent e){

    }

        @Override
        public void mouseMoved (MouseEvent e){
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
        public void init (GLAutoDrawable glAutoDrawable){
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black


        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);
        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(RabbitFolderName   + "//" + textureNames[i], true);
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
        starttime = System.currentTimeMillis();
    }

        public void DrawSprite (GL gl,int x, int y, int index, float scale){
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


    public void DrawBackground (GL gl){
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
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[textures.length - 1]);    // Turn Blending On

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
        public void display (GLAutoDrawable glAutoDrawable){
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        DrawBackground(gl);
            if(easy && levels) {
                changev(gl , 1000);

            } else if (meduim && levels) {
                changev(gl , 700);


            } else if (hard  && levels) {
                changev(gl , 400);

            }


            //start menu
            else if(levels && back==false) {
                DrawBackground2(gl);
                DrawLevels(gl, x,y );
                DrawSound(gl, x, y, 8);
            }
            else  if(back && levels) {
                DrawBackground2(gl);
                DrawPlay(gl, x, y, 6);
                DrawHowToPlay(gl, x, y,15);
                DrawExit(gl, x, y, 7);
                DrawSound(gl, x, y, 8);
                back = false;
                levels=false;
            } else if (meduim&& levels) {

            } else {
                DrawBackground2(gl);

                DrawPlay(gl, x, y, 6);
                DrawExit(gl, x, y, 7);
                DrawHowToPlay(gl, x, y,15);
                DrawSound(gl, x, y, 8);
            }


        }
//        handleKeyPress();

        @Override
        public void reshape (GLAutoDrawable glAutoDrawable,int i, int i1, int i2, int i3){

    }

        @Override
        public void displayChanged (GLAutoDrawable glAutoDrawable,boolean b, boolean b1){

    }
    public void changev(GL gl , double  v){
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

    }}}
        public void GenerateRabbit ( double v) {
        endtime = System.currentTimeMillis();

        if (endtime - starttime > v) {
            starttime = endtime;
            ir = (int) (Math.random() * 3);
            jr = (int) (Math.random() * 3);

       }

    }
        public void DrawRabbit (GL gl){
        DrawSprite(gl, x - 30 + ir * 30, y - jr * 15, 4, 3);
    }
    public void DrawHammer(GL gl) {
        if (mouseClick) {
            DrawSprite(gl, xmove - 5, ymove - 5, 14, 1f);
        } else {
            DrawSprite(gl, xmove - 5, ymove - 5, 1, 1f);
        }

        mouseClick = false;
    }



      void DrawPlay (GL gl, int x,int y,int index){
        DrawSprite2(gl, x - 50, y - 30, index, 2.5f,1.5f);
      }
    void DrawHowToPlay (GL gl, int x,int y,int index){

        //  gl.glTranslated(0,-0.4,0);
        DrawSprite2(gl, x-50, y - 50, index, 2.5f,1.5f);
    }
      void DrawExit (GL gl, int x,int y,int index){

     //  gl.glTranslated(0,-0.4,0);
         DrawSprite2(gl, x-50, y - 70, index, 2.5f,1.5f);
      }

      void DrawSound(GL gl, int x,int y,int index) {
      //  gl.glTranslated(0.85,0.85,0);
        if(changeState == false && sound == false )
            DrawSprite2(gl, x-8, y-8, index, 0.9f, 0.9f);

        else if(changeState && sound == false) {
            DrawSprite2(gl, x - 8, y - 8, 9, 0.85f, 0.85f);
            sound = true;
            changeState = false;
        }
        else if(sound && changeState == false) {
            DrawSprite2(gl, x - 8, y -8, 9, 0.85f, 0.85f);
        }
        else if(changeState && sound == true) {
            DrawSprite2(gl, x - 8, y -8 , index, 0.85f, 0.85f);
            sound = false;
            changeState = false;
        }
    }
    void DrawLevels(GL gl, int x, int y) {
        DrawSprite2(gl, x-50, y-30, 10, 2.5f,1.5f);

        DrawSprite2(gl, x-50, y-50, 11, 2.5f,1.5f);

        DrawSprite2(gl, x-50, y-70, 12, 2.5f,1.5f);

        DrawSprite2(gl, x - 92, y - 8 , 13, 0.9f, 0.9f);

        //  DrawSound(gl, x, y, 2);
    }
    void handleMousePressed() {
        if(mouseX >= 88 && mouseX <= 95 && mouseY <= 96 && mouseY >= 90 ) {
            changeState = true;
        }
        else  if(mouseX >= 38 && mouseX <= 60 && mouseY <= 77 && mouseY >= 65 && levels == false) {
            levels = true;
            System.out.println("play");

        }
        else if(mouseX >= 4 && mouseX <= 10 && mouseY <= 96 && mouseY >= 90) {
            back = true;
        }
        else  if(mouseX >= 38 && mouseX <= 60 && mouseY <= 77 && mouseY >= 65 && levels ) {
            easy = true;

        }
        else  if(mouseX >= 38 && mouseX <= 61 && mouseY <= 58 && mouseY >= 44 && levels ) {
            meduim = true;

        }
        else  if(mouseX >= 38 && mouseX <= 61 && mouseY <= 38 && mouseY >= 24 && levels ) {
            meduim = true;

        }

    }

}

