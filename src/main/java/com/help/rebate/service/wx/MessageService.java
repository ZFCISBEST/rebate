package com.help.rebate.service.wx;

import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface MessageService {
    String newMessageRequest(HttpServletRequest request) throws IOException, SAXException, ParserConfigurationException;

}
