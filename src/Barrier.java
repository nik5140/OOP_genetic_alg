import java.awt.*;

public class Barrier extends Rectangle {
    int x1,y1,x2,y2;
    double nx, ny; // normals new function
    public int barrierDrawingMode = 0;
    Graphics figure;

    public static int pointsEntered=0;

    Barrier(int x1, int y1, int x2, int y2)
    {
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
        barrierDrawingMode = Main.barr_drawing_mode;

    }


    double amplitude()   // this is a new part
    {
        return Math.pow(Math.pow(x2-x1,2)+Math.pow(y2-y1,2),0.5);
    }


    void normCalc()  // this is a new part
    {
        nx = (x2-x1)/amplitude(); // !!!this is normalized normal and we can do here something with signs!!!
        ny = -(y2-y1)/amplitude();  //

    }

    void paint_me(Graphics painter)

    {
        // if we don't need orthogonal lines next 4 lines can be deleted or commented
        if (barrierDrawingMode==1)
        {
            if (Math.abs(x1-x2)<Math.abs(y1-y2))
                x2 = x1;
            else if (Math.abs(x1-x2)>Math.abs(y1-y2))
                y2 = y1;

            painter.drawLine(x1,y1,x2,y2);
        }
        else if ((barrierDrawingMode==0))
            painter.drawLine(x1,y1,x2,y2);
        else if (barrierDrawingMode==2)
        {
            super.width = Math.abs(x2-x1);
            super.height = Math.abs(y2-y1);

            if ((x1<x2)&&(y1>y2))
        {   super.x = x1;
            super.y = y2;


        }
            else if ((x1<x2)&&(y1<y2))
            {
                super.x = x1;
                super.y = y1;
            }

            else if ((x1>x2)&&(y1<y2))

            {
                super.x = x2;
                super.y = y1;
            }

            else if ((x1>x2)&&(y1>y2))
            {
                super.x = x2;
                super.y = y2;
            }
            painter.fillRect(super.x,super.y,super.width,super.height);

            }

        }

    }






