/***************************************************************************************
*    Title: Processor with basic components in verilog
*    Author: Anjana Senanayake
*    Date: 27/08/2017  
*    Code version: v1.0
*    Availability: https://github.com/AnjanaSenanayake/Processor
*
***************************************************************************************/

module testbench;

reg clk;
reg reset;
wire Read,Write;
wire [7:0] result,OUT1,OUT2,Data2,mux1out,immediate,mux2out,muxspecialout,read_data;
wire [3:0] Select;

initial
    
	 $monitor("Res=%b O1=%b O2=%b D2=%b mux1=%b imm=%b mux2=%b muxspecial=%b wrt_sig=%b write=%b rd_sig=%b read=%b S=%b clk=%b R=%b",result,OUT1,OUT2,Data2,mux1out,immediate,mux2out,muxspecialout,Write,result,Read,read_data,Select,clk,reset);
	 
	// $strobe("Res=%b O1=%b O2=%b D2=%b mux1=%b imm=%b mux2=%b muxspecial=%b read=%b S=%b clk=%b R=%b",result,OUT1,OUT2,Data2,mux1out,immediate,mux2out,muxspecialout,read_data,Select,clk,reset);
    
    initial
 begin
    $dumpfile("testbench.vcd");
    $dumpvars(0,testbench);
 end
    
proccessor myproccessor(result,OUT1,OUT2,Data2,mux1out,immediate,mux2out,muxspecialout,read_data,Read,Write,Select,clk,reset);

initial
begin
	clk=1'b1;
	reset=0;
end

always #5 clk=~clk;

initial
begin
	#120 $finish;
end
endmodule

///////////////////////////////////////////////////////////////////////////////////////////////
module alu(out,Data1,Data2,Select);
input [7:0] Data1,Data2;
input [3:0] Select;
output reg [7:0] out;

always@(Data1,Data2,Select)
begin
case(Select)
3'b000:out<=Data1;
3'b001:out<=Data1+Data2;
3'b010:out<=Data1&Data2;
3'b011:out<=Data1|Data2;
3'b100:out<=Data1;
3'b101:out<=8'dx;
endcase
end
endmodule

module regfile8x8a(OUT1,OUT2,clk,INaddr,OUT1addr,OUT2addr,IN);

input clk;
input [7:0] IN;
output [7:0] OUT1,OUT2;
input [2:0] INaddr,OUT1addr,OUT2addr;
reg [7:0] reg0, reg1, reg2, reg3,reg4,reg5,reg6,reg7;

assign OUT1 = (OUT1addr==3'b000)?reg0:
(OUT1addr==3'b001)?reg1:
(OUT1addr==3'b010)?reg2:
(OUT1addr==3'b011)?reg3:
(OUT1addr==3'b100)?reg4:
(OUT1addr==3'b101)?reg5:
(OUT1addr==3'b110)?reg6:
(OUT1addr==3'b111)?reg7:0;
// add until 8 //
assign OUT2 = OUT2addr == 0 ? reg0 :
OUT2addr == 1 ? reg1 :
OUT2addr == 2 ? reg2 :
OUT2addr == 3 ? reg3 :
OUT2addr == 4 ? reg4 :
OUT2addr == 5 ? reg5 :
OUT2addr == 6 ? reg6 :
OUT2addr == 7 ? reg7 :0;
//add until 8//
always @(negedge clk) 
begin
case(INaddr)
3'b000:reg0=IN;
3'b001:reg1=IN;
3'b010:reg2=IN;
3'b011:reg3=IN;
3'b100:reg4=IN;
3'b101:reg5=IN;
3'b110:reg6=IN;
3'b111:reg7=IN;
// your code here
endcase
end // always @ (negedgeclk)
endmodule

module CU(OUT1addr,OUT2addr,INaddr,busy_signal,Read,Write,Address,immediate,Select,imm_signal,comp_signal,instruction,busy_wait);

reg busy;
input busy_wait;
input [31:0] instruction;
output reg [7:0] immediate;
output reg imm_signal;
output reg [3:0] Select;
output reg [2:0] OUT1addr;
output reg [2:0] OUT2addr;
output reg [2:0] INaddr;
output reg [7:0] Address;
output reg Read;
output reg Write;
output reg busy_signal=1'b1;
output reg comp_signal;
always @(instruction) 
begin
	imm_signal = 1'b0;
	comp_signal = 1'b0;
	Read = 1'b0;
	Write = 1'b0;
	busy = 1'b1;
	Select = instruction[26:24];
	case(instruction[31:24])
		8'b00000100:
		begin
			Address = instruction[23:16];
			imm_signal = 1'b0;
			Write = 1;
			Read = 0;
			OUT2addr = instruction[2:0];
			busy = 1'b0;
		end	
		8'b00000101:
		begin
			Address = instruction[7:0];
			INaddr = instruction[18:16];
			Write = 0;
			Read = 1;
			busy = 1'b0;			
		end		
		default:
            busy=1'b1;
	endcase		
	if (busy)
	begin
		immediate = instruction[7:0];
		Select = instruction[26:24];
		case (instruction[31:24])
			8'b00001000:
				imm_signal = 1'b1;
			8'b00001001:						
				comp_signal = 1'b1;		
			default:;
		endcase
		if (busy)
		begin
			INaddr = instruction[18:16]; //& busy_wait;
			OUT2addr = instruction[2:0]; //& busy_wait;
			OUT1addr = instruction[10:8]; //& busy_wait;
		end
	end
	busy_signal = busy;
end
endmodule

module mux(out,select,input1,input2);

input select,clk;
input [7:0] input1,input2;
output reg [7:0] out;
always @* begin
	if (select==1) 
		out = input1;
	else 
		out = input2;
end
endmodule

module muxspecial(out,select,input1,input2);

input select,clk;
input [7:0] input1,input2;
output reg [7:0] out;
always @* begin
	if (select==0) 
		 out = input1;
	else 
		 out = input2;
end
endmodule

module compliment(out,in);
input [7:0] in;
output [7:0] out;
reg [7:0] comp=8'b11111111;
assign out=(comp-in)+8'b00000001;
endmodule

module regInstructions(instruction,clk,Read_Addr,busy_signal);
input clk,busy_signal;
input [3:0] Read_Addr;
output reg [31:0] instruction;
reg reg_clock=1'b1;

reg [31:0] addr1 = 32'b00001000000001000000000011111111;		// loadi 4, X, 0xFF
reg [31:0] addr2 = 32'b00001000000001100000000010101010;		// loadi 6, X, 0xAA
reg [31:0] addr3 = 32'b00001000000000110000000010111011;		// loadi 3, X, 0xBB
reg [31:0] addr4 = 32'b00000001000001010000011000000011;		// add   5, 6, 3
reg [31:0] addr5 = 32'b00000010000000010000010000000101;		// and   1, 4, 5
reg [31:0] addr6 = 32'b00000011000000100000000100000110;		// or    2, 1, 6 
reg [31:0] addr7 = 32'b00000000000001110000000000000010;		// mov   7, x, 2
reg [31:0] addr8 = 32'b00001001000001000000011100000011;		// sub   4, 7, 3
reg [31:0] addr9 = 32'b00000100111111110000000000000100;		// store 4, X, 0xFF
reg [31:0] addr10 = 32'b00000101000001100000000011111111;		// load 6, X, 0xFF
reg [31:0] addr11 = 32'b00001001000001000000011100000110;		// sub   4, 7, 6
reg [31:0] addr12 = 32'b00000000000000000000000000000000;		// do nothing

always @(negedge clk) 
begin
  //  if(!busy_signal)
 //   begin
 //       repeat (1)
 //        reg_clock= !reg_clock;	
 //   end    
	case (Read_Addr)
		4'd0:instruction = addr1;
		4'd1:instruction = addr2;
		4'd2:instruction = addr3;
		4'd3:instruction = addr4;
		4'd4:instruction = addr5;
		4'd5:instruction = addr6;
		4'd6:instruction = addr7;
		4'd7:instruction = addr8;
		4'd8:instruction = addr9;
        4'd9:instruction = addr12;
		4'd10:instruction = addr10;
		4'd11:instruction = addr11;
		
		default :;
	endcase
end
endmodule

module counter (Read_addr,busy_signal,clk,reset);
input clk,busy_signal;
input reset;
output reg [3:0] Read_addr=0;
always @(negedge clk)
//if(busy_signal)
//begin
if(!reset) 
begin
	Read_addr<=Read_addr+4'd1;
end
else 
begin
	Read_addr<=4'd0;	
end
//end
endmodule

module data_mem(read_data,busy_wait,write_data,read,write,address,clk,reset);

input clk;
input reset;
input read;
input write;
input[7:0] address;
input[7:0] write_data;
output reg [7:0] read_data;
output reg busy_wait;

integer  i;
reg clock =1'b1;

// Declare memory 256x8 bits 
reg [7:0] memory_array [255:0];
//reg [7:0] memory_ram_r [255:0];


always @(negedge reset)
begin
    if (reset)
    begin
        for (i=0;i<256; i=i+1)
            memory_array[i] <= 0;
    end
end

always @(*)
begin
    if (write && !read)
	begin
		//busy_wait <= 0;
        // artificially delay 100 cycles	
		memory_array[address] = write_data;
        repeat (5)
		#1 clock= !clock;
		//busy_wait <= 1;
	end
    if (!write && read)
	begin
		//busy_wait <= 0;
		// artificially delay 100 cycles
		read_data = memory_array[address];
		repeat (5)
        #1 clock= !clock;
		//busy_wait <= 1;
	end
	if(!read && !write)
	begin
        read_data=8'bx;
    end    
end
 
endmodule


module proccessor(result,OUT1,OUT2,Data2,mux1out,immediate,mux2out,muxspecialout,read_data,Read,Write,Select,clk,reset);

input reset;
input clk;
output [3:0] Select;
wire busy_wait;
output Read,Write;
wire [7:0] Address;
wire [3:0] Read_addr;
wire [31:0] instruction;
wire [2:0] OUT1addr,OUT2addr,INaddr;
output [7:0] immediate,OUT2,Data2,mux1out,result,OUT1,mux2out,muxspecialout,read_data;
wire comp_signal,imm_signal,busy_signal;

counter mycounter(Read_addr,busy_signal,clk,reset);
regInstructions myreg(instruction,clk,Read_addr,busy_signal);
CU mycu1(OUT1addr,OUT2addr,INaddr,busy_signal,Read,Write,Address,immediate,Select,imm_signal,comp_signal,instruction,busy_wait);
data_mem mydata_mem1(read_data,busy_wait,result,Read,Write,Address,clk,reset);
regfile8x8a myregister(OUT1,OUT2,clk,INaddr,OUT1addr,OUT2addr,muxspecialout);
compliment mycomp(Data2,OUT2);
mux mux1(mux1out,comp_signal,Data2,OUT2);
mux mux2(mux2out,imm_signal,immediate,mux1out);
alu myalu(result,mux2out,OUT1,Select);
muxspecial muxspecial1(muxspecialout,busy_signal,read_data,result);


endmodule
