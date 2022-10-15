
package com.provider.uws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GenericParam complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GenericParam"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="paramKey" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="paramValue" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenericParam", propOrder = {
    "paramKey",
    "paramValue"
})
public class GenericParam {

    @XmlElement(required = true)
    protected String paramKey;
    @XmlElement(required = true)
    protected String paramValue;

    /**
     * Gets the value of the paramKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamKey() {
        return paramKey;
    }

    /**
     * Sets the value of the paramKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamKey(String value) {
        this.paramKey = value;
    }

    /**
     * Gets the value of the paramValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * Sets the value of the paramValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParamValue(String value) {
        this.paramValue = value;
    }

}
