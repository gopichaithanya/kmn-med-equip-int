package id.ac.idu.webui.administrasi.matakuliah;

import id.ac.idu.webui.util.GFCBaseCtrl;
import org.zkoss.lang.Strings;
import org.zkoss.zk.au.out.AuClearWrongValue;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MatakuliahMainCtrl extends GFCBaseCtrl implements Serializable {

    Window windowMatakuliahMain;
    Borderlayout borderLayout_matakuliahList;
    Borderlayout borderLayout_matakuliahDetail;
    Listbox personslb;
    Textbox nametb;
    Listbox titlelb;

    AnnotateDataBinder binder;

    List<Person> model = new ArrayList<Person>();
    List<String> titleModel = new ArrayList<String>();
    Person selected;

    public MatakuliahMainCtrl() {
    }

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        this.self.setAttribute("controller", this, false);

        model.add(new Person("Brian", "Engineer"));
        model.add(new Person("John", "Tester"));
        model.add(new Person("Sala", "Manager"));
        model.add(new Person("Peter", "Architect"));

        titleModel.add("");
        titleModel.add("Engineer");
        titleModel.add("Tester");
        titleModel.add("Manager");
        titleModel.add("Architect");

        binder = new AnnotateDataBinder(comp);
        binder.loadAll();
    }

    public List getModel() {
        return model;
    }

    public List getTitleModel() {
        return titleModel;
    }

    public Person getSelected() {
        return selected;
    }

    public void setSelected(Person selected) {
        this.selected = selected;
    }

    public void onCreate$windowMatakuliahMain() {
        doFitSize();
    }

    public void doFitSize() {
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        borderLayout_matakuliahList.setHeight(String.valueOf(maxListBoxHeight - 300) + "px");
        borderLayout_matakuliahDetail.setHeight(String.valueOf(200) + "px");
        windowMatakuliahMain.invalidate();
    }

    public void onClick$add() {
        Person person = new Person(getSelectedName(), getSelectedTitle());
        model.add(person);
        selected = person;
        binder.loadAll();
    }

    public void onClick$update() {
        if (selected == null) {
            alert("Nothing selected");
            return;
        }
        selected.setName(getSelectedName());
        selected.setTitle(getSelectedTitle());
        binder.loadAll();
    }

    public void onClick$delete() {
        if (selected == null) {
            alert("Nothing selected");
            return;
        }
        model.remove(selected);
        selected = null;

        binder.loadAll();
    }

    public void onSelect$titlelb() {
        closeErrorBox(new Component[]{titlelb});
    }

    public void onSelect$personslb() {
        closeErrorBox(new Component[]{nametb, titlelb});
    }


    private void closeErrorBox(Component[] comps) {
        for (Component comp : comps) {
            Executions.getCurrent().addAuResponse(null, new AuClearWrongValue(comp));
        }
    }

    private String getSelectedTitle() throws WrongValueException {
        int index = titlelb.getSelectedIndex();
        if (index < 1) {
            throw new WrongValueException(titlelb, "Must selecte one!");
        }

        return titleModel.get(index);
    }

    private String getSelectedName() throws WrongValueException {
        String name = nametb.getValue();
        if (Strings.isBlank(name)) {
            throw new WrongValueException(nametb, "Must not blank!");
        }
        return name;
    }

    public class Person {
        public String name;
        public String title;

        public Person(String name, String title) {
            this.name = name;
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}
