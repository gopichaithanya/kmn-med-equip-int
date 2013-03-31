package id.co.kmn.webui.util.searchdialogs;

import id.co.kmn.administrasi.service.RoomService;
import id.co.kmn.backend.bean.ResultObject;
import id.co.kmn.backend.model.Mmedroom;
import org.apache.log4j.Logger;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.*;
import org.zkoss.zul.event.PagingEvent;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 5/25/12
 * Time: 3:48 AM
 */
public class RoomLookup extends Window implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(RoomLookup.class);

    // the textbox for inserting the searchparameter
    private Textbox _textbox;

    // button for sending the ServiceMethod if searchParameters are used
    private Button _searchButton;

    // the paging component
    private Paging _paging;

    // pageSize
    private int pageSize = 18;

    // the data listbox
    private Listbox listbox;

    // the model for the listbox
    private ListModelList listModelList;

    // the windows title
    private final String _title = Labels.getLabel("message.Information.ExtendedSearch") + " / Room";

    // 1. Listheader
    private final String _listHeader1 = Labels.getLabel("common.Description");

    // the windows height
    private final int _height = 410;

    // the windows width
    private final int _width = 300;

    // the returned bean object
    private Mmedroom obj = null;

    // The service from which we get the data
    private RoomService objService;

    /**
     * The Call method.
     *
     * @param parent The parent component
     * @return a BeanObject from the listBox or null.
     */
    public static Mmedroom show(Component parent) {
        return new RoomLookup(parent).getObj();
    }

    /**
     * Private Constructor. So it can only be created with the static show()
     * method.<br>
     *
     * @param parent
     */
    private RoomLookup(Component parent) {

        super();

        System.out.println("PARENT = " + parent);
        System.out.println("PARENT ID = " + parent.getId());

        setParent(parent);

        createBox();
    }

    /**
     * Creates the components, sets the model and show the window as modal.<br>
     */
    @SuppressWarnings("unchecked")
    private void createBox() {

        // Window
        this.setWidth(String.valueOf(this._width) + "px");
        this.setHeight(String.valueOf(this._height) + "px");
        this.setTitle(this._title);
        this.setVisible(true);
        this.setClosable(true);

        // Borderlayout
        Borderlayout bl = new Borderlayout();
        bl.setHeight("100%");
        bl.setWidth("100%");
        bl.setParent(this);

        Center center = new Center();
        center.setBorder("none");
        center.setFlex(true);
        center.setParent(bl);

        // Borderlayout
        Borderlayout bl2 = new Borderlayout();
        bl2.setHeight("100%");
        bl2.setWidth("100%");
        bl2.setParent(center);

        North north2 = new North();
        north2.setBorder("none");
        north2.setHeight("26px");
        north2.setParent(bl2);
        // Paging
        this._paging = new Paging();
        this._paging.setDetailed(true);
        this._paging.addEventListener("onPaging", new OnPagingEventListener());
        this._paging.setPageSize(getPageSize());
        this._paging.setParent(north2);

        Center center2 = new Center();
        center2.setBorder("none");
        center2.setFlex(true);
        center2.setParent(bl2);
        // Div Center area
        Div divCenter2 = new Div();
        divCenter2.setWidth("100%");
        divCenter2.setHeight("100%");
        divCenter2.setParent(center2);

        // Listbox
        this.listbox = new Listbox();
        // listbox.setStyle("border: none;");
        this.listbox.setHeight("290px");
        this.listbox.setVisible(true);
        this.listbox.setParent(divCenter2);
        this.listbox.setItemRenderer(new SearchBoxItemRenderer());

        Listhead listhead = new Listhead();
        listhead.setParent(this.listbox);
        Listheader listheader = new Listheader();
        listheader.setSclass("FDListBoxHeader1");
        listheader.setParent(listhead);
        listheader.setLabel(this._listHeader1);

        South south2 = new South();
        south2.setBorder("none");
        south2.setHeight("26px");
        south2.setParent(bl2);
        // hbox for holding the Textbox + SearchButton
        Hbox hbox = new Hbox();
        hbox.setPack("stretch");
        hbox.setStyle("padding-left: 5px");
        hbox.setWidth("100%");
        hbox.setHeight("27px");
        hbox.setParent(south2);
        // textbox for inserting the search parameter
        this._textbox = new Textbox();
        this._textbox.setWidth("100%");
        this._textbox.setMaxlength(20);
        this._textbox.setParent(hbox);
        // serachButton
        this._searchButton = new Button();
        this._searchButton.setImage("/images/icons/search.gif");
        this._searchButton.addEventListener("onClick", new OnSearchListener());
        this._searchButton.setParent(hbox);

        South south = new South();
        south.setBorder("none");
        south.setHeight("30px");
        south.setParent(bl);

        Div divSouth = new Div();
        divSouth.setWidth("100%");
        divSouth.setHeight("100%");
        divSouth.setParent(south);

        Separator sep = new Separator();
        sep.setBar(true);
        sep.setOrient("horizontal");
        sep.setParent(divSouth);

        // Button
        Button btnOK = new Button();
        btnOK.setStyle("padding-left: 5px");
        btnOK.setLabel("OK");
        btnOK.addEventListener("onClick", new OnCloseListener());
        btnOK.setParent(divSouth);

        /**
         * init the model.<br>
         * The ResultObject is a helper class that holds the generic list and
         * the totalRecord count as int value.
         */
        ResultObject ro = getObjService().getAllLikeText("", 0, getPageSize());
        List<Mmedroom> resultList = (List<Mmedroom>) ro.getList();
        this._paging.setTotalSize(ro.getTotalCount());

        // set the model
        setListModelList(new ListModelList(resultList));
        this.listbox.setModel(getListModelList());

        try {
            doModal();
        } catch (final SuspendNotAllowedException e) {
            logger.fatal("", e);
            this.detach();
        } catch (final InterruptedException e) {
            logger.fatal("", e);
            this.detach();
        }
    }

    /**
     * Inner ListItemRenderer class.<br>
     */
    final class SearchBoxItemRenderer implements ListitemRenderer {

        @Override
        public void render(Listitem item, Object data) throws Exception {

            Mmedroom obj = (Mmedroom) data;

            Listcell lc = new Listcell(obj.getRoomName());
            lc.setParent(item);

            item.setAttribute("data", data);
            ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClicked");
        }
    }

    /**
     * If a DoubleClick appears on a listItem. <br>
     * This method is forwarded in the renderer.<br>
     *
     * @param event
     */
    public void onDoubleClicked(Event event) {

        if (this.listbox.getSelectedItem() != null) {
            Listitem li = this.listbox.getSelectedItem();
            Mmedroom obj = (Mmedroom) li.getAttribute("data");

            setObj(obj);
            this.onClose();
        }
    }

    /**
     * "onPaging" EventListener for the paging component. <br>
     * <br>
     * Calculates the next page by currentPage and pageSize values. <br>
     * Calls the method for refreshing the data with the new rowStart and
     * pageSize. <br>
     */
    public final class OnPagingEventListener implements EventListener {
        @Override
        public void onEvent(Event event) throws Exception {

            PagingEvent pe = (PagingEvent) event;
            int pageNo = pe.getActivePage();
            int start = pageNo * getPageSize();

            String searchText = RoomLookup.this._textbox.getValue();
            // refresh the list
            refreshModel(searchText, start);
        }
    }

    /**
     * Refreshes the list by calling the DAO methode with the modified search
     * object. <br>
     *
     * @param searchText SearchObject, holds the entity and properties to search. <br>
     * @param start      Row to start. <br>
     */
    @SuppressWarnings("unchecked")
    void refreshModel(String searchText, int start) {

        // clear old data
        getListModelList().clear();

        // init the model
        ResultObject ro = getObjService().getAllLikeText(searchText, start, getPageSize());
        List<Mmedroom> resultList = (List<Mmedroom>) ro.getList();
        this._paging.setTotalSize(ro.getTotalCount());

        // set the model
        setListModelList(new ListModelList(resultList));
        this.listbox.setModel(getListModelList());
    }

    /**
     * Inner OnSearchListener class.<br>
     */
    final class OnSearchListener implements EventListener {
        @Override
        public void onEvent(Event event) throws Exception {

            String searchText = RoomLookup.this._textbox.getValue();

            // we start new
            refreshModel(searchText, 0);
        }
    }

    /**
     * Inner OnCloseListener class.<br>
     */
    final class OnCloseListener implements EventListener {
        @Override
        public void onEvent(Event event) throws Exception {

            if (RoomLookup.this.listbox.getSelectedItem() != null) {
                Listitem li = RoomLookup.this.listbox.getSelectedItem();
                Mmedroom obj = (Mmedroom) li.getAttribute("data");

                setObj(obj);
            }
            onClose();
        }

    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //

    public Mmedroom getObj() {
        return obj;
    }

    public void setObj(Mmedroom obj) {
        this.obj = obj;
    }

    public RoomService getObjService() {
        if (this.objService == null) {
            this.objService = (RoomService) SpringUtil.getBean("roomService");
        }
        return objService;
    }

    public void setObjService(RoomService objService) {
        this.objService = objService;
    }


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setListModelList(ListModelList listModelList) {
        this.listModelList = listModelList;
    }

    public ListModelList getListModelList() {
        return this.listModelList;
    }
}
