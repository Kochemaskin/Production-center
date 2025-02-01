package org.example;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) {
        String filePath = "тестовый сценарий №1 6 сотрудников.xlsx";
        List<Worker> workers = new ArrayList<>();
        Queue<Detail> initialDetails = new LinkedList<>();
        Fabric fabric = new Fabric();
        try {
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);

            int workersCount = parseScenario(workbook, "Scenario", 0);
            int detailsCount = parseScenario(workbook, "Scenario", 1);

            for (int i = 1; i <= workersCount; i++) {
                workers.add(new Worker(i));
            }

            for (int i = 1; i <= detailsCount; i++) {
                initialDetails.add(new Detail(i));
            }

            fabric.setWorkers(workers);
            fabric.setInitialDetails(initialDetails);
            fabric.setTargetDetailNumber(initialDetails);

            Map<String, ProductionCenter> productionCenters = parseProductionCenters(fabric, workbook, "ProductionCenter");
            connectCenters(workbook, "Connection", productionCenters);

            List<ProductionCenter> sortedCenters = productionCenters.values().stream()
                    .sorted(Comparator.comparingInt(Main::extractNumber))
                    .collect(Collectors.toList());
            sortedCenters.forEach(center -> System.out.println("Center: " + center.getName() + " , performance: " + center.getPerformance()));



            fabric.setCenters(sortedCenters);
            fabric.startProduction("output.csv");

            workbook.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


        private static int parseScenario (Workbook workbook, String sheetName,int cellIndex){
            Sheet sheet = workbook.getSheet(sheetName);
            Row row = Objects.requireNonNull(sheet, "Sheet " + sheetName + " not found").getRow(2);
            Cell cell = Objects.requireNonNull(row, "Row 3 missing in sheet " + sheetName).getCell(cellIndex);
            return parseCellToInt(cell, "Scenario sheet column " + (cellIndex + 1));
        }

    private static Map<String, ProductionCenter> parseProductionCenters(Fabric fabric, Workbook workbook, String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        Objects.requireNonNull(sheet, "Sheet " + sheetName + " not found");

        Map<String, ProductionCenter> centers = new HashMap<>();
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                String id = getCellValue(row.getCell(0));
                String name = getCellValue(row.getCell(1));
                double performance = Double.parseDouble(getCellValue(row.getCell(2)));
                int maxWorkers = Integer.parseInt(getCellValue(row.getCell(3)));
                centers.put(id, new ProductionCenter(fabric, id, name, performance, maxWorkers));
            }
        }
        return centers;
    }


    private static void connectCenters(Workbook workbook, String sheetName, Map<String, ProductionCenter> centers) {
        Sheet sheet = workbook.getSheet(sheetName);
        Objects.requireNonNull(sheet, "Sheet " + sheetName + " not found");

        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                String sourceId = getCellValue(row.getCell(0));
                String destId = getCellValue(row.getCell(1));
                ProductionCenter source = centers.get(sourceId);
                ProductionCenter destination = centers.get(destId);

                if (source != null && destination != null) {
                    source.destCenters.add(destination);
                }
            }
        }
    }

        private static int parseCellToInt (Cell cell, String context){
            try {
                return Integer.parseInt(getCellValue(cell));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid data in " + context);
            }
        }

        private static String getCellValue (Cell cell){
            if (cell == null) return "";
            return switch (cell.getCellType()) {
                case STRING -> cell.getStringCellValue().trim();
                case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
                default -> "";
            };
        }

    private static int extractNumber(ProductionCenter center) {
        return center.getName().replaceAll("[^0-9]", "").isEmpty()
                ? Integer.MAX_VALUE
                : Integer.parseInt(center.getName().replaceAll("[^0-9]", ""));
    }
    }

