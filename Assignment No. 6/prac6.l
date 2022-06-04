%{
#include <stdio.h>
#include "y.tab.h"

%}

DIGIT [0-9]
REAL {DIGIT}+[.]{DIGIT}*
LETTER [A-Za-z]
ASSIGN =

%%

[\t ] ;
int {return (INT);}
float {return (FLOAT);}
char {return (CHAR);}
boolean {return (BL);}
true|false {return BLVAL;}
['][^\t\n]['] {return CHVAL;}
[a-zA-z]+[a-zA-z0-9_]*  {return ID;}
{REAL} {return REAL;}
{DIGIT}+ {return NUM;}
"," {return COMMA;}
";" {return SC;}
{ASSIGN} {return AS;}
\n return NL;
. ;

%%


int yywrap()
{
return 1;
}

