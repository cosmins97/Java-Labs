package com.example.lab2;

public class Record {
    private String category;
    private String key;
    private String value;

    public Record(String category, String key, String value) {
        this.setCategory(category);
        this.setKey(key);
        this.setValue(value);
    }

    public Record() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
