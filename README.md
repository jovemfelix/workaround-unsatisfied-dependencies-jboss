# Validate EJB called by client considering abstract layer

# Build + Running

Start de JBOSS_EAP with default port 

```shell
# package and deploy
$ mvn -s .m2/settings.xml clean package wildfly:deploy
```

## Test

```shell
$ curl localhost:8080/demo/hello
My fruit is: apple
```

# Problem

At [Orange](src/main/java/org/jboss/as/quickstarts/ejb/service/impl/Orange.java) and [Apple](src/main/java/org/jboss/as/quickstarts/ejb/service/impl/Apple.java) classes comment "duplicate" information of been Food -> remove `implements Food` like this:

```java
package org.jboss.as.quickstarts.ejb.service.impl;

import org.jboss.as.quickstarts.ejb.service.Food;
import org.jboss.as.quickstarts.ejb.service.Organic;
import org.jboss.as.quickstarts.ejb.qualifier.FruitQualifier;

import javax.ejb.Stateless;

@Stateless
@FruitQualifier(kind = FruitQualifier.Kind.APPLE)
public class Apple extends Organic /*implements Food*/ {
    @Override
    public String name() {
        return "apple";
    }
}
```

```java
package org.jboss.as.quickstarts.ejb.service.impl;

import org.jboss.as.quickstarts.ejb.service.Food;
import org.jboss.as.quickstarts.ejb.service.Organic;
import org.jboss.as.quickstarts.ejb.qualifier.FruitQualifier;

import javax.ejb.Stateless;

@Stateless
@FruitQualifier(kind = FruitQualifier.Kind.APPLE)
public class Orange extends Organic /*implements Food*/ {
    @Override
    public String name() {
        return "orange";
    }
}
```

<u>**Don't have**</u> to change the [Organic](src/main/java/org/jboss/as/quickstarts/ejb/service/Organic.java) and [Fruit](src/main/java/org/jboss/as/quickstarts/ejb/service/Fruit.java) classe bellow:

```java
package org.jboss.as.quickstarts.ejb.service;

public abstract class Organic extends Fruit {}
```

```java
package org.jboss.as.quickstarts.ejb.service;

public abstract class Fruit implements Food {}
```

### 

### Now build and deploy.

  You should get this error at server:

  > ```shell
  > Caused by: org.jboss.weld.exceptions.DeploymentException: WELD-001408: Unsatisfied dependencies for type Food with qualifiers @FruitQualifier
  >   at injection point [BackedAnnotatedField] @Inject @FruitQualifier private org.jboss.as.quickstarts.helloworld.HelloWorldServlet.apple
  >   at org.jboss.as.quickstarts.helloworld.HelloWorldServlet.apple(HelloWorldServlet.java:0)
  > ```

  Full log bellow:

  ```verilog
  16:45:07,622 INFO  [org.jboss.as.repository] (management-handler-thread - 1) WFLYDR0001: Content added at location /Users/rfelix/redhat/consultoria/rs-mprs/apps/jboss-eap-7.2/standalone/data/content/43/e042b5b433d1bb502c9ab2aa51e63585985343/content
  16:45:07,625 INFO  [org.wildfly.extension.undertow] (ServerService Thread Pool -- 111) WFLYUT0022: Unregistered web context: '/demo' from server 'default-server'
  16:45:07,650 INFO  [org.jboss.as.server.deployment] (MSC service thread 1-4) WFLYSRV0028: Stopped deployment client-web-0.0.1.war (runtime-name: client-web-0.0.1.war) in 25ms
  16:45:07,653 INFO  [org.jboss.as.server.deployment] (MSC service thread 1-1) WFLYSRV0027: Starting deployment of "client-web-0.0.1.war" (runtime-name: "client-web-0.0.1.war")
  16:45:07,744 INFO  [org.jboss.weld.deployer] (MSC service thread 1-7) WFLYWELD0003: Processing weld deployment client-web-0.0.1.war
  16:45:07,776 INFO  [io.jaegertracing.internal.JaegerTracer] (MSC service thread 1-7) No shutdown hook registered: Please call close() manually on application shutdown.
  16:45:07,776 INFO  [org.jboss.as.ejb3.deployment] (MSC service thread 1-7) WFLYEJB0473: JNDI bindings for session bean named 'Apple' in deployment unit 'deployment "client-web-0.0.1.war"' are as follows:
  
  	java:global/client-web-0.0.1/Apple!org.jboss.as.quickstarts.ejb.service.impl.Apple
  	java:app/client-web-0.0.1/Apple!org.jboss.as.quickstarts.ejb.service.impl.Apple
  	java:module/Apple!org.jboss.as.quickstarts.ejb.service.impl.Apple
  	ejb:/client-web-0.0.1/Apple!org.jboss.as.quickstarts.ejb.service.impl.Apple
  	java:global/client-web-0.0.1/Apple
  	java:app/client-web-0.0.1/Apple
  	java:module/Apple
  
  16:45:07,776 INFO  [org.jboss.as.ejb3.deployment] (MSC service thread 1-7) WFLYEJB0473: JNDI bindings for session bean named 'Orange' in deployment unit 'deployment "client-web-0.0.1.war"' are as follows:
  
  	java:global/client-web-0.0.1/Orange!org.jboss.as.quickstarts.ejb.service.impl.Orange
  	java:app/client-web-0.0.1/Orange!org.jboss.as.quickstarts.ejb.service.impl.Orange
  	java:module/Orange!org.jboss.as.quickstarts.ejb.service.impl.Orange
  	ejb:/client-web-0.0.1/Orange!org.jboss.as.quickstarts.ejb.service.impl.Orange
  	java:global/client-web-0.0.1/Orange
  	java:app/client-web-0.0.1/Orange
  	java:module/Orange
  
  16:45:07,934 ERROR [org.jboss.msc.service.fail] (MSC service thread 1-4) MSC000001: Failed to start service jboss.deployment.unit."client-web-0.0.1.war".WeldStartService: org.jboss.msc.service.StartException in service jboss.deployment.unit."client-web-0.0.1.war".WeldStartService: Failed to start service
  	at org.jboss.msc.service.ServiceControllerImpl$StartTask.execute(ServiceControllerImpl.java:1731)
  	at org.jboss.msc.service.ServiceControllerImpl$ControllerTask.run(ServiceControllerImpl.java:1559)
  	at org.jboss.threads.ContextClassLoaderSavingRunnable.run(ContextClassLoaderSavingRunnable.java:35)
  	at org.jboss.threads.EnhancedQueueExecutor.safeRun(EnhancedQueueExecutor.java:1982)
  	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.doRunTask(EnhancedQueueExecutor.java:1486)
  	at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1377)
  	at java.lang.Thread.run(Thread.java:748)
  Caused by: org.jboss.weld.exceptions.DeploymentException: WELD-001408: Unsatisfied dependencies for type Food with qualifiers @FruitQualifier
    at injection point [BackedAnnotatedField] @Inject @FruitQualifier private org.jboss.as.quickstarts.helloworld.HelloWorldServlet.apple
    at org.jboss.as.quickstarts.helloworld.HelloWorldServlet.apple(HelloWorldServlet.java:0)
  
  	at org.jboss.weld.bootstrap.Validator.validateInjectionPointForDeploymentProblems(Validator.java:378)
  	at org.jboss.weld.bootstrap.Validator.validateInjectionPoint(Validator.java:290)
  	at org.jboss.weld.bootstrap.Validator.validateGeneralBean(Validator.java:143)
  	at org.jboss.weld.bootstrap.Validator.validateRIBean(Validator.java:164)
  	at org.jboss.weld.bootstrap.Validator.validateBean(Validator.java:526)
  	at org.jboss.weld.bootstrap.ConcurrentValidator$1.doWork(ConcurrentValidator.java:64)
  	at org.jboss.weld.bootstrap.ConcurrentValidator$1.doWork(ConcurrentValidator.java:62)
  	at org.jboss.weld.executor.IterativeWorkerTaskFactory$1.call(IterativeWorkerTaskFactory.java:62)
  	at org.jboss.weld.executor.IterativeWorkerTaskFactory$1.call(IterativeWorkerTaskFactory.java:55)
  	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
  	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
  	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
  	at java.lang.Thread.run(Thread.java:748)
  	at org.jboss.threads.JBossThread.run(JBossThread.java:485)
  
  16:45:07,936 ERROR [org.jboss.as.controller.management-operation] (management-handler-thread - 1) WFLYCTL0013: Operation ("full-replace-deployment") failed - address: ([]) - failure description: {"WFLYCTL0080: Failed services" => {"jboss.deployment.unit.\"client-web-0.0.1.war\".WeldStartService" => "Failed to start service
      Caused by: org.jboss.weld.exceptions.DeploymentException: WELD-001408: Unsatisfied dependencies for type Food with qualifiers @FruitQualifier
    at injection point [BackedAnnotatedField] @Inject @FruitQualifier private org.jboss.as.quickstarts.helloworld.HelloWorldServlet.apple
    at org.jboss.as.quickstarts.helloworld.HelloWorldServlet.apple(HelloWorldServlet.java:0)
  "}}
  16:45:07,948 INFO  [org.jboss.as.server.deployment] (MSC service thread 1-2) WFLYSRV0028: Stopped deployment client-web-0.0.1.war (runtime-name: client-web-0.0.1.war) in 11ms
  16:45:07,948 ERROR [org.jboss.as.server] (management-handler-thread - 1) WFLYSRV0014: Replacement of deployment "client-web-0.0.1.war" by deployment "client-web-0.0.1.war" was rolled back with the following failure message:
  {"WFLYCTL0080: Failed services" => {"jboss.deployment.unit.\"client-web-0.0.1.war\".WeldStartService" => "Failed to start service
      Caused by: org.jboss.weld.exceptions.DeploymentException: WELD-001408: Unsatisfied dependencies for type Food with qualifiers @FruitQualifier
    at injection point [BackedAnnotatedField] @Inject @FruitQualifier private org.jboss.as.quickstarts.helloworld.HelloWorldServlet.apple
    at org.jboss.as.quickstarts.helloworld.HelloWorldServlet.apple(HelloWorldServlet.java:0)
  "}}
  16:45:07,949 INFO  [org.jboss.as.server.deployment] (MSC service thread 1-2) WFLYSRV0027: Starting deployment of "client-web-0.0.1.war" (runtime-name: "client-web-0.0.1.war")
  16:45:08,013 INFO  [org.jboss.weld.deployer] (MSC service thread 1-4) WFLYWELD0003: Processing weld deployment client-web-0.0.1.war
  16:45:08,035 INFO  [io.jaegertracing.internal.JaegerTracer] (MSC service thread 1-4) No shutdown hook registered: Please call close() manually on application shutdown.
  16:45:08,035 INFO  [org.jboss.as.ejb3.deployment] (MSC service thread 1-4) WFLYEJB0473: JNDI bindings for session bean named 'Apple' in deployment unit 'deployment "client-web-0.0.1.war"' are as follows:
  
  	java:global/client-web-0.0.1/Apple!org.jboss.as.quickstarts.ejb.service.Food
  	java:app/client-web-0.0.1/Apple!org.jboss.as.quickstarts.ejb.service.Food
  	java:module/Apple!org.jboss.as.quickstarts.ejb.service.Food
  	ejb:/client-web-0.0.1/Apple!org.jboss.as.quickstarts.ejb.service.Food
  	java:global/client-web-0.0.1/Apple
  	java:app/client-web-0.0.1/Apple
  	java:module/Apple
  
  16:45:08,036 INFO  [org.jboss.as.ejb3.deployment] (MSC service thread 1-4) WFLYEJB0473: JNDI bindings for session bean named 'Orange' in deployment unit 'deployment "client-web-0.0.1.war"' are as follows:
  
  	java:global/client-web-0.0.1/Orange!org.jboss.as.quickstarts.ejb.service.Food
  	java:app/client-web-0.0.1/Orange!org.jboss.as.quickstarts.ejb.service.Food
  	java:module/Orange!org.jboss.as.quickstarts.ejb.service.Food
  	ejb:/client-web-0.0.1/Orange!org.jboss.as.quickstarts.ejb.service.Food
  	java:global/client-web-0.0.1/Orange
  	java:app/client-web-0.0.1/Orange
  	java:module/Orange
  
  16:45:08,186 INFO  [org.wildfly.extension.undertow] (ServerService Thread Pool -- 114) WFLYUT0021: Registered web context: '/demo' for server 'default-server'
  16:45:08,187 INFO  [org.jboss.as.repository] (management-handler-thread - 1) WFLYDR0002: Content removed from location /Users/rfelix/redhat/consultoria/rs-mprs/apps/jboss-eap-7.2/standalone/data/content/43/e042b5b433d1bb502c9ab2aa51e63585985343/content
  ```

