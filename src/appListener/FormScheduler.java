package appListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import actionForListener.ReviewReminder;
import actionForListener.SubmitForm;

/**
 * Application Lifecycle Listener implementation class FormScheduler
 *
 */
public class FormScheduler implements ServletContextListener {
	private ScheduledExecutorService scheduler;
    /**
     * Default constructor. 
     */
    public FormScheduler() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    	scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new SubmitForm(), 0, 1, TimeUnit.DAYS);
        scheduler.scheduleAtFixedRate(new ReviewReminder(), 0, 1, TimeUnit.DAYS);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    	scheduler.shutdownNow();
    }
	
}
