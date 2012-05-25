package id.co.kmn.webui.administrasi.room;

import com.trg.search.Filter;
import id.co.kmn.UserWorkspace;
import id.co.kmn.administrasi.service.RoomService;
import id.co.kmn.backend.model.Mmedroom;
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
 * Time: 3:36 AM
 */
public class RoomMainCtrl extends GFCBaseCtrl implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(RoomMainCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowRoomMain; // autowired

    // Tabs
    protected Tabbox tabbox_RoomMain; // autowired
    protected Tab tabRoomList; // autowired
    protected Tab tabRoomDetail; // autowired
    protected Tabpanel tabPanelRoomList; // autowired
    protected Tabpanel tabPanelRoomDetail; // autowired

    // filter components
    protected Checkbox checkbox_RoomList_ShowAll; // autowired
    protected Textbox txtb_Room_Code; // aurowired
    protected Button button_RoomList_SearchCode; // aurowired
    protected Textbox txtb_Room_Name; // aurowired
    protected Button button_RoomList_SearchName; // aurowired
    protected Textbox txtb_Room_Description; // aurowired
    protected Button button_RoomList_SearchDescription; // aurowired

    // checkRights
    protected Button button_RoomList_PrintList;

    // Button controller for the CRUD buttons
    private final String btnCtroller_ClassPrefix = "button_RoomMain_";
    private ButtonStatusCtrl btnCtrlRoom;
    protected Button btnNew; // autowired
    protected Button btnEdit; // autowired
    protected Button btnDelete; // autowired
    protected Button btnSave; // autowired
    protected Button btnCancel; // autowired

    protected Button btnHelp;

    // Tab-Controllers for getting the binders
    private RoomListCtrl roomListCtrl;
    private RoomDetailCtrl roomDetailCtrl;

    // Databinding
    private Mmedroom selectedRoom;
    private BindingListModelList rooms;

    // ServiceDAOs / Domain Classes
    private RoomService roomService;

    // always a copy from the bean before modifying. Used for reseting
    private Mmedroom originalRoom;

    /**
     * default constructor.<br>
     */
    public RoomMainCtrl() {
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
    public void onCreate$windowRoomMain(Event event) throws Exception {
        windowRoomMain.setContentStyle("padding:0px;");

        // create the Button Controller. Disable not used buttons during working
        btnCtrlRoom = new ButtonStatusCtrl(getUserWorkspace(), btnCtroller_ClassPrefix, btnNew, btnEdit, btnDelete, btnSave, btnCancel);

        //doCheckRights();

        /**
         * Initiate the first loading by selecting the customerList tab and
         * create the components from the zul-file.
         */
        tabRoomList.setSelected(true);

        if (tabPanelRoomList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelRoomList, this, "ModuleMainController", "/WEB-INF/pages/administrasi/room/roomList.zul");
        }

        // init the buttons for editMode
        btnCtrlRoom.setInitEdit();
    }

    /**
     * When the tab 'tabRoomList' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws java.io.IOException
     */
    public void onSelect$tabRoomList(Event event) throws IOException {
        logger.debug(event.toString());

        // Check if the tabpanel is already loaded
        if (tabPanelRoomList.getFirstChild() != null) {
            tabRoomList.setSelected(true);

            return;
        }

        if (tabPanelRoomList != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelRoomList, this, "ModuleMainController", "/WEB-INF/pages/administrasi/room/roomList.zul");
        }

    }

    /**
     * When the tab 'tabPanelRoomDetail' is selected.<br>
     * Loads the zul-file into the tab.
     *
     * @param event
     * @throws IOException
     */
    public void onSelect$tabRoomDetail(Event event) throws IOException {
        // logger.debug(event.toString());

        // Check if the tabpanel is already loaded
        if (tabPanelRoomDetail.getFirstChild() != null) {
            tabRoomDetail.setSelected(true);

            // refresh the Binding mechanism
            getRoomDetailCtrl().setRoom(getSelectedRoom());
            getRoomDetailCtrl().getBinder().loadAll();
            //refresh combo
            getRoomDetailCtrl().doRenderCombo();
            return;
        }

        if (tabPanelRoomDetail != null) {
            ZksampleCommonUtils.createTabPanelContent(tabPanelRoomDetail, this, "ModuleMainController", "/WEB-INF/pages/administrasi/room/roomDetail.zul");
        }
    }

    /**
     * when the "print rooms list" button is clicked.
     *
     * @param event
     * @throws InterruptedException
     */
    public void onClick$button_RoomList_PrintList(Event event) throws InterruptedException {
        final Window win = (Window) Path.getComponent("/outerIndexWindow");
        new MpegawaiSimpleDJReport(win);
    }

    /**
     * when the checkBox 'Show All' for filtering is checked. <br>
     *
     * @param event
     */
    public void onCheck$checkbox_RoomList_ShowAll(Event event) {
        // logger.debug(event.toString());

        // empty the text search boxes
        txtb_Room_Code.setValue(""); // clear
        txtb_Room_Name.setValue(""); // clear
        //txtb_Room_Description.setValue(""); // clear

        // ++ create the searchObject and init sorting ++//
        HibernateSearchObject<Mmedroom> soRoom = new HibernateSearchObject<Mmedroom>(Mmedroom.class, getRoomListCtrl().getCountRows());
        soRoom.addSort(ConstantUtil.ROOM_NAME, false);

        // Change the BindingListModel.
        if (getRoomListCtrl().getBinder() != null) {
            getRoomListCtrl().getPagedBindingListWrapper().setSearchObject(soRoom);

            // get the current Tab for later checking if we must change it
            Tab currentTab = tabbox_RoomMain.getSelectedTab();

            // check if the tab is one of the Detail tabs. If so do not
            // change the selection of it
            if (!currentTab.equals(tabRoomList)) {
                tabRoomList.setSelected(true);
            } else {
                currentTab.setSelected(true);
            }
        }

    }

    /**
     * Filter the room list with 'like room number'. <br>
     */
    public void onClick$button_RoomList_SearchCode(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_Room_Code.getValue().isEmpty()) {
            checkbox_RoomList_ShowAll.setChecked(false); // unCheck
            txtb_Room_Name.setValue(""); // clear
            //txtb_Room_Description.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Mmedroom> soRoom = new HibernateSearchObject<Mmedroom>(Mmedroom.class, getRoomListCtrl().getCountRows());
            soRoom.addFilter(new Filter(ConstantUtil.ROOM_CODE, "%" + txtb_Room_Code.getValue() + "%", Filter.OP_ILIKE));
            soRoom.addSort(ConstantUtil.ROOM_CODE, false);

            // Change the BindingListModel.
            if (getRoomListCtrl().getBinder() != null) {
                getRoomListCtrl().getPagedBindingListWrapper().setSearchObject(soRoom);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_RoomMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabRoomList)) {
                    tabRoomList.setSelected(true);
                } else {
                    currentTab.setSelected(true);
                }
            }
        }
    }

    /**
     * Filter the room list with 'like room name'. <br>
     */
    public void onClick$button_RoomList_SearchName(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_Room_Name.getValue().isEmpty()) {
            checkbox_RoomList_ShowAll.setChecked(false); // unCheck
            //txtb_Room_Description.setValue(""); // clear
            txtb_Room_Code.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Mmedroom> soRoom = new HibernateSearchObject<Mmedroom>(Mmedroom.class, getRoomListCtrl().getCountRows());
            soRoom.addFilter(new Filter(ConstantUtil.ROOM_NAME, "%" + txtb_Room_Name.getValue() + "%", Filter.OP_ILIKE));
            soRoom.addSort(ConstantUtil.ROOM_NAME, false);

            // Change the BindingListModel.
            if (getRoomListCtrl().getBinder() != null) {
                getRoomListCtrl().getPagedBindingListWrapper().setSearchObject(soRoom);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_RoomMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabRoomList)) {
                    tabRoomList.setSelected(true);
                } else {
                    currentTab.setSelected(true);
                }
            }
        }
    }

    /**
     * Filter the room list with 'like room city'. <br>
     */
    /*public void onClick$button_RoomList_SearchDescription(Event event) throws Exception {
        // logger.debug(event.toString());

        // if not empty
        if (!txtb_Room_Description.getValue().isEmpty()) {
            checkbox_RoomList_ShowAll.setChecked(false); // unCheck
            txtb_Room_Name.setValue(""); // clear
            txtb_Room_Code.setValue(""); // clear

            // ++ create the searchObject and init sorting ++//
            HibernateSearchObject<Mmedroom> soRoom = new HibernateSearchObject<Mmedroom>(Mmedroom.class, getRoomListCtrl().getCountRows());
            soRoom.addFilter(new Filter(ConstantUtil.DESCRIPTION, "%" + txtb_Room_Description.getValue() + "%", Filter.OP_ILIKE));
            //soRoom.addSort(ConstantUtil.DESCRIPTION, false);

            // Change the BindingListModel.
            if (getRoomListCtrl().getBinder() != null) {
                getRoomListCtrl().getPagedBindingListWrapper().setSearchObject(soRoom);

                // get the current Tab for later checking if we must change it
                Tab currentTab = tabbox_RoomMain.getSelectedTab();

                // check if the tab is one of the Detail tabs. If so do not
                // change the selection of it
                if (!currentTab.equals(tabRoomList)) {
                    tabRoomList.setSelected(true);
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
        if (getRoomDetailCtrl().getBinder() != null) {

            // refresh all dataBinder related controllers/components
            getRoomDetailCtrl().getBinder().loadAll();

            // set editable Mode
            getRoomDetailCtrl().doReadOnlyMode(true);

            btnCtrlRoom.setInitEdit();
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
        Tab currentTab = tabbox_RoomMain.getSelectedTab();

        // check first, if the tabs are created, if not than create it
        if (getRoomDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabRoomDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getRoomDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabRoomDetail, null));
        }

        // check if the tab is one of the Detail tabs. If so do not change the
        // selection of it
        if (!currentTab.equals(tabRoomDetail)) {
            tabRoomDetail.setSelected(true);
        } else {
            currentTab.setSelected(true);
        }

        getRoomDetailCtrl().getBinder().loadAll();

        // remember the old vars
        doStoreInitValues();

        btnCtrlRoom.setBtnStatus_Edit();

        getRoomDetailCtrl().doReadOnlyMode(false);
        // set focus
        getRoomDetailCtrl().txtb_code.focus();
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
        if (getRoomDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabRoomDetail, null));
        }

        // check first, if the tabs are created
        if (getRoomDetailCtrl().getBinder() == null) {
            return;
        }

        final Mmedroom anRoom = getSelectedRoom();
        if (anRoom != null) {

            // Show a confirm box
            final String msg = Labels.getLabel("message.Question.Are_you_sure_to_delete_this_record") + "\n\n --> " + anRoom.getRoomName();
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
                        getRoomService().delete(anRoom);
                    } catch (DataAccessException e) {
                        ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());
                    }
                }

            }

            ) == MultiLineMessageBox.YES) {
            }

        }

        btnCtrlRoom.setInitEdit();

        setSelectedRoom(null);
        // refresh the list
        getRoomListCtrl().doFillListbox();

        // refresh all dataBinder related controllers
        getRoomDetailCtrl().getBinder().loadAll();
    }

    /**
     * Saves all involved Beans to the DB.
     *
     * @param event
     * @throws InterruptedException
     */
    private void doSave(Event event) throws InterruptedException {
        // logger.debug(event.toString());
        if (getRoomDetailCtrl().list_status.getSelectedItem() != null) {
            if (getRoomDetailCtrl().txtb_status.getValue() == getRoomDetailCtrl().list_status.getSelectedItem().getValue().toString()) {
                getRoomDetailCtrl().getRoom().setActiveSts(getRoomDetailCtrl().txtb_status.getValue());
            } else {
                getRoomDetailCtrl().txtb_status.setValue(getRoomDetailCtrl().list_status.getSelectedItem().getValue().toString());
                getRoomDetailCtrl().getRoom().setActiveSts(getRoomDetailCtrl().list_status.getSelectedItem().getValue().toString());
            }
        } else {
            getRoomDetailCtrl().txtb_status.setValue(Codec.StatusAktif.Stat1.getValue());
            getRoomDetailCtrl().getRoom().setActiveSts(Codec.StatusAktif.Stat1.getValue());
        }
        // save all components data in the several tabs to the bean
        getRoomDetailCtrl().getBinder().saveAll();

        try {
            // save it to database
            getRoomService().saveOrUpdate(getRoomDetailCtrl().getRoom());
            // if saving is successfully than actualize the beans as
            // origins.
            doStoreInitValues();
            // refresh the list
            getRoomListCtrl().doFillListbox();
            // later refresh StatusBar
            Events.postEvent("onSelect", getRoomListCtrl().getListBoxRoom(), getSelectedRoom());

            // show the objects data in the statusBar
            String str = getSelectedRoom().getRoomName();
            EventQueues.lookup("selectedObjectEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeSelectedObject", null, str));

        } catch (DataAccessException e) {
            ZksampleMessageUtils.showErrorMessage(e.getMostSpecificCause().toString());

            // Reset to init values
            doResetToInitValues();

            return;

        } finally {
            btnCtrlRoom.setInitEdit();
            getRoomDetailCtrl().doReadOnlyMode(true);
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
        if (getRoomDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", tabRoomDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getRoomDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", tabRoomDetail, null));
        }

        // remember the current object
        doStoreInitValues();

        /** !!! DO NOT BREAK THE TIERS !!! */
        // We don't create a new DomainObject() in the frontend.
        // We GET it from the backend.
        final Mmedroom anRoom = getRoomService().getNew();

        // set the beans in the related databinded controllers
        getRoomDetailCtrl().setRoom(anRoom);
        getRoomDetailCtrl().setSelectedRoom(anRoom);

        // Refresh the binding mechanism
        getRoomDetailCtrl().setSelectedRoom(getSelectedRoom());
        if (getRoomDetailCtrl().getBinder() != null) {
            getRoomDetailCtrl().getBinder().loadAll();
        }
        // set editable Mode
        getRoomDetailCtrl().doReadOnlyMode(false);

        // set the ButtonStatus to New-Mode
        btnCtrlRoom.setInitNew();

        tabRoomDetail.setSelected(true);
        // set focus
        //getRoomDetailCtrl().txtb_code.focus();

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

        if (tabbox_RoomMain.getSelectedTab() == tabRoomDetail) {
            getRoomDetailCtrl().doFitSize(event);
        } else if (tabbox_RoomMain.getSelectedTab() == tabRoomList) {
            // resize and fill Listbox new
            getRoomListCtrl().doFillListbox();
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

        if (getSelectedRoom() != null) {

            try {
                setOriginalRoom((Mmedroom) ZksampleBeanUtils.cloneBean(getSelectedRoom()));
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

        if (getOriginalRoom() != null) {

            try {
                setSelectedRoom((Mmedroom) ZksampleBeanUtils.cloneBean(getOriginalRoom()));
                // TODO Bug in DataBinder??
                windowRoomMain.invalidate();

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

        // window_RoomList.setVisible(workspace.isAllowed("window_RoomList"));
        button_RoomList_PrintList.setVisible(workspace.isAllowed("button_RoomList_PrintList"));
        button_RoomList_SearchCode.setVisible(workspace.isAllowed("button_RoomList_SearchCode"));
        button_RoomList_SearchName.setVisible(workspace.isAllowed("button_RoomList_SearchName"));
        button_RoomList_SearchDescription.setVisible(workspace.isAllowed("button_RoomList_SearchDescription"));

        btnHelp.setVisible(workspace.isAllowed("button_RoomMain_btnHelp"));
        btnNew.setVisible(workspace.isAllowed("button_RoomMain_btnNew"));
        btnEdit.setVisible(workspace.isAllowed("button_RoomMain_btnEdit"));
        btnDelete.setVisible(workspace.isAllowed("button_RoomMain_btnDelete"));
        btnSave.setVisible(workspace.isAllowed("button_RoomMain_btnSave"));

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public void setOriginalRoom(Mmedroom originalRoom) {
        this.originalRoom = originalRoom;
    }

    public Mmedroom getOriginalRoom() {
        return this.originalRoom;
    }

    public void setSelectedRoom(Mmedroom selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    public Mmedroom getSelectedRoom() {
        return this.selectedRoom;
    }

    public void setRooms(BindingListModelList rooms) {
        this.rooms = rooms;
    }

    public BindingListModelList getRooms() {
        return this.rooms;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public RoomService getRoomService() {
        return this.roomService;
    }

    public void setRoomListCtrl(RoomListCtrl roomListCtrl) {
        this.roomListCtrl = roomListCtrl;
    }

    public RoomListCtrl getRoomListCtrl() {
        return this.roomListCtrl;
    }

    public void setRoomDetailCtrl(RoomDetailCtrl roomDetailCtrl) {
        this.roomDetailCtrl = roomDetailCtrl;
    }

    public RoomDetailCtrl getRoomDetailCtrl() {
        return this.roomDetailCtrl;
    }
}
