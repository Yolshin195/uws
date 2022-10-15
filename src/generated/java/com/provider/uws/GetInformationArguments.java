
package com.provider.uws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetInformationArguments complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetInformationArguments"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://uws.provider.com/}GenericArguments"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="parameters" type="{http://uws.provider.com/}GenericParam" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="serviceId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetInformationArguments", propOrder = {
    "parameters",
    "serviceId"
})
public class GetInformationArguments
    extends GenericArguments
{

    @XmlElement(nillable = true)
    protected List<GenericParam> parameters;
    protected long serviceId;

    /**
     * Gets the value of the parameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GenericParam }
     * 
     * 
     */
    public List<GenericParam> getParameters() {
        if (parameters == null) {
            parameters = new ArrayList<GenericParam>();
        }
        return this.parameters;
    }

    /**
     * Gets the value of the serviceId property.
     * 
     */
    public long getServiceId() {
        return serviceId;
    }

    /**
     * Sets the value of the serviceId property.
     * 
     */
    public void setServiceId(long value) {
        this.serviceId = value;
    }

}
