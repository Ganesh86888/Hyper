package com.hyperloop;
import com.hyperloop.RouteDetails;
import java.util.*;
import java.util.ArrayList;


class StationDetails {
    String name;
    List<RouteDetails> connections;

    public StationDetails(String name) {
        this.name = name;
        this.connections = new ArrayList<>();
    }

    public void addConnection(RouteDetails route) {
        connections.add(route);
    }
}
