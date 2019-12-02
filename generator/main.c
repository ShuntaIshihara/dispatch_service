/* 入力:[filename] [case_type] [seed] */
/* 入力はコマンドライン引数で受け取る */
//#include <stdio.h>
//#include <stdlib.h>

//#define MAXDEGREE 5

//typedef struct {
//	double x;
//	double y;
//	int degree = 0;
//} Vertex;
//
//typedef struct {
//	int v;
//	int u;
//	int d;
//} Edge;

//int main(int argc, char *argv[]){
/* コマンドライン引数の数が正しいかチェックする */
//	if (argc <= 1 && argc > 4){
//		fputs("Deffirent number of command line arguments\n", stderr);
//		exit(1);
//	}

/* 第一引数が正しいかチェックする */
//	if (check_filename(argv[1])){
//		fputs("filename is not correct: *.txt\n", stderr);
//		exit(1);
//	}

/* 第二引数が正しいかチェックする */
//	if (check_case_type(argv[2])){
//		fputs("case_type is not correct: '1' or '2'\n", stderr);
//		exit(1);
//	}

/* 第三引数が正しいかチェックする */
//	seed = atoi(argv[3]);
//	if(seed < 1 && seed > RAND_MAX){
//		fputs("seed is not correct: 1 <= seed <= RAND_MAX(>= 32767)\n", stderr);
//		exit(1);
//	}

/* [filename]で指定したファイルに書き込む */
//	FILE *fp;
//	fp = fopen(argv[1], "w");

/* [seed]と[case_type]から頂点と辺の数を決める */
//	srand((unsigned) seed);
//	if (argv[2] == '1') NoV = rand() % 200 + 200;
//	else NoV = 400; 
//	NoE = NoV * 2 / (rand() % 2 + 3);

/* 頂点の生成 */
/* |V| = R^2 + r を満たす最大の非負整数Rを見つける(ただしrも非負整数) */
//	R = max_square(NoV)

//	fclose(fp);

//	return 0;
//}
