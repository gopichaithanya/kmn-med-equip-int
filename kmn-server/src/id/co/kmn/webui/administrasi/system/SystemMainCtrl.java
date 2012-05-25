package id.co.kmn.webui.administrasi.system;

import com.trg.search.Filter;
import id.co.kmn.UserWorkspace;
import id.co.kmn.administrasi.service.SystemService;
import id.co.kmn.backend.model.Mmedsystem;
import id.co.kmn.backend.util.HibernateSearchObject;
import id.co.kmn.backend.util.ZksampleBeanUtils;
import id.co.kmn.util.Codec;
import id.co.kmn.util.ConstantUtil;
import id.co.kmn.webui.administrasi.report.MpegawaiSimpleDJReport;
import id.co.kmn.webui.util.*;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.*;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/10/12
 * Time: 7:27 AM
 */
public class SystemMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(SystemMainCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowSystemMain; // autowired

    // Tabs
    protected Tabbox tabbox_SystemMain; // autowired
    protected Tab tabSystemList; // autowired
    protected Tab tabSystemDetail; // autowired
    protected Tabpanel tabPanelSystemList; // autowired
    protected Tabpanel tabPanelSystemDetail; // autowired

    // filter components
    protected Checkbox checkbox_SystemList_ShowAll; // autowired
    protected Textbox txtb_System_Code; // aurowired
    protected Button button_SystemList_SearchCode; // aurowired
    protected Textbox txtb_System_Name; // aurowired
    protected Button button_SystemList_SearchName; // aurowired
    protected Textbox txtb_System_Alamat; // aurowired
    protected Button button_SystemList_SearchAlamat; // aurowired

    // checkRights
    protected Button button_SystemList_PrintList;

    // Button controller for the CRUD buttons
    private final String btnCtroller_ClassPrefix = "button_SystemMain_";
    private ButtonStatusCtrl btnCtrlSystem;
    protected Button btnNew; // autowired
    protected Button btnEdit; // autowired
    protected Button btnDelete; // autowired
    protected Button btnSave; // autowired
    protected Button btnCancel; // autowired

    protected Button btnHelp;

    // Tab-Controllers for getting the binders
    private SystemListCtrl systemListCtrl;
    private SystemDetailCtrl systemDetailCtrl;

    // Databinding
    private Mmedsystem selectedSystem;
    private BindingListModelList systems;

    // ServiceDAOs / Domain Classes
    private SystemService systemService;

    // always a copy from the bean before modifying. Used for reseting
    private Mmedsystem originalSystem;

    /**
     * default constructor.<br>
     */
    public SystemMainCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        /**
         * 1. Set an 'alias' for this composer name to access it in the
         * zul-file.<br>
         * 2. Set the parameter 'recurse' to 'false' to avoid problems with
         * managing more than one zul-file in one page. Otherwise it would be
         * overridden and can ends in curious error messages.
         */
        this.self.setAttribute("controller", this, false);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++ Component Events ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * Automatically called method from zk.
     *
     * @param event
     * @throws Exception
     */
    public void onCreate$windowSystemMain(Event event) throws Exception {
        windowSystemMain.setContentStyle("padding:0px;");

        // create the Button Controller. Disable not used buttons during working
        btnCtrlSystem = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, btnSave, btnCancel);

        //doCheckRights();

        /**
         * Initiate the first loading by selecting the customerList tab and
         * create the components from the zul-file.
         */
        tabSystemList.setSelected(true);

        if (tabPanelSystemList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSystemList, this, "ModuleMainController", "/WEB-INF/pages/administrasi/system/systemList.zul");
        }

        // init the buttons for editMode
        btnCtrlSystem.setInitEdit();
    }

    /**
     * When the tab 'tabSystemList' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws java.io.IOException
     */
    public void onSelect$tabSystemList(Event event) throws IOException {
        logger.debug(event.toString());

        // Check if the tabpanel is already loaded
        if (tabPanelSystemList.getFirstChild() != null) {
            tabSystemList.setSelected(true);

            return;
        }

        if (tabPanelSystemList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSystemList, this, "ModuleMainController", "/WEB-INF/pages/administrasi/system/systemList.zul");
        }

    }

    /**
     * When the tab 'tabPanelSystemDetail' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws IOException
     */
    public void onSelect$tabSystemDetail(Event event) throws IOException {
        // logger.debug(event.toString());

        // Check if the tabpanel is already loaded
        if (tabPanelSystemDetail.getFirstChild() != null) {
            tabSystemDetail.setSelected(true);

            // refresh the Binding mechanism
            getSystemDetailCtrl().setSystem(getSelectedSystem());
            getSystemDetailCtrl().getBinder().loadAll();
            //refresh combo
            getSystemDetailCtrl().doRenderCombo();
            return;
        }

        if (tabPanelSystemDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelSystemDetail, this, "ModuleMainController", "/WEB-INF/pages/administrasi/system/systemDetail.zul");
        }
    }

    /**
     * when the "print systems list" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$button_SystemList_PrintList(Event event) throws InterruptedException {
        final Window win = (Window) Path.getComponent("/outerIndexWindow");
        new MpegawaiSimpleDJReport(win);
    }

    /**
     * when the checkBox 'Show All' for filtering is checked. <br>
     *
     * @param event
     */
    public void onCheck$checkbox_SystemList_ShowAll(Event event) {
        // logger.debug(event.toString());

        // empty the text search boxes
        txtb_System_Code.setValue(""); // clear
        txtb_System_Name.setValue(""); // clear
        //txtb_System_Alamat.setValue(""); // clear

        // ++ create the searchObject and init sorting ++//
        HibernateSearchObject<Mmedsystem> soSystem = new HibernateSearchObject<Mmedsystem>(Mmedsystem.class, getSystemListCtrl().getCountRows());
        soSystem.addSort(ConstantUtil.TYPE_NAME, false);

        // Change the BindingListModel.
        if (getSystemListCtrl().getBinder() != null) {
            getSystemListCtrl().getPagedBindingListWrapper().setSearchObject(soSystem);

            // get the current Tab for later checking if we must change it
            Tab currentTab = tabbox_SystemMain.getSelectedTab();

            // check if the tab is one of the Detail tabs. If so do not
            // change the selection of it
            if (!currentTab.equals(tabSystemList)) {
                tabSystemList.setSelected(true);
            } else {
                currentTab.setSelected(true);
            }
        }

    }

    /**
     * Filter the system list with 'like system number'. <br>
     */
    public void onClick$button_SystemList_SearchCode(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_System_Code.getValue().isEmpty()) {
            checkbox_SystemList_ShowAll.setChecked(false); // unCheck
            txtb_System_Name.setValue(""); // clear
            //txtb_System_Alamat.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Mmedsystem> soSystem = new HibernateSearchObject<Mmedsystem>(Mmedsystem.class, getSystemListCtrl().getCountRows());
            soSystem.addFilter(new Filter(ConstantUtil.TYPE_CODE, "%" + txtb_System_Code.getValue() + "%", Filter.OP_ILIKE));
            soSystem.addSort(ConstantUtil.TYPE_CODE, false);

            // Change the BindingListModel.
            if (getSystemListCtrl().getBinder() != null) {
                getSystemListCtrl().getPagedBindingListWrapper().setSearchObject(soSystem);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_SystemMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabSystemList)) {
                    tabSystemList.setSelected(true);
                } else {
                    currentTab.setSelected(true);
                }
            }
        }
    }

    /**
     * Filter the system list with 'like system name'. <br>
     */
    public void onClick$button_SystemList_SearchName(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_System_Name.getValue().isEmpty()) {
            checkbox_SystemList_ShowAll.setChecked(false); // unCheck
            //txtb_System_Alamat.setValue(""); // clear
            txtb_System_Code.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Mmedsystem> soSystem = new HibernateSearchObject<Mmedsystem>(Mmedsystem.class, getSystemListCtrl().getCountRows());
            soSystem.addFilter(new Filter(ConstantUtil.TYPE_NAME, "%" + txtb_System_Name.getValue() + "%", Filter.OP_ILIKE));
            soSystem.addSort(ConstantUtil.TYPE_NAME, false);

            // Change the BindingListModel.
            if (getSystemListCtrl().getBinder() != null) {
                getSystemListCtrl().getPagedBindingListWrapper().setSearchObject(soSystem);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_SystemMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabSystemList)) {
                    tabSystemList.setSelected(true);
                } else {
                    currentTab.setSelected(true);
                }
            }
        }
    }

    /**
     * Filter the system list with 'like system city'. <br>
     */
    /*public void onClick$button_SystemList_SearchAlamat(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_System_Alamat.getValue().isEmpty()) {
            checkbox_SystemList_ShowAll.setChecked(false); // unCheck
            txtb_System_Name.setValue(""); // clear
            txtb_System_Code.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Mmedsystem> soSystem = new HibernateSearchObject<Mmedsystem>(Mmedsystem.class, getSystemListCtrl().getCountRows());
            soSystem.addFilter(new Filter("alamatUniv", "%" + txtb_System_Alamat.getValue() + "%", Filter.OP_ILIKE));
            //soSystem.addSort("alamatUniv", false);

            // Change the BindingListModel.
            if (getSystemListCtrl().getBinder() != null) {
                getSystemListCtrl().getPagedBindingListWrapper().setSearchObject(soSystem);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_SystemMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabSystemList)) {
                    tabSystemList.setSelected(true);
                } else {
                    currentTab.setSelected(true);
                }
            }

        }
    }*/

    /**
     * When the "help" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnHelp(Event event) throws InterruptedException {
        doHelp(event);
    }

    /**
     * When the "new" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnNew(Event event) throws InterruptedException {
        doNew(event);
    }

    /**
     * When the "save" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnSave(Event event) throws InterruptedException {
        doSave(event);
    }

    /**
     * When the "cancel" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnEdit(Event event) throws InterruptedException {
        doEdit(event);
    }

    /**
     * When the "delete" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnDelete(Event event) throws InterruptedException {
        doDelete(event);
    }

    /**
     * When the "cancel" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnCancel(Event event) throws InterruptedException {
        doCancel(event);
    }

    /**
     * when the "refresh" button is clicked. <br>
     * <br>
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$btnRefresh(Event event) throws InterruptedException {
        doResizeSelectedTab(event);
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++ Business Logic ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * 1. Cancel the current action.<br>
     * 2. Reset the values to its origin.<br>
     * 3. Set UI components back to readonly/disable mode.<br>
     * 4. Set the buttons in edit mode.<br>
     *
     * @param event
     * @throws InterruptedException
     */
    private void doCancel(Event event) throws InterruptedException {
        // logger.debug(event.toString());

        // reset to the original object
        doResetToInitValues();

        // check first, if the tabs are created
        if (getSystemDetailCtrl().getBinder() != null) {

            // refresh all dataBinder related controllers/components
            getSystemDetailCtrl().getBinder().loadAll();

            // set editable Mode
            getSystemDetailCtrl().doReadOnlyMode(true);

            btnCtrlSystem.setInitEdit();
        }
    }

    /**
     * Sets all UI-components to writable-mode. Sets the buttons to edit-Mode.
     * Checks first, if the NEEDED TABS with its contents are created. If not,
     * than create it by simulate an onSelect() with calling Events.sendEvent()
     *
     * @param event
     * @throws InterruptedException
     */
    private void doEdit(Event event) {
        // logger.debug(event.toString());

        // get the current Tab for later checking if we must change it
        Tab currentTab = tabbox_SystemMain.getSelectedTab();

        // check first, if the tabs are created, if not than create it
        if (getSystemDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabSystemDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getSystemDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabSystemDetail, null));
        }

        // check if the tab is one of the Detail tabs. If so do not change the
        // selection of it
        if (!currentTab.equals(tabSystemDetail)) {
            tabSystemDetail.setSelected(true);
        } else {
            currentTab.setSelected(true);
        }

        getSystemDetailCtrl().getBinder().loadAll();

        // remember the old vars
        doStoreInitValues();

        btnCtrlSystem.setBtnStatus_Edit();

        getSystemDetailCtrl().doReadOnlyMode(false);
        // set focus
        getSystemDetailCtrl().txtb_code.focus();
    }

    /**
     * Deletes the selected Bean from the DB.
     *
     * @param event
     * @throws InterruptedException
     * @throws InterruptedException
     */
    private void doDelete(Event event) throws InterruptedException {
        // logger.debug(event.toString());

        // check first, if the tabs are created, if not than create them
        if (getSystemDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabSystemDetail, null));
        }

        // check first, if the tabs are created
        if (getSystemDetailCtrl().getBinder() == null) {
            return;
        }

        final Mmedsystem anSystem = getSelectedSystem();
        if (anSystem != null) {

            // Show a confirm box
            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + anSystem.getSystemValue();
            final String title = Labels.getLabel("message.Deleting.Record");

            MultiLineMessageBox.doSetTemplate();
            if (MultiLineMessageBox.show(msg, title, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, true, new EventListener() {
                @Override
                public void onEvent(Event evt) {
                    switch (((Integer) evt.getData()).intValue()) {
                        case MultiLineMessageBox.YES:
                            try {
                                deleteBean();
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            break; //
                        case MultiLineMessageBox.NO:
                            break; //
                    }
                }

                private void deleteBean() throws InterruptedException {
                    try {
                        getSystemService().delete(anSystem);
                    } catch (DataAccessException e) {
                        ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
                    }
                }

            }

            ) == MultiLineMessageBox.YES) {
            }

        }

        btnCtrlSystem.setInitEdit();

        setSelectedSystem(null);
        // refresh the list
        getSystemListCtrl().doFillListbox();

        // refresh all dataBinder related controllers
        getSystemDetailCtrl().getBinder().loadAll();
    }

    /**
     * Saves all involved Beans to the DB.
     *
     * @param event
     * @throws InterruptedException
     */
    private void doSave(Event event) throws InterruptedException {
        // logger.debug(event.toString());
        if (getSystemDetailCtrl().list_status.getSelectedItem() != null) {
            if (getSystemDetailCtrl().txtb_status.getValue() == getSystemDetailCtrl().list_status.getSelectedItem().getValue().toString()) {
                getSystemDetailCtrl().getSystem().setActiveSts(getSystemDetailCtrl().txtb_status.getValue());
            } else {
                getSystemDetailCtrl().txtb_status.setValue(getSystemDetailCtrl().list_status.getSelectedItem().getValue().toString());
                getSystemDetailCtrl().getSystem().setActiveSts(getSystemDetailCtrl().list_status.getSelectedItem().getValue().toString());
            }
        } else {
            getSystemDetailCtrl().txtb_status.setValue(Codec.StatusAktif.Stat1.getValue());
            getSystemDetailCtrl().getSystem().setActiveSts(Codec.StatusAktif.Stat1.getValue());
        }
        // save all components data in the several tabs to the bean
        getSystemDetailCtrl().getBinder().saveAll();

        try {
            // save it to database
            getSystemService().saveOrUpdate(getSystemDetailCtrl().getSystem());
            // if saving is successfully than actualize the beans as
            // origins.
            doStoreInitValues();
            // refresh the list
            getSystemListCtrl().doFillListbox();
            // later refresh StatusBar
            Events.postEvent("onSelect", getSystemListCtrl().getListBoxSystem(), getSelectedSystem());

            // show the objects data in the statusBar
            String str = getSelectedSystem().getSystemValue();
            EventQueues.lookup("selectedObjectEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeSelectedObject", null, str));

        } catch (DataAccessException e) {
            ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

            // Reset to init values
            doResetToInitValues();

            return;

        } finally {
            btnCtrlSystem.setInitEdit();
            getSystemDetailCtrl().doReadOnlyMode(true);
        }
    }

    /**
     * Sets all UI-components to writable-mode. Stores the current Beans as
     * originBeans and get new Objects from the backend.
     *
     * @param event
     * @throws InterruptedException
     */
    private void doNew(Event event) {
        // logger.debug(event.toString());

        // check first, if the tabs are created
        if (getSystemDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabSystemDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getSystemDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabSystemDetail, null));
        }

        // remember the current object
        doStoreInitValues();

        /** !!! DO NOT BREAK THE TIERS !!! */
        // We don't create a new DomainObject() in the frontend.
        // We GET it from the backend.
        final Mmedsystem anSystem = getSystemService().getNew();

        // set the beans in the related databinded controllers
        getSystemDetailCtrl().setSystem(anSystem);
        getSystemDetailCtrl().setSelectedSystem(anSystem);

        // Refresh the binding mechanism
        getSystemDetailCtrl().setSelectedSystem(getSelectedSystem());
        if (getSystemDetailCtrl().getBinder() != null) {
            getSystemDetailCtrl().getBinder().loadAll();
        }
        // set editable Mode
        getSystemDetailCtrl().doReadOnlyMode(false);

        // set the ButtonStatus to New-Mode
        btnCtrlSystem.setInitNew();

        tabSystemDetail.setSelected(true);
        // set focus
        //getSystemDetailCtrl().txtb_ckdUniv.focus();

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * Resizes the container from the selected Tab.
     *
     * @param event
     */
    private void doResizeSelectedTab(Event event) {
        // logger.debug(event.toString());

        if (tabbox_SystemMain.getSelectedTab() == tabSystemDetail) {
            getSystemDetailCtrl().doFitSize(event);
        } else if (tabbox_SystemMain.getSelectedTab() == tabSystemList) {
            // resize and fill Listbox new
            getSystemListCtrl().doFillListbox();
        }
    }

    /**
     * Opens the help screen for the current module.
     *
     * @param event
     * @throws InterruptedException
     */
    private void doHelp(Event event) throws InterruptedException {

        ZksampleMessageUtils.doShowNotImplementedMessage();

        // we stop the propagation of the event, because zk will call ALL events
        // with the same name in the namespace and 'btnHelp' is a standard
        // button in this application and can often appears.
        // Events.getRealOrigin((ForwardEvent) event).stopPropagation();
        event.stopPropagation();
    }

    /**
     * Saves the selected object's current properties. We can get them back if a
     * modification is canceled.
     */
    public void doStoreInitValues() {

        if (getSelectedSystem() != null) {

            try {
                setOriginalSystem((Mmedsystem) ZksampleBeanUtils.cloneBean(getSelectedSystem()));
            } catch (final IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (final InstantiationException e) {
                throw new RuntimeException(e);
            } catch (final InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (final NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Reset the selected object to its origin property values.
     */
    public void doResetToInitValues() {

        if (getOriginalSystem() != null) {

            try {
                setSelectedSystem((Mmedsystem) ZksampleBeanUtils.cloneBean(getOriginalSystem()));
                // TODO Bug in DataBinder??
                windowSystemMain.invalidate();

            } catch (final IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (final InstantiationException e) {
                throw new RuntimeException(e);
            } catch (final InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (final NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * User rights check. <br>
     * Only components are set visible=true if the logged-in <br>
     * user have the right for it. <br>
     * <p/>
     * The rights are get from the spring framework users grantedAuthority(). A
     * right is only a string. <br>
     */
    // TODO move it to zul
    private void doCheckRights() {

        final UserWorkspace workspace = getUserWorkspace();

        // window_SystemList.setVisible(workspace.isAllowed("window_SystemList"));
        button_SystemList_PrintList.setVisible(workspace.isAllowed("button_SystemList_PrintList"));
        button_SystemList_SearchCode.setVisible(workspace.isAllowed("button_SystemList_SearchCode"));
        button_SystemList_SearchName.setVisible(workspace.isAllowed("button_SystemList_SearchName"));
        button_SystemList_SearchAlamat.setVisible(workspace.isAllowed("button_SystemList_SearchAlamat"));

        btnHelp.setVisible(workspace.isAllowed("button_SystemMain_btnHelp"));
        btnNew.setVisible(workspace.isAllowed("button_SystemMain_btnNew"));
        btnEdit.setVisible(workspace.isAllowed("button_SystemMain_btnEdit"));
        btnDelete.setVisible(workspace.isAllowed("button_SystemMain_btnDelete"));
        btnSave.setVisible(workspace.isAllowed("button_SystemMain_btnSave"));

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setOriginalSystem(Mmedsystem originalSystem) {
        this.originalSystem = originalSystem;
    }

    public Mmedsystem getOriginalSystem() {
        return this.originalSystem;
    }

    public void setSelectedSystem(Mmedsystem selectedSystem) {
        this.selectedSystem = selectedSystem;
    }

    public Mmedsystem getSelectedSystem() {
        return this.selectedSystem;
    }

    public void setSystems(BindingListModelList systems) {
        this.systems = systems;
    }

    public BindingListModelList getSystems() {
        return this.systems;
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public SystemService getSystemService() {
        return this.systemService;
    }

    public void setSystemListCtrl(SystemListCtrl systemListCtrl) {
        this.systemListCtrl = systemListCtrl;
    }

    public SystemListCtrl getSystemListCtrl() {
        return this.systemListCtrl;
    }

    public void setSystemDetailCtrl(SystemDetailCtrl systemDetailCtrl) {
        this.systemDetailCtrl = systemDetailCtrl;
    }

    public SystemDetailCtrl getSystemDetailCtrl() {
        return this.systemDetailCtrl;
    }
}
