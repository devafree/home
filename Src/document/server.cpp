#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
//#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>

#define MYPORT 3490
#define BACKLOG 10

int main(int argc, char *argv[])
{
	int listenfd, connfd;
	struct sockaddr_in my_addr;
	struct sockaddr_in their_addr;
	int sin_size;

	if( (listenfd = socket(AF_INET, SOCK_STREAM, 0)) == -1 )
	{
		perror("socket");
		exit(1);
	}

	my_addr.sin_family = AF_INET;
	my_addr.sin_port = htons(MYPORT);
	my_addr.sin_addr.s_addr = htonl(INADDR_ANY);
	bzero(&(my_addr.sin_zero), 8);
	
	if( bind(listenfd, (struct sockaddr*)&my_addr, sizeof(struct sockaddr)) == -1 )
	{
		perror("bind");
		exit(1);
	}

	if( listen(listenfd, BACKLOG) == -1)
	{
		perror("listen");
		exit(1);
	}

	while(1)
	{
		sin_size = sizeof(struct sockaddr_in);
		if( (connfd = accept(listenfd, (struct sockaddr *)&their_addr, (socklen_t *)&sin_size)) == -1)
		{
			perror("accpet");
			continue;
		}

		printf("server: got connection from %s\n",\
				inet_ntoa(their_addr.sin_addr));

		if( !fork() )
		{
			/*this is the child process*/
			if( send(connfd, "Hello, world!\n", 14, 0) == -1 )
			{
				perror("send");
			}

			close(connfd);
			exit(0);
		}
	}
	
	close(listenfd);
	while( waitpid(-1, NULL, WNOHANG) > 0); /*clean up child processes*/
	
	return 0;
}

