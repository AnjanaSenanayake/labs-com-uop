#include <stdio.h>	
#include <string.h>
#include <stdlib.h>


int main(int argc,char** argv)
{
	FILE *fp;
	int width,space,spaceleft;
	char *word;
	int charcount=0;
	char ch,character;
	if(argc!=3)
	{
		printf("Usage: ./prog <width> <input-file>\n");
		return 1;
	}	
	fp=fopen(argv[2],"r");
	width=atoi(argv[1]);
	if(width<0)
	{	
		width=1;	
	}		
	else
	{	
		while (!feof(fp)) 
		{
	 		while(ch=fgetc(fp))
	 		{	
				if (ch !=' ' && ch != '\n' && !feof(fp)) 
				{ 
		   			++charcount; 
				}	
				else
					break;
			}
			if(charcount>width)
			{
				printf("Word length must me less than screen width\n");	
				return 0;
			}	
			charcount=0;
		}
	}	
	spaceleft=width;
	fseek(fp,0*sizeof(char),0);
	while(!feof(fp))
	{
		fscanf(fp,"%s",word);
		space=strlen(word);
		if(space<=spaceleft)
		{
			printf("%s",word);
			spaceleft=spaceleft-space-1;
		}
		else
		{
			printf("\n");
			printf("%s",word);
			spaceleft=width-space-1;
		}	
		fscanf(fp,"%c",&character);
		if(character=='\n')
		{
			printf("\n");
			spaceleft=width;
		}	
		else
			printf(" ");
	}	
	fclose(fp);
}


