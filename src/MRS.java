import java.io.*;
import java.util.*;

public class MRS{

	//w,DG_z link_into_graph (L)
	// Construct the Disk Graph DGz, definition-4.4 from olga:2012, using the set of links L
	// And the weight w for the links using the data-rates

	//G,A graph_into_protocol (DGz)
	// Construct the conflict graph G and the vertex set A from DGz

	//D orientate_edge (G)
	// Assign an orientation on the conflict edges (needs more study on how to do)

	//N^in_D,N^out_D get_in_out (D)
	// Establish for all links the in and out edges

	//x PDA(w, N^in_D, N^out_D, eps)
	// Price-Directive Algorithm

	//I PG(w, A, x)
	// Prune-and-Grow algorithm

	public static void main(String[] args) {
		for(int i = 0;i<args.length;i++) {
		     System.out.println(args[i]);
		}		

		System.out.println("link_into_graph");

		System.out.println("graph_into_protocol");

		System.out.println("orientate_edge");

		System.out.println("get_in_out");

		System.out.println("PDA");

		System.out.println("PG");

	}
}