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

	struct timeval tv;
	tv.tv_sec = 3; //TIMEOUT TIME IS 3s
    tv.tv_usec = 0;
    setsockopt(sockfd, SOL_SOCKET, SO_RCVTIMEO, (char*)&tv, sizeof(struct timeval));

	char userInput[1000];
	while(true)
	{	
		scanf("%s",userInput);
		int i=0;
		canSend = true;	
		int length = strlen(userInput);
		for(;i<length;)
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
				i++;
			}	
			else if(n<0)
			{
				printf("Timeout Reached\nPacket resend\n");
				stringify[0] = userInput[i];
				sendto(sockfd,stringify,strlen(userInput),0,(struct sockaddr*)&servaddr,sizeof(servaddr));
				canSend = false;
				i=0;
			}	
		}	
	}	
	return 0;
}