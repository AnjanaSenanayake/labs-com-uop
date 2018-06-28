module my_or(z,x,y);

input x,y;
output z;
wire t1,t2;

nand nnd1(t1,x,x);
nand nnd2(t2,y,y);
nand nnd3(z,t1,t2);
endmodule

module my_and(z,x,y);

input x,y;
output z;
wire t1;
nand nnd1(t1,x,y);
nand nnd2(z,t1,t1);

endmodule

module my_not(z,x);

input x;
output z;

nand nnd1(z,x,x);

endmodule

module my_xor(z,x,y);

input x,y;
output z;
wire t1,t2,t3,t4;

my_not mynot1(t1,y);
my_not mynot2(t2,x);
my_and myand1(t3,x,t1);
my_and myand2(t4,y,t2);
my_or myor(z,t3,t4);

endmodule


module stimulus;

reg a,b;
wire c;
my_xor myxor(c,a,b);

initial
begin
	a=1'b0;
	b=1'b0;
	#3
	$display("%b ^ %b = %b",a,b,c);
	a=1'b0;
	b=1'b1;
	#3
	$display("%b ^ %b = %b",a,b,c);
	a=1'b1;
	b=1'b0;
	#3
	$display("%b ^ %b = %b",a,b,c);
	a=1'b1;
	b=1'b1;
	#3
	$display("%b ^ %b = %b",a,b,c);
	
end
endmodule
