#include <sys/socket.h>
#include <netinet/in.h>
#include <strings.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <stdbool.h>

int main(int argc, char**argv)
{
	int sockfd,n;
	struct sockaddr_in servaddr, cliaddr;
	socklen_t len;
	char mesg[1000];
	char* ACK = "ACK";
	char* NAK = "NAK";
	bool conn = true;
	
	sockfd=socket(AF_INET,SOCK_DGRAM,0);	//create a socket
	
	servaddr.sin_family = AF_INET;	//address family	
	servaddr.sin_addr.s_addr=htonl(INADDR_ANY);	//IP address in the network byte order
	servaddr.sin_port=htons(32000);

	
	bind(sockfd,(struct sockaddr *)&servaddr,sizeof(servaddr));
	len = sizeof(cliaddr);
	char stringify[2];
	stringify[1]='\0';
	int tmp;
	// n=recvfrom(sockfd,mesg,1000,0,(struct sockaddr*)&cliaddr,&len);	
	// mesg[n] = 0;
	// printf("Recieved: frame: %s\n", mesg);
	int length = 10;
	int j=0;
	int recv_buffer[length];
	for (j=0;j<10;j++)
	{
		recv_buffer[j]=0;// set value 0 to all the array index
	}
	bool flag = true;
	int i=0;

	struct timeval tv;
	tv.tv_sec = 1; //TIMEOUT TIME IS 3s
    tv.tv_usec = 0;
    setsockopt(sockfd, SOL_SOCKET, SO_RCVTIMEO, (char*)&tv, sizeof(struct timeval));

	while(conn)
	{
		for(;i<length;)
		{
			n=recvfrom(sockfd,mesg,1000,0,(struct sockaddr*)&cliaddr,&len);		
			mesg[n] = 0;
			//printf("i: %d\n",i);
			sprintf(stringify,"%d",i);
			if(n>0)
			{
				if(recv_buffer[i]==0)
				{
					printf("Recieved: frame: %s\n", mesg);
					tmp = atoi(mesg);
					recv_buffer[tmp] = 1;
					if(i==tmp)
					{
						//printf("inside ack\n");
						sendto(sockfd,ACK,10,0,(struct sockaddr*)&cliaddr,sizeof(cliaddr));
						sendto(sockfd,mesg,10,0,(struct sockaddr*)&cliaddr,sizeof(cliaddr));									
						i++;
						flag = true;
					}
					else if(i!=tmp && flag)
					{
						//printf("inside nak\n");
						sendto(sockfd,NAK,10,0,(struct sockaddr*)&cliaddr,sizeof(cliaddr));
						sendto(sockfd,stringify,10,0,(struct sockaddr*)&cliaddr,sizeof(cliaddr));
						flag = false;
					}
				}
				else
				{
					i++;
				}	
			}
			else
			{
				//printf("inside nak\n");
				sendto(sockfd,NAK,10,0,(struct sockaddr*)&cliaddr,sizeof(cliaddr));
				sendto(sockfd,stringify,10,0,(struct sockaddr*)&cliaddr,sizeof(cliaddr));
			}	
		}
		conn = false;
	}	
	return 0;
}
