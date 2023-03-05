// Class of ProtoCell - all cells in our program are instances of this class

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ProtoCell extends Rectangle{
    Image bacteria = new ImageIcon("src/bacteria2-2.png").getImage();
    public int cellNumberInGen; // Number of this cell in generation
    public double result;
    public int[][] genom = new int[3][8]; // never changes for this cell. cellVelicity and initial directions goes from here.

    public Direction cellDirection = new Direction();
    public Direction cellbornDirection = new Direction();
    public int cellbornVelocity;

    private int cellVelocity; // cells Velocity never changes for this cell
    private double x,y; // coordinates

    ProtoCell(int Number, int Velocity, double x, double y, int dx, int dy)
    {
        cellNumberInGen = Number;
        cellVelocity = Velocity;
        this.x = x;
        this.y = y;
        cellDirection.x = dx;
        cellDirection.y = dy;
        if (Math.abs(cellDirection.x)<1)
            cellDirection.x=1;
        if (Math.abs(cellDirection.y)<1)
            cellDirection.y=1;


    }

    void paint_me(){
        System.out.println("Imagine that cell "+cellNumberInGen+" is painted");
    } // painting function

    public void cellInfo(){
        System.out.println("Cell number "+cellNumberInGen+", Cell Velocity "+cellVelocity+", cell x "+x+", cell y "+y+", Cell dx "+
                cellDirection.x+", Cell dy "+cellDirection.y+" cell result is "+result);
    }
    public double getX(){
        return x;
    }

    public double getY()
    {
        return y;
    }

    public int getCellVelocity()
    {
        return cellVelocity;
    }

    public int[][] getGenom()
    {
        return genom;
    }


    void step()
    {
            x= (int) (x+cellVelocity*cellDirection.x);
            y= (int) (y+cellVelocity*cellDirection.y);

            // if we collide with walls
            if (x> Main.w-500)
            {
                cellDirection.x *= -1;
            }
            if (x<20)
            {
                cellDirection.x *= -1;

            }

            if (y>Main.h-200)
                cellDirection.y *= -1;

            if (y<20)
            {
                cellDirection.y *= -1;

            }
            // if we collide with barriers ... I don't know, if it will be ok with barrier reflections, i should change this part

        for (int j=0; j<Main.territoryBarriers.size();j++) {

            double distance = 100;
            double x0 = this.x;
            double y0 = this.y;

            Main.territoryBarriers.get(j).normCalc();
            int x1, y1, x2, y2;
            x1 = Main.territoryBarriers.get(j).x1;
            x2 = Main.territoryBarriers.get(j).x2;
            y1 = Main.territoryBarriers.get(j).y1;
            y2 = Main.territoryBarriers.get(j).y2;

            double distance_cell_barrier_start, distance_cell_barrier_end, barrier_length;

            barrier_length = Math.hypot(x2 - x1, y2 - y1);
            distance_cell_barrier_start = Math.hypot(x1 - x0, y1 - y0);
            distance_cell_barrier_end = Math.hypot(x2 - x0, y2 - y0);

            if ((Main.territoryBarriers.get(j).contains(this))&&(Main.territoryBarriers.get(j).barrierDrawingMode==2))
            {


                if((super.getCenterX()<Main.territoryBarriers.get(j).getCenterX())&&(super.getCenterY()<Main.territoryBarriers.get(j).getCenterY()))
                {
                    if (Math.abs((super.x) - (Main.territoryBarriers.get(j).x)) < (Math.abs((super.y) - (Main.territoryBarriers.get(j).y)))) {
                        cellDirection.x *= -1;
                        x = (int) (Main.territoryBarriers.get(j).getMinX() - 20);
                    }

                    else if (Math.abs((super.y) - (Main.territoryBarriers.get(j).y)) < (Math.abs((super.x) - (Main.territoryBarriers.get(j).x)))) {
                        cellDirection.y *= -1;
                        y=(int) (Main.territoryBarriers.get(j).getMinY()-20);
                    }
                    else
                    {
                        cellDirection.x *= -1;
                        x = (int) (Main.territoryBarriers.get(j).getMinX() - 20);
                        cellDirection.y *= -1;
                        y=(int) (Main.territoryBarriers.get(j).getMinY()-20);
                    }

                }

                else if((super.getCenterX()<Main.territoryBarriers.get(j).getCenterX())&&(super.getCenterY()>Main.territoryBarriers.get(j).getCenterY()))
                {
                    if (Math.abs((super.x) - (Main.territoryBarriers.get(j).x)) < (Math.abs((super.y+super.height) -
                            (Main.territoryBarriers.get(j).y+Main.territoryBarriers.get(j).height))))
                    {
                        cellDirection.x *= -1;
                        x = (int) (Main.territoryBarriers.get(j).getMinX() - 20);
                    }

                    else if (Math.abs((super.y+super.height) - (Main.territoryBarriers.get(j).y+Main.territoryBarriers.get(j).height))
                            < (Math.abs((super.x) - (Main.territoryBarriers.get(j).x)))) {
                        cellDirection.y *= -1;
                        y=(int) (Main.territoryBarriers.get(j).getMaxY()+20);
                    }
                    else
                    {
                        cellDirection.x *= -1;
                        x = (int) (Main.territoryBarriers.get(j).getMinX() - 20);
                        cellDirection.y *= -1;
                        y=(int) (Main.territoryBarriers.get(j).getMaxY()+20);
                    }

                }

                else if((super.getCenterX()>Main.territoryBarriers.get(j).getCenterX())&&(super.getCenterY()<Main.territoryBarriers.get(j).getCenterY()))
                {
                    if (Math.abs((super.x+super.width) - (Main.territoryBarriers.get(j).x+Main.territoryBarriers.get(j).width)) < Math.abs((super.y) -
                            (Main.territoryBarriers.get(j).y)))
                    {
                        cellDirection.x *= -1;
                        x = (int) (Main.territoryBarriers.get(j).getMaxX() + 20);
                    }

                    else if (Math.abs((super.y)-(Main.territoryBarriers.get(j).y)) < Math.abs((super.x+super.width) -
                            (Main.territoryBarriers.get(j).x+Main.territoryBarriers.get(j).width)))
                    {
                        cellDirection.y *= -1;
                        y=(int) (Main.territoryBarriers.get(j).getMinY()-20);
                    }
                    else
                    {
                        cellDirection.x *= -1;
                        x = (int) (Main.territoryBarriers.get(j).getMaxX() + 20);
                        cellDirection.y *= -1;
                        y=(int) (Main.territoryBarriers.get(j).getMinY()-20);
                    }

                }

                else if((super.getCenterX()<Main.territoryBarriers.get(j).getCenterX())&&(super.getCenterY()<Main.territoryBarriers.get(j).getCenterY()))
                {
                    if (Math.abs((super.x+super.width) - (Main.territoryBarriers.get(j).x+Main.territoryBarriers.get(j).width)) < Math.abs((super.y+super.height) -
                            (Main.territoryBarriers.get(j).y+Main.territoryBarriers.get(j).height)))
                    {
                        cellDirection.x *= -1;
                        x = (int) (Main.territoryBarriers.get(j).getMaxX() + 20);
                    }

                    else if (Math.abs((super.x+super.width) - (Main.territoryBarriers.get(j).x+Main.territoryBarriers.get(j).width)) > Math.abs((super.y+super.height) -
                            (Main.territoryBarriers.get(j).y+Main.territoryBarriers.get(j).height)))
                    {
                        cellDirection.y *= -1;
                        y=(int) (Main.territoryBarriers.get(j).getMaxY()+20);
                    }
                    else
                    {
                        cellDirection.x *= -1;
                        x = (int) (Main.territoryBarriers.get(j).getMaxX() + 20);
                        cellDirection.y *= -1;
                        y=(int) (Main.territoryBarriers.get(j).getMaxY()+20);
                    }
                }

                System.out.println("New feature testing...");
            }

            else if ((super.intersects(Main.territoryBarriers.get(j)))&&(Main.territoryBarriers.get(j).barrierDrawingMode==2))
            {
                System.out.println("cell N "+cellNumberInGen+" intersects box N "+j);


                    if ((super.intersection(Main.territoryBarriers.get(j)).x==Main.territoryBarriers.get(j).x)||
                            (super.intersection(Main.territoryBarriers.get(j)).x+super.intersection(Main.territoryBarriers.get(j)).width)==
                                    (Main.territoryBarriers.get(j).x+Main.territoryBarriers.get(j).width))

                    {
                        cellDirection.x *= -1;

                        if(super.getCenterX()<Main.territoryBarriers.get(j).getCenterX())
                            x=(int) (Main.territoryBarriers.get(j).getMinX()-20);
                        else if(super.getCenterX()>Main.territoryBarriers.get(j).getCenterX())
                            x=(int) (Main.territoryBarriers.get(j).getMaxX()+20);
                    }

                if ((super.intersection(Main.territoryBarriers.get(j)).y==Main.territoryBarriers.get(j).y)||
                        (super.intersection(Main.territoryBarriers.get(j)).y+super.intersection(Main.territoryBarriers.get(j)).height)==
                                (Main.territoryBarriers.get(j).y+Main.territoryBarriers.get(j).height))
                {
                    cellDirection.y *= -1;

                    if(super.getCenterY()<Main.territoryBarriers.get(j).getCenterY())
                        y=(int) (Main.territoryBarriers.get(j).getMinY()-20);
                    else if(super.getCenterY()>Main.territoryBarriers.get(j).getCenterY())
                        y=(int) (Main.territoryBarriers.get(j).getMaxY()+20);
                }

//!!!!!!!!!!SHOULD DO SOMETHING HERE!!!!!!!!!!!!!!!!!!!!!
                    System.out.println("x is "+super.intersection(Main.territoryBarriers.get(j)).x+
                            "y is "+super.intersection(Main.territoryBarriers.get(j)).y);
            }
            // refactor here - formula
            else if ((Math.abs(barrier_length - (distance_cell_barrier_start + distance_cell_barrier_end)) < 5) && (Main.territoryBarriers.get(j).x1 == Main.territoryBarriers.get(j).x2)) {
                cellDirection.x *= -1;

                System.out.println("Work Extranew part x1 = x2, cell № " + cellNumberInGen);
            } else if ((Math.abs(barrier_length - (distance_cell_barrier_start + distance_cell_barrier_end)) < 5) && (Main.territoryBarriers.get(j).y1 == Main.territoryBarriers.get(j).y2)) {
                cellDirection.y *= -1;

                System.out.println("Work Extranew part y1 = y2, cell № " + cellNumberInGen);
            }

            else if(Math.abs(barrier_length-(distance_cell_barrier_start+distance_cell_barrier_end))<5)
            {

                double r, rx, ry;

                if (Math.abs(Main.territoryBarriers.get(j).nx)>0.95)
                {
                    cellDirection.y*=-1;

                    x=(int) (x+cellDirection.x*2);
                    y=(int) (y+cellDirection.y*2);
                    System.out.println("Work old part,nx>0.95, cell № "+cellNumberInGen);

                }

                else if (Math.abs(Main.territoryBarriers.get(j).ny)>0.95)
                {
                    cellDirection.x*=-1;

                    x=(int) (x+cellDirection.x*2);
                    y=(int) (y+cellDirection.y*2);
                    System.out.println("Work old part,ny>0.95, cell № "+cellNumberInGen);

                }

                else
                {
                    r = 2*(-cellDirection.x*Main.territoryBarriers.get(j).nx+cellDirection.y*Main.territoryBarriers.get(j).ny); // this is dot product
                    rx = -r*Main.territoryBarriers.get(j).nx-cellDirection.x;
                    ry = r*Main.territoryBarriers.get(j).ny-cellDirection.y;

                    if((rx==0)&&(ry==0))

                    {

                        System.out.printf("Karaul!!!!\n");
                        cellDirection.x=-Main.territoryBarriers.get(j).nx;
                        cellDirection.y=Main.territoryBarriers.get(j).ny;
                        cellVelocity=3;
                        x=x+20;
                        y=y+20;

                    }

                    else {
                        cellDirection.x=rx;
                        cellDirection.y=ry;
                        System.out.println("Work old part, cell № "+cellNumberInGen);
                    }
                }

            }}

    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setVelocity(int new_Velocity)
    {
        cellVelocity = new_Velocity;
        if (cellVelocity<1)
            cellVelocity = 1;
    }

    public void setVelocityFromGenom()
    {
        int velocity_from=0;

        for (int j=7; j>0; j--)
            velocity_from+=genom[0][j]*Math.pow(2,7-j);

        cellVelocity = velocity_from;

        if (cellVelocity>5)
            cellVelocity=5;

        if (cellVelocity==0)
            cellVelocity = 1;
    }

    public void setDirectionFromGenom()
    {
        int[] vect1;
        vect1 = new int[]{0, 0, 0, 0, 0, 0, 0, 0};

        int direction_from=0;

        for (int i=1; i<3; i++)
        {
            direction_from=0;

            if (genom[i][0]==0)
                for (int j=7; j>0; j--)
                    direction_from+=genom[i][j]*Math.pow(2,7-j);

            else if (genom[i][0]==1)

            {
                for (int k=0; k<8; k++)
                    vect1[k] = 0;  // every time we will zeroing our temp vector

                for (int k=0; k<8; k++)
                    vect1[k]=genom[i][k];

                int goon=0;
                for (int i2=7; i2>1; i2--)
                {
                    if (i2==7)
                    {
                        if (vect1[i2]==0)
                        {vect1[i2]=1;
                            goon=1;
                        }
                        else if(vect1[i2]==1)
                        {vect1[i2]=0;

                        }
                    }
                    else if(goon==1)
                    {
                        goon=0;
                        if (vect1[i2]==0)
                        {vect1[i2]=1;
                            goon=1;
                        }
                        else if(vect1[i2]==1)
                        {vect1[i2]=0;

                        }
                    }
                }

                for (int i3=1; i3<8; i3++)
                {
                    if (vect1[i3]==0)
                        vect1[i3]=1;
                    else if(vect1[i3]==1)
                        vect1[i3]=0;
                }

                for (int j=7; j>0; j--)
                    direction_from+=vect1[j]*Math.pow(2,7-j);

                direction_from=-direction_from;
            }

            if (i==1)
                cellDirection.x=direction_from;
            if (i==2)
                cellDirection.y=direction_from;

        }
// maybe revise this part... there was problems in winapi version in c++
        if (cellDirection.x>3)
            cellDirection.x=3;
        if (cellDirection.x<-3)
            cellDirection.x=-3;

        if (cellDirection.y>3)
            cellDirection.y=3;
        if (cellDirection.y<-3)
            cellDirection.y=-3;
    }

    public void setNumber(int newNumber)
    {
        cellNumberInGen = newNumber;
    }


    public void calculateResult() //calculate result after life
    {
        result = Math.pow(Math.pow((Target.x-x),2)+Math.pow((Target.y-y),2),0.5);
    }

    public void setGenomFromTwo(int[][] genom1, int[][] genom2, int point)
    // in this function we should make crossbreeding procedure with two parents and alternative NGen function
    {

        for (int i=0; i<3; i++)
        {
            for (int j=0; j<8; j++)

                if (j>=point)
                {
                    genom[i][j] = genom2[i][j];
                }
                else
                {
                    genom[i][j] = genom1[i][j];
                }
        }

    }

    public void setGenomRandomly()
    {
        int x=0;
        int sign=0;

        int[] vect1;
        vect1 = new int[]{0, 0, 0, 0, 0, 0, 0, 0}; //temporaty vector for storing results


        for (int i=0; i<3; i++)
        {
            for (int k=0; k<8; k++)
                vect1[k] = 0;  // every time we will zeroing our temp vector

            int j=8; // starting from last bit in vector
            if (i==0) // if it is first string - its a Velocity. Cant be negative
            {
                Random ran = new Random();
                x = ran.nextInt(6);
            }
            else    // else it's a dx or dy directions. Both can be positive or negative
            {
                Random ran = new Random();
                x = ran.nextInt(6)-ran.nextInt(6);
            }

            int result = 0;
            if (x<0)
                sign=1; // for additional code
            else
                sign=0;

            while(x!=0)
            {
                result=x%2;  // ostatok from dividing on two
                x = x/2;  // divide our x for next step
                vect1[j-1]=Math.abs(result); // put bit into vector
                j--; // next step
            }

            /*----------- this part is for negative numbers--------------------------*/
            if (sign==1)
            {
                // last bit shows negativity of number
                vect1[0] = 1;

                // starting from 1, make everything inverted
                for (int i2=1; i2<8; i2++)
                {
                    if (vect1[i2]==0)
                        vect1[i2]=1;
                    else if(vect1[i2]==1)
                        vect1[i2]=0;
                }

                // part that adding one
                int goon=0;
                for (int i3=7; i3>1; i3--)
                {
                    if (i3==7)
                    {
                        if (vect1[i3]==0)
                        {vect1[i3]=1;}
                        else if(vect1[i3]==1)
                        {vect1[i3]=0;
                            goon=1;
                        }
                    }
                    else if(goon==1)
                    {
                        goon=0;
                        if (vect1[i3]==0)
                        {vect1[i3]=1;}
                        else if(vect1[i3]==1)
                        {vect1[i3]=0;
                            goon=1;
                        }
                    }

                }
            }

            /*---------------------------------- end of negative numbers part--------------------------------*/

            // put information from vector to genom
            for(int j1=0; j1<8; j1++)
                genom[i][j1] = vect1[j1];

        }

// show genom result in binary. We should uncoment this part if we need extra information about genom

        /*----------------this part shows convertion from binary vector into decimal with sign----------------*/

        int back_x=0;
        for (int i=0; i<3; i++)
        {
            if (genom[i][0]==0)
                for (int j=7; j>0; j--)
                    back_x+=genom[i][j]*Math.pow(2,7-j);

            else if (genom[i][0]==1)

            {
                for (int k=0; k<8; k++)
                    vect1[k] = 0;  // every time we will zeroing our temp vector

                for (int k=0; k<8; k++)
                    vect1[k]=genom[i][k];


                int goon=0;
                for (int i2=7; i2>1; i2--)
                {
                    if (i2==7)
                    {
                        if (vect1[i2]==0)
                        {vect1[i2]=1;
                            goon=1;
                        }
                        else if(vect1[i2]==1)
                        {vect1[i2]=0;

                        }
                    }
                    else if(goon==1)
                    {
                        goon=0;
                        if (vect1[i]==0)
                        {vect1[i]=1;
                            goon=1;
                        }
                        else if(vect1[i]==1)
                        {vect1[i]=0;

                        }
                    }
                }

                for (int i3=1; i3<8; i3++)
                {
                    if (vect1[i3]==0)
                        vect1[i3]=1;
                    else if(vect1[i3]==1)
                        vect1[i3]=0;
                }


                for (int j=7; j>0; j--)
                    back_x+=vect1[j]*Math.pow(2,7-j);

                back_x=-back_x;
            }

            back_x=0;
        }

    }


        void paint_me(Graphics painter)

        {
            super.x = (int) x;
            super.y = (int) y;
            super.width=10;
            super.height=10;
            Color color = new Color(20,200,1);
       //     painter.drawImage(bacteria,super.x,super.y,super.width+20,super.height+20,null); // for an image
            painter.fillRect(super.x,super.y,super.width,super.height);
            painter.setColor(Color.BLACK);
            painter.drawString(String.valueOf(cellNumberInGen), (int) x, (int) y);
            painter.setColor(color);

        }



}