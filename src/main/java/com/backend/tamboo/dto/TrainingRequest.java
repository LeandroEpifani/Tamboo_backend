package com.backend.tamboo.dto;

public class TrainingRequest {

    private Integer id;
    private String name; // Campo aggiunto
    private String timeSignature;
    private String bpm;
    private String beat;

    // Costruttori
    public TrainingRequest() {
    }

    public TrainingRequest(Integer id, String name, String timeSignature, String bpm, String beat) {
        this.id = id;
        this.name = name;
        this.timeSignature = timeSignature;
        this.bpm = bpm;
        this.beat = beat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(String timeSignature) {
        this.timeSignature = timeSignature;
    }

    public String getBpm() {
        return bpm;
    }

    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    public String getBeat() {
        return beat;
    }

    public void setBeat(String beat) {
        this.beat = beat;
    }
}
