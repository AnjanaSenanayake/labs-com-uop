#include<stdio.h>

int main()
{
	char player1,player2;
	scanf("%c %c",&player1,&player2);
	//scanf("%c",&player2);

	if(player1 != 'R' && player1 != 'P' && player1 != 'C' && player1 !='L' && player1 !='S')	
		printf("wrong input\n");
	else if(player2 != 'R' && player2 != 'P' && player2 != 'C' && player2 !='L' && player2 !='S')
		printf("wrong input\n");
	else if(player1==player2)
		printf("Tie\n");
	
		else if (player1=='S')
		{
			if(player2=='R' || player2=='C')
				printf("player 1 wins\n");
			else
				printf("player 2 wins\n");		
		}
		else if(player1=='P')	
		{
			if(player2=='R' || player2=='S')
				printf("player 1 wins\n");
			else
				printf("player 2 wins\n");
		}
		else if(player1=='R')	
		{
			if(player2=='L' || player2=='C')
				printf("player 1 wins\n");
			else
				printf("player 2 wins\n");
		}	
		else if(player1=='L')
		{
			if(player2=='P' || player2=='S')
				printf("player 1 wins\n");
			else
				printf("player 2 wins\n");
		}	
		else if(player1=='C')
		{
			if(player2=='P' || player2=='L')
			{
				printf("player 1 wins\n");
			}
			else
			{
			printf("player 2 wins\n");
			}
		}
	//else
		//printf("wrong input\n");
	return 0;
}	
