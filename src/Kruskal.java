/* Kruskal.java */

import java.util.Hashtable;

import list.SList;

import dict.HashTableChained;
import graph.*;
import set.*;
import graph.*;
import graph.*
;
import graph.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   */
  public static WUGraph minSpanTree(WUGraph g){
	  WUGraph t = new WUGraph();
	  Object[] vertices = g.getVertices();
	  int x = 0;
	  EdgeWeight[] edges = new EdgeWeight[g.edgeCount()];
	  HashTableChained edgeHash = new HashTableChained(2*g.edgeCount());
	  for (Object o: vertices){	// adds all vertices to t + puts all edges in "edges" array
		  t.addVertex(o);
		  if (edges[-1] == null) {
			  Neighbors n = g.getNeighbors(o);
			  for (int i=0; i < n.neighborList.length; i++) {
				  EdgeWeight e = new EdgeWeight(o, n.neighborList[i], n.weightList[i]);
				  if (edgeHash.find(e) == null) {
					  edgeHash.insert(e, e);
					  edges[x] = e;
					  x++;
				  }
			  }
		  }
	  }
	  
	  quicksort(edges, 0, edges.length - 1); // sorts "edges" array
	  
	  int s = findSize(vertices.length);
	  DisjointSets set = new DisjointSets(s); // adds all min edges to graph t.
	  for (EdgeWeight ed: edges) {
		  int a = compFunction(ed.vertex1().hashCode(), s);
		  int b = compFunction(ed.vertex2().hashCode(), s);
		  if (set.find(a)!=set.find(b)) {
			  t.addEdge(ed.vertex1(), ed.vertex2(), ed.weight());
			  set.union(set.find(a),set.find(b));
		  }
	  }
	  
	  return t;
  }
  
  
  
  public static int findSize(int sizeEstimate) {
		// .5<= n/N <=1; N = # of buckets; n = sizeEstimate
	int lowerBound = 3*sizeEstimate / 2; // lower bound of 200 = 101; 199 = 100;
	int upperBound = 2 * sizeEstimate;
	return aPrime(upperBound, lowerBound);
  }
  
  private static int aPrime(int n, int lower) {
	  boolean[] prime = new boolean[n+1];
	  int i;
	  for (i=2; i <= n; i++) {
		  prime[i] = true;
	  }
	  for (int divisor = 2; divisor * divisor <= n; divisor++) {
		  if (prime[divisor]) {
			  for (i = 2 * divisor; i <= n; i = i + divisor) {
				  prime[i] = false;
			  }
		  }
	  }
	  
	  for (int index = lower; index < n; index++) {
		  if (prime[index] == true) {
			  return index;
		  }
	  }
	  for (int index = lower; index > 2; index--) {
		  if (prime[index] == true) {
			  return index;
		  }
	  }
	  return 0;
  }
  

  private static int compFunction(int code, int mod) {	// code(i) = (((ai+b) mod p) mod N)
	int p = 16908799;
	int a = 27947;
	int b = 2100001;
	return modulus(modulus(a*code + b, p), mod);
    // Replace the following line with your solution.
  }
  
  private static int modulus(int x, int n) {	// x mod n
	  while (x < 0) {
		  x = x + n;
	  }
	  return x % n;
  }
  
  
  public static void quicksort(EdgeWeight[] a, int low, int high) {
	  // If there's fewer than two items, do nothing.
	  if (low < high) {
	    int pivotIndex = low + (int) (Math.random()*((high-low)+1)); // <- random int in [low, high]
	    Comparable pivotw = (Comparable) a[pivotIndex].weight();
	    EdgeWeight pivot = a[pivotIndex];
	    a[pivotIndex] = a[high];                       // Swap pivot with last item
	    a[high] = pivot;

	    int i = low - 1;
	    int j = high;
	    do {
	      do { i++; } while (((Comparable) a[i].weight()).compareTo(pivotw) < 0);
	      do { j--; } while ((((Comparable) a[j].weight()).compareTo(pivotw) > 0) && (j > low));
	      if (i < j) {
	        EdgeWeight placeholder = a[i]; //swap a[i] and a[j];
	        a[i] = a[j];
	        a[j] = placeholder;
	      }
	    } while (i < j);

	    a[high] = a[i];
	    a[i] = pivot;                   // Put pivot in the middle where it belongs
	    quicksort(a, low, i - 1);                     // Recursively sort left list
	    quicksort(a, i + 1, high);                   // Recursively sort right list
	  }
  }

}