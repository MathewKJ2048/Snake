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
        for(int i=0;i<r;i++)
        {
            for(int j=0;j<c;j++)
            {
                this.field[i][j] = this.empty;
            }
        }
        this.snake = new Snake(r/2,c/2);
        this.snake.grow_tail_at(r/2 - 1,c/2);
        update_snake();
    }
    private void update_snake()
    {
        int n = this.snake.length();
        this.field[this.snake.get_i_of_segment(0)%this.r][this.snake.get_j_of_segment(0)%this.c] = this.head;
        for(int t=1;t<n-1;t++)
        {
            this.field[this.snake.get_i_of_segment(t)%this.r][this.snake.get_j_of_segment(t)%this.c] = this.body;
        }
        this.field[this.snake.get_i_of_segment(n-1)%this.r][this.snake.get_j_of_segment(n-1)%this.c] = this.tail;
    }
    //
    public void print()
    {
        System.out.print('\f');
        for(int i=0;i<this.r;i++)
        {
            for(int j=0;j<this.c;j++)
            {
                System.out.print(this.field[i][j]+" ");
            }
            System.out.println();
        }
    }
}
