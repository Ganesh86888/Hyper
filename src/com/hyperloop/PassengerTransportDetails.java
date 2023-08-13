package com.hyperloop;

import java.util.*;

class PassengerTransportDetails {
    String startingStation;
    Map<String, StationDetails> stationMap;
    Queue<PassengerDetails> passengerQueue;
    private Set<String> validStations;

    public PassengerTransportDetails(String startingStation) {
        this.startingStation = startingStation;
        this.stationMap = new HashMap<>();
        this.passengerQueue = new LinkedList<>();
        this.validStations = new HashSet<>();
        validStations.add(startingStation);
    }

    public void addRoute(String from, String to, int distance) {
        stationMap.computeIfAbsent(from, StationDetails::new).addConnection(new RouteDetails(from, to, distance));
        stationMap.computeIfAbsent(to, StationDetails::new).addConnection(new RouteDetails(to, from, distance));
        validStations.add(from);
        validStations.add(to);
    }

    public boolean isValidStation(String station) {
        return validStations.contains(station);
    }

    public void addPassenger(String name, int age, String destination) {
        if(!isValidStation(destination)){
            System.out.println("Error: Invalid destination station.");
            return;
        }
        else {
            passengerQueue.add(new PassengerDetails(name, age, destination));
        }
    }

    public void startPod(int numPassengers) {
        PriorityQueue<PassengerDetails> priorityQueue = new PriorityQueue<>(Comparator.comparing(PassengerDetails::getAge).reversed());
        priorityQueue.addAll(passengerQueue);
        if(priorityQueue.isEmpty()){
            System.out.println("The queue is empty, unable to start the pod");
        }
        else {
            for (int i = 0; i < numPassengers && !priorityQueue.isEmpty(); i++) {
                PassengerDetails passenger = priorityQueue.poll();
                String route = calculateRoute(passenger.destination);
                System.out.println(passenger.name + " " + route);
                //once the passenger is boarded to the pod, automatically removed that passenger from the queue
                passengerQueue.remove(passenger);
            }
        }
    }

    public void printQueue() {
        int queueSize = passengerQueue.size();
        if(passengerQueue.isEmpty()){
            System.out.println("Queue is empty");
        }
        else {
            System.out.println(queueSize);
            for (PassengerDetails passenger : passengerQueue) {
                System.out.println(passenger.name + " " + passenger.age);
            }
        }
    }

    //This is the method used to calculate the shortest path to the destination by dijikstar's algorithm
    private String calculateRoute(String destination) {
        Map<String, Integer> distanceMap = new HashMap<>();
        Map<String, String> previousStationMap = new HashMap<>();
        PriorityQueue<String> minHeap = new PriorityQueue<>(Comparator.comparing(distanceMap::get));

        distanceMap.put(startingStation, 0);
        minHeap.add(startingStation);

        while (!minHeap.isEmpty()) {
            String currentStation = minHeap.poll();
            if (currentStation.equals(destination)) {
                break;
            }

            for (RouteDetails route : stationMap.get(currentStation).connections) {
                String nextStation = route.to;
                int newDistance = distanceMap.get(currentStation) + route.distance;

                if (!distanceMap.containsKey(nextStation) || newDistance < distanceMap.get(nextStation)) {
                    distanceMap.put(nextStation, newDistance);
                    previousStationMap.put(nextStation, currentStation);
                    minHeap.add(nextStation);
                }
            }
        }

        List<String> routeStations = new ArrayList<>();
        String station = destination;
        while (station != null) {
            routeStations.add(station);
            station = previousStationMap.get(station);
        }
        Collections.reverse(routeStations);

        return String.join(" ", routeStations);
    }
}