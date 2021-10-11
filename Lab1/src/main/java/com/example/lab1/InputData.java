package com.example.lab1;

public class InputData {
    private String key;

    private int value;

    private String mock;

    private String sync;

    public InputData(String key, int value, String mock, String sync) {
        this.key = key;
        this.value = value;
        this.mock = mock;
        this.sync = sync;
    }

    public InputData() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMock() {
        return mock;
    }

    public void setMock(String mock) {
        this.mock = mock;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }
}
