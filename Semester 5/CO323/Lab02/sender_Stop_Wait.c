/* Sample UDP client */
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>


int main(int argc, char**argv)
{
	int sockfd,n;
	struct sockaddr_in servaddr;
	char recvline[1000];
	bool canSend;
	char stringify[2];
	stringify[1]='\0';


	if (argc != 2){
		printf("usage:%s <IP address>\n",argv[0]);
		return -1;
	}
	
	sockfd=socket(AF_INET,SOCK_DGRAM,0);
	
	bzero(&servaddr,sizeof(servaddr));

	
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr=inet_addr(argv[1]);
	servaddr.sin_port=htons(32000);

	char userInput[1000];
	scanf("%s",userInput);
	int i=0;
	while(true)
	{	
		canSend = true;	
		int length = strlen(userInput);
		for(;i<length;i++)
		{
			if(length>0 && canSend)
			{
				stringify[0] = userInput[i];
				sendto(sockfd,stringify,strlen(userInput),0,(struct sockaddr*)&servaddr,sizeof(servaddr));
				canSend = false;
			}	
			n=recvfrom(sockfd,recvline,10000,0,NULL,NULL);
			if(n>0)
			{
				recvline[n]=0;
				printf("Received: %s\n",recvline);
				canSend = true;
				n=0;
			}	
		}	
	}	
	return 0;
}