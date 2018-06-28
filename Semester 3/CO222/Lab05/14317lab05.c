/*
	A program to draw a donut or checker with given foreground and and background colors.
*/	

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
int getBackcolor(char* backcolor){  
	//A function to get the background color 
 	char* color[8]={"black","red","green","yellow","blue","magenta","cyan","white"};//An array declared to compare the the input colors.
 	int i;
 	for(i=0;i<8;i++)
 	{
 		if(strcmp(backcolor,color[i])==0)
 			return i;
 	}
 	return -1;
}	
int getForecolor(char* forecolor){   
	//A Function to get the foreground color 
	char* color[8]={"black","red","green","yellow","blue","magenta","cyan","white"};
 	int i;
 	for(i=0;i<8;i++)
 	{
 		if(strcmp(forecolor,color[i])==0)
 			return i;
 	}
 	return -1;
} 
void drawDonut(char* backcolor,char* forecolor){ 
	//A function to print a donut in the terminal 
	int bg=getBackcolor(backcolor);										//The background color is selected from the getBackcolor function 
	int fg=getForecolor(forecolor);										//The foreground color is selected from the getForecolor function 
	double radius;
	scanf("%lf",&radius);												//A prompt for user to enter the radius for the donut
	double x=radius/2.0;
	double i,j;
    for(i=-x+0.5;i<x+0.5;i++){
        for(j=-x+0.5;j<x+0.5;j++){
            if(i*i+j*j<(x*x)){
            	if(i*i+j*j<(x*x)/4){ 
                    textcolor(RESET,fg,bg);								//Background color is initialized using the given function
            		printf(" "); 
                }    
            	else{
                    textcolor(RESET,bg,fg);								//Foreground color is initialized using the given function
            	    printf(" ");
                    textcolor(RESET,fg,bg);
                } 
            }       	
            else{
                textcolor(RESET,fg,bg);
                printf(" ");
            }      
        } 
        textcolor(RESET,fg,bg);
        printf("\n");   
    }          
    textcolor(RESET,WHITE,BLACK);										//Background color is set to black and textcolor to white
}
void drawChecker(char* backcolor,char* forecolor){
	//A function to print a checker in the  terminal
	int i,j,k,z;
	int bg=getBackcolor(backcolor);										//Getting the background color from the functiion 'getBackcolor' 
	int fg=getForecolor(forecolor);										//Getting the foreground color from the functiion 'getForecolor' 
	for(z=0;z<4;z++){
        for(i=0;i<8;i++){
            for(j=0;j<4;j++){
                    textcolor(RESET,WHITE, fg);							//Foreground color is initialized using the given function 
                    printf("        ");
                    textcolor(RESET,WHITE,bg);							//Background color is initialized using the given function 
                    textcolor(RESET,WHITE, bg);
                    printf("        ");
                    textcolor(RESET,WHITE,bg); 
            }
            printf("\n");
        }    
        textcolor(RESET,WHITE,BLACK);      
        for(i=0;i<8;i++){
            for(j=0;j<4;j++){
                    textcolor(RESET,WHITE, bg); 
                    printf("        ");
                    textcolor(RESET,WHITE,bg);    
                    textcolor(RESET,WHITE, fg);
                    printf("        ");
                    textcolor(RESET,WHITE,bg);  
            }
            printf("\n");
        }          
    }
    textcolor(RESET,WHITE,BLACK); 
}

int main(int argc,char** argv){
    if(argc==4){														//Check for number of arguments in the command line
    	int fore=getForecolor(argv[3]);									//Getting the foreground color from the function 'getForecolor' 
		int back=getBackcolor(argv[2]);									//Getting the background color from the function 'getBackcolor' 
    	if(strcmp(argv[1],"donut")==0){									//Comparing the argv[1] with "donut"
  			if(back!=-1){												//Check whether the background color is available
  				if(fore!=-1){											//Check whether the background color is available
  					drawDonut(argv[2],argv[3]);
  				}
  				else{
  					printf("Foreground color is not available\n");		//Warning messages to the user for input errors
  				}		
  			}
  			else{
  				printf("Background color is not available\n");
  			}
  		}
  		else if(strcmp(argv[1],"checker")==0){							//Comparing the argv[1] with "checker"
  			if(back!=-1){
  				if(fore!=-1){											
  					drawChecker(argv[2],argv[3]);
  				}
  				else{
  					printf("Foreground color is not available\n");
  				}	
  			}
  			else{
  				printf("Background color is not available\n");
  			}	
  		}
  		else{
  			printf("Requested figure is not available\n");
  		}	
	}
	else if (argc>4){													//Check whether the number of command line arguments are greater than 4
		printf("Too many arguments\n");
	}
	else{
		printf("Not enough arguments\n");
	}
	return 0;	
}