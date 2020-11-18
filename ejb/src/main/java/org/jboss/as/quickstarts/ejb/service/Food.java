package org.jboss.as.quickstarts.ejb.service;

import javax.ejb.Stateless;

@Stateless
public interface Food {
    String name();
}
