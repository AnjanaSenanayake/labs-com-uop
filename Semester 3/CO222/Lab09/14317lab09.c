#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct node
{
	int len;
	char word[150];
	struct node* next;
}node;

void printList(node* head);
void sortList(node* first);
void linkSwap(node *node1, node *node2);

int main(int argc, char** argv)
{
	char word[150], *file;
	int lenword = 0;
	FILE *fp;
	if (argc != 2)
    {
		printf("Err ! No Input file found\n");
		return 1;
	}
    else
    {
		node* head = malloc(sizeof(node));
		head->len = 0;
		head->word;
		node* temp = malloc(sizeof(node));
		head->next = temp;
		file = argv[1];
		fp = fopen(file, "r");		
		while(fscanf(fp, "%s", word) != EOF)
        {
			lenword = strlen(word);
			temp->len = lenword;
			strcpy(temp->word , word);
			temp->next = malloc(sizeof(node));
			temp = temp->next;	
		}
		fclose(fp);
	    sortList(head->next);
		printList(head);	
	}
	return 0;
}
void sortList(node* first)
{
    int swap = 1;
    node *new1;
    node *new2 = NULL;
    while (swap)
    {
        swap = 0;
        new1 = first;
        while (new1->next != new2)
        {
            if(strlen(new1->word) < strlen(new1->next->word))
            { 
                linkSwap(new1, new1->next);
                swap = 1;                
            }
            new1 = new1->next;   
        }
        new2 = new1;
    }
} 
void linkSwap(node *node1, node *node2)
{
    char tmp[150];
    strcpy(tmp, node1->word);
    strcpy(node1->word , node2->word);
    strcpy(node2->word , tmp);
}
void printList(node* head) 
{
    node* current = head->next->next;
    while (current != NULL) 
    {
        printf("%s\n", current->word);
        current = current->next;
    }
}
