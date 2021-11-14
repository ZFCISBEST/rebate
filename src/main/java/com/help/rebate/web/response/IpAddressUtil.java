package com.help.rebate.web.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

/**
 * 获取IP地址
 * @author zfcisbest
 * @date 21/11/14
 */
public class IpAddressUtil {
    private static final Logger logger = LoggerFactory.getLogger(IpAddressUtil.class);

    /**
     * ip地址和mac地址
     */
    private static String hostAddress = null;
    private static String macAddress = null;

    static {
        try {
            refreshAddress();

            logger.info("[IpAddressUtil] current ip address:{}", hostAddress);
            logger.info("[IpAddressUtil] current mac address:{}", macAddress);
        } catch (Throwable e) {
            new RuntimeException("fail to resolve local address", e).printStackTrace();
        }
    }

    /**
     * 获取地址
     */
    public static void refreshAddress() throws SocketException {
        List<InetAddress> candidateInetAddresses = new ArrayList<InetAddress>();
        Map<String, String> candidateMacAddressMap = new HashMap<>(8, 1);

        //遍历网卡，查找一个非回路的ip地址并记录
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (!inetAddress.isLoopbackAddress() && !(inetAddress instanceof Inet6Address)) {
                    //找到一个A类IP地址
                    String firstTriplet = inetAddress.getHostAddress().substring(0, inetAddress.getHostAddress().indexOf('.'));

                    if (Integer.parseInt(firstTriplet) < 128) {
                        candidateInetAddresses.add(inetAddress);
                    }

                    //尝试获取Mac地址
                    NetworkInterface byInetAddress = NetworkInterface.getByInetAddress(inetAddress);
                    byte[] hardwareAddress = byInetAddress.getHardwareAddress();
                    if (hardwareAddress != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < hardwareAddress.length; i++) {
                            sb.append(String.format("%02X%s", hardwareAddress[i], (i < hardwareAddress.length - 1) ? "-" : ""));
                        }

                        candidateMacAddressMap.put(inetAddress.getHostAddress(), sb.toString());
                    }
                }
            }
        }

        //筛选IP
        if (!candidateInetAddresses.isEmpty()) {
            hostAddress = candidateInetAddresses.get(0).getHostAddress();
        }

        //筛选Mac地址
        if (!candidateMacAddressMap.isEmpty()) {
            macAddress = candidateMacAddressMap.values().iterator().next();
        }
    }

    public static String getHostAddress() {
        return hostAddress;
    }

    public static String getMacAddress() {
        return macAddress;
    }
}
