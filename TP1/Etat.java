/* INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * Hiver 2017
 * Joe Francois Abi Najem ABIJ18039207
 */


import java.util.Collection;
import java.util.LinkedList;

/**
 * Représente un état dans le monde.
 */
public class Etat implements Comparable<Etat> {

    // Référence sur le planete (pour accéder aux objets du planete).
    protected Planete planete;

    // Noyau de la représentation d'un état. Ici, on met tout ce qui rend l'état unique.
    /**
     * Emplacement du Htepien.
     */
    protected Emplacement emplacementHtepien;
    /**
     * Array indicant l'emplacement de chaque bombes (il y'en a qu'une seule
     * dans ce TP).
     */
    protected Emplacement emplacementsBombes[];
    /**
     * Array indicant l'état de chargement de chaque bombes pat le Htepien.
     */
    protected boolean bombesCharges[];
    /**
     * Nombre de bombes que porte le Htepien.
     */
    protected int nbbombesCharges = 0;

    // Variables pour l'algorithme A*.
    /**
     * État précédent permettant d'atteindre cet état.
     */
    protected Etat parent = null;
    /**
     * Action à partir de parent permettant d'atteindre cet état.
     */
    protected String actionFromParent;
    /**
     * f=g+h.
     */
    protected double f;
    /**
     * Meilleur coût trouvé pour atteindre cet été à partir de l'état initial.
     */
    protected double g = 0;
    /**
     * Estimation du coût restant pour atteindre le but.
     */
    protected double h;

    public Etat(Planete planete) {
        this.planete = planete;
    }

    /**
     * Fonction retournant les états successeurs à partir de cet été. Aussi
     * appelé fonction de transition. Cela permet d'explorer l'espace d'état (le
     * graphe de recherche).
     */
    public Collection<Successeur> genererSuccesseurs() {
        LinkedList<Successeur> successeurs = new LinkedList<Successeur>();

        if (nbbombesCharges == 0 && emplacementHtepien.type.equals("B")) {
            Successeur s = new Successeur();
            s.etat = clone();
            s.action = "Charger(" + planete.nomHtepien + ",B)";
            s.cout = 30.0;
            s.etat.actionFromParent = s.action;
            s.etat.parent = this;
            s.etat.parent.emplacementHtepien = emplacementHtepien;
            s.etat.parent.g = this.g;
            s.etat.g = s.cout + s.etat.parent.g;
            s.etat.nbbombesCharges = s.etat.nbbombesCharges + 1;
            successeurs.add(s);

        } else if (nbbombesCharges > 0 && emplacementHtepien.type.equals("S")) {
            Successeur s = new Successeur();
            s.etat = clone();
            s.action = "Decharger(" + planete.nomHtepien + ",B)";
            s.cout = 30.0;
            s.etat.actionFromParent = s.action;
            s.etat.parent = this;
            s.etat.parent.emplacementHtepien = emplacementHtepien;
            s.etat.parent.g = this.g;
            s.etat.g = s.cout + s.etat.parent.g;
            successeurs.add(s);

        } else {
            for (int i = 1; i < 5; i++) {
                if (planete.emplacements.get(intAndString(i)) != null) {

                    if (planete.emplacements.get(intAndString(i)).type.equals("+")) {
                        Successeur s = new Successeur();
                        s.etat = clone();
                        s.action = actionIs(i) + " = Lieu " + emplacementHtepien.nom + " -> Lieu " + intAndString(i) + ")";
                        s.cout = 0.0;
                        s.etat.emplacementHtepien = new Emplacement(intAndString(i), faireX(intAndString(i)), faireY(intAndString(i)), "+");
                        s.etat.actionFromParent = s.action;
                        s.etat.parent = this;
                        s.etat.parent.emplacementHtepien = emplacementHtepien;
                        s.etat.parent.g = this.g;
                        s.etat.g = s.cout + planete.vitesse + s.etat.parent.g;
                        successeurs.add(s);

                    } else if (planete.emplacements.get(intAndString(i)).type.equals("-") || planete.emplacements.get(intAndString(i)).type.equals("P")) {
                        Successeur s = new Successeur();
                        s.etat = clone();
                        s.action = actionIs(i) + " = Lieu " + emplacementHtepien.nom + " -> Lieu " + intAndString(i) + ")";
                        s.cout = 2.0;
                        if (planete.emplacements.get(intAndString(i)).type.equals("-")) {
                            s.etat.emplacementHtepien = new Emplacement(intAndString(i), faireX(intAndString(i)), faireY(intAndString(i)), "-");
                        } else if (planete.emplacements.get(intAndString(i)).type.equals("P")) {
                            s.etat.emplacementHtepien = new Emplacement(intAndString(i), faireX(intAndString(i)), faireY(intAndString(i)), "P");
                        }
                        s.etat.actionFromParent = s.action;
                        s.etat.parent = this;
                        s.etat.parent.emplacementHtepien = emplacementHtepien;
                        s.etat.parent.g = this.g;
                        s.etat.g = s.cout + planete.vitesse + s.etat.parent.g;
                        successeurs.add(s);

                    } else if (planete.emplacements.get(intAndString(i)).type.equals("#") || planete.emplacements.get(intAndString(i)).type.equals("E") || planete.emplacements.get(intAndString(i)).type.equals("S")) {
                        Successeur s = new Successeur();
                        s.etat = clone();
                        s.action = actionIs(i) + " = Lieu " + emplacementHtepien.nom + " -> Lieu " + intAndString(i) + ")";
                        s.cout = 1.0;
                        if (planete.emplacements.get(intAndString(i)).type.equals("#")) {
                            s.etat.emplacementHtepien = new Emplacement(intAndString(i), faireX(intAndString(i)), faireY(intAndString(i)), "#");
                        } else if (planete.emplacements.get(intAndString(i)).type.equals("E")) {
                            s.etat.emplacementHtepien = new Emplacement(intAndString(i), faireX(intAndString(i)), faireY(intAndString(i)), "E");
                        } else if (planete.emplacements.get(intAndString(i)).type.equals("S")) {
                            s.etat.emplacementHtepien = new Emplacement(intAndString(i), faireX(intAndString(i)), faireY(intAndString(i)), "S");
                        }
                        s.etat.actionFromParent = s.action;
                        s.etat.parent = this;
                        s.etat.parent.emplacementHtepien = emplacementHtepien;
                        s.etat.parent.g = this.g;
                        s.etat.g = s.cout + planete.vitesse + s.etat.parent.g;
                        successeurs.add(s);

                    } else if (planete.emplacements.get(intAndString(i)).type.equals("B")) {
                        if (nbbombesCharges == 0) {
                            Successeur s = new Successeur();
                            s.etat = clone();
                            s.action = actionIs(i) + " = Lieu " + emplacementHtepien.nom + " -> Lieu " + intAndString(i) + ")";
                            s.cout = 1.0;
                            s.etat.emplacementHtepien = new Emplacement(intAndString(i), faireX(intAndString(i)), faireY(intAndString(i)), "B");
                            s.etat.actionFromParent = s.action;
                            s.etat.parent = this;
                            s.etat.parent.emplacementHtepien = emplacementHtepien;
                            s.etat.parent.g = this.g;
                            s.etat.g = s.cout + planete.vitesse + s.etat.parent.g;
                            successeurs.add(s);
                        }

                    } else if (planete.emplacements.get(intAndString(i)).type.equals("S")) {
                        if (nbbombesCharges > 0) {
                            Successeur s = new Successeur();
                            s.etat = clone();
                            s.action = actionIs(i) + " = Lieu " + emplacementHtepien.nom + " -> Lieu " + intAndString(i) + ")";
                            s.cout = 1.0;
                            s.etat.emplacementHtepien = new Emplacement(intAndString(i), faireX(intAndString(i)), faireY(intAndString(i)), "S");
                            s.etat.actionFromParent = s.action;
                            s.etat.parent = this;
                            s.etat.parent.emplacementHtepien = emplacementHtepien;
                            s.etat.parent.g = this.g;
                            s.etat.g = s.cout + planete.vitesse + s.etat.parent.g;
                            successeurs.add(s);
                        }
                    }
                }
            }
        }
        return successeurs;
    }

    public int faireX(String leString) {
        return Integer.parseInt(leString.substring(0, leString.indexOf(45)));
    }

    public int faireY(String leString) {
        return Integer.parseInt(leString.substring(leString.indexOf(45) + 1));
    }

    public String actionIs(int cote) {
        String leString = "";
        if (cote == 1) {
            leString = "Ouest";
        } else if (cote == 2) {
            leString = "Est";
        } else if (cote == 3) {
            leString = "Nord";
        } else if (cote == 4) {
            leString = "Sud";
        }
        return leString;
    }

    public String intAndString(int stringIt) {
        int temp = emplacementHtepien.nom.indexOf(45);
        String x = emplacementHtepien.nom.substring(0, temp);
        String y = emplacementHtepien.nom.substring(temp + 1);

        int numX = Integer.parseInt(x);
        int numY = Integer.parseInt(y);

        String gauche = x + "-" + (numY - 1);
        String droite = x + "-" + (numY + 1);
        String haut = (numX - 1) + "-" + y;
        String bas = (numX + 1) + "-" + y;

        String leString = "";
        if (stringIt == 1) {
            leString = gauche;
        } else if (stringIt == 2) {
            leString = droite;
        } else if (stringIt == 3) {
            leString = haut;
        } else if (stringIt == 4) {
            leString = bas;
        }
        return leString;
    }

    /**
     * Crée un nouvel État en copiant l'état présent. Effectue une copie en
     * surface. En principe, vous n'aurez pas à modifier la méthode clone().
     */
    @Override
    public Etat clone() {
        Etat etat2 = new Etat(planete);
        etat2.nbbombesCharges = nbbombesCharges;
        etat2.emplacementHtepien = emplacementHtepien;
        etat2.emplacementsBombes = new Emplacement[emplacementsBombes.length];
        for (int i = 0; i < emplacementsBombes.length; i++) {
            etat2.emplacementsBombes[i] = emplacementsBombes[i];
        }

        etat2.bombesCharges = new boolean[bombesCharges.length];
        for (int i = 0; i < bombesCharges.length; i++) {
            etat2.bombesCharges[i] = bombesCharges[i];
        }
        return etat2;
    }

    /**
     * Relation d'ordre nécessaire pour TreeSet.
     */
    @Override
    public int compareTo(Etat o) {
        int c;
        c = emplacementHtepien.compareTo(o.emplacementHtepien);
        if (c != 0) {
            return c;
        }

        c = nbbombesCharges - o.nbbombesCharges;
        if (c != 0) {
            return c;
        }

        for (int i = 0; i < emplacementsBombes.length; i++) {
            c = (bombesCharges[i] ? 1 : 0) - (o.bombesCharges[i] ? 1 : 0);
            if (c != 0) {
                return c;
            }
            if (!bombesCharges[i]) {
                c = emplacementsBombes[i].compareTo(o.emplacementsBombes[i]);
                if (c != 0) {
                    return c;
                }
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        String s = "ETAT: f=" + f + "  g=" + g + "\n";
        s += "  Pos=" + emplacementHtepien.nom + "";
        for (int i = 0; i < emplacementsBombes.length; i++) {
            s += "\n  PosBombes[i]=";
            s += emplacementsBombes[i] == null ? "--" : emplacementsBombes[i].nom;
        }
        s += "\n";
        return s;
    }

}
