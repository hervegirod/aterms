package aterm;

// Prefer toolbus.util.PrintWriter above java.io.PrintWriter
import aterm.util.PrintWriter;
import aterm.util.*;
import java.util.*;
import java.io.*;

/**
  * The class ATermList is the binary list constructor for ATermList.
  */

public class ATermList extends ATerm
{
  private ATerm first;
  private ATermList next;

  //{ static protected int hashFunction(ATerm first, List next, List annos)

  /**
    * Calculate the hash-code of a list.
    */

  static protected int hashFunction(ATerm first, ATermList next, ATermList annos)
  {
    return first.hashCode() + next.hashCode();
  }

  //}

  //{ protected ATermList(World world, ATermList annos)

  /**
    * Construct an empty list.
    */

  protected ATermList(World world, ATermList annos)
  {
    super(world, annos);
    first = null;
    next  = null;
    hashcode = 123;
  }

  //}
  //{ protected ATermList(World world, ATerm first, List next, List annos)

  /**
    * Construct a new list from a first element and a tail.
    */

  protected ATermList(World world, ATerm first, ATermList next, ATermList annos)
  {
    super(world, annos);
    this.first = first;
    this.next  = next;
    hashcode = hashFunction(first, next, annos);
  }

  //}
  //{ protected boolean deepEquality(ATerm peer)

  /**
    * Check deep equality on terms
    */

  protected boolean deepEquality(ATerm peer)
  {
    if(peer.getType() != ATerm.LIST)
      return false;

    ATermList list = (ATermList)peer;
    return first.deepEquality(list.first) && next.deepEquality(list.next) 
      && annos.deepEquality(list.annos);
  }

  //}
  //{ protected ATerm setAnnotations(ATermList annos)

  /**
    * Annotate this term.
    */

  protected ATerm setAnnotations(ATermList annos)
  {
    return world.makeList(first, next, annos);
  }

  //}
  //{ protected boolean match(ATerm trm, Vector subterms)

  /**
    * Match against trm, using this as a placeholder term.
    */

  protected boolean match(ATerm trm, Vector subterms)
  {
    if(trm.getType() == LIST) {
      ATermList trms = (ATermList)trm;
      // Check for the empty list
      if(first == null || trms.first == null)
        return first == null && trms.first == null;
			
			
      // First we need to handle the special case where the pattern <term>
      // is used.
      if(first.getType() == PLACEHOLDER) {
				ATerm ph = ((ATermPlaceholder)first).getPlaceholderType();
				if(ph.getType() == APPL) {
					ATermAppl appl = (ATermAppl)ph;
					if(appl.getName().equals("list") && appl.getArguments().isEmpty()) {
						subterms.addElement(trms);
						return true;
					}
				}
      }
      
      if(this == trm)
				return true;

      // Just match the first element and the tail.
      if(!first.match(trms.first, subterms))
				return false;
      
      return next.match(trms.next, subterms);
    }
    return false;
  }

  //}

  //{ public int getType()

  public int getType()
  {
    return LIST;
  }

  //}
  //{ public void write(OutputStream o) 

  /**
    * Write a list to an OutputStream.
    */

	public void write(OutputStream o)
		throws java.io.IOException
	{
		o.write('[');
		_write(o);
		o.write(']');
		super.write(o);
	}

  protected void _write(OutputStream o) 
    throws java.io.IOException
  { 
    if(first != null) {
      first.write(o);
      if(!next.isEmpty()) {
				o.write(',');
				next._write(o);
      }
    }
  }

  //}

  //{ public boolean isEmpty()

  /**
    * Check if an object represents the empty list.
    */

  public boolean isEmpty()
  {
    return first == null && next == null;
  }

  //}
  //{ public ATerm getFirst()

  /**
    * Retrieve the first element of a list.
    */

  public ATerm getFirst()
  {
    return first;
  }

  //}
  //{ public ATermList getNext()

  /**
    * Retrieve the tail of a list.
    */

  public ATermList getNext()
  {
    return next;
  }

  //}
  //{ public int getLength()

  /**
    * Calculate the length of a list.
    */

  public int getLength()
  {
    if(first == null)
      return 0;

    int length = 1;
    ATermList tail = next;
    while(!tail.isEmpty()) {
      tail = tail.next;
      length++;
    }
    return length;
  }

  //}
  //{ public int indexOf(ATerm el, int start)

  /**
    * Search for the first element in a list.
    */

  public int indexOf(ATerm el, int start)
  {
    ATermList cur = this;
    int index;

		for(index=0; index < start; index++)
			cur = cur.getNext();
			
    while(!cur.isEmpty()) {
      if(cur.getFirst().equals(el))
				return index;
       
      index++;
      cur = cur.getNext();
    }

    return -1;
  }

  //}
  //{ public int lastIndexOf(ATerm el, int start)

  /**
    * Search for the last element in a list.
    */

  public int lastIndexOf(ATerm el, int start)
  {
    int index = 0, last = -1;
		if(start < 0)
			start += getLength();

    for(ATermList cur = this; !cur.isEmpty() && index<=start; 
				cur = cur.getNext()) {
      if(cur.getFirst().equals(el))
				last = index;
      index++;
    }
    return last;
  }

  //}
  //{ public ATermList concat(ATermList rhs)

  /** 
    * Concatenate two ATermList and return the result.
    */

  public ATermList concat(ATermList rhs)
  {
    if(isEmpty())
      return rhs;

    return world.makeList(first, next.concat(rhs));
  }

  //}
  //{ public ATermList append(ATerm el)

  /**
    * Add one element to a list.
    */

  public ATermList append(ATerm el)
  {
    return concat(world.makeList(el, world.empty, world.empty));
  }

  //}
  //{ public ATerm elementAt(int n)

  /**
    * Return a specific element from a list.
    */

  public ATerm elementAt(int n)
  {
    ATermList cur = this;

    try {
      for(int i=0; i<n; i++) 
				cur = cur.getNext();
      return cur.getFirst();
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("index out of bounds: " + n);
    }
  }

  //}

  //{ public ATermList removeElementAt(int n)

  /**
    * Remove a specific element from a list.
    */

  public ATermList removeElementAt(int n)
  {
    if(n == 0)
      return next;
    return world.makeList(first, next.removeElementAt(n-1));
  }

  //}

	// <PO> Missing:
	// public ATermList getPrefix()
	// public ATerm getLast()
	// public ATermList getSlice(int start, int end)
	// public ATermList insert(ATerm el)
	// public ATermList insertAt(ATerm el, int idx)
	// public ATermList removeElement(ATerm el)
	// public ATermList removeAll(ATerm el)
	// public ATermList replace(ATerm el, int idx)
	// public ATermList filter(Predicate pred)
}


