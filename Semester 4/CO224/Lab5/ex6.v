module decoder(q0,q1,q2,q3,a,b);

input a,b;
output q0,q1,q2,q3;
wire ta,tb;

not nt1(ta,a);
not nt2(tb,b);
and and1(q0,ta,tb);
and and2(q1,ta,b);
and and3(q2,a,tb);
and and4(q3,a,b);

endmodule

module stimulus;

reg a,b;
wire q0,q1,q2,q3;
decoder decoder1(q0,q1,q2,q3,a,b);

integer i;
initial
begin

	a =1'b0;
	b =1'b0;
	#5
	$display("A = %b, B = %b",a,b);
	#5
	$display("Q3 = %b, Q2 = %b, Q1 = %b, Q0 = %b",q3,q2,q1,q0);
	$display("");

	a =1'b0;
	b =1'b1;
	#5
	$display("A = %b, B = %b",a,b);
	#5
	$display("Q3 = %b, Q2 = %b, Q1 = %b, Q0 = %b",q3,q2,q1,q0);
	$display("");

	a =1'b1;
	b =1'b0;
	#5
	$display("A = %b, B = %b",a,b);
	#5
	$display("Q3 = %b, Q2 = %b, Q1 = %b, Q0 = %b",q3,q2,q1,q0);
	$display("");

	a =1'b1;
	b =1'b1;
	#5
	$display("A = %b, B = %b",a,b);
	#5
	$display("Q3 = %b, Q2 = %b, Q1 = %b, Q0 = %b",q3,q2,q1,q0);
	$display("");

end
endmodule
