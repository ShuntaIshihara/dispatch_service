import java.io.*;
import java.util.ArrayList;
import java.util.List;
class Evaluation {
	static public void main(String[] args){
		//グラフの情報を読み込み記憶する
		try{
		File f = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(f));
		String str = br.readLine();
		String[] s = str.split(" ");
		int V = Integer.parseInt(s[0]);
		int E = Integer.parseInt(s[1]);

		//V,E 確認用--------------------------------------------------
//		System.out.println("V = " + V + ", E = " + E);
		//------------------------------------------------------------

		Edges[] es = new Edges[V];
		Edge[] e = new Edge[E];
		for(int i = 0; i < V; i++)
			es[i] = new Edges();
		for(int i = 0; i < E; i++)
			e[i] = new Edge();
		for(int i = 0; i < E; i++){
			str = br.readLine();
			String[] w = str.split(" ");
			int v = Integer.parseInt(w[0]);
			int u = Integer.parseInt(w[1]);
			int d = Integer.parseInt(w[2]);
			es[v].u.add(u);
			es[v].d.add(d);
			es[v].index.add(i);
			es[u].u.add(v);
			es[u].d.add(d);
			es[u].index.add(i);
			e[i].v = v;
			e[i].u = u;
			e[i].d = d;
		}

		//グラフ情報の確認用-------------------------------------------
//		for(int i = 0; i < V; i++){
//			System.out.print("["+i+"]: ");
//			for(int j = 0; j < es[i].u.size(); j++)
//				System.out.print(es[i].u.get(j)+" "+es[i].d.get(j)+" "+es[i].index.get(j)+", ");
//			System.out.println();
//		}
//		for(int i = 0; i < E; i++)
//			System.out.println("["+i+"]: v = "+e[i].v+", u = "+e[i].u+", d = "+e[i].d);
		//-------------------------------------------------------------

		//注文情報を読み込み記憶する
		str = br.readLine();
		int tmax = Integer.parseInt(str);
		OderList[] oderlist = new OderList[V];
		for(int i = 0; i < V; i++)
			oderlist[i] = new OderList();
		for(int t = 0; t < tmax; t++){
			str = br.readLine();
			int times = Integer.parseInt(str);
			for(int i = 0; i < times; i++){
				str = br.readLine();
				String[] w = str.split(" ");
				int v = Integer.parseInt(w[0]);
				oderlist[v].goal.add(Integer.parseInt(w[1]));
				oderlist[v].id.add(Integer.parseInt(w[2]));
				oderlist[v].time.add(t);
				oderlist[v].done.add(false);
			}
		}

		//注文情報確認用-----------------------------------------------
//		System.out.println("tmax = " + tmax);
//		for(int i = 0; i < V; i++){
//			System.out.println("["+i+"]: ");
//			for(int j = 0; j < oderlist[i].id.size(); j++){
//				System.out.print("id = "+oderlist[i].id.get(j)+" ");
//				System.out.print("goal = "+oderlist[i].goal.get(j)+" ");
//				System.out.print("time = "+oderlist[i].time.get(j)+" ");
//				System.out.println("done = "+oderlist[i].done.get(j));
//			}
//			System.out.println();
//		}
		//-------------------------------------------------------------
		br.close();


		//車の移動情報を読み込む
		f = new File(args[1]);
		br = new BufferedReader(new FileReader(f));
		Car[] car = new Car[20];
		for(int i = 0; i < car.length; i++)
			car[i] = new Car();

		//評価スタート
		long total = 0;
		int count = 0;
		for(int t = 0; t < tmax; t++){
			str = br.readLine();
			String[] w = str.split(" ");
			for(int i = 0; i < car.length; i++){
				int go = Integer.parseInt(w[i*3]);
				if(car[i].check(es[car[i].current], go)){
					car[i].move(go, es[car[i].current], e);
					if(car[i].e == null && car[i].current == Integer.parseInt(w[i*3+1])){
						if(car[i].id == -1){
							int index = oderlist[car[i].current].id.indexOf(Integer.parseInt(w[i*3+2]));
							car[i].id = oderlist[car[i].current].id.get(index);
							car[i].from = car[i].current;
							int wt = t - oderlist[car[i].current].time.get(index);
							car[i].score = tmax*tmax - wt*wt;

						}
						else{
							int index = oderlist[car[i].from].id.indexOf(car[i].id);
							if(car[i].current != oderlist[car[i].from].goal.get(index)){
								System.err.println("car["+i+"] wrong goal");
								System.err.println("car["+i+"] current is "+car[i].current);
								System.err.println("id = "+car[i].id);
								System.err.println("from = "+car[i].from);
								System.err.println("goal = "+oderlist[car[i].from].goal.get(index));
								System.err.println("t = "+t);
								System.exit(2);
							}
							total += car[i].score;
							count++;
							System.out.println("id "+car[i].id+": score = "+car[i].score);
							oderlist[car[i].from].done.set(index, true);
							car[i].score = 0;
							car[i].id = -1;
							car[i].from = -1;
						}
					}
				}
				else{
					System.err.println("car["+i+"] can not move from "+car[i].current+" to "+go+".");
					System.err.println("t = "+t);
					br.close();
					System.exit(1);
				}
			}
		}

		System.out.println("total = " + total);
		System.out.println("complete oder = " + count);
		br.close();

		}catch(FileNotFoundException e){
			System.err.println(e);
		}catch(IOException e){
			System.err.println(e);
		}
	}
}

class Car {
	Edge e;
	int current;
	int next;
	int d;
	int id;
	int from;
	int score;

	Car(){
		e = null;
		current = 0;
		next = 0;
		d = 0;
		id = -1;
		from = -1;
		score = 0;
	}

	void move(int go, Edges es, Edge[] e){
		if(this.e == null){
			next = go;
			if(current == next) return;
			this.e = new Edge(e[es.index.get(es.u.indexOf(go))]);
			d = this.e.d;
		}

		if(go == next) d--;
		if(go == current) d++;

		if(d <= 0){
			current = next;
			this.e = null;
		}
		else if(d >= this.e.d){
			this.e = null;
		}
	}


	boolean check(Edges es, int go){
		if(e != null){
			if(e.v == go || e.u == go)
				return true;
			System.out.println("here");
			System.out.println("e: "+e.v+"---"+e.u);
			return false;
		}
		else{
			if(current == go) return true;
			if(es.u.indexOf(go) == -1){
				 return false;
			}
			return true; 
		}
	}
}

class Edge {
	int v;
	int u;
	int d;

	Edge(){
		v = -1;
		u = -1;
		d = 0;
	}

	Edge(Edge e){
		v = e.v;
		u = e.u;
		d = e.d;
	}
}

class Edges {
	List<Integer> u;
	List<Integer> d;
	List<Integer> index;

	Edges(){
		u = new ArrayList<Integer>(); 
		d = new ArrayList<Integer>(); 
		index = new ArrayList<Integer>(); 
	}
}

class OderList {
	List<Integer> id;
	List<Integer> goal;
	List<Integer> time;
	List<Boolean> done;

	OderList(){
	id = new ArrayList<Integer>();
	goal = new ArrayList<Integer>();
	time = new ArrayList<Integer>();
	done = new ArrayList<Boolean>();
	}
}
