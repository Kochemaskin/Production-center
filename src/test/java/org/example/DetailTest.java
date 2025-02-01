package org.example;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DetailTest {

    @Test
    void testInitialState() {
        Detail detail = new Detail(1);
        assertEquals("BUFFER", detail.currentState, "Начальное состояние должно быть 'BUFFER'");
        assertEquals(0, detail.progress, "Прогресс должен начинаться с 0");
        assertEquals(Double.MAX_VALUE, detail.endTime, "Время завершения должно быть MAX_VALUE");
    }

    @Test
    void testStateChange() {
        Detail detail = new Detail(1);
        detail.setState("PROCESSING");
        assertEquals("PROCESSING", detail.currentState, "Состояние должно быть 'PROCESSING'");
    }

    @Test
    void testCompletion() {
        Detail detail = new Detail(1);
        detail.setState("COMPLETED");
        assertTrue(detail.isCompleted(), "Деталь должна быть завершена");
    }
}