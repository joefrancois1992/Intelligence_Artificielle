/* INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * Hiver 2017
 * Joe Francois Abi Najem ABIJ18039207
 */


public class Heuristique {

    protected Planete planete;

    public Heuristique(Planete planete) {
        this.planete = planete;
    }

    /**
     * Estime et retourne le coût restant pour atteindre le but à partir d'un
     * état. Attention : pour être admissible, cette fonction heuristique ne
     * doit pas surestimer le coût restant.
     */
    public double estimerCoutRestant(Etat etat, But but) {
        return 0;
    }
}
