module alu(Result,Data1,Data2,Select);
input[8:0] Data1,Data2;
input[3:0] Select;
Output [8:0] out;
reg out;

always@(DATA1,DATA2,Select)
begin
case(Select)
000:Result=Data1;
001:Result=Data1+Data2;
010:Result=Data1&Data2;
011:Result=Data1||Data2;



endcase