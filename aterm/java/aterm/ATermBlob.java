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

/**
 * This interface describes the functionality of an ATermBlob
 * (Binary Large OBject).
 * 
 * @author Hayco de Jong (jong@cwi.nl)
 * @author Pieter Olivier (olivierp@cwi.nl)
 * @version 0.1, Fri Jan 28 10:19:58 MET 2000
 */
public interface ATermBlob extends ATerm {

    /**
     * Gets the size (in bytes) of the data in this blob.
     *
     * @param the size of the data in this blob.
     */
    public int getSize();

    /**
     * Gets the data in this blob.
     *
     * @return the data in this blob.
     *
     */
    public byte[] getData();
}
