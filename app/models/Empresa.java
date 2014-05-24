package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Email;
import play.db.jpa.JPA;

/**
 * Company entity managed by JPA
 */
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @Column(name = "cnpj")
    @Constraints.Required
    public Long id;

    @Constraints.Required
    public String email;

    @Column(name = "email_alternativo")
    public String emailAlternativo;

    @Constraints.Required
    @Column(name = "inscricao_estadual")
    public Integer inscricaoEstadual;

    @Constraints.Required
    @Column(name = "inscricao_municipal")
    public Integer inscricaoMunicipal;

    @Constraints.Required
    public Long telefone;

    @Constraints.Required
    public String nome;

    @Constraints.Required
    @Column(name = "razao_social")
    public String razaoSocial;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    @Constraints.Required
    public Endereco endereco;

    public boolean ativo;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "subclasse_id")
//    @Constraints.Required
//    public Subclasse subclasse;

    private static final Logger LOGGER = LoggerFactory.getLogger(Empresa.class);

    /**
     * Find a company by id.
     */
    public static Empresa findById(Long id) {

        return JPA.em().find(Empresa.class, id);
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

        /*
         * if(this.tipo.id == null) { this.tipo = null; } else { this.tipo = Tipo.findById(tipo.id); }
         */
        this.id = id;
        JPA.em().persist(this);
    }

    /**
     * Delete this computer.
     */
    public void delete() {

        this.ativo = Boolean.FALSE;
        JPA.em().merge(this);
    }

    public static Map<String, String> options() {

        @SuppressWarnings("unchecked")
        List<Empresa> empresas = JPA.em().createQuery("from empresa order by nome").getResultList();
        LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
        for (Empresa c : empresas) {
            options.put(c.id.toString(), c.nome);
        }
        return options;
    }

    /**
     * Return a page of itens
     * 
     * @param page Page to display
     * @param pageSize Number of Empresas per page
     * @param sortBy Empresa property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the nome column
     */
    public static Page page(int page, int pageSize, String sortBy, String order, String filter) {

        if (page < 1)
            page = 1;

        LOGGER.error(filter);

        Long total = (Long) JPA.em().createQuery("select count(c) from Empresa c where lower(c.nome) like ? ")
            .setParameter(1, "%" + filter.toLowerCase() + "%").getSingleResult();

        @SuppressWarnings("unchecked")
        List<Empresa> data = JPA.em()
            .createQuery("from Empresa c where lower(c.nome) like ? order by c." + sortBy + " " + order)
            .setParameter(1, "%" + filter.toLowerCase() + "%").setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize).getResultList();
        return new Page(data, total, page, pageSize);
    }

    /**
     * Used to represent a Empresas page.
     */
    public static class Page {

        private final int pageSize;

        private final long totalRowCount;

        private final int pageIndex;

        private final List<Empresa> list;

        public Page(List<Empresa> data, long total, int page, int pageSize) {

            this.list = data;
            this.totalRowCount = total;
            this.pageIndex = page;
            this.pageSize = pageSize;
        }

        public long getTotalRowCount() {

            return totalRowCount;
        }

        public int getPageIndex() {

            return pageIndex;
        }

        public List<Empresa> getList() {

            return list;
        }

        public boolean hasPrev() {

            return pageIndex > 1;
        }

        public boolean hasNext() {

            return (totalRowCount / pageSize) >= pageIndex;
        }

        public String getDisplayXtoYofZ() {

            int start = ((pageIndex - 1) * pageSize + 1);
            int end = start + Math.min(pageSize, list.size()) - 1;
            return start + " com " + end + " total " + totalRowCount;
        }

    }

}
