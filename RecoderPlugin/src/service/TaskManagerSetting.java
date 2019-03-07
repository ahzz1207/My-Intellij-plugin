package service;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;
import org.jetbrains.annotations.Nullable;

/**
 * TaskManagerSetting
 *
 * 对插件提供持久化服务的组件
 *
 * */
@State(
        name = "TaskManagerSetting",
        storages = {
        @Storage(
                file = "$APP_CONFIG$/mytaskmanager.xml")
        }
)
public class TaskManagerSetting implements PersistentStateComponent<Element> {

    private String pd = "/";
    private String ld = "/";

    public TaskManagerSetting() {
    }

    public static TaskManagerSetting getInstance() {
        return (TaskManagerSetting) ServiceManager.getService(TaskManagerSetting.class);
    }

    @Nullable
    public Element getState() {
        Element element = new Element("TaskManagerSetting");
        element.setAttribute("pd", this.getPD());
        element.setAttribute("ld", this.getLD());
        return element;
    }

    @Override
    public void loadState(Element state) {
        this.setPD(state.getAttributeValue("pd"));
        this.setLD(state.getAttributeValue("ld"));
    }

    public void setPD(String pd) {
        this.pd = pd;
    }

    public String getPD() {
        return this.pd;
    }

    public void setLD(String ld) {
        this.ld = ld;
    }

    public String getLD() {
        return this.ld;
    }

}
