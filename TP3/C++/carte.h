#if !defined(_CARTE__H_)
#define _CARTE__H_
#include "coordonnee.h"
#include <cassert>
#include <istream>
#include <list>
#include <map>
#include <string>
#include <vector>

using namespace std;

class Carte{
    public:
	    struct Edge {
			string nomDeRue;
			string vertex1;
			string vertex2;
			int weight;		
        }edge;
		
		struct Vertice{
			string nom;
			Coordonnee coordonnee;
		}vertice;
		
	    struct Graph{
			vector<Vertice> vertices;
			vector<Edge> edges;			
		}graph;
		
		vector<Edge> edgeSorted;	
		vector<Edge> A;
		vector<Edge> edgeFinal;
	
    private:
	
    friend istream& operator >> (istream& is, Carte& carte);
};
#endif