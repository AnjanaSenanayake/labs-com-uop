#include <avr/io.h>
#include <avr/interrupt.h>
volatile int overflow_count;


ISR(TIMER0_OVF_vect){ 
	if (overflow_count == 12){
		TCNT0 = 0xCB;
		overflow_count = 0;
		PORTB = PORTB ^ 1<<4;
	}
	overflow_count ++;
	TCNT0 = 0x00;
}

ISR(TIMER1_OVF_vect){ 
	TCNT1 = 0x85EE;
	PORTB = PORTB ^ 1<<5;
}

int main(void) {
	DDRB = 0xFF; 
	PORTB = 0x00; // initial value of PORTB



	//directly by setting the TCNT1 we can get the desired delay
	TCCR1B = 0x04; //the 1:256 prescaler is used
	TCCR1A = 0x00; 
	TIMSK1 = 0x01; 
	TCNT1 = 0x85EE;

	//we need a nested loop to do this.
	overflow_count = 0; //we keep a count of how many overflows happened. (12 overflows)
	TCCR0A = 0x00; 
	TCCR0B = 0x04; 
	TIMSK0 = 0x01; 
	TCNT0 =  0x00;

	sei(); 
	while (1){} 
	return 0;
}