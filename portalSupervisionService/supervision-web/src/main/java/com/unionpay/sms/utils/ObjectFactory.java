
package com.unionpay.sms.utils;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.unionpay.portal.meets.smsMessageService package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SendResponseOut1_QNAME = new QName("http://www.primeton.com/SMSMessageServiceService", "out1");
    private final static QName _WMResultErrorMsg_QNAME = new QName("http://message.web.oa.unionpay.com", "errorMsg");
    private final static QName _WMResultMsg_QNAME = new QName("http://message.web.oa.unionpay.com", "msg");
    private final static QName _SendIn0_QNAME = new QName("http://www.primeton.com/SMSMessageServiceService", "in0");
    private final static QName _SendIn1_QNAME = new QName("http://www.primeton.com/SMSMessageServiceService", "in1");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.unionpay.portal.meets.smsMessageService
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendResponse }
     * 
     */
    public SendResponse createSendResponse() {
        return new SendResponse();
    }

    /**
     * Create an instance of {@link WMResult }
     * 
     */
    public WMResult createWMResult() {
        return new WMResult();
    }

    /**
     * Create an instance of {@link Send }
     * 
     */
    public Send createSend() {
        return new Send();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WMResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.primeton.com/SMSMessageServiceService", name = "out1", scope = SendResponse.class)
    public JAXBElement<WMResult> createSendResponseOut1(WMResult value) {
        return new JAXBElement<WMResult>(_SendResponseOut1_QNAME, WMResult.class, SendResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.web.oa.unionpay.com", name = "errorMsg", scope = WMResult.class)
    public JAXBElement<String> createWMResultErrorMsg(String value) {
        return new JAXBElement<String>(_WMResultErrorMsg_QNAME, String.class, WMResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.web.oa.unionpay.com", name = "msg", scope = WMResult.class)
    public JAXBElement<String> createWMResultMsg(String value) {
        return new JAXBElement<String>(_WMResultMsg_QNAME, String.class, WMResult.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.primeton.com/SMSMessageServiceService", name = "in0", scope = Send.class)
    public JAXBElement<String> createSendIn0(String value) {
        return new JAXBElement<String>(_SendIn0_QNAME, String.class, Send.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfString }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.primeton.com/SMSMessageServiceService", name = "in1", scope = Send.class)
    public JAXBElement<ArrayOfString> createSendIn1(ArrayOfString value) {
        return new JAXBElement<ArrayOfString>(_SendIn1_QNAME, ArrayOfString.class, Send.class, value);
    }

}
