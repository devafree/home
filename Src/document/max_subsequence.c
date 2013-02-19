#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define MAXNUM 100

float array[MAXNUM];
unsigned int arraylen;

float alg1(unsigned int *pleft, unsigned int *pright)
{
	unsigned int i, j, k;
	float sum, maxsofar = 0;
	for(i=0; i<arraylen; i++)
	{
		for(j=i; j<arraylen; j++)
		{
			sum = 0;
			for(k=i; k<=j; k++)
			{
				sum += array[k];
			}
			if(sum > maxsofar)
			{
				maxsofar = sum;
				*pleft = i;
				*pright = k--;
			}
		}
	}

	return maxsofar;
}

float alg2(unsigned int *pleft, unsigned int *pright)
{
	unsigned int i, j;
	float sum, maxsofar = 0;
	for(i=0; i<arraylen; i++)
	{
		sum = 0;
		for(j=i; j<arraylen; j++)
		{
			sum += array[j];
			if(sum > maxsofar)
			{
				maxsofar = sum;
				*pleft = i;
				*pright = j;
			}
		}
	}
	return maxsofar;
}

float alg2b(unsigned int *pleft, unsigned int * pright)
{
	unsigned int i,j;
	float sum, maxsofar;
	float cumarrv[MAXNUM+1];
	float *pcumarr =  cumarrv+1;
	pcumarr[-1] = 0;  //pcumarr[-1] == cumarrv[0]
	
	for(i=0; i<arraylen; i++)
	{
		pcumarr[i] = pcumarr[i-1] + array[i];
	}
	
	maxsofar = 0;
	for(i=0; i<arraylen; i++)
	{
		for(j=i; j<arraylen; j++)
		{
			sum = pcumarr[j] - pcumarr[i-1];
			if(sum > maxsofar)
			{
				maxsofar = sum;
				*pleft = i;
				*pright = j;
			}
		}
	}
	
	return maxsofar;
}

/**************************************************
 *input:  begin     --array[begin]
 *		  end       --array[end]
 *output: pleft     --the left position of array when maxsofar is maximized
 *		  pright    --the right position of array when maxsofar is maximized
 * return: maxsofar -- the sum of sub array with sub-rule method		  
 *************************************************/
float max_subRuleMeth(int begin, int end, 
	   	unsigned int* pleft, unsigned int *pright)
{
	if(begin > end)
		return 0;
	if(begin == end)
	{
		*pleft = *pright = begin;
		return array[begin]>0 ? array[begin] : 0;
	}
	
	int mid = (begin + end)/2;
	float lmax=0, rmax=0, sum = 0;
	int i;
	for(i=mid; i>=begin; i--)
	{
		sum += array[i];
		if(sum > lmax)
		{
			lmax = sum;
			*pleft = i;
		}
	}
	rmax = sum = 0;
	for(i=mid+1; i<=end; i++)
	{
		sum += array[i];
		if(sum > rmax)
		{
			rmax = sum;
			*pright = i;
		}
	}
	
	unsigned int ultmp=0, urtmp=0;
	float maxsofar = lmax + rmax;
	float lMax = max_subRuleMeth(begin, mid, &ultmp, &urtmp);
	if(lMax > maxsofar)
	{
		maxsofar = lMax;
		*pleft = ultmp;
		*pright = urtmp;
	}
	ultmp = urtmp = 0;
	float rMax = max_subRuleMeth(mid+1, end, &ultmp, &urtmp);
	if(rMax > maxsofar)
	{
		maxsofar = rMax;
		*pleft = ultmp;
		*pright = urtmp;
	}
	
	return maxsofar;
}

float alg3(unsigned int *pleft, unsigned int * pright)
{
	return max_subRuleMeth(0, arraylen-1, pleft, pright);
}

float alg4(unsigned int *pleft, unsigned int * pright)
{
	float maxsofar = 0, sumtmp = 0;
	float maxendinghere = 0;
	unsigned int i = 0;
	
	*pleft = *pright =  0;
	for(; i<arraylen; i++)
	{
		sumtmp = maxendinghere + array[i];
		if(sumtmp > 0)
		{
			maxendinghere = sumtmp;
		}
		else
		{
			maxendinghere = 0;
			*pleft = i+1; //array[0]+...+array[i]<0, presume a[i+1]>0
		}
		
		if(maxendinghere > maxsofar)
		{
			maxsofar = maxendinghere;
			*pright = i;
		}
	}
	
	if(*pleft > *pright)
	{//a[i+1]>0 is not true. 
		*pleft = *pright;
	}
	return maxsofar;
}

int main(void)
{
	typedef float (*PFUN_ALG)(unsigned int *pleft, unsigned int *pright);
	enum AlgNum {F_alg1=0, F_alg2, F_alg2b, F_alg3, F_alg4} algnum;
	char algFunName[F_alg4+1][10] = {"alg1", "alg2", "alg2b", "alg3", "alg4"};
	PFUN_ALG pfun[F_alg4+1]={alg1, alg2, alg2b, alg3, alg4};
	
	printf("please input the array(length < %d), ctrl+d at the end:\n", MAXNUM);
	arraylen = 0;
	while( (arraylen<MAXNUM) && (scanf("%f", &array[arraylen]) != EOF) )
	{
		arraylen++;
	}
	
	unsigned int i = 0;
	printf("num  -----  function\n");
	for(; i <= F_alg4; i++)
	{
		printf("%2d   -----   %s\n", i, algFunName[i]);
	}
	printf("please input the number represented function name:\n");

	int fun_num = 0;
	scanf("%d", &fun_num);
	if((fun_num < F_alg1) || (fun_num > F_alg4))
	{
		printf("the fun_alg%d doesn't exist!", fun_num);
		return 1;
	}
	
	unsigned int lret = 0, rret = 0;
	float maxsum = pfun[fun_num](&lret, &rret);

	printf("the subsequence between position %d and %d has maxsum:%f\n", lret, rret, maxsum);
	return 0;
}
