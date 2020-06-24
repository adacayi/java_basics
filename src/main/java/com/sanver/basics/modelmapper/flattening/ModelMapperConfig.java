package com.sanver.basics.modelmapper.flattening;

import org.modelmapper.ModelMapper;

public class ModelMapperConfig {
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        return modelMapper;
    }
}

