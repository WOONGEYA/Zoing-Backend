package com.woongeya.zoing.domain.application.service;

import com.woongeya.zoing.domain.application.domain.Application;
import com.woongeya.zoing.domain.project.ProjectFacade;
import com.woongeya.zoing.domain.project.domain.Project;
import com.woongeya.zoing.domain.application.presetation.dto.request.ApplicationCreateRequest;
import com.woongeya.zoing.domain.user.UserFacade;
import com.woongeya.zoing.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationCreateService {

    private final ProjectFacade projectFacade;
    private final UserFacade userFacade;

    @Transactional
    public void execute(ApplicationCreateRequest request, Long id) {

        User user = userFacade.getCurrentUser();
        Project project = projectFacade.getProject(id);

        Application.builder()
                .user(user)
                .project(project)
                .introduce(request.getIntroduce())
                .build();
    }
}
