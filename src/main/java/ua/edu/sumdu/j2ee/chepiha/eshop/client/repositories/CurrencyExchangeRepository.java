package ua.edu.sumdu.j2ee.chepiha.eshop.client.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.entities.db.CurrencyExchange;
import ua.edu.sumdu.j2ee.chepiha.eshop.client.interfaces.ModelRepository;

import java.sql.Date;
import java.util.List;

@Repository
public class CurrencyExchangeRepository implements ModelRepository<CurrencyExchange> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(CurrencyExchange entity) {
        String sql = "insert into lab4_chepihavv_currency_exchange(r030, rate, exchangedate) values (?, ?, ?)";
        jdbcTemplate.update(sql, entity.getR030(), entity.getRate(), entity.getExchangeDate());
    }

    @Override
    public void update(CurrencyExchange entity) {
        String sql = "update lab4_chepihavv_currency_exchange set r030=?, rate=?, exchangedate=? where id=?";
        jdbcTemplate.update(sql, entity.getR030(), entity.getRate(), entity.getExchangeDate(), entity.getId());
    }

    @Override
    public void delete(long id) {
        String sql = "delete from lab4_chepihavv_currency_exchange where id = ?;";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<CurrencyExchange> findAll() {
        String sql = "select * from lab4_chepihavv_currency_exchange order by r030";
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CurrencyExchange.class));
    }

    @Override
    public CurrencyExchange findOne(long id) {
        String sql = "select * from lab4_chepihavv_currency_exchange where id = ?";
        return  jdbcTemplate.queryForObject (sql, new BeanPropertyRowMapper<>(CurrencyExchange.class), id);
    }

    @Override
    public CurrencyExchange findOne(int r030) {
        String sql = "select * from lab4_chepihavv_currency_exchange where r030 = ?";
        return  jdbcTemplate.queryForObject (sql, new BeanPropertyRowMapper<>(CurrencyExchange.class), r030);
    }

    public Date getLastUpdate() {
        String sql = "select max(exchangedate) exchangedate from lab4_chepihavv_currency_exchange";
        return jdbcTemplate.queryForObject(sql, Date.class);
    }

    public Integer checkLastDateUpdate() {
        String sql = "SELECT case when (to_date(to_char(MAX(EXCHANGEDATE), 'ddmmyyyy'), 'ddmmyyyy') " +
                ">= to_date(to_char(sysdate, 'ddmmyyyy'), 'ddmmyyyy')) then 1 else -1 end " +
                " FROM LAB4_CHEPIHAVV_CURRENCY_EXCHANGE";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

}
