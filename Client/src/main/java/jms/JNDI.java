package jms;

import java.util.Properties;

import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDI {
	final static String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
	final static String PROVIDER_URL = "http-remoting://127.0.0.1:8080";
	Context namingContext = null;
	JMSContext context = null;
	public static Context getIntialContext() throws NamingException {
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
		env.put(Context.PROVIDER_URL,PROVIDER_URL);
        env.put(Context.SECURITY_PRINCIPAL, "trongtuyen");     // username
        env.put(Context.SECURITY_CREDENTIALS, "P@ssword1999");   // password
		Context context = new InitialContext(env);
		return context;
	}
}
