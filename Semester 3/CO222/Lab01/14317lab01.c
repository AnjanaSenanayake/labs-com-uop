#include<stdio.h>

int main()
{
    float A=2.5*30;
    float B=7.85*60;
    float AB=4.85*60;
    float BC=10*30;
    float CD=27.75*30;
    float DE=32*60;
    float charge;
    int usage;

    scanf("%d",&usage);
    if(usage>0)
    {
        if(usage>180)
           charge=(usage-180)*45+DE+CD+BC+B+540;
        else if(usage>120)
           charge=(usage-120)*32+CD+BC+B+480;
        else if(usage>90)
           charge=(usage-90)*27.75+BC+B+480;
        else if(usage>60)
           charge=(usage-60)*10+B+90;
        else if(usage>30)
           charge=(usage-30)*4.85+A+60;
        else
           charge=usage*2.50+30;
        printf("%.2f\n",charge);
    }
    else
        printf("-1\n");
}
