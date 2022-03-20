package com.database.jdbc;

import com.database.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PersonJdbcDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    class PersonRowMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final Person person = new Person();
            person.setId(rs.getInt("id"));
            person.setName(rs.getString("name"));
            person.setLocation(rs.getString("location"));
            person.setBirthDate(rs.getDate("birth_date").toLocalDate());
            return person;
        }
    }

    public List<Person> findAll() {
        return jdbcTemplate.query("select * from person",
                new PersonRowMapper());
    }

    public Person findById(final int id) {
        return jdbcTemplate.queryForObject("select * from person where id=?",
                new PersonRowMapper(),
                id);
    }

    public int deleteById(final int id) {
        return jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    public int insert(final Person person) {
        return jdbcTemplate.update("INSERT INTO person VALUES(?, ?, ?, ?);",
                person.getId(),
                person.getName(),
                person.getLocation(),
                person.getBirthDate());
    }

    public int update(Person person) {
        return jdbcTemplate.update("UPDATE person SET name=?, location=?, birth_date=? WHERE id=?;",
                person.getName(),
                person.getLocation(),
                person.getBirthDate(),
                person.getId());
    }

}
