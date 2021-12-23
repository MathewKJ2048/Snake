import java.util.*;
public class Snake
{
    private List<Segment> snake;
    public Snake(int i,int j)
    {
        snake = new ArrayList<Segment>();
        Segment head = new Segment(i,j);
        snake.add(head);
    }
    //
    int length()
    {
        return snake.size();
    }
    int get_i_of_segment(int t)
    {
        return snake.get(t).i;
    }
    int get_j_of_segment(int t)
    {
        return snake.get(t).j;
    }
    //
    void grow_tail_at(int i, int j)
    {
        Segment new_tail = new Segment(i,j);
        snake.add(new_tail);
    }
    void move_head_to(int i,int j)
    {
        int n = snake.size();
        for(int t=n-1;t>0;t--)
        {
            snake.get(t).i = snake.get(t-1).i;
            snake.get(t).j = snake.get(t-1).j;
        }
        snake.get(0).i = i;
        snake.get(0).j = j;        
    }
    //
    char get_direction()
    {
        if(this.snake.size()<2)
        {
            return ' ';
        }
        if(this.snake.get(0).i == this.snake.get(1).i)
        {
            if(this.snake.get(0).j<this.snake.get(1).j) return 'a';
            else return 'd';
        }
        else
        {
            if(this.snake.get(0).i<this.snake.get(1).i) return 'w';
            else return 's';
        }
    }
}