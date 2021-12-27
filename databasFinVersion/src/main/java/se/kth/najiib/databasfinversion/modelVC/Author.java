package se.kth.najiib.databasfinversion.modelVC;

import java.util.ArrayList;
import java.util.List;

/**Denna klass skapar ett författarobjekt
 *
 */
public class Author {
    private String fullName;
    private String authorIDs;
    private ArrayList<Book> books;
    private String ISBN;
    private String dob;

    public Author(String authorIDs, String fullName ,String dob, String ISBN){

        this.fullName = fullName;
        this.books = new ArrayList<>();
        this.ISBN = ISBN;
        this.authorIDs = authorIDs;
        this.dob=dob;

    }



    public String getISBN(){
        return ISBN;
    }

    public List<Book> getBooks(){
        return (List<Book>) books.clone();
    }
    /** Denna metod hämtar födelsedatum
     *
     * @return födelsedatum
     */
    public String getAuthorIDs(){return authorIDs;}
    public String getDob() {
        return dob;
    }
    /** Denna metod hämtar namn på författare
     *
     * @return namnet på författaren
     */
    public String getFullName(){
        return fullName;
    }

    @Override
    public String toString() {
          return "ID: "+authorIDs + "  "+
                "name:" + fullName +
                " dob: " + dob;

    }
}