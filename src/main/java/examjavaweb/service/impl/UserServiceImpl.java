package examjavaweb.service.impl;

import examjavaweb.model.entity.User;
import examjavaweb.model.service.UserServiceModel;
import examjavaweb.repository.UsersRepository;
import examjavaweb.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UsersRepository usersRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel,User.class);

        return this.modelMapper
                .map(this.usersRepository.saveAndFlush(user),UserServiceModel.class);

    }

    @Override
    public UserServiceModel findByUsername(String username) {
        return this.usersRepository.findByUsername(username)
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }
}
