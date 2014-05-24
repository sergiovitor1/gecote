/**
 * @author Sérgio Vitor (sergiovitor1@gmail.com)
 * @since 14/05/2014
 *
 */
package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.db.jpa.JPA;

/**
 * @author Sérgio Vitor (sergiovitor1@gmail.com)
 * @since 14/05/2014
 * 
 */
@Entity
@Table(name = "estado")
public class Estado {

    @Id
    public Long id;

    public String descricao;

    private static final Logger LOGGER = LoggerFactory.getLogger(Estado.class);

    public static Estado findById(Long id) {

        return JPA.em().find(Estado.class, id);
    }

    public static List<Estado> findAll() {

        @SuppressWarnings("unchecked")
        List<Estado> estados = JPA.em().createQuery("select s from estado as s order by descricao")
            .getResultList();
        return estados;
    }

}
