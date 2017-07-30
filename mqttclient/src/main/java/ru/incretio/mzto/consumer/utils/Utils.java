package ru.incretio.mzto.consumer.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Utils {
    public static String getMacAddress() throws UnknownHostException, SocketException {
        byte[] mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
        StringBuilder macAddress = new StringBuilder();
        for (byte value : mac) {
            macAddress.append(String.format("%02X", value));
        }
        return macAddress.toString();
    }
}
