package com.ningzhengao.nzgweb;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class TestServlet {
    public static void main(String[] args) {
        try {
            Connector connector = new Connector();
            connector.setPort(18888);
            connector.setSecure(true);

            connector.setURIEncoding("UTF-8");
            //Http protocol Http11AprProtocol
            connector.setAttribute("protocol", "org.apache.coyote.http11.Http11AprProtocol");

            //Maximum threads allowedd on this instance of tomcat
            connector.setAttribute("maxThreads","300");
            connector.setAttribute("minThreads","200");
            connector.setAttribute("SSLEnabled", true);

            //No client Authentification is required in order to connect
            connector.setAttribute("clientAuth", false);

            //SSL TLSv1 protocol
            connector.setAttribute("sslProtocol","TLS");

            //Ciphers configuration describing how server will encrypt his messages
            //A common cipher suite need to exist between server and client in an ssl
            //communication in order for the handshake to succeed
            connector.setAttribute("ciphers","TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA");

            //Here an absolute file path is needed in order to properly set up the keystore attribute
            connector.setAttribute("keystoreFile","d:/test.keystore");

            connector.setAttribute("keystorePass","testtest");

            Tomcat tomcat=new Tomcat();
//            tomcat.setPort(18888);
//            Connector defaultConnector = tomcat.getConnector();
//            defaultConnector.setRedirectPort(18888);
            Service service=tomcat.getService();
            service.addConnector(connector);

            Context context = tomcat.addContext("", null);
            tomcat.addServlet("","test",new MyServlet());
            context.addServletMappingDecoded("/*", "test");

            tomcat.start();
            tomcat.stop();
            tomcat.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    static class MyServlet extends HttpServlet{
        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            System.out.print("11111");
        }
    }
}
