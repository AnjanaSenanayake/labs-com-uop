#include<avr/io.h>
#include<util/delay.h>
void delayTimer0(){
	TCNT0 = 0X00;
	TCCR0A = 0X00;
	TCCR0B = 0X04;

	unsigned char i =0;
	for(i=0;i<24;i++){
		while((TIFR0 & 0X01)==0);
		TCNT0 = 0X00;
		TIFR0 = 0X01;
	}
	TCNT0 = 0X96;
	while((TIFR0 & 0X01)==0);
	TIFR0 = 0X01;
	TCCR0A = 0X00;
	TCCR0B = 0X00;

}

int main(void){
	DDRB = DDRB | (1<<5);
	while(1){
		PORTB = PORTB ^ (1<<5);
		delayTimer0();
		//
	}
}
