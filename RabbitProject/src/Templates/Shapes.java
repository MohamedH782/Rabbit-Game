package Templates;

import javax.media.opengl.GL;

public class Shapes {
    public static void drawPolygonCircle(int xCenter, int yCenter, int radius, GL gl, float c1, float c2, float c3) {
        gl.glColor3f(c1, c2, c3);

        int x, y;
        gl.glBegin(GL.GL_POLYGON);
        gl.glColor3f(c1, c2, c3);
        for (double a = 0; a < Math.toRadians(360); a += Math.toRadians(1)) {
            x = (int) (radius * (Math.cos(a))) + xCenter;
            y = (int) (radius * (Math.sin(a))) + yCenter;
            gl.glVertex2d(x, y);
        }
        gl.glEnd();

    }

    public static void drawCircle(int xCenter, int yCenter, int radius, GL gl,float c1, float c2, float c3) {
        gl.glColor3f(c1, c2, c3);
        int x, y;
        gl.glBegin(GL.GL_LINE_LOOP);
        for (double a = 0; a < Math.toRadians(360); a += Math.toRadians(1)) {
            x = (int) (radius * (Math.cos(a))) + xCenter;
            y = (int) (radius * (Math.sin(a))) + yCenter;
            gl.glVertex2d(x, y);
        }
        gl.glEnd();

    }


    public static void drawEllipse(int xCenter, int yCenter, double a, double b, GL gl) {
        gl.glBegin(GL.GL_LINE_LOOP);
        for (int i = 0; i < 100; i++) {
            double angle = 2.0 * Math.PI * i / 100;
            double x = a * Math.cos(angle) + xCenter;
            double y = b * Math.sin(angle) + yCenter;

            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    public static void drawPolygonTriangle(int xp1, int yp1, int xp2, int yp2, int xp3, int yp3, GL gl) {

        gl.glBegin(GL.GL_POLYGON);
        gl.glVertex2i(xp1, yp1);
        gl.glVertex2i(xp2, yp2);
        gl.glVertex2i(xp3, yp3);
        gl.glEnd();

    }

    public static void drawSquare(int x1Pos, int x2Pos, int y1Pos, int y2Pos, GL gl) {
        gl.glBegin(GL.GL_LINES);
        //
        gl.glVertex2i(x1Pos, y1Pos);
        gl.glVertex2i(x2Pos, y1Pos);
        //
        gl.glVertex2i(x2Pos, y1Pos);
        gl.glVertex2i(x2Pos, y2Pos);
        //
        gl.glVertex2i(x2Pos, y2Pos);
        gl.glVertex2i(x1Pos, y2Pos);
        //
        gl.glVertex2i(x1Pos, y2Pos);
        gl.glVertex2i(x1Pos, y1Pos);


        gl.glEnd();
    }

    public static void drawBackGround(int x1Pos, int x2Pos, int y1Pos, int y2Pos, GL gl,float c1, float c2, float c3) {
        gl.glBegin(GL.GL_POLYGON);
        //
        gl.glColor3f(c1, c2, c3);
        gl.glVertex2i(x1Pos, y1Pos);
        gl.glVertex2i(x2Pos, y1Pos);
        gl.glVertex2i(x2Pos, y2Pos);
        //
        gl.glVertex2i(x1Pos, y2Pos);


        gl.glEnd();
    }

    public static void drawTriangle(int x1Pos, int x2Pos, int y1Pos, int y2Pos, GL gl) {
        gl.glBegin(GL.GL_LINES);
        //
        gl.glVertex2i(x1Pos, y1Pos);
        gl.glVertex2i(x2Pos, y1Pos);
        //
        gl.glVertex2i(x2Pos, y1Pos);
        gl.glVertex2i(x2Pos - x1Pos, y1Pos + y2Pos);
        //
        gl.glVertex2i(x2Pos - x1Pos, y1Pos + (y2Pos - y1Pos));
        gl.glVertex2i(x1Pos, y1Pos);


        gl.glEnd();
    }

}
