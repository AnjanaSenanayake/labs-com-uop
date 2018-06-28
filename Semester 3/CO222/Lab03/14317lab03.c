#include<stdio.h>
int main()
{
	int n,m;
	scanf("%d",&n);
	scanf("%d",&m);
	printf("%d %d\n",n,m);
	int rgbval,rgbvalnew;
	int i,j,p,r;
	int zeroes=4-((3*n)%4);
	for (p=0;p<m;p++)
	{
		for(i=0;i<n;i++)
		{
			for(j=0;j<3;j++)
			{	
				scanf("%d",&rgbval);
				rgbvalnew=255-rgbval;
				printf("%d",rgbvalnew);
				if(j!=2)
					printf(" ");	
			}
			printf("\n");
		}
		if(zeroes!=4)
		{	
			for(i=0;i<zeroes;i++)
			{
				scanf("%d",&r);
			}
		}	
		if(zeroes!=4)
		{	
			for(i=0;i<zeroes;i++)
			{
				printf("%d\n",0);
			}	
		}	
	}
	return 0;
}