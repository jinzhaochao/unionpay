
package com.unionpay.sms.utils;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="out1" type="{http://message.web.oa.unionpay.com}WMResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "out1"
})
@XmlRootElement(name = "sendResponse")
public class SendResponse {

    @XmlElementRef(name = "out1", namespace = "http://www.primeton.com/SMSMessageServiceService", type = JAXBElement.class)
    protected JAXBElement<WMResult> out1;

    /**
     * ��ȡout1���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link WMResult }{@code >}
     *     
     */
    public JAXBElement<WMResult> getOut1() {
        return out1;
    }

    /**
     * ����out1���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link WMResult }{@code >}
     *     
     */
    public void setOut1(JAXBElement<WMResult> value) {
        this.out1 = value;
    }

}
