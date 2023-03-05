import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

// here is my own alghorithm for intersections detection.

/*
I should add:
    6. UI like in C++ version +/- little differs

        6.5. ability to change number of cells in generation (maybe up/down field) -
        6.8. label near barriers combo box
        6.9. button to reset everything -

    7. Add normal comments -
    8. Add menu and about author part - in process +
 */

public class Main extends JComponent implements ActionListener, MouseListener { // Important thing - Main should extends JComponent to get drawing ability

    public static int w = 1300;
    public static int h = 1000;
    public static int prg_mode = 4;
    public static int select_mode = 1;
    public static int b = 0;
    public static int barr_drawing_mode = 2;
    public static int genToSkip = 50;


    // 0 - put target mode
    // 1 - put barrier mode
    // 2 - sim mode
    // 4 - initial state unknown

    public static void GenerateZeroGen()
    {
        Random ran = new Random();
        for (int i=0; i<amount; i++)
        {
            myCells.add(new ProtoCell(i, 1, 100,100,1,1));

            myCells.get(i).setX(ran.nextInt(200)+20);
            myCells.get(i).setY(ran.nextInt(200)+20);
            myCells.get(i).setGenomRandomly();
            myCells.get(i).setVelocityFromGenom();
            myCells.get(i).setDirectionFromGenom();
            myCells.get(i).cellbornVelocity = myCells.get(i).getCellVelocity();
            myCells.get(i).cellbornDirection = myCells.get(i).cellDirection;
            infoField.setText("Generation: "+generation);

        }
    }

    public static void StepRoutine()
    {
        // for skipping itterations - i should put it in another function and itterate it!
        //for cells in vector

        if (!myCells.isEmpty())
        {
            for (int i=0; i<myCells.size(); i++)
            {
                myCells.get(i).step();
            }
            // repaint everything in window
        }

        if(mutual_flag)
        {
            for (int i=0; i<myCells.size(); i++)
                for (int j=0; j<myCells.size(); j++)
                    if (i!=j)
                    {
                        if ((Math.abs(myCells.get(i).getX()-myCells.get(j).getX())<10)&&((Math.abs(myCells.get(i).getY()-myCells.get(j).getY()))<10))

                        {
                            // you can switch off it to make cells with nobody
                            // if we delete this - results are better
                            myCells.get(i).cellDirection.x=-myCells.get(i).cellDirection.x;     // I should add in UI ability to on/off this part
                            myCells.get(i).cellDirection.y=-myCells.get(i).cellDirection.y;     //

                        }
                    }

        }

        step_in_gen++;

      }


    static ArrayList<ProtoCell> myCells = new ArrayList<>(); // in this array list there will be current generation
    static ArrayList<ProtoCell> bestCells = new ArrayList<>(); // best cells after selection

    static ArrayList<Barrier> territoryBarriers = new ArrayList<>();



    static int amount = 20;
    static int generation=0; // number of generation
    public static JFrame f = new JFrame("Genetics KP version 2");
    public static JButton moveButton = new JButton("Make'm move");
    public static JButton setSButton = new JButton("Set steps per life");
    public static JButton setNOGtoskipButton = new JButton("Set number of gens to skip");
    public static JButton killemButton = new JButton("Kill'em all");
    public static JButton genButton = new JButton("Generate'em");
    public static JButton quicktButton = new JButton("Quick Turn");
    public static JButton removeAllBarriersButton = new JButton("Remove all barriers");
    public static JCheckBox mutualColl = new JCheckBox("Mutual collisions");

    public static JTextField field = new JTextField();
    public static JTextField infoField = new JTextField();

    public static JRadioButton putTargetm = new JRadioButton("put Target mode");
    public static JRadioButton putBarrierm = new JRadioButton("put Barrier mode");
    public static JRadioButton simm = new JRadioButton("simulation mode");

//    public static JRadioButton stdOnePtCross = new JRadioButton("Standard one point cross");
//    public static JRadioButton primitiveVar = new JRadioButton("Primitive var");
    public static JMenuBar menuBar = new JMenuBar();
    public static JMenu aboutMenu = new JMenu("О программе");
    public static JMenuItem help = new JMenuItem("Справка");
    public static JMenuItem author = new JMenuItem("О разработчике");

    static boolean mutual_flag = false;
    static int step_in_gen = 0;

    // Next method is for UI Creating
    private static void UI()
    {

        JPanel panel = new JPanel();
        JLabel label1 = new JLabel();
        JComboBox barriersVar = new JComboBox();

        barriersVar.addItem("Box");
        barriersVar.addItem("Simple line");
        barriersVar.addItem("Ortho line");


        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "1. Firstly you are in 'put target mode' - put it on white board\n" +
                                "2. You can select another regime with radiobutton on the right\n" +
                                "3. If barrier mode selected - put barriers on the screen (barriers type also can be selected)\n" +
                                "4. Make'em move and Quick turn aviable only in simulation mode\n" +
                                "5. Manual way looks like Make'em move -> Kill'em all -> Generate'em -> repeat \n" +
                                 "6. Quick Turn for time keeping - skip watching cells animation, skip N generations to gain quick result\n"+
                                 "7. Use edit box for tuning steps per life or number of gens to skip \n"+
                                "8. Mutual collisions flag - if you want cells to collide each other \n"+
                                "9. Remove all barriers - button for cleaning up board from barriers \n",
                        "LiteHelper",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        author.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null," Author: Vagapov N.R.\n email: nik5140@mail.ru",
                        "About author", JOptionPane.PLAIN_MESSAGE);
            }
        });

        menuBar.add(aboutMenu);
        aboutMenu.add(help);
        aboutMenu.add(author);
        f.setJMenuBar(menuBar);



        f.setSize(w,h);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // for stopping app after closing the window
        f.getContentPane().add(new Main()); // important thing for painting
        f.setVisible(true);
        panel.setSize(f.getSize().width-450,f.getSize().height-100);
        panel.setVisible(true);
        //     panel.setBackground(Color.BLACK);
        panel.setLocation(f.getLocation().x+25,f.getLocation().y+25);
        f.add(panel);
        f.setLayout(null);

        f.add(infoField);
        infoField.setSize(250,25);
        infoField.setLocation(880,50);
        infoField.setVisible(true);
        infoField.setEditable(false);


        infoField.setFont(infoField.getFont().deriveFont(16f));




        f.add(moveButton);
        moveButton.setSize(130,25);
        moveButton.setLocation(880,100);
        moveButton.setVisible(true);

        f.add(putTargetm);
        putTargetm.setSize(230,25);
        putTargetm.setLocation(1050,100);
        putTargetm.setVisible(true);

        f.add(killemButton);
        killemButton.setSize(130,25);
        killemButton.setLocation(880,150);
        killemButton.setVisible(true);

        f.add(putBarrierm);
        putBarrierm.setSize(230,25);
        putBarrierm.setLocation(1050,125);
        putBarrierm.setVisible(true);

        f.add(genButton);
        genButton.setSize(130,25);
        genButton.setLocation(880,200);
        genButton.setVisible(true);

        f.add(simm);
        simm.setSize(230,25);
        simm.setLocation(1050,150);
        simm.setVisible(true);

        f.add(field);
        field.setSize(130,25);
        field.setLocation(880,300);
        field.setVisible(true);

        f.add(quicktButton);
        quicktButton.setSize(130,25);
        quicktButton.setLocation(880,250);
        quicktButton.setVisible(true);

        f.add(setSButton);
        setSButton.setSize(130,25);
        setSButton.setLocation(880,350);
        setSButton.setVisible(true);


        f.add(setNOGtoskipButton);
        setNOGtoskipButton.setSize(200,25);
        setNOGtoskipButton.setLocation(880,390); // edited
        setNOGtoskipButton.setVisible(true);

        ButtonGroup modesGroup = new ButtonGroup();

        modesGroup.add(putTargetm);
        modesGroup.add(putBarrierm);
        modesGroup.add(simm);

 //       ButtonGroup alghGroup = new ButtonGroup();

//        alghGroup.add(stdOnePtCross);
//        alghGroup.add(primitiveVar);

   /*     f.add(stdOnePtCross);
        stdOnePtCross.setSize(230,25);
        stdOnePtCross.setLocation(880,450);
        stdOnePtCross.setVisible(true);

        f.add(primitiveVar);
        primitiveVar.setSize(230,25);
        primitiveVar.setLocation(880,475);
        primitiveVar.setVisible(true);
*/
        f.add(mutualColl);
        mutualColl.setSize(230,25);
        mutualColl.setLocation(880,550);
        mutualColl.setVisible(true);


        f.add(removeAllBarriersButton);
        removeAllBarriersButton.setSize(230,25);
        removeAllBarriersButton.setLocation(880,600);
        removeAllBarriersButton.setVisible(true);

        removeAllBarriersButton.setForeground(Color.red);


        f.add(barriersVar);
        barriersVar.setVisible(true);
        barriersVar.setEditable(true);
        barriersVar.setLocation(880,435); // edited
        barriersVar.setSize(230,28);
        barriersVar.setLightWeightPopupEnabled(true);



        putTargetm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prg_mode = 0;
            }
        });

        putBarrierm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prg_mode = 1;
            }
        });

        simm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prg_mode = 2;
            }
        });

        putTargetm.setSelected(true);
        prg_mode = 0;

        barriersVar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (barriersVar.getSelectedItem()=="Simple line")
                    barr_drawing_mode = 0;
                else if(barriersVar.getSelectedItem()=="Ortho line")
                    barr_drawing_mode = 1;
                else if(barriersVar.getSelectedItem()=="Box")
                    barr_drawing_mode = 2;


                System.out.println("Barr drawing mode is "+barr_drawing_mode);
            }
        });

 /*       stdOnePtCross.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                select_mode = 1;
            }
        });
*/
   /*     primitiveVar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                select_mode = 0;
            }
        });
*/
//        stdOnePtCross.setSelected(true);
        select_mode = 1;

        f.add(label1);
        label1.setSize(230,25);
        label1.setLocation(1050,350);
        label1.setVisible(true);
        label1.setText("Steps til end:"+stepsUntilGenEnd);
// new part for digits only in textfield
        PlainDocument doc = new PlainDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int off, String str, AttributeSet attr)
                throws BadLocationException
            {
                fb.insertString(off, str.replaceAll("\\D++",""), attr);
            }
            @Override
            public void replace (FilterBypass fb, int off, int len, String str, AttributeSet attr)
                throws BadLocationException
            {
                fb.replace(off, len, str.replaceAll("\\D++",""), attr);
            }


        });
        field.setDocument(doc);
// end of new part
        setSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                step_in_gen = 0;
                stepsUntilGenEnd = Integer.parseInt(field.getText());
                label1.setText("Steps til end:"+stepsUntilGenEnd);
            }
        });


        setNOGtoskipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genToSkip = Integer.parseInt(field.getText());
                label1.setText(genToSkip+" generations will be skipped");
            }
        });

        removeAllBarriersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                territoryBarriers.clear();
                f.repaint();
                b=0;
                Barrier.pointsEntered=0;

            }
        });


    }
    static int stepsUntilGenEnd=40;

    public static void main(String[] args) {
        Timer timer1 = new Timer(200,null);
        System.out.println("Starting the KP...");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UI();
            }
        });


        ActionListener performer = new ActionListener() {// how Timers work in Java - for example p.48 Makarov E.M.

        private int timerTick=0;
            @Override
            public void actionPerformed(ActionEvent e) {
                StepRoutine();
                f.repaint();
                timerTick++;
                if (timerTick>=stepsUntilGenEnd)
                {
                    timer1.stop();
                    timerTick = 0;
                    System.out.println("Timer delay is "+timer1.getDelay());
                   //     JOptionPane.showMessageDialog(null, "Turn ended");
                }
            }
        };



        moveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    if (prg_mode==2) // if we are in sim mode
                    {
                        // without next for block there will be problem acceleration every generation
                        // because there will be a lot of timers that will run StepRoutine(); method...
                        for (ActionListener listener : timer1.getActionListeners()) {
                            timer1.removeActionListener(listener);
                        }

                        timer1.addActionListener(performer);
                        //  timer1.setRepeats(true);
                        timer1.start();
                        step_in_gen =0;

                    }
                    else
                        JOptionPane.showMessageDialog(null, "You should switch into sim mode");


                    }
        });

        killemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KillGen();

            }
        });

        genButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(select_mode==0)
                    NGen();
                else if(select_mode==1)
                    NGen2();

                infoField.setText("Generation: "+generation);
               //
            }
        });


        quicktButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(prg_mode==2)
                    AlotOfSteps(genToSkip);
            }
        });

        GenerateZeroGen(); // GenerateFirstGen and another methods outside main method should be static.

                for (int i=0; i<amount; i++)
                {
                    myCells.get(i).cellInfo();
                }

        f.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {


            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if((prg_mode==0)&&(e.getX()>16)&&(e.getX()<814)&&(e.getY()>66)&&(e.getY()<854))
                {
                    //    Point p = MouseInfo.getPointerInfo().getLocation();


                    Target.exist=true;
                    Target.x = e.getX()-7;  // new edition
                    Target.y = e.getY()-52; // new edition
                    System.out.println("Target x is "+Target.x+", Target y is"+ Target.y);

                    f.repaint();
                }

                else if ((prg_mode==1)&&(e.getX()>16)&&(e.getX()<814)&&(e.getY()>66)&&(e.getY()<854))
                {


                    if (Barrier.pointsEntered==0)
                    {
                        Barrier tmpBarrier= new Barrier(0,0,0,0);

                        territoryBarriers.add(tmpBarrier);


                        System.out.printf("Cursor position is x = %d y = %d\n", e.getX(), e.getY());
                        territoryBarriers.get(b).x1=e.getX()-7; // new edition
                        territoryBarriers.get(b).y1=e.getY()-52; // new edition
                        Barrier.pointsEntered+=1;

                        //  break; // maybe here will be problem
                    }

                    else if (Barrier.pointsEntered==1)
                    {

                        System.out.printf("Cursor position is x = %d y = %d\n", e.getX(), e.getY());
                        territoryBarriers.get(b).x2=e.getX()-7; // new edition
                        territoryBarriers.get(b).y2=e.getY()-52; // new edition
                        Barrier.pointsEntered+=1;

                        b++;

                        f.repaint();
                        Barrier.pointsEntered=0;


                    }

                }

                else if (prg_mode!=0)
                    JOptionPane.showMessageDialog(null, "If you want to put target, switch radio " +
                            "button to target mode and try to be inside white box");

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

                mutualColl.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(mutualColl.isSelected())
                            mutual_flag = true;
                        else if(!mutualColl.isSelected())
                            mutual_flag = false;
                    }
                });

            }

    public static void KillGen() // this method is for destroying generation of cells and selecting amount/4 sorted best cells
    {
        if(myCells.size()>0)
        {
            // i should understand how to destroy objects from memory
            for (int i=0; i<myCells.size(); i++)
            {
                myCells.get(i).calculateResult();

            }

            // here we are selecting best 5 cells from generation
            int i,j;
            ProtoCell tempCell = new ProtoCell(1, 1, 100,100,1,1); // make temporary cell

            // bubble sorting is very slow, especially if we want amount of 150 for example
            // here is sorting
            for (i=0; i<myCells.size(); i++)
            {
                for (j=myCells.size()-1; j>i; j--)
                    if (myCells.get(j-1).result>myCells.get(j).result)
                    {
                        //       printf("in this block\n");
                        tempCell=myCells.get(j-1);
                        myCells.set(j-1,myCells.get(j));
                        myCells.set(j,tempCell);
                    }


                //   printf("going here");
                for (int k=0; k<myCells.size(); k++)
                    myCells.get(k).cellInfo();

            }

            bestCells.clear();
            for (int s=0; s<amount/4; s++)
            {
                bestCells.add(myCells.get(s));
                bestCells.get(s).cellInfo();
            }

            myCells.clear();



            System.out.println(" And best 5 cells are:");
            for (i=0; i<bestCells.size(); i++)
            {
                bestCells.get(i).cellInfo();
            }

            generation++;
            f.repaint();

        }
    }


    public static void NGen()
    {
        Barrier.pointsEntered = 0;
        Random ran = new Random();
        // generating new generation
        // its not a genetic alghorithm its simply an improvisation

        for (int i=0; i<amount; i++)
        {
            ProtoCell cell = new ProtoCell(1, 1, 100,100,1,1);
            myCells.add(cell);
            // tried operator .insert - no way...

            myCells.get(i).setX(ran.nextInt(200)+20);
            myCells.get(i).setY(ran.nextInt(200)+20);

            if (i<amount/4)
            {
                //first 5 cells - from best cells
                myCells.get(i).setVelocity(bestCells.get(i).cellbornVelocity);
                myCells.get(i).cellDirection = bestCells.get(i).cellbornDirection;
            }
            else if (i<amount-4)
            {
                // next cells exept 4 as worst one from the best
                myCells.get(i).setVelocity(bestCells.get((amount/4)-1).cellbornVelocity);
                myCells.get(i).cellDirection = bestCells.get((amount/4)-1).cellbornDirection;
            }
            else {
                // last 4 cells - random velocity and directions

                myCells.get(i).setVelocity(ran.nextInt(5));
                myCells.get(i).cellDirection.x=ran.nextInt(6)-ran.nextInt(6);
                myCells.get(i).cellDirection.y=ran.nextInt(6)-ran.nextInt(6);
            }

            myCells.get(i).setNumber(i);
            myCells.get(i).cellbornVelocity = myCells.get(i).getCellVelocity();
            myCells.get(i).cellbornDirection =  myCells.get(i).cellbornDirection;


        }
        f.repaint();
        System.out.println("Attention!!!! New generation № "+generation+" is here!");
        for (int i=0; i<amount; i++)
        {
            myCells.get(i).cellInfo();
        }

    }

    public static void NGen2()
    {

        Random ran = new Random();
        for (int i=0; i<amount; i++)
        {
            ProtoCell cell = new ProtoCell(1, 1, 100,100,1,1);
            myCells.add(cell);
            // tried operator .insert - no way...

            myCells.get(i).setX(ran.nextInt(200)+20);
            myCells.get(i).setY(ran.nextInt(200)+20);

            if (i<(amount-4))
            {
                //first 5 cells - from best cells

                myCells.get(i).setGenomFromTwo(bestCells.get(ran.nextInt(5)).genom,
                        bestCells.get(ran.nextInt(5)).genom,
                        ran.nextInt(8));
                myCells.get(i).setVelocityFromGenom();
                myCells.get(i).setDirectionFromGenom();
                                   }

            else {
                // last 4 cells - random velocity and directions

                myCells.get(i).setGenomRandomly();
                myCells.get(i).setVelocityFromGenom();
                myCells.get(i).setDirectionFromGenom();

            }

            myCells.get(i).setNumber(i);
            myCells.get(i).cellbornVelocity = myCells.get(i).getCellVelocity();
            myCells.get(i).cellbornDirection = myCells.get(i).cellDirection;

        }
        for (int i=0; i<amount; i++)
        {
            System.out.println("Cell Velocity of cell "+
                    myCells.get(i).cellNumberInGen+
                    " is "+myCells.get(i).getCellVelocity()+
                    " and dx is "+ myCells.get(i).cellDirection.x+
                    " and dy is "+ myCells.get(i).cellDirection.y);
        }

        System.out.println("hello you");

        f.repaint();

        System.out.println("Attention!!!! New generation № "+generation+" is here!");
        for (int i=0; i<amount; i++)
        {
            myCells.get(i).cellInfo();
        }

    }



    public void paint(Graphics painter)
    {
        Color color = new Color(20,200,1);
        Color bkGroundColor;

        bkGroundColor = Color.WHITE;
        painter.setColor(bkGroundColor);
        painter.fillRect(10,10, w-500,h-200);

        painter.setColor(color);

        // if there is at least one cell in myCells List - we should draw it
        if (!myCells.isEmpty())
        {
            for (int i = 0; i<amount; i++)
            {
                myCells.get(i).paint_me(painter);

            }
        }

        if (Target.exist)
        {
            for (int i=0; i<5; i++)
            {
                painter.setColor(Color.BLACK);
                painter.drawRect(Target.x,Target.y,1,1);
                painter.drawRect(Target.x+i,Target.y+i,1,1);
                painter.drawRect(Target.x-i,Target.y-i,1,1);
                painter.drawRect(Target.x+i,Target.y-i,1,1);
                painter.drawRect(Target.x-i,Target.y+i,1,1);

            }
        }

        if (!territoryBarriers.isEmpty())
        {
            painter.setColor(Color.BLACK);
            for (int i=0; i<territoryBarriers.size(); i++)

            {
               territoryBarriers.get(i).paint_me(painter);
                //   InvalidateRect(hwnd,0,TRUE);
            }

        }


        // something like this we should do for target and for
    }

    static void AlotOfSteps(int N) {
        infoField.setText("Waiting...");
        infoField.repaint();
      //  f.repaint();

        JOptionPane.showMessageDialog(null, "Starting skipping generations ...");

        for (int i = 0; i < N; i++) {
            step_in_gen = 0;
            for (int j = 0; j < stepsUntilGenEnd; j++)    // amount of steps also here
                StepRoutine();

            f.repaint();


            KillGen();

            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {}

            //   Sleep(10);          // I don't know, maybe decrease it

            if (select_mode == 0)
                NGen();
            else if (select_mode == 1)
                NGen2();
            else
                JOptionPane.showMessageDialog(null, "You should select selection mode!");

        }

        infoField.setText("Waiting ...");
        JOptionPane.showMessageDialog(null, "Finised! We skip "+genToSkip+" generations");
        infoField.setText("Generation: "+generation);
        System.out.println("Finished "+N+" generations");
    }

    @Override
    public void actionPerformed(ActionEvent e) { }

    public void mouseClicked(MouseEvent e) { }

    public void mousePressed(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }
}