package apigen.adt.api;

import aterm.*;
import aterm.pure.PureFactory;
public class ADTFactory extends PureFactory
{
  private aterm.AFun funEntries_Empty;
  private Entries protoEntries_Empty;
  private aterm.AFun funEntries_List;
  private Entries protoEntries_List;
  private aterm.AFun funEntry_Constructor;
  private Entry protoEntry_Constructor;
  private aterm.AFun funEntry_List;
  private Entry protoEntry_List;
  public ADTFactory()
  {
     super();
     initialize();
  }

  public ADTFactory(int logSize)
  {
     super(logSize);
     initialize();
  }

  private void initialize()
  {
    Entries.initialize(this);

    Entries_Empty.initializePattern();
    funEntries_Empty = makeAFun("_Entries_Empty", 0, false);
    protoEntries_Empty = new Entries_Empty();

    Entries_List.initializePattern();
    funEntries_List = makeAFun("_Entries_List", 2, false);
    protoEntries_List = new Entries_List();

    Entry.initialize(this);

    Entry_Constructor.initializePattern();
    funEntry_Constructor = makeAFun("_Entry_Constructor", 3, false);
    protoEntry_Constructor = new Entry_Constructor();

    Entry_List.initializePattern();
    funEntry_List = makeAFun("_Entry_List", 2, false);
    protoEntry_List = new Entry_List();

  }

  protected Entries_Empty makeEntries_Empty(aterm.AFun fun, aterm.ATerm[] args, aterm.ATermList annos) {
    synchronized (protoEntries_Empty) {
      protoEntries_Empty.initHashCode(annos,fun,args);
      return (Entries_Empty) build(protoEntries_Empty);
    }
  }

  public Entries_Empty makeEntries_Empty() {
    aterm.ATerm[] args = new aterm.ATerm[] {};
    return makeEntries_Empty( funEntries_Empty, args, empty);
  }

  protected Entries_List makeEntries_List(aterm.AFun fun, aterm.ATerm[] args, aterm.ATermList annos) {
    synchronized (protoEntries_List) {
      protoEntries_List.initHashCode(annos,fun,args);
      return (Entries_List) build(protoEntries_List);
    }
  }

  public Entries_List makeEntries_List(Entry _head, Entries _tail) {
    aterm.ATerm[] args = new aterm.ATerm[] {_head, _tail};
    return makeEntries_List( funEntries_List, args, empty);
  }

  protected Entry_Constructor makeEntry_Constructor(aterm.AFun fun, aterm.ATerm[] args, aterm.ATermList annos) {
    synchronized (protoEntry_Constructor) {
      protoEntry_Constructor.initHashCode(annos,fun,args);
      return (Entry_Constructor) build(protoEntry_Constructor);
    }
  }

  public Entry_Constructor makeEntry_Constructor(String _sort, String _alt, ATerm _pat) {
    aterm.ATerm[] args = new aterm.ATerm[] {makeAppl(makeAFun(_sort, 0, true)), makeAppl(makeAFun(_alt, 0, true)), _pat};
    return makeEntry_Constructor( funEntry_Constructor, args, empty);
  }

  protected Entry_List makeEntry_List(aterm.AFun fun, aterm.ATerm[] args, aterm.ATermList annos) {
    synchronized (protoEntry_List) {
      protoEntry_List.initHashCode(annos,fun,args);
      return (Entry_List) build(protoEntry_List);
    }
  }

  public Entry_List makeEntry_List(String _sort, String _elementSort) {
    aterm.ATerm[] args = new aterm.ATerm[] {makeAppl(makeAFun(_sort, 0, true)), makeAppl(makeAFun(_elementSort, 0, true))};
    return makeEntry_List( funEntry_List, args, empty);
  }

}
