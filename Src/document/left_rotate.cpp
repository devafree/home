#include <stdio.h>
#include <stdlib.h>

template <typename T>
void reverse(T a[], unsigned int begin, unsigned int end)
{
	for(; begin < end; begin++, end--)
	{
		T tmp = a[begin];
		a[begin] = a[end];
		a[end] = tmp;
	}
}

int main(void)
{
	const unsigned int uBufNum = 100;
	char str[uBufNum] = {'\0'};
   	unsigned int uRotateNum = 0, uStrLen = 0;
	printf("Please input the string(length < %u):\n", uBufNum);
	scanf("%s", str);
	for(uStrLen=0; '\0'!=str[uStrLen]; uStrLen++)
		NULL;

	printf("Please input the left rotate digits(num <= %u)\n", uStrLen);
	scanf("%u", &uRotateNum);
	if(uRotateNum > uStrLen)
	{
		printf("Error: left rotate digits(%u) > String length(%u)\n",
				uRotateNum, uStrLen);
		return 1;
	}

	reverse(str, 0, uRotateNum-1);
	reverse(str, uRotateNum, uStrLen-1);
	reverse(str, 0, uStrLen-1);

	printf("the sorted string:\n%s\n", str);
		
	system("pause");
	return 0;
}
