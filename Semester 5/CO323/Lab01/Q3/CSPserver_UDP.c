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
	char* banner = "ack";
	
	sockfd=socket(AF_INET,SOCK_DGRAM,0);	//create a socket
	
	servaddr.sin_family = AF_INET;	//address family	
	servaddr.sin_addr.s_addr=htonl(INADDR_ANY);	//IP address in the network byte order
	servaddr.sin_port=htons(32000);
	
	bind(sockfd,(struct sockaddr *)&servaddr,sizeof(servaddr));
	len = sizeof(cliaddr);

	n=recvfrom(sockfd,mesg,1000,0,(struct sockaddr*)&cliaddr,&len);	
	sendto(sockfd,banner,3,0,(struct sockaddr*)&cliaddr,sizeof(cliaddr));
	mesg[n] = 0;
	printf("Received: %s\n",mesg);
	int loopNumber = atoi(mesg);
	//printf("%d\n",loopNumber );
	
	int i;
	for(i=0;i<loopNumber;i++)
	{
		n=recvfrom(sockfd,mesg,1000,0,(struct sockaddr*)&cliaddr,&len);	
		int j;
		char newmesg[n];
		for(j=0;j<n;j++)
		{
			newmesg[j] = toupper(mesg[j]);	
		}

		sendto(sockfd,newmesg,n,0,(struct sockaddr*)&cliaddr,sizeof(cliaddr));
		mesg[n] = 0;
		printf("Received: %s\n",mesg);
	}
	
	return 0;
}