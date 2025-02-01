package org.example;

import java.io.*;

public class JupyterRunner {
    public static void main(String[] args) {
        try {

            String notebookPath = "PlotScript.ipynb";

            // Команда для выполнения Jupyter Notebook
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "jupyter", "nbconvert", "--to", "notebook", "--execute", notebookPath
            );


            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();


            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }


            int exitCode = process.waitFor();
            System.out.println("Jupyter Notebook выполнен, код завершения: " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}