package org.example.noten.service;

import org.example.noten.model.Nota;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NotaKalkulagailua {

    /**
     * Calcula la nota media ponderada de cada evaluación.
     * Si hay 2 trabajos con peso 40 y 60, la nota es la media ponderada.
     * Si tienen el mismo peso, es simplemente la media.
     */
    public static Map<String, Double> kalkulatuEbaluazioNotak(List<Nota> notak) {
        // Agrupamos por ikasgaia + evaluación
        Map<String, List<Nota>> agrupadas = notak.stream().collect(
                Collectors.groupingBy(
                        n -> n.getEbaluazioZatia().getIkasgaia().getId() + "-" +
                                n.getEbaluazioZatia().getEbaluazioZenbakia()
                )
        );

        return agrupadas.entrySet().stream().collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        e -> {
                            List<Nota> notakTalde = e.getValue();
                            double sumaPisuak = notakTalde.stream()
                                    .mapToDouble(n -> n.getEbaluazioZatia().getPisua())
                                    .sum();
                            double sumaPonderatua = notakTalde.stream()
                                    .mapToDouble(n -> n.getNotaZenbakia() * n.getEbaluazioZatia().getPisua())
                                    .sum();
                            // Media ponderada: suma(nota * peso) / suma(pesos)
                            return biribildu(sumaPonderatua / sumaPisuak);
                        }
                )
        );
    }

    /**
     * Calcula la nota final de una asignatura (media de las evaluaciones).
     */
    public static Map<Integer, Double> kalkulatuNotaFinalak(Map<String, Double> ebaluazioNotak) {
        Map<Integer, List<Double>> porIkasgaia = ebaluazioNotak.entrySet().stream()
                .collect(Collectors.groupingBy(
                        e -> Integer.parseInt(e.getKey().split("-")[0]),
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

        return porIkasgaia.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> biribildu(e.getValue().stream()
                                .mapToDouble(Double::doubleValue)
                                .average()
                                .orElse(0.0))
                ));
    }

    public static double biribildu(double nota) {
        return Math.round(nota * 100.0) / 100.0;
    }
}