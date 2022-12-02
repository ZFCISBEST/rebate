package com.help.rebate.vo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeChatImageMessage extends WeChartMessageBase{

    private String MediaId;


    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getMediaId() {
        return MediaId;
    }

}