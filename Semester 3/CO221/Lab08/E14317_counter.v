//CO221 - Digital Design - 2017
//Lab 8 : 4-bit Counter implementation in verilog

// Top level stimulus module
module testbed;

	// Declare variables for stimulating input
	reg CLK, CLEAR_BAR;
	wire [3:0] NUM;
	
	// Monitor changes in NUM and print once a change happens
	initial
		$monitor($time," Count : %d",NUM);
		
	//Instantiate the design block counter	
	//NUM is the 4-bit output from the counter
	//CLK is the clock signal
	//The counter should increment at each falling edge of the clock cycle 
	//CLEAR_BAR is the signal that asynchronously clears the counter. A CLEAR_BAR=0 should clear the counter.
	rippleCounter mycounter(NUM,CLK,CLEAR_BAR);	
	// reset	
	initial
	begin	
		CLEAR_BAR=1'b0;	
		#5 CLEAR_BAR=1'b1;
		#500 CLEAR_BAR=1'b0;
		#50 CLEAR_BAR=1'b1;
	end			
	// Set up the clock to toggle every 10 time units	
	initial
	begin
		
		//generate files needed to plot the waveform
		//you can plot the waveform generated after running the simulator by using gtkwave	
		$dumpfile("wavedata.vcd");
		$dumpvars(0,testbed);	
		CLK = 1'b0;
					
	end

	always #10 CLK = ~CLK; // An indefinite loop in which clock is complemented
								// after a delay of 10 time units	

	// Finish the simulation after 700 time units
	initial
	begin
		#700 $finish;
	end
	
endmodule
//////////////////////////////////////////////////////////////////////////////////////////////////////

//SR latch
module srlatch(q,qcomp,s,r,set,reset);

	input s,r,set,reset;
	output q,qcomp;

	not not0(nreset,reset);
	not not1(nset,set); 
	nand nand1(q,r,qcomp,nset);
	nand nand2(qcomp,s,q,nreset);

endmodule

//D latch
module dlatch(q,qcomp,d,clk,set,reset);
	
	input d,clk,set,reset;
	output q,qcomp;
	wire t1,t2,dnot;

	not not2(dnot,d);
	nand nand3(t1,d,clk);
	nand nand4(t2,dnot,clk);
	srlatch sr(q,qcomp,t2,t1,set,reset);

endmodule

//D flipflop
module dflipflop(q,d,clk,set,reset);

	input d,clk,set,reset;
	output q;
	wire t3,clknot;

	not not3(clknot,clk);
	dlatch dlatch1(t3,,d,clk,set,reset);
	dlatch dlatch2(q,,t3,clknot,set,reset);

endmodule

//T flipflop
module	tflipflop(t,q,clk,set,reset);

	input t,clk,set,reset;
	output q;
	wire t5;

	xor xor1(t5,t,q); 
	dflipflop dflipflop1(q,t5,clk,set,reset);

endmodule

//4 Bit Ripple Counter
module rippleCounter(a,clk,preset);

	input clk,preset;
	output [3:0] a;
	
	not not4(npreset,preset);
	tflipflop tflipflop1(1,a[0],clk,0,npreset);
	tflipflop tflipflop2(1,a[1],a[0],0,npreset);
	tflipflop tflipflop3(1,a[2],a[1],0,npreset);
	tflipflop tflipflop4(1,a[3],a[2],0,npreset);

endmodule