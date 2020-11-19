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
public class Infarto {

    String imprimir = "";
    InferenceGraph interenceGraph = new InferenceGraph();
    InferenceGraphNode deporte = BayesNetHelper.createNode(interenceGraph, "Deporte", "siHaceDeporte", "noHaceDeporte");
    InferenceGraphNode Alimentacion = BayesNetHelper.createNode(interenceGraph, "Alimentación", "Equilibrada", "NoEquilibrada");
    InferenceGraphNode PresSanguinea = BayesNetHelper.createNode(interenceGraph, "presionSg", "alta", "normal");
    InferenceGraphNode Fumador = BayesNetHelper.createNode(interenceGraph, "fumador", "fum", "noFum");
    InferenceGraphNode Infarto = BayesNetHelper.createNode(interenceGraph, "infarto", "siInfar", "noInfar");
    private double belief;
    private double[] ValDeporte = {0.1, 0.9};
    private double[] ValAlimentacion = {0.4, 0.6};
    private double[] ValFumador = {0.4, 0.6};
    private double[][] ValPresion = {{0.01, 0.99}, {0.2, 0.8}, {0.25, 0.75}, {0.7, 0.3}};
    private double[][] ValInfarto = {{0.8, 0.2}, {0.6, 0.4}, {0.7, 0.3}, {0.3, 0.7}};

    public Infarto() {
        
        interenceGraph.create_arc(Alimentacion, PresSanguinea);
        interenceGraph.create_arc(deporte, PresSanguinea);
        interenceGraph.create_arc(PresSanguinea, Infarto);
        interenceGraph.create_arc(Fumador, Infarto);
        MostrarDatos();
    }

    //metodos para establecer los 
    public void realizaDeporte(boolean realizaDeporte) {
        if (realizaDeporte) {
            deporte.set_observation_value("siDeporte");
            belief = BayesNetHelper.getBelief(interenceGraph, Infarto);
            imprimir += "Hace deporte, ";
        } else {
            deporte.set_observation_value("noDeporte");
            belief = BayesNetHelper.getBelief(interenceGraph, Infarto);
            imprimir += "No hace deporte, ";
        }
    }

    public void AlimentacionEQ_NOEQ(boolean AlimentacionEq) {
        if (AlimentacionEq) {
            Alimentacion.set_observation_value("Equilibrada");
            belief = BayesNetHelper.getBelief(interenceGraph, Infarto);
            System.out.println("La probabilidad de infarto con alimentacion equilibrada: " + belief);
            imprimir += "Su alimentación es equilibrada, ";
        } else {
            Alimentacion.set_observation_value("noEquilibrada");
            belief = BayesNetHelper.getBelief(interenceGraph, Infarto);
            System.out.println("Su alimentación no es equilibrada: " + belief);
            imprimir += "No tiene alimentacion equilibrada, ";
        }
    }

    public void Fumador(boolean fuma) {
        if (fuma) {
            Fumador.set_observation_value("Fuma");
            belief = BayesNetHelper.getBelief(interenceGraph, Infarto);
            System.out.println("La probabilidad de infarto y fume : " + belief);
            imprimir += "Fuma, ";
        } else {
            Fumador.set_observation_value("noFuma");
            belief = BayesNetHelper.getBelief(interenceGraph, Infarto);
            System.out.println("La probabilidad de infarto y fume : " + belief);
            imprimir += "No Fuma, ";
        }
    }

    public void PresionAlta_Normal(boolean P_alta) {
        if (P_alta) {
            Infarto.set_observation_value("Presion Alta");
            belief = BayesNetHelper.getBelief(interenceGraph, Infarto);
            imprimir += "Su presion sanguinea es Alta, ";
        } else {
            Infarto.set_observation_value("Presion normal");
            belief = BayesNetHelper.getBelief(interenceGraph, Infarto);
            imprimir += "Su presion sanguinea es normal, ";
        }
    }

    public String Imprimir() {
        //System.out.println(imprimir);
        return imprimir;
    }

    public void MostrarDatos() {
        setValDeporte(ValDeporte);
        setValAlimentacion(ValAlimentacion);
        setValFumador(ValFumador);
        setValPresion(ValPresion);
        setValInfarto(ValInfarto);
    }

    //datos de las variables asignadas
    /**
     * @param ValDeporte the ValDeporte to set
     */
    public void setValDeporte(double[] ValDeporte) {
        this.ValDeporte = ValDeporte;

        BayesNetHelper.setProbabilityValues(deporte, ValDeporte[0], ValDeporte[1]);
    }

    /**
     * @param ValAlimentacion the ValAlimentacion to set
     */
    public void setValAlimentacion(double[] ValAlimentacion) {
        this.ValAlimentacion = ValAlimentacion;
        BayesNetHelper.setProbabilityValues(Alimentacion, ValAlimentacion[0], ValAlimentacion[1]);
    }

    /**
     * @param ValFumador the ValFumador to set
     */
    public void setValFumador(double[] ValFumador) {
        this.ValFumador = ValFumador;
        BayesNetHelper.setProbabilityValues(Fumador, ValFumador[0], ValFumador[1]);
    }

    /**
     * @param ValPresion the ValPresion to set
     */
    public void setValPresion(double[][] ValPresion) {
        this.ValPresion = ValPresion;
        BayesNetHelper.setProbabilityValues(PresSanguinea, "equilibrada", "siDeporte", ValPresion[0][0], ValPresion[0][1]);
        BayesNetHelper.setProbabilityValues(PresSanguinea, "noEquilibrada", "siDeporte", ValPresion[1][0], ValPresion[1][1]);
        BayesNetHelper.setProbabilityValues(PresSanguinea, "equilibrada", "noDeporte", ValPresion[2][0], ValPresion[2][1]);
        BayesNetHelper.setProbabilityValues(PresSanguinea, "noEquilibrada", "noDeporte", ValPresion[3][0], ValPresion[3][1]);

    }

    /**
     * @param ValInfarto the ValInfarto to set
     */
    public void setValInfarto(double[][] ValInfarto) {
        this.ValInfarto = ValInfarto;
        BayesNetHelper.setProbabilityValues(Infarto, "Alta", "Fuma", ValInfarto[0][0], ValInfarto[0][1]);
        BayesNetHelper.setProbabilityValues(Infarto, "Normal", "Fuma", ValInfarto[1][0], ValInfarto[1][1]);
        BayesNetHelper.setProbabilityValues(Infarto, "Alta", "noFuma", ValInfarto[2][0], ValInfarto[2][1]);
        BayesNetHelper.setProbabilityValues(Infarto, "Normal", "Fuma", ValInfarto[3][0], ValInfarto[3][1]);

    }

    public double Belief() {
        return (belief) * 100;
    }

    public static void main(String[] args) {
        new Infarto();
    }
}
