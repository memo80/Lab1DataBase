package se.kth.najiib.databasdb.modelVC;




import java.time.LocalDate;

/**This class creates the object of the Author
 *
 */
public class Author {
    private String name;
    private LocalDate dateOfBirth;
    private int authorID;

    public Author(int authorID, String name, LocalDate dateOfBirth){
        this.authorID=authorID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    /** Method that retrieves the Author's name
     *

     * @return  Author's name
     */

    public String getName(){
        return name;
    }// ändrad get ska inte ha någon parameter


    /**  Method that retrieves the author's date of birth
     *
     * @return date of birth
     */

    public LocalDate getDateOfBirth(){
        return dateOfBirth;
    }



    @Override
    public String toString() {
        return name+" " + dateOfBirth;
    }
}
