package jms;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.NamingException;

public class Publisher {
	TopicConnection conn = null;
	TopicSession session = null;
	Topic topic = null;
	Context namingContext = null;
	final String TOPIC_DESTINATION = "java:/jms/topic/myTopic";
	public void setupPubSub() throws JMSException, NamingException, InterruptedException {
		namingContext = JNDI.getIntialContext();
		TopicConnectionFactory tcf = (TopicConnectionFactory) namingContext.lookup("jms/RemoteConnectionFactory");
		conn = tcf.createTopicConnection("trongtuyen","P@ssword1999");
		topic = (Topic) namingContext.lookup(TOPIC_DESTINATION);
		session = conn.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
		conn.start();
	}

	public void sendAsync(String text) throws JMSException, NamingException, InterruptedException {
		System.out.println("Begin sendAsync");
		setupPubSub();
		TopicPublisher send = session.createPublisher(topic);
		ObjectMessage mesText=session.createObjectMessage(text);
		send.publish(mesText);		
		System.out.println("sendAsync, sent text=" + mesText);
		send.close();
		System.out.println("End sendAsync");
	}

	public void stop() throws JMSException {
		conn.stop();
		session.close();
		conn.close();
	}
	public static void sender(String text) throws JMSException, NamingException, InterruptedException {
		System.out.println("Begin TopicSendClient, now=" + System.currentTimeMillis());
		Publisher client = new Publisher();
		client.sendAsync(text);
		client.stop();
		System.out.println("End TopicSendClient");
		
	}
	public static void main(String args[]) throws Exception {
		sender("there is a new song just added");
	}

}