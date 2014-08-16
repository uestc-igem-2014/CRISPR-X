package com.example.crispr_x;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Dict implements Serializable {

	private String key;
	private String value;
	
	public Dict() {
    }

    public Dict(String key, String value) {
        super();
        this.key = key;
        this.value = value;
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

    @Override
    public String toString() {
        return value;
    }
    
}
