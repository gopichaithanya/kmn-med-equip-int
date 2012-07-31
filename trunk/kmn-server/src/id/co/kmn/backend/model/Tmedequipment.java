package id.co.kmn.backend.model;

import java.util.Date;

/**
 * @author <a href="valeo.gumilang@gmail.com">Valeo Gumilang</a>
 * @Date 7/31/12
 * Time: 11:38 PM
 */
public class Tmedequipment implements java.io.Serializable {
    private int id;
    private String equipmentCode;
    private String branchCode;
    private Mmedproducer mmedproducer;
    private Mmedequiptype mmedequiptype;
    private String name;
    private String dicomSts;
    private Mmedroom mmedroom;
    private String resultId;
    private Integer interfaceId;
    private Date createDate;
    private String creatorId;
    private Date lastUpdate;
    private String lastUserId;

    public Mmedequipment() {
    }


    public Mmedequipment(int id, String equipmentCode, String name) {
        this.id = id;
        this.equipmentCode = equipmentCode;
        this.name = name;
    }
    public Mmedequipment(int id, String equipmentCode, String branchCode, Mmedproducer mmedproducer, Mmedequiptype mmedequiptype,
        String name, String dicomSts, Mmedroom mmedroom, String resultId, Integer interfaceId, Date createDate,
        String creatorId, Date lastUpdate, String lastUserId) {
       this.id = id;
       this.equipmentCode = equipmentCode;
       this.branchCode = branchCode;
       this.mmedproducer = mmedproducer;
       this.mmedequiptype = mmedequiptype;
       this.name = name;
       this.dicomSts = dicomSts;
       this.mmedroom = mmedroom;
       this.resultId = resultId;
       this.interfaceId = interfaceId;
       this.createDate = createDate;
       this.creatorId = creatorId;
       this.lastUpdate = lastUpdate;
       this.lastUserId = lastUserId;
    }
}
