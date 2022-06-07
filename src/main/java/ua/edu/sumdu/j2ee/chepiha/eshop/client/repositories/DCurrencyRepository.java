package ua.edu.sumdu.j2ee.chepiha.eshop.client.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db.DCurrency;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.ModelRepository;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.services.LoggerMsgService;

import java.util.List;

@Repository
public class DCurrencyRepository implements ModelRepository <DCurrency> {

    private static final LoggerMsgService logger = new LoggerMsgService(DCurrencyRepository.class) ;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(DCurrency entity) {
        // nothing here
    }

    @Override
    public void update(DCurrency entity) {
        // nothing here
    }

    @Override
    public void delete(long id) {
        // nothing here
    }

    @Override
    public List<DCurrency> findAll() {
        logger.msgDebugGetAll();
        String sql = "select * from lab4_chepihavv_d_currency order by r030";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DCurrency.class));
    }

    // todo:
    //      jdbcTemplate.query(sql, Integer.class);
//    public List<Integer> findAllR030() {
//        String sql = "select r030 from lab4_chepihavv_d_currency order by r030";
//        return jdbcTemplate.query(sql, Integer.class);
//    }

    @Override
    public DCurrency findOne(long id) {
        logger.msgDebugGetOne(id);
        String sql = "select * from lab4_chepihavv_d_currency where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(DCurrency.class), id);
    }

    @Override
    public DCurrency findOne(int r030) {
        logger.msgDebugGetOne("R030", r030);
        String sql = "select * from lab4_chepihavv_d_currency where r030 = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(DCurrency.class), r030);
    }
}
