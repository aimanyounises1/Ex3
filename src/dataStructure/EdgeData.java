package dataStructure;

import java.io.Serializable;

public class EdgeData implements edge_data,Serializable {
	int dest;
	int source;
	double weight;
	int tag;
	String s;

	// Point3D c = new Point3D(dest.p.x()-1, dest.p.y()-1);
	public EdgeData(int sor, int d,double weight) {
		this.dest = d;
		this.source = sor;
		this.weight=weight;
	
	}
	EdgeData(EdgeData other) {
		this.dest = other.dest;
		this.source = other.source;
		this.weight = other.weight;
	}

	public EdgeData() {
		this.dest=0;
		this.source=0;
		this.weight=0;
		this.tag=0;
	}
	public void setInfo(String s)
	{
		
		
	}
	
	

	@Override
	public int getSrc() {
		// TODO Auto-generated method stub
		return this.source;
	}

	@Override
	public int getDest() {
		// TODO Auto-generated method stub
		return this.dest;
	}

	@Override
	public double getWeight() {
		// TODO Auto-generated method stub
		return weight;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return "source: " +this.source+"\n"+"dest: "+this.dest+"\n"+"weight: "+this.weight+"\n"+"info: "+this.s+"\n";
	}
	
	
	

	
	

	@Override
	public int getTag() {
		// TODO Auto-generated method stub
		return tag;
	}

	@Override
	public void setTag(int t) {
		this.tag = t;
	}
	public String toString()
	{
		return "{src:"+this.source+",dst:"+this.dest+",w:"+this.weight+"}";
		
	}
	
	public boolean equals(EdgeData e)
	{
		if(this.source!=e.source||this.dest!=e.dest||this.weight!=e.weight)
			return false;
		return true;
			
	}


}
