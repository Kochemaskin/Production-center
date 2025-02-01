package org.example;


import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


class Fabric {
    List<ProductionCenter> centers;
    List<Worker> workers;
    Queue<Detail> initialDetails;
    List<Detail> completedDetails;
    int targetDetailNumber;

    public Fabric(List<ProductionCenter> centers, List<Worker> workers, Queue<Detail> initialDetails) {
        this.centers =  new ArrayList<>(centers);
        this.workers = workers;
        this.initialDetails = initialDetails;
        this.completedDetails = new ArrayList<>();
        this.targetDetailNumber = initialDetails.size();
    }

    public Fabric() {
        this.centers = new ArrayList<>();
        this.workers = new ArrayList<>();
        this.initialDetails = new LinkedList<>();
        this.completedDetails = new ArrayList<>();
        this.targetDetailNumber = 0;
    }

    public void startProduction(String outputPath) {
        ProductionCenter firstCenter = centers.stream()
                .filter(c -> c.sourceCenters.isEmpty())
                .findFirst()
                .orElse(null);

        ProductionCenter lastCenter = centers.stream()
                .filter(c -> c.destCenters.isEmpty())
                .findFirst()
                .orElse(null);


        if (firstCenter == null || lastCenter == null) {
            throw new IllegalStateException("Ошибка: отсутствует начальный или конечный центр производства.");
        }

        List<String> log = new ArrayList<>();
        log.add("Time; ProductionCenter; WorkersCount; BufferCount");

        firstCenter.buffer.addAll(initialDetails);
        while (completedDetails.size() < targetDetailNumber) {
            for (ProductionCenter center : centers) {
                assignWorkersToCenters();
                center.updateDetails(log);
                log.add(String.format("%.1f, %s, %d, %d", Timer.getTime(), center.name, center.workers.size(), center.buffer.size()));
            }

            redistributeEmployees();
            Timer.incrementTime();
            writeLogToFile(log, outputPath);
        }

        writeLogToFile(log, outputPath);
        System.out.println(" Цикл завершен. Итоговое время: " + Timer.getTime());
    }






    public void assignWorkersToCenters() {
        for (Worker worker : workers) {
            if (worker.currentCenter == null && worker.currentDetail == null) {
                for (ProductionCenter center : centers) {
                    if (!center.buffer.isEmpty() && center.workers.size() < center.maxWorkers) {
                        center.addWorker(worker);
                        break;
                    }
                }
            }
        }
    }

    void redistributeEmployees() {
        for (Worker worker : workers) {
            if (!worker.isWorkerBusy()) {
                ProductionCenter bestCenter = null;
                for (ProductionCenter center : centers) {
                    if (!center.buffer.isEmpty() && center.workers.size() < center.maxWorkers) {
                        bestCenter = center;
                        break;
                    }
                }
                if (bestCenter != null) {
                    if (worker.currentCenter != null) {
                        worker.currentCenter.removeWorker(worker);
                    }
                    bestCenter.addWorker(worker);
                }
            }
        }
    }




    private void writeLogToFile(List<String> log, String outputPath) {

        try (FileWriter writer = new FileWriter(outputPath)) {
            for (String line : log) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public void setInitialDetails(Queue<Detail> initialDetails) {
        this.initialDetails = initialDetails;
    }

    public void setCenters(List<ProductionCenter> centers) {
        this.centers =  new ArrayList<>(centers);
    }

    public void setTargetDetailNumber(Queue<Detail> initialDetails) {
        this.targetDetailNumber = initialDetails.size();
    }
}

