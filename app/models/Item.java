package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * Company entity managed by JPA
 */
@Entity 
@Table(name="item")
@SequenceGenerator(name = "Item_seq", sequenceName = "item_seq")
public class Item {
    

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Item_seq")
    public Long id;
    
    @Constraints.Required
    public String nome;

    @Constraints.Required
    public String descricao;
   
//    @Constraints.Max(30)
    public String marca;
    
//    @Constraints.Max(60)
    public String modelo;

//    @Constraints.Max(60)
    public String fabricante;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Item.class);
    
    
    /**
     * Find a company by id.
     */
    public static Item findById(Long id) {
        return JPA.em().find(Item.class, id);
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
    

    public static Map<String,String> options() {
        @SuppressWarnings("unchecked")
				List<Item> itens = JPA.em().createQuery("from Item order by nome").getResultList();
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Item c: itens) {
            options.put(c.id.toString(), c.nome);
        }
        return options;
    }
    
    /**
     * Return a page of itens
     *
     * @param page Page to display
     * @param pageSize Number of Items per page
     * @param sortBy Item property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the nome column
     */
    public static Page page(int page, int pageSize, String sortBy, String order, String filter) {
        if(page < 1) page = 1;
        
        LOGGER.error(filter);
        
        
//        StringBuilder sql = new StringBuilder("select c from Item c ");
//        List<Item> list = JPA.em().createQuery(sql.toString()).getResultList();
//        Long total = (long) list.size();
//
//        //        q.setParameter("nomePassado",'%' +filter+ '%');
////        Long total = (Long) q.getSingleResult();
////
        
//        JPA.em().createQuery("select count(c) from Item c ").getResultList();
        Long total = (Long) JPA.em()
            .createQuery("select count(c) from Item c where lower(c.nome) like ? ")
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .getSingleResult();
        
        @SuppressWarnings("unchecked")
				List<Item> data = JPA.em()
            .createQuery("from Item c where lower(c.nome) like ? order by c." + sortBy + " " + order)
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .getResultList();
        return new Page(data, total, page, pageSize);
    }
    
    

    
    
    /**
     * Used to represent a Items page.
     */
    public static class Page {
        
        private final int pageSize;
        private final long totalRowCount;
        private final int pageIndex;
        private final List<Item> list;
        
        public Page(List<Item> data, long total, int page, int pageSize) {
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
        
        public List<Item> getList() {
            return list;
        }
        
        public boolean hasPrev() {
            return pageIndex > 1;
        }
        
        public boolean hasNext() {
            return (totalRowCount/pageSize) >= pageIndex;
        }
        
        public String getDisplayXtoYofZ() {
            int start = ((pageIndex - 1) * pageSize + 1);
            int end = start + Math.min(pageSize, list.size()) - 1;
            return start + " com " + end + " total " + totalRowCount;
        }
        
    }
    

}
