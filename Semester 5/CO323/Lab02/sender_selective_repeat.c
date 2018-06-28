#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>

int main(int argc, char**argv)
{
	int sockfd,n,k=0,recv;
	struct sockaddr_in servaddr;
	char* ACK = "ACK";
	char* NAK = "NAK";

	if (argc != 2){
		printf("usage:%s <IP address>\n",argv[0]);
		return -1;
	}
	
	sockfd=socket(AF_INET,SOCK_DGRAM,0);
	
	bzero(&servaddr,sizeof(servaddr));

	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr=inet_addr(argv[1]);
	servaddr.sin_port=htons(32000);

	struct timeval tv;
	tv.tv_sec = 2; //TIMEOUT TIME IS 3s
	tv.tv_usec = 0;
	setsockopt(sockfd, SOL_SOCKET, SO_RCVTIMEO, (char*)&tv, sizeof(struct timeval));

	bool canSend;
	char tmp[2];
	tmp[1]='\0';
	char stringify[20];
	stringify[19]='\0';
	char recvline[100];
	char userInput[100];
	printf("UDP Client Ready.....\n");
	printf("Enter Number of Frames to Send: ");
	scanf("%s",userInput);
	sendto(sockfd,userInput,10,0,(struct sockaddr*)&servaddr,sizeof(servaddr));
	int length = atoi(userInput);
	canSend = true;
	while(true)
	{	
		int i=0;

		for(;i<length;i++)
		{
			if(length>0 && canSend)
			{
				sprintf(stringify,"%d",i);
				sendto(sockfd,stringify,strlen(userInput),0,(struct sockaddr*)&servaddr,sizeof(servaddr));
				printf("Send frame %d\n",i);
			}	
			n=recvfrom(sockfd,recvline,10000,0,NULL,NULL);
			if(n>0)
			{
				if(strcmp(ACK,recvline)==0)
				{
					recvfrom(sockfd,recvline,10000,0,NULL,NULL);
					printf("Recieved: ACK%s\n",recvline);						
				}
				else if(strcmp(NAK,recvline)==0)
				{
					recvfrom(sockfd,recvline,10000,0,NULL,NULL);
					printf("Recieved: NAK%s\n",recvline);							
					recv = atoi(recvline);
					// tmp[0]=userInput[recv];
					// printf("%c\n",tmp[0]);
					sendto(sockfd,recvline,strlen(userInput),0,(struct sockaddr*)&servaddr,sizeof(servaddr));
					printf("Resend frame%d\n",recv);
				}
				else
				{
					printf("ERROR\n");
				}
				n=0;
			}	
		}	
		canSend = false;	
	}	
}