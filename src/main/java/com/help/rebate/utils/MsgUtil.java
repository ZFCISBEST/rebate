package com.help.rebate.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.help.rebate.vo.WeChartTextMessage;
import com.help.rebate.vo.WeChatImageMessage;
import com.help.rebate.web.controller.wx.SignatureController;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.http.HttpException;
import org.apache.logging.log4j.core.net.Protocol;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.codec.multipart.FilePart;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsgUtil {

    private final static Logger logger = LoggerFactory.getLogger(MsgUtil.class);

    /**
     * 对象到xml的处理
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();

        InputStream ins = null;

        try {
            ins = request.getInputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Document doc = null;
        try {
            doc = reader.read(ins);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();

            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }

            return map;
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }finally{
            ins.close();
        }

        return null;
    }

    public static Map<String, String> xmlToMap(String xmlContent) throws IOException {

        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();

        InputStream ins = new ByteArrayInputStream(xmlContent.getBytes());

        Document doc = null;
        try {
            doc = reader.read(ins);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();

            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }

            return map;
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }finally{
            ins.close();
        }

        return null;
    }

    public static String textMessageToXML(WeChartTextMessage textMessage){

            xstream.alias("xml", textMessage.getClass());
            return xstream.toXML(textMessage);
    }

    public static String imageMessageToXML(WeChatImageMessage imageMessage){

        xstream.alias("xml", imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }


    public static String getDownloadUrl(String token, String mediaId) {
        return String.format(SignatureController.DOWNLOAD_MEDIA, token, mediaId);
    }

    public static File downloadMedia(String fileName, String token, String mediaId) {
        String path = getDownloadUrl(token, mediaId);
        //return httpRequestToFile(fileName, url, "GET", null);
        if (fileName == null || path == null) {
            return null;
        }
        File file = null;
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        FileOutputStream fileOut = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");

            inputStream = conn.getInputStream();
            if (inputStream != null) {
                file = new File(fileName);
            } else {
                return file;
            }

            //写入到文件
            fileOut = new FileOutputStream(file);
            if (fileOut != null) {
                int c = inputStream.read();
                while (c != -1) {
                    fileOut.write(c);
                    c = inputStream.read();
                }
            }
        } catch (Exception e) {
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

            try {
                inputStream.close();
                fileOut.close();
            } catch (IOException execption) {
            }
        }
        return file;
    }

//    @WebServlet(name = "UploadMediaServlet")
//    public class UploadMediaServlet extends HttpServlet {
//        @Override
//        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        }
//
//        @Override
//        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//            UploadMediaApiUtil uploadMediaApiUtil = new UploadMediaApiUtil();
//            String appId = "wx8948d77934e934e0";
//            String appSecret = "d4babf8b4bc341167b283e21e129e073";
//            String accessToken = uploadMediaApiUtil.getAccessToken(appId,appSecret);
//
//            String filePath = "D:\\img\\1.jpg";
//            File file = new File(filePath);
//            String type = "IMAGE";
//            JSONObject jsonObject = uploadMediaApiUtil.uploadMedia(file,accessToken,type);
//            System.out.println(jsonObject.toString());
//        }
//    }


    public static void main(String[] args) throws IOException {
        String content = "<xml>\n" +
                "<return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "<return_msg><![CDATA[发放成功]]></return_msg>\n" +
                "<result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "<err_code><![CDATA[SUCCESS]]></err_code>\n" +
                "<err_code_des><![CDATA[发放成功]]></err_code_des>\n" +
                "<mch_billno><![CDATA[16610964586166018]]></mch_billno>\n" +
                "<mch_id><![CDATA[1617446954]]></mch_id>\n" +
                "<wxappid><![CDATA[wx9f4ab53be3e5e226]]></wxappid>\n" +
                "<re_openid><![CDATA[odgJP5w-s6pRav3pIcIeB1urmqX8]]></re_openid>\n" +
                "<total_amount>200</total_amount>\n" +
                "<send_listid><![CDATA[1000041701202208213033416589419]]></send_listid>\n" +
                "</xml>";

        Map<String, String> result = xmlToMap(content);
        System.out.println(JSON.toJSONString(result, true));
    }
}
