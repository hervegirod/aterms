package aterm;

/**
 * An ATermAppl represents a function application.
 * 
 * 
 * @author Pieter Olivier (olivierp@cwi.nl)
 * @author Hayco de Jong (jong@cwi.nl)
 * @version 0.1, Fri Jan 28 10:19:58 MET 2000
 */
public interface ATermAppl extends ATerm {

    /**
     * Gets the function name of this application.
     *
     * @return the function name of this application.
     *
     */
    public String getName();

    /**
     * Gets the arguments of this application.
     *
     * @return a list containing all arguments of this application.
     */
    public ATermList getArguments();

    /**
     * Gets a specific argument of this application.
     *
     * @param i the index of the argument to be retrieved.
     *
     * @return the ith argument of the application.
     */
    public ATerm getArgument(int i);

    /**
     * Gets if this application is quoted. A quoted application looks
     * like this: "foo", whereas an unquoted looks like this: foo.
     *
     * @return true if this application is quoted, false otherwise.
     */
    public boolean isQuoted();

    /**
     * Gets the arity of this application. Arity is the number
     * of arguments of a function application.
     *
     * @return the number of arguments of this application.
     */
    public int getArity();
}
