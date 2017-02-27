#include <fstream>
#include <iostream>
#include <string>
#include <list>
#include <math.h>
#include "carte.h"
#include <stdlib.h>

using namespace std;

int faireInt(string s);
void MakeSet(string vertex, map<string,int> &rank, map<string,string> &parent);
string Find(string vertex, map<string,int> &rank, map<string,string> &parent);

int faireInt(string s){
	int retour;
	s.erase(0,1);
	retour = atoi(s.c_str());	
	return retour;
}

void MakeSet(string vertex, map<string,int> &rank, map<string,string> &parent){
	parent[vertex] = vertex;
	rank[vertex] = 0;
}

string Find(string vertex, map<string,int> &rank, map<string,string> &parent){
	if(parent[vertex] == vertex){
		return parent[vertex];
	}else{
		return Find(parent[vertex], rank, parent);
	}	
}

void tp3(Carte& carte){
	
	map<string,int> rank;
	map<string,string> parent;
		
    for(int i = 0; i < int(carte.graph.vertices.size()); ++i){	
	   MakeSet(carte.graph.vertices[i].nom, rank, parent);
	}
		
	while(!carte.graph.edges.empty()){
	    int laquel = 0;
	    string n = carte.graph.edges[0].nomDeRue;
	    string v1 = carte.graph.edges[0].vertex1;
	    string v2 = carte.graph.edges[0].vertex2;
	    int w = carte.graph.edges[0].weight;
	    for(int i = 1; i < int(carte.graph.edges.size()); ++i){	
	        if(carte.graph.edges[i].weight > w){
	    		n = carte.graph.edges[i].nomDeRue;
	            v1 = carte.graph.edges[i].vertex1;
	            v2 = carte.graph.edges[i].vertex2;
	            w = carte.graph.edges[i].weight;
	    		laquel = i;
	     	}else if(carte.graph.edges[i].weight == w){
		    	if(faireInt(carte.graph.edges[i].vertex1) < faireInt(v1)/*carte.graph.edges[i].vertex1 < v1*/){
		    		n = carte.graph.edges[i].nomDeRue;
	                v1 = carte.graph.edges[i].vertex1;
	                v2 = carte.graph.edges[i].vertex2;
	                w = carte.graph.edges[i].weight;
			        laquel = i;
			    }else if(faireInt(carte.graph.edges[i].vertex1) == faireInt(v1)/*carte.graph.edges[i].vertex1 == v1*/){
			    	if(faireInt(carte.graph.edges[i].vertex2) < faireInt(v2)/*carte.graph.edges[i].vertex2 < v2*/){
				        n = carte.graph.edges[i].nomDeRue;
	                    v1 = carte.graph.edges[i].vertex1;
	                    v2 = carte.graph.edges[i].vertex2;
	                    w = carte.graph.edges[i].weight;
			    		laquel = i;
			    	}
		    	}
		    }
	   
	    }
	
	    carte.graph.edges.erase(carte.graph.edges.begin()+laquel);
	
	    carte.edge.nomDeRue = n;
	    carte.edge.vertex1 = v1;
	    carte.edge.vertex2 = v2;
	    carte.edge.weight = w;
	
	    carte.edgeSorted.push_back(carte.edge);
	}
	
	for(int i = 0; i < int(carte.edgeSorted.size()); ++i){
		string root1 = Find(carte.edgeSorted[i].vertex1, rank, parent);
		string root2 = Find(carte.edgeSorted[i].vertex2, rank, parent);
		if(root1 != root2){
			carte.A.push_back(carte.edgeSorted[i]);
			if(rank[root1] > rank[root2]){
				parent[root2] = root1;
			}else if(rank[root2] > rank[root1]){
				parent[root1] = root2;
			}else{
				parent[root1] = root2;
				rank[root2]++;
			}
		}
	}
		
	while (!carte.A.empty()){
	    int laquel = 0;
	    string n = carte.A[0].nomDeRue;
	    string v1 = carte.A[0].vertex1;
	    string v2 = carte.A[0].vertex2;
	    int w = carte.A[0].weight;
	    for(int i = 1; i < int(carte.A.size()); ++i){	
	        if(carte.A[i].weight < w){
	    		n = carte.A[i].nomDeRue;
	            v1 = carte.A[i].vertex1;
	            v2 = carte.A[i].vertex2;
	            w = carte.A[i].weight;
	    		laquel = i;
	     	}else if(carte.A[i].weight == w){
		    	if(/*faireInt(carte.A[i].vertex1) < faireInt(v1)//carte.A[i].vertex1 < v1*/carte.A[i].vertex1 < v1){
		    		n = carte.A[i].nomDeRue;
	                v1 = carte.A[i].vertex1;
	                v2 = carte.A[i].vertex2;
	                w = carte.A[i].weight;
			        laquel = i;
			    }else if(/*faireInt(carte.A[i].vertex1) == faireInt(v1)//carte.A[i].vertex1 == v1*/carte.A[i].vertex1 == v1){
			    	if(/*faireInt(carte.A[i].vertex2) < faireInt(v2)//carte.A[i].vertex2 < v2*/carte.A[i].vertex2 < v2){
				        n = carte.A[i].nomDeRue;
	                    v1 = carte.A[i].vertex1;
	                    v2 = carte.A[i].vertex2;
	                    w = carte.A[i].weight;
			    		laquel = i;
			    	}
		    	}
		    }
	   
	    }
	
	    carte.A.erase(carte.A.begin()+laquel);
	
	    carte.edge.nomDeRue = n;
	    carte.edge.vertex1 = v1;
	    carte.edge.vertex2 = v2;
	    carte.edge.weight = w;
	
	    carte.edgeFinal.push_back(carte.edge);
	}
	
	int coutTotal = 0;
	
	for(int i = 0; i < int(carte.edgeFinal.size()); ++i){
		cout << carte.edgeFinal[i].nomDeRue << " " << ":" << " " << carte.edgeFinal[i].vertex1 << " " << carte.edgeFinal[i].vertex2 << " " << carte.edgeFinal[i].weight << endl;
        coutTotal = coutTotal + carte.edgeFinal[i].weight;		
	}
	
	cout << "---" << endl;
	cout << coutTotal << endl;
}

int main(int argc, const char** argv)
{
    if(argc!=2){
        cout << "Syntaxe: ./tp3 carte.txt" << endl;
        return 1;
    }

    Carte carte;
    {
        ifstream fichiercarte(argv[1]);
        if(fichiercarte.fail()){
            cout << "Erreur ouverture du fichier : " << argv[1] << endl;    
            return 1;
        }		
        fichiercarte >> carte;
    }

    tp3(carte);
    return 0;
}