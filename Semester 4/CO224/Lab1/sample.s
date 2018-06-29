@ ARM Assembly - sample program 

	.text 	@ instruction memory
	.global main
main:
	@ stack handling, will discuss later
	@ push (store) lr to the stack
	sub sp, sp, #4
	str lr, [sp, #0]

	@ load values --> can be changed
	mov r0, #5
	mov r1, #10
	mov r2, #7
	mov r3, #-8

	
	@ Write YOUR CODE HERE
	@ ---------------------
	
	cmp r2,#11
	bge print2
	bne print1
	
	
	@ ---------------------
	
print1:
	@ load aguments and print
		ldr r0, =format1
		@mov r1, r5
		bl printf
		b exit

print2:
	@ load aguments and print
		ldr r0, =format2
		@mov r1, r5
		bl printf		
exit:
	@ stack handling (pop lr from the stack) and return
	ldr lr, [sp, #0]
	add sp, sp, #4
	mov pc, lr


	.data	@ data memory
format1: .asciz "Large thama\n"
format2: .asciz "Large ne\n"

