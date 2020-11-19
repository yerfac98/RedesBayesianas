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
public class RedesBay {

    public void RedBayesiana() {
        InferenceGraph interenceGraph = new InferenceGraph();
        InferenceGraphNode age = BayesNetHelper.createNode(interenceGraph, "under55", "<55", ">55");
        InferenceGraphNode smoker = BayesNetHelper.createNode(interenceGraph, "smoker", "smokes", "doesnotsmoke");
        InferenceGraphNode duration = BayesNetHelper.createNode(interenceGraph, "duration", "<2Y", ">2Y");
        InferenceGraphNode surgical = BayesNetHelper.createNode(interenceGraph, "surgicalOutcome", "positive", "negative");

        interenceGraph.create_arc(age, smoker);
        interenceGraph.create_arc(smoker, surgical);
        interenceGraph.create_arc(duration, surgical);

        BayesNetHelper.setProbabilityValues(smoker, "<55", 0.4, 0.6);
        BayesNetHelper.setProbabilityValues(smoker, ">55", 0.8, 0.2);
        BayesNetHelper.setProbabilityValues(surgical, "smokes", "<2Y", 0.1, 0.9);
        BayesNetHelper.setProbabilityValues(surgical, "smokes", ">2Y", 0.01, 0.99);
        BayesNetHelper.setProbabilityValues(surgical, "doesnotsmoke", "<2Y", 0.8, 0.2);
        BayesNetHelper.setProbabilityValues(surgical, "doesnotsmoke", ">2Y", 0.58, 0.42);
        BayesNetHelper.setProbabilityValues(duration, 0.9, 0.1);
        BayesNetHelper.setProbabilityValues(age, 0.8, 0.2);

        double belief = BayesNetHelper.getBelief(interenceGraph, surgical);
        System.out.println("La probabilidad de cirugía es positiva: " + belief);
        age.set_observation_value("<55");
        belief = BayesNetHelper.getBelief(interenceGraph, surgical);
        System.out.println("La probabilidad de que la cirugía se positiva y el paciente es menor de 55 años: " + belief);

        smoker.set_observation_value("smokes");
        belief = BayesNetHelper.getBelief(interenceGraph, surgical);
        System.out.println("La probabilidad de que la cirugía sea positiva para un fumador, menor de 55 años: " + belief);

        duration.set_observation_value(">2Y");
        belief = BayesNetHelper.getBelief(interenceGraph, surgical);
        System.out.println("La probabilidad de que la cirugía sea positiva para un fumador, menor de 55 años con síntomas durante 2 años: " + belief);

    }

    public static void main(String[] args) {
        RedesBay rb = new RedesBay();
        rb.RedBayesiana();
    }
}
