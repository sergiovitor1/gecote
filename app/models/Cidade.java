/**
 * @author Sérgio Vitor (sergiovitor1@gmail.com)
 * @since 14/05/2014
 */
package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.Estado;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "cidade")
public class Cidade {

    @Id
    public Long id;

    @Column
    public String descricao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_id", insertable = false, updatable = false)
    public Estado estado;
    

    private static final Logger LOGGER = LoggerFactory.getLogger(Cidade.class);

    public static Cidade findById(Long id) {

        return JPA.em().find(Cidade.class, id);
    }

    public static List<Cidade> findAll() {

        @SuppressWarnings("unchecked")
        List<Cidade> cidades = JPA.em().createQuery("select e from cidade as e order by descricao")
            .getResultList();
        return cidades;
    }
    
    
    public static Map<String,String> options() {
        @SuppressWarnings("unchecked")
                List<Cidade> cidades = JPA.em().createQuery("from Cidade order by descricao").getResultList();
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Cidade c: cidades) {
            options.put(c.id.toString(), c.descricao);
        }
        return options;
    }
    

}
