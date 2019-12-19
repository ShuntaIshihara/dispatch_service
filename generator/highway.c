#include <stdio.h>
#include <stdlib.h>
#include "struct.h"
#include <math.h>

typedef struct binary{
	Edge e;
	struct binary *right;
	struct binary *left;
}Binary;

/*データxが属する木の根を得る*/
int find(int x, int par[]){
	if(x == par[x]) return x;
	return par[x] = find(par[x], par);
}

/*データが異なる木に属しているか*/
int is_diffirent(int x, int y, int par[]){
	/*親が違ければtrue*/
	return find(x, par) != find(y, par);
}

/*頂点xの木と頂点yの木を結合する*/
void unit(int x, int y, int par[], int size[]){
	/*根ノードを取得する*/
	int i = find(x, par);
	int j = find(y, par);

	/*大きい木にマージする*/
	if(size[i] < size[j]){
		int w = i;
		i = j;
		j = w;
	}

	par[j] = i;
	size[i] += size[j];
}

int a = 0;
void mst(Binary *root, Edge e[], int par[], int size[], int length){
	if(a >= length) return;

	if(root->left != NULL) mst(root->left, e, par, size, length);

	/*２つの頂点が異なる木に属している時*/
	if(is_diffirent(root->e.v, root->e.u, par)){
		e[a].v = root->e.v;
		e[a].u = root->e.u;
		e[a].d = root->e.d * 2;
		a += 1;
		unit(root->e.v, root->e.u, par, size);
	}

	if(root->right != NULL) mst(root->right, e, par, size, length);
}

/*二分木に新たに値を追加する関数*/
void b_tree(Edge e, Binary *root){
	Binary *node;
	node = (Binary*)malloc(sizeof(Binary));

	if(e.d < root->e.d){
		if(root->left != NULL) b_tree(e, root->left);
		else{
			node->e = e;
			node->left = NULL;
			node->right = NULL;
			root->left = node;
		}
	}
	else{
		if(root->right != NULL) b_tree(e, root->right);
		else{
			node->e = e;
			node->left = NULL;
			node->right = NULL;
			root->right = node;
		}
	}
}
/*テスト用
void tostring(Binary *root){
	if(root->left != NULL) tostring(root->left);
	printf("v = %3d, u = %3d, d = %f\n", root->e.v, root->e.u, root->e.d);
	if(root->right != NULL) tostring(root->right);
}
*/

void highway(Vertex v[], Edge e[], int NoV){
	/*頂点が木に含まれているのかの情報を保持するデータ構造*/
	int i;
	Edge w;
	w.v = 0;
	w.u = NoV/2;
	double x = v[NoV/2].x - v[0].x;
	double y = v[NoV/2].y - v[0].y;
	w.d = sqrt(x*x + y*y);

	Binary *root;
	root = (Binary*)malloc(sizeof(Binary));
	root->e = w;
	root->left = NULL;
	root->right = NULL;

	/*完全グラフを作成する*/
	/*グラフの辺は左の子が小さい値、右の子が大きい値となるように二分木によるデータ構造で格納される*/
	int j;
	for(i = 0; i < NoV; i++){
		for(j = i+1; j < NoV; j++){
			if(i == 0 && j == NoV/2) continue;
			w.v = i;
			w.u = j;
			double x = v[j].x - v[i].x;
			double y = v[j].y - v[i].y;
			w.d = sqrt(x*x + y*y);
			b_tree(w, root);
		}
	}

	/*par[i]: データiの親が入っている。par[i] == iのときは根ノード*/
	int par[NoV];
	/*size[i]: データiを根とする木のサイズ。iが根以外のときは無意味な値*/
	int size[NoV];
	for(i = 0; i < NoV; i++){
		size[i] = 1;
		par[i] = i;
	}
	mst(root, e, par, size, NoV-1);
	for(i = 0; i < NoV; i++){
		printf("par[%d] = %d\n", i, par[i]);
	}
	for(i = 0; i < NoV-1; i++)
		printf("%3d: v = %3d, u = %3d, d = %f\n", i, e[i].v, e[i].u, e[i].d);
	free(root);
}
