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

class Main {
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
			Edges[] es;
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
			
			Vehicle[] car;
			car = new Vehicle[20];

		}catch(FileNotFoundException e){
			System.err.println("The file which you specified doesn't exsit. e:" + e);
		}catch(IOException e){
			System.err.println("IOException occurred!" + e);
		}
	}
}
