package id.ac.idu.administrasi.dao;

import id.ac.idu.backend.model.Mfasilitasruang;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: siak
 * Date: 3/9/12
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MfasilitasruangDAO {
    /**
     * EN: Get a new Mfasilitasruang object.<br>
     * DE: Gibt ein neues Mfasilitasruang (Niederlassung) Objekt zurueck.<br>
     *
     * @return Mfasilitasruang
     */
    public Mfasilitasruang getNewMfasilitasruang();

    /**
     * EN: Get an Mfasilitasruang by its ID.<br>
     * DE: Gibt ein Mfasilitasruang anhand seiner ID zurueck.<br>
     *
     * @param filId
     *            / the persistence identifier / der PrimaerKey
     * @return Mfasilitasruang / Mfasilitasruang
     */
    public Mfasilitasruang getMfasilitasruangById(Long filId);

    /**
     * EN: Get an Mfasilitasruang object by its ID.<br>
     * DE: Gibt ein Mfasilitasruang Objekt anhand seiner ID zurueck.<br>
     *
     * @param fil_nr
     *            / the mfasilitasruang number / die Niederlassungs Nummer
     * @return Mfasilitasruang / Mfasilitasruang
     */
    public Mfasilitasruang getMfasilitasruangByFilNr(String fil_nr);

    /**
     * EN: Get a list of all Mfasilitasruangs.<br>
     * DE: Gibt eine Liste aller Mfasilitasruangs zurueck.<br>
     *
     * @return List of Mfasilitasruangs / Liste von Mfasilitasruangs
     */
    public List<Mfasilitasruang> getAllMfasilitasruangs();

    /**
     * EN: Get the count of all Mfasilitasruangs.<br>
     * DE: Gibt die Anzahl aller Mfasilitasruangs zurueck.<br>
     *
     * @return int
     */
    public int getCountAllMfasilitasruangs();

    /**
     * EN: Gets a list of Mfasilitasruangs where the city name contains the %string% .<br>
     * DE: Gibt eine Liste aller Mfasilitasruangs zurueck bei denen der Stadtname
     * %string% enthaelt.<br>
     *
     * @param string
     *            Name of the city / Stadtnamen
     * @return List of Mfasilitasruangs / Liste of Mfasilitasruangs
     */
    public List<Mfasilitasruang> getMfasilitasruangsLikeCity(String string);

    /**
     * EN: Gets a list of Mfasilitasruangs where the mfasilitasruang name1 contains the %string% .<br>
     * DE: Gibt eine Liste aller Mfasilitasruangs zurueck bei denen der Mfasilitasruang Name1
     * %string% enthaelt.<br>
     *
     * @param string
     *            Name1 of the mfasilitasruang / Name1 vom Mfasilitasruang
     * @return List of Mfasilitasruangs / Liste of Mfasilitasruangs
     */
    public List<Mfasilitasruang> getMfasilitasruangsLikeName1(String string);

    /**
     * EN: Gets a list of Mfasilitasruangs where the mfasilitasruang number contains the %string%
     * .<br>
     * DE: Gibt eine Liste aller Mfasilitasruangs zurueck bei denen die Mfasilitasruang Nummer
     * %string% enthaelt.<br>
     *
     * @param string
     *            Number of the mfasilitasruang / Nummer vom Mfasilitasruang
     * @return List of Mfasilitasruangs / Liste of Mfasilitasruangs
     */
    public List<Mfasilitasruang> getMfasilitasruangsLikeNo(String string);

    /**
     * EN: Deletes an Mfasilitasruang by its Id.<br>
     * DE: Loescht ein Mfasilitasruang anhand seiner Id.<br>
     *
     * @param id
     *            / the persistence identifier / der PrimaerKey
     */
    public void deleteMfasilitasruangById(long id);

    /**
     * EN: Saves new or updates an Mfasilitasruang.<br>
     * DE: Speichert neu oder aktualisiert ein Mfasilitasruang.<br>
     */
    public void saveOrUpdate(Mfasilitasruang entity);

    /**
     * EN: Deletes an Mfasilitasruang.<br>
     * DE: Loescht ein Mfasilitasruang.<br>
     */
    public void delete(Mfasilitasruang entity);

    /**
     * EN: Saves an Mfasilitasruang.<br>
     * DE: Speichert ein Mfasilitasruang.<br>
     */
    public void save(Mfasilitasruang entity);

}
