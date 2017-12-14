package com.github.parkalot.dao.sqlite.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.github.parkalot.model.Rating;

@Component("sqliteRatingMapper")
public class RatingMapper implements RowMapper<Rating> {

    @Override
    public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

}
