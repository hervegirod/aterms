ATbool result;

/* Sets result to ATtrue and rval to 16. */
result = ATmatch(ATmake("f(16)"), "f(<int>)", &ival);

/* Sets result to ATtrue and rval to 3.14. */
result = ATmatch(ATmake("3.14"), "<real>", &rval);

/* Sets result to ATfalse because f(g) != g(f) */
result = ATmatch(ATmake("f(g)"), "g(f)");
