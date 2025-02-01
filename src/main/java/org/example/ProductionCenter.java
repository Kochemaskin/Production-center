package org.example;

import java.util.*;

class ProductionCenter {
    Fabric fabric; // Ссылка на Fabric
    String id;
    String name;
    int maxWorkers;
    double performance;
    List<ProductionCenter> sourceCenters;
    List<ProductionCenter> destCenters;
    List<Worker> workers;
    Queue<Detail> buffer;
    private int destCenterIndex = 0;

    public ProductionCenter(Fabric fabric, String id, String name, double performance, int maxWorkers) {
        this.fabric = fabric;
        this.id = id;
        this.name = name;
        this.maxWorkers = maxWorkers;
        this.performance = performance;
        this.sourceCenters = new ArrayList<>();
        this.destCenters = new ArrayList<>();
        this.workers = new ArrayList<>();
        this.buffer = new LinkedList<>();
    }

    public void addWorker(Worker worker) {
        if (workers.size() < maxWorkers) {
            workers.add(worker);
            worker.currentCenter = this;
        }
    }

    public void removeWorker(Worker worker) {
        workers.remove(worker);
        worker.currentCenter = null;
    }

    public void updateDetails(List<String> log) {
        if (workers.isEmpty()) {
            return;
        }

        Iterator<Worker> workerIterator = workers.iterator();

        while (workerIterator.hasNext() ) {
            Worker worker = workerIterator.next();

            if (!worker.busy && !buffer.isEmpty()) {
                Detail detail = buffer.poll();
                detail.setState("PROCESSING");
                detail.startTime = Timer.getTime();
                detail.endTime = detail.startTime + performance;
                worker.assignDetail(detail);
                worker.busy = true;
            }
        }

        for (Worker worker : workers) {
            if (worker.busy && worker.currentDetail != null) {
                Detail detail = worker.currentDetail;
                if (Timer.getTime() >= detail.endTime) {
                    worker.release();
                    worker.busy = false;
                    worker.currentDetail=null;

                    if (!destCenters.isEmpty()) {

                        ProductionCenter nextCenter = destCenters.get(destCenterIndex);
                        detail.setState("BUFFER");
                        detail.startTime = 0;
                        detail.endTime = 0;

                        nextCenter.buffer.add(detail);
                        destCenterIndex = (destCenterIndex + 1) % destCenters.size();
                    } else {

                        detail.setState("COMPLETED");
                        fabric.completedDetails.add(detail);
                    }
                }
            }
        }

    }

    public String getName() {
        return name;
    }

    public double getPerformance() {
        return performance;
    }


}
