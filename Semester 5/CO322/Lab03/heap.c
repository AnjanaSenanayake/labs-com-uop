#include "heap.h"
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <assert.h>

#define IMPLEMENT_ME							\
  printf("you need to implement %s function in %s\n",__func__, __FILE__);	\
  exit(-1);

/**
 * Note: typically, in C functions/variables that starts with
 * __ are local and should not be used unless you really know what you
 * are doing. For example __start
 */
int __isFull(heap_t heap)
{
  return (heap -> next == heap -> curr_size);
}

void __resize_heap(heap_t heap)
{
    int i=0;
    int current_size = heap->curr_size;
    int new_size=heap->curr_size+INCREASE_BY;
    int *new_data = malloc(new_size*sizeof(int));
    for(i=0;i<current_size;i++)
    {
        new_data[i] = heap->data[i];
    }
    heap->data=new_data;
    heap->curr_size=current_size+INCREASE_BY;
}

void __swap(heap_t heap, int i, int j)
{
  int tmp = heap -> data[i];
  heap -> data[i] = heap -> data[j];
  heap -> data[j] = tmp;
}

int __getParentIndex(int childIndex)
{
    return (childIndex-1)/2;
}

int __getLeftChildIndex(int parentIndex)
{
    return (2*parentIndex+1);
}

int __getRightChildIndex(int parentIndex)
{
    return (2*parentIndex+2);
}

int __getParent(heap_t heap,int index)
{
    return heap->data[__getParentIndex(index)];
}

int __getLeftChild(heap_t heap,int index)
{
    return heap->data[__getLeftChildIndex(index)];
}

int __getRightChild(heap_t heap,int index)
{
    return heap->data[__getRightChildIndex(index)];
}

bool __hasParent(int index)
{
    return (__getParentIndex(index)>0);
}

bool __hasLeftChild(heap_t heap,int index)
{
    return (__getLeftChildIndex(index)<heap->next);
}

bool __hasRightChild(heap_t heap,int index)
{
    return (__getRightChildIndex(index)<heap->next);
}

int isEmpty(heap_t heap)
{
  return (heap -> next == 0);
}

heap_t create()
{
    heap_t heap_ptr = malloc(sizeof(heap_t));
    heap_ptr->data=(int*)malloc(SIZE*sizeof(int));
    heap_ptr->next = 0;
    heap_ptr->curr_size = SIZE;
    return heap_ptr;
}

void bubbleUp(heap_t heap)
{
    int index = heap->next-1;
    while(index>0)
    {
        if(__getParent(heap,index) <= heap->data[index])
        {
            break;
        }
        __swap(heap,__getParentIndex(index),index);
        index = __getParentIndex(index);
    }
}

void bubbleDown(heap_t heap)
{
    int index = 0;
    while(__hasLeftChild(heap,index))
    {
        int smallerChildIndex = __getLeftChildIndex(index);
        if(__hasRightChild(heap,index) && (__getRightChild(heap,index)<__getLeftChild(heap,index)))
        {
            smallerChildIndex = __getRightChildIndex(index);
        }
        if(heap->data[index]< heap->data[smallerChildIndex])
        {
            break;
        }
        else
        {
            __swap(heap,index,smallerChildIndex);
        }
        index = smallerChildIndex;
    }
}

void insert(heap_t heap, int data)
{
    if(__isFull(heap))
    {
        __resize_heap(heap);
    }
    int next = heap->next;
    heap->data[next] = data;
    heap->next = heap->next+1;
    bubbleUp(heap);
}

int heap_remove(heap_t heap)
{
    int temp = heap->data[0];
    heap->next=heap->next-1;
    __swap(heap,0,heap->next);
    bubbleDown(heap);
    return temp;
}
