package com.techforb.challenge.config;

import com.techforb.challenge.converter.LoanConverter;
import com.techforb.challenge.converter.PaymentConverter;
import com.techforb.challenge.converter.TransactionConverter;
import com.techforb.challenge.converter.TransferConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(TransactionConverter transactionConverter, TransferConverter transferConverter, PaymentConverter paymentConverter, LoanConverter loanConverter) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(transactionConverter);
        modelMapper.addConverter(transferConverter);
        modelMapper.addConverter(paymentConverter);
        modelMapper.addConverter(loanConverter);

        // Set the matching strategy to strict to avoid ambiguous property mappings
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // Configure access level
        modelMapper.getConfiguration().setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        return modelMapper;
    }
}