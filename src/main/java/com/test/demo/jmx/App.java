package com.test.demo.jmx;

import org.alfresco.repo.admin.RepoServerMgmt;
import org.alfresco.repo.admin.RepoServerMgmtMBean;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String HOST = "test";
    public static final String PORT = "50500";

    public static void main(String[] args) throws IOException, MalformedObjectNameException {
        HashMap environment = new HashMap();
        String[]  credentials = new String[] {"controlRole", "change_asap"};
        environment.put (JMXConnector.CREDENTIALS, credentials);
        JMXServiceURL url =
                new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + HOST + ":" + PORT + "/alfresco/jmxrmi");

        JMXConnector jmxConnector = JMXConnectorFactory.connect(url,environment);
        MBeanServerConnection mbeanServerConnection = jmxConnector.getMBeanServerConnection();
        //ObjectName should be same as your MBean name
        ObjectName mbeanName = new ObjectName("Alfresco:Name=RepoServerMgmt");

        //Get MBean proxy instance that will be used to make calls to registered MBean
        RepoServerMgmtMBean mbeanProxy =
                (RepoServerMgmtMBean) MBeanServerInvocationHandler.newProxyInstance(
                        mbeanServerConnection, mbeanName, RepoServerMgmtMBean.class, true);

        //let's make some calls to mbean through proxy and see the results.
        System.out.println("getUserCountNonExpired::" + mbeanProxy.getUserCountNonExpired());
        System.out.println("getUserCountAll :: "+mbeanProxy.getUserCountAll());
        System.out.println("Max Users :: "+mbeanProxy.getMaxUsers());
        System.out.println("getTicketCountNonExpired :: "+mbeanProxy.getTicketCountNonExpired());


        //close the connection
        jmxConnector.close();
    }
}
