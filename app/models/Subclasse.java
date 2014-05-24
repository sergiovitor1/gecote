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
@Table(name = "subclasse")
public class Subclasse {

    @Id
    public Long id;

    public String descricao;

    private static final Logger LOGGER = LoggerFactory.getLogger(Subclasse.class);

    public static Subclasse findById(Long id) {

        return JPA.em().find(Subclasse.class, id);
    }

    public static List<Subclasse> findAll() {

        @SuppressWarnings("unchecked")
        List<Subclasse> subclasses = JPA.em().createQuery("select s from subclasse as s order by descricao")
            .getResultList();
        return subclasses;
    }

}
