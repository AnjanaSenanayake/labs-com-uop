#include<avr/io.h>
#include<util/delay.h>
#define BLINK_DELAY_MS 200

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
					_delay_ms(BLINK_DELAY_MS);
				}	
			}
			else if(PORTB==0b00001000)
			{
				for(i=0;i<3;i++)
				{
					PORTB=PORTB>>1;
					_delay_ms(BLINK_DELAY_MS);
				}		
			}	
		}	
	}	
}