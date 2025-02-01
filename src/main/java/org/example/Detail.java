package org.example;

class Detail {
    int id;
    String currentState; // "BUFFER", "PROCESSING", "COMPLETED"
    double progress;
    double endTime;
    double startTime;

    public Detail(int id) {
        this.id = id;
        this.currentState = "BUFFER";
        this.progress = 0;
        this.endTime = Double.MAX_VALUE;
        this.startTime = 0.0;
    }

    public void setState(String state) {
        this.currentState = state;
    }

    public boolean isCompleted() {
        return "COMPLETED".equals(this.currentState);
    }
}