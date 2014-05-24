package models;

import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.Empresa;
import play.db.jpa.JPA;


/**
 * Created with IntelliJ IDEA.
 * User: Vitor
 * Date: 19/05/14
 * Time: 01:03
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="compra")
@SequenceGenerator(name = "Compra_seq", sequenceName = "compra_seq")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Compra_seq")
    public Long id;

    @Constraints.Required
    @Column(name = "data_concorrencia")
    public Date dataConcorrencia;

    @Constraints.Required
    @Column(name = "data_inclusao")
    public Date dataInclusao;

    @Constraints.Required
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id")
    public Empresa empresa;

    @Constraints.Required
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subclasse_id")
    public Subclasse subclasse;

    @Constraints.Required
    public Integer prazo;

    public boolean finalizado;

    public String detalhe;


    /**
     */
    public static Compra findById(Long id) {
        return JPA.em().find(Compra.class, id);
    }

    /**
     */
    public void update(Long id) {
        this.id = id;
        JPA.em().merge(this);
    }

    /**
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
