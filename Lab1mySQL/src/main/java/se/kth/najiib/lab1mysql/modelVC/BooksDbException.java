package se.kth.najiib.lab1mysql.modelVC;

public class BooksDbException extends Exception{

    public BooksDbException(String msg,Exception cause)
    {
        super(msg,cause);
    }
    public  BooksDbException(String msg)
    {
        super(msg);
    }
public BooksDbException()
{
    super();
}










}
