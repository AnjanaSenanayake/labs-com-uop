module mux4(out,i0,i1,i2,i3,s1,s0);

input i0,i1,i2,i3;
input [3:0] s0,s1;
output out;
wire s1n,s0n;
wire y0,y1,y2,y3;

not nt1(s1n,s1);
not nt2(s0n,s0);
and and1(y0,i0,s1n,s0n);
and and2(y1,i1,s1n,s0);
and and3(y2,i2,s1,s0n);
and and4(y3,i3,s1,s0);
or or1(out,y0,y1,y2,y3);

endmodule

module stimulus;

reg i0,i1,i2,i3,t1,t0;
reg [3:0] s1,s0;
wire out;
mux4 mux(out,i0,i1,i2,i3,s1,s0);
integer i;
initial
begin

	i0=1'b0;i1=1'b1;i2=1'b0;i3=1'b1;
	s0 =4'b0101;
	s1 =4'b0011;

	for(i=3;i>=0;i=i-1)
	begin
	s0=i;
	s1=i;
	#2
	$display("s1 = %b, s0 = %b",s1[i],s0[i]);
	#2
	$display("i0 = %b, i1 = %b, i2 = %b, i3 = %b, out = %b",i0,i1,i2,i3,out);
	$display("");
	end
	i0=1'b1;i1=1'b0;i2=1'b1;i3=1'b0;
	s0 =4'b0101;
	s1 =4'b0011;
	
	for(i=3;i>=0;i=i-1)
	begin
	s0=i;
	s1=i;
	#2
	$display("s1 = %b, s0 = %b",s1[i],s0[i]);
	#2
	$display("i0 = %b, i1 = %b, i2 = %b, i3 = %b, out = %b",i0,i1,i2,i3,out);
	$display("");
	end
		
end
endmodule
