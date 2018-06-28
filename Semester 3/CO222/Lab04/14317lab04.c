#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define RESET       0
#define BRIGHT      1

#define BLACK       0
#define RED         1
#define GREEN       2
#define YELLOW      3
#define BLUE        4
#define MAGENTA     5
#define CYAN        6
#define WHITE       7

void textcolor(int attr, int fg, int bg);

void textcolor(int attr, int fg, int bg)
{   char command[13];

    /* Command is the control command to the terminal */

    /* textcolor(BRIGHT,BLACK,WHITE) will have characters printed in
    black in white background */
    sprintf(command, "%c[%d;%d;%dm", 0x1B, attr, fg + 30, bg + 40);
    printf("%s", command);
}

int main(int argc,char** argv)
{
    if(argc==4)
    {    
    int fg,bg;
    if(strcmp(argv[2],"black")==0)
        bg=0;
    else if(strcmp(argv[2],"red")==0)
        bg=1;
    else if(strcmp(argv[2],"green")==0)
        bg=2;
    else if(strcmp(argv[2],"yellow")==0)
        bg=3;
    else if(strcmp(argv[2],"blue")==0)
        bg=4;
    else if(strcmp(argv[2],"magenta")==0)
        bg=5;
    else if(strcmp(argv[2],"cyan")==0)
        bg=6;
    else if(strcmp(argv[2],"white")==0)
        bg=7;    
    else
        bg=-1;

    if(strcmp(argv[3],"black")==0)
        fg=0;
    else if(strcmp(argv[3],"red")==0)
        fg=1;
    else if(strcmp(argv[3],"green")==0)
        fg=2;
    else if(strcmp(argv[3],"yellow")==0)
        fg=3;
    else if(strcmp(argv[3],"blue")==0)
        fg=4;
    else if(strcmp(argv[3],"magenta")==0)
        fg=5;
    else if(strcmp(argv[3],"cyan")==0)
        fg=6;
    else if(strcmp(argv[3],"white")==0)
        fg=7;   
    else
        fg=-1;
    if(strcmp(argv[1],"donut")==0)
    { 
        if(bg!=-1)
        {    
            if(fg!=-1)
            {    
               double radius;
	           scanf("%lf",&radius);
	           double x=radius/2.0;
	           double i,j;
               for(i=-x+0.5;i<x+0.5;i++)
               {
                    for(j=-x+0.5;j<x+0.5;j++)
                    {
                        if(i*i+j*j<(x*x))
                        {	
            	           if(i*i+j*j<(x*x)/4)
                            {    
                                textcolor(RESET,fg,bg);
            		            printf(" ");
                            }    
            	           else
                            {    
                                textcolor(RESET,bg,fg);
            	                printf(" ");
                                textcolor(RESET,fg,bg);
                            } 
                        }       	
                        else
                        {    
                            textcolor(RESET,fg,bg);
                            printf(" ");
                        }      
                    } 
                    textcolor(RESET,fg,bg); 
                    printf("\n");   
                }          
                textcolor(RESET,WHITE,BLACK); 
            }
            else
                printf("Foreground color is not available\n");   
        }
        else
            printf("Background color is not available\n");         
    }
    else if(strcmp(argv[1],"checker")==0)
    {
        int i,j,k,z;
        if(bg!=-1)
        {    
            if(fg!=-1)
            {    
                for(z=0;z<4;z++)
                {    
                    for(i=0;i<8;i++)
                    {
                        for(j=0;j<4;j++)
                        {
                            for(k=0;k<8;k++)
                            {   
                                textcolor(RESET,WHITE, fg); 
                                printf(" ");
                                textcolor(RESET,WHITE,bg); 
                            }    
                            for(k=0;k<8;k++)
                            {   
                                textcolor(RESET,WHITE, bg); 
                                printf(" ");
                                textcolor(RESET,WHITE,bg); 
                            }    
                        }
                        printf("\n");
                    }    
                    textcolor(RESET,WHITE,BLACK);      
                    for(i=0;i<8;i++)
                    {
                        for(j=0;j<4;j++)
                        {
                            for(k=0;k<8;k++)
                            {   
                                textcolor(RESET,WHITE, bg); 
                                printf(" ");
                                textcolor(RESET,WHITE,bg); 
                            }    
                            for(k=0;k<8;k++)
                            {    
                                textcolor(RESET,WHITE, fg);
                                printf(" ");
                                textcolor(RESET,WHITE,bg); 
                            }    
                        }
                        printf("\n");
                    }          
                }
                textcolor(RESET,WHITE,BLACK); 
            }   
            else
                printf("Foreground color is not available\n");
        }     
        else
            printf("Background color is not available\n");      
    }  
    else
        printf("Requested figure is not available\n");
    }
    else
        printf("Not enough arguments\n");
	return 0;
}