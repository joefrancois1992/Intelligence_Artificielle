/* INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * Hiver 2017
 * Joe Francois Abi Najem ABIJ18039207
 */


public class Route {

    public Route(Emplacement origine, Emplacement destination) {
        this.origine = origine;
        this.destination = destination;
    }

    public double getLongueur() {
        return origine.positionGeographique.distance(destination.positionGeographique);
    }

    protected Emplacement origine;
    protected Emplacement destination;

}
