/*

    ATerm -- The ATerm (Annotated Term) library
    Copyright (C) 1998-2000  Stichting Mathematisch Centrum, Amsterdam, 
                             The  Netherlands.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA

*/
package aterm;

import java.util.List;

/**
 * This is the base interface for all ATerm interfaces,
 * which will ultimately be implemented by two separate
 * ATerm Factories (a native and a pure one).
 * 
 * @author Hayco de Jong (jong@cwi.nl)
 * @author Pieter Olivier (olivierp@cwi.nl)
 * @version 0.1, Thu Jan 27 15:45:52 MET 2000
 */
public interface ATerm {

    /**
     * A term of type INT
     */
    public static final int INT = 1;

    /**
     * A term of type REAL
     */
    public static final int REAL = 2;

    /**
     * A term of type APPL (function application)
     */
    public static final int APPL = 3;

    /**
     * A term of type LIST
     */
    public static final int LIST = 4;

    /**
     * A term of type PLACEHOLDER
     */
    public static final int PLACEHOLDER = 5;

    /**
     * A term of type BLOB (Binary Large OBject)
     */
    public static final int BLOB = 6;

    /**
     * Gets the type of this term.
     * 
     * 
     * @return the type of this ATerm.
     * 
     * @see #INT
     * @see #REAL
     * @see #APPL
     * @see #LIST
     * @see #PLACEHOLDER
     * @see #BLOB
     */
    public int getType();

    /**
     * Gets a hashcode value of this term.
     * 
     * @return the hashcode of this term.
     *
     */
    public int hashCode();

    /**
     * Matches this term against a String pattern. The pattern is
     * parsed into a term, which this term is then matched against.
     * 
     * @param pattern the string pattern to match this term against.
     * 
     * @return a list containing the subterms matching the placeholders
     * if the match succeeds, or null if the match fails.
     *
     * @throws ParseError if pattern cannot be parsed into a term.
     * 
     * @see #match(ATerm)
     */
    public List match(String pattern) throws ParseError;

    /**
     * Matches this term against a term pattern. A list containing
     * the subterms matching the placeholders in the pattern is
     * built as this term is matched against the pattern.
     *
     * @param pattern The term pattern to match this term against.
     *
     * @return a list containing the subterms matching the placeholders
     * if the match succeeds, or null if the match fails.
     *
     */
    public List match(ATerm pattern);

    /**
     * Gets a annotation associated with specific label from this term.
     * 
     * 
     * @param label the label of the desired annotation.
     * 
     * @return the annotation associated with label, or null if
     * annotation with specified label does not exist.
     * 
     * @see #setAnnotation
     */
    public ATerm getAnnotation(ATerm label);

    /**
     * Sets annotation of this term with given label. If no annotation
     * with this label exists, a new annotation is created, otherwise
     * the existing annotation is updated.
     * 
     * 
     * @param label the label of the annotation.
     * @param anno the annotation itself.
     * 
     * @return a new version of this term with the requested annotation.
     * 
     * @see #getAnnotation
     */
    public ATerm setAnnotation(ATerm label, ATerm anno);

    /**
     * Removes a specific annotation from this term.
     *
     * 
     * @param label the label of the annotation to be removed.
     * 
     * @return a new version of this term without the annotation.
     * 
     * @see #setAnnotation
     */
    public ATerm removeAnnotation(ATerm label);

    /**
     * Checks equality of this term against another term.
     * This method exists to keep a tight relation to the C-library.
     * Experienced Java programmers might feel more comfortable using
     * the {@link #equals} method.
     * 
     * 
     * @param term the term to check for equality.
     * 
     * @return true iff terms are equal (including
     * any annotations they might have!), false otherwise.
     *
     * @see #equals(Object)
     * 
     */
    public boolean isEqual(ATerm term);

    /**
     * Checks equality of this term against any java object.
     * Note that for two terms to be equal, any annotations they
     * might have must be equal as well.
     * 
     * @param obj the object to check for equality.
     * 
     * @return true iff term equals obj (including annotations).
     * 
     * @see #isEqual
     */
    public boolean equals(Object obj);

    /**
     * Gets a string representation of this term.
     * 
     * 
     * @return a string representation of this term.
     * 
     */
    public String toString();
}
