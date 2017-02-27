#include <cstdio>
#include <limits>
#include <math.h>
#include <queue>
#include <sstream>
#include "carte.h"
#include <string.h>

istream& operator >> (istream& is, Carte& carte){
	string chaine;
	string poubelle;
	Coordonnee coor;
	
	int val;
	string entrant;
	string sortant;
	string rue;
		
    is >> chaine;
	do{		
	    is >> coor;
		is >> poubelle;
		carte.vertice.nom = chaine;
		carte.vertice.coordonnee = coor;
		carte.graph.vertices.insert(carte.graph.vertices.end(), carte.vertice);
		is >> chaine;	  
	}while(is && chaine != "---");
	
	while(is){		
	    is >> rue;
		is >> poubelle;
		is >> entrant;
		is >> sortant;
		is >> val;
		is >> poubelle;
		
		carte.edge.nomDeRue = rue;
		carte.edge.vertex1 = entrant;
		carte.edge.vertex2 = sortant;
		carte.edge.weight = val;
		
		carte.graph.edges.push_back(carte.edge);	
	}
	
	//carte.graph.edges.pop_back();
	
    return is;
}