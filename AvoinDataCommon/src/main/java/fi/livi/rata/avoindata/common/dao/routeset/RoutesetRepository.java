package fi.livi.rata.avoindata.common.dao.routeset;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import fi.livi.rata.avoindata.common.domain.trainreadymessage.TrainRunningMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fi.livi.rata.avoindata.common.dao.CustomGeneralRepository;
import fi.livi.rata.avoindata.common.domain.routeset.Routeset;

@Repository
public interface RoutesetRepository extends CustomGeneralRepository<Routeset, Long> {
    @Query("select coalesce(max(t.version),0) from Routeset t")
    long getMaxVersion();

    @Query("delete from Routeset t where t.id in ?1")
    @Modifying
    void removeById(List<Long> ids);

    @Query("SELECT max(t.virtualDepartureDate) FROM Routeset t " +
            "where t.trainId.trainNumber = ?1 and t.virtualDepartureDate > ?2" +
            " order by t.id desc")
    LocalDate getMaxDepartureDateForTrainNumber(String train_number, LocalDate localDate);

    @Query("SELECT distinct t FROM Routeset t left join fetch t.routesections rsec where " +
            " t.trainId.trainNumber = ?1 and " +
            " t.version > ?3 and" +
            " (" +
            "   t.virtualDepartureDate = ?2 " +
            "   or" +
            "   (t.virtualDepartureDate = ?4 and t.trainId.departureDate is null and t.messageTime between ?5 and ?6)" +
            " ) order by t.id desc, rsec.sectionOrder asc")
    List<Routeset> findByTrainNumberAndDepartureDate(String trainNumber, LocalDate departureDate, Long version, LocalDate localDate, ZonedDateTime start, ZonedDateTime end);

    @Query("SELECT distinct t FROM Routeset t left join fetch t.routesections rsec where " +
            " rsec.stationCode = ?1 and" +
            " (" +
            "   t.virtualDepartureDate = ?2 " +
            "   or" +
            "   (t.virtualDepartureDate = ?3 and t.trainId.departureDate is null and t.messageTime between ?4 and ?5)" +
            " ) order by t.id desc, rsec.sectionOrder asc")
    List<Routeset> findByStationAndDepartureDate(String station, LocalDate departureDate, final LocalDate nextDay,
                                                 final ZonedDateTime nextDayStart, final ZonedDateTime nextDayEnd);

    // This is faster but limiting does not work
    // @Query("SELECT distinct t FROM Routeset t left join fetch t.routesections rsec where t.version > ?1 order by t.version desc, rsec.sectionOrder asc ")
    @Query("SELECT distinct t FROM Routeset t where t.version > ?1 order by t.version desc")
    List<Routeset> findByVersionGreaterThan(long version, Pageable pageable);
}
