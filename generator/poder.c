#include <stdio.h>

int poder(int t, int last, int peak){
	if(0 <= t && t < last){
		return t*100/peak;
	}
	else if(peak <= t && t < last){
		return (last - t)*100/(last - peak);
	}
	else{
		return 0;
	}
}
