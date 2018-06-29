@ ARM Assembly - a
	.text 	@ instruction memory
	
adder:	
	sub sp, sp, #4
	str lr, [sp, #0]

	@ load values
	mov r4, #25 
	mov r5, #40
	mov r6, #-30
	mov r7, #100
	mov r8, #10 

	add r2,r4,r5
	add r1,r6,r7
	add r2,r1,r2
	add r2,r2,r8

	ldr r0, =format2
	mov r1,r2
	bl printf

	ldr lr, [sp, #0]
	add sp, sp, #4
	mov pc, lr

	.global main

main:
	@ stack handling, will discuss later
	@ push (store) lr to the stack
	sub sp, sp, #4
	str lr, [sp, #0]

	ldr r0, =format1
	bl printf

	bl adder
	
	ldr r0, =format3
	bl printf
	b exit
	
exit:
	@ stack handling (pop lr from the stack) and return
	ldr lr, [sp, #0]
	add sp, sp, #4
	mov pc, lr

	.data	@ data memory
format1: .asciz "Calling the main\n"
format2: .asciz "Total is %d\n"
format3: .asciz "Adder function is finished\n"
