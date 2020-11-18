package org.jboss.as.quickstarts.ejb.service.impl;

import org.jboss.as.quickstarts.ejb.service.Organic;

import javax.ejb.Stateless;

@Stateless
@org.jboss.as.quickstarts.ejb.qualifier.Apple
public class Apple extends Organic /*implements Food*/ {
    @Override
    public String name() {
        return "apple";
    }
}
