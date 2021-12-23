class Segment
{
    int i;
    int j;
    Segment()
    {
        this.i=0;
        this.j=0;
    }
    Segment(int i,int j)
    {
        this.i=i;
        this.j=j;
    }
    //
    public void move_to(int i,int j)
    {
        this.i=i;
        this.j=j;
    }
}