package com.techforb.challenge.config;

import com.techforb.challenge.converter.TransactionConverter;
import com.techforb.challenge.dto.TransactionDTO;
import com.techforb.challenge.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(TransactionConverter transactionConverter) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(transactionConverter);

        // Set the matching strategy to strict to avoid ambiguous property mappings
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // Configure access level
        modelMapper.getConfiguration().setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        return modelMapper;
    }
}