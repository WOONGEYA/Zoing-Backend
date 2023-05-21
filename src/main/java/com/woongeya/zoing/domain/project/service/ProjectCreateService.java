package com.woongeya.zoing.domain.project.service;

import com.woongeya.zoing.domain.project.domain.Member;
import com.woongeya.zoing.domain.project.domain.Project;
import com.woongeya.zoing.domain.project.domain.repository.MemberRepository;
import com.woongeya.zoing.domain.project.domain.repository.ProjectRepository;
import com.woongeya.zoing.domain.project.domain.type.MemberType;
import com.woongeya.zoing.domain.project.domain.type.State;
import com.woongeya.zoing.domain.project.presetation.dto.request.CreateProjectRequestDto;
import com.woongeya.zoing.domain.user.UserFacade;
import com.woongeya.zoing.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectCreateService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final UserFacade userFacade;

    @Transactional
    public void execute(CreateProjectRequestDto request) {
        User user = userFacade.getCurrentUser();

        Project project = projectRepository.save(Project.builder()
                .name(request.getName())
                .content(request.getContent())
                .state(State.RECRUIT)
                .writer(user.getName())
                .build());

        memberRepository.save(Member.builder()
                .user(user)
                .project(project)
                .role(MemberType.ADMIN)
                .build());
    }
}