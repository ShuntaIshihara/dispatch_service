#include <stdio.h>

int check_filename(char argv[]){
	int length;
	for (length = 0; argv[length] != '\0'; length++);
	int i;
	for (i = length-1; i >= length-4; i--){
		switch (length - i){
			case 1:
			case 3: if (argv[i] != 't') return 1;
					break;
			case 2: if (argv[i] != 'x') return 1;
					break;
			case 4: if (argv[i] != '.') return 1;
					break;
			default: break;
		}
	}

	return 0;
}
