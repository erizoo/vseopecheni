package ru.vseopecheni.app.data.ResponseModel;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "DailyExRates")
public class DailyExRates {

    @Attribute(name = "Date")
    private String version;

    @ElementList(name = "Currency", inline = true)
    private List<Currency> currency;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Currency> getCurrency() {
        return currency;
    }

    public void setCurrency(List<Currency> currency) {
        this.currency = currency;
    }
}
