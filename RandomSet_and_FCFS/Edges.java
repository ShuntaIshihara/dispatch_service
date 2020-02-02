import java.util.List;
import java.util.ArrayList;

class Edges {
	List<Integer> u;
	List<Integer> d;

	Edges(){
		u = new ArrayList<Integer>();		
		d = new ArrayList<Integer>();
	}

	void append(int v, int dis){
		try{
			u.add(v);
			d.add(dis);
		}catch(ArrayIndexOutOfBoundsException e){
			System.err.println("degree is over 5. e:"+ e);
		}
	}
}
