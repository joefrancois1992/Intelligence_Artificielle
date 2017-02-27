/* INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * Hiver 2017
 * Joe Francois Abi Najem ABIJ18039207
 */

import static java.lang.Math.*;

public class EuclideanHeuristique extends Heuristique {

    public EuclideanHeuristique(Planete planete) {
        super(planete);
    }

    @Override
    public double estimerCoutRestant(Etat etat, But but) {
        double leX;
        double leY;
        double calcul;

        if (etat.nbbombesCharges == 0) {
            leX = abs(etat.emplacementHtepien.positionGeographique.getX() - etat.emplacementsBombes[0].positionGeographique.getX());
            leY = abs(etat.emplacementHtepien.positionGeographique.getY() - etat.emplacementsBombes[0].positionGeographique.getY());
            calcul = sqrt((leX * leX) + (leY * leY));
            return calcul;
        } else {
            leX = abs(etat.emplacementHtepien.positionGeographique.getX() - but.destinationsBombes[0].positionGeographique.getX());
            leY = abs(etat.emplacementHtepien.positionGeographique.getY() - but.destinationsBombes[0].positionGeographique.getY());
            calcul = sqrt((leX * leX) + (leY * leY));
            return calcul;
        }
    }

}
