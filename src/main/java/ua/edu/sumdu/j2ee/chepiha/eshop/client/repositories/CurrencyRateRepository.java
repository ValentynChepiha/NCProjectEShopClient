package ua.edu.sumdu.j2ee.chepiha.eshop.client.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db.CurrencyRate;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.ModelRateRepository;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.services.LoggerMsgService;

import java.util.List;

@Repository
public class CurrencyRateRepository implements ModelRateRepository<CurrencyRate> {

    private static final LoggerMsgService logger = new LoggerMsgService(CurrencyRateRepository.class) ;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CurrencyRateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CurrencyRate> findActualExchange() {
        logger.msgDebugGetActualExchange();
        String sql = "select a.id, a.r030, a.rate,  c.cc, c.txt from lab4_chepihavv_currency_exchange a " +
                " left join lab4_chepihavv_d_currency c on a.r030 = c.r030 where a.exchangedate " +
                " = (select max(b.exchangedate) from lab4_chepihavv_currency_exchange b) order by c.cc";
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CurrencyRate.class));
    }

}
