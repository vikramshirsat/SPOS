#include<stdio.h>
#include<unistd.h>
int main()
{
  int choice;
  int pid;
   printf("1. ps\n2. fork 3. exec\n4. join \n5. wait\n\n");
   printf("enter ypur choice: ");
   scanf("%d",&choice);
   
switch(choice) 
{
   case 1: execlp("ps","ps",NULL);
           break;
   case 2: pid=fork();
           if(pid==0)
           { 
            printf("In a child process \n");
            printf("Child process pid = %d \n",getpid());
           }
           else
           {
            printf("parent process pid = %d \n",getpid());
           }
           break;
   case 3: execlp("ls","ls","-l",NULL);
           printf("This will not printed because exec replace the parent process with current process \n");
           break;
   case 4: execlp("join","join","file1.txt","file2.txt",NULL);
           break;
   case 5: pid = fork();
           if(pid==0)
           { 
            printf("In a child process \n");
            sleep(2);
           }
           else
           {
            waitpid();
            printf("In parent process \n");
           }
           break;
   default: printf("Invalid input....");
            break;
   }
return 0;
}
