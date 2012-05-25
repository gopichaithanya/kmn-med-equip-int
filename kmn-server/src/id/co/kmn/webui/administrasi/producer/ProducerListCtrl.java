package id.co.kmn.webui.administrasi.producer;

import id.co.kmn.administrasi.service.ProducerService;
import id.co.kmn.backend.model.Mmedproducer;
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
 * @Date 5/1/12
 * Time: 12:18 PM
 */
public class ProducerListCtrl extends GFCBaseListCtrl<Mmedproducer> implements Serializable {
    private static final long serialVersionUID = -1L;
    private static final Logger logger = Logger.getLogger(ProducerListCtrl.class);

    /*
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      * All the components that are defined here and have a corresponding
      * component with the same 'id' in the zul-file are getting autowired by our
      * 'extends GFCBaseCtrl' GenericForwardComposer.
      * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
      */
    protected Window windowProducerList; // autowired
    protected Panel panelProducerList; // autowired

    protected Borderlayout borderLayout_producerList; // autowired
    protected Paging paging_ProducerList; // autowired
    protected Listbox listBoxProducer; // autowired
    protected Listheader listheader_ProducerList_Code; // autowired
    protected Listheader listheader_ProducerList_Name; // autowired
    protected Listheader listheader_ProducerList_Alamat; // autowired
    protected Listheader listheader_ProducerList_Status; // autowired

    // NEEDED for ReUse in the SearchWindow
    private HibernateSearchObject<Mmedproducer> searchObj;

    // row count for listbox
    private int countRows;

    // Databinding
    private AnnotateDataBinder binder;
    private ProducerMainCtrl producerMainCtrl;

    // ServiceDAOs / Domain Classes
    private transient ProducerService producerService;

    /**
     * default constructor.<br>
     */
    public ProducerListCtrl() {
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
            setProducerMainCtrl((ProducerMainCtrl) arg.get("ModuleMainController"));

            // SET THIS CONTROLLER TO THE module's Parent/MainController
            getProducerMainCtrl().setProducerListCtrl(this);

            // Get the selected object.
            // Check if this Controller if created on first time. If so,
            // than the selectedXXXBean should be null
            if (getProducerMainCtrl().getSelectedProducer() != null) {
                setSelectedProducer(getProducerMainCtrl().getSelectedProducer());
            } else
                setSelectedProducer(null);
        } else {
            setSelectedProducer(null);
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

    public void onCreate$windowProducerList(Event event) throws Exception {
        binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);

        doFillListbox();

        binder.loadAll();
    }

    public void doFillListbox() {

        doFitSize();

        // set the paging params
        paging_ProducerList.setPageSize(getCountRows());
        paging_ProducerList.setDetailed(true);

        // not used listheaders must be declared like ->
        // lh.setSortAscending(""); lh.setSortDescending("")
        listheader_ProducerList_Code.setSortAscending(new FieldComparator(ConstantUtil.PRODUCER_CODE, true));
        listheader_ProducerList_Code.setSortDescending(new FieldComparator(ConstantUtil.PRODUCER_CODE, false));
        listheader_ProducerList_Name.setSortAscending(new FieldComparator(ConstantUtil.PRODUCER_NAME, true));
        listheader_ProducerList_Name.setSortDescending(new FieldComparator(ConstantUtil.PRODUCER_NAME, false));
        //listheader_ProducerList_Alamat.setSortAscending(new FieldComparator(ConstantUtil.ACTIVE_STATUS, true));
        //listheader_ProducerList_Alamat.setSortDescending(new FieldComparator(ConstantUtil.ACTIVE_STATUS, false));
        listheader_ProducerList_Status.setSortAscending(new FieldComparator(ConstantUtil.ACTIVE_STATUS, true));
        listheader_ProducerList_Status.setSortDescending(new FieldComparator(ConstantUtil.ACTIVE_STATUS, false));

        // ++ create the searchObject and init sorting ++//
        // ++ create the searchObject and init sorting ++//
        searchObj = new HibernateSearchObject<Mmedproducer>(Mmedproducer.class, getCountRows());
        searchObj.addSort(ConstantUtil.PRODUCER_CODE, false);
        setSearchObj(searchObj);

        // Set the BindingListModel
        getPagedBindingListWrapper().init(searchObj, getListBoxProducer(), paging_ProducerList);
        BindingListModelList lml = (BindingListModelList) getListBoxProducer().getModel();
        setProducers(lml);

        // check if first time opened and init databinding for selectedBean
        if (getSelectedProducer() == null) {
            // init the bean with the first record in the List
            if (lml.getSize() > 0) {
                final int rowIndex = 0;
                // only for correct showing after Rendering. No effect as an
                // Event
                // yet.
                getListBoxProducer().setSelectedIndex(rowIndex);
                // get the first entry and cast them to the needed object
                setSelectedProducer((Mmedproducer) lml.get(0));

                // call the onSelect Event for showing the objects data in the
                // statusBar
                Events.sendEvent(new Event("onSelect", getListBoxProducer(), getSelectedProducer()));
            }
        }

    }

    /**
     * Selects the object in the listbox and change the tab.<br>
     * Event is forwarded in the corresponding listbox.
     */
    public void onDoubleClickedProducerItem(Event event) {
        // logger.debug(event.toString());

        Mmedproducer anProducer = getSelectedProducer();

        if (anProducer != null) {
            setSelectedProducer(anProducer);
            setProducer(anProducer);

            // check first, if the tabs are created
            if (getProducerMainCtrl().getProducerDetailCtrl() == null) {
                Events.sendEvent(new Event("onSelect", getProducerMainCtrl().tabProducerDetail, null));
                // if we work with spring beanCreation than we must check a
                // little bit deeper, because the Controller are preCreated ?
            } else if (getProducerMainCtrl().getProducerDetailCtrl().getBinder() == null) {
                Events.sendEvent(new Event("onSelect", getProducerMainCtrl().tabProducerDetail, null));
            }

            Events.sendEvent(new Event("onSelect", getProducerMainCtrl().tabProducerDetail, anProducer));
        }
    }

    /**
     * When a listItem in the corresponding listbox is selected.<br>
     * Event is forwarded in the corresponding listbox.
     *
     * @param event
     */
    public void onSelect$listBoxProducer(Event event) {
        // logger.debug(event.toString());

        // selectedProducer is filled by annotated databinding mechanism
        Mmedproducer anProducer = getSelectedProducer();

        if (anProducer == null) {
            return;
        }

        // check first, if the tabs are created
        if (getProducerMainCtrl().getProducerDetailCtrl() == null) {
            Events.sendEvent(new Event("onSelect", getProducerMainCtrl().tabProducerDetail, null));
            // if we work with spring beanCreation than we must check a little
            // bit deeper, because the Controller are preCreated ?
        } else if (getProducerMainCtrl().getProducerDetailCtrl().getBinder() == null) {
            Events.sendEvent(new Event("onSelect", getProducerMainCtrl().tabProducerDetail, null));
        }

        // INIT ALL RELATED Queries/OBJECTS/LISTS NEW
        getProducerMainCtrl().getProducerDetailCtrl().setSelectedProducer(anProducer);
        getProducerMainCtrl().getProducerDetailCtrl().setProducer(anProducer);

        // store the selected bean values as current
        getProducerMainCtrl().doStoreInitValues();

        // show the objects data in the statusBar
        String str = Labels.getLabel("common.Producer") + ": " + anProducer.getProdCode();
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
        borderLayout_producerList.setHeight(String.valueOf(maxListBoxHeight) + "px");

        windowProducerList.invalidate();
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
    public Mmedproducer getProducer() {
        // STORED IN THE module's MainController
        return getProducerMainCtrl().getSelectedProducer();
    }

    public void setProducer(Mmedproducer anProducer) {
        // STORED IN THE module's MainController
        getProducerMainCtrl().setSelectedProducer(anProducer);
    }

    public void setSelectedProducer(Mmedproducer selectedProducer) {
        // STORED IN THE module's MainController
        getProducerMainCtrl().setSelectedProducer(selectedProducer);
    }

    public Mmedproducer getSelectedProducer() {
        // STORED IN THE module's MainController
        return getProducerMainCtrl().getSelectedProducer();
    }

    public void setProducers(BindingListModelList producers) {
        // STORED IN THE module's MainController
        getProducerMainCtrl().setProducers(producers);
    }

    public BindingListModelList getProducers() {
        // STORED IN THE module's MainController
        return getProducerMainCtrl().getProducers();
    }

    public void setBinder(AnnotateDataBinder binder) {
        this.binder = binder;
    }

    public AnnotateDataBinder getBinder() {
        return this.binder;
    }

    public void setSearchObj(HibernateSearchObject<Mmedproducer> searchObj) {
        this.searchObj = searchObj;
    }

    public HibernateSearchObject<Mmedproducer> getSearchObj() {
        return this.searchObj;
    }

    public void setProducerService(ProducerService producerService) {
        this.producerService = producerService;
    }

    public ProducerService getProducerService() {
        return this.producerService;
    }

    public Listbox getListBoxProducer() {
        return this.listBoxProducer;
    }

    public void setListBoxProducer(Listbox listBoxProducer) {
        this.listBoxProducer = listBoxProducer;
    }

    public int getCountRows() {
        return this.countRows;
    }

    public void setCountRows(int countRows) {
        this.countRows = countRows;
    }

    public void setProducerMainCtrl(ProducerMainCtrl producerMainCtrl) {
        this.producerMainCtrl = producerMainCtrl;
    }

    public ProducerMainCtrl getProducerMainCtrl() {
        return this.producerMainCtrl;
    }
}
