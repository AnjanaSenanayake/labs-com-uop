@ ARM Assembly - Mini project
@ E/14/317

.text                       @ instruction memory

@ ---------------------     @ functions

myscanf:   
    sub sp,sp,#4            @ make room on stack
    str lr,[sp,#0]          @ push (store) lr to the stack
    push {r1,r2,r3}         @ push r1,r2,r3 to the stack
    ldr r0, =format_input   @ r0 contains address of format string
    sub sp, sp,#4           @ allocate stack for input
    mov r1, sp              @ move sp to r1 to store entry on stack
    bl scanf                @ call scanf("%d",sp)
    ldr r0, [sp]            @ load value at sp into r0
    add sp, sp, #4          @ remove value from stack    
    pop {r1,r2,r3}          @ restore r1,r2,r3 from stack
    ldr lr,[sp,#0]          @ pop lr from the stack and return
    add sp,sp,#4
    mov pc,lr   

myscanf2:   
    sub sp,sp,#4            @ make room on stack
    str lr,[sp,#0]          @ push (store) lr to the stack
    push {r5}               @ push r5 to the stack
    ldr r0, =format_input   @ r0 contains address of format string
    sub sp, sp,#4           @ allocate stack for input
    mov r1, sp              @ move sp to r1 to store entry on stack
    bl scanf                @ call scanf("%d",sp)
    ldr r0, [sp]            @ load value at sp into r0
    add sp, sp, #4          @ remove value from stack    
    pop {r5}                @ pop r5 from stack
    ldr lr,[sp,#0]          @ pop lr from the stack and return
    add sp,sp,#4
    mov pc,lr

myprintf:
    sub sp,sp,#4            @ make room on stack
    str lr,[sp,#0]          @ push (store) lr to the stack
    ldr r0, =printf_array   @ r0 contains formatted string address
    bl printf               @ call printf
    ldr lr,[sp,#0]          @ pop lr from the stack and return
    add sp,sp,#4
    mov pc,lr

invert:                     @ function for inverting the matrix
    sub sp,sp,#4            
    str lr,[sp,#0]          
    ldr r8,=array_a
    mov r1,#255
    mov r7,#0
    b loop
loop:                       @ looping through the matrix
    lsl r2,r7,#2
    ldr r4,[r8,r2]
    sub r4,r1,r4    
    str r4,[r8,r2]
    add r7,r7,#1
    cmp r7,r6
    bls loop
    ldr lr,[sp,#0]          
    add sp,sp,#4
    mov pc,lr

rotation:                   @ function for rotating the matrix 
    sub sp,sp,#4            
    str lr,[sp,#0]          
    push {r9}
    mov r7,#0
    mov r3,#0
    sub r10,r6,#1  
    ldr r8,=array_a
    ldr r9,=array_b
    b copy_array
copy_array:                 @ creating a copy of the array
    lsl r4,r3,#2
    ldr r7,[r8,r4]
    str r7,[r9,r4]
    add r3,r3,#1
    cmp r3,r6
    bne copy_array
    mov r3,#0
    b loop_r
loop_r:
    lsl r4,r3,#2
    lsl r7,r10,#2
    ldr r7,[r9,r7]
    str r7,[r8,r4]
    add r3,r3,#1
    sub r10,r10,#1
    cmp r10,#0
    bge loop_r
    pop {r9}
    ldr lr,[sp,#0]         
    add sp,sp,#4
    mov pc,lr

flip:                        @ function to flip the matrix
    sub sp,sp,#4           
    str lr,[sp,#0]        
    push {r0,r1,r2,r3,r5,r6}
    mov r7,#0
    mov r5,r9
    mov r0,#0
    mov r12,#1
    sub r3,r9,#1
    ldr r10,=array_a
    ldr r11,=array_b
    b copy_array2
copy_array2:
    lsl r4,r0,#2
    ldr r7,[r10,r4]
    str r7,[r11,r4]
    add r0,r0,#1
    cmp r0,r6
    bne copy_array2
    mov r1,#0
    b loop_f
column_adjust:               @ moving through rows    
    add r12,r12,#1
    mul r3,r12,r9
    sub r3,r3,#1     
    mov r5,r9 
loop_f:
    cmp r5,#0
    beq column_adjust
    lsl r4,r3,#2
    lsl r2,r1,#2
    ldr r7,[r11,r4]
    str r7,[r10,r2]
    add r1,r1,#1
    sub r3,r3,#1
    sub r5,r5,#1
    sub r6,r6,#1
    cmp r6,#0 
    bhi loop_f
    pop {r0,r1,r2,r3,r5,r6}
    ldr lr,[sp,#0]         
    add sp,sp,#4
    mov pc,lr

@ --------------------- 

.global main
main:
    sub sp, sp, #4          
    str lr, [sp, #0]        

    mov r3,#0               @ initialize index variable                 
    bl myscanf2
    mov r8,r0               @ copy row value to r8             
    bl myscanf2
    mov r9,r0               @ copy column value to r9
    bl myscanf2
    mov r5,r0               @ copy opration value to r5
    mul r6,r8,r9
    b inputloop

inputloop:                  @ getting matrix values
    ldr r1,=array_a         @ get address of a
    lsl r2,r3,#2            @ multiply index*4 to get array offset
    add r2,r1,r2            @ r2 now has the element address
    push {r1,r2,r3}
    bl myscanf
    pop {r1,r2,r3}
    mov r4,r0               @ load value at r0 to r4
    str r4,[r2]             @ write input to a[i]
    add r3,r3,#1            @ increment index
    cmp r3,r6               @ check to see if we are done iterating
    blt inputloop           @ branch to next loop iteration

    cmp r5,#0               @ if op value=0 jump to function original
    beq original
    cmp r5,#1               @ if op value=1 jump to function invertmatrix
    beq invertmatrix
    cmp r5,#2               @ if op value=2 jump to function rotatematrix
    beq rotatematrix
    cmp r5,#3               @ if op value=e jump to function flipmatrix
    beq flipmatrix
    b error                 @ if else jump to function error


original:                   @ function 'original'
    ldr r0,=printf_original
    bl printf
    b output

invertmatrix:               @ function 'invertmatrix'
    ldr r0,=printf_invert
    bl printf
    bl invert
    b output

rotatematrix:               @ function 'rotatematrix'
    ldr r0,=printf_rotate
    bl printf
    bl rotation
    b output
flipmatrix:                 @ function 'flipmatrix'
    ldr r0,=printf_flip
    bl printf
    bl flip      
    b output  
error:                      @ function 'error'
    ldr r0,=printf_invalid
    bl printf    
    b exit


output:                     @ initializing for printing the output
    ldr r10,=array_a        @ get address of a
    mov r3,#0   
    mov r4,#0

outputloop:                 @ printing a 2d matrix
    cmp r6,#0               @ check to see if we are done iterating
    beq exit                @ branch to exit

    cmp r9,r4               @ check with column
    beq next_row            @ branch to exit

    mul r2,r3,r9
    add r2,r2,r4
    lsl r2,r2,#2            @ multiply index*4 to get array offset
    push {r2,r3,r4}
    ldr r0,=printf_array    @ r0 contains formatted string address
    ldr r1,[r10,r2]         @ read the array at address 
    bl  printf              @ branch to print procedure with return  
    pop {r2,r3,r4}
    add r4,r4,#1            @ increment i index
    sub r6,r6,#1
    b outputloop            @ branch to next loop iteration
    
next_row:                   @ jump to print next row
    push {r2,r3,r4}
    ldr r0, =printf_line    @ r0 contains formatted string address
    bl printf               @ call printf
    pop {r2,r3,r4}
    mov r4,#0
    add r3,r3,#1
    b outputloop
   
exit:                       @ exiting the program
    ldr r0,=printf_line
    bl printf
    ldr lr, [sp, #0]
    add sp, sp, #4
    mov pc, lr

.data                       @ data memory

@array called arrary_a of size 90 bytes
.balign 4               @align to an address of multiple of 4
array_a: .skip 40000        @unitilized array_a of 90 bytes
@array called arrary_a of size 90 bytes
.balign 4
array_b: .skip 40000       @unitilized array_a of 90 bytes

format_input: .asciz "%d"
printf_array: .asciz "%d "
printf_line: .asciz "\n"
printf_original: .asciz "Original\n"
printf_invert: .asciz "Inversion\n"
printf_rotate: .asciz "Rotation by 180\n"
printf_flip: .asciz "Flip\n"
printf_invalid: .asciz "Invalid operation"
