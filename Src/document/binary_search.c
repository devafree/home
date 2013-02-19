#include <stdio.h>
#include <stdlib.h>

int bSearch(int a[], int l, int u, int num)
{
	while(l <= u)
	{
		int m = (l+u)/2;
		if(num == a[m])
		{
			return m;
		}
		else if(num < a[m])
		{
			u = m-1;
		}
		else
		{
			l = m+1;
		}
	}
	return -1;
}

int main(void)
{
	const unsigned int array_size = 100;
	int array[array_size];
	int num;
	int i = 0;

	printf("please input the sorted array, ctrl+d at the end:\n");
	
	while( (i<array_size) && (scanf("%d", &array[i]) != EOF) )
	{
		i++;
	}
	if(0 != i)
	{
		i--;
	}

	printf("please input the find number:\n");
	scanf("%d", &num);

	int ret = bSearch(array, 0, i, num);
	if(-1 == ret)
	{
		printf("%d can't find!\n", num);
	}
	else
	{
		printf("%d find at position: %d\n", num, ret);
	}
	return 0;
}
