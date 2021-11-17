package com.help.rebate.utils;

import com.help.rebate.vo.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
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

    public static String textMessageToXML(TextMessage textMessage){

            xstream.alias("xml", textMessage.getClass());
            return xstream.toXML(textMessage);
    }

//        String xml = null;
//        if(textMessage.getMsgType().equals("text")) {
//            xml = "<xml>\n" +
//                    "  <ToUserName>" +"![CDATA["+ textMessage.getFromUserName() +"]]"+ "</ToUserName>\n" +
//                    "  <FromUserName>" + "![CDATA["+ textMessage.getToUserName() +"]]" + "</FromUserName>\n" +
//                    "  <CreateTime>" + new Date().getTime() + "</CreateTime>\n" +
//                    "  <MsgType>" + "![CDATA[" + "text"+ "]]" + "</MsgType>\n" +
//                    "  <Content>" + "![CDATA[" + "Hello HI " + textMessage.getContent()+"]]" + "</Content>\n" +
//                    "</xml>";
//        } else {
//            xml = "<xml>\n" +
//                    "  <ToUserName>" +"![CDATA["+ textMessage.getFromUserName() +"]]"+ "</ToUserName>\n" +
//                    "  <FromUserName>" + "![CDATA["+ textMessage.getToUserName() +"]]" + "</FromUserName>\n" +
//                    "  <CreateTime>" + new Date().getTime() + "</CreateTime>\n" +
//                    "  <MsgType>" + "![CDATA[" + "text"+ "]]" + "</MsgType>\n" +
//                    "  <Content>" + "![CDATA[" + "Hello HI " + "非文字消息本君回复不了。" +"]]" + "</Content>\n" +
//                    "</xml>";
//        }
//
//        return xml;

}
