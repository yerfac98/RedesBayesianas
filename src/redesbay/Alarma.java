/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redesbay;

import javabayes.Helpers.BayesNetHelper;
import javabayes.InferenceGraphs.InferenceGraph;
import javabayes.InferenceGraphs.InferenceGraphNode;

/**
 *
 * @author Gerardo Fac
 */
public class Alarma {

    InferenceGraph interenceGraph = new InferenceGraph();
    double[] ValRobo = {0.001, 0.999};
    private double[] ValTerre = {0.002, 0.998};
    private double[][] ValAlarma = {{0.95, 0.05}, {0.94, 0.06}, {0.29, 0.71}, {0.001, 0.999}};
    private double[] ValJuan = {0.90, 0.05};
    double[] ValMaria = {0.70, 0.01};

    double resJuan;
    double resMaria, belief;
    InferenceGraphNode casoRobo = BayesNetHelper.createNode(interenceGraph, "robo", " robo", "no robo");
    InferenceGraphNode temblor = BayesNetHelper.createNode(interenceGraph, "Terremoto", "tiembla", "no tiembla");
    InferenceGraphNode Alarma = BayesNetHelper.createNode(interenceGraph, "Alarma", "robo", "terremoto");
    InferenceGraphNode juanllama = BayesNetHelper.createNode(interenceGraph, "JuanLlama", "seActiva", "NoActiva");
    InferenceGraphNode mariaLlama = BayesNetHelper.createNode(interenceGraph, "Maria", "OnAlarma", "OfAlarma");

    String respMaria = "";
    String respJuan = "";

    public Alarma() {

        interenceGraph.create_arc(casoRobo, Alarma);
        interenceGraph.create_arc(temblor, Alarma);
        interenceGraph.create_arc(Alarma, juanllama);
        interenceGraph.create_arc(Alarma, mariaLlama);

        BayesNetHelper.setProbabilityValues(casoRobo, ValRobo[0], ValRobo[1]);
        BayesNetHelper.setProbabilityValues(temblor, ValTerre[0], ValTerre[1]);
        BayesNetHelper.setProbabilityValues(Alarma, "siRobo", "siTerr", ValAlarma[0][0], ValAlarma[0][1]);
        BayesNetHelper.setProbabilityValues(Alarma, "siRob", "noTerr", ValAlarma[1][0], ValAlarma[1][1]);
        BayesNetHelper.setProbabilityValues(Alarma, "noRob", "siTerr", ValAlarma[2][0], ValAlarma[2][1]);
        BayesNetHelper.setProbabilityValues(Alarma, "noRob", "noTerr", ValAlarma[3][0], ValAlarma[3][1]);
        BayesNetHelper.setProbabilityValues(juanllama, "siAlarm", "noAlarm", ValJuan[0], ValJuan[1]);
        BayesNetHelper.setProbabilityValues(mariaLlama, "siAlarm", "noAlarm", ValMaria[0], ValMaria[1]);
    }

    public void siRobo(boolean hayRobo) {
        if (hayRobo) {
            casoRobo.set_observation_value("siRobo");
            resJuan = BayesNetHelper.getBelief(interenceGraph, juanllama);
            resMaria = BayesNetHelper.getBelief(interenceGraph, mariaLlama);
            System.out.println("hay Robo maria LLama: " + (resJuan * 100));

            respMaria += " ";
        } else {
            casoRobo.set_observation_value("noRobo");
            resJuan = BayesNetHelper.getBelief(interenceGraph, juanllama);
            System.out.println("no robo juan: " + (resJuan * 100));
            resMaria = BayesNetHelper.getBelief(interenceGraph, mariaLlama);
            System.out.println("no robo maria: " + (resJuan * 100));

            respMaria += "  ";
        }
    }

    public void Terremoto(boolean Terremoto) {
        if (Terremoto) {
            casoRobo.set_observation_value("siTerremoto");
            resJuan = BayesNetHelper.getBelief(interenceGraph, juanllama);
            System.out.println("terremoto juan: " + (resJuan * 100));
            resMaria = BayesNetHelper.getBelief(interenceGraph, mariaLlama);
            System.out.println("Terremoto maria: " + (resJuan * 100));

            respMaria += " Hay terremoto, ";
        } else {
            casoRobo.set_observation_value("noTerremoto");
            resJuan = BayesNetHelper.getBelief(interenceGraph, Alarma);
            System.out.println("No terremoto juan " + (resJuan * 100));
            resMaria = BayesNetHelper.getBelief(interenceGraph, mariaLlama);
            System.out.println("No terremoto maria: " + (resJuan * 100));

            respMaria += "   ";
        }
    }

    public  double ProbAlarma() {
        resJuan = BayesNetHelper.getBelief(interenceGraph, Alarma);
        System.out.println("La probabilidad de alarma:" + resJuan);
        return resJuan;
    }

    public double obtenerJuan() {
        return resJuan * 100;
    }

    public double ObtenerMaria() {
        return resMaria * 100;
    }

    /**
     * @param datorobo the Valrobo to set
     */
    public void setValrobo(double[] datorobo) {
        this.ValRobo = datorobo;
        BayesNetHelper.setProbabilityValues(casoRobo, ValRobo[0], ValRobo[1]);
    }

    /**
     * @param ValTerre the ValTerre to set
     */
    public void setValTerre(double[] ValTerre) {
        this.ValTerre = ValTerre;
        BayesNetHelper.setProbabilityValues(temblor, ValTerre[0], ValTerre[1]);
    }

    /**
     * @param valAlarma the ValAlarma to set
     */
    public void setValAlarma(double[][] valAlarma) {
        this.ValAlarma = valAlarma;
        BayesNetHelper.setProbabilityValues(Alarma, "siRobo", "siTerr", ValAlarma[0][0], ValAlarma[0][1]);
        BayesNetHelper.setProbabilityValues(Alarma, "siRob", "noTerr", ValAlarma[1][0], ValAlarma[1][1]);
        BayesNetHelper.setProbabilityValues(Alarma, "noRob", "siTerr", ValAlarma[2][0], ValAlarma[2][1]);
        BayesNetHelper.setProbabilityValues(Alarma, "noRob", "noTerr", ValAlarma[3][0], ValAlarma[3][1]);

    }

    /**
     * @param valJuan the ValJuan to set
     */
    public void setValJuan(double[] valJuan) {
        this.ValJuan = valJuan;
        BayesNetHelper.setProbabilityValues(juanllama, "OnAlarm", "OfAlarm", ValJuan[0], ValJuan[1]);

    }

    /**
     * @param valMaria the ValMaria to set
     */
    public void setValMaria(double[] valMaria) {
        this.ValMaria = valMaria;
        BayesNetHelper.setProbabilityValues(mariaLlama, "OnAlarm", "ofAlarm", ValMaria[0], ValMaria[1]);
    }


    public static void main(String[] args) {
        new Alarma();

    }

}
