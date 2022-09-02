package com.cqupt.logistic.bean;

public class Route {

    private  String action;
    private  String duration;
    private  String assistant_action;
    private  String distance;
    private  String instruction;
    private  String toll_road;
    private  String road;

    public Route() {
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAssistant_action() {
        return assistant_action;
    }

    public void setAssistant_action(String assistant_action) {
        this.assistant_action = assistant_action;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getToll_road() {
        return toll_road;
    }

    public void setToll_road(String toll_road) {
        this.toll_road = toll_road;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    @Override
    public String toString() {
        return "Route{" +
                "action='" + action + '\'' +
                ", duration='" + duration + '\'' +
                ", assistant_action='" + assistant_action + '\'' +
                ", distance='" + distance + '\'' +
                ", instruction='" + instruction + '\'' +
                ", toll_road='" + toll_road + '\'' +
                ", road='" + road + '\'' +
                '}';
    }
}
