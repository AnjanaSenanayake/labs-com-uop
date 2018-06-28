//largest interval possible in the 8 bit TIMER0 is 16.384 ms hence 500ms delay cannot be generated.

#include<avr/io.h>

void delayTimer0(){
	// TCNT0 = 0X00;
	// TCCR0A = 0X00;
	// TCCR0B = 0X05;

	// while((TIFR0 & 0X01)==0);
	// TCCR0A = 0X00;
	// TCCR0B = 0X00;
	// TIFR0 = 0X01;

}

int main(void)
{
	DDRB=DDRB | (0b00001111);// setting port B to Output
	while(1)
	{
		PORTB = 0b00000001;
		int i;
		while(1)
		{
			if(PORTB==0b00000001)
			{
				for(i=0;i<3;i++)
				{
					PORTB=PORTB<<1;
					delayTimer0();
				}	
			}
			else if(PORTB==0b00001000)
			{
				for(i=0;i<3;i++)
				{
					PORTB=PORTB>>1;
					delayTimer0();
				}		
			}	
		}	
	}	
}