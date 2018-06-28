#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>
#include<string.h>
#include<time.h>

int nodeCount=0;

typedef struct trieNode
{
    struct trieNode *children[26];
    char character;
    bool endOfWord;
}trieNode;

int getCharIndex(char c)
{
    return c-'a';
}

trieNode* createNode()
{
    trieNode* newtrieNode = (trieNode*)malloc(sizeof(trieNode));
    newtrieNode->character='#';
    newtrieNode->endOfWord = false;
    int i;
    for (i = 0; i<26; i++)
    {
    	newtrieNode->children[i] = NULL;
    }
    return newtrieNode;
}

void insertNode(trieNode* root,char* word)
{
    int i,charIndex;
    trieNode* newNode = root;
    for(i=0;i<strlen(word)-1;i++)
    {
        charIndex = getCharIndex(word[i]);
        if(!(newNode->children[charIndex]))
        {
            newNode->children[charIndex] = createNode();
            newNode->children[charIndex]->character = word[i];

        }
        newNode = newNode->children[charIndex];
    }
    newNode->endOfWord = true;
    nodeCount = nodeCount+1;
}

trieNode* fileReader(char* fileName)
{
    char word[100];
    FILE *fp = fopen(fileName,"r");
    if (fp == NULL)
    {
        printf("Unable to open the file");
        exit(1);
    }
    trieNode* root = createNode();
    root->character = '/';
    clock_t begin = clock();
    while(1)
    {
        fgets(word,100,fp);
        insertNode(root,word);
        if(feof(fp))
            break;
    }
    clock_t end = clock();
    double time_spent = (double)(end-begin)/CLOCKS_PER_SEC;
    printf("Insertion Time: %f s\n",time_spent);
    fclose(fp);
    return root;
}

trieNode* searchNodes(trieNode* root,char* word)
{
    int i,charIndex;
    trieNode* currentNode = root;
    for(i=0;i<strlen(word);i++)
    {
        charIndex = getCharIndex(word[i]);
        currentNode =currentNode->children[charIndex];
    }
    return currentNode;
}

void printSuggetions(trieNode* suggestedNode,char wordBuffer[],int bufferSize)
{
    int i,charIndex;
    if(suggestedNode->endOfWord)
    {
        for(i=0;i<bufferSize;i++)
        {
            printf("%c",wordBuffer[i]);
        }
        printf("\n");
    }
    for(i=0;i<26;i++)
    {
        if(suggestedNode->children[i]!=NULL)
        {
            wordBuffer[bufferSize] = suggestedNode->children[i]->character;
            printSuggetions(suggestedNode->children[i],wordBuffer,bufferSize+1);
        }
    }
}

int main()
{
    char text[100];
    trieNode* root = fileReader("wordlist.txt");

    while(1)
    {
        printf("Enter keyword: ");
        scanf("%s",&text);
        char str[100];
        strcpy(str,text);
        trieNode* suggestedNode = searchNodes(root,str);
        printf("Suggested Words\n");
        printf("-----------------------\n");
        clock_t begin = clock();
        printSuggetions(suggestedNode,str,strlen(str));
        clock_t end = clock();
        double elapsedtime = (double)(end-begin)/CLOCKS_PER_SEC;
        printf("Elapsed time: %f s\n",elapsedtime);
        printf("Memory: %d Bytes",sizeof(*(root))*nodeCount);
        printf("\n");
    }
    return 0;
}
