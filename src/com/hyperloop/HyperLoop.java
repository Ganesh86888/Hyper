package com.hyperloop;
import com.hyperloop.PassengerTransportDetails;
import java.util.*;
public class HyperLoop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PassengerTransportDetails transportSystem = null;

        while (scanner.hasNextLine()) {
            String[] command = scanner.nextLine().split(" ");

            switch (command[0]) {
                case "INIT":
                    transportSystem = new PassengerTransportDetails(command[2]);
                    int numRoutes = Integer.parseInt(command[1]);
                    for (int i = 0; i < numRoutes; i++) {
                        String[] routeDetails = scanner.nextLine().split(" ");
                        transportSystem.addRoute(routeDetails[0], routeDetails[1], Integer.parseInt(routeDetails[2]));
                    }
                    break;

                case "ADD_PASSENGER":
                    int numPassengers = Integer.parseInt(command[1]);
                    for (int i = 0; i < numPassengers; i++) {
                        String[] passengerDetails = scanner.nextLine().split(" ");
                        String name = passengerDetails[0];
                        int age;
                        String destination = passengerDetails[2];
                        try {
                            age = Integer.parseInt(passengerDetails[1]);
                            if (age <= 0) {
                                System.out.println("Error: Age must be a positive integer.");
                                continue;
                            }
                        } catch (NumberFormatException ex) {
                            System.out.println("Error: Invalid age format. Please enter a positive integer.");
                            continue;
                        }
                        transportSystem.addPassenger(name, age, destination);
                    }
                    break;

                case "START_POD":
                    int numToStart = Integer.parseInt(command[1]);
                    try{
                        if(numToStart>0){
                            transportSystem.startPod(numToStart);
                            break;
                        }
                    }catch(ArrayIndexOutOfBoundsException e) {
                        System.out.println("ERROR: " + e);

                    }
                case "PRINT_Q":
                    transportSystem.printQueue();
                    break;
            }
        }
    }
}
