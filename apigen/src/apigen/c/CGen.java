package apigen.c;

//{{{ imports

import java.io.*;
import java.util.*;

import aterm.*;
import aterm.pure.PureFactory;

import apigen.*;

//}}}

public class CGen
{
  //{{{ Constants

  // Do *NOT* add an entry for '-' (Dash) here, the dash is used
  // as a word-separator!

  private final static String[] SPECIAL_CHAR_WORDS =
  { "[BracketOpen", "]BracketClose",
    "{BraceOpen",   "}BraceClose",
    "(ParenOpen",   ")ParenClose",
    "<LessThan",    ">GreaterThan",
    "|Bar",         "&Amp",
    "+Plus",	    ",Comma",
    ".Period",	    "~Tilde",
    ":Colon",	    ";SemiColon",
    "=Equals",	    "#Hash",
    "/Slash",	    "\\Backslash",
    "*Star",	    "_Underscore",
    "$Dollar",	    "'SingleQuote",
  };

  private final static String[][] RESERVED_TYPES =
  { { "int",  "int"    },
    { "real", "double" },
    { "str",  "char *" },
    { "term", "ATerm"  }
  };

  //}}}

  public static boolean verbose = false;
  /* Optimization disabled because of possible presence of annotations! */
  private static final boolean OPTIMIZE_WITHOUT_ANNOS = false;

  private ATermFactory factory;

  private InputStream input;
  private String      output;
  private String      capOutput;
  private String      prefix;
  private String      prologue;
  private String      macro;
  
  private int nextAFun;
  private Map afuns_by_name;
  private Map afuns_by_afun;

  private PrintStream source;
  private PrintStream header;

  private Map specialChars;
  private Map reservedTypes;
  private char last_special_char;
  private String last_special_char_word;

  private boolean make_term_compatibility;

  //{{{ private static void usage()

  private static void usage()
  {
    System.err.println("usage: CGen [options]");
    System.err.println("options:");
    System.err.println("\t-prefix <prefix>          [\"\"]");
    System.err.println("\t-input <in>               [-]");
    System.err.println("\t-output <out>");
    System.err.println("\t-prologue <prologue>");
    System.exit(1);
  }

  //}}}
  //{{{ public static String capitalize(String s)

  public static String capitalize(String s)
  {
    return Character.toUpperCase(s.charAt(0)) + s.substring(1);
  }

  //}}}

  //{{{ public final static void main(String[] args)

  public final static void main(String[] args)
    throws IOException
  {
    String prefix = "";
    String input  = "-";
    String output = null;
    String prologue = null;
    InputStream inputStream;
    boolean make_term_compatibility = false;

    if (args.length == 0) {
      usage();
    }

    for (int i=0; i<args.length; i++) {
      if ("-help".startsWith(args[i])) {
	usage();
      } else if ("-verbose".startsWith(args[i])) {
	verbose = true;
      } else if ("-prefix".startsWith(args[i])) {
	prefix = args[++i];
      } else if ("-output".startsWith(args[i])) {
	output = args[++i];
      } else if ("-prologue".startsWith(args[i])) {
	prologue = args[++i];
      } else if ("-input".startsWith(args[i])) {
	input = args[++i];
      } else if ("-compatible:term".equals(args[i])) {
	make_term_compatibility = true;
      } else {
	usage();
      }
    }

    if (input.equals("-")) {
      inputStream = System.in;
      if (output == null) {
	usage();
      }
    } else {
      inputStream = new FileInputStream(input);
      if (output == null) {
	int extIndex = input.lastIndexOf((int)'.');
	output = input.substring(0, extIndex);
      }
    }
    CGen gen = new CGen(inputStream, output, prefix, prologue,
			make_term_compatibility);
  }

  //}}}

  //{{{ public CGen(InputStream input, String output, String prefix, String prologue)

  public CGen(InputStream input, String output, String prefix, String prologue,
	      boolean make_term_compatibility)
    throws IOException
  {
    this.input     = input;
    this.output    = output;
    this.capOutput = capitalize(output);
    this.prefix    = prefix;
    this.prologue  = prologue;
    this.make_term_compatibility = make_term_compatibility;
    afuns_by_name  = new HashMap();
    afuns_by_afun  = new HashMap();
    specialChars   = new HashMap();
    reservedTypes  = new HashMap();

    for (int i=0; i<SPECIAL_CHAR_WORDS.length; i++) {
      String word = SPECIAL_CHAR_WORDS[i];
      specialChars.put(new Character(word.charAt(0)), word.substring(1));
    }

    for (int i=0; i<RESERVED_TYPES.length; i++) {
      reservedTypes.put(RESERVED_TYPES[i][0], RESERVED_TYPES[i][1]);
    }

    factory = new PureFactory();

    ATerm adt = factory.readFromFile(input);

    API api = new API(adt);

    String header_name = output + ".h";
    header = new PrintStream(new FileOutputStream(header_name));

    String source_name = output + ".c";
    source = new PrintStream(new FileOutputStream(source_name));

    genPrologue(api);
    genTypes(api);
    genInitFunction(api);
    genTermConversions(api);
    genConstructors(api);
    genIsEquals(api);
    genAccessors(api);
    genSortVisitors(api);
    genEpilogue(api);

    ATerm dict = buildDictionary(api);
    OutputStream dict_out = new FileOutputStream(output + ".dict");
    dict.writeToTextFile(dict_out);
  }

  //}}}

  //{{{ public void lookupAFunName(AFun afun)

  public String lookupAFunName(AFun afun)
  {
    String name = (String)afuns_by_afun.get(afun);

    if (name == null) {
      name = prefix + "afun" + nextAFun++;
      afuns_by_name.put(name, afun);
      afuns_by_afun.put(afun, name);
    }

    return name;
  }

  //}}}

  //{{{ private void genPrologue(API api)

  private void genPrologue(API api)
    throws IOException
  {
    /* Header stuff */
    macro = "_" + output.toUpperCase() + "_H";
    StringBuffer buf = new StringBuffer();
    for (int i=0; i<macro.length(); i++) {
      if (Character.isLetterOrDigit(macro.charAt(i))) {
	buf.append(macro.charAt(i));
      } else {
	buf.append('_');
      }
    }
    macro = buf.toString();

    header.println("#ifndef " + macro);
    header.println("#define " + macro);

    //{{{ Output includes

    header.println();
    printFoldOpen(header, "includes");
    header.println("#include <aterm1.h>");
    header.println("#include \"" + output + "_dict.h\"");
    printFoldClose(header);
    header.println();

    //}}}
    //{{{ Copy prologue file verbatim

    if (prologue != null) {
      printFoldOpen(header, "prologue");
      InputStream stream = new FileInputStream(prologue);
      while (true) {
	int b = stream.read();
	if (b == -1) {
	  break;
	}
	header.write(b);
      }
      printFoldClose(header);
    }

    //}}}

    /* Source stuff */
    source.println("#include <assert.h>");
    source.println();
    source.println("#include <aterm2.h>");
    source.println("#include <deprecated.h>");
    source.println("#include \"" + output + ".h\"");
    source.println();
  }

  //}}}
  //{{{ private void genTypes(API api)

  private void genTypes(API api)
  {
    Iterator types = api.typeIterator();
    printFoldOpen(header, "typedefs");
    printFoldOpen(source, "typedefs");
    while (types.hasNext()) {
      Type type = (Type)types.next();
      String id = buildTypeName(type);
      header.println("typedef struct _" + id + " *" + id + ";");
      source.println("typedef struct ATerm _" + id + ";");
    }
    printFoldClose(source);
    printFoldClose(header);
    source.println();
    header.println();
  }

  //}}}
  //{{{ private String buildDictInitFunc(String name)

  private String buildDictInitFunc(String name)
  {
    StringBuffer buf = new StringBuffer();
    for (int i=0; i<name.length(); i++) {
      char c = name.charAt(i);
      if (Character.isLetterOrDigit(c)) {
	buf.append(c);
      } else {
	buf.append('_');
      }
    }
    return buf.toString();
  }

  //}}}
  //{{{ private void genInitFunction(API api)

  private void genInitFunction(API api)
  {
    String decl = "void " + prefix
      + "init" + buildId(capOutput) + "Api(void)";
    header.println(decl + ";");

    printFoldOpen(source, decl);
    source.println(decl);
    source.println("{");
    String dictInitfunc = buildDictInitFunc(output);
    source.println("  init_" + dictInitfunc + "_dict();");
    source.println("}");
    printFoldClose(source);
    source.println();
    header.println();
  }

  //}}}
  //{{{ private void genTermConversions(API api)

  private void genTermConversions(API api)
  {
    Iterator types = api.typeIterator();
    printFoldOpen(header, "term conversion functions");
    printFoldOpen(source, "term conversion functions");
    while (types.hasNext()) {
      Type type = (Type)types.next();
      String type_id = buildId(type.getId());
      String type_name = buildTypeName(type);

      //{{{ PPPXXXFromTerm(ATerm t)

      if (make_term_compatibility) {
	String old_macro = "#define " + prefix + "make" + type_id + "FromTerm(t)"
	  + " (" + prefix + type_id + "FromTerm(t))";
	header.println(old_macro);
      }
      String decl = type_name + " " + prefix + type_id + "FromTerm(ATerm t)";
      header.println(decl + ";");
      printFoldOpen(source, decl);
      source.println(decl);
      source.println("{");
      source.println("  return (" + type_name + ")t;");
      source.println("}");
      printFoldClose(source);

      //}}}
      //{{{ PPPXXXtoTerm(Xxx arg)

      if (make_term_compatibility) {
	String old_macro = "#define " + prefix + "makeTermFrom" + type_id + "(t)"
	  + " (" + prefix + type_id + "ToTerm(t))";
	header.println(old_macro);
      }
      decl = "ATerm " + prefix + type_id + "ToTerm(" + type_name + " arg)";
      header.println(decl + ";");
      printFoldOpen(source, decl);
      source.println(decl);
      source.println("{");
      source.println("  return (ATerm)arg;");
      source.println("}");
      printFoldClose(source);

      //}}}
    }
    printFoldClose(source);
    printFoldClose(header);
  }

  //}}}
  //{{{ private void genIsEquals(API api)

  private void genIsEquals(API api)
  {
    Iterator types = api.typeIterator();
    printFoldOpen(header, "equality functions");
    printFoldOpen(source, "equality functions");

    while (types.hasNext()) {
      Type type = (Type)types.next();
      String type_id = buildId(type.getId());
      String type_name = buildTypeName(type);

      String decl = "ATbool " + prefix + "isEqual" + type_id
	+ "(" + type_name + " arg0, " + type_name + " arg1)";

      header.println(decl + ";");

      source.println(decl);
      source.println("{");
      source.println("  return ATisEqual((ATerm)arg0, (ATerm)arg1);");
      source.println("}");
      if (types.hasNext()) {
	source.println();
      }
    }

    printFoldClose(source);
    printFoldClose(header);
  }

  //}}}
  //{{{ private String genConstructorImpl(ATerm pattern)

  private String genConstructorImpl(ATerm pattern)
  {
    String result = null;

    switch (pattern.getType()) {
      case ATerm.REAL:
	result = "(ATerm)ATmakeReal(" + ((ATermReal)pattern).getReal() + ")";
	break;

      case ATerm.INT:
	result = "(ATerm)ATmakeInt(" + ((ATermInt)pattern).getInt() + ")";
	break;

      case ATerm.BLOB:
	throw new RuntimeException("blobs are not supported");

      case ATerm.LIST:
	//{{{ Generate list creation code
	{
	  ATermList list = (ATermList)pattern;
	  int length = list.getLength();
	  if (length == 0) {
	    result = "ATempty";
	  } else {
	    ATerm last = list.elementAt(length-1);
	    if (last.getType() == ATerm.PLACEHOLDER) {
	      ATerm ph = ((ATermPlaceholder)last).getPlaceholder();
	      if (ph.getType() == ATerm.LIST) {
		ATermAppl field = (ATermAppl)(((ATermList)ph).getFirst());
		result = "(ATermList)" + buildId(field.getName());
	      }
	    }
	    if (result == null) {
	      result = "ATmakeList1(" + genConstructorImpl(last) + ")";
	    }
	    for (int i=length-2; i>=0; i--) {
	      ATerm elem = list.elementAt(i);
	      result = "ATinsert(" + result + ", " + genConstructorImpl(elem) + ")";
	    }
	  }
	  result = "(ATerm)" + result;
	}
	//}}}
	break;

      case ATerm.APPL:
	//{{{ Generate application creation code
	{

	  ATermAppl appl = (ATermAppl)pattern;
	  int arity = appl.getArity();
	  if (arity > 0) {
	    ATerm last = appl.getArgument(arity-1);
	    if (last.getType() == ATerm.PLACEHOLDER) {
	      ATerm ph = ((ATermPlaceholder)last).getPlaceholder();
	      if (ph.getType() == ATerm.LIST) {
		throw new RuntimeException("list placeholder not supported in"
					   + " argument list of function application");
	      }
	    }
	  }
	  result = "(ATerm)ATmakeAppl"+(arity <= 6 ? String.valueOf(arity) : "")+"(";
	  result += lookupAFunName(appl.getAFun());
	  for (int i=0; i<arity; i++) {
	    ATerm arg = appl.getArgument(i);
	    result += ", " + genConstructorImpl(arg);
	  }
	  result += ")";
	}

	//}}}
	break;

      case ATerm.PLACEHOLDER:
	//{{{ Generate placeholder code (argument substitution)

	{
	  ATermAppl hole = (ATermAppl)((ATermPlaceholder)pattern).getPlaceholder();
	  String name = buildId(hole.getName());
	  String type = hole.getArgument(0).toString();
	  if (type.equals("int")) {
	    result = "(ATerm)ATmakeInt(" + name + ")";
	  } else if (type.equals("real")) {
	    result = "(ATerm)ATmakeReal(" + name + ")";
	  } else if (type.equals("str")) {
	    result = "(ATerm)ATmakeAppl0(ATmakeAFun(" + name + ", 0, ATtrue))";
	  } else {
	    result = "(ATerm)" + name;
	  }
	}

	//}}}
	break;
    }

    return result;
  }

  //}}}
  //{{{ private void genConstructors(API api)

  private void genConstructors(API api)
  {
    Iterator types = api.typeIterator();
    printFoldOpen(header, "constructors");
    printFoldOpen(source, "constructors");
    while (types.hasNext()) {
      Type type = (Type)types.next();
      String type_id = buildId(type.getId());
      String type_name = buildTypeName(type);

      Iterator alts = type.alternativeIterator();
      while (alts.hasNext()) {
	Alternative alt = (Alternative)alts.next();
	String decl = buildConstructorDecl(type, alt);
	header.println(decl + ";");
	//{{{ Generate implementation

	printFoldOpen(source, decl.toString());
	source.println(decl);
	source.println("{");

	source.println("  return (" + type_name + ")"
		       + genConstructorImpl(alt.getPattern()) + ";");
	/*
	source.print("  return (" + type_name + ")ATmakeTerm("
		     + prefix + "pattern" + type_id
		     + capitalize(buildId(alt.getId())));
	Iterator fields = type.altFieldIterator(alt.getId());
	while (fields.hasNext()) {
	  Field field = (Field)fields.next();
	  source.print(", " + buildId(field.getId()));
	}
	source.println(");");
	 */

	source.println("}");
	printFoldClose(source);

	//}}}
      }
    }
    printFoldClose(source);
    printFoldClose(header);
  }

  //}}}
  //{{{ private String buildConstructorName(Type type, Alternative alt)

  private String buildConstructorName(Type type, Alternative alt)
  {
    String type_id   = buildId(type.getId());
    String alt_id    = buildId(alt.getId());
    return prefix + "make" + type_id + capitalize(alt_id);
  }

  //}}}
  //{{{ private String buildConstructorDecl(Type type, Alternative alt)

  private String buildConstructorDecl(Type type, Alternative alt)
  {
    String type_id   = buildId(type.getId());
    String type_name = buildTypeName(type);
    String alt_id    = buildId(alt.getId());

    StringBuffer decl = new StringBuffer();
    decl.append(type_name + " " + buildConstructorName(type, alt) + "(");
    Iterator fields = type.altFieldIterator(alt.getId());
    boolean first = true;
    while (fields.hasNext()) {
      Field field = (Field)fields.next();
      if (first) {
	first = false;
      } else {
	decl.append(", ");
      }
      decl.append(buildTypeName(field.getType()) + " "
		  + buildId(field.getId()));
    }
    decl.append(")");

    return decl.toString();
  }

  //}}}

  //{{{ private void genAccessors(API api)

  private void genAccessors(API api)
  {
    Iterator types = api.typeIterator();
    while (types.hasNext()) {
      Type type = (Type)types.next();
      String type_name = buildTypeName(type);
      printFoldOpen(header, type_name + " accessors");
      printFoldOpen(source, type_name + " accessors");

      genTypeIsValid(type);

      Iterator alts = type.alternativeIterator();
      while (alts.hasNext()) {
	Alternative alt = (Alternative)alts.next();
	genIsAlt(type, alt);
      }

      Iterator fields = type.fieldIterator();
      while (fields.hasNext()) {
	Field field = (Field)fields.next();
	genHasField(type, field);
	genGetField(type, field);
	genSetField(type, field);
      }

      printFoldClose(source);
      printFoldClose(header);
    }
  }

  //}}}
  //{{{ private void genTypeIsValid(Type type)

  private void genTypeIsValid(Type type)
  {
    String type_id = buildId(type.getId());
    String type_name = buildTypeName(type);
    String decl = "ATbool " + prefix + "isValid" + type_id
      + "(" + type_name + " arg)";

    printFoldOpen(source, decl);

    header.println(decl + ";");
    source.println(decl);
    source.println("{");
    Iterator alts = type.alternativeIterator();
    boolean first = true;
    while (alts.hasNext()) {
      Alternative alt = (Alternative)alts.next();
      source.print("  ");
      if (first) {
	first = false;
      } else {
	source.print("else ");
      }
      source.println("if (" + buildIsAltName(type, alt) + "(arg)) {");
      source.println("    return ATtrue;");
      source.println("  }");
    }
    source.println("  return ATfalse;");
    source.println("}");

    printFoldClose(source);
  }

  //}}}
  //{{{ private void genIsAlt(Type type, Alternative alt)

  private void genIsAlt(Type type, Alternative alt)
  {
    String type_id = buildId(type.getId());
    String type_name = buildTypeName(type);
    String decl = "inline ATbool " + buildIsAltName(type, alt) + "(" + type_name + " arg)";
    String pattern;
    StringBuffer match_code = new StringBuffer();
    boolean contains_placeholder = alt.containsPlaceholder();
    int alt_count = type.getAlternativeCount();
    boolean inverted = false;

    //{{{ When OPTIMIZE_WITHOUT_ANNOS: check for patterns without holes

    if (OPTIMIZE_WITHOUT_ANNOS && alt_count == 2 && contains_placeholder) {
      Iterator iter = type.alternativeIterator();
      while (iter.hasNext()) {
	Alternative cur = (Alternative)iter.next();
	if (cur != alt && !cur.containsPlaceholder()) {
	  inverted = true;
	  alt = cur;
	  contains_placeholder = false;
	}
      }
    }

    //}}}

    //{{{ Create match_code

    pattern = prefix + "pattern" + type_id + capitalize(buildId(alt.getId()));
    if (!OPTIMIZE_WITHOUT_ANNOS || contains_placeholder) {
      match_code.append("ATmatchTerm((ATerm)arg, " + pattern);
      Iterator fields = type.altFieldIterator(alt.getId());
      while (fields.hasNext()) {
	fields.next();
	match_code.append(", NULL");
      }
      match_code.append(")");
    } else {
      match_code.append("ATisEqual((ATerm)arg, " + pattern + ")");
    }

    //}}}

    header.println(decl + ";");

    printFoldOpen(source, decl);
    source.println(decl);
    source.println("{");
    if (OPTIMIZE_WITHOUT_ANNOS && inverted) {
      source.println("  return !(" + match_code + ");");
    } else if (OPTIMIZE_WITHOUT_ANNOS && alt_count != 1
	       && !contains_placeholder) {
      source.println("  return " + match_code + ";");
    } else {
      AlternativeList alts_left = type.getAlternatives();
      alts_left.remove(alt);

      alt_count = alts_left.size();
      int pat_type = alt.getPatternType();
      alts_left.keepByType(pat_type);
      if (alts_left.size() != alt_count) {
	//{{{ Check term types

	source.println("  if (ATgetType((ATerm)arg) != "
		       + getATermTypeName(pat_type) + ") {");
	source.println("    return ATfalse;");
	source.println("  }");

	//}}}
      }
      if (pat_type == ATerm.APPL) {
	//{{{ Check function symbols

	AFun afun = ((ATermAppl)alt.buildMatchPattern()).getAFun();
	alt_count = alts_left.size();
	alts_left.keepByAFun(afun);
	if (alts_left.size() < alt_count) {
	  source.println("  if (ATgetAFun((ATermAppl)arg) != ATgetAFun("
			 + pattern + ")) {");
	  source.println("    return ATfalse;");
	  source.println("  }");
	}

	//}}}
      } else if (pat_type == ATerm.LIST) {
	//{{{ Check list length

	ATermList matchPattern = (ATermList)alt.buildMatchPattern();
	if (matchPattern.isEmpty()) {
	  alts_left.clear();
	  source.println("  if (!ATisEmpty((ATermList)arg)) {");
	  source.println("    return ATfalse;");
	  source.println("  }");
	} else if (!matchPattern.equals(factory.parse("[<list>]"))) {
	  alts_left.removeEmptyList();
	  source.println("  if (ATisEmpty((ATermList)arg)) {");
	  source.println("    return ATfalse;");
	  source.println("  }");
	}

	//}}}
      }

      if (alts_left.size() == 0) {
	source.println("#ifndef DISABLE_DYNAMIC_CHECKING");
	source.println("  assert(arg != NULL);");
	source.println("  assert(" + match_code + ");");
	source.println("#endif");
	source.println("  return ATtrue;");
      } else {
	source.println("  {");
	source.println("    static ATerm last_arg = NULL;");
	source.println("    static int last_gc = -1;");
	source.println("    static ATbool last_result;");
	source.println();
	source.println("    assert(arg != NULL);");
	source.println();
	source.println("    if (last_gc != ATgetGCCount() || "
		       + "(ATerm)arg != last_arg) {");
	source.println("      last_arg = (ATerm)arg;");
	source.println("      last_result = " + match_code + ";");
	source.println("      last_gc = ATgetGCCount();");
	source.println("    }");
	source.println();
	source.println("    return last_result;");
	source.println("  }");
      }
    }
    source.println("}");

    printFoldClose(source);
  }

  //}}}
  //{{{ private String buildIsAltName(Type type, Alternative alt)

  private String buildIsAltName(Type type, Alternative alt)
  {
    return buildIsAltName(type, alt.getId());
  }

  //}}}
  //{{{ private String buildIsAltName(Type type, String altId)

  private String buildIsAltName(Type type, String altId)
  {
    return prefix + "is" + buildId(type.getId()) + capitalize(buildId(altId));
  }

  //}}}

  //{{{ private void genHasField(Type type, Field field)

  private void genHasField(Type type, Field field)
  {
    String type_id = buildId(type.getId());
    String type_name = buildTypeName(type);
    String decl = "ATbool " + prefix + "has" + type_id
      + capitalize(buildId(field.getId())) + "(" + type_name + " arg)";

    header.println(decl + ";");

    printFoldOpen(source, decl);
    source.println(decl);
    source.println("{");
    Iterator locs = field.locationIterator();
    boolean first = true;
    while (locs.hasNext()) {
      Location loc = (Location)locs.next();
      source.print("  ");
      if (first) {
	first = false;
      }
      else {
	source.print("else ");
      }
      source.println("if (" + buildIsAltName(type, loc.getAltId()) + "(arg)) {");
      source.println("    return ATtrue;");
      source.println("  }");
    }
    source.println("  return ATfalse;");
    source.println("}");
    printFoldClose(source);
  }

  //}}}

  //{{{ private void genSortVisitors(API api)

  private void genSortVisitors(API api)
  {
    Iterator types = api.typeIterator();
    printFoldOpen(header, "sort visitors");
    printFoldOpen(source, "sort visitors");

    while (types.hasNext()) {
      Type type = (Type)types.next();
      String type_id   = buildId(type.getId());
      String type_name = buildTypeName(type);

      StringBuffer decl_buf = new StringBuffer();
      String visitor_name = prefix + "visit" + type_id;
      decl_buf.append(type_name);
      decl_buf.append(" ");
      decl_buf.append(visitor_name);
      decl_buf.append("(");
      decl_buf.append(type_name);
      decl_buf.append(" arg");
      Iterator fields = type.fieldIterator();
      while (fields.hasNext()) {
	Field field = (Field)fields.next();
	if (!field.getType().equals(type.getId())) {
	  decl_buf.append(", ");
	  decl_buf.append(genAcceptor(field));
	}
      }
      decl_buf.append(")");
      String decl = decl_buf.toString();

      header.println(decl + ";");

      printFoldOpen(source, decl);
      source.println(decl);
      source.println("{");

      Iterator alts = type.alternativeIterator();
      while (alts.hasNext()) {
	Alternative alt = (Alternative)alts.next();
	genSortVisitorAltImpl(type, alt);
      }

      source.println("  ATabort(\"not a " + type_id + ": %t\\n\", arg);");
      source.println("  return (" + type_name + ")NULL;");

      source.println("}");
      printFoldClose(source);
    }

    printFoldClose(source);
    printFoldClose(header);
  }

  //}}}
  //{{{ private String genAcceptor(Field field)

  private String genAcceptor(Field field)
  {
    String type = buildTypeName(field.getType());
    String name = "accept" + capitalize(buildId(field.getId()));

    return type + " (*" + name + ")(" + type + ")";
  }

  //}}}
  //{{{ private void genSortVisitorAltImpl(String type, Alternative alt)

  private void genSortVisitorAltImpl(Type type, Alternative alt)
  {
    String type_id   = buildId(type.getId());
    String alt_id    = buildId(alt.getId());
    String type_name = buildTypeName(type);
    String cons_name = buildConstructorName(type, alt);

    source.println("  if (" + buildIsAltName(type, alt) + "(arg)) {");
    source.print("    return " + cons_name + "(");
    Iterator fields = type.altFieldIterator(alt.getId());
    while (fields.hasNext()) {
      Field field = (Field)fields.next();
      source.println("");
      source.print("        ");
      String getter_name = buildGetterName(type, field);
      if (field.getType().equals(type.getId())) {
	String visitor_name = prefix + "visit" + type_id;
	source.print(visitor_name + "(" + getter_name + "(arg)");
	Iterator params = type.fieldIterator();
	while (params.hasNext()) {
	  Field param = (Field)params.next();
	  if (!param.getType().equals(type.getId())) {
	    source.print(", ");
	    source.print("accept" + capitalize(buildId(param.getId())));
	  }
	}
	source.print(")");
      } else {
	String acceptor_name = "accept" + capitalize(buildId(field.getId()));
	source.print(acceptor_name
		     + " ? " + acceptor_name + "(" + getter_name + "(arg))");
	source.print(" : " + getter_name + "(arg)");
      }
      if (fields.hasNext()) {
	source.print(",");
      }
    }
    source.println(");");
    source.println("  }");
  }

  //}}}

  //{{{ private void genGetField(Type type, Field field)

  private void genGetField(Type type, Field field)
  {
    String type_id = buildId(type.getId());
    String type_name = buildTypeName(type);
    String field_type_name = buildTypeName(field.getType());
    String fieldId = capitalize(buildId(field.getId()));
    String decl = field_type_name + " " + buildGetterName(type, field)
      + "(" + type_name + " arg)";

    header.println(decl + ";");

    printFoldOpen(source, decl);
    source.println(decl);
    source.println("{");
    Iterator locs = field.locationIterator();
    boolean first = true;
    while (locs.hasNext()) {
      Location loc = (Location)locs.next();
      source.print("  ");
      if (first) {
	first = false;
      }
      else {
	source.print("else ");
      }
      if (locs.hasNext()) {
	source.println("if (" + buildIsAltName(type, loc.getAltId()) + "(arg)) {");
      } else {
	source.println("");
      }
      source.print("    return (" + field_type_name + ")");
      Iterator steps = loc.stepIterator();
      String[] type_getter = genReservedTypeGetter(field.getType());
      source.print(type_getter[0]);
      genGetterSteps(steps, "arg");
      source.print(type_getter[1]);
      source.println(";");
      if (locs.hasNext()) {
	source.println("  }");
      }
    }
    /*
    source.println();
    source.println("  ATabort(\"" + type_id + " has no " + fieldId
		   + ": %t\\n\", arg);");
    source.println("  return (" + field_type_name + ")NULL;");
     */

    source.println("}");
    printFoldClose(source);
  }

  //}}}
  //{{{ private String buildGetterName(Type type, Field field)

  private String buildGetterName(Type type, Field field)
  {
    String type_id = buildId(type.getId());
    String fieldId = capitalize(buildId(field.getId()));

    return prefix + "get" + type_id + fieldId;
  }

  //}}}

  //{{{ private void genGetterSteps(Iterator steps, String arg)

  private void genGetterSteps(Iterator steps, String arg)
  {
    if (steps.hasNext()) {
      Step step = (Step)steps.next();
      int index = step.getIndex();
      switch (step.getType()) {
	case Step.ARG:
	  genGetterSteps(steps, "ATgetArgument((ATermAppl)" + arg
			 + ", " + step.getIndex() + ")");
	  break;
	case Step.ELEM:
	  if (index == 0) {
	    genGetterSteps(steps, "ATgetFirst((ATermList)" + arg + ")");
	  } else {
	    genGetterSteps(steps, "ATelementAt((ATermList)" + arg
			   + ", " + step.getIndex() + ")");
	  }
	  break;
	case Step.TAIL:
	  if (index == 0) {
	    genGetterSteps(steps, arg);
	  } else if (index == 1) {
	    genGetterSteps(steps, "ATgetNext((ATermList)" + arg + ")");
	  } else {
	    genGetterSteps(steps, "ATgetTail((ATermList)" + arg
			   + ", " + step.getIndex() + ")");
	  }
	  break;
      }
    }
    else {
      source.print(arg);
    }
  }

  //}}}
  //{{{ private void genEpilogue(API api)

  private void genEpilogue(API api)
  {
    header.println();
    header.println("#endif /* " + macro + " */");
    header.close();
  }

  //}}}

  //{{{ private void genSetField(Type type, Field field)

  private void genSetField(Type type, Field field)
  {
    String type_id    = buildId(type.getId());
    String type_name  = buildTypeName(type);
    String field_id   = buildId(field.getId());
    String field_type_name = buildTypeName(field.getType());
    String decl = type_name + " "
      + prefix + "set" + type_id + capitalize(field_id)
      + "(" + type_name + " arg, " + field_type_name + " " + field_id + ")";

    header.println(decl + ";");

    printFoldOpen(source, decl);
    source.println(decl);
    source.println("{");
    Iterator locs = field.locationIterator();
    boolean first = true;
    while (locs.hasNext()) {
      Location loc = (Location)locs.next();
      source.print("  ");
      if (first) {
	first = false;
      }
      else {
	source.print("else ");
      }
      source.println("if (" + buildIsAltName(type, loc.getAltId()) + "(arg)) {");
      source.print("    return (" + type_name + ")");
      Iterator steps = loc.stepIterator();
      String arg = genReservedTypeSetterArg(field.getType(),
					    buildId(field.getId()));
      genSetterSteps(steps, new LinkedList(), arg);
      source.println(";");
      source.println("  }");
    }
    source.println();
    source.println("  ATabort(\"" + type_id + " has no "
		   + capitalize(field_id) + ": %t\\n\", arg);");
    source.println("  return (" + type_name + ")NULL;");

    source.println("}");
    printFoldClose(source);
  }

  //}}}
  //{{{ private void genSetterSteps(Iterator steps, List parentPath, String arg)

  private void genSetterSteps(Iterator steps, List parentPath, String arg)
  {
    if (steps.hasNext()) {
      Step step = (Step)steps.next();
      switch (step.getType()) {
	case Step.ARG:
	  source.print("ATsetArgument((ATermAppl)");
	  break;
	case Step.ELEM:
	  source.print("ATreplace((ATermList)");
	  break;
	case Step.TAIL:
	  source.print("ATreplaceTail((ATermList)");
	  break;
      }
      genGetterSteps(parentPath.iterator(), "arg");
      if (step.getType() == Step.TAIL) {
        source.print(", (ATermList)");
      } else {
        source.print(", (ATerm)");
      }
      parentPath.add(step);
      genSetterSteps(steps, parentPath, arg);
      source.print(", " + step.getIndex() + ")");
    }
    else {
      source.print(arg);
    }
  }

  //}}}

  //{{{ private void printFoldOpen(PrintStream out, String comment)

  private void printFoldOpen(PrintStream out, String comment)
  {
    out.println("/*{{" + "{  " + comment + " */");
    out.println();
  }

  //}}}
  //{{{ private void printFoldClose(PrintStream out)

  private void printFoldClose(PrintStream out)
  {
    out.println();
    out.println("/*}}" + "}  */");
  }

  //}}}
  //{{{ private String buildTypeName(Type type)

  private String buildTypeName(Type type)
  {
    return buildTypeName(type.getId());
  }

  //}}}
  //{{{ private String buildTypeName(String typeId)

  private String buildTypeName(String typeId)
  {
    String nativeType = (String)reservedTypes.get(typeId);

    if (nativeType != null) {
      return nativeType;
    } else {
      return prefix + buildId(typeId);
    }
  }

  //}}}
  //{{{ private String buildId(String id)

  private String buildId(String id)
  {
    StringBuffer buf = new StringBuffer();
    boolean cap_next = false;

    for (int i=0; i<id.length(); i++) {
      char c = id.charAt(i);
      if (isSpecialChar(c)) {
	buf.append(getSpecialCharWord(c));
	cap_next = true;
      } else {
	switch (c) {
	  case '-':
	    cap_next = true;
	    break;

	  default:
	    if (cap_next) {
	      buf.append(Character.toUpperCase(c));
	      cap_next = false;
	    } else {
	      buf.append(c);
	    }
	    break;
	}
      }
    }

    return buf.toString();
  }

  //}}}
  //{{{ private boolean isSpecialChar(char c)

  private boolean isSpecialChar(char c)
  {
    return getSpecialCharWord(c) != null;
  }

  //}}}
  //{{{ private String getSpecialCharWord(char c)

  private String getSpecialCharWord(char c)
  {
    if (c == last_special_char) {
      return last_special_char_word;
    }

    last_special_char = c;
    last_special_char_word = (String)specialChars.get(new Character(c));

    return last_special_char_word;
  }

  //}}}
  //{{{ private ATerm buildDictionary(API api)

  private ATerm buildDictionary(API api)
  {
    ATermList afun_list = factory.makeList();
    for (int i=nextAFun-1; i>=0; i--) {
      String name = prefix + "afun" + i;
      AFun afun = (AFun)afuns_by_name.get(name);
      ATerm[] args = new ATerm[afun.getArity()];
      for (int j=0; j<afun.getArity(); j++) {
	args[j] = factory.parse("x");
      }
      ATerm term = factory.makeAppl(afun, args);
      afun_list = afun_list.insert(factory.make("[" + name + ",<term>]", term));
    }

    ATermList term_list = factory.makeList();

    Iterator types = api.typeIterator();
    while (types.hasNext()) {
      Type type = (Type)types.next();
      String id = buildId(type.getId());
      Iterator alts = type.alternativeIterator();
      while (alts.hasNext()) {
	Alternative alt = (Alternative)alts.next();
	ATerm entry = factory.make("[<appl>,<term>]",
				   prefix + "pattern" + id
				   + buildId(capitalize(alt.getId())),
				   alt.buildMatchPattern());
	term_list = factory.makeList(entry, term_list);
      }
    }

    return factory.make("[afuns(<term>),terms(<term>)]", afun_list, term_list);
  }

  //}}}
  //{{{ private String[] genReservedTypeGetter(String type)

  private String[] genReservedTypeGetter(String type)
  {
    String pre = "";
    String post = "";

    if (type.equals("int")) {
      pre = "ATgetInt((ATermInt)";
      post = ")";
    }
    else if (type.equals("real")) {
      pre = "ATgetReal((ATermReal)";
      post = ")";
    }
    else if (type.equals("str")) {
      pre = "ATgetName(ATgetAFun((ATermAppl)";
      post = "))";
    }
      
    String[] result = { pre, post };
    return result;
  }

  //}}}
  //{{{ private String genReservedTypeSetterArg(String type, String id)

  private String genReservedTypeSetterArg(String type, String id)
  {
    if (type.equals("int")) {
      return "ATmakeInt(" + id + ")";
    } else if (type.equals("real")) {
      return "ATmakeReal(" + id + ")";
    } else if (type.equals("str")) {
      return "ATmakeAppl0(ATmakeAFun(" + id + ", 0, ATtrue))";
    } else {
      return id;
    }
  }

  //}}}

  //{{{ private String getATermTypeName(int type)

  private String getATermTypeName(int type)
  {
    switch (type) {
      case ATerm.INT:         return "AT_INT";
      case ATerm.REAL:        return "AT_REAL";
      case ATerm.APPL:        return "AT_APPL";
      case ATerm.LIST:        return "AT_LIST";
      case ATerm.PLACEHOLDER: return "AT_PLACEHOLDER";
      case ATerm.BLOB:        return "AT_BLOB";
      default: throw new RuntimeException("illegal ATerm type: " + type);
    }
  }

  //}}}
}
