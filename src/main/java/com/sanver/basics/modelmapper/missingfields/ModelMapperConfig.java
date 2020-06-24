package com.sanver.basics.modelmapper.missingfields;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class ModelMapperConfig {
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        modelMapper.getConfiguration().setFieldMatchingEnabled(true);
        modelMapper.addMappings(new PropertyMap<UserDto, User>() {
            @Override
            protected void configure() {
                skip(destination.getId()); // id field in User will be tried to be mapped by both userId and projectId from UserDto because of Standard matching strategy. To get rid of it we skip it like this.
            }
        });
        return modelMapper;
    }
}

