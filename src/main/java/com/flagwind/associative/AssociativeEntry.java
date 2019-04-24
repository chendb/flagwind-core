package com.flagwind.associative;

import java.util.Arrays;

import com.flagwind.associative.annotation.Associative;
import com.flagwind.associative.annotation.Associative.TriggerType;
import com.flagwind.lang.ExtensibleObject;

import org.apache.commons.lang3.StringUtils;

public class AssociativeEntry {

    private String name;
    private String provider;
    private String extras;
    private TriggerType tigger = TriggerType.Json;

    public AssociativeEntry(String name, String source, String extras) {
        this.name = name;
        this.provider = source;
        this.extras = extras;
    }

    public AssociativeEntry(String name, String provider) {
        this.name = name;
        this.provider = provider;
    }

    public AssociativeEntry(Associative associative) {
        this.name = associative.name();
        this.provider = associative.provider();
        this.extras = associative.extras();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    /**
     * @return the tigger
     */
    public TriggerType getTigger() {
        return tigger;
    }

    /**
     * @param tigger the tigger to set
     */
    public void setTigger(TriggerType tigger) {
        this.tigger = tigger;
    }

    public AssociativeProvider getAssociativeProvider(){
        return AssociativeProviderFactory.instance().resolve(this.provider);
    }

    public Object getAssociateValue(Object value) {
        AssociativeProvider provider = AssociativeProviderFactory.instance().resolve(this.provider);
        if (StringUtils.isEmpty(extras)) {
            return provider.associate(value);
        } else {
            return provider.associate(Arrays.asList(value, this.extras).toArray());
        }
    }

    public void execute(ExtensibleObject extensibleObject, Object value) {
        extensibleObject.set(this.name, this.getAssociateValue(value));
    }
}
