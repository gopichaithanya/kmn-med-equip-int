package id.co.kmn.webui.administrasi.producer;

import com.trg.search.Filter;
import id.co.kmn.UserWorkspace;
import id.co.kmn.administrasi.service.ProducerService;
import id.co.kmn.backend.model.Mmedproducer;
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
 * @Date 5/1/12
 * Time: 12:18 PM
 */
public class ProducerMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ProducerMainCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowProducerMain; // autowired

    // Tabs
    protected Tabbox tabbox_ProducerMain; // autowired
    protected Tab tabProducerList; // autowired
    protected Tab tabProducerDetail; // autowired
    protected Tabpanel tabPanelProducerList; // autowired
    protected Tabpanel tabPanelProducerDetail; // autowired

    // filter components
    protected Checkbox checkbox_ProducerList_ShowAll; // autowired
    protected Textbox txtb_Producer_Code; // aurowired
    protected Button button_ProducerList_SearchCode; // aurowired
    protected Textbox txtb_Producer_Name; // aurowired
    protected Button button_ProducerList_SearchName; // aurowired
    protected Textbox txtb_Producer_Alamat; // aurowired
    protected Button button_ProducerList_SearchAlamat; // aurowired

    // checkRights
    protected Button button_ProducerList_PrintList;

    // Button controller for the CRUD buttons
    private final String btnCtroller_ClassPrefix = "button_ProducerMain_";
    private ButtonStatusCtrl btnCtrlProducer;
    protected Button btnNew; // autowired
    protected Button btnEdit; // autowired
    protected Button btnDelete; // autowired
    protected Button btnSave; // autowired
    protected Button btnCancel; // autowired

    protected Button btnHelp;

    // Tab-Controllers for getting the binders
    private ProducerListCtrl producerListCtrl;
    private ProducerDetailCtrl producerDetailCtrl;

    // Databinding
    private Mmedproducer selectedProducer;
    private BindingListModelList producers;

    // ServiceDAOs / Domain Classes
    private ProducerService producerService;

    // always a copy from the bean before modifying. Used for reseting
    private Mmedproducer originalProducer;

    /**
     * default constructor.<br>
     */
    public ProducerMainCtrl() {
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
    public void onCreate$windowProducerMain(Event event) throws Exception {
        windowProducerMain.setContentStyle("padding:0px;");

        // create the Button Controller. Disable not used buttons during working
        btnCtrlProducer = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, btnSave, btnCancel);

        //doCheckRights();

        /**
         * Initiate the first loading by selecting the customerList tab and
         * create the components from the zul-file.
         */
        tabProducerList.setSelected(true);

        if (tabPanelProducerList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelProducerList, this, "ModuleMainController", "/WEB-INF/pages/administrasi/producer/producerList.zul");
        }

        // init the buttons for editMode
        btnCtrlProducer.setInitEdit();
    }

    /**
     * When the tab 'tabProducerList' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws java.io.IOException
     */
    public void onSelect$tabProducerList(Event event) throws IOException {
        logger.debug(event.toString());

        // Check if the tabpanel is already loaded
        if (tabPanelProducerList.getFirstChild() != null) {
            tabProducerList.setSelected(true);

            return;
        }

        if (tabPanelProducerList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelProducerList, this, "ModuleMainController", "/WEB-INF/pages/administrasi/producer/producerList.zul");
        }

    }

    /**
     * When the tab 'tabPanelProducerDetail' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws IOException
     */
    public void onSelect$tabProducerDetail(Event event) throws IOException {
        // logger.debug(event.toString());

        // Check if the tabpanel is already loaded
        if (tabPanelProducerDetail.getFirstChild() != null) {
            tabProducerDetail.setSelected(true);

            // refresh the Binding mechanism
            getProducerDetailCtrl().setProducer(getSelectedProducer());
            getProducerDetailCtrl().getBinder().loadAll();
            //refresh combo
            getProducerDetailCtrl().doRenderCombo();
            return;
        }

        if (tabPanelProducerDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelProducerDetail, this, "ModuleMainController", "/WEB-INF/pages/administrasi/producer/producerDetail.zul");
        }
    }

    /**
     * when the "print producers list" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$button_ProducerList_PrintList(Event event) throws InterruptedException {
        final Window win = (Window) Path.getComponent("/outerIndexWindow");
        new MpegawaiSimpleDJReport(win);
    }

    /**
     * when the checkBox 'Show All' for filtering is checked. <br>
     *
     * @param event
     */
    public void onCheck$checkbox_ProducerList_ShowAll(Event event) {
        // logger.debug(event.toString());

        // empty the text search boxes
        txtb_Producer_Code.setValue(""); // clear
        txtb_Producer_Name.setValue(""); // clear
        //txtb_Producer_Alamat.setValue(""); // clear

        // ++ create the searchObject and init sorting ++//
        HibernateSearchObject<Mmedproducer> soProducer = new HibernateSearchObject<Mmedproducer>(Mmedproducer.class, getProducerListCtrl().getCountRows());
        soProducer.addSort(ConstantUtil.PRODUCER_NAME, false);

        // Change the BindingListModel.
        if (getProducerListCtrl().getBinder() != null) {
            getProducerListCtrl().getPagedBindingListWrapper().setSearchObject(soProducer);

            // get the current Tab for later checking if we must change it
            Tab currentTab = tabbox_ProducerMain.getSelectedTab();

            // check if the tab is one of the Detail tabs. If so do not
            // change the selection of it
            if (!currentTab.equals(tabProducerList)) {
                tabProducerList.setSelected(true);
            } else {
                currentTab.setSelected(true);
            }
        }

    }

    /**
     * Filter the producer list with 'like producer number'. <br>
     */
    public void onClick$button_ProducerList_SearchCode(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_Producer_Code.getValue().isEmpty()) {
            checkbox_ProducerList_ShowAll.setChecked(false); // unCheck
            txtb_Producer_Name.setValue(""); // clear
            //txtb_Producer_Alamat.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Mmedproducer> soProducer = new HibernateSearchObject<Mmedproducer>(Mmedproducer.class, getProducerListCtrl().getCountRows());
            soProducer.addFilter(new Filter(ConstantUtil.PRODUCER_CODE, "%" + txtb_Producer_Code.getValue() + "%", Filter.OP_ILIKE));
            soProducer.addSort(ConstantUtil.PRODUCER_CODE, false);

            // Change the BindingListModel.
            if (getProducerListCtrl().getBinder() != null) {
                getProducerListCtrl().getPagedBindingListWrapper().setSearchObject(soProducer);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_ProducerMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabProducerList)) {
                    tabProducerList.setSelected(true);
                } else {
                    currentTab.setSelected(true);
                }
            }
        }
    }

    /**
     * Filter the producer list with 'like producer name'. <br>
     */
    public void onClick$button_ProducerList_SearchName(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_Producer_Name.getValue().isEmpty()) {
            checkbox_ProducerList_ShowAll.setChecked(false); // unCheck
            //txtb_Producer_Alamat.setValue(""); // clear
            txtb_Producer_Code.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Mmedproducer> soProducer = new HibernateSearchObject<Mmedproducer>(Mmedproducer.class, getProducerListCtrl().getCountRows());
            soProducer.addFilter(new Filter(ConstantUtil.PRODUCER_NAME, "%" + txtb_Producer_Name.getValue() + "%", Filter.OP_ILIKE));
            soProducer.addSort(ConstantUtil.PRODUCER_NAME, false);

            // Change the BindingListModel.
            if (getProducerListCtrl().getBinder() != null) {
                getProducerListCtrl().getPagedBindingListWrapper().setSearchObject(soProducer);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_ProducerMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabProducerList)) {
                    tabProducerList.setSelected(true);
                } else {
                    currentTab.setSelected(true);
                }
            }
        }
    }

    /**
     * Filter the producer list with 'like producer city'. <br>
     */
    /*public void onClick$button_ProducerList_SearchAlamat(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_Producer_Alamat.getValue().isEmpty()) {
            checkbox_ProducerList_ShowAll.setChecked(false); // unCheck
            txtb_Producer_Name.setValue(""); // clear
            txtb_Producer_Code.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Mmedproducer> soProducer = new HibernateSearchObject<Mmedproducer>(Mmedproducer.class, getProducerListCtrl().getCountRows());
            soProducer.addFilter(new Filter("alamatUniv", "%" + txtb_Producer_Alamat.getValue() + "%", Filter.OP_ILIKE));
            //soProducer.addSort("alamatUniv", false);

            // Change the BindingListModel.
            if (getProducerListCtrl().getBinder() != null) {
                getProducerListCtrl().getPagedBindingListWrapper().setSearchObject(soProducer);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_ProducerMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabProducerList)) {
                    tabProducerList.setSelected(true);
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
        if (getProducerDetailCtrl().getBinder() != null) {

            // refresh all dataBinder related controllers/components
            getProducerDetailCtrl().getBinder().loadAll();

            // set editable Mode
            getProducerDetailCtrl().doReadOnlyMode(true);

            btnCtrlProducer.setInitEdit();
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
        Tab currentTab = tabbox_ProducerMain.getSelectedTab();

        // check first, if the tabs are created, if not than create it
        if (getProducerDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabProducerDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getProducerDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabProducerDetail, null));
        }

        // check if the tab is one of the Detail tabs. If so do not change the
        // selection of it
        if (!currentTab.equals(tabProducerDetail)) {
            tabProducerDetail.setSelected(true);
        } else {
            currentTab.setSelected(true);
        }

        getProducerDetailCtrl().getBinder().loadAll();

        // remember the old vars
        doStoreInitValues();

        btnCtrlProducer.setBtnStatus_Edit();

        getProducerDetailCtrl().doReadOnlyMode(false);
        // set focus
        getProducerDetailCtrl().txtb_ckdUniv.focus();
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
        if (getProducerDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabProducerDetail, null));
        }

        // check first, if the tabs are created
        if (getProducerDetailCtrl().getBinder() == null) {
            return;
        }

        final Mmedproducer anProducer = getSelectedProducer();
        if (anProducer != null) {

            // Show a confirm box
            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + anProducer.getProdName();
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
                        getProducerService().delete(anProducer);
                    } catch (DataAccessException e) {
                        ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
                    }
                }

            }

            ) == MultiLineMessageBox.YES) {
            }

        }

        btnCtrlProducer.setInitEdit();

        setSelectedProducer(null);
        // refresh the list
        getProducerListCtrl().doFillListbox();

        // refresh all dataBinder related controllers
        getProducerDetailCtrl().getBinder().loadAll();
    }

    /**
     * Saves all involved Beans to the DB.
     *
     * @param event
     * @throws InterruptedException
     */
    private void doSave(Event event) throws InterruptedException {
        // logger.debug(event.toString());
        if (getProducerDetailCtrl().list_status.getSelectedItem() != null) {
            if (getProducerDetailCtrl().txtb_cstatus.getValue() == getProducerDetailCtrl().list_status.getSelectedItem().getValue().toString()) {
                getProducerDetailCtrl().getProducer().setActiveSts(getProducerDetailCtrl().txtb_cstatus.getValue());
            } else {
                getProducerDetailCtrl().txtb_cstatus.setValue(getProducerDetailCtrl().list_status.getSelectedItem().getValue().toString());
                getProducerDetailCtrl().getProducer().setActiveSts(getProducerDetailCtrl().list_status.getSelectedItem().getValue().toString());
            }
        } else {
            getProducerDetailCtrl().txtb_cstatus.setValue(Codec.StatusAktif.Stat1.getValue());
            getProducerDetailCtrl().getProducer().setActiveSts(Codec.StatusAktif.Stat1.getValue());
        }
        // save all components data in the several tabs to the bean
        getProducerDetailCtrl().getBinder().saveAll();

        try {
            // save it to database
            getProducerService().saveOrUpdate(getProducerDetailCtrl().getProducer());
            // if saving is successfully than actualize the beans as
            // origins.
            doStoreInitValues();
            // refresh the list
            getProducerListCtrl().doFillListbox();
            // later refresh StatusBar
            Events.postEvent("onSelect", getProducerListCtrl().getListBoxProducer(), getSelectedProducer());

            // show the objects data in the statusBar
            String str = getSelectedProducer().getProdName();
            EventQueues.lookup("selectedObjectEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeSelectedObject", null, str));

        } catch (DataAccessException e) {
            ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

            // Reset to init values
            doResetToInitValues();

            return;

        } finally {
            btnCtrlProducer.setInitEdit();
            getProducerDetailCtrl().doReadOnlyMode(true);
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
        if (getProducerDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabProducerDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getProducerDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabProducerDetail, null));
        }

        // remember the current object
        doStoreInitValues();

        /** !!! DO NOT BREAK THE TIERS !!! */
        // We don't create a new DomainObject() in the frontend.
        // We GET it from the backend.
        final Mmedproducer anProducer = getProducerService().getNew();

        // set the beans in the related databinded controllers
        getProducerDetailCtrl().setProducer(anProducer);
        getProducerDetailCtrl().setSelectedProducer(anProducer);

        // Refresh the binding mechanism
        getProducerDetailCtrl().setSelectedProducer(getSelectedProducer());
        if (getProducerDetailCtrl().getBinder() != null) {
            getProducerDetailCtrl().getBinder().loadAll();
        }
        // set editable Mode
        getProducerDetailCtrl().doReadOnlyMode(false);

        // set the ButtonStatus to New-Mode
        btnCtrlProducer.setInitNew();

        tabProducerDetail.setSelected(true);
        // set focus
        //getProducerDetailCtrl().txtb_ckdUniv.focus();

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

        if (tabbox_ProducerMain.getSelectedTab() == tabProducerDetail) {
            getProducerDetailCtrl().doFitSize(event);
        } else if (tabbox_ProducerMain.getSelectedTab() == tabProducerList) {
            // resize and fill Listbox new
            getProducerListCtrl().doFillListbox();
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

        if (getSelectedProducer() != null) {

            try {
                setOriginalProducer((Mmedproducer) ZksampleBeanUtils.cloneBean(getSelectedProducer()));
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

        if (getOriginalProducer() != null) {

            try {
                setSelectedProducer((Mmedproducer) ZksampleBeanUtils.cloneBean(getOriginalProducer()));
                // TODO Bug in DataBinder??
                windowProducerMain.invalidate();

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

        // window_ProducerList.setVisible(workspace.isAllowed("window_ProducerList"));
        button_ProducerList_PrintList.setVisible(workspace.isAllowed("button_ProducerList_PrintList"));
        button_ProducerList_SearchCode.setVisible(workspace.isAllowed("button_ProducerList_SearchCode"));
        button_ProducerList_SearchName.setVisible(workspace.isAllowed("button_ProducerList_SearchName"));
        button_ProducerList_SearchAlamat.setVisible(workspace.isAllowed("button_ProducerList_SearchAlamat"));

        btnHelp.setVisible(workspace.isAllowed("button_ProducerMain_btnHelp"));
        btnNew.setVisible(workspace.isAllowed("button_ProducerMain_btnNew"));
        btnEdit.setVisible(workspace.isAllowed("button_ProducerMain_btnEdit"));
        btnDelete.setVisible(workspace.isAllowed("button_ProducerMain_btnDelete"));
        btnSave.setVisible(workspace.isAllowed("button_ProducerMain_btnSave"));

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setOriginalProducer(Mmedproducer originalProducer) {
        this.originalProducer = originalProducer;
    }

    public Mmedproducer getOriginalProducer() {
        return this.originalProducer;
    }

    public void setSelectedProducer(Mmedproducer selectedProducer) {
        this.selectedProducer = selectedProducer;
    }

    public Mmedproducer getSelectedProducer() {
        return this.selectedProducer;
    }

    public void setProducers(BindingListModelList producers) {
        this.producers = producers;
    }

    public BindingListModelList getProducers() {
        return this.producers;
    }

    public void setProducerService(ProducerService producerService) {
        this.producerService = producerService;
    }

    public ProducerService getProducerService() {
        return this.producerService;
    }

    public void setProducerListCtrl(ProducerListCtrl producerListCtrl) {
        this.producerListCtrl = producerListCtrl;
    }

    public ProducerListCtrl getProducerListCtrl() {
        return this.producerListCtrl;
    }

    public void setProducerDetailCtrl(ProducerDetailCtrl producerDetailCtrl) {
        this.producerDetailCtrl = producerDetailCtrl;
    }

    public ProducerDetailCtrl getProducerDetailCtrl() {
        return this.producerDetailCtrl;
    }
}
