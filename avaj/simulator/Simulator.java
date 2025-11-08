package avaj.simulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


public class Simulator {

    public static void main(String[] args) {
        PrintStream originalOut = System.out;
        PrintStream fileOut = null;

        if (args.length != 1) {
            System.out.println("Error: Invalid number of arguments.");
            return;
        }

        String filename = args[0];

        try {
            fileOut = new PrintStream(new FileOutputStream("simulation.txt"));
            System.setOut(fileOut);

            runSimulation(filename);

        } catch (InvalidScenarioException | InvalidAircraftTypeException e) {
            System.setOut(originalOut);
            System.out.println("Error: " + e.getMessage());
        } catch (IOException e) {
            System.setOut(originalOut);
            System.out.println("Error: Could not read file '" + filename + "'");
            System.out.println("Details: " + e.getMessage());
        }
        catch (Exception e) {
            System.setOut(originalOut);
            System.out.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
            System.setOut(originalOut);
        }
    }


    private static void runSimulation(String filename)
            throws IOException, InvalidScenarioException, InvalidAircraftTypeException {

        SimulationData data = parseScenarioFile(filename);

        WeatherTower weatherTower = new WeatherTower();

        for (Flyable aircraft : data.getAircrafts()) {
            aircraft.registerTower(weatherTower);
        }

        for (int i = 0; i < data.getSimulationCount(); i++) {
            weatherTower.changeWeather();
        }
    }


    private static SimulationData parseScenarioFile(String filename)
            throws IOException, InvalidScenarioException, InvalidAircraftTypeException {

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<Flyable> aircrafts = new ArrayList<>();

        try {
            String firstLine = reader.readLine();
            if (firstLine == null || firstLine.trim().isEmpty()) {
                throw new InvalidScenarioException("File is empty");
            }

            int simulationCount = parseSimulationCount(firstLine);

            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                Flyable aircraft = parseAircraftLine(line, lineNumber);
                aircrafts.add(aircraft);
            }

            if (aircrafts.isEmpty()) {
                throw new InvalidScenarioException("No aircraft defined in scenario");
            }

            return new SimulationData(simulationCount, aircrafts);

        } finally {
            reader.close();
        }
    }


    private static int parseSimulationCount(String line) throws InvalidScenarioException {
        try {
            int count = Integer.parseInt(line.trim());
            if (count <= 0) {
                throw new InvalidScenarioException(
                        "Simulation count must be a positive integer, got: " + count);
            }
            return count;
        } catch (NumberFormatException e) {
            throw new InvalidScenarioException(
                    "Invalid simulation count: '" + line + "' is not a valid integer");
        }
    }


    private static Flyable parseAircraftLine(String line, int lineNumber)
            throws InvalidScenarioException, InvalidAircraftTypeException {

        // Split by whitespace
        String[] parts = line.trim().split("\\s+");

        if (parts.length != 5) {
            throw new InvalidScenarioException(
                    "Line " + lineNumber + ": Invalid format. Expected: TYPE NAME LONGITUDE LATITUDE HEIGHT");
        }

        String type = parts[0];
        String name = parts[1];

        try {
            int longitude = Integer.parseInt(parts[2]);
            int latitude = Integer.parseInt(parts[3]);
            int height = Integer.parseInt(parts[4]);

            if (longitude < 0 || latitude < 0) {
                throw new InvalidScenarioException(
                        "Line " + lineNumber + ": Coordinates must be positive");
            }

            if (height < 0 || height > 100) {
                throw new InvalidScenarioException(
                        "Line " + lineNumber + ": Height must be between 0 and 100");
            }

            Coordinates coordinates = new Coordinates(longitude, latitude, height);

            Flyable aircraft = AircraftFactory.getInstance().newAircraft(type, name, coordinates);

            if (aircraft == null) {
                throw new InvalidAircraftTypeException(type);
            }

            return aircraft;

        } catch (NumberFormatException e) {
            throw new InvalidScenarioException(
                    "Line " + lineNumber + ": Invalid number format in coordinates");
        }
    }

    private static class SimulationData {
       private int simulationCount;
        private List<Flyable> aircrafts;

        SimulationData(int simulationCount, List<Flyable> aircrafts) {
            this.simulationCount = simulationCount;
            this.aircrafts = aircrafts;
        }

        public List<Flyable> getAircrafts() {
            return aircrafts;
        }

        public int getSimulationCount() {
            return simulationCount;
        }
    }
}