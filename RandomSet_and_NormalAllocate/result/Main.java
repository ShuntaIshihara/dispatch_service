import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

class Main {
	static Shortest[] st;
	static Edges[] es;

	static void make(int current, int point, int t){
		//既に追加されている時間より大きいときはreturnする
		if(st[current].time[point] < t) return;

		//行き先が自分の頂点であるときの時間は0にする
		if(current == point) st[current].time[point] = 0;
		
		for(int i = 0; i < es[point].u.size(); i++){
			int p = es[point].u.get(i);
			int d = es[point].d.get(i);
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

		for(int i = 0; i < es[point].u.size(); i++){
			make(current, es[point].u.get(i), t+es[point].d.get(i));
		}
	}

	public static void main(String[] args){
		try{
			File f = new File(args[0]);
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
				for(int j = 0; j < es[i].u.size(); j++)
					System.out.println("es["+i+"]: "+es[i].u.get(j)+", "+es[i].d.get(j));
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

//			//shortestの確認用
//			int sumlen = 0;
//			int count = 0;
//			for(int i = 0; i < V; i++){
//				System.out.println("This point is : " + i);
//				System.out.println("shortest time list.");
//				System.out.print("[");
//				for(int j = 0; j < V; j++)
//					System.out.print(st[i].time[j] + ", ");
//				System.out.println("]");
//				System.out.println("shortest route list.");
//				for(int j = 0; j < V; j++){
//					System.out.print("[");
//					System.out.print(st[i].route[j]);
//					sumlen += st[i].route[j].length();
//					System.out.println("]");
//					count++;
//				}
//			}
//			System.out.println("average = " + (sumlen/count));

			//routeをint型の2次元配列rに変換する。
			for(int i = 0; i < V; i++){
				for(int j = 0; j < V; j++){
					st[i].convert_str_into_int(j);
				}
			}

			//convert_str_into_intの動作確認用
//			for(int i = 0; i < V; i++){
//				for(int j = 0; j < V; j++){
//					System.out.println("[" + st[i].next[j] + "]");
//				}
//			}


			Vehicle[] car;
			car = new Vehicle[20];
			
			for(int i = 0; i < 20; i++){
				car[i] = new Vehicle();
			}

			Oder[] oder = new Oder[V];
			for(int i = 0; i < V; i++){
				oder[i] = new Oder();
			}

			int tmax = Integer.parseInt(br.readLine());
			//tmax確認用
//			System.out.println(tmax);

			//車の配置をランダムに決める
			Random_set.set(car, st);

			StringBuffer num = new StringBuffer(args[0]);
			num.delete(0, 8);
			num.delete(2, num.length());
			FileWriter fw = new FileWriter("result.txt");
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

			for(int t = 0; t < tmax; t++){
				//注文を読み込む
				s = br.readLine();
				int times = Integer.parseInt(s);
				for(int i = 0; i < times; i++){
					s = br.readLine();
					String[] b = s.split(" ");
					int occur = Integer.parseInt(b[0]);
					oder[occur].goal.add(Integer.parseInt(b[1]));
					oder[occur].id.add(Integer.parseInt(b[2]));
					oder[occur].exist = true;
				}

				NomalAllocate.allocate(oder, car, st);

				for(int i = 0; i < car.length; i++){
					int go = car[i].move(st);
					if(car[i].empty != 0)
						pw.print(go+" "+car[i].goal+" "+car[i].passenger[0]+" ");
					else
						pw.print(go+" -1 -1 ");
					car[i].renew(st, go);
					car[i].check();
				}
				pw.println();

				if(times != 0)
					Random_set.set(car, st);
			}
			pw.close();

		}catch(FileNotFoundException e){
			System.err.println("The file which you specified doesn't exsit. e:" + e);
		}catch(IOException e){
			System.err.println("IOException occurred!" + e);
		}
	}
}
