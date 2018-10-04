package ru.vseopecheni.app.data.ResponseModel;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Currency")
public class Currency {

    @Attribute(name = "Id")
    private String id;

    @Element(name = "NumCode")
    private String numCode;

    @Element(name = "CharCode")
    private String charCode;

    @Element(name = "Scale")
    private String scale;

    @Element(name = "Name")
    private String name;

    @Element(name = "Rate")
    private String rate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
