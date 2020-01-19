import java.io.*;

class Edges {
	int[] u;
	int[] d;

	Edges(){
		u = new int[5];
		d = new int[5];
		for(int i = 0; i < 5; i++){
			u[i] = -1;
			d[i] = -1;
		}
	}

	void append(int v, int dis){
		try{
			int i;
			for(i = 0; u[i] != -1; i++);
			u[i] = v;
			d[i] = dis;
		}catch(ArrayIndexOutOfBoundsException e){
			System.err.println("degree is over 5. e:"+ e);
		}
	}
}

//st[i].time[j]:頂点iから頂点jまでの最短時間
//st[i].route[j]:頂点iから頂点jまでの最短経路
class Shortest {
	int[] time;
	StringBuffer[] route;

	Shortest(int V){
		time = new int[V];
		route = new StringBuffer[V];
		for(int i = 0; i < V; i++){
			time[i] = Integer.MAX_VALUE;
			route[i] = new StringBuffer("");
		}
	}

	void reverse(int i){
		int f = route[i].indexOf(", ", 0);
		int l = route[i].lastIndexOf(", ", route[i].length());
		int nf, nl;
		String fs, ls;
		while(true){
			if(f >= l) break;
			nf = route[i].indexOf(", ", f+1);
			nl = route[i].indexOf(", ", l+1);
			if(nf == -1) nf = route[i].length();
			if(nl == -1) nl = route[i].length();

			fs = route[i].substring(f+2, nf);
			ls = route[i].substring(l+2, nl);

			route[i].replace(f+2, f+2+fs.length(), ls);
			route[i].replace(l+2+ls.length()-fs.length(), l+2+ls.length()+ls.length()-fs.length(), fs);
			f = route[i].indexOf(", ", f+1);
			l = route[i].lastIndexOf(", ", l+ls.length()-fs.length()-1);
		}
	}
}

class Main {
	static Shortest[] st;
	static Edges[] es;

	static void make(int current, int point, int t){
		//既に追加されている時間より大きいときはreturnする
		if(st[current].time[point] < t) return;

		//行き先が自分の頂点であるときの時間は0にする
		if(current == point) st[current].time[point] = 0;
		
		for(int i = 0; i < 5 && es[point].u[i] != -1; i++){
			int p = es[point].u[i];
			int d = es[point].d[i];
			if(st[current].time[p] > t+d){
				st[current].time[p] = t+d;
				st[p].time[current] = t+d;

				st[current].route[p].delete(0,st[current].route[p].length());
				st[current].route[p].append(st[current].route[point]);
				st[current].route[p].append(", ");
				st[current].route[p].append(p);

				st[p].route[current].delete(0,st[p].route[current].length());
				st[p].route[current].append(st[current].route[point]);
				//並び順を逆にする
				//BufferString reverse()だと文字列が逆さまになってしまう
				st[p].reverse(current);
				st[p].route[current].append(", ");
				st[p].route[current].append(current);

			}
		}

		for(int i = 0; i < 5 && es[point].u[i] != -1; i++){
			make(current, es[point].u[i], t+es[point].d[i]);
		}
	}

	public static void main(String[] args){
		try{
			File f = new File("../test/"+args[0]);
			if(!f.exists()) throw new FileNotFoundException();
			BufferedReader br = new BufferedReader(new FileReader(f));

			String s;
			s = br.readLine();
			String[] a = s.split(" ");
			int V = Integer.parseInt(a[0]);
			int E = Integer.parseInt(a[1]);
			System.out.println("V = "+a[0]+", E = "+a[1]);
			es = new Edges[V];
			for(int i = 0; i < V; i++){
				es[i] = new Edges();
			}
			
			for(int i = 0; i < E; i++){
				s = br.readLine();
				String[] w = s.split(" ");
				int v = Integer.parseInt(w[0]);
				int u = Integer.parseInt(w[1]);
				int d = Integer.parseInt(w[2]);
				es[v].append(u,d);
				es[u].append(v,d);
			}

			//グラフの情報の読み込み確認用
			for(int i = 0; i < V; i++){
				for(int j = 0; j < 5 && es[i].u[j] != -1; j++)
					System.out.println("es["+i+"]: "+es[i].u[j]+", "+es[i].d[j]);
			}

			int R;
			for(R = 0; R*R <= V; R++);
			R--;

			//|V| = R^2 + rとなるRの確認用
			System.out.println("R = " + R);

			st = new Shortest[V];
			for(int i = 0; i < V; i++){
				st[i] = new Shortest(V);
			}
			for(int i = 0; i < V; i++){
				make(i, i, 0);
			}
			//[, a, b, c, ...]となっているので最初の", "を削除する
			for(int i = 0; i < V; i++){
				for(int j = 0; j < V; j++){
					if(st[i].route[j].length() == 0) continue;
					st[i].route[j].delete(0,2);
				}
			}

			//shortestの確認用
			for(int i = 0; i < V; i++){
				System.out.println("This point is : " + i);
				System.out.println("shortest time list.");
				System.out.print("[");
				for(int j = 0; j < V; j++)
					System.out.print(st[i].time[j] + ", ");
				System.out.println("]");
				System.out.println("shortest route list.");
				for(int j = 0; j < V; j++){
					System.out.print("[");
					System.out.print(st[i].route[j]);
					System.out.println("]");
				}
			}

//			Vehicle[] car;
//			car = new Vehicle[20];

		}catch(FileNotFoundException e){
			System.err.println("The file which you specified doesn't exsit. e:" + e);
		}catch(IOException e){
			System.err.println("IOException occurred!" + e);
		}
	}
}
