package com.huev.iot.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class IOTDeviceSimulator {
    private List<Device> devices;
    private API api;

    public IOTDeviceSimulator(API api) {
        this.api = api;
        this.devices = new ArrayList<>();
    }

    public void addDevice(Device device) {
        devices.add(device);
    }

    public void startSimulation() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for (Device device : devices) {
                    device.updateStatus();
                    api.sendData(device.getDeviceId(), device.getStatus());
                }
            }
        }, 0, 1000); // update every 1 second
    }

    public interface API {
        void sendData(String deviceId, String status);
    }

    public static class Device {
        private String deviceId;
        private String status;

        public Device(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public String getStatus() {
            return status;
        }

        public void updateStatus() {
            // simulate device status update logic here
            this.status = "ON" + Math.random();
        }
    }
}