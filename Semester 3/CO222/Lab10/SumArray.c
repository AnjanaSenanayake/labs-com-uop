// Week10_SumArray.c
// Sum up all values in an integer array.
// appear in an array.
#include <stdio.h>
#define MAX_SIZE 10

int readArray(int []);
void printArray(int [], int);
int sumArray(int [], int);

int main(void) {
	int list[MAX_SIZE], size;

	size = readArray(list);
	printArray(list, size);

	printf("Sum = %d\n", sumArray(list, size));

	return 0;
}

// Read values into array.
// Return number of elements read.
int readArray(int arr[]) {
	int size, i;

	printf("Enter number of elements: ");
	scanf("%d", &size);
	printf("Enter %d values: ", size);
	for (i=0; i<size; i++)
		scanf("%d", &arr[i]);

	return size;
}

// Print values in array
void printArray(int arr[], int size) {
	int i;

	printf("Array read: ");
	for (i=0; i<size; i++)
		printf("%d ", arr[i]);
	printf("\n");
}

// Sum all the values in the array arr
// Pre-cond: size >= 0
int sumArray(int arr[], int size) 
{
	if(size==0)
		return arr[0];
	else
		return (sumArray(arr,size-1)+arr[size]); 
}

