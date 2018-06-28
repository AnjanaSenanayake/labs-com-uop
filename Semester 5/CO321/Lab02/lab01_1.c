#include<avr/io.h>
#include<util/delay.h>
#define BLINK_DELAY_MS 300

int main(void)
{
	DDRB=DDRB | (0b00001111);// setting port B to Output
	while(1)
	{
		PORTB = 0b00001111;
		_delay_ms(BLINK_DELAY_MS);
		PORTB = 0b00000000;		
		_delay_ms(BLINK_DELAY_MS);
	}	
}