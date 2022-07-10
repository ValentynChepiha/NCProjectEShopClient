package ua.edu.sumdu.j2ee.chepiha.eshop.client.repositories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db.CurrencyRate;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.ModelRateRepository;

import java.util.List;

@Slf4j
@Repository
public class CurrencyRateRepository implements ModelRateRepository<CurrencyRate> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CurrencyRateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CurrencyRate> findActualExchange() {
        log.debug("Get all CurrencyRate's on last update");
        String sql = "select a.id, a.r030, a.rate,  c.cc, c.txt from lab4_chepihavv_currency_exchange a " +
                " left join lab4_chepihavv_d_currency c on a.r030 = c.r030 where a.exchangedate " +
                " = (select max(b.exchangedate) from lab4_chepihavv_currency_exchange b) order by c.cc";
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CurrencyRate.class));
    }

}
