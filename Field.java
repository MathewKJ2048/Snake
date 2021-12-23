import java.util.*;
import java.math.*;
public class Field
{
    private char field[][]; //
    private int r,c; //rows and columns
    private Snake snake;
    private char head,tail,body,food,empty; //stores characters used to represent given element
    private char UP,DOWN,LEFT,RIGHT,PAUSE_PLAY,QUIT;
    private int num_food;
    private boolean is_overlapping;
    private int moves;
    public Field(int r, int c)
    {
        this.field = new char[r][c];
        this.r = r;
        this.c = c;
        
        //graphics
        this.empty = '·';
        this.head = 'O';
        this.tail = '*';
        this.body = '#';
        this.food = '@';
        //controls
        this.UP = 'w';
        this.DOWN = 's';
        this.LEFT = 'a';
        this.RIGHT = 'd';
        this.PAUSE_PLAY = 'e';
        this.QUIT = 'q';
        
        this.num_food=0;
        this.moves = 0;
        clear_field();
        this.snake = new Snake(r/2,c/2);
        this.snake.grow_tail_at(r/2 + 1,c/2);
        update_snake();
        set_food();
        check_overlap();
    }
    //
    private void clear_field()
    {
        for(int i=0;i<this.r;i++)
        {
            for(int j=0;j<this.c;j++)
            {
                this.field[i][j] = this.empty;
            }
        }
    }
    private void clear_snake()
    {
        int n = this.snake.length();
        for(int t=0;t<n;t++)
        {
            this.field[(this.snake.get_i_of_segment(t)%this.r+r)%this.r][(this.snake.get_j_of_segment(t)%this.c+c)%this.c]=this.empty;
        }
    }
    private void update_snake()
    {
        int n = this.snake.length();
        for(int t=1;t<n-1;t++)
        {
            this.field[(this.snake.get_i_of_segment(t)%this.r+r)%this.r][(this.snake.get_j_of_segment(t)%this.c+c)%this.c] = this.body;
        }
        this.field[(this.snake.get_i_of_segment(n-1)%this.r+r)%this.r][(this.snake.get_j_of_segment(n-1)%this.c+c)%this.c] = this.tail;
        this.field[(this.snake.get_i_of_segment(0)%this.r+r)%this.r][(this.snake.get_j_of_segment(0)%this.c+c)%this.c] = this.head;
    } 
    private void set_food()
    {
        Random r = new Random();
        int i,j;
        do
        {
            i = r.nextInt(this.r);
            j = r.nextInt(this.c);
        }while(this.field[i][j] != this.empty);
        this.field[i][j] = this.food;
        this.num_food++;
    }
    private void check_overlap()
    {
        int n=this.snake.length();
        for(int t1=0;t1<n-1;t1++)
        {
            for(int t2=t1+1;t2<n;t2++)
            {
                if((this.snake.get_i_of_segment(t1)%r+r)%r == (this.snake.get_i_of_segment(t2)%r+r)%r 
                && (this.snake.get_j_of_segment(t1)%c+c)%c == (this.snake.get_j_of_segment(t2)%c+c)%c)
                {
                    this.is_overlapping = true;
                    return;
                }
            }
        }
        this.is_overlapping = false;
    }
    //
    public void move(char d)
    {
        if(d!='w'&&d!='s'&&d!='a'&&d!='d')return;
        
        int n = this.snake.length();
        int i_new = this.snake.get_i_of_segment(0);
        int j_new = this.snake.get_j_of_segment(0);
        int i_blocked = this.snake.get_i_of_segment(1);
        int j_blocked = this.snake.get_j_of_segment(1);
        int i_tail = this.snake.get_i_of_segment(n-1);
        int j_tail = this.snake.get_j_of_segment(n-1);
        if(d == 'w')
        {
            i_new--;
        }
        else if(d == 's')
        {
            i_new++;
        }
        else if(d == 'd')
        {
            j_new++;
        }
        else if(d == 'a')
        {
            j_new--;
        }
        if(!(i_new == i_blocked && j_new == j_blocked))
        {
            clear_snake();
            this.snake.move_head_to(i_new,j_new);
            this.moves++;
            if(this.field[(i_new%this.r+r)%this.r][(j_new%this.c+c)%this.c] == this.food)
            {
                this.snake.grow_tail_at(i_tail,j_tail);
                num_food--;
            }     
            update_snake();
            if(this.num_food==0)
            {
                set_food();
            }
            check_overlap();
        }
        
    }
    public boolean is_overlapping()
    {
        return this.is_overlapping;
    }
    public void print()
    {
        System.out.print('\f');
        System.out.println("length:"+this.snake.length());
        System.out.println("moves: "+this.moves);
        System.out.println();
        for(int i=0;i<this.r;i++)
        {
            for(int j=0;j<this.c;j++)
            {
                System.out.print(this.field[i][j]+" ");
            }
            System.out.println();
        }
    }
    public char get_direction()
    {
        return this.snake.get_direction();
    }
    //
    public char process(char ch)
    {
        if(ch == this.QUIT)return 'q';
        else if(ch == this.PAUSE_PLAY)return 'e';
        else if(ch == this.UP)return 'w';
        else if(ch == this.DOWN)return 's';
        else if(ch == this.LEFT)return 'a';
        else if(ch == this.RIGHT)return 'd';
        else return ' ';
    }
    static void main()
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter number of rows:");
        int r = sc.nextInt();
        System.out.println("Enter number of columns:");
        int c = sc.nextInt();
        System.out.println("Enter delay:");
        int delay = sc.nextInt();
        Field f = new Field(r,c);
        class Flag
        {
            public int n;
            public Flag(int n)
            {
                this.n = n;
            }
        }
        Flag is_over = new Flag(0);
        class Mover extends Thread
        {
            private boolean run;
            Mover()
            {
                this.run = false;
            }
            public boolean is_running()
            {
                return this.run;
            }
            public void play()
            {
                this.run = true;
            }
            public void pause()
            {
                this.run = false;
            }
            public void run()
            {
                while(true)
                {
                    f.print();
                    try
                    {
                        Thread.sleep(delay);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    if(this.run)
                    {
                        f.move(f.get_direction());
                    }
                    if(f.is_overlapping())
                    {
                        is_over.n = 1;
                        f.print();
                        System.out.println("\nGame over: enter any key to quit");
                        break;
                    }
                }
            }
        }
        Mover m = new Mover();
        //
        char ch;
        m.start();
        m.play();
        do
        {   
            if(f.is_overlapping())is_over.n = 1;
            if(is_over.n == 1)
            {
                System.out.println("\nGame over");
                break;
            }
            f.print();
            ch = f.process(sc.next().charAt(0));
            if(ch == 'e')
            {
                if(m.is_running())m.pause();
                else m.play();
            }
            if(m.is_running())f.move(ch);
        }while(ch!='q');
        m.pause();
    }
    
}