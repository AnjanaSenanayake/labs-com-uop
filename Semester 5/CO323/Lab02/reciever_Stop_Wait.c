/* Sample UDP server */
#include <sys/socket.h>
#include <netinet/in.h>
#include <strings.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

int main(int argc, char**argv){
	int sockfd,n;
	struct sockaddr_in servaddr, cliaddr;
	socklen_t len;
	char mesg[1000];
	char* bannerACK = "ack";
	
	sockfd=socket(AF_INET,SOCK_DGRAM,0);	//create a socket

	
	servaddr.sin_family = AF_INET;	//address family	
	servaddr.sin_addr.s_addr=htonl(INADDR_ANY);	//IP address in the network byte order
	servaddr.sin_port=htons(32000);
	
	bind(sockfd,(struct sockaddr *)&servaddr,sizeof(servaddr));
	len = sizeof(cliaddr);

	while(1)
	{
		n=recvfrom(sockfd,mesg,1000,0,(struct sockaddr*)&cliaddr,&len);	
		if(n>0)
		{
			mesg[n] = 0;
			printf("Received: %s\n",mesg);
			printf("ACK Send\n");
			sendto(sockfd,bannerACK,n,0,(struct sockaddr*)&cliaddr,sizeof(cliaddr));			
			n=0;
		}	
	}	
	return 0;
}