package dataStructure;

import java.awt.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.node_data;

import utils.Point3D;
public class DGraph implements graph,Serializable {
	// search by id and des 
	HashMap<Integer, EdgeData> inner = new HashMap<>();
	 HashMap<Integer, HashMap<Integer, EdgeData>> edges=new HashMap<Integer, HashMap<Integer, EdgeData>>();
	 HashMap<Integer, NodeData> verticals=new HashMap<Integer,NodeData>();
	 int changes=0;
	private int edgecount;
//copy constructor to copy verticals hashmap and edges
	public DGraph(DGraph g) {
		for (NodeData n : g.verticals.values())

			this.verticals.put(n.getKey(), new NodeData(n));

		if (g.edges != null)
			for (HashMap<Integer, EdgeData> e : g.edges.values()) {
				for (EdgeData ee : e.values())
					this.edges.put(ee.source, new HashMap<Integer, EdgeData>(e));
			}
		this.changes = g.changes;
		this.edgecount = g.edgecount;

	}
	
	public void init(String g)
	{
		try
		{
			JSONObject graph= new JSONObject(g);
			JSONArray nodes= graph.getJSONArray("Nodes");
			int i=0;
			while(i<nodes.length())
			{
				JSONObject nod=nodes.getJSONObject(i);
				int key= nod.getInt("id");
				String loc= nod.getString("pos");
				String point[]=loc.split(",");
				double x=Double.parseDouble(point[0]);
				double y=Double.parseDouble(point[1]);

				double z=Double.parseDouble(point[2]);
				NodeData n=new NodeData(key,new Point3D(x,y,z));
				this.addNode(n);
				i++;
				

				
			}
		}
			catch(JSONException e)
			{
				e.printStackTrace();
			}
		
		try
		{
			JSONObject graph= new JSONObject(g);

			JSONArray edges= graph.getJSONArray("Edges");
			int j=0;
			while(j<edges.length())
			{
				JSONObject edg=edges.getJSONObject(j);
				int source=edg.getInt("src");
				int dest=edg.getInt("dest");
				double weight=edg.getDouble("w");
				this.connect(source, dest, weight);
				j++;
			}

			
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}

			
		}
	
	// default constructor
	public DGraph() {
		verticals = new HashMap<Integer, NodeData>();
		edges = new HashMap<Integer, HashMap<Integer, EdgeData>>();
		this.changes = 0;
		this.edgecount = 0;
	}
	
	//gets the node with the id key
	@Override
	public node_data getNode(int key) {
		if (this.verticals.get(key) == null)
			return null;
		NodeData node = verticals.get(key);
		return node;
	}
	
	@Override
	//gets the edge that connects src and dest nodes
	public edge_data getEdge(int src, int dest) {
		EdgeData e = null;
		if (edges.get(src).get(dest) == null) {
			return null;
		}
		e = edges.get(src).get(dest);
		return e;
	}
	//addds a node
	@Override
	public void addNode(node_data n) {
		// TODO Auto-generated method stub
		NodeData d = new NodeData((NodeData) n);
		verticals.put(d.getId(),d);
		changes++;
	}
	
	//add an edge that connects betweet src and dest with weight w
	@Override
	public void connect(int src, int dest, double w) {
		// TODO Auto-generated met hod stub
		EdgeData edge = new EdgeData(src, dest, w);
// if src and dest nodes are not in the graph returns a message
		if (this.verticals.get(src) == null || this.verticals.get(dest) == null) {
			System.out.print("cant connect src or dest are not found in the graph");
			return;
		}

		if (this.edges.get(src) == null) {
			HashMap<Integer, EdgeData> e = new HashMap<Integer, EdgeData>();
			e.put(dest, edge);
			this.edges.put(src, e);
			this.changes++;
			this.edgecount++;
		} else {
			this.edges.get(src).put(dest, edge);
			this.changes++;
			this.edgecount++;
		}
	}
	
	//get a list of all the nodes in the hashmap verticals
	@Override
	public Collection<node_data> getV() {
		// TODO Auto-generated method stub
		Collection<node_data> l = new ArrayList<node_data>();
		for (node_data data : verticals.values()) {
			l.add(data);
		}
		return l;
	}
	
	//removes node with id key and all the edges that connects it 
	@Override
	public node_data removeNode(int key) {
		if (this.verticals.get(key) == null) {
			return null;
		}
		NodeData d = verticals.get(key);
		this.verticals.remove(key);
		this.changes++;
		if (this.edges.get(key) != null) {
			int x = this.edges.get(key).size();

			this.edges.remove(key);
			this.changes = this.changes + x;
			this.edgecount = this.edgecount - x;
		}
		for (int i : this.edges.keySet()) {
			HashMap<Integer, EdgeData> e = this.edges.get(i);

			if (e.get(key) != null) {
				this.edges.get(i).remove(key);
				this.changes++;
				this.edgecount--;
			}
		}
		return d;

	}
	//remove and edge that connects between node src and noded dest
	@Override
	public edge_data removeEdge(int src, int dest) {
		EdgeData e = null;
		// if edge is not exit return null
		if (edges.get(src).get(dest) == null)
			return null;
		// if edge is exit remove it
		e = this.edges.get(src).get(dest);
		// and remove the edges associated with that node too
		edges.get(src).remove(dest);
		this.changes++;
		this.edgecount--;

		return e;
	}
	@Override
	// how many verticals in graph
	public int nodeSize() {
		// TODO Auto-generated method stub
		return verticals.size();
	}
	@Override
	// edges size
	public int edgeSize() {
		// TODO Auto-generated method stub
		return edgecount;
	}

	@Override
	// number of changes
	public int getMC() {
		// TODO Auto-generated method stub
		return changes;
	}
	@Override
	// getting the collection of negheiboors
	public Collection<edge_data> getE(int node_id) {
		// TODO Auto-gene.rated method stub
		// if vertex is not exit throw error
		if(verticals.get(node_id) == null) {
			throw new RuntimeException("Nice One Tricky Asshole ;)");
		}
		Collection<edge_data> l = new ArrayList<edge_data>();
		if(edges.get(node_id)!=null)
		{
		inner = edges.get(node_id);
		for (EdgeData edge : inner.values()) {
			l.add(edge);
		}
			
		}
		return l;
	}
// to string function
	public String toString() {
		String s = "";
		s = "Nodes: ";
		for (int i : this.verticals.keySet()) {
			s = s + this.verticals.get(i).toString() + ", ";
		}
		s = s + "\n" + "Edges: ";
		for (int i : this.edges.keySet()) {
			for (int j : this.edges.get(i).keySet())
				s = s + this.edges.get(i).get(j) + ", ";
		}
		return s;

	}

}