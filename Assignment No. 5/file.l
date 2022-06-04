%{
%}
letter [A-Za-z]
digit [0-9]
identifier {letter}({letter}|{digit})*
number {digit}+(\.{digit}+)?
punctuation [;,"#(){}]
operators [+=*<>]
keywords if|else|for|int|while|do|void|char
literal ["].*["]
comment "//".*
headerfile "#".*
multicomment "/*"(.*|\n)*"*/"

%%

{keywords} {printf("Keyword %s is found \n",yytext);}
{digit} {printf("Digit %s is found \n",yytext);}
{identifier} {printf("Identifier %s is found \n",yytext);}
{number} {printf("Number %s is found \n",yytext);}
{punctuation} {printf("Punctuation %s is found \n",yytext);}
{operators} {printf("Operators %s is found \n",yytext);}
{literal} {printf("Literals %s is found \n",yytext);}
{comment} {printf("Comment %s is found \n",yytext);}
{headerfile} {printf("Headerfile %s is found \n",yytext);}
{multicomment} {printf("Multiline comment %s is found \n",yytext);}

%%

int main()
{
	yylex();
}
int yywrap()
{
	return 1;
}
