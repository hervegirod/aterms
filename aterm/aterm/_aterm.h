
#ifndef _ATERM_H
#define _ATERM_H

#include "aterm2.h"

typedef struct ProtEntry
{
	struct ProtEntry *next;
	ATerm *term;
} ProtEntry;

typedef struct
{
	ATerm *start;
	int    size;
} ProtectedArray;


extern ATbool silent;
extern ProtEntry **at_prot_table;
extern int at_prot_table_size;
extern ProtectedArray *at_protected_arrays;
extern int at_nrprotected_arrays;

void AT_markTerm(ATerm t);
void AT_unmarkTerm(ATerm t);
int  AT_calcTextSize(ATerm t);
int  AT_calcCoreSize(ATerm t);
int  AT_calcSubterms(ATerm t);
int  AT_calcUniqueSubterms(ATerm t);
int  AT_calcUniqueSymbols(ATerm t);
int  AT_calcTermDepth(ATerm t);
void AT_writeToStringBuffer(ATerm t, char *buffer);
void AT_assertUnmarked(ATerm t);
void AT_assertMarked(ATerm t);
int AT_calcAllocatedSize();

#endif
