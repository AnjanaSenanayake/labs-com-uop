#include<avr/io.h>
#include<util/delay.h>
void delayTimer0(){
	TCNT0 = 0X74;
	TCCR0A = 0X00;
	TCCR0B = 0X02;

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
