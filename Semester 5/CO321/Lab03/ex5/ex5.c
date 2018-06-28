//largest interval possible in the 8 bit TIMER0 is 16.384 ms
//hence prescaler is 1:1024
//counter set to 0


#include<avr/io.h>
#include<util/delay.h>
void delayTimer0(){
	TCNT1 = 0XBDC;	//65536-62500
	TCCR1A = 0X00;
	TCCR1B = 0X04;

	while((TIFR1 & 0X01)==0);
	TCCR1A = 0X00;
	TCCR1B = 0X00;
	TIFR1 = 0X01;
	// 	TCNT0 = 0X00;
	// TCCR0A = 0X00;
	// TCCR0B = 0X04;

	// unsigned char i =0;
	// for(i=0;i<24;i++){
	// 	while((TIFR0 & 0X01)==0);
	// 	TCNT0 = 0X00;
	// 	TIFR0 = 0X01;
	// }
	// TCNT0 = 0X96;
	// while((TIFR0 & 0X01)==0);
	// TIFR0 = 0X01;
	// TCCR0A = 0X00;
	// TCCR0B = 0X00;

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
