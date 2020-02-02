import java.util.List;
import java.util.ArrayList;
//st[i].time[j]:頂点iから頂点jまでの最短時間
//st[i].route[j]:頂点iから頂点jまでの最短経路
class Shortest {
	int[] time;
	StringBuffer[] route;
	int[] next;

	Shortest(int V){
		time = new int[V];
		route = new StringBuffer[V];
		next = new int[V];
		for(int i = 0; i < V; i++){
			time[i] = Integer.MAX_VALUE;
			route[i] = new StringBuffer();
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

	void convert_str_into_int(int v){
		if(route[v].length() == 0){
			next[v] = v;
			return;
		}

		String a = route[v].toString();
		String[] s = a.split(", ");
		next[v] = Integer.parseInt(s[1]);
	}
}
