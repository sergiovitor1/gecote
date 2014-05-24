/**
 * @author Sérgio Vitor (sergiovitor1@gmail.com)
 * @since 14/05/2014
 *
 */
package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * @author Sérgio Vitor (sergiovitor1@gmail.com)
 * @since 14/05/2014
 * 
 */
@Entity 
@Table(name="endereco")
@SequenceGenerator(name = "Endereco_seq", sequenceName = "endereco_seq")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Endereco_seq")
    public Long id;

    @Constraints.Required
    public String descricao;

    @Constraints.Required
    public Integer numero;

    public String complemento;

    @Constraints.Required
    public String cep;
    
    @ManyToOne
    @Constraints.Required
    public Cidade cidade;
    

    private static final Logger LOGGER = LoggerFactory.getLogger(Endereco.class);

    public static Endereco findById(Long id) {

        return JPA.em().find(Endereco.class, id);
    }

    /**
     * Update this computer.
     */
    public void update(Long id) {
        this.id = id;
        JPA.em().merge(this);
    }

    /**
     * Insert this new computer.
     */
    public void save() {
/*        if(this.tipo.id == null) {
            this.tipo = null;
        } else {
            this.tipo = Tipo.findById(tipo.id);
        }
 */
        this.id = id;
        JPA.em().persist(this);
    }

    /**
     * Delete this computer.
     */
    public void delete() {
        JPA.em().remove(this);
    }

}
