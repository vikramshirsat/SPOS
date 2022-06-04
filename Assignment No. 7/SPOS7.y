%{ 
  #include <stdio.h>
  int yylex();
  int yyerror();
%}

%token VERB ADVERB PREPOSITION CONJUCTION ADJECTIVE PRONOUN NOUN NL 
 
%%
sentence : simple_sentence NL {printf("simple sentence");}; | compound_sentence NL {printf("compound sentence");};
simple_sentence : subject verb object | subject verb object prephase;
compound_sentence : simple_sentence CONJUCTION simple_sentence | compound_sentence CONJUCTION simple_sentence ;
subject : NOUN | PRONOUN | ADJECTIVE subject;
verb : VERB |ADVERB verb;
object : NOUN | ADJECTIVE object;
prephase : PREPOSITION|NOUN;

%%

int main()
{
yyparse();
return 0;
}
int yyerror()
{
printf("invalid sentence");
}
int yywrap()
{
return 1;
}

