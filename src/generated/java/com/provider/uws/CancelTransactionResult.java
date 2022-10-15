
package com.provider.uws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CancelTransactionResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CancelTransactionResult"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://uws.provider.com/}GenericResult"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="transactionState" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CancelTransactionResult", propOrder = {
    "transactionState"
})
public class CancelTransactionResult
    extends GenericResult
{

    protected int transactionState;

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

}
