package jms;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.NamingException;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.musicmanager.Controls;
import com.musicmanager.FormLogin;
import com.musicmanager.FormMain;
import com.musicmanager.GlobalVarible;

public class Subcribers extends FormMain implements Runnable {
	public static ObjectMessage msg = null;
	static TopicConnection conn = null;
	static TopicSession session = null;
	Topic topic = null;
	Context namingContext = null;
	final String TOPIC_DESTINATION = "java:/jms/topic/myTopic";
	public static TopicSubscriber recv;

	public static void stop() throws JMSException {
		conn.stop();
		session.close();
		conn.close();
	}

	public void receiver() throws Exception, NamingException {
		namingContext = JNDI.getIntialContext();
		TopicConnectionFactory tcf = (TopicConnectionFactory) namingContext.lookup("jms/RemoteConnectionFactory");
		conn = tcf.createTopicConnection("trongtuyen", "P@ssword1999");
		topic = (Topic) namingContext.lookup(TOPIC_DESTINATION);
		session = conn.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
		conn.start();
		recv = session.createSubscriber(topic);
		msg = (ObjectMessage) recv.receive();
		if (msg == null) {
			return;
		} else {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					try {
						String str[] = msg.getObject().toString().split("-");
						if (str[0].equals(String.valueOf(FormLogin.ID_USER))) {
							return;
						} else {
							MESS += "User: " + str[1]+ " " + str[2] + "\r\n";
							GlobalVarible.isEnable = true;
							FormMain.itemNot.setEnabled(true);
							FormMain.itemNot.setText("NOTIFICATION !!!");
						}
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				receiver();
				stop();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}