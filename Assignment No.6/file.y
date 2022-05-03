%{ #include <stdio.h>
   #include <stdlib.h>
   int yylex();	int yyerror(); 
%}
%token TYPE ID SC COMMA NL
%%
start: TYPE varlist SC NL {printf("statement valid \n"); exit(1);}
varlist: varlist COMMA ID | ID;
%%
int main()
{
	yyparse();
        
}
int yyerror()
{
 	printf("InValid statement\n");
}
int yywrap()
{
	return 1;
}
