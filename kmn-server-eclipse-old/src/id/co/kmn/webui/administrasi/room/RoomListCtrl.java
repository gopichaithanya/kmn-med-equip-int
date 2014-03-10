package id.co.kmn.webui.administrasi.room;

import id.co.kmn.administrasi.service.RoomService;
import id.co.kmn.backend.model.Mmedroom;
import id.co.kmn.backend.util.HibernateSearchObject;
import id.co.kmn.util.ConstantUtil;
import id.co.kmn.webui.util.GFCBaseListCtrl;
import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.*;

import java.io.Serializable;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/10/12
 * Time: 3:36 AM
 */
public class RoomListCtrl extends GFCBaseListCtrl<Mmedroom> implements Serializable {
    private static final long serialVersionUID = -1L;
    private static final Logger logger = Logger.getLogger(RoomListCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowRoomList; // autowired
    protected Panel panelRoomList; // autowired

    protected Borderlayout borderLayout_roomList; // autowired
    protected Paging paging_RoomList; // autowired
    protected Listbox listBoxRoom; // autowired
    protected Listheader listheader_RoomList_Code; // autowired
    protected Listheader listheader_RoomList_Name; // autowired
    protected Listheader listheader_RoomList_Description; // autowired
    protected Listheader listheader_RoomList_Status; // autowired

    // NEEDED for ReUse in the SearchWindow
    private HibernateSearchObject<Mmedroom> searchObj;

    // row count for listbox
    private int countRows;

    // Databinding
    private AnnotateDataBinder binder;
    private RoomMainCtrl roomMainCtrl;

    // ServiceDAOs / Domain Classes
    private transient RoomService roomService;

    /**
     * default constructor.<br>
     */
    public RoomListCtrl() {
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

        /**
         * 1. Get the overhanded MainController.<br>
         * 2. Set this controller in the MainController.<br>
         * 3. Check if a 'selectedObject' exists yet in the MainController.<br>
         */
        if (arg.containsKey("ModuleMainController")) {
            setRoomMainCtrl((RoomMainCtrl) arg.get("ModuleMainController"));

            // SET THIS CONTROLLER TO THE module's Parent/MainController
            getRoomMainCtrl().setRoomListCtrl(this);

            // Get the selected object.
            // Check if this Controller if created on first time. If so,
            // than the selectedXXXBean should be null
            if (getRoomMainCtrl().getSelectedRoom() != null) {
                setSelectedRoom(getRoomMainCtrl().getSelectedRoom());
            } else
                setSelectedRoom(null);
        } else {
            setSelectedRoom(null);
        }
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

    public void onCreate$windowRoomList(Event event) throws Exception {
        binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);

        doFillListbox();

        binder.loadAll();
    }

    public void doFillListbox() {

        doFitSize();

        // set the paging params
        paging_RoomList.setPageSize(getCountRows());
        paging_RoomList.setDetailed(true);

        // not used listheaders must be declared like ->
        // lh.setSortAscending(""); lh.setSortDescending("")
        listheader_RoomList_Code.setSortAscending(new FieldComparator(ConstantUtil.ROOM_CODE, true));
        listheader_RoomList_Code.setSortDescending(new FieldComparator(ConstantUtil.ROOM_CODE, false));
        listheader_RoomList_Name.setSortAscending(new FieldComparator(ConstantUtil.ROOM_NAME, true));
        listheader_RoomList_Name.setSortDescending(new FieldComparator(ConstantUtil.ROOM_NAME, false));
        listheader_RoomList_Description.setSortAscending(new FieldComparator(ConstantUtil.DESCRIPTION, true));
        listheader_RoomList_Description.setSortDescending(new FieldComparator(ConstantUtil.DESCRIPTION, false));
        listheader_RoomList_Status.setSortAscending(new FieldComparator(ConstantUtil.ACTIVE_STATUS, true));
        listheader_RoomList_Status.setSortDescending(new FieldComparator(ConstantUtil.ACTIVE_STATUS, false));

        // ++ create the searchObject and init sorting ++//
        searchObj = new HibernateSearchObject<Mmedroom>(Mmedroom.class, getCountRows());
        searchObj.addSort(ConstantUtil.ROOM_CODE, false);
        setSearchObj(searchObj);

        // Set the BindingListModel
        getPagedBindingListWrapper().init(searchObj, getListBoxRoom(), paging_RoomList);
        BindingListModelList lml = (BindingListModelList) getListBoxRoom().getModel();
        setRooms(lml);

        // check if first time opened and init databinding for selectedBean
        if (getSelectedRoom() == null) {
            // init the bean with the first record in the List
            if (lml.getSize() > 0) {
                final int rowIndex = 0;
                // only for correct showing after Rendering. No effect as an
                // Event
                // yet.
                getListBoxRoom().setSelectedIndex(rowIndex);
                // get the first entry and cast them to the needed object
                setSelectedRoom((Mmedroom) lml.get(0));

                // call the onSelect Event for showing the objects data in the
                // statusBar
                Events.sendEvent(new Event("onSelect", getListBoxRoom(), getSelectedRoom()));
            }
        }

    }

    /**
     * Selects the object in the listbox and change the tab.<br>
     * Event is forwarded in the corresponding listbox.
     */
    public void onDoubleClickedRoomItem(Event event) {
        // logger.debug(event.toString());

        Mmedroom anRoom = getSelectedRoom();

        if (anRoom != null) {
            setSelectedRoom(anRoom);
            setRoom(anRoom);

            // check first, if the tabs are created
            if (getRoomMainCtrl().getRoomDetailCtrl() == null) {
                Events.sendEvent(new Event("onSelect", getRoomMainCtrl().tabRoomDetail, null));
                // if we work with spring beanCreation than we must check a
                // little bit deeper, because the Controller are preCreated ?
            } else if (getRoomMainCtrl().getRoomDetailCtrl().getBinder() == null) {
                Events.sendEvent(new Event("onSelect", getRoomMainCtrl().tabRoomDetail, null));
            }

            Events.sendEvent(new Event("onSelect", getRoomMainCtrl().tabRoomDetail, anRoom));
        }
    }

    /**
     * When a listItem in the corresponding listbox is selected.<br>
     * Event is forwarded in the corresponding listbox.
     *
     * @param event
     */
    public void onSelect$listBoxRoom(Event event) {
        // logger.debug(event.toString());

        // selectedRoom is filled by annotated databinding mechanism
        Mmedroom anRoom = getSelectedRoom();

        if (anRoom == null) {
            return;
        }

        // check first, if the tabs are created
        if (getRoomMainCtrl().getRoomDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", getRoomMainCtrl().tabRoomDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getRoomMainCtrl().getRoomDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", getRoomMainCtrl().tabRoomDetail, null));
        }

        // INIT ALL RELATED Queries/OBJECTS/LISTS NEW
        getRoomMainCtrl().getRoomDetailCtrl().setSelectedRoom(anRoom);
        getRoomMainCtrl().getRoomDetailCtrl().setRoom(anRoom);

        // store the selected bean values as current
        getRoomMainCtrl().doStoreInitValues();

        // show the objects data in the statusBar
        String str = Labels.getLabel("common.Room") + ": " + anRoom.getRoomCode();
        EventQueues.lookup("selectedObjectEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeSelectedObject", null, str));

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // +++++++++++++++++ Business Logic ++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++++++ Helpers ++++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    /**
     * Recalculates the container size for this controller and resize them.
     * <p/>
     * Calculate how many rows have been place in the listbox. Get the
     * currentDesktopHeight from a hidden Intbox from the index.zul that are
     * filled by onClientInfo() in the indexCtroller.
     */
    public void doFitSize() {
        // normally 0 ! Or we have a i.e. a toolBar on top of the listBox.
        final int specialSize = 5;
        final int height = ((Intbox) Path.getComponent("/outerIndexWindow/currentDesktopHeight")).getValue().intValue();
        final int maxListBoxHeight = height - specialSize - 148;
        setCountRows((int) Math.round(maxListBoxHeight / 17.7));
        borderLayout_roomList.setHeight(String.valueOf(maxListBoxHeight) + "px");

        windowRoomList.invalidate();
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    // ++++++++++++++++++ getter / setter +++++++++++++++++++//
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//

    /**
     * Best Pratice Hint:<br>
     * The setters/getters for the local annotated data binded Beans/Sets are
     * administered in the module's mainController. Working in this way you have
     * clean line to share this beans/sets with other controllers.
     */
    public Mmedroom getRoom() {
        // STORED IN THE module's MainController
        return getRoomMainCtrl().getSelectedRoom();
    }

    public void setRoom(Mmedroom anRoom) {
        // STORED IN THE module's MainController
        getRoomMainCtrl().setSelectedRoom(anRoom);
    }

    public void setSelectedRoom(Mmedroom selectedRoom) {
        // STORED IN THE module's MainController
        getRoomMainCtrl().setSelectedRoom(selectedRoom);
    }

    public Mmedroom getSelectedRoom() {
        // STORED IN THE module's MainController
        return getRoomMainCtrl().getSelectedRoom();
    }

    public void setRooms(BindingListModelList rooms) {
        // STORED IN THE module's MainController
        getRoomMainCtrl().setRooms(rooms);
    }

    public BindingListModelList getRooms() {
        // STORED IN THE module's MainController
        return getRoomMainCtrl().getRooms();
    }

    public void setBinder(AnnotateDataBinder binder) {
        this.binder = binder;
    }

    public AnnotateDataBinder getBinder() {
        return this.binder;
    }

    public void setSearchObj(HibernateSearchObject<Mmedroom> searchObj) {
        this.searchObj = searchObj;
    }

    public HibernateSearchObject<Mmedroom> getSearchObj() {
        return this.searchObj;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public RoomService getRoomService() {
        return this.roomService;
    }

    public Listbox getListBoxRoom() {
        return this.listBoxRoom;
    }

    public void setListBoxRoom(Listbox listBoxRoom) {
        this.listBoxRoom = listBoxRoom;
    }

    public int getCountRows() {
        return this.countRows;
    }

    public void setCountRows(int countRows) {
        this.countRows = countRows;
    }

    public void setRoomMainCtrl(RoomMainCtrl roomMainCtrl) {
        this.roomMainCtrl = roomMainCtrl;
    }

    public RoomMainCtrl getRoomMainCtrl() {
        return this.roomMainCtrl;
    }
}
