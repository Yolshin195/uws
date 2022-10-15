
package com.provider.uws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CheckTransactionResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CheckTransactionResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://uws.provider.com/}GenericResult"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="providerTrnId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="transactionState" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="transactionStateErrorStatus" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="transactionStateErrorMsg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CheckTransactionResult", propOrder = {
    "providerTrnId",
    "transactionState",
    "transactionStateErrorStatus",
    "transactionStateErrorMsg"
})
public class CheckTransactionResult
    extends GenericResult
{

    protected long providerTrnId;
    protected int transactionState;
    protected int transactionStateErrorStatus;
    @XmlElement(required = true)
    protected String transactionStateErrorMsg;

    /**
     * Gets the value of the providerTrnId property.
     * 
     */
    public long getProviderTrnId() {
        return providerTrnId;
    }

    /**
     * Sets the value of the providerTrnId property.
     * 
     */
    public void setProviderTrnId(long value) {
        this.providerTrnId = value;
    }

    /**
     * Gets the value of the transactionState property.
     * 
     */
    public int getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     */
    public void setTransactionState(int value) {
        this.transactionState = value;
    }

    /**
     * Gets the value of the transactionStateErrorStatus property.
     * 
     */
    public int getTransactionStateErrorStatus() {
        return transactionStateErrorStatus;
    }

    /**
     * Sets the value of the transactionStateErrorStatus property.
     * 
     */
    public void setTransactionStateErrorStatus(int value) {
        this.transactionStateErrorStatus = value;
    }

    /**
     * Gets the value of the transactionStateErrorMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionStateErrorMsg() {
        return transactionStateErrorMsg;
    }

    /**
     * Sets the value of the transactionStateErrorMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionStateErrorMsg(String value) {
        this.transactionStateErrorMsg = value;
    }

}
