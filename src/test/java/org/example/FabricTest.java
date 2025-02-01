package org.example;



import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FabricTest {

    @Test
    void testFabricInitialization() {
        List<ProductionCenter> centers = new ArrayList<>();
        List<Worker> workers = Arrays.asList(new Worker(1), new Worker(2));
        Queue<Detail> details = new LinkedList<>(Arrays.asList(new Detail(1), new Detail(2)));

        Fabric fabric = new Fabric(centers, workers, details);

        assertEquals(2, fabric.workers.size(), "Должно быть 2 работника");
        assertEquals(2, fabric.initialDetails.size(), "Должно быть 2 детали в буфере");
    }


    @Test
    void testProductionCycle() {
        Fabric fabric = new Fabric();
        Detail detail = new Detail(1);
        Worker worker = new Worker(1);
        ProductionCenter center = new ProductionCenter(fabric, "1", "Center1", 1.0, 1);

        fabric.centers.add(center);
        fabric.workers.add(worker);
        center.buffer.add(detail);

        fabric.assignWorkersToCenters();
        center.updateDetails(new ArrayList<>());

        assertEquals(1, center.workers.size(), "Один рабочий должен быть назначен");
        assertEquals("PROCESSING", detail.currentState, "Деталь должна быть в обработке");
    }
}
