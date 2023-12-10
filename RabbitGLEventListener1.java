

import Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;

public class RabbitGLEventListener1 extends RabbitListener implements GLEventListener, MouseListener{
    int animationIndex = 0;
    int ir;
    int jr;
    long starttime;
    long endtime;
    int maxWidth = 100;
    int maxHeight = 100;
    int xpos;
    int ypos;
    int xclicked=200;
    int yclicked=200;
    int x = maxWidth / 2, y = maxHeight / 2;
    GLCanvas glCanvas;
    int[][] HoleIndex = new int[3][3];
    boolean displayed;

    String textureNames[] = {"Rabbit.png", "Hammer.png", "Hole.png", "Boom.png", "Hit.png", "Back.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];
///////////////////////////////////////////////////
    public RabbitGLEventListener1(GLCanvas glCanvas) {
        this.glCanvas = glCanvas;
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
        
         System.out.println(xclicked+"  "+yclicked);
        
}

//////////////////////////////////////////////////
    
    
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
starttime=System.currentTimeMillis();
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

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

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
        animationIndex =animationIndex %4;
        GenerateRabbit();
        DrawRabbit(gl);
        
        if(xclicked>=xpos-25 &&xclicked<=xpos+25 ){
            if(yclicked>=ypos-25&&yclicked<=ypos+25){
            DrawSprite(gl, xpos, ypos, 3, 2);
            xclicked=200;
            yclicked=200;}}

       //System.out.println(xpos+"  "+ypos);
       

    }
//        handleKeyPress();

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }
    public void GenerateRabbit(){
        endtime=System.currentTimeMillis();
        if(endtime-starttime>1000){
            starttime=endtime;
            ir= (int) (Math.random()*3);
            jr= (int) (Math.random()*3);
        }

    }
    public void DrawRabbit(GL gl){
        xpos=x-30+ir*30;
        ypos=y-jr*15;
        DrawSprite(gl,xpos,ypos,4,3);
    }
}
