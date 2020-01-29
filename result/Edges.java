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
