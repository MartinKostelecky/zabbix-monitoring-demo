package cz.martinkostelecky;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        double cpuUsage = getCPUUsage();

        String numericValue = String.format("%.2f", cpuUsage);

        String[] command = {
                "C:\\Program Files\\Zabbix Agent\\zabbix_sender.exe", // enter PATH to the zabbix_sender.exe or add executable in system's PATH
                "-z", "0.0.0.0", // enter Zabbix server IP
                "-p", "10051", // Port
                "-s", "cpu-monitoring", // Hostname
                "-k", "cpu.monitor", // Key
                "-o", numericValue // Value
        };

        while (true) {

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
