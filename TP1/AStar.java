/* INF4230 - Intelligence artificielle
 * UQAM / Département d'informatique
 * Hiver 2017
 * Joe Francois Abi Najem ABIJ18039207
 */

import java.util.*;

public class AStar {

    static protected double coutSolution;
    static protected double seconds;
    static protected int noeudGenere = 0;
    static protected int noeudExplores = 0;

    public static List<String> genererPlan(Etat etatInitial, But but, Heuristique heuristique) {
        long startTime = System.nanoTime();

        LinkedList<String> plan = new LinkedList<String>();
        LinkedList<String> plan2 = new LinkedList<String>();
        TreeSet<Etat> openS = new TreeSet<Etat>();
        TreeSet<Etat> closedS = new TreeSet<Etat>();
        TreeSet<Etat> open = new TreeSet<Etat>();
        TreeSet<Etat> closed = new TreeSet<Etat>();
        Etat nouvEtat = null;
        Etat etatTemp = etatInitial;

        etatTemp.f = etatTemp.g + heuristique.estimerCoutRestant(etatTemp, but);
        open.add(etatTemp);

        while (!open.isEmpty() && etatTemp.nbbombesCharges == 0) {

            for (Etat e : open) {
                e.f = e.g + heuristique.estimerCoutRestant(e, but);
                
                if (e.f < etatTemp.f) {
                    etatTemp = e;
                }
            }            
             
            if (etatTemp.emplacementHtepien.type.equals("B")) {
                closed.add(etatTemp);
                Collection<Successeur> sucDec = new LinkedList<>();
                sucDec = etatTemp.genererSuccesseurs();
                Successeur[] succDech = sucDec.toArray(new Successeur[0]);
                etatTemp = succDech[0].etat;

                nouvEtat = etatTemp;
                while (!etatTemp.emplacementHtepien.type.equals("E")) {
                    plan.add(etatTemp.actionFromParent);
                    etatTemp = etatTemp.parent;
                }
                Collections.reverse(plan);
                break;
            }
            
            open.remove(etatTemp);
            closed.add(etatTemp);

            Collection<Successeur> succ = new LinkedList<>();
            succ = etatTemp.genererSuccesseurs();
            Successeur[] s = succ.toArray(new Successeur[0]);

            for (int i = 0; i < s.length; i++) {
                boolean check = false;
                boolean check1 = false;
                if ((etatTemp.parent == null) || !s[i].etat.emplacementHtepien.nom.equals(etatTemp.parent.emplacementHtepien.nom)) {
                    for (Etat e : closed) {
                        if (e.emplacementHtepien.nom.equals(s[i].etat.emplacementHtepien.nom)) {
                            check = true;
                        }
                    }

                    if (check == false) {
                        for (Etat e : open) {
                            if (e.emplacementHtepien.nom.equals(s[i].etat.emplacementHtepien.nom)) {
                                if (s[i].etat.g >= e.g) {
                                    check1 = true;
                                }
                            }

                        }
                        if (check1 == false) {
                            open.add(s[i].etat);
                        }
                    }
                }
            }

            if (!open.isEmpty()) {
                etatTemp = open.first();
            }
        }

        nouvEtat.f = nouvEtat.g + heuristique.estimerCoutRestant(nouvEtat, but);
        openS.add(nouvEtat);

        while (!openS.isEmpty() && nouvEtat.nbbombesCharges > 0) {
            for (Etat e : openS) {
                e.f = e.g + heuristique.estimerCoutRestant(e, but);

                if (e.f < nouvEtat.f) {
                    nouvEtat = e;
                }
            }

            if (nouvEtat.emplacementHtepien.type.equals("S")) {
                closedS.add(nouvEtat);
                Collection<Successeur> sucDec = new LinkedList<>();
                sucDec = nouvEtat.genererSuccesseurs();
                Successeur[] succDech = sucDec.toArray(new Successeur[0]);
                nouvEtat = succDech[0].etat;

                noeudGenere = noeudGenere + 1;
                coutSolution = nouvEtat.g;

                while (!nouvEtat.emplacementHtepien.type.equals("B")) {
                    plan2.add(nouvEtat.actionFromParent);
                    nouvEtat = nouvEtat.parent;
                }

                Collections.reverse(plan2);
                for (int i = 0; i < plan2.size(); i++) {
                    plan.add(plan2.get(i));
                }
                break;
            }

            openS.remove(nouvEtat);
            closedS.add(nouvEtat);

            Collection<Successeur> succ = new LinkedList<>();
            succ = nouvEtat.genererSuccesseurs();
            Successeur[] s = succ.toArray(new Successeur[0]);

            for (int i = 0; i < s.length; i++) {
                if (noeudGenere < s[i].etat.emplacementHtepien.id) {
                    noeudGenere = s[i].etat.emplacementHtepien.id;
                }

                boolean check = false;
                boolean check1 = false;
                if ((nouvEtat.parent == null) || !s[i].etat.emplacementHtepien.nom.equals(nouvEtat.parent.emplacementHtepien.nom)) {
                    for (Etat e : closedS) {
                        if (e.emplacementHtepien.nom.equals(s[i].etat.emplacementHtepien.nom)) {
                            check = true;
                        }
                    }

                    if (check == false) {
                        for (Etat e : openS) {
                            if (e.emplacementHtepien.nom.equals(s[i].etat.emplacementHtepien.nom)) {
                                if (s[i].etat.g >= e.g) {
                                    check1 = true;
                                }
                            }

                        }
                        if (check1 == false) {
                            openS.add(s[i].etat);
                        }
                    }
                }
            }

            if (!openS.isEmpty()) {
                nouvEtat = openS.first();
            }
        }

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        seconds = (double) duration / 1000000000.0;
        noeudExplores = closed.size() + closedS.size();

        return plan;
    }
}