#include <avr/io.h>
#include <util/delay.h>

#define TRUE 1
#define FALSE 0

#define FREQ 16
#define ACCURACY 10000


int stateButton=0;
int stateNumber=1;


void offAll()
{
	PORTB=0;
}

void displayNumber()
{
	PORTB=stateNumber;
}
void loopIter()
{
	switch(stateButton)
	{
		case 0:{
			if(PIND & (1<<2)){
				int highCount=0,i;
				for(i=0;i<ACCURACY;i++){
					if(PIND & (1<<2)) highCount++;
				}
				if(highCount>ACCURACY-1){
					stateButton=1;
					offAll();	
				}
				
			}
			break;
		}
		case 1:{
			if( (PIND>>2) & 1 ){}
			else{
				int lowCount=0,i;
				for(i=0;i<ACCURACY;i++){
					if(!((PIND>>2) & 1)) lowCount++;
				}
				if(lowCount>ACCURACY-1){
					stateButton=0;
					stateNumber=(stateNumber+1)%64;
					displayNumber();
				}				


			}
			break;
		}
	}
}


int main(){
	

	DDRD= DDRD & ~(1<<2);//Pin D,2 is input
	DDRB= DDRB | 0b111111;//Pin B,[0:5] as output

	displayNumber();
	while(TRUE){
		loopIter();
	}

	return 0;
}


