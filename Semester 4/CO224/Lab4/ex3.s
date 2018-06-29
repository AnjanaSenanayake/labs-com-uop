@ ARM Assembly Example
@	Read two integers x and y
@	Print x+y

	.text	@ instruction memory
	
	.global main
main:
	@ stack handling, 
    @ push (store) lr to the stack
	sub	sp, sp, #4
	str	lr, [sp, #0]


	@allocate stack for input/scanf
	sub	sp, sp, #4

	@printf for int1
	ldr	r0, =format1
	bl	printf

	@scanf for int1
	ldr	r0, =formats
	mov	r1, sp	
	bl	scanf	@scanf("%d",sp)

	@copy int1 from the stack to register r4
	ldr	r4, [sp]
	
	@release stack
	add	sp, sp, #4

	@loop print
	mov r2,#1
	mov r1,#0
loop:
	add r1,r1,r2
	ldr	r0, =formatp
	bl 	printf
	cmp r1,r4 
	ble loop


exit:
    @ stack handling (pop lr from the stack) and return
	ldr	lr, [sp, #0]
	add	sp, sp, #4
	mov	pc, lr		
	
	.data	@ data memory
format1: .asciz "Enter N: "
format2: .asciz "Enter integer 2: "
formats: .asciz "%d"
formatp: .asciz "%d\n"
