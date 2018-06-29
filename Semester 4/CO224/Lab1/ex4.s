@ ARM Assembly - Lab 1 - Exercise 4 - Template
@ 

	.text 	@ instruction memory
	.global main
main:
	@ stack handling, will discuss later
	@ push (store) lr to the stack
	sub sp, sp, #4
	str lr, [sp, #0]

	@ load values
	ldr r0, =array_a
	ldr r1, =array_b
	ldr r2, =array_c
	mov r3, #7
	
	@ Write YOUR CODE HERE
	@ c[0] = a[8] - 7 + a[3] - b[4] - c 
	@ c[4] = 2 + b[2] - b[1] + c[0]
	@ c[2] = c[0] * c[4] 
	
	@ Base address of a in r0
	@ Base address of b in r1
	@ Base address of c in r2
	@ c is in r3 

	@ ---------------------
	
	ldr r4,[r0,#32]
	ldr r5,[r0,#12]
	ldr r6,[r1,#16]
	add r4,r4,r5
	add r6,r6,#7
	add r6,r6,r3
	sub r4,r4,r6
	str r4,[r2,#0]

	ldr r4,[r1,#8]
	ldr r5,[r1,#4]
	sub r4,r4,r5
	ldr r6,[r2,#0]
	add r5,r6,#2
	add r5,r4,r5
	str r5,[r2,#16]

	ldr r5,[r2,#16]
	ldr r4,[r2,#0]
	mul r3,r5,r4
	str r3,[r2,#8]


	@ ---------------------
	
	
	@ load aguments and print
	ldr r0, =result1
	ldr r2, =array_c
	ldr r1, [r2,#0]
	bl printf

	@ load aguments and print
	ldr r0, =result2
	ldr r2, =array_c
	ldr r1, [r2,#16]
	bl printf

	@ load aguments and print
	ldr r0, =result3
	ldr r4, =array_c
	ldr r1, [r4,#0]
	ldr r2, [r4,#16]
	ldr r3, [r4,#8]
	bl printf

	@ stack handling (pop lr from the stack) and return
	ldr lr, [sp, #0]
	add sp, sp, #4
	mov pc, lr
	
	.data	@ data memory

result1: .asciz "Answer 1 is %d (Expect 82 if correct)\n"

result2: .asciz "Answer 2 is %d (Expect 88 if correct)\n"

result3: .asciz "Multiplication of %d by %d is %d (Expect 7216 if correct)\n"
	
	@array called array_a of size 40 bytes
	.balign 4 			@align to an address of multiple of 4
array_a: .word 1,5,7,67,5,54,65,23,34,54

	@array called array_b of size 20 bytes
	.balign 4 			@align to an address of multiple of 4
array_b: .word 7,4,8,3,5

	@array called arary_a of size 40 bytes
	.balign 4 			@align to an address of multiple of 4
array_c: .skip 40		@unitilized array of 400 bytes
