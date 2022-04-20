package se.kth.najiib.lab1mysql.modelVC;

import java.util.ArrayList;
import java.util.List;

/**Denna klass skapar ett författarobjekt
 *
 */
public class Author {
    private String fullName;
    private String authorIDs;
   // private ArrayList<Book> books;
    private String ISBN;
    private String dob;

    public Author(String authorIDs, String fullName ,String dob){

        this.fullName = fullName;
        //this.books = new ArrayList<>();

        this.authorIDs = authorIDs;
        this.dob=dob;


    }
    public String getISBN(){
        return ISBN;
    }


    /** this method obtains authorID of an author
     *
     * @return authorID of an author
     */
    public String getAuthorIDs(){return authorIDs;}
    /** this method obtains birtday of an author
     *
     * @return födelsedatum
     */
    public String getDob() {
        return dob;
    }
    /**this method obtains name of an author
     *
     * @return name of an author
     */
    public String getFullName(){
        return fullName;
    }

    public void isbnWrite(String isbn)
    {
        this.ISBN=isbn;
    }

    @Override
    public String toString() {
        return "ID: "+authorIDs + "  "+
                "name:" + fullName +
                " dob: " + dob+"\n";

    }
}