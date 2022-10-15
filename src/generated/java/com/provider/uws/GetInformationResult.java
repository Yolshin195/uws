
package com.provider.uws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetInformationResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetInformationResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://uws.provider.com/}GenericResult"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="parameters" type="{http://uws.provider.com/}GenericParam" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetInformationResult", propOrder = {
    "parameters"
})
public class GetInformationResult
    extends GenericResult
{

    @XmlElement(nillable = true)
    protected List<GenericParam> parameters;

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

}
