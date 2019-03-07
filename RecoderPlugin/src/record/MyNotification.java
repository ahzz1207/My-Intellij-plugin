package record;

import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

public class MyNotification {
    public MyNotification(String message) {
        com.intellij.notification.Notification notification = new Mynotifications("Plugins Suggestion",
                "Project", message, NotificationType.WARNING);
        Notifications.Bus.notify(notification);
    }
}
