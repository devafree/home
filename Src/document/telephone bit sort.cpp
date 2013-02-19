#include <stdio.h>
#include <stdlib.h>

#define BITSPERWORD 32
#define SHIFT 5
#define MASK 0x1F
#define N 10000000

int a[1 + N/BITSPERWORD];

void set(int i) {        a[i>>SHIFT] |=  (1<<(i & MASK)); }
void clr(int i) {        a[i>>SHIFT] &= ~(1<<(i & MASK)); }
int test(int i) { return a[i>>SHIFT] &   (1<<(i & MASK)); }

int main()
{ 
    int i;

    int top = 1 + N/BITSPERWORD;
    for (i = 0; i < top; i++)
    {
        a[i] = 0;
    }

    FILE* fReadData = fopen("data1.txt", "r" );
    if (NULL == fReadData)
    {
        printf("fread data1.txt error!\n");
        return 1;
    }
    while (fscanf(fReadData, "%d", &i) != EOF)
    {
        set(i);
    }
    fclose(fReadData);

    FILE* fWriteData = fopen("data2.txt", "w");
    if (NULL == fWriteData)
    {
        printf("fopen data2.txt error!\n");
        return 1;
    }

    for (i = 0; i < N; i++)
    {
        if (test(i))
        {
            fprintf(fWriteData, "%d ", i);
        }
    }
    fclose(fWriteData);

    return 0;
}