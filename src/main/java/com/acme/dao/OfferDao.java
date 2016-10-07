package com.acme.dao;
;
import com.acme.model.Offer;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * Created by Mir on 05/10/2016.
 *
 * This interface class extends the generic Jpa CrudReposity interface provided by Spring Data. There
 * is no need to implement these CRUD operations as they are already implemented and injected by Spring
 * Data. Only additional operations that are not covered by these CRUD operations need to be implemented.
 * These are defined by the interface class <code>OfferDaoCustom</code> and implemented by its
 * implementation Dao class <code>OfferDaoCustom</code>.
 *
 */
public interface OfferDao extends CrudRepository<Offer, Long>, OfferDaoCustom {

}
