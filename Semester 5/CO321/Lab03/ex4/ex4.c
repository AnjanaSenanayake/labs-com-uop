//largest interval possible in the 8 bit TIMER0 is 16.384 ms
//hence prescaler is 1:1024
//counter set to 0


#include<avr/io.h>
#include<util/delay.h>
void delayTimer0(){
	TCNT0 = 0X00;
	TCCR0A = 0X00;
	TCCR0B = 0X05;

	while((TIFR0 & 0X01)==0);
	TCCR0A = 0X00;
	TCCR0B = 0X00;
	TIFR0 = 0X01;

}

int main(void){
	DDRB = DDRB | (1<<5);
	while(1){
		PORTB = PORTB ^ (1<<5);
		delayTimer0();
	}
}
