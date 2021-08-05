import java.util.*;
import java.math.*;
public class Field
{
    private char field[][]; //
    private int r,c; //rows and columns
    private Snake snake;
    private char head,tail,body,food,empty; //stores characters used to represent given element
    
    public Field(int r, int c)
    {
        this.field = new char[r][c];
        this.r = r;
        this.c = c;
        this.empty = '.';
        this.head = 'O';
        this.tail = '*';
        this.body = '#';
        this.food = '@';
        clear_field();
        this.snake = new Snake(r/2,c/2);
        this.snake.grow_tail_at(r/2 - 1,c/2);
        update_snake();
    }
    public void clear_field()
    {
        for(int i=0;i<this.r;i++)
        {
            for(int j=0;j<this.c;j++)
            {
                this.field[i][j] = this.empty;
            }
        }
    }
    public void clear_snake()
    {
        int n = this.snake.length();
        for(int t=0;t<n;t++)
        {
            this.field[(this.snake.get_i_of_segment(t)%this.r+r)%this.r][(this.snake.get_j_of_segment(t)%this.c+c)%this.c]=this.empty;
        }
    }
    public void update_snake()
    {
        int n = this.snake.length();
        for(int t=1;t<n-1;t++)
        {
            this.field[(this.snake.get_i_of_segment(t)%this.r+r)%this.r][(this.snake.get_j_of_segment(t)%this.c+c)%this.c] = this.body;
        }
        this.field[(this.snake.get_i_of_segment(0)%this.r+r)%this.r][(this.snake.get_j_of_segment(0)%this.c+c)%this.c] = this.head;
        this.field[(this.snake.get_i_of_segment(n-1)%this.r+r)%this.r][(this.snake.get_j_of_segment(n-1)%this.c+c)%this.c] = this.tail;
    } 
    public void set_food()
    {
        Random r = new Random();
        int i,j;
        do
        {
            i = r.nextInt(this.r);
            j = r.nextInt(this.c);
        }while(this.field[i][j] != this.empty);
        this.field[i][j] = this.food;
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
            if(this.field[(i_new%this.r+r)%this.r][(j_new%this.c+c)%this.c] == this.food)
            {
                this.snake.grow_tail_at(i_tail,j_tail);
            }     
            update_snake();
        }
    }
    //
    public void print()
    {
        System.out.print('\f');
        System.out.println("length:"+this.snake.length());
        for(int i=0;i<this.r;i++)
        {
            for(int j=0;j<this.c;j++)
            {
                System.out.print(this.field[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    //
    static void main()
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter number of rows:");
        int r = sc.nextInt();
        System.out.println("Enter number of columns:");
        int c = sc.nextInt();
        Field f = new Field(r,c);
        //
        char ch;
        do
        {
            f.print();
            ch = sc.next().charAt(0);
            if(ch == 'f')f.set_food();
            f.move(ch);
            f.clear_snake();
            f.update_snake();
        }while(ch!='q');
    }
}
