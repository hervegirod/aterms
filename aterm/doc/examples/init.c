#include <stdio.h>
#include <aterm1.h>

int
main(int argc, char *argv[])
{
    ATerm bottomOfStack;                /* Used in initialization of library    */

    ATinit(argc, argv, &bottomOfStack); /* Initialize the ATerm library.        */
    foo();                              /* Call to code that works with ATerms. */

    return 0;                           /* End of program. */
}
