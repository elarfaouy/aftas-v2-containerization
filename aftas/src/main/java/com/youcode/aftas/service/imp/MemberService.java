package com.youcode.aftas.service.imp;

import com.youcode.aftas.domain.entity.Role;
import com.youcode.aftas.domain.entity.User;
import com.youcode.aftas.dto.update.UpdateUserDto;
import com.youcode.aftas.exception.DataBaseConstraintException;
import com.youcode.aftas.repository.MemberRepository;
import com.youcode.aftas.service.IMemberService;
import com.youcode.aftas.dto.payload.MemberDto;
import com.youcode.aftas.dto.store.StoreMemberDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MemberService implements IMemberService {
    private final MemberRepository repository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<MemberDto> findAll() {
        return repository
                .findAll()
                .stream().map((element) -> mapper.map(element, MemberDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MemberDto store(StoreMemberDto storeMemberDto) {
        Optional<User> optional = repository.findById(storeMemberDto.getNum());
        if (optional.isPresent()) {
            throw new DataBaseConstraintException("already register member with same number.");
        }

        try {
            User member = mapper.map(storeMemberDto, User.class);
            member.setAccessionDate(LocalDate.now());
            member.setRole(Role.builder().id(3L).build());
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            User saved = repository.save(member);
            return mapper.map(saved, MemberDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseConstraintException("error when save member, identity number must be unique");
        }
    }

    @Override
    public MemberDto update(UpdateUserDto updateUserDto) {
        User user = repository.findById(updateUserDto.getNum()).orElseThrow(
                () -> new DataBaseConstraintException("member not found.")
        );

        user.setRole(updateUserDto.getRole());
        User updated = repository.save(user);
        return mapper.map(updated, MemberDto.class);
    }
}
