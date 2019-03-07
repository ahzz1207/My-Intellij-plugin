package record;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import org.jetbrains.annotations.NotNull;

public class Mynotifications extends Notification {
    public Mynotifications(@NotNull String groupDisplayId, @NotNull String title, @NotNull String content, @NotNull NotificationType type) {
        super(groupDisplayId, title, content, type);
    }

}
