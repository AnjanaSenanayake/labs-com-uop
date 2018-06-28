/* Sample UDP client */
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>


int main(int argc, char**argv){
	int sockfd,n;
	struct sockaddr_in servaddr;
	//char sendline[] = "Hello UDP server! This is UDP client";
	char recvline[1000];

	if (argc != 3){
		printf("usage:%s <IP address>\n",argv[0]);
		return -1;
	}
	
	sockfd=socket(AF_INET,SOCK_DGRAM,0);
	
	bzero(&servaddr,sizeof(servaddr));

	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr=inet_addr(argv[1]);
	servaddr.sin_port=htons(32000);

	sendto(sockfd,argv[2],strlen(argv[2]),0,(struct sockaddr*)&servaddr,sizeof(servaddr));
	n=recvfrom(sockfd,recvline,10000,0,NULL,NULL);
	recvline[n]=0;
	printf("Received: %s\n",recvline);

	int k = atoi(argv[2]);
	int z;
	for(z=0;z<k;z++)		
	{
		char* CSPInput;
		scanf("%s",CSPInput);
		sendto(sockfd,CSPInput,strlen(CSPInput),0,(struct sockaddr*)&servaddr,sizeof(servaddr));
		n=recvfrom(sockfd,recvline,10000,0,NULL,NULL);
		recvline[n]=0;
		printf("Received: %s\n",recvline);
	}	
	return 0;
}