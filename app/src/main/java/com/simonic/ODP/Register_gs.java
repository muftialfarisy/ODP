package com.simonic.ODP;

public class Register_gs {
    public String getDevice_id() {
        return device_id;
    }
    public Register_gs(String device_id,String uuid){
        this.device_id = device_id;
        this.uuid = uuid;
    }
    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    String device_id,uuid;
}
