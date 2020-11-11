package org.jboss.as.quickstarts.ejb.service.impl;

import org.jboss.as.quickstarts.ejb.service.Food;
import org.jboss.as.quickstarts.ejb.service.Organic;
import org.jboss.as.quickstarts.ejb.qualifier.FrutaQualifier;

import javax.ejb.Stateless;

@Stateless
@FrutaQualifier(kind = FrutaQualifier.Kind.ORANGE)
public class Orange extends Organic /*implements Food*/ {
    @Override
    public String name() {
        return "orange";
    }
}
