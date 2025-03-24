package cz.martinkostelecky;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        while (true) {

        double cpuUsage = getCPUUsage();

        String numericValue = Double.toString(cpuUsage);

        String[] command = {
                "/home/<user>/bin/zabbix_sender", // enter PATH to the zabbix_sender binary
                "-z", "0.0.0.0", // enter Zabbix server IP
                "-p", "10051", // Port
                "-s", "monitoring-cpu-java-app", // Hostname
                "-k", "cpu.usage", // Key
                "-o", numericValue // Value
        };

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                System.out.println(command[10]);
            }
            reader.close();

            Thread.sleep(5000);
        }
    }

    private static double getCPUUsage() {

        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor cpu = hal.getProcessor();
        double[] cpuLoad = cpu.getSystemLoadAverage(1);
        return cpuLoad[0];
    }
}
