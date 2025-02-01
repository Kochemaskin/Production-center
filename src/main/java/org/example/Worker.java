package org.example;


class Worker {
    int id;
    boolean busy;
    Detail currentDetail;
    ProductionCenter currentCenter;

    public Worker(int id) {
        this.id = id;
        this.busy = false;
        this.currentDetail = null;
        this.currentCenter = null;
    }

    public void assignDetail(Detail detail) {
        this.currentDetail = detail;
        this.busy = true;
    }




    public void release() {
        this.currentDetail = null;
        this.busy = false;
    }

    public boolean isWorkerBusy() {
        return busy && currentDetail != null ;
    }

}