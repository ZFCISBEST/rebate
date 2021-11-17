package com.help.rebate.service.wx;

/**
 * @DESCRIPTION
 * @Author lst
 * @Date 2020-08-18
 */
public interface WxCheckSignatureService {

    String checkSignature( String signature, String timestamp,
                           String nonce,String echostr);

}